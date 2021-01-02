package com.chengzi.controller.redisSonLock;

import com.chengzi.controller.jvmLock.LocalCacheController1;
import com.chengzi.mapper.UserMapper;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * 集成redis Session：https://redis.io/topics/distlock
 *
 */
@RestController
public class RedissonController3 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedissonClient redisson;

    volatile  private int i = 1;

    /**
     *读写锁保证能读取到最新数据，修改期间写锁是一个排他锁（互斥锁），读锁是共享锁（）
     *  读+读 相当于无锁，并发读，只会在redis中记录好，所有当前的读锁 他们都会同时加锁成功
     *  写+读 等待写锁释放
     *  写+写 阻塞方式
     *  读+写 有读锁，写也需要等待
     *  只要有写的存在都必须顺讯执行
     * @return
     */
    @RequestMapping("/write")
    public  Object write(){
        //获取一把锁
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("read-write");

        RLock writeLock = readWriteLock.writeLock();
        String s = null;
        try {
            writeLock.lock();
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writevalue",s);
        }catch (Exception e) {

        }finally {
            //解锁
            System.out.println("释放锁。。。。"+ Thread.currentThread().getId());
            writeLock.unlock();
        }
        return s;

    }


    /**
     *
     * @return
     */
    @RequestMapping("/write")
    public  Object read(){
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("read-write");
        RLock rLock = readWriteLock.readLock();
        rLock.lock();
        String s = "";
        try {
            s = redisTemplate.opsForValue().get("writevalue");
        }catch (Exception e) {

        }finally {
            //解锁
            System.out.println("释放锁。。。。"+ Thread.currentThread().getId());
            rLock.unlock();
        }
        return s;

    }


}
