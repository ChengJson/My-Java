package com.chengzi.ssm.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 9:45
 */
@Aspect
public class MyAspect {
    @Before("execution(* *..UserServiceImpl.doSome())")
    public void beforeSome(){
        System.out.println("前置增强");
    }
    @After("execution(* *..UserServiceImpl.doSome())")
    public void afterReturing(){
        System.out.println("后置增强");
    }
}
