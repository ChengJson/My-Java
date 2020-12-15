package com.chengzi.ssm.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @ClassName: TestProxy
 * @Description: 测试Java动态代理
 * @Author: chengli
 * @Date: 2020/12/14 15:00
 * @Version: v1.0 文件初始创建
 */

public class TestProxy {
    @Test
    public void testMyinvocation(){
        Dosome target = new MyDosome();
        MyInvocationHandler hanlder = new MyInvocationHandler(target);
        Dosome proxy = (Dosome) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), hanlder);
        String result = proxy.dosome("123");
        System.out.println(result);
    }
}
