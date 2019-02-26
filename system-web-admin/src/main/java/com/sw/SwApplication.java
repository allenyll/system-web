package com.sw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @Author: yu.leilei
 * @Date: 下午 9:47 2018/5/24 0024
 */
@SpringBootApplication(scanBasePackages = "com.sw")
@Component("com.sw")
@EnableWebMvc
@EnableTransactionManagement
public class SwApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwApplication.class, args);
	}
}
