<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*"
%>
<h1>Test Hibernate L2 cache</h1>

<%
Attachment att = Application.getContext().get("attachmentDao", AttachmentDao.class).get(1238982865231L);

%>
<p><p>
ID: <%=(att != null ? att.getId() : 0L)%><br>
FileName: <%=(att != null ? att.getFileName() : "NULL")%><br>
