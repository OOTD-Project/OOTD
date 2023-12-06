package com.sparta_a5.ootd.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}")
    private String password;

    @Email
    private String email;

    private String intro;

    private int age; // 나이제한고민중

    private int height;

    private int weight;

}
