/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.sism.territoriale.service;


import it.mds.sdk.connettoremds.ConnettoreMds;
import it.mds.sdk.connettoremds.exception.ConnettoreMdsException;
import it.mds.sdk.connettoremds.gaf.webservices.bean.ArrayOfUploadEsito;
import it.mds.sdk.connettoremds.gaf.webservices.bean.ResponseUploadFile;
import it.mds.sdk.connettoremds.gaf.webservices.bean.UploadEsito;
import it.mds.sdk.flusso.sism.territoriale.parser.regole.ParserTracciatoImpl;
import it.mds.sdk.flusso.sism.territoriale.parser.regole.RecordDtoSismTerritoriale;
import it.mds.sdk.flusso.sism.territoriale.parser.regole.conf.ConfigurazioneFlussoSismTerritoriale;
import it.mds.sdk.flusso.sism.territoriale.tracciato.TracciatoSplitterImpl;
import it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie.TerritorialePrestazioniSanitarie;
import it.mds.sdk.gestoreesiti.GestoreRunLog;
import it.mds.sdk.gestoreesiti.conf.Configurazione;
import it.mds.sdk.gestoreesiti.modelli.InfoRun;
import it.mds.sdk.gestoreesiti.modelli.ModalitaOperativa;
import it.mds.sdk.gestoreesiti.modelli.StatoRun;
import it.mds.sdk.gestorefile.GestoreFile;
import it.mds.sdk.gestorefile.exception.XSDNonSupportedException;
import it.mds.sdk.gestorefile.factory.GestoreFileFactory;
import it.mds.sdk.libreriaregole.dtos.CampiInputBean;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.gestorevalidazione.BloccoValidazione;
import it.mds.sdk.libreriaregole.parser.ParserRegole;
import it.mds.sdk.libreriaregole.parser.ParserTracciato;
import it.mds.sdk.libreriaregole.regole.beans.RegoleFlusso;
import it.mds.sdk.libreriaregole.tracciato.TracciatoSplitter;
import it.mds.sdk.libreriaregole.validator.ValidationEngine;
import it.mds.sdk.rest.exception.ParseCSVException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Component("flussoSismTerrService")
public class FlussoSismTerrService {

    private final String CATEGORIA_FLUSSI = "TER";
    // ANT (Anagrafica)/CNT (Contatto)/PST (Prestazione Sanitaria)
    private final String NOME_FLUSSO_ANAGRAFICA = "ANT";
    private final String NOME_FLUSSO_CONTATTO = "CNT";
    private final String NOME_FLUSSO_PRESTAZIONE = "PST";
    private static final String PERIODO_RIFERIMENTO_SEPARATOR = "I";
    private static final String MDS_RESP_OK = "IF00";
    private static final String XML_FLUSSO_TAG = "</TerritorialePrestazioniSanitarie><TerritorialePrestazioniSanitarie>";
    private final String NOME_FILE_XSD_SISM = "PST.xsd";
    private final Configurazione config = new Configurazione();

    private ParserRegole parserRegole;
    private ParserTracciato parserTracciato;
    private ValidationEngine validationEngine;
    private TracciatoSplitter<RecordDtoSismTerritoriale> tracciatoSplitter;
    private ConnettoreMds connettoreMds;
    private final ConfigurazioneFlussoSismTerritoriale conf;

    @Autowired
    public FlussoSismTerrService(@Qualifier("parserRegoleSismTerr") final ParserRegole parserRegole,
                                 @Qualifier("parserTracciatoSismTerr") final ParserTracciato parserTracciato,
                                 @Qualifier("validationEngine") final ValidationEngine validationEngine,
                                 @Qualifier("tracciatoSplitterSismTerr") final TracciatoSplitter<RecordDtoSismTerritoriale> tracciatoSplitter,
                                 @Qualifier("connettoreMds") final ConnettoreMds connettoreMds,
                                 @Qualifier("configurazioneFlussoSismTerr") ConfigurazioneFlussoSismTerritoriale conf) {
        this.parserRegole = parserRegole;
        this.parserTracciato = parserTracciato;
        this.validationEngine = validationEngine;
        this.tracciatoSplitter = tracciatoSplitter;
        this.connettoreMds = connettoreMds;
        this.conf = conf;
    }

    @Async
    public void validazioneBlocchi(int dimensioneBlocco, String nomeFile, RegoleFlusso regoleFlusso, String idRun,
                                   String idClient, ModalitaOperativa modalitaOperativa, String periodoRiferimento,
                                   String annoRiferimento, String codiceRegione, GestoreRunLog gestoreRunLog) {
        //Loop
        List<RecordDtoGenerico> records;
        int inizio = 0;
        int fine = dimensioneBlocco;
        ParserTracciatoImpl parser = getParserTracciatoImpl();
        CampiInputBean campiInputBean = CampiInputBean.builder()
                .withAnnoRiferimentoInput(annoRiferimento)
                .withCodiceRegioneInput(codiceRegione)
                .withPeriodoRiferimentoInput(periodoRiferimento)
                .build();
        //Legge un blocco dal CSV e scrive records, su questo blocco chiama il valida flusso
        TerritorialePrestazioniSanitarie territorialePrestazioniSanitarie = null;
        File file = getfileFromPath(conf.getFlusso().getPercorso() + nomeFile);
        String nomeFileXml = conf.getXmlOutput().getPercorso() + "SDK_TER_PST_" + periodoRiferimento + "_" + idRun + ".xml";

        String nomeFileXmlTmp = nomeFileXml + "tmp";
        int totale = 0;
        int accettati = 0;
        int scartati = 0;
        int numeroBlocco = 0;
        int numRecordValidati = 0;
        final String percorso = String.format("%s/ESITO_%s.json", config.getEsito().getPercorso(), idRun);
        final String percorsoTemp = String.format("%s/ESITO_%s_TEMP.json", config.getEsito().getPercorso(), idRun);
        do {
            numeroBlocco++;
            log.info("Inizio elaborazione blocco {} con dimensione massima {} di idRun {}", numeroBlocco,
                    dimensioneBlocco, idRun);
            try {
                records = parser.parseTracciatoBlocco(file, inizio, fine);
            } catch (ParseCSVException pe) {
                log.error("Errore di parsing del blocco {} idRun {}", numeroBlocco, idRun, pe);
                InfoRun infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_GENERICO);
                infoRun.setDescrizioneStatoEsecuzione(pe.getMessage());
                infoRun.setDataFineEsecuzione(new Timestamp(System.currentTimeMillis()));
                gestoreRunLog.updateRun(infoRun);
                return;
            }
            inizio = inizio + dimensioneBlocco;
            fine = fine + records.size();
            records.forEach(r -> r.setCampiInput(campiInputBean));
            BloccoValidazione bloccoValidazione = validationEngine.startValidaFlussoBlocco(records, regoleFlusso, idRun, gestoreRunLog, numRecordValidati);

            numRecordValidati += records.size();

            totale = totale + bloccoValidazione.getNumeroRecord();
            scartati = scartati + bloccoValidazione.getScartati();
            accettati = accettati + bloccoValidazione.getNumeroRecord() - bloccoValidazione.getScartati();
            //scrittura esito parziale o append
            validationEngine.creaFileEsiti(bloccoValidazione);
            //Scrittura XML solo se almeno un record è presente
            List<RecordDtoSismTerritoriale> recordConv =
                    bloccoValidazione.getRecordList().stream().map(k -> (RecordDtoSismTerritoriale) k).collect(Collectors.toList());
            boolean isWriteOk = true;
            if (!recordConv.isEmpty()) {
                log.debug("Per il blocco {} di idRun {} presenti {} record da trasformare in xml", numeroBlocco,
                        idRun, recordConv.size());
                territorialePrestazioniSanitarie = ((TracciatoSplitterImpl) tracciatoSplitter).creaTerritorialePrestazioniSanitarie(recordConv, territorialePrestazioniSanitarie);
            } else {
                log.warn("Nessun record valido per il blocco {} di idRun {}, non viene generato xml", numeroBlocco, idRun);
                isWriteOk = false;
            }

            //creo xml solo se sono presenti record accettati
            if (isWriteOk) {
                GestoreFile gestoreFile = GestoreFileFactory.getGestoreFile("XML");
                URL urlXsd = this.getClass().getClassLoader().getResource("PST.xsd");
                log.debug("URL dell'XSD per la validazione idrun {} : {}", idRun, urlXsd);
                log.info("Inizio scrittura file temporaneo {} con xsd {} per idRun {}", nomeFileXmlTmp,
                        urlXsd.getFile(), idRun);
                try {
                    gestoreFile.scriviDtoFragment(territorialePrestazioniSanitarie, nomeFileXmlTmp, urlXsd);
                } catch (XSDNonSupportedException e) {
                    log.error("XSD non validato. ", e);
                    InfoRun infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_VALIDAZIONE_SDK);
                    infoRun.setDescrizioneStatoEsecuzione(e.getMessage());
                    infoRun.setDataFineEsecuzione(new Timestamp(System.currentTimeMillis()));
                    gestoreRunLog.updateRun(infoRun);
                    boolean isFileEsitiCleaned = validationEngine.formatJsonEsiti(percorso, percorsoTemp);
                    if (!isFileEsitiCleaned) {
                        log.warn("ATTENZIONE: Un'operazione tra copia, rename o eliminazione del file temp degli esiti non è andata a buon fine.");
                        throw new RuntimeException();
                    }
                    return;
                }
                log.info("Scritto file temporaneo {} per idRun {}", nomeFileXmlTmp, idRun);
                territorialePrestazioniSanitarie = null;
                System.gc();
            }

            log.info("Terminata elaborazione blocco {} di idRun {} con questi risultati:\n" +
                            "Record elaborati: {}\nRecord accettati: {}\nRecord scartati: {}\n" +
                            "Totale dei blocchi elaborati fino ad ora:\nRecord elaborati: {}\nRecord accettati: {}\nRecord " +
                            "scartati: {}", numeroBlocco, idRun, bloccoValidazione.getNumeroRecord(),
                    bloccoValidazione.getNumeroRecord() - bloccoValidazione.getScartati(), bloccoValidazione.getScartati(), totale
                    , accettati, scartati);
        } while (fine - inizio >= dimensioneBlocco);

        boolean isFileEsitiCleaned = validationEngine.formatJsonEsiti(percorso, percorsoTemp);

        if (!isFileEsitiCleaned) {
            log.warn("ATTENZIONE: Un'operazione tra copia, rename o eliminazione del file temp degli esiti non è andata a buon fine.");
            throw new RuntimeException();
        }

        InfoRun infoRun = gestoreRunLog.getRun(idRun);

        //TODO rendere il file XML utilizzabile
        if (accettati > 0) {
            log.info("Inizio pulizia file {}", nomeFileXmlTmp);
            String nomeFilePulito = "";
            try {
                nomeFilePulito = validationEngine.puliziaFileSism(nomeFileXmlTmp, nomeFileXml, XML_FLUSSO_TAG);
                boolean isFileXMLValidated = validationEngine.validateXML(nomeFilePulito, NOME_FILE_XSD_SISM);

                if (!isFileXMLValidated) {
                    //log.warn("ATTENZIONE: Un'operazione tra copia, rename o eliminazione del file temp XML OUTPUT non è andata a buon fine.");
                    throw new XSDNonSupportedException();
                }
            } catch (XSDNonSupportedException x) {
                log.error("XSD non validato.", x);
                infoRun.setStatoEsecuzione(StatoRun.KO_VALIDAZIONE_SDK);
                infoRun.setDescrizioneStatoEsecuzione(x.getMessage());
                infoRun.setDataFineEsecuzione(new Timestamp(System.currentTimeMillis()));
                gestoreRunLog.updateRun(infoRun);
                return;
            }
            log.info("Pulito file {}", nomeFilePulito);
        }

        infoRun.setNumeroRecord(totale);
        infoRun.setNumeroRecordScartati(scartati);
        infoRun.setNumeroRecordAccettati(accettati);
        infoRun.setDataFineEsecuzione(new Timestamp(System.currentTimeMillis()));
        infoRun = gestoreRunLog.updateRun(infoRun);

        //Update inforun ad elaborata se modalitàOperativa = T
        if (modalitaOperativa == modalitaOperativa.T) {
            log.debug("modalità operativa T, idRun {} in stato elaborata", idRun);
            infoRun.setStatoEsecuzione(StatoRun.ELABORATA);
            gestoreRunLog.updateRun(infoRun);
            return;
        }
        String soglia = conf.getSogliaInvio().getSoglia();
        var divisiore = new BigDecimal(totale);
        BigDecimal sogliaCalcolata =
                new BigDecimal(accettati).divide(divisiore, 1, RoundingMode.FLOOR).multiply(new BigDecimal("100"));
        log.info("Soglia calcolata {}, soglia fissata {}", sogliaCalcolata, soglia);
        if (accettati > 0 && (sogliaCalcolata.compareTo(new BigDecimal(soglia)) >= 0) && modalitaOperativa.equals(ModalitaOperativa.P)) {
            periodoRiferimento = periodoRiferimento + PERIODO_RIFERIMENTO_SEPARATOR;
            this.inviaTracciatoMds(idRun, nomeFileXml, gestoreRunLog, periodoRiferimento, annoRiferimento);
        } else if (accettati == 0) {
            log.warn("Record da mandare al MdS vuoti per idRun {}", idRun);
            infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_INVIO_SOGLIA);
            infoRun.setDescrizioneStatoEsecuzione("Soglia minima non raggiunta");
            gestoreRunLog.updateRun(infoRun);
        } else {
            log.warn("Soglia {} non raggiunta, è {}", soglia, sogliaCalcolata);
            infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_INVIO_SOGLIA);
            infoRun.setDescrizioneStatoEsecuzione("Soglia minima non raggiunta");
            gestoreRunLog.updateRun(infoRun);
        }
    }

    public void inviaTracciatoMds(String idRun, String nomeFileXml, GestoreRunLog gestoreRunLog,
                                  String periodoRiferimento, String annoRiferimento) {
        InfoRun infoRun = gestoreRunLog.getRun(idRun);

        infoRun.setNomeFileOutputMds(nomeFileXml);
        gestoreRunLog.updateRun(infoRun);
        List<Path> listPath = List.of(Path.of(nomeFileXml));
        try {
            ResponseUploadFile resp = connettoreMds.invioTracciati(listPath, CATEGORIA_FLUSSI, "", periodoRiferimento, annoRiferimento);
            if (!resp.getErrorCode().isBlank()) {
                log.warn("errore presente in risposta mds per idRun {} : {}", idRun, resp.getErrorCode());
                infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_INVIO_MINISTERO);
            } else if (resp.getListaEsitiUpload().getItem().stream().filter(i -> !MDS_RESP_OK.equals(i.getEsito())).collect(Collectors.toList()).isEmpty()) {
                log.debug("Riposta MDS ok per run {}, cambio stato a {} e copia xml", idRun, StatoRun.ELABORATA);
                infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.ELABORATA);

                for (Path pathDaCopiare : listPath) {
                    fileCopy(pathDaCopiare,
                            Paths.get(conf.getSent().getPercorsoSent(), pathDaCopiare.getFileName().toString()));
                }
            } else {
                log.warn("Errore risposta MDS per idRun {}, cambio stato a {}", idRun, StatoRun.KO_INVIO_MINISTERO);
                infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_INVIO_MINISTERO);
            }
            infoRun.setCodiceErroreInvioFlusso(resp.getErrorCode());
            infoRun.setTestoErroreInvioFlusso(resp.getErrorText());
            if (resp.getErrorCode() == null || resp.getErrorCode().isBlank()) {
                ArrayOfUploadEsito listaEsitiResp = resp.getListaEsitiUpload();
                StringJoiner joiner = new StringJoiner("|");
                for (UploadEsito esitoFile : listaEsitiResp.getItem()) {
                    joiner.add(esitoFile.getNomeFile() + "-" + esitoFile.getEsito());
                }
                infoRun.setEsitoAcquisizioneFlusso(joiner.toString());
                List<String> listStringhe = listaEsitiResp.getItem().stream().map(UploadEsito::getIdUpload).collect(Collectors.toList());
                if (!listStringhe.isEmpty()) {
                    infoRun.setIdUploads(listStringhe);
                }
            }
            gestoreRunLog.updateRun(infoRun);
        } catch (ConnettoreMdsException | IOException e) {
            log.error("Errore invioFlussi al ministero per file {}", listPath);
            infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_INVIO_MINISTERO);
            infoRun.setDescrizioneStatoEsecuzione(e.getMessage());
            gestoreRunLog.updateRun(infoRun);
        } catch (Throwable t) {
            infoRun = gestoreRunLog.cambiaStatoRun(idRun, StatoRun.KO_GENERICO);
            log.error("Errore generico idRun {}", idRun, t);
            infoRun.setDescrizioneStatoEsecuzione(t.getMessage());
            gestoreRunLog.updateRun(infoRun);
        }
    }

    public void fileCopy(Path pathDaCopiare, Path path) throws IOException {
        Files.copy(pathDaCopiare, path);
    }

    public ParserTracciatoImpl getParserTracciatoImpl() {
        return new ParserTracciatoImpl();
    }

    public File getfileFromPath(String s) {
        return new File(s);
    }

}
