package com.toyboard.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BoardUserDto {
    private Long id;
    private String username;
    private String title;
}
