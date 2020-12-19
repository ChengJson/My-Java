<%--
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
    Object welcome = request.getAttribute("welcome");
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>index.jsp</title>
</head>
<body>
<h1><%=welcome%></h1>
</body>
</html>
