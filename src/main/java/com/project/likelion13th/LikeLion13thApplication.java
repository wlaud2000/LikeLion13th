package com.project.likelion13th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LikeLion13thApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeLion13thApplication.class, args);
    }

}
