package com.toyboard.demo.dto;

import com.toyboard.demo.entity.Board;
import com.toyboard.demo.entity.BoardFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private List<String> imageUrls;

    @Builder
    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.imageUrls = board.getBoardFiles().stream()
                .map(BoardFile::getUrl)
                .collect(Collectors.toList());
    }
}
