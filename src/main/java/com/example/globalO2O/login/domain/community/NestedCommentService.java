package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.user.User;
import com.example.globalO2O.login.domain.user.UserRepository;
import com.example.globalO2O.login.http.dto.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NestedCommentService {
    NestedCommentRepository nestedCommentRepository;

    CommentRepository commentRepository;
    UserRepository userRepository;

    @Autowired
    public NestedCommentService(NestedCommentRepository nestedCommentRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.nestedCommentRepository = nestedCommentRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public NestedComment saveNestedComment(CommentDto CommentDto, String loginId) {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElse(null);

        NestedComment nestedComment = NestedComment.builder().content(CommentDto.getContent())
                .date(CustomTimeString.getDatetime())
                .nickname(user.getNickname())
                .uid(user.getId())
                .comment(commentRepository.getById(CommentDto.getParentId()))
                .build();

        return nestedCommentRepository.save(nestedComment);
    }
}
