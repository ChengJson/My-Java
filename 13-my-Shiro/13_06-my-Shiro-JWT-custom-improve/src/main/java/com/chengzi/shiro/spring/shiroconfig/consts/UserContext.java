package com.chengzi.shiro.spring.shiroconfig.consts;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:38
 */
public class UserContext implements AutoCloseable {
    static final ThreadLocal<LoginUser> current = new ThreadLocal<LoginUser>();

    public UserContext(LoginUser user) {
        current.set(user);
    }

    public static LoginUser getCurrentUser() {
        return current.get();
    }

    public void close() {
        current.remove();
    }
}

