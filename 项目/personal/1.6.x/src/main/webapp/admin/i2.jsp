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
	com.tangosol.net.Member"
%><%

%>
<%!
private void showMessage(javax.servlet.jsp.JspWriter out, String msg) throws java.io.IOException{
	StringBuffer sb = new StringBuffer();
	sb.append("<script language='JavaScript'>\n");
	sb.append("<!--\n");
	sb.append("alert('" + msg + "');\n");
	sb.append("location.href = '?';\n");
	sb.append("//-->\n");
	sb.append("</script>\n");
	out.println(sb.toString());
	System.out.println(msg);
}
%>












<h1><a href="../logout.jsp">�˳�</a>  <a href="?">��ǰҳ��</a>  <a href="settings-module.jsp">ģ�����</a>  <a href="system-cache.jsp">�������</a>
<a href="system-properties.jsp">���Թ���</a>
</h1>


<%
PresenceManagerImpl pm = Application.getContext().get("presenceManager", PresenceManagerImpl.class);
String sf = request.getParameter("pm_sf");
String asc = request.getParameter("pm_asc");
int psf = (sf == null) ? 0 : Integer.parseInt(sf);
boolean pasc = "1".equals(asc);

%>
<%=pm%>
<%=UserHolder.getUser()%>

<table border="1">
<caption>�����û�: <%=pm.getOnlineUserCount()%></caption>
<tr><th>
<%
if("0".equals(sf) && "1".equals(asc)){
	out.println("<a href='?pm_sf=0&pm_asc=0' title='��������'>�û���</a>");
}else{
	out.println("<a href='?pm_sf=0&pm_asc=1' title='��������'>�û���</a>");
}
%>

<!-- �û��� -->

</th><th>״̬</th><th>���ʱ��</th><th>
<%
if("1".equals(sf) && "1".equals(asc)){
	out.println("<a href='?pm_sf=1&pm_asc=0' title='��������'>��¼ʱ��</a>");
}else{
	out.println("<a href='?pm_sf=1&pm_asc=1' title='��������'>��¼ʱ��</a>");
}
%>

<!-- ��¼ʱ�� -->
</th><th>IP</th><th>SessionId</th><th>�ڵ�</th></tr>
<%

//if(pm.getOnlineUserCount() > 0){
	Iterator<OnlineUser> users = pm.getOnlineUsers(pasc, psf, 0, 100);
	while(users.hasNext()){
		OnlineUser user = users.next();
		Presence pr = user.getLastPresence();
		List<Presence> nps = user.getPresences();
		out.println("<tr><td rowspan='" + nps.size() + "'>" + user.getUsername() + "(" + nps.size() + ")" + "</td>");
		int n = 0;
		for(Presence np: nps){
			if(n > 0){
				out.println("<tr>");
			}
			out.println("<td>" + (np.getStatus() == 0 ? "����" : "����") + "</td>");
			out.println("<td>" + np.getLastUpdateTime() + "</td>");
			out.println("<td>" + np.getLoginTime() + "</td>");
			out.println("<td>" + np.getRemoteAddr() + "</td>");
			out.println("<td>" + np.getSessionId() + "</td>");
			out.println("<td>" + np.getNodeId() + "</td></tr>");
			n++;
		}
	}
//}
%>
</table>


<%=pm.getPresenceList()%>

