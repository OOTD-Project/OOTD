package com.sparta_a5.ootd.userPostLike.service;

import com.querydsl.core.BooleanBuilder;
import com.sparta_a5.ootd.common.s3.S3Const;
import com.sparta_a5.ootd.common.s3.S3Util;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.repository.PostRepository;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.userPostLike.dto.UserPostLikeResponse;
import com.sparta_a5.ootd.userPostLike.entity.QUserPostLike;
import com.sparta_a5.ootd.userPostLike.entity.UserPostLike;
import com.sparta_a5.ootd.userPostLike.repository.UserPostLikeQueryRepository;
import com.sparta_a5.ootd.userPostLike.repository.UserPostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPostLikeService {

    private final PostRepository postRepository;
    private final UserPostLikeRepository userPostLikeRepository;
    private final UserPostLikeQueryRepository userPostLikeQueryRepository;
    private final S3Util s3Util;

    @Transactional
    public void togglePostLike(Long postId, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );

        UserPostLike userPostLike = userPostLikeRepository.findByPostIdAndUserId(postId,user.getId());

        if(userPostLike == null){
            userPostLike = UserPostLike.builder()
                    .post(post)
                    .user(user)
                    .build();

            userPostLikeRepository.save(userPostLike);
        } else{
            userPostLikeRepository.delete(userPostLike);
        }
    }


    public List<UserPostLikeResponse> getLikeList(User user,Long offset) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QUserPostLike.userPostLike.user.id.eq(user.getId()));

        List<UserPostLike> userPostLikeList = userPostLikeQueryRepository.findAll(builder,offset);
        return userPostLikeList.stream().map(userPostLike -> UserPostLikeResponse.builder()
                .postId(userPostLike.getPost().getId())
                .title(userPostLike.getPost().getTitle())
                .imageURL(s3Util.getImageURL(S3Const.S3_DIR_POST,userPostLike.getPost().getFilename()))
                .build()

        ).toList();

    }
}
