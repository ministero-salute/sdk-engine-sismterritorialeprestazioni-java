/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.sism.territoriale.tracciato;

import it.mds.sdk.flusso.sism.territoriale.parser.regole.RecordDtoSismTerritoriale;
import it.mds.sdk.flusso.sism.territoriale.parser.regole.conf.ConfigurazioneFlussoSismTerritoriale;
import it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie.ObjectFactory;
import it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie.PeriodoRiferimento;
import it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie.TerritorialePrestazioniSanitarie;
import it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie.TipoOperazione;
import it.mds.sdk.gestorefile.GestoreFile;
import it.mds.sdk.gestorefile.factory.GestoreFileFactory;
import it.mds.sdk.libreriaregole.tracciato.TracciatoSplitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("tracciatoSplitterSismTerr")
public class TracciatoSplitterImpl implements TracciatoSplitter<RecordDtoSismTerritoriale> {

    @Override
    public List<Path> dividiTracciato(Path tracciato) {
        return null;
    }


    @Override
    public List<Path> dividiTracciato(List<RecordDtoSismTerritoriale> records, String idRun) {

        try {
            ConfigurazioneFlussoSismTerritoriale conf = getConfigurazione();
            String annoRif = records.get(0).getAnnoRiferimento();
            String codiceRegione = records.get(0).getCodiceRegione();

            //XML PRESTAZIONI
            ObjectFactory objPrestazioni = getObjectFactory();
            TerritorialePrestazioniSanitarie territorialePrestazioniSanitarie = objPrestazioni.createTerritorialePrestazioniSanitarie();
            territorialePrestazioniSanitarie.setAnnoRiferimento(annoRif);
            territorialePrestazioniSanitarie.setCodiceRegione(codiceRegione);
            territorialePrestazioniSanitarie.setPeriodoRiferimento(PeriodoRiferimento.fromValue(records.get(0).getPeriodoRiferimento()));

            for (RecordDtoSismTerritoriale r : records) {
                if (!r.getTipoOperazionePrestazione().equalsIgnoreCase("NM")) {
                    creaPrestazioniXml(r, territorialePrestazioniSanitarie, objPrestazioni);
                }
            }

            GestoreFile gestoreFile = GestoreFileFactory.getGestoreFile("XML");

            //recupero il path del file xsd di prestazioni
            URL resourcePrestazioni = this.getClass().getClassLoader().getResource("PST.xsd");
            log.debug("URL dell'XSD per la validazione idrun {} : {}", idRun, resourcePrestazioni);

            //scrivi XML PRESTAZIONI
            String pathPrestazioni = conf.getXmlOutput().getPercorso() + "SDK_TER_PST_" + records.get(0).getPeriodoRiferimento() + "_" + idRun + ".xml";
            //gestoreFile.scriviDto(territorialePrestazioniSanitarie, pathPrestazioni, resourcePrestazioni);

            return List.of(Path.of(pathPrestazioni));
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            log.error("[{}].dividiTracciato  - records[{}]  - idRun[{}] -" + e.getMessage(),
                    this.getClass().getName(),
                    records.stream().map(obj -> "" + obj.toString()).collect(Collectors.joining("|")),
                    idRun,
                    e
            );
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossibile validare il csv in ingresso. message: " + e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void creaPrestazioniXml(RecordDtoSismTerritoriale r, TerritorialePrestazioniSanitarie territorialePrestazioniSanitarie,
                                    ObjectFactory objPrestazioni) {
        //ASL RIF
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento currentAsl = getCurrentAsl(territorialePrestazioniSanitarie, r);
        if (currentAsl == null) {
            currentAsl = creaAslPrestazioni(r.getCodiceAziendaSanitariaRiferimento(), objPrestazioni);
            territorialePrestazioniSanitarie.getAziendaSanitariaRiferimento().add(currentAsl);
        }

        //DSM
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM currentDsm = getCurrentDsm(currentAsl, r);
        if (currentDsm == null) {
            currentDsm = creaDSMPrestazioni(r.getCodiceDipartimentoSaluteMentale(), objPrestazioni);
            currentAsl.getDSM().add(currentDsm);
        }

        //ASSISTITO
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito currentAssisitito = getCurrentAssistito(currentDsm, r);
        if (currentAssisitito == null) {
            currentAssisitito = creaAssistitoPrestazioni(r, objPrestazioni);
            currentDsm.getAssistito().add(currentAssisitito);
        }

        //STRUTTURA
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura currentStruttura = getStruttura(currentAssisitito, r);
        if (currentStruttura == null) {
            currentStruttura = creaStrutturaPrestazioni(r, objPrestazioni);
            currentAssisitito.getStruttura().add(currentStruttura);
        }

        //CONTATTO
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto currentContatto = getContatto(currentStruttura, r);
        if (currentContatto == null) {
            currentContatto = creaContattoPrestazioni(r, objPrestazioni);
            currentStruttura.getContatto().add(currentContatto);
        }

        //PRESTAZIONI
        currentContatto.getPrestazioni().add(creaPrestazioni(r, objPrestazioni));

    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento creaAslPrestazioni(String codAsl,
                                                                                            ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento asl = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimento();
        asl.setCodiceAziendaSanitariaRiferimento(codAsl);
        return asl;
    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM creaDSMPrestazioni(String codDsm,
                                                                                                ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM dsm = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimentoDSM();
        dsm.setCodiceDSM(codDsm);
        return dsm;
    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito creaAssistitoPrestazioni(RecordDtoSismTerritoriale r,
                                                                                                                ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito assistito = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimentoDSMAssistito();
        assistito.setIdRec(r.getIdRecord());
        return assistito;
    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura creaStrutturaPrestazioni(RecordDtoSismTerritoriale r,
                                                                                                                          ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura struttura = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimentoDSMAssistitoStruttura();
        struttura.setCodiceStruttura(r.getCodiceStruttura());
        return struttura;
    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto creaContattoPrestazioni(RecordDtoSismTerritoriale r,
                                                                                                                                  ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto contatto = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimentoDSMAssistitoStrutturaContatto();
        contatto.setIDContatto(r.getIdContatto());
        return contatto;
    }

    private TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto.Prestazioni creaPrestazioni(RecordDtoSismTerritoriale r,
                                                                                                                                      ObjectFactory objPrestazioni) {
        TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto.Prestazioni prestazioni = objPrestazioni.createTerritorialePrestazioniSanitarieAziendaSanitariaRiferimentoDSMAssistitoStrutturaContattoPrestazioni();
        XMLGregorianCalendar dataIntervento = null;
        try {
            dataIntervento = r.getDataIntervento() != null ? DatatypeFactory.newInstance().newXMLGregorianCalendar(r.getDataIntervento()) : null;
        } catch (DatatypeConfigurationException e) {
            log.error("Errore conversione XMLGregorianCalendar date", e);
        }
        prestazioni.setDataIntervento(dataIntervento);
        prestazioni.setTipoOperazione(TipoOperazione.fromValue(r.getTipoOperazionePrestazione()));
        prestazioni.setModalitaErogazione(r.getModalitaErogazioneIntervento());
        prestazioni.setProgressivoPrestazioniContatto(r.getProgressivoPrestazioniContatto());
        prestazioni.setOperatore1(r.getOperatore1());
        prestazioni.setOperatore2(r.getOperatore2());
        prestazioni.setOperatore3(r.getOperatore3());
        prestazioni.setSedeIntervento(r.getSedeIntervento());
        prestazioni.setTipoIntervento(r.getTipoIntervento());

        return prestazioni;
    }

    public TerritorialePrestazioniSanitarie creaTerritorialePrestazioniSanitarie(List<RecordDtoSismTerritoriale> records, TerritorialePrestazioniSanitarie territorialePrestazioniSanitarie) {

        //Imposto gli attribute element
        String annoRif = records.get(0).getAnnoRiferimento();
        String codiceRegione = records.get(0).getCodiceRegione();

        if (territorialePrestazioniSanitarie == null) {
            ObjectFactory objSismTer = getObjectFactory();

            territorialePrestazioniSanitarie = objSismTer.createTerritorialePrestazioniSanitarie();
            territorialePrestazioniSanitarie.setAnnoRiferimento(annoRif);
            territorialePrestazioniSanitarie.setCodiceRegione(codiceRegione);
            territorialePrestazioniSanitarie.setPeriodoRiferimento(PeriodoRiferimento.fromValue(records.get(0).getPeriodoRiferimento()));

            for (RecordDtoSismTerritoriale r : records) {
                if (!r.getTipoOperazionePrestazione().equalsIgnoreCase("NM")) {
                    creaPrestazioniXml(r, territorialePrestazioniSanitarie, objSismTer);
                }
            }

        }
        return territorialePrestazioniSanitarie;
    }

    public ConfigurazioneFlussoSismTerritoriale getConfigurazione() {
        return new ConfigurazioneFlussoSismTerritoriale();
    }

    private ObjectFactory getObjectFactory() {
        return new ObjectFactory();
    }

    public TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito getCurrentAssistito(TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM currentDsm, RecordDtoSismTerritoriale r) {
        return currentDsm.getAssistito()
                .stream()
                .filter(ass -> r.getIdRecord().equalsIgnoreCase(ass.getIdRec()))
                .findFirst()
                .orElse(null);
    }

    public TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM getCurrentDsm(TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento currentAsl, RecordDtoSismTerritoriale r) {
        return currentAsl.getDSM()
                .stream()
                .filter(dsm -> r.getCodiceDipartimentoSaluteMentale().equalsIgnoreCase(dsm.getCodiceDSM()))
                .findFirst()
                .orElse(null);
    }

    public TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento getCurrentAsl(TerritorialePrestazioniSanitarie territorialePrestazioniSanitarie, RecordDtoSismTerritoriale r) {
        return territorialePrestazioniSanitarie.getAziendaSanitariaRiferimento()
                .stream()
                .filter(asl -> r.getCodiceAziendaSanitariaRiferimento().equalsIgnoreCase(asl.getCodiceAziendaSanitariaRiferimento()))
                .findFirst()
                .orElse(null);
    }

    public TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura getStruttura(TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito currentAssisitito, RecordDtoSismTerritoriale r) {
        return currentAssisitito.getStruttura()
                .stream()
                .filter(str -> r.getCodiceStruttura().equalsIgnoreCase(str.getCodiceStruttura()))
                .findFirst()
                .orElse(null);
    }

    public TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura.Contatto getContatto(
            TerritorialePrestazioniSanitarie.AziendaSanitariaRiferimento.DSM.Assistito.Struttura currentStruttura,
            RecordDtoSismTerritoriale r) {
        return currentStruttura.getContatto()
                .stream()
                .filter(cnt -> r.getIdContatto().equals(cnt.getIDContatto()))
                .findFirst()
                .orElse(null);
    }

}
