package com.toyboard.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardFileUploadDTO {
    private List<MultipartFile> files;
}
