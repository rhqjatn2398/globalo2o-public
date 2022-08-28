package com.example.globalO2O.sor.DAO;


import java.util.List;

public interface AnnounceRepo {

    Announ save(Announ announ);
    List<Announ> findAll();
    List<Announ> findByCondition(String annNo, String annRuteNm, String shipngPrtNm, String landngPrtNm, String codPrtNm, String tsYn, String contnOwnSeNm, String contnCndNm, String contnStdStndrdNm, String frghtNm, String tnspotSeNm);
    List<Announ> findAnnNo(int annNo);

}
