create or replace table announce(
    annId bigint auto_increment,

    annNo varchar(10),
    annRuteNm varchar(6),
    shipngPrtNm varchar(50),
    landngPrtNm varchar(50),
    codPrtNm varchar(50),
    tsYn varchar(1),
    contnOwnSeNm varchar(20),
    contnCndNm varchar(20),
    contnStdStndrdNm varchar(20),
    frghtNm varchar(20),
    tnspotSeNm varchar(20),

    cychgOf int,
    cychgBaf int,
    cychgCaf int,
    cychgLss int,
    cychgEbs int,
    cychgOthc int,
    cychgDthc int,
    cychgDf int,
    cychgDo int,
    cychgWaf int,
    cychgSc double,
    cychgEtc int,

    cychgOfCurCd varchar(20),
    cychgBafCurCd varchar(20),
    cychgCafCurCd varchar(20),
    cychgLssCurCd varchar(20),
    cychgEbsCurCd varchar(20),
    cychgOthcCurCd varchar(20),
    cychgDthcCurCd varchar(20),
    cychgDfCurCd varchar(20),
    cychgDoCurCd varchar(20),
    cychgWafCurCd varchar(20),
    cychgScCurCd varchar(20),
    cychgEtcCurCd varchar(20),

    primary key(annId)
);