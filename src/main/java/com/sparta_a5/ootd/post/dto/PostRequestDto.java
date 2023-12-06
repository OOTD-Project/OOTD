package com.sparta_a5.ootd.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String content;
    private String imageURL;
}
