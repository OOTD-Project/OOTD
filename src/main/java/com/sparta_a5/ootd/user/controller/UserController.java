package com.sparta_a5.ootd.user.controller;

import com.sparta_a5.ootd.user.dto.*;
import com.sparta_a5.ootd.user.jwt.JwtUtil;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import com.sparta_a5.ootd.user.service.UserService;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signup(userRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입을 환영합니다.", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인에 실패하였습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인에 성공하였습니다.", HttpStatus.OK.value()));
    }

    @GetMapping("/profile/{id}") // id로 유저 조회
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<CommonResponseDto> updateUser(@RequestBody UpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateUser(updateRequestDto,  userDetails);
        return ResponseEntity.ok().body(new CommonResponseDto("프로필 수정을 성공하였습니다.", HttpStatus.BAD_REQUEST.value()));
    }


}
