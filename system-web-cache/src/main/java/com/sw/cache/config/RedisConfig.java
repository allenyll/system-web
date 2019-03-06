package com.sw.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.cache.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 配置
 * @Author: yu.leilei
 * @Date: 下午 5:25 2018/5/28 0028
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    /**
     * 获取JedisPoolConfig配置
     * @return
     */
    @Bean("redisConfigs")
    @ConfigurationProperties(value = "${spring.redis.pool}")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig redisConfig = new JedisPoolConfig();
        redisConfig.setMaxIdle(redisProperties.getMaxIdle());
        redisConfig.setMinIdle(redisProperties.getMinIdle());
        redisConfig.setMaxTotal(redisProperties.getMaxActive());
        redisConfig.setMaxWaitMillis(redisProperties.getMaxWait());
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
    /*@Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?, ?> redisTemplate = new StringRedisTemplate(getConnectionFactory());
        return redisTemplate;
    }*/

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionFactory());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
