<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 2020/12/16
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<script src="js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

<h1>服务注册成功</h1>
</body>
</html>
