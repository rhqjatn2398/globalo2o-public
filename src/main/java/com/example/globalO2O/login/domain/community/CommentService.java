package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.user.User;
import com.example.globalO2O.login.domain.user.UserRepository;
import com.example.globalO2O.login.http.dto.CommentDto;
import com.example.globalO2O.login.http.dto.CommentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.globalO2O.login.domain.community.CustomTimeString.getDatetime;

@Service
public class CommentService {
    BoardRepository boardRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;

    @Autowired
    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment saveComment(CommentDto commentDto, String loginId) {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();

        Comment comment = Comment.builder().content(commentDto.getContent())
                .date(getDatetime())
                .board(boardRepository.getById(commentDto.getParentId()))
                .nickname(user.getNickname())
                .uid(user.getId())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public ArrayList<Comment> getComments(CommentRequestDto commentRequestDto) {
        return new ArrayList<>(commentRepository.findByBoardId(commentRequestDto.getBoardId()));
    }

    @Transactional
    public void deleteComment(Long commentId, String loginId) throws Exception {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (!comment.getUid().equals(user.getId())) {
            throw new Exception("다른 사용자의 댓글을 삭제할 수 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}