package com.sw.mq.constants;

/**
 * 邮箱常用配置常量类
 * @Author: yu.leilei
 * @Date: 下午 3:38 2018/6/19 0019
 */
public interface EmailConstants {

    class EmailConfig{

        /**
         * QQ邮箱服务地址
         */
        public static final String QQ_MAIL_HOST = "smtp.qq.com";

        /**
         * QQ邮箱服务地址端口
         */
        public static final int QQ_MAIL_PORT = 587;

        /**
         * 163邮箱服务地址
         */
        public static final String OST_MAIL_HOST = "smtp.163.com";
    }

    class EmialType{

        /**
         * QQ邮箱
         */
        public static final String QQ = "qq";

        /**
         * 163邮箱
         */
        public static final String OST = "163";

    }
}
