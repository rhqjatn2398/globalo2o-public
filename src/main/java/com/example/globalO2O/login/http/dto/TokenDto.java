package com.example.globalO2O.login.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private Long uid;
    private String nickname;
    private String name;
    private String email;
    private String token;
}