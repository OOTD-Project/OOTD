package com.sparta_a5.ootd.post.service;

import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.post.entity.Post;
import com.sparta_a5.ootd.post.repository.PostRepository;
import com.sparta_a5.ootd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {

        Post post = postRepository.save(new Post(postRequestDto,user));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> postResponseDtoList = postList.stream()
                .map(PostResponseDto::new)
                .toList();
        return postResponseDtoList;

    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
         Post post = postRepository.findById(postId).orElseThrow(
                 ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
         );

         if(post.getUser() == user){
             post.update(postRequestDto);
             return new PostResponseDto(post);
         } else {
             throw new IllegalArgumentException("권한이 없습니다.");
         }
    }

    public void deletePost(Long postId,User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        if(post.getUser() == user){
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}
