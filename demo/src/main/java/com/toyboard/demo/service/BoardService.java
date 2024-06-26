package com.toyboard.demo.service;

import com.toyboard.demo.dto.BoardDto;
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
     * @param boardDto
     */
    public void createBoard(BoardDto boardDto) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();
        boardRepository.save(board);
    }


    /**
     * 게시글 수정
     * @param board
     */
    public void updateBoard(Board board) {
        Board b = null;
        if(boardRepository.findById(board.getId()).isPresent()) {
            b = Board.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();
        }
        boardRepository.save(b);
    }

    /**
     * 게시글 삭제
     * @param id
     */
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    /**
     * 게시글 리스트 조회
     * @return List<Board>
     */
    public List<Board> getBoardList() {
        return boardRepository.findAll();

    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }


}
