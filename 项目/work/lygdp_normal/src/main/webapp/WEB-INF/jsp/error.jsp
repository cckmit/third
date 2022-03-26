<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/4/9
      Time: 23:43
      To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>错误提示</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/config.css">
</head>
<body>
<div class="warning">${exception.code}:${exception.msg}</div>
</body>
</html>
<style>
    .jlsj {
        color: #0a1139;
    }
</style>
