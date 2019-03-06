package com.sw.cache.service.impl;

import com.alibaba.fastjson.JSON;
import com.sw.cache.domain.RedisDo;
import com.sw.cache.service.IRedisService;
import com.sw.cache.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作方法实现类
 * @Author: yu.leilei
 * @Date: 下午 5:40 2018/5/28 0028
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 一周有多少秒
     */
    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

    @Autowired
    RedisDo redisDo;

    private Jedis jedis;

    /**
     * 获取操作字符串的ValueOperations
     */
    public ValueOperations<String, Object> getValueOperations(){
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        return vo;
    }

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    @Override
    public String get(final String key) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        return result;
    }

    @Override
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String key) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.del(key.getBytes());
            return true;
        });
        return result;
    }

    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = JSON.toJSONString(list);
        return set(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        String json = get(key);
        if(json != null){
            List<T> list = JSON.parseArray(json, clazz);
            return list;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllCache() {
        redisDo.open();
        jedis = redisDo.jedis;
        // 获取所有key
        Set<String> keySet = jedis.keys("*");
        Iterator it = keySet.iterator();
        List<Map<String, Object>> list = new ArrayList<>();

        while (it.hasNext()){
            Map<String, Object> map = new HashMap<>();
            String key = (String) it.next();
            if(key != null && key != ""){
                map.put("key", key);
                map.put("value", jedis.get(key));
                list.add(map);
            }
        }
        redisDo.close();
        return list;
    }

    @Override
    public void removeAll() {
        redisDo.open();
        jedis = redisDo.jedis;
        // 获取所有key
        Set<String> keySet = jedis.keys("*");
        redisTemplate.delete(keySet);
        redisDo.close();
    }

    /**
     * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, Object value) {
        getValueOperations().set(key, JsonUtil.convertObj2String(value), WEEK_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String key, Object value, long expireTime) {
        getValueOperations().set(key, JsonUtil.convertObj2String(value), expireTime, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取与 key 对应的对象
     * @param key
     * @param clazz 目标对象类型
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        String s = getKey(key);
        if (s == null) {
            return null;
        }
        return JsonUtil.convertString2Obj(s, clazz);
    }

    /**
     * 获取 key 对应的字符串
     * @param key
     * @return
     */
    public String getKey(String key) {
        return (String) getValueOperations().get(key);
    }

    public static void main(String[] args) {

    }

}
