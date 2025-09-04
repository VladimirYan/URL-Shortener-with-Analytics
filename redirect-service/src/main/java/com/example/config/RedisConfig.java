package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(org.springframework.core.env.Environment env) {
        String host = env.getProperty("spring.data.redis.host", "localhost");
        int port = Integer.parseInt(env.getProperty("spring.data.redis.port", "6379"));
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(conf);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
