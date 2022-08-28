package com.example.globalO2O.login.http.community;

import com.example.globalO2O.login.domain.community.CommentService;
import com.example.globalO2O.login.domain.community.NestedCommentService;
import com.example.globalO2O.login.http.dto.CommentDeleteDto;
import com.example.globalO2O.login.http.dto.CommentDto;
import com.example.globalO2O.login.http.dto.CommentRequestDto;
import com.example.globalO2O.login.http.dto.CommentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class CommentController {

    CommentService commentService;

    NestedCommentService nestedCommentService;

    @Autowired
    public CommentController(CommentService commentService, NestedCommentService nestedCommentService) {
        this.commentService = commentService;
        this.nestedCommentService = nestedCommentService;
    }

    @PostMapping("/auth/comment/new")
    public ResponseEntity<String> add(@Valid @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
        try {
            if (commentDto.getIs_nested()) {
                nestedCommentService.saveNestedComment(commentDto, user.getUsername());
            } else {
                commentService.saveComment(commentDto, user.getUsername());
            }
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }

    @PostMapping("/comment/search")
    public ResponseEntity<CommentResponseDto> search(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = new CommentResponseDto(commentService.getComments(commentRequestDto));
        return ResponseEntity.ok(commentResponseDto);
    }

    @PostMapping("/auth/comment/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody CommentDeleteDto commentDeleteDto, @AuthenticationPrincipal User user) {
        try {
            commentService.deleteComment(commentDeleteDto.getCommentId(), user.getUsername());
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok("failed");
        }
    }
}