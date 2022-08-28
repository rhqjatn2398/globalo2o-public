package com.example.globalO2O.login.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Bumsoo
 * @version 1.0, 2022.6.3
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long boardId);
}