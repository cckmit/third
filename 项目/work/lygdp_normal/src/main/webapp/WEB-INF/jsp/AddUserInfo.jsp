<%@ page import="java.util.List" %>
<%@ page import="com.beitie.bean.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
<html>
<head>
    <title>添加用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>I love you ,  beibei  !!!!</h1>
<table border="1px" cellpadding="0" cellspacing="0" width="510">
    <caption>学生情况一览表</caption>
    <form action="${pageContext.request.contextPath}/user/addUser.action" method="post">
        <tr>
            <th width="40">姓名</th>
            <th width="180"><input type="text" name="uname" /></th>
        </tr>
        <tr>
            <th>年龄</th>
            <th><input type="number" name="uage" /></th>
        </tr>
        <tr>
            <th>地址</th>
            <th><input type="text" name="address" /></th>
        </tr>
        <tr>
            <th>性别</th>
            <th><input type="text" name="sex" ></th>
        </tr>
        <tr>
            <th>年级</th>
            <th><input type="text" name="grade" /></th>
        </tr>
        <tr>
            <th>出生年月</th>
            <th><input type="text" name="ubirth" /></th>
        </tr>
        <tr>
            <th colspan="2"><input type="submit" value="提交"/></th>
        </tr>
    </form>
</table>
</body>
</html>
