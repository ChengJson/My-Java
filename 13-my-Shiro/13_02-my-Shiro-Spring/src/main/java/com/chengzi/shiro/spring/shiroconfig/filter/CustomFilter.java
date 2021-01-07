package com.chengzi.shiro.spring.shiroconfig.filter;

import com.chengzi.shiro.spring.intercepter.MyIntercepter;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *Desc: 自定义过滤器 或者的关系 只要有一个权限就允许访问
 *@author:chengli
 *@date:2020/12/23 11:25
 */
public class CustomFilter extends AuthorizationFilter {

    private static Logger LOG = LoggerFactory.getLogger(MyIntercepter.class);


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        if (roles == null || roles.length == 0 ){
            return true;
        }
        for (String role : roles) {
            if (subject.hasRole(role))
                return true;
        }
        /**
         * 这里抛出的异常不能被spring捕获到，因为是servlet级别的，realm中抛出的异常时login控制器，调用realm，已经进入springmvc
         */
        //if (1==1) {throw  new UnauthorizedException(); }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return true;
    }
}
