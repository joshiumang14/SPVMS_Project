package com.project.spvms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class SpvmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpvmsApplication.class, args);
    }

}
