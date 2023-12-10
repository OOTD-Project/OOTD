package com.sparta_a5.ootd.user.entity;


import com.sparta_a5.ootd.post.timestamped.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 ID값 적용

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String intro;

    @Column
    private int age;

    @Column
    private int height;

    @Column
    private int weight;

    @Column
    private String filename;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private Long socialId;

    public User(String username, String email , String password, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRoleEnum.USER;

    }

    public User(String username, String email, String password, UserRoleEnum role, Long socialId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.socialId =socialId;
    }

    public User(String username, String email , String password, UserRoleEnum role, String filename) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRoleEnum.USER;
        this.filename = "default_image.png";
    }

    public User socialIdUpdate(Long socialId) {
        this.socialId = socialId;
        return this;
    }


    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }
    public void setIntro(String intro) { this.intro = intro; }
    public void setAge(int age) { this.age = age; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }

    public void setUserRole(UserRoleEnum userRole){
        this.role = userRole;
    }

}
