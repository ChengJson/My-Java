package com.fantaike.template.common.shiro.security;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fantaike.template.api.JsonResponse;
import com.fantaike.template.constant.ApiEnum;
import com.fantaike.template.dto.nosql.RedisService;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName: JwtFilter
 * @Description: jwt token过滤器
 * @Author: wuguizhen
 * @Date: 2019/7/15 18:16
 * @Version: v1.0 文件初始创建
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private JwtProperties jwtProperties;
    private RedisService redisService;

    public JwtFilter(JwtProperties jwtProperties, RedisService redisService){
        this.jwtProperties=jwtProperties;
        this.redisService=redisService;
    }

    /**
     * 是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = String.format("Token或者密钥不正确(%s)",throwable.getMessage());
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    msg = String.format("Token已过期(%s)",throwable.getMessage());
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
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        //绑定上下文获取账号
        String account = JwtUtil.getClaim(authorization, SecurityConsts.ACCOUNT);

        //检查是否需要更换token，需要则重新颁发
        this.refreshTokenIfNeed(account, authorization, response);

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     *      * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     *      * onAccessDenied是否执行取决于isAccessAllowed的值，
     *      * 如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
     *      * 如果onAccessDenied也返回false，则直接返回，
     * 不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return Boolean.FALSE;
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
            boolean getLockSuccess = redisService.getLock(lockName, System.currentTimeMillis(),SecurityConsts.ExpireTime.ONE_MINUTE);
            if (getLockSuccess) {
                //获取到锁
                String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
                if(redisService.exists(refreshTokenKey)){
                    //检查redis中的时间戳与token的时间戳是否一致
                    String tokenTimeStamp = (String)redisService.get(refreshTokenKey);
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
                redisService.set(refreshTokenKey, strCurrentTimeMillis, jwtProperties.getTokenExpireTime()*60);

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, newToken);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
            }
            //释放锁
            redisService.releaseLock(lockName);
        }
        return true;
    }

    /**
     * 检查是否需要更新Token
     *
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private boolean refreshCheck(String authorization, Long currentTimeMillis) {
        String tokenMillis = JwtUtil.getClaim(authorization, SecurityConsts.CURRENT_TIME_MILLIS);
        //获取refreshCheckTime 配置的是分钟，此处转为毫秒
        long refreshCheckTime = jwtProperties.refreshCheckTime * 60 * 1000L;
        if (currentTimeMillis - Long.parseLong(tokenMillis) > (refreshCheckTime)) {
            return true;
        }
        return false;
    }

    /**
     * 401非法请求
     *
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.append(JSON.toJSONString(JsonResponse.response(ApiEnum.RSLT_CDE_001004.getCode(),msg)));
        } catch (IOException e) {
            LOGGER.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
