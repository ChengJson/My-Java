package com.chengzi.controller;


import com.chengzi.beans.Student;
import com.chengzi.dao.StudentMapper;
import com.chengzi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class RedisController {


    @Autowired
    RedisTemplate redisTemplete;

    @Autowired
    StudentMapper studentMapper;

    /**
     * 双重检测锁
     * @param id
     * @param b
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public Object test07(Long id, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Student student = (Student) redisTemplete.opsForValue().get("student_cache");
        if (student == null){
            //双重检测锁
            synchronized (this) {
                Object student_cache = redisTemplete.opsForValue().get("student_cache");
                if (student_cache == null) {
                    student = studentMapper.selectByPrimaryKey(id);
                    redisTemplete.opsForValue().set("student_cache",student);
                    System.out.println("============查询数据库");
                }else {
                    System.out.println("============redis查询");
                }
            }
        }else {
            System.out.println("redis查询的数据");
        }
        return student;
    }

    /**
     * 测试缓存
     * @param id
     * @param b
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object test07(Long id, String b) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i <= 10000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    studentMapper.selectByPrimaryKey(1l);
                }
            });

        }
        return "ok";
    }


}