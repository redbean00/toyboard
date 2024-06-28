package com.toyboard.demo.entity;

import com.toyboard.demo.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BoardFile> boardFileList;

    @Column
    private int fileAttached;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Board toUpdateEntity(BoardDTO boardDTO) {
        return Board.builder()
                .id(boardDTO.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .build();
    }
}