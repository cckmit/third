<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*,
	org.opoo.apps.bean.core.*,org.opoo.apps.dao.*,org.opoo.apps.*,org.opoo.cache.*,org.opoo.apps.database.*"
%><%
java.util.Random ran = new java.util.Random();
int i = ran.nextInt(9999);
session.setAttribute("validationKey", String.valueOf(i));
%>

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
</table>




<p>

<table border="1">
<caption>已安装模块</caption>
<tr><th>模块</th><th>描述</th><th>版本</th><th>位置</th></tr>
<%
ModuleManager manager = Application.getContext().getModuleManager();
Collection<Module<?>> modules = manager.getModules();
for(Module<?> module: modules){
	ModuleMetaData meta = manager.getMetaData(module);
%>
<tr><td>
<%
String url = "";
if(meta.isSmallLogoExists()){
	url = request.getContextPath() + "/modules/" + meta.getName() + "/logo_small.png";
}else{
	url = request.getContextPath() + "/admin/images/plugin-16x16.gif";
}
out.println("<img src='" + url + "' border='0' title='模块'>");
%>
<%=meta.getName() %>
<%
if(meta.isReadmeExists()){
	out.println("<a href='" + request.getContextPath() + "/modules/" 
			+ meta.getName() 
			+ "/readme.html' target='_blank' title='README'><img src='"
			+ request.getContextPath() 
			+ "/admin/images/info-16x16.gif' border='0'></a>");
}
%>
</td>
<td><%=meta.getDescription() %></td>
<td><%=meta.getVersion() %></td>
<td><%=meta.getModuleDirectory() %></td></tr>

<%	
}

%>
</table>




<p>
<table border="1">
<caption>Struts2 部分属性</caption>
<tr>
	<td>编码</td>
	<td><%=DefaultSettings.get("struts.i18n.encoding")%></td>
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
PresenceManager pm = Application.getContext().getPresenceManager();
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

</th><th>IP</th><th>状态</th><th>最后活动时间</th><th>
<%
if("1".equals(sf) && "1".equals(asc)){
	out.println("<a href='?pm_sf=1&pm_asc=0' title='倒序排列'>登录时间</a>");
}else{
	out.println("<a href='?pm_sf=1&pm_asc=1' title='正序排列'>登录时间</a>");
}
%>

<!-- 登录时间 -->
</th></tr>
<%

//if(pm.getOnlineUserCount() > 0){
	Iterator<User> users = pm.getOnlineUsers(pasc, psf, 0, 100);
	while(users.hasNext()){
		User user = users.next();
		Presence pr = pm.getPresence(user);
		out.println("<tr><td>" + user.getUsername() + "</td>");
		out.println("<td>" + pr.getIPAddress() + "</td>");
		out.println("<td>" + (pr.getStatus() == 0 ? "在线" : "空闲") + "</td>");
		out.println("<td>" + pr.getLastUpdateTime() + "</td>");
		out.println("<td>" + pr.getLoginTime() + "</td></tr>");
		//out.println(user.getUsername() + " : " + pr.hashCode() + "/" + pr.getLastUpdateTime() + "/" + pr.getLoginTime() + "<br/>");
	}
//}
%>
</table>


<%
//Attachment att = Application.getContext().get("attachmentDao", AttachmentDao.class).get(1238982865231L);
%>
<p>



<%
org.opoo.cache.Cache[] caches = org.opoo.apps.cache.CacheFactory.getAllCaches();
//for(org.opoo.cache.Cache cache: caches){
//	out.println(cache.getName() + " " + cache.getCacheSize() + "<br/>");
//}
%>
<!-- <%=com.tangosol.net.CacheFactory.getCache("onlineUserCache").get("a")%> -->

<table border="1">
<caption>缓存： <%=caches.length%></caption>
<tr><th>名称</th><th align="right">大小</th><th>&nbsp;</th><th>&nbsp;</th></tr>
<%
for(org.opoo.cache.Cache cache: caches){
	out.println("<tr><td>" + cache.getName() + "</td><td align='right'>" + cache.getCacheSize()/1000.0 + "KB</td><td>" + ((CacheWrapper)cache).getWrappedCache().getClass().getName() + "</td><td><a href='?action=emptyCache&cacheName=" + cache.getName() + "'>清空</a></td></tr>");
}
%>
</table>


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
