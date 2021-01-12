package com.fantaike.template.service.nosql;

import org.apache.dubbo.config.annotation.Service;
import com.fantaike.template.dto.nosql.RedisService;
import com.fantaike.template.util.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisServiceImpl
 * @Description: redis 服务实现类
 * @Author: wuguizhen
 * @Date: 2019/7/13 19:45
 * @Version: v1.0 文件初始创建
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    
    /** 默认超时时间 */
    @Value("${spring.redis.expireTime}")
    private long defaultExpireTime;

    /**
     * @Description: 写入缓存
     * @param key 键
     * @param value 值
     * @Date: 2019/7/13 19:01
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @Override
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 写入缓存设置时效时间
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间 单位为秒
     * @Date: 2019/7/13 19:01
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 列表添加
     * @param key 键
     * @param value 值
     * @Date: 2019/7/13 19:02
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void listPush(String key, Object value) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key, value);
    }

    /**
     * @Description: 列表获取
     * @param k
     * @param l
     * @param l1
     * @Date: 2019/7/13 19:09
     * @Author: wuguizhen
     * @Return java.util.List<java.lang.Object>
     * @Throws
     */
    @Override
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * @Description: 集合添加
     * @param key 键
     * @param value 值
     * @Date: 2019/7/13 19:07
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * @Description: 集合获取
     * @param key 键
     * @Date: 2019/7/13 19:08
     * @Author: wuguizhen
     * @Return java.util.Set<java.lang.Object>
     * @Throws
     */
    @Override
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * @Description: 有序集合添加
     * @param key
     * @param value
     * @param scoure
     * @Date: 2019/7/13 19:08
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * @Description: 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @Date: 2019/7/13 19:08
     * @Author: wuguizhen
     * @Return java.util.Set<java.lang.Object>
     * @Throws
     */
    @Override
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * @Description: 判断缓存中是否有对应的value
     * @param key
     * @Date: 2019/7/13 19:10
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @Description: 读取缓存
     * @param key
     * @Date: 2019/7/13 19:10
     * @Author: wuguizhen
     * @Return java.lang.Object
     * @Throws
     */
    @Override
    public Object get(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * @Description: 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     * @Date: 2019/7/13 19:12
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value) {
        //若没有传进时间相关的参数，则过期时间为如下。默认单位为秒，时间从配置文件中读取
        hmSet(key, hashKey, value, defaultExpireTime, TimeUnit.SECONDS);
    }

    /**
     * @Description: 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     * @param expireTime 过期时间 单位为秒
     * @Date: 2019/7/13 19:13
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value, long expireTime) {
        hmSet(key, hashKey, value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * @Description: 哈希添加，自主设置过期时间及时间单位
     * @param key
     * @param hashKey
     * @param value
     * @param expireTime
     * @param timeUnit
     * @Date: 2019/7/13 19:13
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value, long expireTime, TimeUnit timeUnit) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
        redisTemplate.expire(key, expireTime, timeUnit);
    }

    /**
     * @Description: 哈希获取数据
     * @param key
     * @param hashKey
     * @Date: 2019/7/13 19:23
     * @Author: wuguizhen
     * @Return java.lang.Object
     * @Throws
     */
    @Override
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * @Description: 获取hash 的所有key
     * @param key
     * @Date: 2019/7/13 19:23
     * @Author: wuguizhen
     * @Return java.util.Set<java.lang.Object>
     * @Throws
     */
    @Override
    public Set<Object> hmKeySet(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.keys(key);
    }

    /**
     * @Description: 批量添加
     * @param mainKey
     * @param map
     * @Date: 2019/7/13 19:24
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmPutAll(String mainKey, Map<String, Object> map) {
        hmPutAll(mainKey, map, defaultExpireTime);
    }

    /**
     * @Description: 哈希批量添加,自主设置过期时间, 单位默认为秒
     * @param mainKey
     * @param map
     * @param expireTime
     * @Date: 2019/7/13 19:24
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmPutAll(String mainKey, Map<String, Object> map, long expireTime) {
        hmPutAll(mainKey, map, expireTime, TimeUnit.SECONDS);
    }

    /**
     * @Description: 哈希批量添加,自主设置过期时间,及时间单位
     * @param mainKey
     * @param map
     * @param expireTime
     * @param timeUnit
     * @Date: 2019/7/13 19:24
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    @Override
    public void hmPutAll(String mainKey, Map<String, Object> map, long expireTime, TimeUnit timeUnit) {
        BoundHashOperations<String, String, Object> boundHashOperations = redisTemplate.boundHashOps(mainKey);
        boundHashOperations.putAll(map);
        redisTemplate.expire(mainKey, expireTime, timeUnit);
    }

    /**
     * @Description: 获取hash 数据类型的 全部数据
     * @param mainKey
     * @Date: 2019/7/13 19:25
     * @Author: wuguizhen
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Throws
     */
    @Override
    public Map<String, Object> hmGetAll(String mainKey) {
        BoundHashOperations<String, String, Object> boundHashOperations = redisTemplate.boundHashOps(mainKey);
        return boundHashOperations.entries();
    }

    /**
     * @Description: 查询hash中是否包含某个key
     * @param mainKey
     * @param key
     * @Date: 2019/7/13 19:25
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
	@Override
	public boolean hmHasKey(String mainKey, String key) {
		 BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(mainKey);
		return boundHashOperations.hasKey(key);
	}

    /**
     * @Description: 获取锁 主要通过redis setNx方式实现，setNx已经实现设置缓存和过期时间原子操作了，超时时间可以设置的相对大一点，以防止获取锁之后的程序未执行完，锁就失效
     * @param key
     * @param value
     * @param expireTime 超时时间 单位是秒
     * @Date: 2019/7/15 19:13
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @Override
    public boolean getLock(String key, Object value,Long expireTime) {
        //对应setnx命令
        if(redisTemplate.opsForValue().setIfAbsent(key,value,expireTime,TimeUnit.SECONDS)){
            //可以成功设置,也就是key不存在
            return true;
        }
        return false;
    }

    /**
     * @Description: 释放锁
     * @param lockName
     * @Date: 2019/7/15 17:54
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    @Override
    public void releaseLock(String lockName) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(lockName);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(lockName) ){
                //删除key
                redisTemplate.opsForValue().getOperations().delete(lockName);
            }
        } catch (Exception e) {
            CommonLogger.error(lockName,"redis","[Redis分布式锁] 解锁出现异常了",e);
        }
    }

}