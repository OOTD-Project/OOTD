package com.sparta_a5.ootd.post.controller;

import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.post.service.PostService;
import com.sparta_a5.ootd.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> getPostList(){
        List<PostResponseDto> postResponseDtoList = postService.getPostList();
        return ResponseEntity.ok(postResponseDtoList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId){
        PostResponseDto postResponseDto = postService.getPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.updatePost(postId, postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<List<PostResponseDto>> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        List<PostResponseDto> postResponseDtoList = postService.getPostList();
        return ResponseEntity.ok(postResponseDtoList);
    }
}
