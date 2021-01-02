package com.chengzi.controller.redisSonLock;

import com.chengzi.controller.jvmLock.LocalCacheController1;
import com.chengzi.mapper.UserMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 *
 * 集成redis Session：https://redis.io/topics/distlock
 *
 */
@RestController
public class RedissonController2 {

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
    @RequestMapping("/redisson2")
    public  Object local(){
        //获取一把锁
        RLock lock = redisson.getLock("my-local");
        /**
         *
         * 10秒后自动解锁，无论业务是否执行完，如果业务没有执行完
         * 锁自动释放，另一个线程执行业务中，第一个线程删锁的时候
         * 会把第二个线程的锁删掉，会报错，删的不是自己的锁，
         * 锁的过期时间一定要大雨业务的执行时间
         *
         * 1、如果我们传递了锁的超时时间，就会给redis发送执行脚本，进行占锁，默认的超时时间就是我们指定的时间
         * 2、如果没有指定长超时时间，就会使用30*1000【lockWatchdogTimeout看门狗的默认超时时间】
         * 只要占锁成功，就会启动一个定时任务【重新给锁设置过期时间，新的过期时间就是看门狗的默认时间】，每隔10秒都睡自动再次续期
         *   看门狗时间/3  ，10s
         *
         * 最佳实践：设置锁的过期时间，省掉了自动续期的过程，业务执行完手动解锁
         *
         *
         */
        lock.lock(10, TimeUnit.SECONDS);

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
