package com.example.globalO2O.login.domain.event;

import com.example.globalO2O.login.domain.community.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageSaveEvent {
    private final Board board;
    private final String base64Img;
}
