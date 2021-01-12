package com.chengzi.shiro.spring.shiroconfig.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.chengzi.shiro.spring.beans.Result;
import com.chengzi.shiro.spring.shiroconfig.consts.LoginUser;
import com.chengzi.shiro.spring.shiroconfig.consts.SecurityConsts;
import com.chengzi.shiro.spring.shiroconfig.consts.UserContext;
import com.chengzi.shiro.spring.util.JedisUtil;
import com.chengzi.shiro.spring.util.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.chengzi.shiro.spring.shiroconfig.jwt.*;
import com.chengzi.shiro.spring.shiroconfig.consts.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/24 11:28
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JedisUtil cacheClient;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    ISyncCacheService syncCacheService;


    /**
     * 不弹登录窗 ,如果当前登录过滤器返回false，其余过滤器就不处理了，返回true，其余过滤器接着处理。
     * 这里重写了BasicHttpAuthenticationFilter的方法，本过滤器也可以直接继承AccessControlFilter
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 检测Header里Authorization字段
     * 判断是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);
        return authorization != null;
    }


    /**
     * 是否允许访问1
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest  ac =(HttpServletRequest)request;
        System.out.println(ac.getRequestURL());
        System.out.println();
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
//                    // AccessToken已过期
//                    if (this.refreshToken(request, response)) {
//                        return true;
//                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
//                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }
    /**
     * 登录验证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        // 绑定上下文
        String account = JwtUtil.getClaim(authorization, SecurityConsts.ACCOUNT);
        UserContext userContext= new UserContext(new LoginUser(account));


        // 检查是否需要更换Token，需要则重新颁发
        this.refreshTokenIfNeed(account,authorization,response);

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 检查是否需要,若需要则校验时间戳，刷新Token，并更新时间戳
     * @param account 账号
     * @param authorization token
     * @param response
     * @return
     */
    private boolean refreshTokenIfNeed(String account, String authorization, ServletResponse response) {
        Long currentTimeMillis = System.currentTimeMillis();
        //检查刷新规则
        if (this.refreshCheck(authorization, currentTimeMillis)) {
            String lockName = SecurityConsts.PREFIX_SHIRO_REFRESH_CHECK + account;
            boolean getLockSuccess = syncCacheService.getLock(lockName,Constants.ExpireTime.ONE_MINUTE);
            if (getLockSuccess) {
                //获取到锁
                String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
                if(cacheClient.exists(refreshTokenKey)){
                    //检查redis中的时间戳与token的时间戳是否一致
                    String tokenTimeStamp = (String)cacheClient.get(refreshTokenKey);
                    String tokenMillis= JwtUtil.getClaim(authorization,SecurityConsts.CURRENT_TIME_MILLIS);
                    if(!tokenMillis.equals(tokenTimeStamp)){
                        throw new TokenExpiredException(String.format("账户%s的令牌无效", account));
                    }
                }
                //时间戳一致，则颁发新的令牌
                LOGGER.info(String.format("为账户%s颁发新的令牌", account));
                String strCurrentTimeMillis = String.valueOf(currentTimeMillis);
                String newToken = JwtUtil.sign(account,strCurrentTimeMillis);

                //更新缓存中的token时间戳
                cacheClient.set(refreshTokenKey, strCurrentTimeMillis, jwtProperties.getTokenExpireTime());

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, newToken);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
            }
            //释放锁
            syncCacheService.releaseLock(lockName);
        }
        return true;
    }

    /**
     * 检查是否需要更新Token
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private boolean refreshCheck(String authorization, Long currentTimeMillis) {
        String tokenMillis = JwtUtil.getClaim(authorization, SecurityConsts.CURRENT_TIME_MILLIS);
        if (currentTimeMillis - Long.parseLong(tokenMillis) > (jwtProperties.getRefreshCheckTime()* 1000L)) {
            return true;
        }
        return false;
    }

    /**
     * 401非法请求
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp,String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();

            Result result = new Result();
            result.setResult(false);
            result.setCode(Constants.PASSWORD_CHECK_INVALID);
            result.setMessage(msg);
            out.append(JSON.toJSONString(result));
        } catch (IOException e) {
            LOGGER.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
