package com.chengzi.ssm.proxy;


import com.chengzi.ssm.util.Myloger;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: MyInvocationHandler
 * @Description: JDK动态代理类
 * @Author: chengli
 * @Date: 2020/12/14 14:34
 * @Version: v1.0 文件初始创建
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler() {
        super();
    }

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        String name = method.getName();
        if ("dosome".equals(name)) {
            Myloger.getLogger(this.getClass()).info("动态代理前逻辑 开始事务。。。。");
            result = method.invoke(target, args);
            Myloger.getLogger(this.getClass()).info("动态代理后逻辑 提交事务。。。。");
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }
}

