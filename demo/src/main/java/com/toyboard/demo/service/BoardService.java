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
        if(boardDTO.getBoardFile().isEmpty()) {
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
    public BoardDTO updateBoard(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

//        return BoardDTO.toBoardDTO(board);

        if(board.getFileAttached() == 0) {
            Board b = Board.toUpdateEntity(boardDTO);
            boardRepository.save(b);
            return getBoard(boardDTO.getId());
        }else { //파일이 존재하는 게시글 -> 해당 게시글의 파일 불러옴
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for(BoardFile boardFile : board.getBoardFileList()) {
                originalFileNameList.add(boardFile.getOriginalFileName());
                storedFileNameList.add(boardFile.getStoredFileName());
            }

            Board b = Board.builder()
                    .id(boardDTO.getId())
                    .title(boardDTO.getTitle())//제목 수정 반영
                    .content(boardDTO.getContent())//내용 수정 반영
                    .fileAttached(board.getFileAttached())
                    .build();

            //수정이 반영된 게시글 DB에 저장
            boardRepository.save(b);

            return   BoardDTO.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .content(b.getContent())
                    .createdAt(b.getCreatedAt())
                    .fileAttached(b.getFileAttached())
                    .originalFileName(originalFileNameList)
                    .storedFileName(storedFileNameList)
                    .build();

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
