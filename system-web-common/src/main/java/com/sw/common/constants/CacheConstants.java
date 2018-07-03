package com.sw.common.constants;

/**
 * 缓存相关常量类
 * @Author: yu.leilei
 * @Date: 上午 9:44 2018/3/9 0009
 */
public final class CacheConstants {

    public CacheConstants(){}

    /**
     * session 失效时间（默认为30分钟 单位：毫秒）
     */
    public static final Integer SESSION_INVALIDATE_TIME = 1800000;

    /**
     * session 验证失效时间（默认为15分钟 单位：毫秒）
     */
    public static final Integer SESSION_VALIDATION_INTERVAL = 900000;

    /**
     * 缓存类型
     */
    public static final String CACHE_TYPE = "redis";
}
