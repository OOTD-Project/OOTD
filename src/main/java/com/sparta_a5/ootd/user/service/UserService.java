package com.sparta_a5.ootd.user.service;

import com.sparta_a5.ootd.common.configuration.JwtUtil;
import com.sparta_a5.ootd.common.s3.S3Const;
import com.sparta_a5.ootd.common.s3.S3Util;
import com.sparta_a5.ootd.user.dto.LoginRequestDto;
import com.sparta_a5.ootd.user.dto.UpdateRequestDto;
import com.sparta_a5.ootd.user.dto.UserRequestDto;
import com.sparta_a5.ootd.user.dto.UserResponseDto;
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

import static com.sparta_a5.ootd.common.s3.S3Const.S3_DIR_USER_PROFILE;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final JwtUtil jwtUtil;
        private final S3Util s3Util;

    @Transactional
        public UserResponseDto signup(UserRequestDto userRequestDto) {
           String username = userRequestDto.getUsername();
           String email = userRequestDto.getEmail();
           String password = passwordEncoder.encode(userRequestDto.getPassword());
        String filename = userRequestDto.getFilename();

        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
            UserRoleEnum role = UserRoleEnum.USER;

            User user = new User(username, email, password, role, filename);
            userRepository.save(user);
        return new UserResponseDto(user);
        }

    @Transactional
        public UserResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
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
        return new UserResponseDto(user);
        }


    @Transactional // 유저 조회
    public UserResponseDto getUserById(Long userId) {
        User user = getUser(userId);
        String filename = user.getFilename();
        String imageURL = s3Util.getImageURL(S3_DIR_USER_PROFILE,filename); // S3_DIR/USER
        return new UserResponseDto(user, imageURL);
    }

        @Transactional
        public UserResponseDto updateUser(UpdateRequestDto updateRequestDto, UserDetailsImpl userDetails) {
            User user = getUser(updateRequestDto.getId());

            user.setUsername(updateRequestDto.getUsername());

            if (!updateRequestDto.getCheckPassword().equals(updateRequestDto.getPassword())){
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }user.setPassword(passwordEncoder.encode(updateRequestDto.getPassword()));

            String filename = user.getFilename();
            s3Util.deleteImage(S3_DIR_USER_PROFILE,filename);

            String newFilename = s3Util.uploadImage(S3_DIR_USER_PROFILE,updateRequestDto.getImageFile());
            updateRequestDto.setFilename(newFilename);

            user.setIntro(updateRequestDto.getIntro());
            user.setAge(updateRequestDto.getAge());
            user.setHeight(updateRequestDto.getHeight());
            user.setWeight(updateRequestDto.getWeight());

            return new UserResponseDto(user, s3Util.getImageURL(S3Const.S3_DIR_USER_PROFILE,newFilename));
        }

    private User getUser(Long id) { // id로 유저찾기 메서드
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

}

