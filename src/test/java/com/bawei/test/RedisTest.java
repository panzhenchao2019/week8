package com.bawei.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.entity.User;
import com.pzc.common.utils.RandomUtil;
import com.pzc.common.utils.StringUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class RedisTest {
	@Resource
	private RedisTemplate redisTemplate;
	
	
	@Test
	public void testJDK() {
		List<User> user_list = new ArrayList();
		for (int i = 1; i < 5; i++) {
			//随机 姓名
			String name = StringUtil.generateChineseName();
			
			//随机手机号
			String phone = "13" + RandomUtil.randomNumber(9);
			
			User user = new User(i, name, phone);
			
			//存入list
			user_list.add(user);
		}
		
		ListOperations opsForList = redisTemplate.opsForList();
		
		//获取开始时间
		long start = System.currentTimeMillis();
		
		opsForList.leftPush("user_jdk", user_list);

		//获取结束时间
		long end = System.currentTimeMillis();
		
		System.out.println("本次运行时间为"+(end-start)+"毫秒");
		
	}
	
	@Test
	public void testJSON() {
		List<User> user_list = new ArrayList();
		
		for (int i = 1; i <= 5; i++) {
			String name = StringUtil.generateChineseName();
			
			String phone = "13" + RandomUtil.randomNumber(9);
			
			User user = new User(i, name, phone);
			user_list.add(user);

		}
		
		ListOperations opsForList = redisTemplate.opsForList();
		
		long start = System.currentTimeMillis();
		
		opsForList.leftPush("user_json", user_list);
		
		long end = System.currentTimeMillis();
		
		System.out.println("需要时间是："+(end-start));
	}
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testhash() {
		Map<String, User> hashmap = new HashMap<String, User>();
		
		for (int i = 1; i <= 5; i++) {
			String name = StringUtil.generateChineseName();
			
			String phone = "13" + RandomUtil.randomNumber(9);
			
			User user_hash = new User(i, name, phone);
			
			hashmap.put(i+"", user_hash);
		}
		
		HashOperations opsForHash = redisTemplate.opsForHash();
		
		long start = System.currentTimeMillis();
		
		opsForHash.putAll("user_hash_json", hashmap);
		
		long end = System.currentTimeMillis();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
