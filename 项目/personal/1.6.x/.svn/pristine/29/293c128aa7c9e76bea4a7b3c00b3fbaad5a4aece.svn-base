<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*,org.opoo.apps.*,org.opoo.cache.*,org.opoo.apps.database.*,org.opoo.apps.id.*"
%><%
java.util.Random ran = new java.util.Random();
int i = ran.nextInt(9999);



IdGeneratorProvider idg = Application.getContext().get("idGeneratorProvider", IdGeneratorProvider.class);
out.println(idg);

IdGenerator ig = idg.getIdGenerator("Users");
for(int j = 0 ; j < 100 ; j++){
	out.println(ig.getNext());
}

%>

