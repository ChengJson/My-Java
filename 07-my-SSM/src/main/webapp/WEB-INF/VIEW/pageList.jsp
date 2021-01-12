<%@ page import="java.util.List" %>
<%@ page import="com.chengzi.beans.Student" %>
<%@ page import="com.chengzi.dto.PageDto" %><%--
  Created by IntelliJ IDEA.
  User: chengli
  Date: 2020-12-16
  Time: 00:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    PageDto pageDto = (PageDto)request.getAttribute("pageDto");
    List<Student> studentList = (List<Student>) pageDto.getList();
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>index.jsp</title>
</head>
<body>
    这里是index.jsp,basepath = <%= basePath %><br/>
    <table border="2">
        <tr>
            <td>学生编号</td>
            <td>学生姓名</td>
            <td>学生年龄</td>
            <td>学生分数</td>
        </tr>
        <% for(Student student:studentList){%>
        <tr>
            <td><%= student.getId()%></td>
            <td><%= student.getName()%></td>
            <td><%= student.getAge()%></td>
            <td><%= student.getScore()%></td>
            <td><a href="delete?id=<%= student.getId()%>">删除</a></td>
            <td><a href="addStudent.jsp">添加</a></td>
        </tr>
        <% }%>
    </table>
</body>
</html>
