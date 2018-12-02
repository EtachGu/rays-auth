package com.client2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author: Gu danpeng
 * @data: 2018-12-2
 * @version：1.0
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SpringApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}