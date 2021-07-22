<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>配置数据源</title></head>
<body>


    <h1>配置数据源</h1>

    <p>本功能用于配置数据源。
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


    <form action="<s:url action='setup.datasource' />" method="post">

	<table cellpadding="3" cellspacing="2" border="0">
    <tr>
        <td align="center" valign="top">
            <input type="radio" name="mode" value="thirdparty" id="rb02"<c:if test="${mode == 'thirdparty'}"> checked</c:if>>
        </td>
        <td>
            <label for="rb02"><b>标准的数据库连接（DBCP）</b></label><br />
使用一个外部的数据库，并使用DBCP作为数据库连接池。</td>
    </tr>
    <tr>
        <td align="center" valign="top">
            <input type="radio" name="mode" value="jndi" id="rb03"<c:if test="${mode == 'jndi'}"> checked</c:if>>
        </td>
        <td>
            <label for="rb03"><b>JNDI 数据源</b></label><br />
使用应用服务器中定义的 JNDI 数据源。        </td>
    </tr>
    <tr>
        <td align="center" valign="top">
            <input type="radio" name="mode" value="embedded" id="rb01"<c:if test="${mode == 'embedded'}"> checked</c:if>>
        </td>
        <td>
            <label for="rb01"><b>评估版数据库</b></label><br />
这个选项不需要外部数据库，系统使用内嵌的数据库，很容易配置并启动系统，但无法获得较好的性能，仅用于评估。</td>
    </tr>
    </table>

    <div align=""><input type="submit" value="继续"></div>

    </form>


  

</div>

</body>
</html>

