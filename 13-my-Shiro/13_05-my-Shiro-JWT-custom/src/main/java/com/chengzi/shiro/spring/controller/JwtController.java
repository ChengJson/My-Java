package com.chengzi.shiro.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.shiro.spring.beans.Result;
import com.chengzi.shiro.spring.beans.User;
import com.chengzi.shiro.spring.shiroconfig.consts.Constants;
import com.chengzi.shiro.spring.shiroconfig.consts.SecurityConsts;
import com.chengzi.shiro.spring.shiroconfig.jwt.JwtProperties;
import com.chengzi.shiro.spring.util.JedisUtil;
import com.chengzi.shiro.spring.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/22 13:00
 */
@RestController
public class JwtController {

    @Autowired
    JedisUtil cacheClient;

    @Autowired
    JwtProperties jwtProperties;

    /**
     * 登录
     * @param user
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/login")
    @ResponseBody
    public Result login(HttpServletResponse response, User user) {
        Assert.notNull(user.getUsername(), "用户名不能为空");
        Assert.notNull(user.getPassword(), "密码不能为空");

//        BpUser userBean = this.findUserByAccount(user.getUsername());
//
//        if(userBean==null){
//            return new Result(false, "用户不存在", null, Constants.PASSWORD_CHECK_INVALID);
//        }
//
//        //域账号直接提示账号不存在
//        if ("1".equals(userBean.getDomainFlag())) {
//            return new Result(false, "账号不存在", null, Constants.PASSWORD_CHECK_INVALID);
//        }
//
//        String encodePassword = ShiroKit.md5(user.getPassword(), SecurityConsts.LOGIN_SALT);
//        if (!encodePassword.equals(userBean.getPassword())) {
//            return new Result(false, "用户名或密码错误", null, Constants.PASSWORD_CHECK_INVALID);
//        }
//
//        //账号是否锁定
//        if ("0".equals(userBean.getStatus())) {
//            return new Result(false, "该账号已被锁定", null, Constants.PASSWORD_CHECK_INVALID);
//        }
        if ("zhangsan".equals(user.getUsername()) && "123".equals(user.getPassword())){
            //验证成功后处理
            this.loginSuccess(user.getUsername(),response);
        } else {
            return new Result(false, "用户名或密码错误", null, Constants.PASSWORD_CHECK_INVALID);
        }

        //登录成功
        return new Result(true, "登录成功", null ,Constants.TOKEN_CHECK_SUCCESS);
    }

    /**
     * 登录后更新缓存，生成token，设置响应头部信息
     * @param account
     * @param response
     */
    private void loginSuccess(String account, HttpServletResponse response){

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        // 清除可能存在的Shiro权限信息缓存
        String tokenKey=SecurityConsts.PREFIX_SHIRO_CACHE + account;
        if (cacheClient.exists(tokenKey)) {
            cacheClient.delKey(tokenKey);
        }

        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        if (cacheClient.exists(refreshTokenKey)) {
            cacheClient.set(refreshTokenKey, currentTimeMillis, jwtProperties.getRefreshTokenExpireTime());
        }else{
            cacheClient.set(refreshTokenKey, currentTimeMillis, jwtProperties.getRefreshTokenExpireTime());
        }

        //生成token
        JSONObject json = new JSONObject();
        String token = JwtUtil.sign(account, currentTimeMillis);
        json.put("token",token );

        //写入header
        response.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
    }
}
