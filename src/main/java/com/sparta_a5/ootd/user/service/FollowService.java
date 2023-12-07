package com.sparta_a5.ootd.user.service;

import com.querydsl.core.BooleanBuilder;
import com.sparta_a5.ootd.common.s3.S3Const;
import com.sparta_a5.ootd.common.s3.S3Util;
import com.sparta_a5.ootd.user.dto.follow.FollowResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowQueryRepository followQueryRepository;
    private final UserRepository userRepository;
    private final S3Util s3Util;

    @Transactional
    public Follow createFollow(User user, Long followUserId) {
        User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Follow follow = Follow.builder()
                            .following(user)
                            .follower(followUser)
                            .build();

        followRepository.save(follow);
        return follow;
    }

    //사용자를 팔로우하는 사람들의 목록을 조회하는 기능
    public List<FollowResponse> readFollowerAll(Long userId, String direction, Long offset) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QFollow.follow.follower.id.eq(userId));

        List<Follow> follows = followQueryRepository.findAll(builder, getDirection(direction), offset);
        return follows.stream().map(follow -> FollowResponse.builder()
                        .userId(follow.getFollowing().getId())
                        .username(follow.getFollowing().getUsername())
                        .imageURL(s3Util.getImageURL(S3Const.S3_DIR_USER_PROFILE, follow.getFollowing().getFilename()))
                        .build()
        ).toList();
    }

    //사용자가 팔로우하는 사람들의 목록을 조회하는 기능
    public List<FollowResponse> readFollowingAll(Long userId, String direction, Long offset) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QFollow.follow.following.id.eq(userId));

        List<Follow> follows = followQueryRepository.findAll(builder, getDirection(direction), offset);
        return follows.stream().map(follow -> FollowResponse.builder()
                        .userId(follow.getFollower().getId())
                        .username(follow.getFollower().getUsername())
                        .imageURL(s3Util.getImageURL(S3Const.S3_DIR_USER_PROFILE, follow.getFollower().getFilename()))
                        .build()
        ).toList();
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

    private Sort.Direction getDirection(String direction) {
        return "ASC".equals(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
