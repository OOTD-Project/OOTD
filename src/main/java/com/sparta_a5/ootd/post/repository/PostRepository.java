package com.sparta_a5.ootd.post.repository;

import com.sparta_a5.ootd.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

}
