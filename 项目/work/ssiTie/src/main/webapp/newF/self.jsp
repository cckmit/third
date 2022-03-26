<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2021/11/22
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <s:hidden name=""></s:hidden>
    <input type="text">
    <s:property value="name"></s:property>
    <s:iterator value="userList" status="st">
        <span><s:property value="userName"></s:property></span>&nbsp;&nbsp;
        <s:property value="address"></s:property>
    </s:iterator>
</body>
</html>
