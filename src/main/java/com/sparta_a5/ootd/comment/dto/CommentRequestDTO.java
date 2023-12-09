package com.sparta_a5.ootd.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long userId;
    private Long postId;
    private String content;
    private Long parentId;

    public CommentRequestDTO(String content) {
        this.content = content;
    }
}
