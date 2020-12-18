<%@ page import="java.util.List" %>
<%@ page import="com.chengzi.beans.Student" %><%--
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
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>index.jsp</title>
</head>
<body>
    这里是index.jsp,basepath = <%= basePath %><br/>
    <form action="add" method="get">
        id:<input name="id"><br>
        name:<input name="name"><br>
        age:<input name ="age"><br>
        score:<input name="score"><br>
        <input type="submit" value="添加">

    </form>
</body>
</html>
