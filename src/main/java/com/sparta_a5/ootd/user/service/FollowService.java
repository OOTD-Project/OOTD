package com.sparta_a5.ootd.user.service;

import com.querydsl.core.BooleanBuilder;
import com.sparta_a5.ootd.user.entity.Follow;
import com.sparta_a5.ootd.user.entity.FollowPK;
import com.sparta_a5.ootd.user.entity.QFollow;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.repository.FollowQueryRepository;
import com.sparta_a5.ootd.user.repository.FollowRepository;
import com.sparta_a5.ootd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowQueryRepository followQueryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Follow createFollow(User user, Long followUserId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다.")
        );

        Follow follow = Follow.builder()
                            .following(user)
                            .follower(followUser)
                            .build();

        followRepository.save(follow);
        return follow;
    }

    //사용자를 팔로우하는 사람들의 목록을 조회하는 기능
    public List<Follow> readFollowerAll(Long userId, Sort.Direction direction, Long offset) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QFollow.follow.follower.id.eq(userId));

        return followQueryRepository.findAll(builder, direction, offset);
    }

    //사용자가 팔로우하는 사람들의 목록을 조회하는 기능
    public List<Follow> readFollowingAll(Long userId, Sort.Direction direction, Long offset) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QFollow.follow.following.id.eq(userId));

        return followQueryRepository.findAll(builder, direction, offset);
    }

    @Transactional
    public void deleteFollow(User user, Long followUserId) {
        Follow follow = followRepository.findById(
                FollowPK.builder()
                        .followingId(user.getId())
                        .followerId(followUserId)
                        .build()
        ).orElseThrow(() -> new IllegalArgumentException("해당 팔로우가 없습니다."));

        followRepository.delete(follow);
    }
}
