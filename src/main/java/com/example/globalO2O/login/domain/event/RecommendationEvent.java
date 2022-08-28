package com.example.globalO2O.login.domain.event;

import com.example.globalO2O.login.domain.community.Board;
import com.example.globalO2O.login.domain.user.User;
import lombok.Getter;

@Getter
public class RecommendationEvent {
    private final User user;
    private final Board board;

    public RecommendationEvent(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}