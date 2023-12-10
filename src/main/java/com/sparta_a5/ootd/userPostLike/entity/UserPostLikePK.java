package com.sparta_a5.ootd.userPostLike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class UserPostLikePK implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public UserPostLikePK(Long postId, Long userId){
        this.postId = postId;
        this.userId = userId;
    }
}
