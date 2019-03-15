package com.sw.mq.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sw.cache.util.DataResponse;
import com.sw.mq.constants.EmailConstants;
import com.sw.mq.util.NotificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 具体的通知类，邮件
 * @Author: yu.leilei
 * @Date: 下午 1:35 2018/6/19 0019
 */
public class Email implements INotification {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public DataResponse sendNotification(Map<String, Object> params) {
        DataResponse dataResponse = this.getNotificationConfig();
        String emailFrom = (String) dataResponse.get("EMAIL_FROM");
        if(NotificationUtil.isEmail(emailFrom)){
            logger.info("发送方邮箱不合法");
            return DataResponse.fail("发送方邮箱不合法，请确认!");
        }
        // 判断邮箱类型, 直接返回邮箱服务器地址
        String emailHost = NotificationUtil.getEmailType(emailFrom);
        String emailTo = (String) dataResponse.get("EMAIL_TO");
        String password = (String) dataResponse.get("PASSWORD");
        boolean auth = (boolean) dataResponse.get("AUTH");
        boolean startTls = (boolean) dataResponse.get("START_TLS");
        int timeout = (int) dataResponse.get("TIMEOUT");
        String subject = "";
        String content = "";
        System.out.println(emailFrom+":"+emailTo);

        // 邮件发送实现类，需要自定义参数，以用来对应不同的邮箱类型
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        if(EmailConstants.EmailConfig.QQ_MAIL_HOST.equals(emailHost)){
            mailSender.setPort(EmailConstants.EmailConfig.QQ_MAIL_PORT);
        }
        mailSender.setUsername(emailFrom);
        mailSender.setPassword(password);

        //加认证机制
        Properties javaMailProperties = new Properties();

        // 是否需要验证
        javaMailProperties.put("mail.smtp.auth", auth);
        // tls方式发送邮件
        javaMailProperties.put("mail.smtp.starttls.enable", startTls);
        // 发送超时时间
        javaMailProperties.put("mail.smtp.timeout", timeout);
        mailSender.setJavaMailProperties(javaMailProperties);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        //设置主题
        mailMessage.setSubject(subject);
        //设置内容
        mailMessage.setText(content);

        try {
            mailSender.send(mailMessage);
            // TODO 记录日志
            logger.info("简单邮件已发送");
            return DataResponse.success();
        } catch (MailException e) {
            logger.error("简单邮件发送异常", e);
            e.printStackTrace();
            return DataResponse.fail("简单邮件发送异常");
        }
    }

    @Override
    public DataResponse getNotificationConfig() {
        String emailFrom = "806141743@qq.com";
        String emailTo = "2673189464@qq.com";
        String password = "hfxzdgdgvsnpbfgi";
        Map<String, Object> params = new HashMap<>(5);
        params.put("EMAIL_FROM", emailFrom);
        params.put("EMAIL_TO", emailTo);
        return DataResponse.success(params);
    }

    @Override
    public DataResponse getNotificationTemplate() {
        return null;
    }

}
