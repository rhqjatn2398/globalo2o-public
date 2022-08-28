package com.example.globalO2O.sor.DAO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announ {
    private int annId;

    private String annNo;
    private String annRuteNm;
    private String imxptrNm;
    private String shipngPrtNm;
    private String landngPrtNm;
    private String codPrtNm;
    private String tsYn;
    private String contnOwnSeNm;
    private String contnCndNm;
    private String contnStdStndrdNm;
    private String frghtNm;
    private String tnspotSeNm;

    private String cychgRm; //운임비고
    private String cychgEtcDc; //운임기타설명

    private int cychgOf;    private String cychgOfCurCd;

    private int cychgBaf;    private String cychgBafCurCd;

    private int cychgCaf;    private String cychgCafCurCd;

    private int cychgLss;    private String cychgLssCurCd;

    private int cychgEbs;    private String cychgEbsCurCd;

    private int cychgOthc;    private String cychgOthcCurCd;

    private int cychgDthc;    private String cychgDthcCurCd;

    private int cychgDf;    private String cychgDfCurCd;

    private int cychgDo;    private String cychgDoCurCd;

    private int cychgWaf;    private String cychgWafCurCd;

    private double cychgSc;    private String cychgScCurCd;

    private int cychgEtc;    private String cychgEtcCurCd;

}
