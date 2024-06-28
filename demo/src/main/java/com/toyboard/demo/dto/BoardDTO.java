package com.toyboard.demo.dto;

import com.toyboard.demo.entity.Board;
import com.toyboard.demo.entity.BoardFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private List<MultipartFile> boardFile;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private String createdAt;
    private int fileAttached;

    public static BoardDTO toBoardDTO(Board board) {
        if(board.getFileAttached() == 0) {
            return   BoardDTO.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .fileAttached(board.getFileAttached())
                    .build();
        }else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for(BoardFile boardFile : board.getBoardFileList()) {
                originalFileNameList.add(boardFile.getOriginalFileName());
                storedFileNameList.add(boardFile.getStoredFileName());
            }
            return   BoardDTO.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .fileAttached(board.getFileAttached())
                    .originalFileName(originalFileNameList)
                    .storedFileName(storedFileNameList)
                    .build();
        }
    }
}


