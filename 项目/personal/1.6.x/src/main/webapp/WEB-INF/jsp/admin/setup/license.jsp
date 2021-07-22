<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>查看授权许可</title></head>
<body>


    <h1>查看授权许可</h1>

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


    <div class="apps-contentBox">

        <c:if test="${not empty license}" >
            <table cellpadding="3" cellspacing="2" border="1">
            <c:if test="${license.ID > 0}">
            <tr valign="top">
                <td nowrap="nowrap" align="right">
                    ID:
                </td>
                <td>${license.ID}</td>
            </tr>
            </c:if>
            <tr>
                <td nowrap="nowrap" align="right">
                     类型:
                </td>
                <td>
                    ${license.type}
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right">
                    授权产品:
                </td>
                <td>
                    ${license.name}
                </td>
            </tr>
			<tr>
                <td nowrap="nowrap" align="right">
                    授权版本:
                </td>
                <td>
                    ${license.version.versionString}
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right">
                     创建日期:
                </td>
                <td>
                    ${license.creationDate}
                </td>
            </tr>
            <c:if test="${not empty license.expirationDate}">
            <tr>
                <td nowrap="nowrap" align="right">
                    License 过期时间:
                </td>
                <td>
                    ${license.expirationDate}
                </td>
            </tr>
            </c:if>
            <tr>
                <td nowrap="nowrap" align="right">
                    授权给:
                </td>
                <td>
                    ${license.client.name}(${license.client.company})
                </td>
            </tr>
            <c:if test="${not empty license.grantedIP}">
            <tr>
                <td nowrap="nowrap" align="right">
                    授权IP:
                </td>
                <td>
                    ${license.grantedIP}
                </td>
            </tr>
            </c:if>
<!-- 			<tr>
                <td nowrap="nowrap" align="right">
                    是否开发版:
                </td>
                <td>
                    ${license.devMode}
                </td>
            </tr> -->
            </table>

            <br>

         
			<div class="apps-description" style="padding-bottom:10px;">
                <table cellspacing='0' cellpadding='0' border='0'>
                <tr>
                    <td>
                        <form action="<s:url value='/admin/setup/setup.license.jspa' />" method="post">
                            <input type="submit" name="continue" value="继续" />
                            <input type="hidden" name="evaluation" value="false" />
                        </form>
                    </td>
                </tr>
                </table>
            </div>


		</c:if>
</div>
    
    
		<c:if test="${empty license}">
    		<p style="padding:30px;">
			<font color="red">
    			<b>没有有效的授权许可。请尝试将授权许可Key插入电脑，或者联系管理员确认网络授权协议。
					<br/>
					确认后重新启动应用。
				</b>
    		</font>
			</p>        
	 	</c:if>

</body>
</html>

