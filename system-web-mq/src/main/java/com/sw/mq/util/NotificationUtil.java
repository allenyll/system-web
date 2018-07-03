package com.sw.mq.util;

import com.sw.mq.constants.EmailConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息工具
 * @Author: yu.leilei
 * @Date: 下午 3:49 2018/6/19 0019
 */
public class NotificationUtil {

    /**
     * 判断邮箱是否是合法的
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断邮箱类型
     * @param emailFrom
     * @return
     */
    public static String getEmailType(String emailFrom) {
        int indexBegin = emailFrom.indexOf("@");
        int indexEnd = emailFrom.indexOf(".com");
        String emailType = emailFrom.substring(indexBegin+1, indexEnd);
        String emailHost="";
        if(EmailConstants.EmialType.QQ.equals(emailType)){
            emailHost = EmailConstants.EmailConfig.QQ_MAIL_HOST;
        }else if(EmailConstants.EmialType.OST.equals(emailType)){
            emailHost = EmailConstants.EmailConfig.OST_MAIL_HOST;
        }
        return emailHost;
    }

    public static void main(String[] args){
        String emailFrom = "806141743@qq.com";

        int indexBegin = emailFrom.indexOf("@");
        int indexEnd = emailFrom.indexOf(".com");
        System.out.println(emailFrom.substring(indexBegin+1, indexEnd));
    }

}
