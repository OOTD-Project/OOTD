package com.sparta_a5.ootd.userPostLike.entity;

import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.timestamped.Timestamped;
import com.sparta_a5.ootd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPostLike extends Timestamped {

    @EmbeddedId
    private UserPostLikePK userPostLikePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId("postId")
    private Post post;

    @Builder
    public UserPostLike(User user, Post post){
        this.user = user;
        this.post = post;
        this.userPostLikePK = UserPostLikePK.builder()
                .userId(user.getId())
                .postId(post.getId())
                .build();
    }
}
