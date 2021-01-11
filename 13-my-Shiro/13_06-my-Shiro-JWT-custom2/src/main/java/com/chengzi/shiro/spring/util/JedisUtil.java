package com.chengzi.shiro.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/23 14:58
 */
@Component
public class JedisUtil {
    private static Logger LOG = LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    public  void set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            LOG.error(e.toString());
        } finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key){
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int time) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, time);
        } catch (Exception e) {
            LOG.error(e.toString());
        } finally {
            jedis.close();
        }
    }

    public void delete(byte[] key){
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            LOG.error(e.toString());
        } finally {
            jedis.close();
        }
    }


    public Set<byte[]> keys(String key){
        Jedis jedis = getJedis();
        try {
            return jedis.keys(key.getBytes());
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public boolean exists(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public String get(String key) {

        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public void set(String refreshTokenCacheKey, String currentTimeMillis, long l) {

        Jedis jedis = getJedis();
        try {
            jedis.set(refreshTokenCacheKey,currentTimeMillis);
            jedis.expire(refreshTokenCacheKey, (int) l);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public void delKey(String tokenKey) {
        Jedis jedis = getJedis();
        try {
            jedis.del(tokenKey);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
    }

    public long incrBy(String lockName, int i) {
        Jedis jedis = getJedis();
        Long aLong;
        try {
             aLong = jedis.incrBy(lockName, i);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw e;
        } finally {
            jedis.close();
        }
        return aLong;
    }
}
