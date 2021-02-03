package com.chengzi.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *Desc:
 *@author:chengli
 *@date:2021/2/2 16:34
 */
public class SimpleCORSFilter implements Filter {

    @Override
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept,"
                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
        response.setHeader("Access-Control-Request-Headers", "Authorization,Origin, X-Requested-With,content-Type,Accept");
        response.setHeader("Access-Control-Expose-Headers", "*");

        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}

