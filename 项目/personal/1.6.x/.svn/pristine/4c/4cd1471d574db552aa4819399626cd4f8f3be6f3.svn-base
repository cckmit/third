<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK" import="org.opoo.apps.lifecycle.*,org.opoo.apps.module.*,org.opoo.apps.util.*,java.util.*,org.opoo.apps.security.*,org.springframework.security.context.*"%>


login ok
<%
/*
Thread thread = Thread.currentThread();
ModuleManager mgr = Application.getContext().getModuleManager();
ClassLoader parent = thread.getContextClassLoader();
if (parent == null)
	parent = this.getClass().getClassLoader();
Collection<ClassLoader> loaders = mgr.getClassLoaders();
ClassLoader loader = new ChainingClassLoader(parent, loaders);

thread.setContextClassLoader(loader);
*/
%>
<%//=org.opoo.apps.modules.als.web.ViewLicenseAction.class%><p>

<%=UserHolder.getUser().getLastLoginTime()%> <br>
<%=UserHolder.getUser().getLoginTime()%> <br>

<%=SecurityContextHolder.getContext().getAuthentication()%>
