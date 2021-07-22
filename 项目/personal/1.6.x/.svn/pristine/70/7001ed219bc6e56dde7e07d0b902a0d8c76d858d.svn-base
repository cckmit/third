<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>系统通用设置</title></head>
<body>


    <h1>系统通用设置</h1>

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


    <form action="<s:url action='setup.settings' />" method="post">

	<div><b>日期时间格式设置</b></div>
	<table cellpadding="3" cellspacing="2" border="0">
	    	<tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    日期格式：
                </td>
                <td>
                    <input type="text" name="dateFormat" size="20" maxlength="100" value="${dateFormat}">
                </td>
            </tr>
	    	<tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    日期时间格式：
                </td>
                <td>
                    <input type="text" name="dateTimeFormat" size="20" maxlength="100" value="${dateTimeFormat}">
                </td>
            </tr>
	    	<tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    精简日期时间格式：
                </td>
                <td>
                    <input type="text" name="shortDateTimeFormat" size="20" maxlength="100" value="${shortDateTimeFormat}">
                </td>
            </tr>
	</table>
	<p><p>
	<div><b>其他设置（暂无）</b></div>

    <div align="right"><input type="submit" value="继续"></div>

    </form>


  

</div>

</body>
</html>

