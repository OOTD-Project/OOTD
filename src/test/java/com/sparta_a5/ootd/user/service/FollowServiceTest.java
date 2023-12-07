package com.sparta_a5.ootd.user.service;

import com.sparta_a5.ootd.user.entity.Follow;
import com.sparta_a5.ootd.user.repository.FollowQueryRepository;
import com.sparta_a5.ootd.user.repository.FollowRepository;
import com.sparta_a5.ootd.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import test.CommonTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest implements CommonTest {

    @Mock
    FollowRepository followRepository;

    @Mock
    FollowQueryRepository followQueryRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("팔로우 생성 성공")
    public void createFollow_success() {
        //given
        FollowService followService = new FollowService(followRepository, followQueryRepository, userRepository);
        given(userRepository.findById(TEST_ANOTHER_USER_ID)).willReturn(Optional.of(TEST_ANOTHER_USER));

        //when
        Follow follow = followService.createFollow(TEST_USER, TEST_ANOTHER_USER_ID);

        //then
        assertEquals(TEST_USER_NAME, follow.getFollowing().getUsername());
        assertEquals(ANOTHER_PREFIX + TEST_USER_NAME, follow.getFollower().getUsername());
    }
}