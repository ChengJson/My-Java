package com.fantaike.template.common.shiro;

import com.fantaike.template.domain.login.ShiroAccountInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: ShiroUserAccountUtil
 * @Description: shiro操作工具类
 * @Author: wuguizhen
 * @Date: 2019/7/8 20:30
 * @Version: v1.0 文件初始创建
 */
public class ShiroUserAccountUtil {

    
    /**
     * @Description: 获取登录用户信息
     * @param 
     * @Date: 2019/7/8 20:30
     * @Author: wuguizhen
     * @Return com.fantaike.template.domain.login.ShiroAccountInfo
     * @Throws
     */
    public static ShiroAccountInfo getShiroUserInfo() {
        if (!isLogin()) {
            throw new UnauthenticatedException();
        }
        Subject subject = getSubject();
        ShiroAccountInfo sui = (ShiroAccountInfo) subject.getPrincipal();
        return sui;
    }

    /**
     * @Description: 获得Subject
     * @param 
     * @Date: 2019/7/8 20:31
     * @Author: wuguizhen
     * @Return org.apache.shiro.subject.Subject
     * @Throws
     */
    public static Subject getSubject() {
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    /**
     * @Description: 登录
     * @param token
     * @Date: 2019/7/8 20:31
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    public static void login(AuthenticationToken token) {
        getSubject().login(token);
    }

    /**
     * @Description: 退出登录
     * @param 
     * @Date: 2019/7/8 20:31
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    public static void logout() {
        Subject subject = getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }
    }
    
    /**
     * @Description: 判断是否登录
     * @param 
     * @Date: 2019/7/8 20:31
     * @Author: wuguizhen
     * @Return boolean
     * @Throws
     */
    public static boolean isLogin() {
        Subject subject = getSubject();
        return subject != null && subject.isAuthenticated();
    }

    /**
     * @Description: 获取当前登录用户的userID
     * @param 
     * @Date: 2019/7/8 20:31
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String getUserId() {
        return getShiroUserInfo().getUsrId();
    }

    /**
     * @Description: 获取当前登录用户的userName
     * @param 
     * @Date: 2019/7/8 20:35
     * @Author: wuguizhen
     * @Return java.lang.String
     * @Throws
     */
    public static String getUserName() {
        return getShiroUserInfo().getUsrName();
    }


    /**
     * 防止初始化
     */
    private ShiroUserAccountUtil() {

    }

}
