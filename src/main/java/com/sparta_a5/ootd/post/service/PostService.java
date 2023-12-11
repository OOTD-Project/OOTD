package com.sparta_a5.ootd.post.service;

import com.sparta_a5.ootd.common.s3.S3Util;
import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.repository.PostRepository;
import com.sparta_a5.ootd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta_a5.ootd.common.s3.S3Const.S3_DIR_POST;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Util s3Util;


    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {

        String filename = s3Util.uploadImage(S3_DIR_POST,postRequestDto.getImageFile());
        postRequestDto.setFilename(filename);
        Post post = postRepository.save(new Post(postRequestDto,user));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = postList.stream()
                .map(post -> new PostResponseDto(post, s3Util.getImageURL(S3_DIR_POST,post.getFilename())))
                .toList();
        return postResponseDtoList;

    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        String filename = post.getFilename();
        String imageURL = s3Util.getImageURL(S3_DIR_POST,filename);
        return new PostResponseDto(post,imageURL);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
         Post post = postRepository.findById(postId).orElseThrow(
                 ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
         );

         if(post.getUser().getId().equals(user.getId())){
             String filename = post.getFilename();
             s3Util.deleteImage(S3_DIR_POST,filename);

             String newFilename = s3Util.uploadImage(S3_DIR_POST,postRequestDto.getImageFile());
             postRequestDto.setFilename(newFilename);
             post.update(postRequestDto);



             return new PostResponseDto(post,newFilename);
         } else {
             throw new IllegalArgumentException("권한이 없습니다.");
         }
    }

    public void deletePost(Long postId,User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        if(post.getUser().getId().equals(user.getId())){
            postRepository.delete(post);
            String filename = post.getFilename();
            s3Util.deleteImage(S3_DIR_POST,filename);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}
