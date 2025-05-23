package com.example.gemfirestarter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.model.Portfolio;

/**
 * Redis Configuration
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.example.gemfirestarter.repository")
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, TradeTransaction> tradeTransactionRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, TradeTransaction> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(TradeTransaction.class));
        return template;
    }
    
    @Bean
    public RedisTemplate<String, Stock> stockRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Stock> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Stock.class));
        return template;
    }
    
    @Bean
    public RedisTemplate<String, Portfolio> portfolioRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Portfolio> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Portfolio.class));
        return template;
    }
}