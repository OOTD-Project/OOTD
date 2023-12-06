package com.sparta_a5.ootd.userPostLike.controller;

import com.sparta_a5.ootd.userPostLike.service.UserPostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class UserPostLikeController {

    private UserPostLikeService userPostLikeService;

    @PostMapping("/{postId}/like")
    public void clickPostLike(@PathVariable Long postId){
        userPostLikeService.clickPostLike(postId);
    }
}
