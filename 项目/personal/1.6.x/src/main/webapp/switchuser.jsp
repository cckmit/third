<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>切换登录用户</title>
</head>

<body onload="document.f.j_username.focus();">
    <%-- this form-login-page form is also used as the
         form-error-page to ask for a login again.
         --%>
	<%if("1".equals(request.getParameter("login_error"))){%>
      <font color="red">
        Your 'su' attempt was not successful, try again.<BR><BR>
        Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
      </font>
	<%}%>

    <form action="<c:url value='j_spring_security_switch_user'/>" method="POST" name='f'>
      <table>
        <tr><td>用户名:</td><td><input type='text' name='j_username'></td></tr>
        <tr><td colspan='2'><input name="switch" type="submit" value="切换到指定用户"></td></tr>
      </table>
    </form>

</body>
</html>

