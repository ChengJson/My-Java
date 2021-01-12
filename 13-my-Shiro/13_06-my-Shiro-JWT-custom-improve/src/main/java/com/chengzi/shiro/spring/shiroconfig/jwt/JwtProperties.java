package com.chengzi.shiro.spring.shiroconfig.jwt;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:30
 */
public class JwtProperties {


    //token过期时间，单位分钟
    Integer tokenExpireTime;


    //token加密密钥
    String secretKey;

    /**
     * 更新令牌时间，单位分钟
     */
    Integer refreshCheckTime;

    public Integer getRefreshCheckTime() {
        return refreshCheckTime;
    }

    public void setRefreshCheckTime(Integer refreshCheckTime) {
        this.refreshCheckTime = refreshCheckTime;
    }

    public Integer getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
