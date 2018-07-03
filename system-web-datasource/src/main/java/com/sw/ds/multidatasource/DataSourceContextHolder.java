package com.sw.ds.multidatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源上下文
 * @Author: yu.leilei
 * @Date: 下午 5:13 2018/2/26 0026
 */
public class DataSourceContextHolder {

    private static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "system_web";

    /**
     * 设置数据源类型
     *
     * @param dataSourceType 数据库类型
     */
    public static void setDataSourceType(String dataSourceType) {
        logger.info("设置数据源为："+dataSourceType);
        contextHolder.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
