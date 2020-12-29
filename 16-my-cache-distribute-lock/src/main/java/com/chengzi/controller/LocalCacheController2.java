package com.chengzi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class LocalCacheController2 {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     * 高并发下的缓存失效
     * 1、缓存穿透，缓存没有，库里也没有，没有将null写入缓存，导致大量请求查库。 解决：null结果缓存。并加入短暂过期时间
     * 2、缓存雪崩：大量key同时过期，解决：过期时间设为随机值
     * 3、缓存击穿：热点数据被大量访问，正好失效，解决：大量并发让一个线程去查，其他线程等待，查到以后释放锁，其他线程获取到锁，先查缓存
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
