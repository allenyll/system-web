package com.sw.admin;

import com.sw.cache.service.IRedisService;
import com.sw.common.util.SystemUtil;
import com.sw.mq.factory.Email;
import com.sw.mq.factory.INotification;
import com.sw.mq.factory.NotificationFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwApplicationTests {

	@Autowired
	IRedisService redisService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void setString(){
		redisService.set("setKey", "hello world");
	}

	@Test
	public void getString(){
		String str = redisService.get("setKey");
		System.out.println(str);
	}

	@Test
	public void removeString(){
		redisService.remove("key1");
	}

	@Test
	public void testFile(){
		ClassLoader classLoader = getClass().getClassLoader();
		System.out.println(SystemUtil.getClassFile("com.sw.common.constants",classLoader));
	}

	@Test
	public void testEmail(){
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
