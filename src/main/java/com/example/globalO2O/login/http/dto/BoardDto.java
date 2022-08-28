package com.example.globalO2O.login.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    @NotNull
    String title;
    @NotNull
    String content;
    @NotNull
    int category;

    String image;
}