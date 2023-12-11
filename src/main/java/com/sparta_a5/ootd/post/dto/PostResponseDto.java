package com.sparta_a5.ootd.post.dto;

import com.sparta_a5.ootd.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String userId;
    private String title;
    private String content;
    private String imageURL;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostResponseDto(Post post, String imageURL) {
        this.postId = post.getId();
        this.userId = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageURL = imageURL;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

}
