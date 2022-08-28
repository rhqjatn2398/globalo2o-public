package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.event.ImageDeleteEvent;
import com.example.globalO2O.login.domain.event.ImageSaveEvent;
import com.example.globalO2O.login.domain.event.RecommendationEvent;
import com.example.globalO2O.login.domain.event.UpdateHotBoardEvent;
import com.example.globalO2O.login.domain.exception.DuplicateRecommendationException;
import com.example.globalO2O.login.domain.user.User;
import com.example.globalO2O.login.domain.user.UserRepository;
import com.example.globalO2O.login.http.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.globalO2O.login.domain.community.CustomTimeString.getDatetime;

/**
 * @author Bumsoo
 * @version 1.2, 2022.8.22
 */
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Board post(BoardDto boardDto, String loginId) {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();

        Boolean hasImage = false;
        if (boardDto.getImage() != null) {
            hasImage = true;
        }

        Board board = Board
            .builder()
            .category(boardDto.getCategory())
            .uid(user.getId())
            .date(getDatetime())
            .recommend(0)
            .hasImage(hasImage)
            .boardTitle(boardDto.getTitle())
            .boardContent(boardDto.getContent())
            .nickname(user.getNickname())
            .build();

        Board result = boardRepository.save(board);

        if (boardDto.getImage() != null) {
            applicationEventPublisher.publishEvent(new ImageSaveEvent(result, boardDto.getImage()));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto searchBoard(BoardRequestDto boardRequestDto) {
        List<Board> list;

        if (boardRequestDto.getList() != null) {
            return BoardResponseDto.builder().category(boardRequestDto.getCategory())
                    .boards(new ArrayList<>(boardRepository.findByIdInOrderByIdDesc(boardRequestDto.getList())))
                    .build();
        }

        if (boardRequestDto.getKeyword() != null) {
            list = boardRepository.findByIdLessThanEqualAndBoardTitleContainsIgnoreCaseAndCategoryOrderByIdDesc(boardRequestDto.getRange(), boardRequestDto.getKeyword(), boardRequestDto.getCategory());
        } else {
            list = boardRepository.findByIdLessThanEqualAndCategoryOrderByIdDesc(boardRequestDto.getRange(), boardRequestDto.getCategory());
        }

        return BoardResponseDto.builder().category(boardRequestDto.getCategory())
                .boards(new ArrayList<>(list.subList(0, Math.min(boardRequestDto.getSize(), list.size()))))
                .build();
    }

    @Transactional
    public Board updateBoard(BoardUpdateDto boardUpdateDto, String loginId) throws Exception {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();
        Board board = boardRepository.findById(boardUpdateDto.getBoardId()).orElseThrow();

        if (!board.getUid().equals(user.getId())) {
            throw new Exception("다른 사용자의 글을 수정할 수 없습니다.");
        }

        board.setBoardTitle(boardUpdateDto.getTitle());
        board.setBoardContent(boardUpdateDto.getContent());

        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId, String loginId) throws Exception {
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();

        if (!board.getUid().equals(user.getId())) {
            throw new Exception("다른 사용자의 글을 삭제할 수 없습니다.");
        }

        if (board.getHasImage()) {
            applicationEventPublisher.publishEvent(new ImageDeleteEvent(board));
        }

        boardRepository.delete(board);
    }

    @Transactional
    public Board recommendBoard(BoardRecommendDto boardRecommendDto, String loginId) throws DuplicateRecommendationException {
        Board board = boardRepository.findById(boardRecommendDto.getBoardId()).orElseThrow();
        User user = userRepository.findOneWithAuthoritiesByLoginId(loginId).orElseThrow();

        if (user.getRecommendations().stream().anyMatch(recommendation -> Objects.equals(recommendation.getBoard().getId(), boardRecommendDto.getBoardId()))) {
            throw new DuplicateRecommendationException("이미 추천한 글입니다.");
        }

        applicationEventPublisher.publishEvent(new RecommendationEvent(user, board));
        board.setRecommend(board.getRecommend() + 1);

        applicationEventPublisher.publishEvent(new UpdateHotBoardEvent());

        return boardRepository.save(board);
    }
}
