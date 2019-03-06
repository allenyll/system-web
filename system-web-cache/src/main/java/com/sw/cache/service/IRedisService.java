package com.sw.cache.service;

import java.util.List;
import java.util.Map;

/**
 * redis 操作方法接口
 * @Author: yu.leilei
 * @Date: 下午 5:39 2018/5/28 0028
 */
public interface IRedisService {

    /**
     * set存数据
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * get获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置有效天数
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 移除数据
     * @param key
     * @return
     */
    boolean remove(String key);

    /**
     * 设置缓存list
     * @param key
     * @param list
     * @param <T>
     * @return
     */
    <T> boolean setList(String key, List<T> list);

    /**
     * 获取缓存list
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 获取所有缓存
     */
    List<Map<String, Object>> getAllCache();

    /**
     * 删除所有缓存
     * @param key
     */
    void removeAll();

    /**
     * 设置缓存为一个obj
     * @param user
     * @param obj
     */
    void set(String user, Object obj);

    /**
     * 获取一个缓存
     * @param key
     * @param clazz
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 判断 key 是否在 redis 数据库中
     *
     * @param key
     * @return
     */
    boolean exists(final String key);
}
