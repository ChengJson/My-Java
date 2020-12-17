package com.chengzi.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyIntercepter implements HandlerInterceptor {

    private static Logger LOG = LoggerFactory.getLogger(MyIntercepter.class);

    private long startTime = 0;
    /**
     * 该方法在处理器方法执行之前执行。其返回值为boolean，若为true，则紧接着会执行处理器方法，
     * 且会将afterCompletion()方法放入到一个专门的方法栈中等待执行。Object handler：处理器对象
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime = System.currentTimeMillis();
        LOG.info("preHandle方法执行"+request.getRequestURL());
        return true;
    }

    /**
     * 该方法在处理器方法执行之后执行。处理器方法若最终未被执行，则该方法不会执行。
     * 由于该方法是在处理器方法执行完后执行，且该方法参数中包含ModelAndView，所以该方法可以修改处理器方法的处理结果数据，且可以修改跳转方向。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("postHandle方法执行");
        //这里可以重新改modelview
    }

    /**
     * 当preHandle()方法返回true时，会将该方法放到专门的方法栈中，等到对请求进行响应的所有工作完成之后才执行该方法。
     * 即该方法是在中央调度器渲染（数据填充）了响应页面之后执行的，此时对ModelAndView再操作也对响应无济于事。
     * 最后执行的方法，在请求处理完成后执行的， 认为视图对象处理完成，请求就算是结束了。
     * 这个方法中，可以实现程序最后要执行的工作，比如内存回收，资源的释放
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long spend = System.currentTimeMillis() - startTime;
        LOG.info("afterCompletion方法执行,耗时：" + spend + "ms");

    }
}
