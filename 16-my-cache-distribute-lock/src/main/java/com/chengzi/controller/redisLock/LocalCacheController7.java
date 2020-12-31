package com.chengzi.controller.redisLock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengzi.beans.User;
import com.chengzi.controller.jvmLock.LocalCacheController1;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 *  //测试结果
 * 如果每个业务异常30秒，失效时间是10秒，那么每10秒进去一个线程，差不多会有3个线程查库，后边的线程都不会再查库了
 *
 *
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

            LOG.info("缓存中没有，准备获取分布式锁");

            List<Map<String, Object>> fromLocal = getFromLocalWithRedisLock();
            return fromLocal;
        }
        List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {});
        LOG.info("从缓存中获得数据，直接返回");
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
        Boolean success = redisTemplate.opsForValue().setIfAbsent("lock", "i_am_distribute_key",10, TimeUnit.SECONDS);
        if (success) {


            //redisTemplate.expire("lock",30, TimeUnit.SECONDS);

            //加锁成功，执行业务
            List<Map<String, Object>> maps = getFromDbAndCache();
            //释放锁
            redisTemplate.delete("lock");
            return maps;
        } else {
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
            //加锁失败重试,zixuan 休眠100毫秒重试
            return getFromLocalWithRedisLock();
        }

    }

    /**
     * 从数据库中获取数据并放入到redis中
     * @return
     */
    private List<Map<String, Object>> getFromDbAndCache() {



        //拿到锁对象后还要再次判断缓存是否存在
        String all_user = redisTemplate.opsForValue().get("all_user");
        if (!StringUtils.isEmpty(all_user)) {
            List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {});
            return maps;
        }

        //模拟业务处理超时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        LOG.info("从数据库中查询数据");
        // 根据 Wrapper 条件，查询全部记录
        List<Map<String, Object>> maps = userMapper.selectMaps(new QueryWrapper<User>());


        //将值放入redis中
        String s = JSON.toJSONString(maps);
        redisTemplate.opsForValue().set("all_user",s);

        return maps;
    }

    /**
     *
     * 分布式锁测试：启动nginx，配置负载均衡：
     * location /balance {
            proxy_pass http://www.mybalance.com;
     * }
     *
     * http模块配置：
     * upstream www.mybalance.com {
     *
     *            server  127.0.0.1:9100;
     *           server  127.0.0.1:9200;
     *        }
     *
     *
     *
     * @return
     */
    @RequestMapping("/balance")
    public String balance(){
        LOG.info("运行。。。。。");
        return "ok";
    }


}
