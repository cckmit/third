<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*,org.opoo.apps.*,org.opoo.cache.*,org.opoo.apps.database.*,org.opoo.apps.id.sequence.*,
	org.springframework.security.ui.*,org.springframework.security.*,org.springframework.security.ui.webapp.*,
	org.springframework.context.*,org.springframework.web.context.support.*,java.util.*,
	org.springframework.security.config.*"
%>
<%


XmlWebApplicationContext wac = (XmlWebApplicationContext) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

//WebApplicationContext wac2 = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

String[] names = wac.getBeanDefinitionNames();
for(String name: names){
	out.println(name + " = " +"" + "<br/>");
}

Map entryPoints = wac.getBeansOfType(AuthenticationEntryPoint.class);

%>
<p>
<%=wac%>

<p>
<%=entryPoints%>

<p>
<%=BeanIds.MAIN_ENTRY_POINT%> : <%=wac.getBean(BeanIds.MAIN_ENTRY_POINT)%>
<br>
_basicAuthenticationEntryPoint : <%=wac.getBean("_basicAuthenticationEntryPoint")%>
<br>

