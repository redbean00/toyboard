package com.toyboard.demo.service;

import com.toyboard.demo.dto.BoardUserDto;
import com.toyboard.demo.entity.Board;
import com.toyboard.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     * @param board
     */
    public void createBoard(Board board) {
        boardRepository.save(board);
    }

    /**
     * 게시글 수정
     * @param board
     */
    public void updateBoard(Board board) {
        Board b = boardRepository.findById(board.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        boardRepository.save(b);
    }

    /**
     * 게시글 삭제
     * @param board
     */
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

    /**
     * 게시글 리스트 조회
     * @return List<Board>
     */
    public List<BoardUserDto> getBoardList() {
        return boardRepository.findAllByUser();

    }

    public Board getBoard(Board board) {
        return boardRepository.findById(board.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }


}
