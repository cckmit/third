<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,
	org.opoo.apps.module.*,
	org.opoo.apps.lifecycle.*,
	org.apache.struts2.config.DefaultSettings,
	java.util.*,
	org.opoo.apps.bean.core.*,
	org.opoo.apps.dao.*,
	org.opoo.apps.*,
	org.opoo.apps.security.impl.*,
	org.opoo.cache.*,
	org.opoo.apps.database.*,
	org.opoo.apps.id.sequence.*,
	org.opoo.apps.license.*,
	org.opoo.apps.lifecycle.*,
	java.text.DecimalFormat,
	com.tangosol.net.Cluster, 
	com.tangosol.net.Member,
	java.lang.reflect.*,
	org.apache.struts2.dispatcher.*,
	org.apache.struts2.config.*,
	org.apache.struts2.*,
	org.opoo.apps.web.struts2.dispatcher.*,
	org.apache.struts2.dispatcher.multipart.*"
%><%
Field[] fields = Dispatcher.class.getDeclaredFields();
out.println(fields.length);
for(Field f: fields){
	if(Modifier.isStatic(f.getModifiers())){
		f.setAccessible(true);
		out.print(f);
		out.println(" : " + f.get(null));
	}
}


%>
<p>
<p>
<%=DefaultSettings.get(StrutsConstants.STRUTS_MULTIPART_SAVEDIR)%>

<p>
<p>
<%
		Dispatcher d = AppsFilterDispatcher.getDispatcher();
		if(d != null){
			MultiPartRequest multi = d.getContainer().getInstance(MultiPartRequest.class);
			if(multi instanceof JakartaMultiPartRequest){
				JakartaMultiPartRequest pr = (JakartaMultiPartRequest) multi;
				Field[] fs = JakartaMultiPartRequest.class.getDeclaredFields();
				for (Field f : fs) {
					f.setAccessible(true);
					out.print(f);
					out.println(" : " + f.get(pr));
				}
			}
		}


%>
