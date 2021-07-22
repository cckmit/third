<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>配置 Hibernate 3</title></head>
<body>


    <h1>配置 Hibernate 3</h1>

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


    <form action="<s:url action='setup.hibernate' />" method="post">

	<table cellpadding="3" cellspacing="2" border="0">
            <tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    Hibernate 3 方言:
                </td>
                <td>
                    <input type="text" name="dialect" size="60" maxlength="100" value="${dialect}">
                </td>
            </tr>
            <tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    Hibernate 3 属性:
                </td>
                <td>
                    <textarea id="hibernateProperties" name="hibernateProperties" cols="60" rows="16" wrap="no">${hibernateProperties}</textarea>
					<br>每个配置一行，参看 hibernate 配置手册。
                </td>
            </tr>
            </table>

    <div align="right"><input type="submit" value="继续"></div>

    </form>


  

</div>

</body>
</html>

