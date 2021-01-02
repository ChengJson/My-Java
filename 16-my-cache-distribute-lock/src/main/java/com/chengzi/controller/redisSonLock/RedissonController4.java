package com.chengzi.controller.redisSonLock;

import com.chengzi.controller.jvmLock.LocalCacheController1;
import com.chengzi.mapper.UserMapper;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 *
 * 集成redis Session：https://redis.io/topics/distlock
 *
 */
@RestController
public class RedissonController4 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedissonClient redisson;

    volatile  private int i = 1;

    /**
     * 信号量可以用来限流
     */
    @RequestMapping("/park")
    public  Object write() throws InterruptedException {
        //获取一把锁
        RSemaphore park = redisson.getSemaphore("park");

        //阻塞等待
        //park.acquire();
        boolean b = park.tryAcquire();
        String s = null;
        if (b) {
            //执行业务
            return "ok";
        } else {
            return "超限";
        }


    }


    /**
     *
     * @return
     */
    @RequestMapping("/go")
    public  Object read(){
        RSemaphore park = redisson.getSemaphore("park");
        park.release();//释放一个车位
        return "ok";

    }


}
