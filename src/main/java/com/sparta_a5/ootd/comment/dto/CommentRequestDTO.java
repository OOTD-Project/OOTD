package com.sparta_a5.ootd.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long userId;
    private Long postId;
    private String comment;
}
