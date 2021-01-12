package com.chengzi.shiro.spring.shiroconfig.consts;

import java.io.Serializable;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:37
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long userId;          // 主键ID
    public String account;      // 账号
    public String name;         // 姓名

    public LoginUser() {
    }

    public LoginUser(String account) {
        this.account=account;
    }

    public LoginUser(Long userId, String account, String name) {
        this.userId = userId;
        this.account = account;
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
