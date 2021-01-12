package com.chengzi.shiro.spring.shiroconfig.jwt;

public interface ISyncCacheService {
    Boolean getLock(String lockName, int expireTime);
    Boolean releaseLock(String lockName);
}
