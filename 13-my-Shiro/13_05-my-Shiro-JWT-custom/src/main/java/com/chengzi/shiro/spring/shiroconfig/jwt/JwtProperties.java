package com.chengzi.shiro.spring.shiroconfig.jwt;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:30
 */
public class JwtProperties {


    //token过期时间，单位分钟
    Integer tokenExpireTime;
    //刷新Token过期时间，单位分钟
    Integer refreshTokenExpireTime;
    //Shiro缓存有效期，单位分钟
    Integer shiroCacheExpireTime;
    //token加密密钥
    String secretKey;

    public Integer getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Integer getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(Integer refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public Integer getShiroCacheExpireTime() {
        return shiroCacheExpireTime;
    }

    public void setShiroCacheExpireTime(Integer shiroCacheExpireTime) {
        this.shiroCacheExpireTime = shiroCacheExpireTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
