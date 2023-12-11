package com.sparta_a5.ootd.userPostLike.controller;

import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import com.sparta_a5.ootd.userPostLike.dto.UserPostLikeResponse;
import com.sparta_a5.ootd.userPostLike.service.UserPostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class UserPostLikeController {

    private final UserPostLikeService userPostLikeService;

    @PostMapping("/{postId}/like")
    public void togglePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        userPostLikeService.togglePostLike(postId,userDetails.getUser());
    }

    @GetMapping("/like/{offset}")
    public ResponseEntity<List<UserPostLikeResponse>> getLikeList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("offset") Long offset
    ){

       return ResponseEntity.ok(userPostLikeService.getLikeList(userDetails.getUser(),offset));
    }
}
