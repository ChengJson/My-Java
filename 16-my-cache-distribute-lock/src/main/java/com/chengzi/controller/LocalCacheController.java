package com.chengzi.controller;

import com.chengzi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalCacheController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/local")
    public Object local(){
        redisTemplate.opsForValue().set("user","123");
        return userMapper.selectById("1");
    }


}
