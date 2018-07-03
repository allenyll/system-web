package com.sw.cache.controller;

import com.sw.cache.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cache 缓存控制器
 * @Author: yu.leilei
 * @Date: 下午 11:33 2018/5/28 0028
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    IRedisService redisService;

    /**
     *  插入字符串
     */
    @RequestMapping("/set")
    public void setString() {
        redisService.set("redis_string_test", "springboot redis test");
    }

    /**
     * 获取字符串
     */
    @RequestMapping("/get")
    public void getString() {
        String result = redisService.get("redis_string_test");
        System.out.println(result);
    }
}
