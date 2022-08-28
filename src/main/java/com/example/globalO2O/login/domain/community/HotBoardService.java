package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.event.UpdateHotBoardEvent;
import com.example.globalO2O.login.http.dto.BoardRequestDto;
import com.example.globalO2O.login.http.dto.BoardResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotBoardService {
    HotBoardRepository hotBoardRepository;
    BoardRepository boardRepository;

    @Autowired
    public HotBoardService(HotBoardRepository hotBoardRepository, BoardRepository boardRepository) {
        this.hotBoardRepository = hotBoardRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    @EventListener
    public void updateList(UpdateHotBoardEvent event) {
        List<Board> list = boardRepository.findFirst10ByOrderByRecommendDesc();

        list.stream().filter(board -> hotBoardRepository.findById(board.getId()).isEmpty())
                .map(board -> hotBoardRepository.save(new HotBoard(board))).collect(Collectors.toList());

        for (Board board: list.stream().filter(board -> hotBoardRepository.findById(board.getId()).isEmpty()).collect(Collectors.toList())) {
            log.info(board.getBoardTitle());
        }
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(BoardRequestDto boardRequestDto) {
        ArrayList<Board> list = new ArrayList<>(hotBoardRepository.findAll().stream().map(hotBoard -> hotBoard.getBoard()).collect(Collectors.toList()));

        return BoardResponseDto.builder()
                .category(boardRequestDto.getCategory())
                .boards(list)
                .build();
    }
}
