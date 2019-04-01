package com.sw.file.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:  Fdfs配置
 * @Author:       allenyll
 * @Date:         2019/3/25 5:08 PM
 * @Version:      1.0
 */
@Data
@Component
public class FdfsConfig {

    @Value("${fdfs.resHost}")
    private String resHost;

    @Value("${fdfs.storagePort}")
    private String storagePort;

}
