/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.sism.territoriale.parser.regole;

import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDtoSismTerritoriale extends RecordDtoGenerico {

    //ANN_RIF~COD_PER~COD_REG~COD_ASR_RFR~COD_DSM~ID_REC_KEY~COD_STR~ID_CNT~PRG_PST_CNT~DAT_ITV~TYP_ITV~MDL_ERG~SDE_ITV~OPT_1~OPT_2~OPT_3~TIP_TRS

    @CsvBindByPosition(position = 0)
    private String annoRiferimento; //ANN_RIF

    @CsvBindByPosition(position = 1)
    private String periodoRiferimento; //COD_PER

    @CsvBindByPosition(position = 2)
    private String codiceRegione; //COD_REG

    @CsvBindByPosition(position = 3)
    private String codiceAziendaSanitariaRiferimento; //COD_ASR_RFR

    @CsvBindByPosition(position = 4)
    private String codiceDipartimentoSaluteMentale;//COD_DSM

    @CsvBindByPosition(position = 5)
    private String idRecord;//ID_REC_KEYX

    @CsvBindByPosition(position = 6)
    private String codiceStruttura;//COD_STR

    @CsvBindByPosition(position = 7)
    private Long idContatto; //ID_CNT

    @CsvBindByPosition(position = 8)
    private Integer progressivoPrestazioniContatto; //PRG_PST_CNT

    @CsvBindByPosition(position = 9)
    private String dataIntervento; //DAT_ITV

    @CsvBindByPosition(position = 10)
    private String tipoIntervento; //TYP_ITV

    @CsvBindByPosition(position = 11)
    private String modalitaErogazioneIntervento; //MDL_ERG

    @CsvBindByPosition(position = 12)
    private String sedeIntervento;//SDE_ITV

    @CsvBindByPosition(position = 13)
    private String operatore1; //OPT_1

    @CsvBindByPosition(position = 14)
    private String operatore2; //OPT_2

    @CsvBindByPosition(position = 15)
    private String operatore3; //OPT_3

    @CsvBindByPosition(position = 16)
    private String tipoOperazionePrestazione; //TYP_OPR_PRES

}
