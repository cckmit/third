<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>配置 JNDI 数据源</title></head>
<body>


    <h1>配置JNDI数据源</h1>

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

      <form action="setup.jndidatasource.jspa" name="jndiform">

<c:if test="${empty bindings}">
    JNDI 数据源名称:
    <input type="text" name="jndiName" size="30" maxlength="100" value="${jndiName}">


    <br /><br />
    <p class="apps-setup-error-text">注意: If you are using MySQL and want to allow attachments greater than 1MB in size, youll need to set the max_allowed_packet variable in MySQL to a value greater than 1MB. The <a href="http://dev.mysql.com/doc/">MySQL documentation</a> has <a href="http://dev.mysql.com/doc/refman/5.0/en/packet-too-large.html">the details</a>.</p>
</c:if>


<c:if test="${not empty bindings}">
 <table cellpadding="3" cellspacing="2" border="0">
    <tr>
        <td><input type="radio" name="jndiNameMode" value="custom"></td>
        <td>
            <span onclick="document.jndiform.jndiName.focus();"
            >自定义 JNDI 名称:</span>
            &nbsp;
            <input type="text" name="jndiName" size="30" maxlength="100"
             value="${jndiName}"
             onfocus="this.form.jndiNameMode[0].checked=true;">
        </td>
    </tr>
		<c:forEach var="binding" items="${bindings}">
            <tr>
                <td><input type="radio" name="jndiNameMode" value="java:comp/env/jdbc/${binding.name}" id="rb${binding.name}"></td>
                <td>
                    <label for="rb${binding.name}" style="font-weight:normal">java:comp/env/jdbc/<b>${binding.name}</b></label>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>


    <div align="right">
        <input type="submit" value="Continue">
        <br />
		注意，连接数据库可能需要 30-60 秒的时间，请耐心等待。 </div>

    </form>

</div>

</body>
</html>

