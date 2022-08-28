package com.example.globalO2O.login.http.dto;

import com.example.globalO2O.login.domain.community.Board;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDto {
    Integer category;
    ArrayList<Board> boards;
}