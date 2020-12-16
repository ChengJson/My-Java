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
<button id="button" >按钮</button>
<input id="input">
<button id="test04" >test04</button>

<button id="test05" >test05</button>
<script>
   $(function () {
       var name  = document.getElementById("input")

       $("#button").click(function () {
           $.ajax({
               url:"test/03",
               data:{
                   "name":name.value,
                   "age":123
               },
               type:"get",
               dataType:"json",
               success:function (student) {
                   alert(student.name);
               },
               error:function (student) {
                    alert(student)
               }
           })
       })


       $("#test04").click(function () {
           $.ajax({
               url:"test/04",
               data:{
                   "name":name.value,
                   "age":123
               },
               type:"get",
               dataType:"json",
               success:function (student) {
                   alert(student.name);
               },
               error:function (student) {
                   alert(student)
               }
           })
       })

       $("#test05").click(function () {
           $.ajax({
               url:"test/05",
               data:{
                   "name":name.value,
                   "age":123
               },
               type:"get",
               dataType:"json",
               success:function (student) {
                   $.each(student,function (i,obj) {
                       alert(student.length)
                   })
               },
               error:function (student) {
                   alert("error")
               }
           })
       })
   })
</script>
</body>
</html>
