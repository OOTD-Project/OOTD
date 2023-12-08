package com.sparta_a5.ootd.post.controller;

import com.sparta_a5.ootd.common.configuration.JwtUtil;
import com.sparta_a5.ootd.common.s3.S3Util;
import com.sparta_a5.ootd.post.dto.PostRequestDto;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.post.service.PostService;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta_a5.ootd.common.s3.S3Const.S3_DIR_POST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final S3Util s3Util;

    @PostMapping("")
    public ResponseEntity<PostResponseDto> createPost(
            @ModelAttribute PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        String filename = s3Util.uploadImage(S3_DIR_POST,postRequestDto.getImageFile());
        postRequestDto.setFilename(filename);
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, userDetails.getUser());
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
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @ModelAttribute PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        PostResponseDto postResponseDto = postService.updatePost(postId, postRequestDto,userDetails.getUser());
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<List<PostResponseDto>> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        postService.deletePost(postId,userDetails.getUser());

        List<PostResponseDto> postResponseDtoList = postService.getPostList();
        return ResponseEntity.ok(postResponseDtoList);
    }
}
