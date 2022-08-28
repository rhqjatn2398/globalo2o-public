package com.example.globalO2O.login.http.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardDeleteDto {
    @NotNull
    Long boardId;
}