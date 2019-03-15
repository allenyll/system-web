package com.sw.mq.factory;


import com.sw.cache.util.DataResponse;

import java.util.Map;

/**
 * 通知接口，
 *     可以派生出不同的通知类型
 * @Author: yu.leilei
 * @Date: 下午 1:33 2018/6/19 0019
 */
public interface INotification {

    /**
     * 发送消息
     * @param params
     * @return
     */
    DataResponse sendNotification(Map<String, Object> params);

    /**
     * 获取参数配置
     * @return DataResponse
     *     自定义返回类型
     */
    DataResponse getNotificationConfig();

    /**
     * 获取通知模板配置
     * @return
     */
    DataResponse getNotificationTemplate();
}
