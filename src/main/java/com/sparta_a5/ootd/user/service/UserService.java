package com.sparta_a5.ootd.user.service;

import com.sparta_a5.ootd.user.dto.*;
import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.repository.UserRepository;
import com.sparta_a5.ootd.user.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
        private final PasswordEncoder passwordEncoder; // password 암호화를 위해서 Spring Security의 기능중 하나인 PasswordEncoder 사용
        private final UserRepository userRepository;


        public void signup(UserRequestDto userRequestDto) {
            String username = userRequestDto.getUsername();
            String password = passwordEncoder.encode(userRequestDto.getPassword());
            String email = userRequestDto.getEmail();

            if (userRepository.findByUsername(username).isPresent()) {
                throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
            }

            User user = new User(username, password, email);
            userRepository.save(user);
        }

        public void login(LoginRequestDto loginRequestDto) {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new IllegalArgumentException("등록된 유저가 없습니다."));
            System.out.println(password);
            System.out.println(user.getPassword());

            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }

        @Transactional // 유저 조회
        public UserResponseDto getUserById(Long id) {
            return new UserResponseDto(getUser(id));
        }

        @Transactional
        public UserResponseDto updateUser(UpdateRequestDto updateRequestDto, UserDetailsImpl userDetails) {
            User user = getUser(updateRequestDto.getId());

            user.setUsername(updateRequestDto.getUsername());
            user.setIntro(updateRequestDto.getIntro());
            user.setAge(updateRequestDto.getAge());
            user.setHeight(updateRequestDto.getHeight());
            user.setWeight(updateRequestDto.getWeight());

            if (!updateRequestDto.getCheckPassword().equals(updateRequestDto.getPassword())){
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }user.setPassword(passwordEncoder.encode(updateRequestDto.getPassword()));



            return new UserResponseDto(user);
        }

        private User getUser(Long id) { // id로 유저찾기 메서드
            return userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        }

    }

