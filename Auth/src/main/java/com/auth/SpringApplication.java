package com.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}