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

        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;

        cacheClient.set(refreshTokenKey, currentTimeMillis, jwtProperties.getTokenExpireTime());

        //生成token
        JSONObject json = new JSONObject();
        String token = JwtUtil.sign(account, currentTimeMillis);
        json.put("token",token );

        //写入header
        response.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
    }
}
