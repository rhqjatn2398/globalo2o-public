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
public class CommentDto {
    @NotNull
    String content;
    @NotNull
    Long parentId;
    @NotNull
    Boolean is_nested;
}
