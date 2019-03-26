package com.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@EnableCaching
@SpringBootApplication
public class RaysAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaysAuthApplication.class, args);
    }
}