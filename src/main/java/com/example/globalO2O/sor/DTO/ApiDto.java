package com.example.globalO2O.sor.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ApiDto {
    private String annNo;       //공표번호
    private String annRuteNm;   //공표항로명
    private String imxptrNm;    //수출입구분명
    private String shipngPrtNm; //선적지항구명
    private String landngPrtNm; //양하지항구명
    private String codPrtNm;    //양륙지항구명
    private String tsYn;        //환적여부
    private String contnOwnSeNm;//컨테이너소유구분명
    private String contnCndNm;  //컨테이너종류명
    private String contnStdStndrdNm;//컨테이너크기명
    private String frghtNm;      //화물품목명
    private String tnspotSeNm;   //운송단위명

}
