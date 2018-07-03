package com.sw.ds.multidatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @Author: yu.leilei
 * @Date: 下午 4:48 2018/2/26 0026
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("当前数据源为{}", DataSourceContextHolder.getDataSourceType());
        return DataSourceContextHolder.getDataSourceType();
    }
}
