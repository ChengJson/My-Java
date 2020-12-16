package com.chengzi.servlet;

import com.chengzi.beans.Student;
import com.chengzi.service.IStudentService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/16 11:29
 */
public class GetAllStudent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        IStudentService studentService = (IStudentService)webApplicationContext.getBean("studentService");
        List<Student> allStudnet = studentService.getAllStudnet();
        request.setAttribute("allStudent",allStudnet);
        request.getRequestDispatcher("allStudent.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
