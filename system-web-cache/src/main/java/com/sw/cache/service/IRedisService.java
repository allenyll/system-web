package com.sw.cache.service;

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

}
