package com.example.globalO2O.login.domain.community;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimeString {
    public static String getDatetime() {
         return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
    }
}
