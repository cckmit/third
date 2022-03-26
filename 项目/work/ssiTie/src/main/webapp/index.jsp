<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2021/11/22
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>jsldfjl</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery-1.7.2.min.js"></script>
    <script>
       // $(() =>{
       //      $("input").attr("value","1111");
       //      $("#cbj").attr("readonly","true");
       // });
       window.onload = function (){
           // $("input").attr("value","1111");
           $("#cbj").attr("readonly","true");
       };
    </script>
</head>
<body>
<input id="cbj" value="${flag}">
<s:textfield name="flag" ></s:textfield>
<s:if test="#request.flag == '1'">
    <input id="cbj" value="${flag}">
</s:if>
<s:else>
    <span id="cbj1" >22222</span>
</s:else>

<s:property value="name"></s:property>
<s:iterator value="userList" status="st">
    <span><s:property value="userName"></s:property></span>&nbsp;&nbsp;
    <s:property value="address"></s:property>
    <br/>
</s:iterator>
</body>
</html>
