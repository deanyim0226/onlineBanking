package com.example.onlinebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
public class OnlineBankingApplication {

    public static void main(String[] args) {

        System.out.println(Base64.getEncoder().encodeToString("1234".getBytes()));

        SpringApplication.run(OnlineBankingApplication.class, args);
    }

}
