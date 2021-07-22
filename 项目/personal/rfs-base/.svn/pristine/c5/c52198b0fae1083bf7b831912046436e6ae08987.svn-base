<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*,org.opoo.apps.dao.hibernate3.*,org.opoo.apps.*,cn.redflagsoft.base.scheme.schemes.user.*,
	cn.redflagsoft.base.security.*"
%>
SA
<%=SampleSecuredService.class%>

<%
SampleSecuredService service = new SampleSecuredService();
service.doNonSecuredMethod();
service.doAdminMethod();
%>

