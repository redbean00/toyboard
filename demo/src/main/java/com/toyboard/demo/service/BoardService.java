package com.toyboard.demo.service;

import com.toyboard.demo.dto.BoardFileUploadDTO;
import com.toyboard.demo.dto.BoardResponseDTO;
import com.toyboard.demo.dto.BoardWriteRequestDTO;
import com.toyboard.demo.entity.Board;
import com.toyboard.demo.entity.BoardFile;
import com.toyboard.demo.repository.BoardFileRepository;
import com.toyboard.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    /**
     * 게시글 등록
     * @param boardWriteRequestDTO
     * @param boardFileUploadDTO
     */
    public void saveBoard(BoardWriteRequestDTO boardWriteRequestDTO, BoardFileUploadDTO boardFileUploadDTO)  {
        Board result = Board.builder()
                    .title(boardWriteRequestDTO.getTitle())
                    .content(boardWriteRequestDTO.getContent())
                    .build();

        boardRepository.save(result);

        if(boardFileUploadDTO.getFiles() != null && !boardFileUploadDTO.getFiles().isEmpty()) {
            for(MultipartFile file : boardFileUploadDTO.getFiles()) {
                String boardFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File destinationFile = new File("C:\\toyboard_image" + boardFileName);

                try{
                    file.transferTo(destinationFile);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }

                BoardFile boardFile = BoardFile.builder()
                        .url("/boardFiles/" + boardFileName)
                        .board(result)
                        .build();

                boardFileRepository.save(boardFile);
            }
        }
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

    /**
     * 게시글 상세보기
     * @param id
     * @return
     */
    public BoardResponseDTO getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return BoardResponseDTO.builder()
                .board(board)
                .build();
    }

}
