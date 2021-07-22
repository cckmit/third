<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.security.impl.*,
	org.opoo.apps.module.*,
	org.opoo.apps.lifecycle.*,
	org.apache.struts2.config.DefaultSettings,
	java.util.*,
	org.opoo.apps.bean.core.*,
	org.opoo.apps.dao.*,
	org.opoo.apps.*,
	org.opoo.cache.*,
	org.opoo.apps.database.*,
	org.opoo.apps.id.sequence.*,
	org.opoo.apps.license.*,
	org.opoo.apps.lifecycle.*,
	java.text.DecimalFormat,
	com.tangosol.net.Cluster, 
	com.tangosol.net.Member,
	java.text.*"
%><%
//request.setCharacterEncoding("iso-8859-1");

String action = request.getParameter("action");


if("changeState".equals(action)){
	String state = request.getParameter("state");
	if("r".equals(state)){
		Application.setApplicationState(ApplicationState.MAINTENANCE_STARTED, ApplicationState.RUNNING);
		showMessage(out, "已经改为运行状态");
		return;
	}
	if("m".equals(state)){
		Application.setApplicationState(ApplicationState.RUNNING, ApplicationState.MAINTENANCE_STARTED);
		showMessage(out, "已经改为维护状态");
		return;
	}
}


java.util.Random ran = new java.util.Random();
int ranni = ran.nextInt(9999);
session.setAttribute("validationKey", String.valueOf(ranni));
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

<style>
.warn
{
	color:red;
	padding-left:100px;
}
</style>










<h1><a href="../logout.jsp">退出</a>  <a href="?">当前页面</a>  <a href="settings-module.jsp">模块管理</a>  <a href="system-cache.jsp">缓存管理</a>
<a href="system-properties.jsp">属性管理</a>
</h1>


<%
long THIRTY_DAYS_TIME = 2592000000L;//30 * 24 * 60 * 60 * 1000;
long THREE_DAYS_TIME = 259200000L;//3 * 24 * 60 * 60 * 1000;
SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
AppsLicenseManager licenseManager = Application.getContext().getLicenseManager();
AppsLicense license = licenseManager.getAppsLicense();
Date ed = license.getExpirationDate();
if(ed != null){
	ed = new Date(ed.getTime() + 24 * 60 * 60 * 1000 - 1);
	Date show = new Date(ed.getTime() + 1);
	long time = ed.getTime() - System.currentTimeMillis();
	if(time <= THIRTY_DAYS_TIME && time > 0){
%>
<p><div class="warn"><img src="images/warn-16x16.gif" width="16" height="16" border="0" alt="" />&nbsp;License 将于<%=format.format(show)%>零时过期，请及时处理。</div></p>
<%		
	}else if(time <= 0){
		%>
<p><div class="warn"><img src="images/error-16x16.gif" width="16" height="16" border="0" alt="" />&nbsp;License 已过期！！</div></p>
		<%
	}
}
%>


<p>
应用程序状态：<%=Application.getApplicationState()%>  
<%if(Application.getApplicationState() == ApplicationState.RUNNING){%>
<a href='?action=changeState&state=m'>更改应用状态为 <b>“系统维护”</b></a>
<%}%>
<%if(Application.getApplicationState() == ApplicationState.MAINTENANCE_STARTED){%>
<a href='?action=changeState&state=r'>更改应用状态为 <b>“正常运行”</b></a>
<%}%>


<p>&nbsp;<p>
<table border="1">
<caption>部分系统属性</caption>
<tr>
	<td>ServletContext</td>
	<td><%=session.getServletContext().getClass().getName()%></td>
</tr>
<tr>
	<td>ClassLoader</td>
	<td><%=Thread.currentThread().getContextClassLoader().getClass().getName()%></td>
</tr>
<tr>
	<td valign="top">验证图片示例</td>
	<td><a href="../confirmation/image" target="_blank"><img src="../confirmation/image" width="290" height="80" border="0" alt=""></a></td>
</tr>
<tr>
	<td>Jsp ClassLoader</td>
	<td><%=this.getClass().getClassLoader()%></td>
</tr>
<tr>
	<td>Jsp Factory</td>
	<td><%=javax.servlet.jsp.JspFactory.getDefaultFactory()%></td>
</tr>
<tr>
	<td>Jsp Super</td>
	<td><%=(this.getClass().getSuperclass())%></td>
</tr>
<tr>
	<td>DataSource</td>
	<td><%=Application.getContext().getBean("dataSource")%></td>
</tr>
<tr>
	<td>AppsHome</td>
	<td><%=AppsHome.getAppsHome()%></td>
</tr>
<tr>
	<td>DetaSourceProvider</td>
	<td><%=DataSourceManager.getDataSourceProvider()%></td>
</tr>
<tr>
	<td>Is Senior Member</td>
	<td><%=org.opoo.apps.cache.CacheFactory.isSeniorClusterMember()%></td>
</tr>
</table>





<p>
<table border="1">
<caption>Struts2 部分属性</caption>
<tr>
	<td>编码(struts.i18n.encoding, UTF-8)</td>
	<td><%=DefaultSettings.get("struts.i18n.encoding")%></td>
</tr>
<tr>
	<td>Locale(struts.locale, zh_CN)</td>
	<td><%=DefaultSettings.get("struts.locale")%></td>
</tr>
<tr>
	<td>附件最大限制</td>
	<td><%=DefaultSettings.get("struts.multipart.maxSize")%></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
</table>
<p>


<%
PresenceManagerImpl pm = Application.getContext().get("presenceManager", PresenceManagerImpl.class);
String sf = request.getParameter("pm_sf");
String asc = request.getParameter("pm_asc");
int psf = (sf == null) ? 0 : Integer.parseInt(sf);
boolean pasc = "1".equals(asc);

%>

<table border="1">
<caption>在线用户: <%=pm.getOnlineUserCount()%></caption>
<tr><th>
<%
if("0".equals(sf) && "1".equals(asc)){
	out.println("<a href='?pm_sf=0&pm_asc=0' title='倒序排列'>用户名</a>");
}else{
	out.println("<a href='?pm_sf=0&pm_asc=1' title='正序排列'>用户名</a>");
}
%>

<!-- 用户名 -->

</th><th>状态</th><th>最后活动时间</th><th>
<%
if("1".equals(sf) && "1".equals(asc)){
	out.println("<a href='?pm_sf=1&pm_asc=0' title='倒序排列'>登录时间</a>");
}else{
	out.println("<a href='?pm_sf=1&pm_asc=1' title='正序排列'>登录时间</a>");
}
%>

<!-- 登录时间 -->
</th><th>IP</th><th>SessionId</th><th>所在节点</th></tr>
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
			out.println("<td>" + (np.getStatus() == 0 ? "在线" : "离开") + "</td>");
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


<%
//Attachment att = Application.getContext().get("attachmentDao", AttachmentDao.class).get(1238982865231L);
%>






<p>


<%
List<String> names = AppsGlobals.getPropertyNames();
%>
<table border="1">
<caption>属性： <%=names.size()%></caption>
<tr><th>名称</th><th align="right">值</th></tr>
<%
for(String name: names){
	out.println("<tr><td>" + name + "</td><td align='right'>" + AppsGlobals.getProperty(name) + "</td></tr>");
}
%>
</table>


<p>
<%
java.util.Enumeration<String> paramNames = request.getParameterNames();

%>
<table border="1">
<caption>参数：</caption>
<tr><th>名称</th><th>值</th><th>值2</th></tr>
<%

while(paramNames.hasMoreElements()){
	String name = paramNames.nextElement();
	String value = request.getParameter(name);
	String value2 = value;
	if(value != null){
		value2 = new String(value.getBytes("ISO-8859-1"), "GBK");
	}
	System.out.println(value + " : " + value2);
	out.println("<tr><td>" + name + "</td><td>" + value + "</td><td >" + value2 + "</td></tr>");
}
%>
</table>


<form method="get" action="">
<input type="text" name="param1" value="中文"/>
<input type="submit" value="发送中文参数"/>
</form>



<p>
ID 生成器：<%=SequenceManager.getSequenceProvider()%> <a href="?action=generateSampleSequence&key=test_seq">生成示例ID</a><br>
<%
if("generateSampleSequence".equals(action)){
	String key = request.getParameter("key");
	SequenceProvider sp = SequenceManager.getSequenceProvider();
	Sequence sq = sp.getSequence(key);
	out.println("示例ID: ");
	for(int jj = 0 ; jj < 10 ; jj++){
		out.println(sq.getNext());
	}
}
%>


<pre>
<%=AppsLicenseManager.getInstance().getAppsLicense()%>

</pre>
