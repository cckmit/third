<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/12/3
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>用户信息页面</title>
</head>
<body>
<c:if test="${userList.size() >0}">
    <c:forEach items="${userList}" var="user" >
        ${user.name}
    </c:forEach>
</c:if>

</body>
</html>
