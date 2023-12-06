package com.sparta_a5.ootd.comment.dto;

import com.sparta_a5.ootd.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    private Long id;
    private String comment;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }
}
