package com.chengzi.test;

import com.chengzi.beans.Student;
import com.chengzi.dao.IStudentDao;
import com.chengzi.service.IStudentService;
import com.chengzi.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.*;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 14:53
 */
public class MyTestAuto {
    private IStudentService studentService;

    @Before
    public void setUp(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(S-> System.out.println(S.toString()));
        studentService = (IStudentService) applicationContext.getBean("studentService");
    }

    @Test
    public void testAdd() throws IOException {
        studentService.addStudent(new Student(1,"name",12,12));
    }





}

