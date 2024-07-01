package com.toyboard.demo.service;

import com.toyboard.demo.dto.BoardDTO;
import com.toyboard.demo.entity.Board;
import com.toyboard.demo.entity.BoardFile;
import com.toyboard.demo.repository.BoardFileRepository;
import com.toyboard.demo.repository.BoardRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void saveBoard(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().isEmpty() || boardDTO.getBoardFile().get(0).getOriginalFilename().equals("")) {
            Board board = Board.builder()
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .fileAttached(0).build();

            boardRepository.save(board);
        }else {

            Board board = Board.builder()
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .fileAttached(1).build();

            Long savedId = boardRepository.save(board).getId();
            Board b = boardRepository.findById(savedId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

            for(MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:\\toyboard_file\\" + storedFileName;
                log.info("savePath =>  " +savePath);


                boardFile.transferTo(new File(savePath));

                BoardFile bf = BoardFile.builder()
                        .originalFileName(originalFileName)
                        .storedFileName(storedFileName)
                        .board(b).build();

                boardFileRepository.save(bf);
            }
        }
    }


    /**
     * 게시글 수정
     * @param boardDTO
     */
    public void updateBoard(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().isEmpty() || boardDTO.getBoardFile().get(0).getOriginalFilename().equals("")) {
            Board board = Board.builder()
                    .id(boardDTO.getId())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .fileAttached(0).build();

            boardRepository.save(board);
        }else {

            boardRepository.deleteById(boardDTO.getId());

            Board board = Board.builder()
                    .id(boardDTO.getId())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .fileAttached(1).build();

            Long savedId = boardRepository.save(board).getId();
            Board b = boardRepository.findById(savedId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

            for(MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:\\toyboard_file\\" + storedFileName;
                log.info("savePath =>  " +savePath);


                boardFile.transferTo(new File(savePath));

                BoardFile bf = BoardFile.builder()
                        .originalFileName(originalFileName)
                        .storedFileName(storedFileName)
                        .board(b).build();

                boardFileRepository.save(bf);
            }
        }



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
    public List<BoardDTO> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for(Board board : boardList) {
            boardDTOList.add(BoardDTO.toBoardDTO(board));
        }
        return boardDTOList;

    }

    /**
     * 게시글 상세보기
     * @param id
     * @return
     */
    public BoardDTO getBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(board);
            return boardDTO;

        }
        return null;
    }

}
