package com.sparta_a5.ootd.admin.controller;

import com.sparta_a5.ootd.admin.dto.AdminUpdateRequestDto;
import com.sparta_a5.ootd.admin.dto.SearchRequestDto;
import com.sparta_a5.ootd.admin.service.AdminService;
import com.sparta_a5.ootd.post.dto.PostResponseDto;
import com.sparta_a5.ootd.user.dto.LoginRequestDto;
import com.sparta_a5.ootd.user.dto.SignupRequestDto;
import com.sparta_a5.ootd.user.dto.UserResponseDto;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> adminSignup(@RequestBody SignupRequestDto signupRequestDto){
        return  ResponseEntity.ok(adminService.adminSignup(signupRequestDto));
    }

    @PostMapping("/login")
    public void adminLogin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){

        adminService.adminLogin(loginRequestDto, response);
    }

    @PostMapping("/user/search")
    public ResponseEntity<List<UserResponseDto>> getUserList(@RequestBody SearchRequestDto searchRequestDto){
        return ResponseEntity.ok(adminService.getUserList(searchRequestDto));
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> updateUserRole(@PathVariable Long userId,
                                                         @RequestBody AdminUpdateRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        return ResponseEntity.ok(adminService.updateUserRole(userId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminService.deleteUser(userId, userDetails.getUser());
    }

    @PostMapping("/post/search")
    public ResponseEntity<List<PostResponseDto>> getPostList(@RequestBody SearchRequestDto searchRequestDto){
        return ResponseEntity.ok(adminService.getPostList(searchRequestDto));
    }

    @DeleteMapping("/post/{postId}")
    public void DeletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminService.deletePost(postId, userDetails.getUser());
    }
}
