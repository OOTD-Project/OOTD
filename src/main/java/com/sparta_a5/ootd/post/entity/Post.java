package com.sparta_a5.ootd.post.entity;

import com.sparta_a5.ootd.comment.entity.Comment;
import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.timestamped.Timestamped;
import com.sparta_a5.ootd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String filename;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user){
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.filename = postRequestDto.getFilename();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.filename = postRequestDto.getFilename();
    }
}
