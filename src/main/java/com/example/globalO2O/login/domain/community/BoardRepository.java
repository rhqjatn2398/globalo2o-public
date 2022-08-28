package com.example.globalO2O.login.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Bumsoo
 * @version 1.1, 2022.7.7
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByIdLessThanEqualAndBoardTitleContainsIgnoreCaseAndCategoryOrderByIdDesc(@Param("id") Long id, @Param("boardContent") String boardContent, @Param("category") int category);
    List<Board> findByIdLessThanEqualAndCategoryOrderByIdDesc(@Param("id") Long id, @Param("category") int category);
    List<Board> findByIdInOrderByIdDesc(List<Long> boardId);
    List<Board> findFirst10ByOrderByRecommendDesc();
}