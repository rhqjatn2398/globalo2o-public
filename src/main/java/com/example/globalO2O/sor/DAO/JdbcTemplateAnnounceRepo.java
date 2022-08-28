package com.example.globalO2O.sor.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateAnnounceRepo implements AnnounceRepo {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateAnnounceRepo(DataSource dataSource){ this.jdbcTemplate = new JdbcTemplate(dataSource);};

    @Override
    public Announ save(Announ announ) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("announce").usingGeneratedKeyColumns("annId");

        Map<String, Object> params = new HashMap<>();

        params.put("annNo", announ.getAnnNo());
        params.put("annRuteNm", announ.getAnnRuteNm());
        params.put("shipngPrtNm", announ.getShipngPrtNm());
        params.put("landngPrtNm", announ.getLandngPrtNm());
        params.put("codPrtNm", announ.getCodPrtNm());
        params.put("tsYn", announ.getTsYn());
        params.put("contnOwnSeNm", announ.getContnOwnSeNm());
        params.put("contnCndNm", announ.getContnCndNm());
        params.put("contnStdStndrdNm", announ.getContnStdStndrdNm());
        params.put("frghtNm", announ.getFrghtNm());
        params.put("tnspotSeNm", announ.getTnspotSeNm());

        params.put("cychgOf", announ.getCychgOf());
        params.put("cychgBaf", announ.getCychgBaf());
        params.put("cychgCaf", announ.getCychgCaf());
        params.put("cychgLss", announ.getCychgLss());
        params.put("cychgEbs", announ.getCychgEbs());
        params.put("cychgOthc", announ.getCychgOthc());
        params.put("cychgDthc", announ.getCychgDthc());
        params.put("cychgDf", announ.getCychgDf());
        params.put("cychgDo", announ.getCychgDo());
        params.put("cychgWaf", announ.getCychgWaf());
        params.put("cychgSc", announ.getCychgSc());
        params.put("cychgEtc", announ.getCychgEtc());

        params.put("cychgOfCurCd", announ.getCychgOfCurCd());
        params.put("cychgBafCurCd", announ.getCychgBafCurCd());
        params.put("cychgCafCurCd", announ.getCychgCafCurCd());
        params.put("cychgLssCurCd", announ.getCychgLssCurCd());
        params.put("cychgEbsCurCd", announ.getCychgEbsCurCd());
        params.put("cychgOthcCurCd", announ.getCychgOthcCurCd());
        params.put("cychgDthcCurCd", announ.getCychgDthcCurCd());
        params.put("cychgDfCurCd", announ.getCychgDfCurCd());
        params.put("cychgDoCurCd", announ.getCychgWafCurCd());
        params.put("cychgWafCurCd", announ.getCychgWafCurCd());
        params.put("cychgScCurCd", announ.getCychgScCurCd());
        params.put("cychgEtcCurCd", announ.getCychgEtcCurCd());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        announ.setAnnId(key.intValue());

        return announ;
    }

    @Override
    public List<Announ> findAnnNo(int annNo){
        return jdbcTemplate.query("select annNo from announce where annNo in (?)", annNoRowMapper(), annNo);
    }

    @Override
    public List<Announ> findAll() {
        return jdbcTemplate.query("select * from announce", announRowMapper());
    }

    @Override
    public List<Announ> findByCondition(String annNo, String annRuteNm, String shipngPrtNm, String landngPrtNm, String codPrtNm, String tsYn, String contnOwnSeNm, String contnCndNm, String contnStdStndrdNm, String frghtNm, String tnspotSeNm){
        return jdbcTemplate.query("select * from announce where annNo = ? and annRuteNm = ? and shipngPrtNm = ? and landngPrtNm = ? and codPrtNm = ? and tsYn = ? and contnOwnSeNm = ? and contnCndNm = ? and contnStdStndrdNm = ? and frghtNm = ? and tnspotSeNm = ?", announRowMapper(), annNo, annRuteNm, shipngPrtNm, landngPrtNm, codPrtNm, tsYn, contnOwnSeNm, contnCndNm, contnStdStndrdNm, frghtNm, tnspotSeNm);
    }

    private RowMapper<Announ> annNoRowMapper(){
        return (rs, rowNum) -> {
              Announ announ = new Announ();
              announ.setAnnNo(rs.getString("annNo"));

              return announ;
        };
    }

    private RowMapper<Announ> announRowMapper(){
       return (rs, rowNum) -> {
           Announ announ = new Announ();
           announ.setAnnNo(rs.getString("annNo"));
           announ.setAnnRuteNm(rs.getString("annRuteNm"));
           announ.setShipngPrtNm(rs.getString("shipngPrtNm"));
           announ.setLandngPrtNm(rs.getString("landngPrtNm"));
           announ.setTsYn(rs.getString("tsYn"));
           announ.setContnOwnSeNm(rs.getString("contnOwnSeNm"));
           announ.setContnCndNm(rs.getString("contnCndNm"));
           announ.setContnStdStndrdNm(rs.getString("contnStdStndrdNm"));
           announ.setFrghtNm(rs.getString("frghtNm"));
           announ.setTnspotSeNm(rs.getString("tnspotSeNm"));

           announ.setCychgOf(rs.getInt("cychgOf"));
           announ.setCychgBaf(rs.getInt("cychgBaf"));
           announ.setCychgCaf(rs.getInt("cychgCaf"));
           announ.setCychgLss(rs.getInt("cychgLss"));
           announ.setCychgEbs(rs.getInt("cychgEbs"));
           announ.setCychgOthc(rs.getInt("cychgOthc"));
           announ.setCychgDthc(rs.getInt("cychgDthc"));
           announ.setCychgDf(rs.getInt("cychgDf"));
           announ.setCychgDo(rs.getInt("cychgDo"));
           announ.setCychgWaf(rs.getInt("cychgWaf"));
           announ.setCychgSc(rs.getDouble("cychgSc"));
           announ.setCychgEtc(rs.getInt("cychgEtc"));

           announ.setCychgOfCurCd(rs.getString("cychgOfCurCd"));
           announ.setCychgBafCurCd(rs.getString("cychgBafCurCd"));
           announ.setCychgCafCurCd(rs.getString("cychgCafCurCd"));
           announ.setCychgLssCurCd(rs.getString("cychgLssCurCd"));
           announ.setCychgEbsCurCd(rs.getString("cychgEbsCurCd"));
           announ.setCychgOthcCurCd(rs.getString("cychgOthcCurCd"));
           announ.setCychgDthcCurCd(rs.getString("cychgDthcCurCd"));
           announ.setCychgDfCurCd(rs.getString("cychgDfCurCd"));
           announ.setCychgDoCurCd(rs.getString("cychgDoCurCd"));
           announ.setCychgWafCurCd(rs.getString("cychgWafCurCd"));
           announ.setCychgScCurCd(rs.getString("cychgScCurCd"));
           announ.setCychgEtcCurCd(rs.getString("cychgEtcCurCd"));

           return announ;
       };
    }
}

