package com.chengzi.controller;

import com.chengzi.beans.Student;
import com.chengzi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/17 13:31
 */
@Controller
public class StudentController {


    @Autowired
    IStudentService studentService;

    @RequestMapping(value = "/getAllStudent", method = RequestMethod.GET)
    public String test07(Integer id, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Student> allStudnet = new ArrayList<>();
        try {
             allStudnet = studentService.getAllStudnet();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        request.setAttribute("allStudent",allStudnet);
        return "studentList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        studentService.deleteStudent(id);
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Integer id, String name,Integer age,Double score, HttpServletRequest request, HttpServletResponse response) throws IOException {
        studentService.addStudent(new Student(id,name,age,score));
        return "redirect:/index.jsp";
    }





}
