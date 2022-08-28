package com.example.globalO2O.login.http.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmailTokenDto {
    String token;

    public EmailTokenDto(String token) {
        this.token = token;
    }
}