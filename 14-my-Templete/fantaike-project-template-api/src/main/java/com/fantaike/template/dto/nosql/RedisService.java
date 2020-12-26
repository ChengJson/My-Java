package com.fantaike.template.dto.nosql;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisService
 * @Description: redis操作接口
 * @Author: wuguizhen
 * @Date: 2019/7/13 18:57
 * @Version: v1.0 文件初始创建
 */
public interface RedisService {

     /**
      * @Description: 写入缓存
      * @param key 键
      * @param value 值
      * @Date: 2019/7/13 19:01
      * @Author: wuguizhen
      * @Return boolean
      * @Throws
      */
    boolean set(String key, Object value);

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
    boolean set(String key, Object value, Long expireTime);

    /**
     * @Description: 列表添加
     * @param key 键
     * @param value 值
     * @Date: 2019/7/13 19:02
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    void listPush(String key, Object value);

    /**
     * @Description: 集合添加
     * @param key 键
     * @param value 值
     * @Date: 2019/7/13 19:07
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    void add(String key, Object value);

    /**
     * @Description: 集合获取
     * @param key 键
     * @Date: 2019/7/13 19:08
     * @Author: wuguizhen
     * @Return java.util.Set<java.lang.Object>
     * @Throws
     */
    Set<Object> setMembers(String key);

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
    void zAdd(String key, Object value, double scoure);

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
    Set<Object> rangeByScore(String key, double scoure, double scoure1);


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
    List<Object> lRange(String k, long l, long l1);
    

    /**
     * @Description: 判断缓存中是否有对应的value
     * @param key
     * @Date: 2019/7/13 19:10
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
     boolean exists(final String key);

    /**
     * @Description: 读取缓存
     * @param key
     * @Date: 2019/7/13 19:10
     * @Author: wuguizhen
     * @Return java.lang.Object
     * @Throws
     */
     Object get(final String key);

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
     void hmSet(String key, Object hashKey, Object value);

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
     void hmSet(String key, Object hashKey, Object value, long expireTime);

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
     void hmSet(String key, Object hashKey, Object value, long expireTime, TimeUnit timeUnit);
     
    /**
     * @Description: 哈希获取数据
     * @param key
     * @param hashKey
     * @Date: 2019/7/13 19:23
     * @Author: wuguizhen
     * @Return java.lang.Object
     * @Throws
     */
     Object hmGet(String key, Object hashKey);

    /**
     * @Description: 获取hash 的所有key
     * @param key
     * @Date: 2019/7/13 19:23
     * @Author: wuguizhen
     * @Return java.util.Set<java.lang.Object>
     * @Throws
     */
     Set<Object> hmKeySet(String key);


    /**
     * @Description: 批量添加
     * @param mainKey
     * @param map
     * @Date: 2019/7/13 19:24
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
     void hmPutAll(String mainKey, Map<String, Object> map);
    

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
     void hmPutAll(String mainKey, Map<String, Object> map, long expireTime);

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
     void hmPutAll(String mainKey, Map<String, Object> map, long expireTime, TimeUnit timeUnit);
     
    /**
     * @Description: 获取hash 数据类型的 全部数据
     * @param mainKey
     * @Date: 2019/7/13 19:25
     * @Author: wuguizhen
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Throws
     */
     Map<String, Object> hmGetAll(String mainKey);

    /**
     * @Description: 查询hash中是否包含某个key
     * @param mainKey
     * @param key
     * @Date: 2019/7/13 19:25
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
     boolean hmHasKey(String mainKey, String key);
    
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
     boolean getLock(String key,Object value,Long expireTime);
     
     /**
      * @Description: 释放锁
      * @param lockName
      * @Date: 2019/7/15 17:54
      * @Author: wuguizhen
      * @Return boolean
      * @Throws
      */
     void releaseLock(String lockName);
}
