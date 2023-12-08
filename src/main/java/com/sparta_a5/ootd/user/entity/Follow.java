package com.sparta_a5.ootd.user.entity;

import com.sparta_a5.ootd.post.timestamped.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends Timestamped {

    @EmbeddedId
    private FollowPK followPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    @MapsId("followingId")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    @MapsId("followerId")
    private User follower;

    @Builder
    public Follow(User following, User follower) {
        this.following = following;
        this.follower = follower;
        this.followPK = FollowPK.builder()
                .followingId(following.getId())
                .followerId(follower.getId())
                .build();
    }
}
