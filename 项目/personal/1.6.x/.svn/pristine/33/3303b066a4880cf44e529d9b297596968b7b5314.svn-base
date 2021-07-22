<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>启用文件格式转换功能</title></head>
<body>


    <h1>启用文件格式转换功能</h1>

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


    <form action="<s:url action='setup.fileconverters' />" method="post">

	<table cellpadding="3" cellspacing="2" border="0">
		<tr valign="top" id="license-key-row" >
			<td nowrap="nowrap" align="right">
			   &nbsp;
			</td>
			<td>
				<input type="checkbox" id="enabled" name="enabled" value="true"<c:if test="${enabled}"> checked</c:if>/>
				<label for="enabled">启用文件格式转换功能</label>
			</td>
		</tr>
   
	</table>

    <div align="right"><input type="submit" value="继续"></div>

    </form>


  

</div>

</body>
</html>

