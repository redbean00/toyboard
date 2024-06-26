package com.toyboard.demo.dto;

import com.toyboard.demo.entity.Board;
import com.toyboard.demo.entity.BoardFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String title;
    private String content;
    private List<String> imageUrls;

    @Builder
    public BoardDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();;
        this.imageUrls = board.getBoardFiles().stream()
                .map(BoardFile::getUrl)
                .collect(Collectors.toList());
    }
}
