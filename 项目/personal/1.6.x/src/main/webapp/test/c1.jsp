<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*,org.opoo.apps.dao.hibernate3.*,org.opoo.apps.*"
%><%
AttachmentDao attachmentDao = Application.getContext().get("attachmentDao", AttachmentDao.class);
AttachmentHibernateDao dao = (AttachmentHibernateDao) attachmentDao;

String action = request.getParameter("action");
if("1".equals(action)){
	Attachment obj = attachmentDao.get(1238982865231L);
	if(obj != null){
		obj.setFileName(System.currentTimeMillis() + ".jpg");
		attachmentDao.update(obj);
	}
	response.sendRedirect("c1.jsp");
}else if("2".equals(action)){
	String hql = "update Attachment set fileName=? where id=?";
	dao.getQuerySupport().executeUpdate(hql, new Object[]{System.currentTimeMillis() + ".jpg", 1238982865231L});
	response.sendRedirect("c1.jsp");
}else if("3".equals(action)){
	AppsGlobals.setProperty("doCusterTest", "test-" + System.currentTimeMillis());
	response.sendRedirect("c1.jsp");
}



Attachment att = attachmentDao.get(1238982865231L);
%>
<h1>Test Hibernate L2 cache</h1>



<p><p>
ID: <%=(att != null ? att.getId() : 0L)%><br>
FileName: <%=(att != null ? att.getFileName() : "NULL")%><br>
<!--
<p><a href="?action=1">Set Attachment.fileName as System.currentTimeMillis() using hibernate.update();</a>

<p><a href="?action=2">Set Attachment.fileName as System.currentTimeMillis() using update hql;</a>
-->

<p>
<hr>
<p>
<br>

Property Value: <%=AppsGlobals.getProperty("doCusterTest")%>
<p>
<a href="?action=3">Set new doCusterTest value!</a>


