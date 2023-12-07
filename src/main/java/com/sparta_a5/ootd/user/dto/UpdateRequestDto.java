package com.sparta_a5.ootd.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
 //   private Long id;
    private String username;
    private String password;
    private String checkPassword;
    private String email;
    private String intro;
    private int age;
    private int height;
    private int weight;
}
