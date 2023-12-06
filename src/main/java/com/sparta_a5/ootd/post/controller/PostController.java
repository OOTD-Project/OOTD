package com.sparta_a5.ootd.post.controller;

import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.post.service.PostService;
import com.sparta_a5.ootd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("")
    public List<PostResponseDto> getPostList(){
        return postService.getPostList();
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }
}
