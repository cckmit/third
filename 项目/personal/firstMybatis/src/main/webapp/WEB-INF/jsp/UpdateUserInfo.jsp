<%@ page import="java.util.List" %>
<%@ page import="com.beitie.bean.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
<html>
<head>
    <title>bietielove</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>I love you ,  beibei  !!!!</h1>
    <table border="1px" cellpadding="0" cellspacing="0" width="510">
        <caption>学生情况一览表</caption>
        <form action="${pageContext.request.contextPath}/user/updateUserInfo.action" method="post">
            <tr>
                <th width="40">姓名</th>
                <th width="180"><input type="text" name="uname" value="${user.uname}"/></th>
            </tr>
            <tr>
                <th>年龄</th>
                <th><input type="number" name="uage" value="${user.uage}"/></th>
            </tr>
            <tr>
                <th>地址</th>
                <th><input type="text" name="uaddress" value="${user.uaddress}"/></th>
            </tr>
            <tr>
                <th>性别</th>
                <th><input type="text" name="usex" value="${user.usex}"/></th>
            </tr>
            <tr>
                <th>年级</th>
                <th><input type="text" name="ugrade" value="${user.ugrade}"/></th>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="提交"/></th>
            </tr>
            <input type="hidden" name="uid" value="${user.uid}"/>
        </form>
    </table>
</body>
</html>
