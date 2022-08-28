package com.example.globalO2O.sor.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResultDto {
    private int cychgOf;            //운임OF
    private String cychgOfCurCd;    //운임OF통화코드
    private int cychgBaf;           //운임BAF
    private String cychgBafCurCd;   //운임BAF통화코드
    private int cychgCaf;           //운임CAF
    private String cychgCafCurCd;   //운임CAF통화코드
    private int cychgLss;           //운임LSS
    private String cychgLssCurCd;   //운임LSS통화코드
    private int cychgEbs;           //운임EBS
    private String cychgEbsCurCd;   //운임EBS통화코드
    private int cychgOthc;          //운임OTHC
    private String cychgOthcCurCd;  //운임OTHC통화코드
    private int cychgDthc;          //운임DTHC
    private String cychgDthcCurCd;  //운임DTHC통화코드
    private int cychgDf;            //서류발급비
    private String cychgDfCurCd;    //서류발급비 통화코드
    private int cychgDo;            //화물인도지시서요금
    private String cychgDoCurCd;    //화물인도지시서요금 통화코드
    private int cychgWaf;           //부두사용료
    private String cychgWafCurCd;   //부두사용료 통화코드
    private double cychgSc;            //컨테이너봉인료
    private String cychgScCurCd;    //컨테이너봉인료 통화코드
    private int cychgEtc;           //운임기타
    private String cychgEtcCurCd;   //운임기타통화코드
}
