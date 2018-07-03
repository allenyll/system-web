package com.sw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @Author: yu.leilei
 * @Date: 下午 9:47 2018/5/24 0024
 */
@SpringBootApplication
@Component("com.sw")
@EnableWebMvc
public class SwApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwApplication.class, args);
	}
}
