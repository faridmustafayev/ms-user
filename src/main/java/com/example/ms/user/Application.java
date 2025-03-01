package com.example.ms.user;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }

}
