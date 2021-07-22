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
<%
    List<User>  list=(List<User>)request.getAttribute("userInfoList");
    System.out.println(list.size()+"===============");
%>
<h1>I love you ,  beibei  !!!!</h1>
<c:if test="${!empty userInfoList}">
    <table border="1px" cellpadding="0" cellspacing="0" width="370">
        <caption>学生情况一览表</caption>
        <tr>
            <th width="80">姓名</th>
            <th width="40">年龄</th>
            <th width="150">地址</th>
            <th width="40">性别</th>
            <th width="60">年级</th>
        </tr>
        <c:forEach items="${userInfoList}" var="student">
            <tr>
                <th>${student.uname}</th>
                <th>${student.uage}</th>
                <th>${student.address}</th>
                <th>${student.sex}</th>
                <th>${student.grade}</th>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
