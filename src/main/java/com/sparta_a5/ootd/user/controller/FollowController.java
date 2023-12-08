package com.sparta_a5.ootd.user.controller;

import com.sparta_a5.ootd.user.dto.follow.FollowResponse;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import com.sparta_a5.ootd.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public void createFollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("userId") Long userId
    ) {
        followService.createFollow(userDetails.getUser(), userId);
    }

    @GetMapping("/{userId}/following/{offset}")
    public ResponseEntity<List<FollowResponse>> readFollowingAll(
            @PathVariable("userId") Long userId,
            @PathVariable("offset") Long offset,
            @RequestParam("direction") String direction
    ) {
        return ResponseEntity.ok(followService.readFollowingAll(userId, direction, offset));
    }

    @GetMapping("/{userId}/follower/{offset}")
    public ResponseEntity<List<FollowResponse>> readFollowerAll(
            @PathVariable("userId") Long userId,
            @PathVariable("offset") Long offset,
            @RequestParam("direction") String direction
    ) {
        return ResponseEntity.ok(followService.readFollowerAll(userId, direction, offset));
    }

    @DeleteMapping("/{userId}/follow")
    public void deleteFollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("userId") Long userId
    ) {
        followService.deleteFollow(userDetails.getUser(), userId);
    }
}
