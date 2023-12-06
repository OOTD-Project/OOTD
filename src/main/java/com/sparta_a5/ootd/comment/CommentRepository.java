package com.sparta_a5.ootd.comment;

import com.sparta_a5.ootd.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
