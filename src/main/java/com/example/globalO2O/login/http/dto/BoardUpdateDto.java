package com.example.globalO2O.login.http.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardUpdateDto {
    @NotNull
    String title;
    @NotNull
    String content;
    @NotNull
    Integer category;
    @NotNull
    Long boardId;
}
