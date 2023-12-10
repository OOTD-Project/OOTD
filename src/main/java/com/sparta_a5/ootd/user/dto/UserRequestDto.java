package com.sparta_a5.ootd.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRequestDto {
    @Email
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}")
    private String password;

    private MultipartFile imageFile;
    private String filename;

    private String username;

    private String intro;

    private int age;

    private int height;

    private int weight;

}
