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
 * 分布式锁的演进4：
 *
 * 删除锁不是原子操作，查到了自己的锁，但是返回途中已经过期了，这样还是把别人的锁删了，使用lua脚本删除
 *
 *  测试发现还是锁不住
 *
 */
@RestController
public class LocalCacheController9 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    volatile  private int i = 1;

    /**
     *
     * @return
     */
    @RequestMapping("/local9")
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
        String uuid = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue().setIfAbsent("lock", uuid,3, TimeUnit.SECONDS);
        if (success) {


            //redisTemplate.expire("lock",30, TimeUnit.SECONDS);
            List<Map<String, Object>> maps;
            //加锁成功，执行业务
            try {
                maps = getFromDbAndCache();
            }finally {
                String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]  then   return redis.call(\"del\",KEYS[1])  else return 0 end";
                Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }

            /*************************/
            /***只删除自己持有的锁*****/
            /************************/
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

        //模拟第一个进来的线程业务处理超时
        if (i ==1){
            i++;
            try {
                LOG.info("第" + Thread.currentThread().getId() + "号线程业务处理超时");
                Thread.sleep(9000);
            } catch (InterruptedException e) {
            }

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

}
