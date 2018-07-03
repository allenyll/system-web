package com.sw.mq.factory;

/**
 * 构建通知工厂
 *     通过反射实现
 * @Author: yu.leilei
 * @Date: 下午 1:42 2018/6/19 0019
 */
public interface INotificationFactory {

    INotification getNotification(Class<?> clazz) throws IllegalAccessException, InstantiationException;
}
