package com.chengzi.controller.jvmLock;

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

/**
 * 问题：释放了锁但是还没有放入redis，也就是在释放锁了，数据还没来得及写入redis
 * 解决：放入redis的操作也放在同步代码块中
 *
 *
 * 实际测试确实只查一次库
 *
 */
@RestController
public class LocalCacheController3 {

    private static Logger LOG = LoggerFactory.getLogger(LocalCacheController1.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     *
     * @return
     */
    @RequestMapping("/local3")
    public  List<Map<String, Object>> local(){

        String all_user = redisTemplate.opsForValue().get("all_user");
        if (StringUtils.isEmpty(all_user)){

            LOG.info("缓存中没有，从数据库中查询数据");

            List<Map<String, Object>> fromLocal = getFromLocal();
            return fromLocal;
        }
        List<Map<String, Object>> maps = JSONObject.parseObject(all_user, new TypeReference<List<Map<String, Object>>>() {});
        LOG.info("从缓存中获得数据，直接返回");
        return maps;
    }

    /**
     * 使用jvm锁
     * @return
     */
    private  List<Map<String, Object>> getFromLocal(){
        synchronized (this) {
            //拿到锁对象后还要再次判断缓存是否存在
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


}
