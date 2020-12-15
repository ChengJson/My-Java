package com.chengzi.ssm.aspect;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 10:05
 */
public class TestAspect {
    public static void main(String[] args) throws BeansException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl myUserService = (UserServiceImpl)applicationContext.getBean("myUserService");
        myUserService.doSome();
    }
}
