package com.chengzi.shiro.spring.shiroconfig.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 12:29
 */
public class JwtToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
