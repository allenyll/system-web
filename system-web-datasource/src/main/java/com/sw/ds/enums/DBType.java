package com.sw.ds.enums;

/**
 * 多数据源类型
 * @Author: yu.leilei
 * @Date: 上午 11:10 2018/2/27 0027
 */
public interface DBType {

    String DEFAULT_DATA_SOURCE = "system_web";			//默认数据源

    String OTHER_DATA_SOURCE = "boot_web";			//其他业务的数据源
}
