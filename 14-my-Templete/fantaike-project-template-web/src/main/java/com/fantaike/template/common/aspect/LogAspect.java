package com.fantaike.template.common.aspect;

import org.apache.dubbo.config.annotation.Reference;
import com.fantaike.template.common.annotation.Log;
import com.fantaike.template.common.shiro.ShiroUserAccountUtil;
import com.fantaike.template.common.util.IPUtils;
import com.fantaike.template.domain.setting.SysLog;
import com.fantaike.template.service.setting.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName: LogAspect
 * @Description: 日志aop实现
 * @Author: wuguizhen
 * @Date: 2019/7/11 11:26
 * @Version: v1.0 文件初始创建
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Reference
    SysLogService logService;

    @Pointcut("@annotation(com.fantaike.template.common.annotation.Log)")
    public void logPointCut() {
    }
    
    /**
     * @Description: 环绕通知 调用前和调用都会执行
     * @param point
     * @Date: 2019/7/11 11:33
     * @Author: wuguizhen
     * @Return java.lang.Object
     * @Throws
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    /**
     * @Description:保存系统日志
     * @param joinPoint
     * @param time
     * @Date: 2019/7/11 11:44
     * @Author: wuguizhen
     * @Return void
     * @Throws
     */
    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            // 注解上的描述
            sysLog.setOperation(log.operation());
            sysLog.setModule(log.module());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IPUtils.getIpAddr(request);
        // 设置IP地址
        sysLog.setIp(ip);
        sysLog.setLocation(IPUtils.getCityInfo(ip));
        // 用户名
        if(ShiroUserAccountUtil.isLogin()){
            sysLog.setUserId(ShiroUserAccountUtil.getUserId());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logService.insert(sysLog);
    }
}
