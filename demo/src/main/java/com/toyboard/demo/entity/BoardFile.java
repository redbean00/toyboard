package com.toyboard.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_file_tb")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFile extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
