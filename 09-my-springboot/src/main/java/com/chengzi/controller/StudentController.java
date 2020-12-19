package com.chengzi.controller;


import com.chengzi.beans.Student;
import com.chengzi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class StudentController {



    @Autowired
    IStudentService studentService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object test07(Long id, String b, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = new Student();
        student.setId(id);
        student.setAge(13);
        student.setName("123");
        student.setScore(132.0);
        studentService.addStudent(student);
        return "ok";
    }


}