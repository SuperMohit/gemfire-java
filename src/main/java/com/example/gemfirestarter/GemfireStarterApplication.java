package com.example.gemfirestarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisStarterApplication.class, args);
    }
}