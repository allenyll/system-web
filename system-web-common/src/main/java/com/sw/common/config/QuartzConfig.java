package com.sw.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sw.common.factory.JobFactory;
import com.sw.ds.datasource.DruidProperties;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description:  Quartz 配置
 * @Author:       allenyll
 * @Date:         2019/3/17 10:01 AM
 * @Version:      1.0
 */
@Configuration
public class QuartzConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    private JobFactory jobFactory;

    /**
     * 指定quartz.properties
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));

        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 配置Scheduler工厂
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(jobFactory);
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        factoryBean.setDataSource(dataSource);
        factoryBean.setOverwriteExistingJobs(true);
        try {
            factoryBean.setQuartzProperties(quartzProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  factoryBean;
    }

    /**
     * 注入Scheduler
     * @return
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}





