package com.chengzi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.beans.User;
import com.chengzi.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * 分布式锁的演进2：
 *
 * 原因：设置过期时间之前系统挂掉，即设置锁和设置过期时间不是原子操作
 * 解决：设置锁的自动过期，自动释放锁
 *
 */
@RestController
public class LocalCacheController7 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     *
     * @return
     */
    @RequestMapping("/local7")
    public  List<Map<String, Object>> local(){

        String all_user = redisTemplate.opsForValue().get("all_user");
        if (StringUtils.isEmpty(all_user)){

            LOG.info("缓存中没有，从数据库中查询数据");

            List<Map<String, Object>> fromLocal = getFromLocalWithRedisLock();
            return fromLocal;
        }
        List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {});
        return maps;
    }

    /**
     * 使用分布式锁
     * @return
     */
    private  List<Map<String, Object>> getFromLocalWithRedisLock(){

        //占分布式锁，去redis占坑

        /*********************/
        /***原子操作EX，NX*****/
        /*********************/
        Boolean success = redisTemplate.opsForValue().setIfAbsent("lock", "i_am_distribute_key",30, TimeUnit.SECONDS);
        if (success) {


            //redisTemplate.expire("lock",30, TimeUnit.SECONDS);

            //加锁成功，执行业务
            List<Map<String, Object>> maps = getFromDbAndCache();
            //释放锁
            redisTemplate.delete("lock");
            return maps;
        } else {
            //加锁失败重试,zixuan 休眠100毫秒重试
            return getFromLocalWithRedisLock();
        }

    }

    /**
     * 从数据库中获取数据并放入到redis中
     * @return
     */
    private List<Map<String, Object>> getFromDbAndCache() {
        //拿到锁对象后还要再次判断缓存是否存在，否则别的线程拿到锁之后再次
        String all_user = redisTemplate.opsForValue().get("all_user");
        if (!StringUtils.isEmpty(all_user)) {
            List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {});
            return maps;
        }

        LOG.info("从数据库中查询数据");
        // 根据 Wrapper 条件，查询全部记录
        List<Map<String, Object>> maps = userMapper.selectMaps(new QueryWrapper<User>());


        //将值放入redis中
        String s = JSON.toJSONString(maps);
        redisTemplate.opsForValue().set("all_user",s);

        return maps;
    }


}
