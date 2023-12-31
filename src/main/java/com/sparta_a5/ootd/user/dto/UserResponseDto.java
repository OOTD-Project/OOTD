package com.sparta_a5.ootd.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta_a5.ootd.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String imageURL;
    private String intro;
    private int age;
    private int height;
    private int weight;

    @Builder
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.intro = user.getIntro();
        this.age = user.getAge();
        this.height = user.getHeight();
        this.weight = user.getWeight();
    }

    public UserResponseDto(User user, String imageURL) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.imageURL = imageURL;
        this.intro = user.getIntro();
        this.age = user.getAge();
        this.height = user.getHeight();
        this.weight = user.getWeight();
    }
}
