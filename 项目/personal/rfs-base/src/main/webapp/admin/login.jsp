<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" 
import="org.apache.commons.logging.Log,
org.apache.commons.logging.LogFactory,
org.opoo.apps.lifecycle.Application,
org.opoo.apps.security.SecurityUtils,
org.opoo.apps.security.User,
org.opoo.apps.security.UserHolder,
org.springframework.context.ApplicationEventPublisher,
org.springframework.security.Authentication,
org.springframework.security.AuthenticationException,
org.springframework.security.AuthenticationManager,
org.springframework.security.context.SecurityContextHolder,
org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent,
org.springframework.security.providers.UsernamePasswordAuthenticationToken,
org.springframework.security.ui.AuthenticationDetailsSource,
org.springframework.security.ui.WebAuthenticationDetailsSource,
org.springframework.security.ui.logout.LogoutFilter,
org.springframework.security.ui.logout.LogoutHandler,
org.apache.commons.lang.StringUtils,
cn.redflagsoft.base.security.webapp.LoginUtils,
org.opoo.apps.web.servlet.JCaptchaServlet,
cn.redflagsoft.base.security.webapp.RFSWebAuthenticationDetails,
cn.redflagsoft.base.security.webapp.AdminLoginHelper"
%><%!
private static final Log logger = LogFactory.getLog("adminlogin");

%><%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %><%
String action = request.getParameter("action");
boolean hasError = "1".equals(request.getParameter("error"));
User user = null;

//if logout
if("logout".equals(action)){
	logger.info("处理登出");
	AdminLoginHelper.attemptLogout(request, response);

	response.sendRedirect("login.jsp");
	return;
}

if("login".equals(action)){
	AdminLoginHelper.attemptLogin(request, response);
	user = UserHolder.getNullableUser();
	//如果登录失败
	if(user == null){
		response.sendRedirect("login.jsp?error=1");
		return;
	}
}

//如果没有错误
//if(!hasError){
	if(user == null){
		user = UserHolder.getNullableUser();
	}
	if(user != null){
		//登录成功
		if(SecurityUtils.isGranted(user, "ROLE_ADMIN")){
			response.sendRedirect("index.jsp");
			return;
		}else{
			//msg = "没有权限 <a href='login.jsp?action=logout'>退出</a>";
			out.println("没有权限，请<a href='login.jsp?action=logout'>退出</a>后重新登录。");
			return;
		}
	}
//}
//继续处理


//redirect if current user is admin
/*
User user = UserHolder.getNullableUser();
if(user != null){
	if(SecurityUtils.isGranted(user, "ROLE_ADMIN")){
		response.sendRedirect("index.jsp");
		return;
	}else{
		msg = "没有权限 <a href='login.jsp?action=logout'>退出</a>";
	}
}else{

}
*/


/////////////////////////////////////////////////////////////////
response.setHeader("Cache-Control", "Public"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires", 0); 
//session.removeAttribute("validationKey");

String usernameKey = AdminLoginHelper.ADMIN_LOGIN_FORM_USERNAME_KEY;
String passwordKey = AdminLoginHelper.ADMIN_LOGIN_FORM_PASSWORD_KEY;
String captchaKey = AdminLoginHelper.ADMIN_LOGIN_FORM_CAPTCHA_KEY;

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>系统登录</title>
<script src="../include/MD5.txt" language="javascript"></script>
<script language="JavaScript">
<!--
function checklogin(){
	if(!document.f.<%=usernameKey%>.value){
		alert("请输入用户名！");
		document.f.<%=usernameKey%>.focus();
		return false;
	}

	if(!document.f.<%=passwordKey%>.value){
		document.f.<%=passwordKey%>.focus();
		return false;
	}
	
	if(!document.f.<%=captchaKey%>.value){
		document.f.<%=captchaKey%>.focus();
		return false;
	}
	
	
	document.f["<%=RFSWebAuthenticationDetails.LOGIN_FORM_RESOLUTION_KEY%>"].value = screen.width + "x" + screen.height;
	document.f["<%=RFSWebAuthenticationDetails.LOGIN_FORM_COLORDEPTH_KEY%>"].value = screen.colorDepth;

	processLoginForm(document.f.<%=passwordKey%>, document.f["<%=LoginUtils.PASSWORD_HASHED_PARAM%>"]);

	return true;
}
	
function processLoginForm(password, js)
{
	<%--
	var len = password.value.length;
	var str = "";
	for(var i = 0 ; i < len ; i++){
		str += "*";
	}
	--%>
	document.f["<%=LoginUtils.HASHED_PASSWORD_PARAM%>"].value = MD5(MD5(password.value) + "|<%=LoginUtils.generateChallenge(session)%>");
	password.value = "********";
	js.value = "1";
}

function reloadCaptcha(){
	document.getElementById("imgc").src = "../captcha.jpg?" + (new Date().getTime());
}
//-->
</script>
</head>

<body onload="document.f.<%=usernameKey%>.focus();history.go(1);" style="font-size:12px;">
  <div style="height:200px;">&nbsp;</div>
  <div id="d01">
  <form name='f' action="login.jsp" method='POST' onsubmit="return checklogin();">
  	<input type="hidden" name="action" value="login"/>
	<input type="hidden" name="<%=LoginUtils.HASHED_PASSWORD_PARAM%>"/>
	<input type="hidden" name="<%=LoginUtils.PASSWORD_HASHED_PARAM%>" value="0"/>
	<input type="hidden" name="<%=RFSWebAuthenticationDetails.LOGIN_FORM_RESOLUTION_KEY%>"/>
	<input type="hidden" name="<%=RFSWebAuthenticationDetails.LOGIN_FORM_COLORDEPTH_KEY%>"/>
	<table border="0" cellspacing="0" cellpadding="3" style="margin:auto;width:420px;border:1px solid #000;border-collapse:collapse">
        <tr valign="top">
        	<td colspan="2" align="center" style="font-size:24px;">  
			系统登录
			</td>
        </tr>
          <%if(hasError){%>
		  <tr>
            <td align="right">&nbsp;</td>
            <td style="color:red;font-size:12px;">
            <img src="images/warn-16x16.gif" width="16" height="16" border="0" alt="" />
            	&nbsp;<%
			Exception e = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			if(e != null){
				//java.io.PrintWriter pw = new java.io.PrintWriter(out);
				//e.printStackTrace(pw);
				//out.println(e);
				out.println(e.getMessage());
			}
			%></td>
          </tr>
		  <%}%>

		  <tr>
            <td align="right">用户名：</td>
            <td><input name="<%=usernameKey%>" type="text" class="text" size="25" value="${SPRING_SECURITY_LAST_USERNAME}"></td>
          </tr>
          <tr>
            <td align="right">密　码：</td>
            <td><input name="<%=passwordKey%>" type="password" class="text" size="25"></td>
          </tr>
          <tr>
            <td align="right">验证码：</td>
            <td><input name="<%=captchaKey%>" type="text" class="text" size="25" maxlength="8"><br/><span style="color:gray;">(不区分大写小)</span></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><a href="#" onclick="reloadCaptcha();return false;"> <img id="imgc" src="../captcha.jpg" title="看不清？点击更换图片！" border="0" width="218" height="48"/></a></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input name="Submit1" type="submit" class="button" value="提 交">
              <input name="Submit2" type="reset" class="button" value="重 输"></td>
          </tr>
        </table>
	</form>
</div>
</body>
</html>


