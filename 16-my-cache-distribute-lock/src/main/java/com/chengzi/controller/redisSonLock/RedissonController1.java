package com.chengzi.controller.redisSonLock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.beans.User;
import com.chengzi.controller.jvmLock.LocalCacheController1;
import com.chengzi.mapper.UserMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * 集成redis Session：https://redis.io/topics/distlock
 *
 */
@RestController
public class RedissonController1 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedissonClient redisson;

    volatile  private int i = 1;

    /**
     *
     * @return
     */
    @RequestMapping("/redisson1")
    public  Object local(){
        //获取一把锁
        RLock lock = redisson.getLock("my-local");
        //1、阻塞式等待，默认的枷锁时间是30s
        //2、锁自动续期，如果业务超长，运行期间自动给锁续上新的30s，不用担心业务时间长，锁自动过期被删掉
        //3、加锁的业务只要运行完成，就不会给当前锁续期，即使不会手动解锁，锁默认会在30s后自动解除
        lock.lock();

        try {
            LOG.info("加锁成功，执行业务。。"+ Thread.currentThread().getId());
            Thread.sleep(30000);
        }catch (Exception e) {

        }finally {
            //解锁
            System.out.println("释放锁。。。。"+ Thread.currentThread().getId());
            lock.unlock();
        }
        return "ok";

    }


}
