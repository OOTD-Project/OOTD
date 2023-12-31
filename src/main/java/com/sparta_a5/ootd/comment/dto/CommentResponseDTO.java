package com.sparta_a5.ootd.comment.dto;

import com.sparta_a5.ootd.CommonResponseDTO;
import com.sparta_a5.ootd.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO extends CommonResponseDTO {
    private Long id;
    private String comment;

    // created_at 추가
    private LocalDateTime created_at;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.created_at = comment.getCreated_at(); // created_at 추가
    }
}
