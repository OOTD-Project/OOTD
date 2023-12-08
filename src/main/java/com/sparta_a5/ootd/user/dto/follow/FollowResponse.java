package com.sparta_a5.ootd.user.dto.follow;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponse {
    private final Long userId;
    private final String username;
    private final String imageURL;

    @Builder
    public FollowResponse(Long userId, String username, String imageURL) {
        this.userId = userId;
        this.username = username;
        this.imageURL = imageURL;
    }
}
