package com.example.globalO2O.login.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImagePathRepository extends JpaRepository<BoardImagePath, String> {
    List<BoardImagePath> findImagePathByBoardId(Long boardId);
}