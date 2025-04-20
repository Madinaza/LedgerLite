package com.ledgerlite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling

@SpringBootApplication
public class Main {   // ← no more 'final' here

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
