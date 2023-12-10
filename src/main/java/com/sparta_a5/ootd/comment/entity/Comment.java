package com.sparta_a5.ootd.comment.entity;

import com.sparta_a5.ootd.comment.dto.CommentRequestDTO;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column
    private LocalDateTime created_at;

    public Comment(CommentRequestDTO dto) {
        this.content = dto.getContent();
        this.created_at = LocalDateTime.now();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void setContent(String comment) {
        this.content = comment;
    }

}