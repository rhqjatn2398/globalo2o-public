package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.event.ImageDeleteEvent;
import com.example.globalO2O.login.domain.event.ImageSaveEvent;
import com.example.globalO2O.login.domain.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BoardImagePathService {
    BoardImagePathRepository boardImagePathRepository;
    S3Service s3Service;

    @Value("${cloud.aws.s3.dir.board-images}")
    private String DIR;

    @Autowired
    public BoardImagePathService(BoardImagePathRepository boardImagePathRepository, S3Service s3Service) {
        this.boardImagePathRepository = boardImagePathRepository;
        this.s3Service = s3Service;
    }

    @EventListener
    public void upload(ImageSaveEvent event) {
        String fileName = UUID.randomUUID().toString() + CustomTimeString.getDatetime();
        s3Service.upload(DIR, fileName, event.getBase64Img());

        BoardImagePath boardImagePath = BoardImagePath
                .builder()
                .path(fileName)
                .board(event.getBoard())
                .build();

        boardImagePathRepository.save(boardImagePath);
    }

    @EventListener
    public void delete(ImageDeleteEvent event) {
        Long boardId = event.getBoard().getId();
        BoardImagePath boardImagePath = boardImagePathRepository.findImagePathByBoardId(boardId).stream().findFirst().orElseThrow();

        s3Service.delete(DIR, boardImagePath.getPath());

        boardImagePathRepository.delete(boardImagePath);
    }

    @Transactional(readOnly = true)
    public List<String> getImage(Long boardId) throws IOException {
        List<String> list = boardImagePathRepository.findImagePathByBoardId(boardId).stream().map(boardImagePath -> boardImagePath.getPath()).collect(Collectors.toList());

        return List.of(s3Service.download(DIR, list.stream().findFirst().orElseThrow()));
    }
}
