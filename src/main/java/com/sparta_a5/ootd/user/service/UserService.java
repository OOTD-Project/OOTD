package com.sparta_a5.ootd.user.service;

import com.sparta_a5.ootd.common.configuration.JwtUtil;
import com.sparta_a5.ootd.user.dto.*;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.entity.UserRoleEnum;
import com.sparta_a5.ootd.user.repository.UserRepository;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final JwtUtil jwtUtil;

    @Transactional
        public void signup(UserRequestDto userRequestDto) {
            String username = userRequestDto.getUsername();
            String password = passwordEncoder.encode(userRequestDto.getPassword());
            String email = userRequestDto.getEmail();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
            UserRoleEnum role = UserRoleEnum.USER;
            /* 관리자를 부여하는 조건?*/
            User user = new User(username, password, email, role);
            userRepository.save(user);
        }

    @Transactional
        public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new IllegalArgumentException("등록된 유저가 없습니다."));
            System.out.println(password);
            System.out.println(user.getPassword());

            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        }


        /*public void logout(User user) {

        }*/

        @Transactional // 유저 조회
        public UserResponseDto getUserByUsername(String username) {
            return new UserResponseDto(getUsername(username));
        }

        @Transactional
        public UserResponseDto updateUser(UpdateRequestDto updateRequestDto, UserDetailsImpl userDetails) {
            User user = getUsername(updateRequestDto.getUsername());

            user.setUsername(updateRequestDto.getUsername());
            user.setEmail(updateRequestDto.getEmail());

            if (!updateRequestDto.getCheckPassword().equals(updateRequestDto.getPassword())){
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }user.setPassword(passwordEncoder.encode(updateRequestDto.getPassword()));

            user.setIntro(updateRequestDto.getIntro());
            user.setAge(updateRequestDto.getAge());
            user.setHeight(updateRequestDto.getHeight());
            user.setWeight(updateRequestDto.getWeight());

            return new UserResponseDto(user);
        }

        private User getUsername(String username) { // id로 유저찾기 메서드
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        }

}

