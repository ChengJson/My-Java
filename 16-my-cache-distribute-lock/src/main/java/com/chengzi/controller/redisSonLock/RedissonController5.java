package com.chengzi.controller.redisSonLock;

import com.chengzi.controller.jvmLock.LocalCacheController1;
import com.chengzi.mapper.UserMapper;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 集成redis Session：https://redis.io/topics/distlock
 *
 */
@RestController
public class RedissonController5 {

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
    @RequestMapping("/lockDoor")
    public  Object write() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();//等待
        return "所有线程执行结束";


    }


    /**
     *
     * @return
     */
    @RequestMapping("/go/${id}")
    public  Object read(@PathVariable("id") long id){
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();
        return "";

    }


}
