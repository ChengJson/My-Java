package com.chengzi.controller;

import com.chengzi.Application;
import com.chengzi.beans.User;
import com.chengzi.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/29 10:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LocalCacheControllerTest {
    @Autowired
    private LocalCacheController1 LocalCacheController;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Map<String, Object>> local = LocalCacheController.local();
        local.forEach(System.out::println);
    }

}
