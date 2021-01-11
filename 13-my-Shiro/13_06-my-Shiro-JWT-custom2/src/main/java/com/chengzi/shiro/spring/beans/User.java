package com.chengzi.shiro.spring.beans;

import java.io.Serializable;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 13:02
 */
public class User implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
