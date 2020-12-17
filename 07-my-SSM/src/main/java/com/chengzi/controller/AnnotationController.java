package com.chengzi.controller;

import com.chengzi.beans.Student;
import com.chengzi.exception.MyCustomException;
import com.chengzi.service.IStudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/16 14:38
 */
@Controller
@RequestMapping("/test")
public class AnnotationController {

    @Autowired
    IStudentService studentService;

    @RequestMapping(value = "/01", method = RequestMethod.GET)
    public ModelAndView test01(@RequestParam(value = "a", required = true) String a, String b) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("welcome", "Hello SpringMVC ==>" + a);
        mv.setViewName("welcome");
        return mv;
    }

    //返回视图
    @RequestMapping(value = "/02", method = RequestMethod.GET)
    public String test02(String a, String b, HttpServletRequest request) {
        request.setAttribute("welcome", "Hello SpringMVC ==>" + a);
        return "welcome";
    }

    //返回json
    @RequestMapping(value = "/03", method = RequestMethod.GET)
    public void test03(String name, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = new Student(1, name, 2, 2);
        ObjectMapper objectMapper = new ObjectMapper();
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream,student);
    }

    //注解返回json
    @RequestMapping(value = "/04", method = RequestMethod.GET)
    @ResponseBody
    public Student test04(String name, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = new Student(1, name, 2, 2);
        return student;

    }

    //返回jsonArray
    @RequestMapping(value = "/05", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> test05(String name, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = new Student(1, name, 2, 2);
        Student student2 = new Student(1, name, 2, 2);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);
        students.add(student2);
        return students;
    }


    //测试自定义异常
    @RequestMapping(value = "/06", method = RequestMethod.GET)
    @ResponseBody
    public void test06(String name, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        throw new MyCustomException();
    }


    //测试自定义异常
    @RequestMapping(value = "/07", method = RequestMethod.GET)
    @ResponseBody
    public void test07(Integer id, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        studentService.addStudent(new Student(id,"213",123,123));
    }


}
