package com.example.globalO2O.login.http.dto;

import com.example.globalO2O.login.domain.community.Comment;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    ArrayList<Comment> comments;
}
