package com.toyboard.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUserDto {
    private Long id;
    private String username;
    private String title;
}
