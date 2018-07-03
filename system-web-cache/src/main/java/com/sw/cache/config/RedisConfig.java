package com.sw.cache.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 配置
 * @Author: yu.leilei
 * @Date: 下午 5:25 2018/5/28 0028
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    /**
     * 获取JedisPoolConfig配置
     * @return
     */
    @Bean
    @ConfigurationProperties(value = "${spring.redis.pool}")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig redisConfig = new JedisPoolConfig();
        return redisConfig;
    }

    /**
     * 获取JedisConnectionFactory工厂
     * @return
     */
    @Bean
    @ConfigurationProperties(value = "${spring.redis}")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        factory.setPoolConfig(getRedisConfig());
        return factory;
    }

    /**
     * 获取RedisTemplate模板
     * @return
     */
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?, ?> redisTemplate = new StringRedisTemplate(getConnectionFactory());
        return redisTemplate;
    }
}
