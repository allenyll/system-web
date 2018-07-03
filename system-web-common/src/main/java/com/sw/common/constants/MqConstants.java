package com.sw.common.constants;

/**
 * 消息对列 常量
 * @Author: yu.leilei
 * @Date: 下午 1:14 2018/3/6 0006
 */
public final class MqConstants {

    public MqConstants(){}

    /**
     * 默认交换机名称 exchangeName
     */
    public static final String DEFAULT_EXCHANGE = "MYMQ";

    /**
     * DLX 队列
     */
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "mymq.dead.letter.queue";

    /**
     * DLX repeat QUEUE 死信转发队列
     */
    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "mymq.repeat.trade.queue";

    /**
     * Hello 测试消息队列名称
     */
    public static final String HELLO_QUEUE_NAME = "HELLO";
}
