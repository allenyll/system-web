package com.sw.mq.factory;

/**
 * 通知工厂具体实现
 * @Author: yu.leilei
 * @Date: 下午 2:42 2018/6/19 0019
 */
public class NotificationFactory implements INotificationFactory{
    @Override
    public INotification getNotification(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        INotification notification = (INotification) clazz.newInstance();
        return notification;
    }
}
