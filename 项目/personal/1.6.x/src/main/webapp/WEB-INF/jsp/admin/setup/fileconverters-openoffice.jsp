<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>OpenOffice.org 配置</title></head>
<body>


    <h1>OpenOffice.org 配置</h1>

    <p>
       <br />
       <br />
      
    </p>
	<c:if test="${not empty actionErrors}">
    <div class="error" style="color:red;">
		<c:forEach var="error" items="${actionErrors}">
			<i>${error}</i><br />
		</c:forEach>
    </div>
	</c:if>


  <div class="jive-contentBox">


    <form action="<s:url action='setup.openofficeconverters' />" method="post">

	<table cellpadding="3" cellspacing="2" border="0">
	<tr valign="top" id="license-key-row" >
		<td nowrap="nowrap" align="right">
			OpenOffice 服务地址:
		</td>
		<td>
			<input type="text" name="host" size="30" maxlength="100" value="${host}">
		</td>
	</tr>
	<tr valign="top" id="license-key-row" >
		<td nowrap="nowrap" align="right">
			OpenOffice 服务端口:
		</td>
		<td>
			<input type="text" name="port" size="5" maxlength="8" value="${port}">
		</td>
	</tr>
	</table>


    <div align="right"><input type="submit" value="继续"></div>

    </form>


  

</div>

</body>
</html>

