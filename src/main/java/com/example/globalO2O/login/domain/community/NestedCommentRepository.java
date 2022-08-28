package com.example.globalO2O.login.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Bumsoo
 * @version 1.0, 2022.6.3
 */
public interface NestedCommentRepository extends JpaRepository<NestedComment, Long> {
    List<NestedComment> findNestedCommentByCommentId(Long commentId);
}