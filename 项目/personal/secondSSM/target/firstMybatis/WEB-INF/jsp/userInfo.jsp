<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
<html>
<head>
    <title>bietielove</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script>
        function queryUserInfoJson(uid){
            $.ajax({
                url: "${pageContext.request.contextPath}/userInfo/querySingleUserInfoForJson.action?uid="+uid,
                type: "POST",
                success: function (result) {
                    console.log(result);
                }
            });

        }
        function publishUserInfoService(){
            $.ajax({
                url: "${pageContext.request.contextPath}/webservice/publishUserInfoService.action?uid=",
                type: "POST",
                success: function (result) {
                    console.log(result);
                }
            });

        }
    </script>
</head>
<body>
<input type="button" value="发布服务" onclick="publishUserInfoService()">
<c:if test="${!empty userInfoList}">
    <table border="1px" cellpadding="0" cellspacing="0" width="550">
        <caption>学生情况一览表</caption>
        <tr>
            <th width="80">姓名</th>
            <th width="40">年龄</th>
            <th width="180">地址</th>
            <th width="40">性别</th>
            <th width="90">年级</th>
            <th width="40">修改</th>
            <th width="40">删除</th>
            <th width="40">添加</th>
            <th width="40">查看</th>
        </tr>
        <c:forEach items="${userInfoList}" var="user">
            <tr>
                <th>${user.name}</th>
                <th>${user.age}</th>
                <th>${user.address}</th>
                <th>${user.sexName}</th>
                <th>${user.gradeName}</th>
                <th><a href="${pageContext.request.contextPath}/userInfo/querySingleUserInfo.action?uid=${user.id}">修改</a></th>
                <th><a href="${pageContext.request.contextPath}/userInfo/deleteUserInfo.action?uid=${user.id}">删除</a></th>
                <th><a href="${pageContext.request.contextPath}/userInfo/tiaoZhuan.action?viewname=AddUserInfo">添加</a></th>
                <th><a onclick="queryUserInfoJson(${user.id})" href="#">查看</a></th>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
