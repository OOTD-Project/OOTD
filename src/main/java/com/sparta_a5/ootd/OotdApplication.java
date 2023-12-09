package com.sparta_a5.ootd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class OotdApplication {

    public static void main(String[] args) {
        SpringApplication.run(OotdApplication.class, args);
    }

}
