package com.chengzi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.chengzi.beans.User;
import com.chengzi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LocalCacheController1 {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     * 1。springboot2以后默认使用lettuce作为操作redis的客户端，使用netty进行网络通讯
     * 2、lettuce的bug导致netty OutOfDirectMemoryError堆外内存益处，-Xms300m,netty如果没有指定堆外内存，默认使用-Xms=300m，可以通过-Dio.netty.maxDirectMemory进行设置
     * 3、不能使用-Dio.netty.maxDirectMemory进行设置 解决方案，升级lettuce客户端，2切换使用jedis
     * 4、redisTemplete是spirng再次封装的RedisAutoConfiguration ，import导入的配置类
     * @return
     */
    @RequestMapping("/local")
    public  List<Map<String, Object>> local(){

        String all_user = redisTemplate.opsForValue().get("all_user");
        if (StringUtils.isEmpty(all_user)){
            List<Map<String, Object>> fromLocal = getFromLocal();
            String s = JSON.toJSONString(fromLocal);
            redisTemplate.opsForValue().set("all_user",s);
            return fromLocal;
        }
        List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {
        });
        return maps;
    }

    /**
     * 从数据库中查询
     * @return
     */
    private  List<Map<String, Object>> getFromLocal(){
        // 根据 Wrapper 条件，查询全部记录
        List<Map<String, Object>> maps = userMapper.selectMaps(new QueryWrapper<User>());
        return maps;
    }


}
