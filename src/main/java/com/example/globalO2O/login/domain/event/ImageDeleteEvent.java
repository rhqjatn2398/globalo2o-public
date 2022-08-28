package com.example.globalO2O.login.domain.event;

import com.example.globalO2O.login.domain.community.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageDeleteEvent {
    private final Board board;
}