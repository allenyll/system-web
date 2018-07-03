package com.sw.ds.multidatasource.annotion;

import java.lang.annotation.*;

/**
 * 多数据源切换标识
 * @Author: yu.leilei
 * @Date: 下午 4:37 2018/2/26 0026
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {
    String value() default "";
}
