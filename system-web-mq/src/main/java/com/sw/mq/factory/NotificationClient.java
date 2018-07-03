package com.sw.mq.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知客户端
 * @Author: yu.leilei
 * @Date: 下午 1:44 2018/6/19 0019
 */
public class NotificationClient {

    public static void main(String[] args){
        Map<String, Object> params = new HashMap<>(5);

        NotificationFactory factory = new NotificationFactory();

        INotification notification = null;

        {
            try {
                notification = factory.getNotification(Email.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        notification.sendNotification(params);
    }

}
