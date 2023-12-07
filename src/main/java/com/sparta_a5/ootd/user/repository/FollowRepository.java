package com.sparta_a5.ootd.user.repository;

import com.sparta_a5.ootd.user.entity.Follow;
import com.sparta_a5.ootd.user.entity.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowPK>  {
}
