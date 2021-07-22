<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,
org.opoo.apps.lifecycle.*,
org.apache.struts2.config.DefaultSettings,
java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>配置内置数据源</title></head>
<body>


    <h1>配置内置数据源</h1>

    <p><!-- 本功能用于配置数据源。 -->
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


 <div class="apps-contentBox">

    <c:if test="${empty actionErrors}" >

        <table cellspacing='3' cellpadding='2' border='0'>
        <tr>
            <td>评估版数据源配置成功。</td>
        </tr>
        </table>

        <form action="setup.datasource!continue.jspa">

			<div align="right"><input type="submit" value="继续"></div>

        </form>

    </c:if>

</div>

</body>
</html>

