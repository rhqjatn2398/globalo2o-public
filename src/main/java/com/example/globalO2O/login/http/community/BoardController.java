package com.example.globalO2O.login.http.community;

import com.example.globalO2O.login.domain.community.BoardService;
import com.example.globalO2O.login.domain.community.BoardImagePathService;
import com.example.globalO2O.login.domain.community.HotBoardService;
import com.example.globalO2O.login.domain.exception.DuplicateRecommendationException;
import com.example.globalO2O.login.http.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

/**
 * @author Bumsoo
 * @version 2.2, 2022.8.22

 */
@Slf4j
@RestController
public class BoardController {
    BoardService boardService;
    BoardImagePathService boardImagePathService;
    HotBoardService hotBoardService;

    @Autowired
    public BoardController(BoardService boardService, BoardImagePathService boardImagePathService, HotBoardService hotBoardService) {
        this.boardService = boardService;
        this.boardImagePathService = boardImagePathService;
        this.hotBoardService = hotBoardService;
    }

    @PostMapping("/auth/board/new")
    public ResponseEntity<String> add(@Valid @RequestBody BoardDto boardDto, @AuthenticationPrincipal User user) {
        try {
            boardService.post(boardDto, user.getUsername());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }

    @PostMapping("/board/search")
    public ResponseEntity<BoardResponseDto> search(@Valid @RequestBody BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getCategory() == 10) {
            return ResponseEntity.ok(hotBoardService.getBoard(boardRequestDto));
        } else {
            return ResponseEntity.ok(boardService.searchBoard(boardRequestDto));
        }

    }

    @PostMapping("/auth/board/update")
    public ResponseEntity<String> update(@Valid @RequestBody BoardUpdateDto boardUpdateDto, @AuthenticationPrincipal User user) {
        try {
            boardService.updateBoard(boardUpdateDto, user.getUsername());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }

    @PostMapping("/auth/board/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody BoardDeleteDto boardDeleteDto, @AuthenticationPrincipal User user) {
        try {
            boardService.deleteBoard(boardDeleteDto.getBoardId(), user.getUsername());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }

    @PostMapping("/auth/board/recommend")
    public ResponseEntity<String> recommend(@Valid @RequestBody BoardRecommendDto boardRecommendDto, @AuthenticationPrincipal User user) {
        try {
            boardService.recommendBoard(boardRecommendDto, user.getUsername());
            return ResponseEntity.ok("success");
        } catch (DuplicateRecommendationException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("already");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }

    @PostMapping("/board/image")
    public ResponseEntity<String> getImage(@Valid @RequestBody BoardImageRequestDto boardImageRequestDto) {
        try {
            return ResponseEntity.ok(boardImagePathService.getImage(boardImageRequestDto.getBoardId()).stream().findFirst().orElseThrow());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }
}