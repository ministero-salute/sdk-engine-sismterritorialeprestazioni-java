# **1 Introduzione**

## ***1.1 Obiettivi del documento***

Il Ministero della Salute (MdS) metterà a disposizione degli Enti, da cui riceve dati, applicazioni SDK specifiche per flusso logico e tecnologie applicative (Java, PHP e C#) per verifica preventiva (in casa Ente) della qualità del dato prodotto.

![](img/Aspose.Words.93c3c2a1-b259-4673-a7d3-0943f2b35da0.002.png)

Nel presente documento sono fornite la struttura e la sintassi dei tracciati previsti dalla soluzione SDK per avviare il proprio processo elaborativo, nonché i relativi schemi xsd di convalida e i controlli di merito sulla qualità, completezza e coerenza dei dati.

Gli obiettivi del documento sono:

- fornire una descrizione funzionale chiara e consistente dei tracciati di input a SDK;
- fornire le regole funzionali per la verifica di qualità, completezza e coerenza dei dati.

In generale, la soluzione SDK è costituita da 2 diversi moduli applicativi (Access Layer e Validation Engine) per abilitare

- l’interoperabilità con il contesto tecnologico dell’Ente in cui la soluzione sarà installata;
- la validazione del dato ed il suo successivo invio verso il MdS.

La figura che segue descrive la soluzione funzionale ed i relativi benefici attesi.

![](img/Aspose.Words.93c3c2a1-b259-4673-a7d3-0943f2b35da0.003.png)

## ***1.2 Acronimi***

Nella tabella riportata di seguito sono elencati tutti gli acronimi e le definizioni adottati nel presente documento.


|**#**|**Acronimo / Riferimento**|**Definizione**|
| - | - | - |
|1|NSIS|Nuovo Sistema Informativo Sanitario|
|2|SDK|Software Development Kit|
|3|SISM|Sistema Informativo Salute Mentale|


# **2. Architettura SDK**

## ***2.1 Architettura funzionale***

Di seguito una rappresentazione architetturale del processo di gestione e trasferimento dei flussi dall’ente verso l’area MdS attraverso l’utilizzo dell’applicativo SDK e il corrispondente diagramma di sequenza.

![Diagram Description automatically generated](img/Aspose.Words.93c3c2a1-b259-4673-a7d3-0943f2b35da0.004.jpeg)

1. L’utente dell’ente caricherà in una apposita directory (es. /sdk/input/) il flusso sorgente.  L’utente avvierà l’SDK passando in input una serie di parametri descritti in dettaglio al paragrafo 3.1
1. La compenente Access Layer estrae dalla chiamata dell’ente i parametri utilizzati per lanciare l’SDK,  genera un identificativo ID\_RUN, e un file chiamato “{ID\_RUN}.json” in cui memorizza le informazioni dell’esecuzione.
1. I record del flusso verranno sottoposti alle logiche di validazione e controllo definite nel Validation Engine. Nel processare il dato, il Validation Engine acquisirà da MdS eventuali anagrafiche di validazione del dato stesso.
1. Generazione del file degli scarti contenente tutti i record in scarto con evidenza degli errori riscontrati. I file di scarto saranno memorizzati in cartelle ad hoc (es. /sdk/esiti).
1. Tutti i record che passeranno i controlli verranno inseriti in un file xml copiato in apposita cartella (es /sdk/xml\_output), il quale verrà eventualmente trasferito a MdS utilizzando la procedura “invioFlussi” esposta da GAF WS (tramite PDI). A fronte di un’acquisizione, il MdS fornirà a SDK un identificativo (ID\_UPLOAD) che sarà usato da SDK sia per fini di logging che di recupero del File Unico degli Scarti.
1. A conclusione del processo di verifica dei flussi, il Validation Engine eseguirà le seguenti azioni:

 a. Aggiornamento file contenente il riepilogo dell’esito dell’elaborazione del Validation Engine e del ritorno dell’esito da parte di MdS. I file contenenti l’esito dell’elaborazione saranno memorizzati in cartelle ad hoc (es. /sdk/run).

 b. Consolidamento del file di log applicativo dell’elaborazione dell’SDK in cui sono disponibili una serie di informazioni tecniche (Es. StackTrace di eventuali errori).

 c.  Copia del file generato al punto 5, se correttamente inviato al MdS, in apposita cartella (es. /sdk/sent).

Ad ogni step del precedente elenco e a partire dal punto 2, l’SDK aggiornerà di volta in volta il file contenente l’esito dell’elaborazione.

**Nota: l’SDK elaborerà un solo file di input per esecuzione.**

In generale, le classi di controllo previste su Validation Engine sono:

- Controlli FORMALI (es. correttezza dei formati e struttura record)
- Controlli SINTATTICI (es. check correttezza del Codice Fiscale)
- Controlli di OBBLIGATORIETÀ DEL DATO (es. Codice Prestazione su flusso…)
- Controlli STRUTTURE FILE (es. header/footer ove applicabile)
- Controlli di COERENZA CROSS RECORD
- Controlli di corrispondenza dei dati trasmessi con le anagrafiche di riferimento
- Controlli di esistenza di chiavi duplicate nel file trasmesso rispetto alle chiavi logiche individuate per ogni tracciato.

Si sottolinea che la soluzione SDK non implementa controlli che prevedono la congruità del dato input con la base date storica (es: non viene verificato se per un nuovo inserimento la chiave del record non sia già presente sulla struttura dati MdS).

## ***2.2 Architettura di integrazione***

La figura sottostante mostra l’architettura di integrazione della soluzione SDK con il MdS. Si evidenzia in particolare che:

- Tutti i dati scambiati fra SDK e MdS saranno veicolati tramite Porta di Interoperabilità (PDI);
- Il MdS esporrà servizi (API) per il download di dati anagrafici;
- SDK provvederà ad inviare vs MdS l’output (record validati) delle proprie elaborazioni. A fronte di tale invio, il MdS provvederà a generare un identificativo di avvenuta acquisizione del dato (ID\_UPLOAD) che SDK memorizzerà a fini di logging.


![Diagram Description automatically generated](img/Aspose.Words.93c3c2a1-b259-4673-a7d3-0943f2b35da0.006.png)


# **3. Funzionamento della soluzione SDK**

In questa sezione è descritta le specifica di funzionamento del flusso **PST**  per l’alimentazione dello stesso.


## ***3.1 Input SDK***

In fase di caricamento del file verrano impostati i seguenti parametri che andranno in input al SDK in fase di processamento del file:


|**NOME PARAMETRO**|**DESCRIZIONE**|**LUNGHEZZA**|**DOMINIO VALORI**|
| :- | :- | :- | :- |
|ID CLIENT|Identificativo univoco della transazione che fa richiesta all'SDK|100|Non definito|
|NOME FILE INPUT|Nome del file per il quale si richiede il processamento lato SDK|256|Non definito|
|ANNO RIFERIMENTO|Stringa numerica rappresentante l’anno di riferimento per cui si intende inviare la fornitura|4|Anno (Es. 2022)|
|PERIODO RIFERIMENTO|Stringa alfanumerica rappresentante il periodo per il quale si intende inviare la fornitura. In fase di invio della fornitura verso Mds si dovrà concatenare al valore di questo campo il carattere I (i MAIUSCOLA) (Es. S1I)|2|S1, S2|
|TIPO TRASMISSIONE |Indica se la trasmissione dei dati verso MDS avverrà in modalità full (F) o record per record (R). Per questo flusso la valorizzazione del parametro sarà impostata di default a F|1|F/R|
|FINALITA’ ELABORAZIONE|Indica se i flussi in output prodotti dal SDK verranno inviati verso MDS (Produzione) oppure se rimarranno all’interno del SDK e il processamento vale solo come test del flusso (Test)|1|Produzione/Test|
|CODICE REGIONE|<p>Individua la Regione a cui afferisce la struttura. Il codice da utilizzare è quello a tre caratteri definito con DM 17 settembre 1986, pubblicato nella Gazzetta Ufficiale n.240 del 15 ottobre 1986, e successive modifiche, utilizzato anche nei modelli per le rilevazioni delle attività gestionali ed economiche delle Aziende unità sanitarie locali.</p><p></p>|3|Es. 010|

## ***3.2 Tracciato input a SDK***

Il flusso di input avrà formato **csv** posizionale e una naming convention libera a discrezione dell’utente che carica il flusso senza alcun vincolo di nomenclatura specifica (es: nome\_file.csv). Il separatore per il file csv sarà la combinazione di caratteri tra doppi apici: “~“.

All’interno della specifica del tracciato sono indicati i dettagli dei campi di business del tracciato di input atteso da SDK, il quale differisce per i diversi flussi dell’area SISM. All’interno di tale file è presente la colonna **Posizione nel file** la quale rappresenta l’ordinamento delle colonne del tracciato di input da caricare all’SDK.

Di seguito la tabella in cui è riportata la specifica del tracciato di input per il flusso in oggetto:

|**Nome campo**|**Posizione nel File**|**Key**|**Descrizione**|**Tipo** |**Obbligatorietà**|**Informazioni di Dominio**|**Lunghezza campo**|**XPATH Tracciato Output**|
| :- | :- | :- | :-: | :-: | :-: | :-: | :-: | :-: |
|Anno di riferimento|1|KEY|Indica l’anno a cui si riferisce la rilevazione.|N|OBB|Formato AAAA|4|<br>/TerritorialePrestazioniSanitarie/AnnoRiferimento|
|Periodo di Riferimento|2|KEY|Indica il semestre a cui si riferisce la rilevazione.|AN|OBB|Valori Accettati<br>• S1<br>• S2|2|<br>/TerritorialePrestazioniSanitarie/PeriodoRiferimento|
|Codice Regione|3|KEY|Individua la Regione a cui afferisce la struttura. Il codice da utilizzare è quello a tre caratteri definito con DM 17 settembre 1986, pubblicato nella Gazzetta Ufficiale n.240 del 15 ottobre 1986, e successive modifiche, utilizzato anche nei modelli per le rilevazioni delle attività gestionali ed economiche delle Aziende unità sanitarie locali.|AN|OBB|Riferimento: Allegato 1|3|<br>/TerritorialePrestazioniSanitarie/CodiceRegione|
|Codice Azienda Sanitaria di Riferimento|4|KEY|Identifica l’azienda sanitaria locale in cui e’ sito il Servizio. Il codice da utilizzare è quello a tre caratteri usato anche nei modelli per le rilevazioni delle attività gestionali ed economiche delle Aziende unità sanitarie locali (codici di cui al D.M. 05/12/2006 e successive modifiche).|AN|OBB|Riferimento: MRA (Monitoraggio Rete Assistenza)|3|<br>/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/CodiceAziendaSanitariaRiferimento|
|Codice Dipartimento Salute Mentale|5|KEY|Identifica il dipartimento di Salute Mentale interessato alla rilevazione.|AN|OBB| |3|<br>/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/CodiceDSM|
|Id Record|6|KEY|Codice identificativo unico del record |AN|OBB|Il valore deve essere generato come descritto nel par. 2.2.3.3.2 - Codice identificativo unico del record (ID\_REC) – modalità di alimentazione|88|<br>/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/id\_Rec|
|Codice Struttura|7|KEY|Codice della Struttura di erogazione Univocità della Chiave, in unione ai campi Codice ASL, codice DSM, Codice Sanitario Individuale. Appartenenza alla regione ed all’ASL di riferimento.|AN|OBB| |8|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/CodiceStruttura|
|ID Contatto|8|KEY|Identificativo univoco del Contatto.|A|OBB| |14|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/IDContatto|
|Progressivo Prestazioni per Contatto|9|KEY|Indica il numero progressivo di interventi di uno stesso contatto.|N|OBB|Valori compresi tra “1” a “99999”|5|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/ProgressivoPrestazioniContatto|
|Data Intervento|10|KEY|Indica la data in cui viene erogato l’intervento.|AN|OBB|Formato: AAAA-MM-GG|10|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/DataIntervento|
|Tipo Intervento|11| |Indica il tipo di intervento territoriale erogato dalla struttura.|N|OBB|Valori Ammessi:<br>01= visite psichiatriche <br>02=colloqui clinico<br>psicologici<br>03=colloqui <br>04=consulenze<br>05=accertamento medico-legale <br>06=valutazioni standardizzate<br>mediante test<br>07=psicoterapia individuale <br>08=psicoterapia di coppia<br>09= psicoterapia familiare <br>10=psicoterapia di gruppo<br>11=somministrazione di farmaci <br>12=colloqui con i familiari<br>13=interventi informativi e psicoeducativi rivolti alla famiglia (individuali)<br>14=interventi informativi e psicoeducativi rivolti alla famiglia(gruppo)<br>15=riunioni <br>16=interventi sulle abilità di base (individuali)<br>17= interventi sulle abilità di base (gruppo)<br>18 =interventi di<br>risocializzazione (individuali)<br>19=interventi di risocializzazione (gruppo) <br>20=soggiorni<br>21=interventi di tipo espressivo,pratico manuale e motorio (individuale) <br>22=interventi di tipo espressivo,pratico manuale e motorio (gruppo)<br>23=formazione lavorativa <br>24= interventi di supporto<br>25= interventi per problemi amministrativi e sociali<br>26=interventi di rete.|2|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/TipoIntervento|
|Modalità di erogazione dell intervento|12| |Indica se l’ intervento è programmato o meno. |N|OBB|Valori Ammessi:<br>1=programmato<br>2= non programmato|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/ModalitaErogazione|
|Sede Intervento|13| |Indica la sede in cui, o a partire dalla quale si eroga l’ intervento.|N|OBB|Valori Ammessi:<br>1=in sede<br>2=a domicilio<br>3= in altro luogo del territorio <br>4 =in altra struttura del DSM<br>5 =Ospedale Generale<br>6 =Carcere<br>7=videochiamata<br>8=telefonata significativa (durata superiore ai 15 minuti)|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/SedeIntervento|
|Operatore1|14| |Indica il profilo professionale di chi eroga l’intervento.|N|OBB|Valori Ammessi:<br>1= medico<br>2=psicologo<br>3=assistente sociale <br>4=infermiere<br>5= educatore professionale <br>6=tecnico della riabilitazione<br>7=OTA <br>8= altro.|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/Operatore1|
|Operatore2|15| |Indica il profilo professionale di chi eroga l’intervento.|N|FAC|Valori Ammessi:<br>1= medico<br>2=psicologo<br>3=assistente sociale <br>4=infermiere<br>5= educatore professionale <br>6=tecnico della riabilitazione<br>7=OTA <br>8= altro.|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/Operatore2|
|Operatore3|16| |Indica il profilo professionale di chi eroga l’intervento.|N|FAC|Valori Ammessi:<br>1= medico<br>2=psicologo<br>3=assistente sociale <br>4=infermiere<br>5= educatore professionale <br>6=tecnico della riabilitazione<br>7=OTA <br>8= altro.|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/Operatore3|
|Tipo Operazione|17| |Campo tecnico utilizzato per distinguere la trasmissione di informazioni nuove, modificate o eventualmente annullate.|A|OBB|Valori Ammessi:<br>I=Inserimento<br>C=Cancellazione<br>V=Variazione<br>NM: Non Movimentato (la componente Prestazione del record non viene inserita nel relativo xml a valle della validazione)|1|/TerritorialePrestazioniSanitarie/AziendaSanitariaRiferimento/DSM/Assistito/Struttura/Contatto/Prestazioni/TipoOperazione|

## ***3.3 Controlli di validazione del dato (business rules)***

Di seguito sono indicati i controlli da configurare sulla componente di Validation Engine e rispettivi error code associati riscontrabili sui dati di input per il flusso **PST**.

Gli errori sono solo di tipo scarti (mancato invio del record).

Al verificarsi anche di un solo errore di scarto, tra quelli descritti, il record oggetto di controllo sarà inserito tra i record scartati.

Business Rule non implementabili lato SDK:

- Storiche (Business Rule che effettuano controlli su dati già acquisiti/consolidati che non facciano parte del dato anagrafico)
- Transazionali (Business Rule che effettuano controlli su record, i quali rappresentano transazioni su cui andrebbe garantito l’ACID (Atomicità-Consistenza-Isolamento-Durabilità))
- Controllo d’integrità (cross flusso) (Business Rule che effettuano controlli sui record utilizzando informazioni estratte da record di altri flussi)

Di seguito le BR per il flusso in oggetto:

|**CAMPO**|**FLUSSO**|**CODICE ERRORE**|**ATTIVA/DISATTIVA**|**DESCRIZIONE ERRORE**|**DESCRIZIONE MDS**|**DESCRIZIONE ALGORITMO**|**TABELLA ANAGRAFICA**|**CAMPI DI COERENZA**|**SCARTI/ANOMALIE**|**TIPOLOGIA BR**|
| :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|Anno Riferimento|PST|3001|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Anno Riferimento|PST|3000|Attiva|Datatype errato in un campo obbligatorio|Valore compreso tra 1000 e 2999 |Non Definito|Non Definito|Non definito|Scarti|Basic|
|Anno Riferimento|PST|3009|Attiva|Il valore del campo Anno Riferimento e' diverso dal valore Anno Riferimento GAF|Il valore del campo Anno Riferimento e' diverso dall’Anno di Riferimento specificato al momento dell’upload sul GAF|Il valore del campo Anno Riferimento del tracciato di input e' diverso dall’Anno di Riferimento passato come parametro all'SDK|Non Definito|Non definito|Scarti|Basic|
|Codice Regione|PST|3011|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Codice Regione|PST|3012|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|I campo è valorizzato con valori diversi da: 10,20,30,41,42,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Codice Regione|PST|3305|Attiva|Il codice regione non coincide con il MITTENTE|Il parametro Codice Regione passato in input all'SDK non coincide con il campo Codice Regione|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Periodo Riferimento|PST|3021|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Periodo Riferimento|PST|3022|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|Il campo è valorizzato con valori diversi da S1, S2|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Periodo Riferimento|PST|3010|Attiva|Il valore del campo Periodo Riferimento e' diverso dal valore Periodo Riferimento GAF|Il valore del campo Periodo Riferimento e' diverso dal Periodo di Riferimento specificato al momento dell’upload sul GAF|Il valore del campo Periodo Riferimento del tracciato di input e' diverso dall’Anno di Riferimento passato come parametro all'SDK|Non Definito|Non definito|Scarti|Basic|
|Codice Azienda Sanitaria di Riferimento|PST|3031|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Codice Azienda Sanitaria di Riferimento|PST|3030|Attiva|Datatype errato in un campo obbligatorio|Il campo prevede 3 caratteri numerici.|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Codice Azienda Sanitaria di Riferimento|PST|3310|Attiva|Il codice della ASL di riferimento non esiste nella relativa anagrafica|Non Definito|Il valore del campo Codice Azienda Sanitaria di Riferimento del tracciato di input deve esistere all'interno della colonna COD\_ASL della query filtrata con le seguenti condizioni:<br>` `- num\_ann (QUERY) = Anno Riferimento  (TRACCIATO INPUT)<br>cod\_reg\_erg (QUERY) = Codice Regione (TRACCIATO INPUT)<br>cod\_asl (QUERY)= Codice Azienda Sanitaria di Riferimento (TRACCIATO INPUT).|ASL|Anno Riferimento; Codice Regione; Codice Azienda Sanitaria di Riferimento|Scarti|Anagrafica|
|Codice Dipartimento Salute Mentale|PST|3041|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Codice Dipartimento Salute Mentale|PST|3040|Attiva|Datatype errato in un campo obbligatorio|Il campo prevede 3 caratteri alfanumerici|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Codice Dipartimento Salute Mentale|PST|3315|Attiva|Il codice DSM non esiste nella relativa anagrafica|Non Definito|Il **Codice Dipartimento Salute Mentale** non esiste all'interno della colonna **COD\_DSM** della query  della query filtrata con le seguenti condizioni:<br>` `- num\_ann (QUERY) = Anno Riferimento  (TRACCIATO INPUT)<br>cod\_reg (QUERY) = Codice Regione (TRACCIATO INPUT)<br>` `cod\_asr\_rfr (QUERY)= Codice Azienda Sanitaria di Riferimento (TRACCIATO INPUT)|DSM|Anno Riferimento; Codice Regione; Codice Azienda Sanitaria di Riferimento|Scarti|Anagrafica|
|Id Record|PST|3051|Attiva|mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Id Record|PST|3050|Attiva|Lunghezza non conforme a quella attesa|La lunghezza del valore specificato non è conforme a quanto previsto (88 caratteri)|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Tipo operazione Prestazione|PST|3181.3|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Tipo operazione Prestazione|PST|3182.3|Attiva|Tipo operazione non appartenente al dominio atteso (I,V,C)|Non Definito|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Codice Struttura|PST|3191|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Codice Struttura|PST|3190|Attiva|Datatype errato in un campo obbligatorio|Il campo deve avere lunghezza compresa tra 6 e 8 caratteri.|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Codice Struttura|PST|3330|Attiva|La Struttura non esiste nella relativa anagrafica|Non Definito|Il valore del campo  **Codice Struttura** del tracciato di input non esiste all'interno della colonna **COD\_IST** della query filtrata con le seguenti condizioni: <br>ANN\_rif (QUERY)= Anno Riferimento  (TRACCIATO INPUT), <br>COD\_REG (QUERY) = Codice Regione (TRACCIATO INPUT),<br>COD\_ASR\_RFR (QUERY) = Codice Azienda Sanitaria di Riferimento (TRACCIATO INPUT)<br>COD\_IST (QUERY) = Codice Struttura (TRACCIATO INPUT)<br><br><br>Nota: flg\_ist\_psi (c'è ma non serve filtrare su questo) |Struttura|` `Anno Riferimento;Codice Regione; Codice Azienda Sanitaria di Riferimento|Scarti|Anagrafica|
|Id Contatto|PST|3211|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Id Contatto|PST|3210|Attiva|Datatype errato in un campo obbligatorio|Il campo deve avere lunghezza compresa tra 1 e 14 caratteri.|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Progressivo Prestazioni per Contatto|PST|3271|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Progressivo Prestazioni per Contatto|PST|3270|Attiva|Datatype errato in un campo obbligatorio|Il campo deve essere un numero compreso tra 1 e 99999|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Data Intervento|PST|3281|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Data Intervento|PST|3280|Attiva|Datatype errato in un campo facoltativo|La data deve essere indicata nel formato AAAA-MM-GG|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Data Intervento|PST|7341|Attiva|Data intervento non compresa nel periodo di riferimento|Non Definito|Data Intervento non compresa nel semestre di riferimento (periodo di riferimento). Es. con Periodo Riferimento = 'S1' e Anno riferimento = '2021' ci si aspetta che la Data Dimissione sia compresa nel primo primo semestre del 2021 |Non Definito|Non definito|Scarti|Basic|
|Tipo Intervento|PST|3291|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Tipo Intervento|PST|3292|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|Valori diversi dai quelli Ammessi:01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Modalità di Erogazione dell’intervento|PST|3321|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Modalità di Erogazione dell’intervento|PST|3222|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|Valori diversi dai quelli Ammessi:1,2 |Non Definito|Non Definito|Non definito|Scarti|Basic|
|Sede intervento|PST|3351|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Sede intervento|PST|3352|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|Valori diversi dai quelli Ammessi:1,2,3,4,5,6,7,8|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Operatore1|PST|3401|Attiva|Mancata valorizzazione di un campo obbligatorio|Non Definito|il campo deve essere valorizzato e diverso da blanks|Non Definito|Non definito|Scarti|Basic|
|Operatore1|PST|3402|Attiva|Non appartenenza alla tabella di riferimento per un campo obbligatorio|Valori diversi dai quelli Ammessi:1,2,3,4,5,6,7,8|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Operatore2-3|PST|3403|Attiva|Non appartenenza alla tabella di riferimento per un campo facoltativo|Valori diversi dai quelli Ammessi:1,2,3,4,5,6,7,8|Non Definito|Non Definito|Non definito|Scarti|Basic|
|Data Intervento|PST|3948|Attiva|Data intervento non compresa nel Periodo Riferimento GAF|La “data intervento” non è compresa nel periodo di Periodo di Riferimento specificato al momento dell’upload sul GAF.|La data intervento non compresa nel semestre di riferimento (periodo di riferimento). Es. con Periodo Riferimento = 'S1' e Anno riferimento = '2021' ci si aspetta che ladata intervento  sia compresa nel primo primo semestre del 2021 |Non Definito|Non definito|Scarti|Basic|

## ***3.4 Accesso alle anagrafiche***

I controlli applicativi saranno implementati a partire dall’acquisizione dei seguenti dati anagrafici disponibili in ambito MdS e retrievati con servizi ad hoc (Service Layer mediante PDI):

- DSM
- ASL
- Struttura
- Codice Alpha2
- ICD-09-CM
- Capitolo di patologie psichiatriche

All’interno del file **censimento\_anagrafiche** sono presenti per ogni anagrafica il dettaglio implementativo (Query SQL) e la tabella fisica da cui alimentare l’anagrafica.

Il dato anagrafico sarà presente sottoforma di tabella composta da tre colonne:

- Valore (in cui è riportato il dato, nel caso di più valori, sarà usato il carattere # come separatore)

- Data inizio validità (rappresenta la data di inizio validità del campo Valore)
 - Formato: AAAA-MM-DD
 - Notazione inizio validità permanente: **1900-01-01**


- Data Fine Validità (rappresenta la data di fine validità del campo Valore)
  - Formato: AAAA-MM-DD
  - Notazione fine validità permanente: **9999-12-31**

Affinchè le Business Rule che usano il dato anagrafico per effettuare controlli siano correttamente funzionanti, occorre sempre controllare che la data di competenza del record su cui si effettua il controllo (la quale varia in base alla componente), sia compresa tra le data di validità.  

Di seguito viene mostrato un caso limite di anagrafica in cui sono presenti delle sovrapposizioni temporali e contraddizioni di validità permanente/specifico range:


|ID|VALUE|VALID\_FROM|VALID\_TO|
| - | - | - | - |
|1|VALORE 1|1900-01-01|9999-12-31|
|2|VALORE 1|2015-01-01|2015-12-31|
|3|VALORE 1|2018-01-01|2023-12-31|
|4|VALORE 1|2022-01-01|2024-12-31|


Diremo che il dato presente sul tracciato di input è valido se e solo se:

∃ VALUE\_R = VALUE\_A “tale che” VALID\_FROM <= **DATA\_COMPETENZA** <= VALID\_TO

(Esiste almeno un valore compreso tra le date di validità)

Dove:

- VALUE\_R rappresenta i campi del tracciato di input coinvolti nei controlli della specifica BR

- VALUE\_A rappresenta i campi dell’anagrafica coinvolti nei controlli della specifica BR

- VALID\_FROM/VALID\_TO rappresentano le colonne dell’anagrafica

- DATA\_COMPETENZA data da utilizzare per il filtraggio del dato anagrafico


## ***3.5 Alimentazione MdS***
### **3.5.1 Invio Flussi**

A valle delle verifiche effettuate dal Validation Engine, qualora il caricamento sia stato effettuato con il parametro Tipo Elaborazione impostato a P, verranno inviati verso MdS tutti i record corretti secondo le regole di validazione impostate.

Verrà richiamata la procedura invioFlussi di GAF WS (tramite PDI) alla quale verranno passati in input i parametri così come riportati nella seguente tabella:



|NOME PARAMETRO|**VALORIZZAZIONE**|
| :- | :- |
|ANNO RIFERIMENTO|Parametro ANNO RIFERIMENTO in input a SDK|
|PERIODO RIFERIMENTO|Parametro PERIODO RIFERIMENTO in input a SDK |
|CATEGORIA FLUSSI|TER|
|NOME FLUSSO|PST|
|NOME FILE|Parametro popolato dall’SDK in fase di invio flusso con il nome file generato dal Validation Engine in fase di produzione file.|

### **3.5.2 Flussi di Output per alimentazione MdS**

I flussi generati dall’SDK saranno presenti sotto la cartella “/sdk/xml\_output” e dovranno essere salvati e inviati verso MdS rispettando la seguente nomenclatura:

**SDK\_TER\_PST\_{Periodo di riferimento}\_{ID\_RUN}.xml**

Dove :

- Periodo di Riferimento: composto dal semestre di riferimento (Es. S1).
- ID\_RUN rappresenta l’identificativo univoco


### **3.5.3 Gestione Risposta Mds**

A valle della presa in carico del dato da parte di MdS, SDK riceverà una response contenente le seguenti informazioni:

1. **codiceFornitura**: stringa numerica indicante l’identificativo univoco della fornitura inviata al GAF
1. **errorCode**: stringa alfanumerica di 256 caratteri rappresentante il codice identificativo dell’errore eventualmente riscontrato
1. **errorText**: stringa alfanumerica di 256 caratteri rappresentante il descrittivo dell’errore eventualmente riscontrato
1. Insieme delle seguenti triple, una per ogni file inviato:

  a. **idUpload**: stringa numerica indicante l’identificativo univoco del singolo file ricevuto ed accettato dal MdS, e corrispondente al file inviato con la denominazione riportata nell’elemento “nomeFile” che segue

  b. **esito**: stringa alfanumerica di 4 caratteri rappresentante l’esito dell’operazione (Vedi tabella sotto)

  c. **nomeFile**: stringa alfanumerica di 256 caratteri rappresentante il nome dei file inviati.

Di seguito la tabella di riepilogo dei codici degli esiti possibili dell’invio del file:

|**ESITO**|**DESCRIZIONE**|
| :- | :- |
|AE00|Errore di autenticazione al servizio|
|IF00|Operazione completata con successo|
|IF01|Incongruenza tra CF utente firmatario e cf utente inviante|
|IF02|Firma digitale non valida|
|IF03|Firma digitale scaduta|
|IF04|Estensione non ammessa|
|IF05|Utente non abilitato all’invio per la Categoria Flusso indicata|
|IF06|Utente non abilitato all’invio per il Flusso indicata|
|IF07|Periodo non congurente con la Categoria Flusso indicata|
|IF08|Il file inviato è vuoto|
|IF09|Errore interno al servizio nella ricezione del file|
|IF10|Il numero di allegati segnalati nel body non corrisponde al numero di allegati riscontrati nella request|
|IF11|Il nome dell’allegato riportato nel body non è presente tra gli allegati della request (content-id)|
|IF12|Presenza di nomi file duplicati|
|IF13|Errore interno al servizio nella ricezione del file|
|IF14|Errore interno al servizio nella ricezione del file|
|IF15|Errore interno al servizio nella ricezione del file|
|IF99|Errore generico dell’operation|

Copia dei file inviati verso MdS il cui esito è positivo (ovvero risposta della procedura Invio Flussi con IF00) saranno trasferiti e memorizzati in una cartella ad hoc di SDK (es. sdk/sent) rispettando la stessa naming descritta per i flussi di output.
## ***3.6 Scarti di processamento***

In una cartella dedicata (es. /sdk/esiti) verrà creato un file json contenente il dettaglio degli scarti riscontrati ad ogni esecuzione del processo SDK.

Il naming del file sarà:  ESITO\_{ID\_RUN}.json

Dove:

- ID\_RUN rappresenta l’identificativo univoco dell’elaborazione

Di seguito il tracciato del record da produrre.


|**CAMPO**|**DESCRIZIONE**|
| :- | :- |
|NUMERO RECORD|Numero del record del flusso input|
|RECORD PROCESSATO|Campi esterni rispetto al tracciato, che sono necessari per la validazione dello stesso.</p><p> Record su cui si è verificato uno scarto, riportato in maniera strutturata (nome\_campo-valore).|
|LISTA ESITI|<p>Lista di oggetti contenente l’esito di validazione per ciascun campo:</p><p>- Campo: nome campo su cui si è verificato uno scarto</p><p>- Valore Scarto: valore del campo su cui si è verificato uno scarto</p><p>- Valore Esito: esito di validazione del particolare campo</p><p>- Errori Validazione: contiene i campi Codice (della Business Rule) e Descrizione (della Business Rule)</p>|

## ***3.7 Informazioni dell’esecuzione***

In una cartella dedicata (es. /sdk/run) verrà creato un file contenente il dettaglio degli esiti riscontrati ad ogni esecuzione del processo SDK. Verrà prodotto un file di log per ogni giorno di elaborazione.

Il naming del file sarà:  

{ID\_RUN}.json

Dove:

- ID\_RUN rappresenta l’identificativo univoco dell’elaborazione

Di seguito il tracciato del record da produrre.


|**CAMPO**|**DESCRIZIONE**|
| :- | :- |
|ID RUN (chiave)|Identificativo univoco di ogni esecuzione del SDK|
|ID\_CLIENT|Identificativo Univoco della trasazione sorgente che richiede processamento lato SDK|
|ID UPLOAD (chiave)|Identificativo di caricamento fornito da MdS|
|TIPO ELABORAZIONE|F (full)/R (per singolo record) - Impostato di default a F|
|MODALITA’ OPERATIVA|P (=produzione) /T (=test)|
|DATA INIZIO ESECUZIONE|Timestamp dell’ inizio del processamento|
|DATA FINE ESECUZIONE|Timestamp di completamento del processamento|
|STATO ESECUZIONE |<p>Esito dell’esecuzione dell’ SDK. </p><p>Possibili valori: </p><p>- IN ELABORAZIONE: Sdk in esecuzione;</p><p>- ELABORATA: Esecuzione completata con successo;</p><p>- KO: Esecuzione fallita: </p><p>- KO SPECIFICO: Esecuzione fallita per una fase/componente più rilevante della soluzione (es. ko\_gestione\_file, ko\_gestione\_validazione, ko\_invio\_ministero, etc.); </p><p>- KO GENERICO: un errore generico non controllato.</p>|
|FILE ASSOCIATI RUN|nome del file di input elaborato dall’SDK|
|NOME FLUSSO|valore fisso che identifica lo specifico SDK in termini di categoria e nome flusso|
|NUMERO RECORD |Numero di record del flusso input|
|NUMERO RECORD ACCETTATI|Numero validi|
|NUMERO RECORD SCARTATI|Numero scarti|
|VERSION|Versione del SDK (Access Layer e Validation Engine)|
|TIMESTAMP CREAZIONE|Timestamp creazione della info run|
|API (\*DPM)|Rappresenta L’API utilizzata per il flusso DPM (non valorizzata per gli altri flussi)|
|IDENTIFICATIVO SOGGETTO ALIMENTANTE (\*DPM)|Chiave flusso DPM (non valorizzata per gli altri flussi)|
|TIPO ATTO (\*DPM)|Chiave flusso DPM (non valorizzata per gli altri flussi)|
|NUMERO ATTO (\*DPM)|Chiave flusso DPM (non valorizzata per gli altri flussi)|
|TIPO ESITO MDS (\*DPM)|Esito della response dell’API 2 (non valorizzata per gli altri flussi) |
|DATA RICEVUTA MDS (\*DPM)|Data della response dell’API 3 (non valorizzata per gli altri flussi)|
|CODICE REGIONE|Codice Regione del Mittente|
|ANNO RIFERIMENTO|Anno cui si riferiscono i dati del flusso|
|PERIODO DI RIFERIMENTO|Periodo di riferimento dei dati del flusso (es. 13)|
|DESCRIZIONE STATO ESECUZIONE |Specifica il messaggio breve dell’errore, maggiori informazioni saranno presenti all’interno del log applicativo|
|NOME FILE OUTPUT MDS|Nome dei file di output inviati verso MdS|
|ESITO ACQUISIZIONE FLUSSO|Codice dell’esito del processo di acquisizione del flusso su MdS. Tale campo riflette la proprietà invioFlussiReturn/listaEsitiUpload/item/esito della response della procedura **invioFlussi**. (Es IF00)|
|CODICE ERRORE INVIO FLUSSI|Codice d’errore della procedura di invio. Tale campo riflette la proprietà InvioFlussiReturn/errorCode della response della procedura **invioFlussi**|
|TESTO ERRORE INVIO FLUSSI|Descrizione codice d’errore della procedura. Tale campo riflette la proprietà InvioFlussiReturn/ errorText della response della procedura **invioFlussi**|

Inoltre, a supporto dell’entità che rappresenta lo stato dell’esecuzione, sotto la cartella /sdk/log, saranno presenti anche i file di log applicativi (aggregati giornalmente) non strutturati, nei quali saranno presenti informazioni aggiuntive, ad esempio lo StackTrace (in caso di errori).

Il naming del file, se non modificata la politica di rolling (impostazioni), è il seguente:

**SDK\_TER-PST.log**

## mantainer:
 Accenture SpA until January 2026
