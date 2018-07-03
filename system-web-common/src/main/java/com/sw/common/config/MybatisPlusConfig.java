package com.sw.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sw.ds.datasource.DruidProperties;
import com.sw.ds.enums.DBType;
import com.sw.ds.multidatasource.DynamicDataSource;
import com.sw.ds.multidatasource.config.MultiDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis-plus 插件配置
 * @Author: yu.leilei
 * @Date: 上午 11:15 2018/2/27 0027
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = {"com.sw.base.mapper"})
public class MybatisPlusConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    MultiDataSourceProperties multiDataSourceProperties;

    /**
     * 默认数据源
     * @return
     */
    private DruidDataSource defaultDatasource(){
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 其他数据源
     * @return
     */
    private DruidDataSource otherDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        multiDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "sw", name = "multi-datasource-switch", havingValue = "false")
    public DruidDataSource singleDatasource(){
        return defaultDatasource();
    }

    /**
     * 单数据源配置
     */
    @Bean(name = "dynamicDataSource")
    @ConditionalOnProperty(prefix = "sw", name = "multi-datasource-switch", havingValue = "true")
    public DynamicDataSource multiDatasource(){
        DruidDataSource defaultDatasource = defaultDatasource();
        DruidDataSource otherDatasource = otherDataSource();

        try {
            defaultDatasource.init();
            otherDatasource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> map = new HashMap<>(5);
        map.put(DBType.DEFAULT_DATA_SOURCE, defaultDatasource);
        map.put(DBType.OTHER_DATA_SOURCE, otherDatasource);
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(defaultDatasource);
        return dynamicDataSource;
    }

    /**
     * 配置事务管理器
     */
    /*@Bean
    public DataSourceTransactionManager transactionManager() {
       return new DataSourceTransactionManager(multiDatasource());
    }*/

}
