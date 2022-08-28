package com.example.globalO2O.login.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    Integer category;
    Integer size;
    Long range;
    String keyword;
    ArrayList<Long> list;
}