package com.sparta_a5.ootd.userPostLike.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserPostLikeResponse {

    private Long postId;
    private String title;
    private String imageURL;

    @Builder
    public UserPostLikeResponse(Long postId, String title, String imageURL){
        this.postId = postId;
        this.title = title;
        this.imageURL = imageURL;
    }
}
