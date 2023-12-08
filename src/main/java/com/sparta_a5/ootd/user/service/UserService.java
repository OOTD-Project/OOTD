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
           String email = userRequestDto.getEmail();
           String password = passwordEncoder.encode(userRequestDto.getPassword());


        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
            UserRoleEnum role = UserRoleEnum.USER;

            User user = new User(username, email, password, role);
            userRepository.save(user);
        }

    @Transactional
        public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
            String email = loginRequestDto.getEmail();
            String password = loginRequestDto.getPassword();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new IllegalArgumentException("등록된 유저가 없습니다."));
            System.out.println(password);
            System.out.println(user.getPassword());

            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

          jwtUtil.addJwtToCookie(jwtUtil.createToken(user.getUsername(), user.getRole()), response);

        }


    @Transactional // 유저 조회
    public UserResponseDto getUserById(Long userId) {
        return new UserResponseDto(getUser(userId));
    }

        @Transactional
        public UserResponseDto updateUser(UpdateRequestDto updateRequestDto, UserDetailsImpl userDetails) {
            User user = getUser(updateRequestDto.getId());

            user.setUsername(updateRequestDto.getUsername());

            if (!updateRequestDto.getCheckPassword().equals(updateRequestDto.getPassword())){
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }user.setPassword(passwordEncoder.encode(updateRequestDto.getPassword()));

            user.setIntro(updateRequestDto.getIntro());
            user.setAge(updateRequestDto.getAge());
            user.setHeight(updateRequestDto.getHeight());
            user.setWeight(updateRequestDto.getWeight());

            return new UserResponseDto(user);
        }

    private User getUser(Long id) { // id로 유저찾기 메서드
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

}

