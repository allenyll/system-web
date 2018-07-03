package com.sw.ds.multidatasource.aop;

import com.sw.ds.multidatasource.DataSourceContextHolder;
import com.sw.ds.multidatasource.annotion.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源切换的aop
 * @Author: yu.leilei
 * @Date: 下午 5:18 2018/2/26 0026
 */
@Aspect
@Component
public class MultiDataSourceAop implements Ordered{

    Logger logger = LoggerFactory.getLogger(MultiDataSourceAop.class);

    @Pointcut(value = "@annotation(com.sw.ds.multidatasource.annotion.DataSource)")
    private void point(){}

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint){
        Signature signature  = joinPoint.getSignature();
        MethodSignature methodSignature = null;
        if(!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException("该注解只能用于方法!");
        }
        methodSignature = (MethodSignature) signature;

        //获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = methodSignature.getParameterTypes();

        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        logger.info("当前数据源为：" + dataSource);
        try {
            Method method = className.getMethod(methodName, argClass);

            // @Datasource 注解
            boolean flag = method.isAnnotationPresent(DataSource.class);
            // 存在注解，切换数据源
            if(flag){
                DataSource annotation = method.getAnnotation(DataSource.class);
                // 将当前数据源修改
                dataSource = annotation.value();
                logger.info("设置数据源为：" + dataSource);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDataSourceType(dataSource);

        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            logger.info("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }

        return object;
    }

   /*@After("@annotation(com.sw.core.multidatasource.annotion.DataSource)")
    public void after(JoinPoint joinPoint){
        logger.info("清空数据源信息！");
        DataSourceContextHolder.clearDataSourceType();
    }*/

    @Override
    public int getOrder() {
        return 1;
    }
}
