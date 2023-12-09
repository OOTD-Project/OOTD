package com.sparta_a5.ootd.comment.entity;

import com.sparta_a5.ootd.comment.dto.CommentRequestDTO;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String content;

    // 삭제 유무
    // true : 삭제된 댓글(default는 false)
    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 부모 댓글
    // null일 경우 최상위 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true) // 부모 댓글이 자식 댓글을 삭제하면 자식 댓글이 DB에서 삭제됨
    private List<Comment> children = new ArrayList<>(); // List로 저장(댓글에 대댓글 여러개 가능)

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

    public void updateParent(Comment comment) {
        this.parent = comment;
    }

    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
