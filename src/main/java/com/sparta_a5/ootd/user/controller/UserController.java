package com.sparta_a5.ootd.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta_a5.ootd.user.dto.*;
import com.sparta_a5.ootd.common.configuration.JwtUtil;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import com.sparta_a5.ootd.user.service.KakaoService;
import com.sparta_a5.ootd.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;
 //   private final JwtUtil jwtUtil;
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.signup(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UserResponseDto userResponseDto = userService.login(loginRequestDto, response);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto userResponseDto = userService.updateUser(updateRequestDto,  userDetails);
        return ResponseEntity.ok(userResponseDto);
    }

  @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }


}
