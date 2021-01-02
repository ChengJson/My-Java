package com.chengzi;

import com.chengzi.beans.User;
import com.chengzi.mapper.UserMapper;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	RedisClient redisClient;

	@Test
	public void testRedisson(){
		System.out.println(redisClient);
	}

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		userList.forEach(System.out::println);
	}



}
