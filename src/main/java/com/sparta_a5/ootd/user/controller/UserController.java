package com.sparta_a5.ootd.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta_a5.ootd.CommonResponseDTO;
import com.sparta_a5.ootd.user.dto.*;
import com.sparta_a5.ootd.common.configuration.JwtUtil;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import com.sparta_a5.ootd.user.service.KakaoService;
import com.sparta_a5.ootd.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signup(userRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDTO("회원가입을 환영합니다.", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto, response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO("로그인에 실패하였습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new CommonResponseDTO("로그인에 성공하였습니다.", HttpStatus.OK.value()));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<CommonResponseDTO> updateUser(@RequestBody UpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateUser(updateRequestDto,  userDetails);
        return ResponseEntity.ok().body(new CommonResponseDTO("프로필 수정을 성공하였습니다.", HttpStatus.BAD_REQUEST.value()));
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
