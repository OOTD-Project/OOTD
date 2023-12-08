package com.sparta_a5.ootd.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String content;
    private MultipartFile imageFile;
    private String filename;
}
