//
// Questo file � stato generato dall'Eclipse Implementation of JAXB, v3.0.0 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.06.01 alle 11:46:51 AM CEST 
//


package it.mds.sdk.flusso.sism.territoriale.tracciato.bean.output.prestazionisanitarie;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoOperazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <pre>
 * &lt;simpleType name="TipoOperazione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="I"/&gt;
 *     &lt;enumeration value="C"/&gt;
 *     &lt;enumeration value="V"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoOperazione")
@XmlEnum
public enum TipoOperazione {

    I,
    C,
    V;

    public String value() {
        return name();
    }

    public static TipoOperazione fromValue(String v) {
        return valueOf(v);
    }

}
