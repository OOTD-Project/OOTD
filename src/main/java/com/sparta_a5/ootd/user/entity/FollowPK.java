package com.sparta_a5.ootd.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowPK implements Serializable {

    @Column(name = "following_id")
    private Long followingId;

    @Column(name = "follower_id")
    private Long followerId;

    @Builder
    public FollowPK(Long followingId, Long followerId) {
        this.followingId = followingId;
        this.followerId = followerId;
    }
}
