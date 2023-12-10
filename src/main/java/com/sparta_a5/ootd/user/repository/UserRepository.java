package com.sparta_a5.ootd.user.repository;

import com.sparta_a5.ootd.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findBySocialId(Long socialId);

    Optional<User> findByEmail(String kakaoEmail);


}
