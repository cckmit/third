<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%><%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %><%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %><%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %><%@ page import="org.springframework.security.AuthenticationException,org.opoo.apps.security.webapp.*,org.opoo.apps.security.*" %><%
//request.setCharacterEncoding("UTF-8");
//System.out.println(">>>" + request.getQueryString());

String ru = WebUtils.getTargetUrl(request);
User user = UserHolder.getNullableUser();
//System.out.println(ru);
//System.out.println(java.net.URLEncoder.encode(request.getQueryString()));
if(user != null && ru != null){
	String contextPath = request.getContextPath();
	if(ru.startsWith("/") && !"".equals(contextPath) && !ru.startsWith(contextPath)){
		ru = contextPath + ru;
	}
	
	response.sendRedirect(ru);
	return;
}
%>
<html>
  <head>
    <title>Login</title>
  </head>

  <body onload="document.f.j_username.focus();">
    <h1>Login</h1>
    <c:if test="${not empty param.login_error}">
      <font color="red">
        Your login attempt was not successful, try again.<br/><br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
      </font>
    </c:if>

    <form name="f" action="<c:url value='login'/>" method="POST">
		<!-- <input type="hidden" name="<%=WebUtils.TARGET_URL_PARAM%>" value="${param.ru}"/> -->
		<input type="hidden" name="<%=org.springframework.security.ui.TargetUrlResolverImpl.DEFAULT_TARGET_PARAMETER%>" value="<%=ru != null ? WebUtils.encodeUrl(ru) : ""%>"/>
		<input type="hidden" name="<%=WebUtils.TOKEN_PARAM%>" value="<%=WebUtils.generateToken(session)%>"/>

      <table>
        <tr><td>User:</td><td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
        <tr><td><input type="checkbox" name="_spring_security_remember_me"></td><td>Don't ask for my password for two weeks</td></tr>

        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>

    </form>
<%
org.springframework.security.ui.savedrequest.SavedRequest savedRequest = (org.springframework.security.ui.savedrequest.SavedRequest) session.getAttribute(org.springframework.security.ui.AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY);


%>
<%=savedRequest%><p>
<%=request.getRequestURL()%>?<%=request.getQueryString()%>
<p>
<%
StringBuffer sb = request.getRequestURL();
if(request.getQueryString() != null){
	sb.append("?" + request.getQueryString());
}

%><p>
<%=java.net.URLEncoder.encode(response.encodeRedirectURL(sb.toString()), "UTF-8")%>
  </body>
</html>
