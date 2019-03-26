package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Gu danpeng
 * @version 1.0
 * @date 2019-3-25
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    LettuceConnectionFactory lettuceConnectionFactory;

    public RedisConnection getRedisConnection(){
        return lettuceConnectionFactory.getConnection();
    }

    @Bean
    public StringRedisTemplate getStringRedisTemplate(){
        StringRedisTemplate redisTemplate = new StringRedisTemplate(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisStringCommands getRedisStringCommands(){
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        return redisConnection.stringCommands();
    }

}
