//
// Questo file � stato generato dall'Eclipse Implementation of JAXB, v3.0.0 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.06.01 alle 11:46:51 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie;

import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AnnoRiferimento" type="{}AnnoRiferimento"/&gt;
 *         &lt;element name="PeriodoRiferimento" type="{}PeriodoRiferimento"/&gt;
 *         &lt;element name="CodiceRegione" type="{}CodiceRegione"/&gt;
 *         &lt;element name="AziendaSanitariaRiferimento" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="CodiceAziendaSanitariaRiferimento" type="{}CodiceAziendaSanitariaRiferimento"/&gt;
 *                   &lt;element name="DSM" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="CodiceDSM" type="{}CodiceDSM"/&gt;
 *                             &lt;element name="Assistito" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Id_Rec" type="{}Id_Rec"/&gt;
 *                                       &lt;element name="Struttura" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="CodiceStruttura" type="{}CodiceStruttura"/&gt;
 *                                                 &lt;element name="Contatto" maxOccurs="unbounded"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="IDContatto" type="{}IDContatto"/&gt;
 *                                                           &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
 *                                                             &lt;complexType&gt;
 *                                                               &lt;complexContent&gt;
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                                   &lt;sequence&gt;
 *                                                                     &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
 *                                                                     &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                                                                     &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
 *                                                                     &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
 *                                                                     &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
 *                                                                     &lt;element name="Operatore1" type="{}Operatore"/&gt;
 *                                                                     &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
 *                                                                     &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
 *                                                                     &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
 *                                                                   &lt;/sequence&gt;
 *                                                                 &lt;/restriction&gt;
 *                                                               &lt;/complexContent&gt;
 *                                                             &lt;/complexType&gt;
 *                                                           &lt;/element&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "annoRiferimento",
    "periodoRiferimento",
    "codiceRegione",
    "aziendaSanitariaRiferimento"
})
@XmlRootElement(name = "TerritorialePrestazioniSanitarie")
public class TerritorialePrestazioniSanitarie {

    @XmlElement(name = "AnnoRiferimento", required = true)
    protected String annoRiferimento;
    @XmlElement(name = "PeriodoRiferimento", required = true)
    @XmlSchemaType(name = "string")
    protected PeriodoRiferimento periodoRiferimento;
    @XmlElement(name = "CodiceRegione", required = true)
    protected String codiceRegione;
    @XmlElement(name = "AziendaSanitariaRiferimento", required = true)
    protected List<AziendaSanitariaRiferimento> aziendaSanitariaRiferimento;

    /**
     * Recupera il valore della propriet� annoRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Imposta il valore della propriet� annoRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
    }

    /**
     * Recupera il valore della propriet� periodoRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link PeriodoRiferimento }
     *     
     */
    public PeriodoRiferimento getPeriodoRiferimento() {
        return periodoRiferimento;
    }

    /**
     * Imposta il valore della propriet� periodoRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodoRiferimento }
     *     
     */
    public void setPeriodoRiferimento(PeriodoRiferimento value) {
        this.periodoRiferimento = value;
    }

    /**
     * Recupera il valore della propriet� codiceRegione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegione() {
        return codiceRegione;
    }

    /**
     * Imposta il valore della propriet� codiceRegione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegione(String value) {
        this.codiceRegione = value;
    }

    /**
     * Gets the value of the aziendaSanitariaRiferimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the aziendaSanitariaRiferimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAziendaSanitariaRiferimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AziendaSanitariaRiferimento }
     * 
     * 
     */
    public List<AziendaSanitariaRiferimento> getAziendaSanitariaRiferimento() {
        if (aziendaSanitariaRiferimento == null) {
            aziendaSanitariaRiferimento = new ArrayList<AziendaSanitariaRiferimento>();
        }
        return this.aziendaSanitariaRiferimento;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="CodiceAziendaSanitariaRiferimento" type="{}CodiceAziendaSanitariaRiferimento"/&gt;
     *         &lt;element name="DSM" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="CodiceDSM" type="{}CodiceDSM"/&gt;
     *                   &lt;element name="Assistito" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Id_Rec" type="{}Id_Rec"/&gt;
     *                             &lt;element name="Struttura" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="CodiceStruttura" type="{}CodiceStruttura"/&gt;
     *                                       &lt;element name="Contatto" maxOccurs="unbounded"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="IDContatto" type="{}IDContatto"/&gt;
     *                                                 &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
     *                                                   &lt;complexType&gt;
     *                                                     &lt;complexContent&gt;
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                                         &lt;sequence&gt;
     *                                                           &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
     *                                                           &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                                                           &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
     *                                                           &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
     *                                                           &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
     *                                                           &lt;element name="Operatore1" type="{}Operatore"/&gt;
     *                                                           &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
     *                                                           &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
     *                                                           &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
     *                                                         &lt;/sequence&gt;
     *                                                       &lt;/restriction&gt;
     *                                                     &lt;/complexContent&gt;
     *                                                   &lt;/complexType&gt;
     *                                                 &lt;/element&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codiceAziendaSanitariaRiferimento",
        "dsm"
    })
    public static class AziendaSanitariaRiferimento {

        @XmlElement(name = "CodiceAziendaSanitariaRiferimento", required = true)
        protected String codiceAziendaSanitariaRiferimento;
        @XmlElement(name = "DSM", required = true)
        protected List<DSM> dsm;

        /**
         * Recupera il valore della propriet� codiceAziendaSanitariaRiferimento.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceAziendaSanitariaRiferimento() {
            return codiceAziendaSanitariaRiferimento;
        }

        /**
         * Imposta il valore della propriet� codiceAziendaSanitariaRiferimento.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceAziendaSanitariaRiferimento(String value) {
            this.codiceAziendaSanitariaRiferimento = value;
        }

        /**
         * Gets the value of the dsm property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the dsm property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDSM().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DSM }
         * 
         * 
         */
        public List<DSM> getDSM() {
            if (dsm == null) {
                dsm = new ArrayList<DSM>();
            }
            return this.dsm;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="CodiceDSM" type="{}CodiceDSM"/&gt;
         *         &lt;element name="Assistito" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Id_Rec" type="{}Id_Rec"/&gt;
         *                   &lt;element name="Struttura" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="CodiceStruttura" type="{}CodiceStruttura"/&gt;
         *                             &lt;element name="Contatto" maxOccurs="unbounded"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence&gt;
         *                                       &lt;element name="IDContatto" type="{}IDContatto"/&gt;
         *                                       &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
         *                                         &lt;complexType&gt;
         *                                           &lt;complexContent&gt;
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                               &lt;sequence&gt;
         *                                                 &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
         *                                                 &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *                                                 &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
         *                                                 &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
         *                                                 &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
         *                                                 &lt;element name="Operatore1" type="{}Operatore"/&gt;
         *                                                 &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
         *                                                 &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
         *                                                 &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
         *                                               &lt;/sequence&gt;
         *                                             &lt;/restriction&gt;
         *                                           &lt;/complexContent&gt;
         *                                         &lt;/complexType&gt;
         *                                       &lt;/element&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "codiceDSM",
            "assistito"
        })
        public static class DSM {

            @XmlElement(name = "CodiceDSM", required = true)
            protected String codiceDSM;
            @XmlElement(name = "Assistito", required = true)
            protected List<Assistito> assistito;

            /**
             * Recupera il valore della propriet� codiceDSM.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodiceDSM() {
                return codiceDSM;
            }

            /**
             * Imposta il valore della propriet� codiceDSM.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodiceDSM(String value) {
                this.codiceDSM = value;
            }

            /**
             * Gets the value of the assistito property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the Jakarta XML Binding object.
             * This is why there is not a <CODE>set</CODE> method for the assistito property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAssistito().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Assistito }
             * 
             * 
             */
            public List<Assistito> getAssistito() {
                if (assistito == null) {
                    assistito = new ArrayList<Assistito>();
                }
                return this.assistito;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="Id_Rec" type="{}Id_Rec"/&gt;
             *         &lt;element name="Struttura" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="CodiceStruttura" type="{}CodiceStruttura"/&gt;
             *                   &lt;element name="Contatto" maxOccurs="unbounded"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence&gt;
             *                             &lt;element name="IDContatto" type="{}IDContatto"/&gt;
             *                             &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
             *                               &lt;complexType&gt;
             *                                 &lt;complexContent&gt;
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                                     &lt;sequence&gt;
             *                                       &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
             *                                       &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
             *                                       &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
             *                                       &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
             *                                       &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
             *                                       &lt;element name="Operatore1" type="{}Operatore"/&gt;
             *                                       &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
             *                                       &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
             *                                       &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
             *                                     &lt;/sequence&gt;
             *                                   &lt;/restriction&gt;
             *                                 &lt;/complexContent&gt;
             *                               &lt;/complexType&gt;
             *                             &lt;/element&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "idRec",
                "struttura"
            })
            public static class Assistito {

                @XmlElement(name = "Id_Rec", required = true)
                protected String idRec;
                @XmlElement(name = "Struttura", required = true)
                protected List<Struttura> struttura;

                /**
                 * Recupera il valore della propriet� idRec.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getIdRec() {
                    return idRec;
                }

                /**
                 * Imposta il valore della propriet� idRec.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setIdRec(String value) {
                    this.idRec = value;
                }

                /**
                 * Gets the value of the struttura property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the Jakarta XML Binding object.
                 * This is why there is not a <CODE>set</CODE> method for the struttura property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getStruttura().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Struttura }
                 * 
                 * 
                 */
                public List<Struttura> getStruttura() {
                    if (struttura == null) {
                        struttura = new ArrayList<Struttura>();
                    }
                    return this.struttura;
                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="CodiceStruttura" type="{}CodiceStruttura"/&gt;
                 *         &lt;element name="Contatto" maxOccurs="unbounded"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence&gt;
                 *                   &lt;element name="IDContatto" type="{}IDContatto"/&gt;
                 *                   &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
                 *                     &lt;complexType&gt;
                 *                       &lt;complexContent&gt;
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                           &lt;sequence&gt;
                 *                             &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
                 *                             &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
                 *                             &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
                 *                             &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
                 *                             &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
                 *                             &lt;element name="Operatore1" type="{}Operatore"/&gt;
                 *                             &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
                 *                             &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
                 *                             &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
                 *                           &lt;/sequence&gt;
                 *                         &lt;/restriction&gt;
                 *                       &lt;/complexContent&gt;
                 *                     &lt;/complexType&gt;
                 *                   &lt;/element&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "codiceStruttura",
                    "contatto"
                })
                public static class Struttura {

                    @XmlElement(name = "CodiceStruttura", required = true)
                    protected String codiceStruttura;
                    @XmlElement(name = "Contatto", required = true)
                    protected List<Contatto> contatto;

                    /**
                     * Recupera il valore della propriet� codiceStruttura.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodiceStruttura() {
                        return codiceStruttura;
                    }

                    /**
                     * Imposta il valore della propriet� codiceStruttura.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodiceStruttura(String value) {
                        this.codiceStruttura = value;
                    }

                    /**
                     * Gets the value of the contatto property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the Jakarta XML Binding object.
                     * This is why there is not a <CODE>set</CODE> method for the contatto property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getContatto().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Contatto }
                     * 
                     * 
                     */
                    public List<Contatto> getContatto() {
                        if (contatto == null) {
                            contatto = new ArrayList<Contatto>();
                        }
                        return this.contatto;
                    }


                    /**
                     * <p>Classe Java per anonymous complex type.
                     * 
                     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence&gt;
                     *         &lt;element name="IDContatto" type="{}IDContatto"/&gt;
                     *         &lt;element name="Prestazioni" maxOccurs="unbounded"&gt;
                     *           &lt;complexType&gt;
                     *             &lt;complexContent&gt;
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *                 &lt;sequence&gt;
                     *                   &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
                     *                   &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
                     *                   &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
                     *                   &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
                     *                   &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
                     *                   &lt;element name="Operatore1" type="{}Operatore"/&gt;
                     *                   &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
                     *                   &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
                     *                   &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
                     *                 &lt;/sequence&gt;
                     *               &lt;/restriction&gt;
                     *             &lt;/complexContent&gt;
                     *           &lt;/complexType&gt;
                     *         &lt;/element&gt;
                     *       &lt;/sequence&gt;
                     *     &lt;/restriction&gt;
                     *   &lt;/complexContent&gt;
                     * &lt;/complexType&gt;
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "idContatto",
                        "prestazioni"
                    })
                    public static class Contatto {

                        @XmlElement(name = "IDContatto")
                        protected long idContatto;
                        @XmlElement(name = "Prestazioni", required = true)
                        protected List<Prestazioni> prestazioni;

                        /**
                         * Recupera il valore della propriet� idContatto.
                         * 
                         */
                        public long getIDContatto() {
                            return idContatto;
                        }

                        /**
                         * Imposta il valore della propriet� idContatto.
                         * 
                         */
                        public void setIDContatto(long value) {
                            this.idContatto = value;
                        }

                        /**
                         * Gets the value of the prestazioni property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the Jakarta XML Binding object.
                         * This is why there is not a <CODE>set</CODE> method for the prestazioni property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getPrestazioni().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Prestazioni }
                         * 
                         * 
                         */
                        public List<Prestazioni> getPrestazioni() {
                            if (prestazioni == null) {
                                prestazioni = new ArrayList<Prestazioni>();
                            }
                            return this.prestazioni;
                        }


                        /**
                         * <p>Classe Java per anonymous complex type.
                         * 
                         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                         * 
                         * <pre>
                         * &lt;complexType&gt;
                         *   &lt;complexContent&gt;
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                         *       &lt;sequence&gt;
                         *         &lt;element name="ProgressivoPrestazioniContatto" type="{}ProgressivoPrestazioniContatto"/&gt;
                         *         &lt;element name="DataIntervento" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
                         *         &lt;element name="TipoIntervento" type="{}TipoIntervento"/&gt;
                         *         &lt;element name="ModalitaErogazione" type="{}ModalitaErogazione"/&gt;
                         *         &lt;element name="SedeIntervento" type="{}SedeIntervento"/&gt;
                         *         &lt;element name="Operatore1" type="{}Operatore"/&gt;
                         *         &lt;element name="Operatore2" type="{}Operatore" minOccurs="0"/&gt;
                         *         &lt;element name="Operatore3" type="{}Operatore" minOccurs="0"/&gt;
                         *         &lt;element name="TipoOperazione" type="{}TipoOperazione"/&gt;
                         *       &lt;/sequence&gt;
                         *     &lt;/restriction&gt;
                         *   &lt;/complexContent&gt;
                         * &lt;/complexType&gt;
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "progressivoPrestazioniContatto",
                            "dataIntervento",
                            "tipoIntervento",
                            "modalitaErogazione",
                            "sedeIntervento",
                            "operatore1",
                            "operatore2",
                            "operatore3",
                            "tipoOperazione"
                        })
                        public static class Prestazioni {

                            @XmlElement(name = "ProgressivoPrestazioniContatto")
                            protected int progressivoPrestazioniContatto;
                            @XmlElement(name = "DataIntervento", required = true)
                            @XmlSchemaType(name = "date")
                            protected XMLGregorianCalendar dataIntervento;
                            @XmlElement(name = "TipoIntervento", required = true)
                            protected String tipoIntervento;
                            @XmlElement(name = "ModalitaErogazione", required = true)
                            protected String modalitaErogazione;
                            @XmlElement(name = "SedeIntervento", required = true)
                            protected String sedeIntervento;
                            @XmlElement(name = "Operatore1", required = true)
                            protected String operatore1;
                            @XmlElement(name = "Operatore2")
                            protected String operatore2;
                            @XmlElement(name = "Operatore3")
                            protected String operatore3;
                            @XmlElement(name = "TipoOperazione", required = true, defaultValue = "I")
                            @XmlSchemaType(name = "string")
                            protected TipoOperazione tipoOperazione;

                            /**
                             * Recupera il valore della propriet� progressivoPrestazioniContatto.
                             * 
                             */
                            public int getProgressivoPrestazioniContatto() {
                                return progressivoPrestazioniContatto;
                            }

                            /**
                             * Imposta il valore della propriet� progressivoPrestazioniContatto.
                             * 
                             */
                            public void setProgressivoPrestazioniContatto(int value) {
                                this.progressivoPrestazioniContatto = value;
                            }

                            /**
                             * Recupera il valore della propriet� dataIntervento.
                             * 
                             * @return
                             *     possible object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public XMLGregorianCalendar getDataIntervento() {
                                return dataIntervento;
                            }

                            /**
                             * Imposta il valore della propriet� dataIntervento.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link XMLGregorianCalendar }
                             *     
                             */
                            public void setDataIntervento(XMLGregorianCalendar value) {
                                this.dataIntervento = value;
                            }

                            /**
                             * Recupera il valore della propriet� tipoIntervento.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getTipoIntervento() {
                                return tipoIntervento;
                            }

                            /**
                             * Imposta il valore della propriet� tipoIntervento.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setTipoIntervento(String value) {
                                this.tipoIntervento = value;
                            }

                            /**
                             * Recupera il valore della propriet� modalitaErogazione.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getModalitaErogazione() {
                                return modalitaErogazione;
                            }

                            /**
                             * Imposta il valore della propriet� modalitaErogazione.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setModalitaErogazione(String value) {
                                this.modalitaErogazione = value;
                            }

                            /**
                             * Recupera il valore della propriet� sedeIntervento.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getSedeIntervento() {
                                return sedeIntervento;
                            }

                            /**
                             * Imposta il valore della propriet� sedeIntervento.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setSedeIntervento(String value) {
                                this.sedeIntervento = value;
                            }

                            /**
                             * Recupera il valore della propriet� operatore1.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getOperatore1() {
                                return operatore1;
                            }

                            /**
                             * Imposta il valore della propriet� operatore1.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setOperatore1(String value) {
                                this.operatore1 = value;
                            }

                            /**
                             * Recupera il valore della propriet� operatore2.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getOperatore2() {
                                return operatore2;
                            }

                            /**
                             * Imposta il valore della propriet� operatore2.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setOperatore2(String value) {
                                this.operatore2 = value;
                            }

                            /**
                             * Recupera il valore della propriet� operatore3.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getOperatore3() {
                                return operatore3;
                            }

                            /**
                             * Imposta il valore della propriet� operatore3.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setOperatore3(String value) {
                                this.operatore3 = value;
                            }

                            /**
                             * Recupera il valore della propriet� tipoOperazione.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TipoOperazione }
                             *     
                             */
                            public TipoOperazione getTipoOperazione() {
                                return tipoOperazione;
                            }

                            /**
                             * Imposta il valore della propriet� tipoOperazione.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TipoOperazione }
                             *     
                             */
                            public void setTipoOperazione(TipoOperazione value) {
                                this.tipoOperazione = value;
                            }

                        }

                    }

                }

            }

        }

    }

}
