<%@page 
	import="org.springframework.security.Authentication,
	org.springframework.security.context.SecurityContextHolder,
	org.opoo.apps.lifecycle.Application,
	org.opoo.apps.security.UserHolder,
	org.opoo.apps.security.User,
	org.opoo.apps.security.UserManager,
	org.opoo.apps.security.SecurityUtils"%><%

Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//如果需要切换回去用户
if(SecurityUtils.isGranted(auth, "ROLE_PREVIOUS_ADMINISTRATOR")){
	System.out.println("切换用户");
	response.sendRedirect("j_spring_security_exit_user");
	return;
}


User user = UserHolder.getNullableUser();
if(user != null){
	Application.getContext().getUserManager().logout(user);
}

session.invalidate();
SecurityContextHolder.clearContext();
response.sendRedirect("j_spring_security_logout");
%>
