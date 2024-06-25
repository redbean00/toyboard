package com.toyboard.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "board_tb")
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

}
