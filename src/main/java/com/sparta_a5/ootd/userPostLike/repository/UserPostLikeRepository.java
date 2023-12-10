package com.sparta_a5.ootd.userPostLike.repository;

import com.sparta_a5.ootd.userPostLike.entity.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Long> {
    UserPostLike findByPostIdAndUserId(Long postId, Long id);

    List<UserPostLike> findAllByUserId(Long id);
}
