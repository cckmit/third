<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>��װ License</title></head>
<body>


    <h1>��װ License</h1>

    <p>���������ڰ�װ License ���滻���е� License �ļ���
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

        <c:if test="${not empty license && !changeLicense}" >
            <table cellpadding="3" cellspacing="2" border="1">
            <c:if test="${license.ID > 0}">
            <tr valign="top">
                <td nowrap="nowrap" align="right">
                    License ID:
                </td>
                <td>${license.ID}</td>
            </tr>
            </c:if>
            <tr>
                <td nowrap="nowrap" align="right">
                    License ����:
                </td>
                <td>
                    ${license.type}
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right">
                    ��Ȩ��Ʒ:
                </td>
                <td>
                    ${license.name}
                </td>
            </tr>
			<tr>
                <td nowrap="nowrap" align="right">
                    ��Ȩ�汾:
                </td>
                <td>
                    ${license.version.versionString}
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap" align="right">
                    License ��������:
                </td>
                <td>
                    ${license.creationDate}
                </td>
            </tr>
            <c:if test="${not empty license.expirationDate}">
            <tr>
                <td nowrap="nowrap" align="right">
                    License ����ʱ��:
                </td>
                <td>
                    ${license.expirationDate}
                </td>
            </tr>
            </c:if>
            <tr>
                <td nowrap="nowrap" align="right">
                    ��Ȩ��:
                </td>
                <td>
                    ${license.client.name}(${license.client.company})
                </td>
            </tr>
            <c:if test="${not empty license.grantedIP}">
            <tr>
                <td nowrap="nowrap" align="right">
                    ��ȨIP:
                </td>
                <td>
                    ${license.grantedIP}
                </td>
            </tr>
            </c:if>
			<tr>
                <td nowrap="nowrap" align="right">
                    �Ƿ񿪷���:
                </td>
                <td>
                    ${license.devMode}
                </td>
            </tr>
            </table>

            <br>

         
			<div class="apps-description" style="padding-bottom:10px;">
                <table cellspacing='0' cellpadding='0' border='0'>
                <tr>
                    <td>
                        <form action="<s:url value='/admin/setup/setup.license.jspa' />" method="post">
                            <input type="submit" name="method:input" value="��� License" />
                            <input type="hidden" name="changeLicense" value="true" />
                        </form>
                    </td>
                    <td>
                        <form action="<s:url value='/admin/setup/setup.license.jspa' />" method="post">
                            <input type="submit" name="continue" value="����" />
                            <input type="hidden" name="evaluation" value="false" />
                        </form>
                    </td>
                </tr>
                </table>
            </div>


		</c:if>
</div>
    
		<c:if test="${empty license || changeLicense}">
            <form action="<s:url value='/admin/setup/setup.license.jspa' />" method="post">
            <table cellpadding="3" cellspacing="2" border="0">
            <tr valign="top">
                <td nowrap="nowrap" align="right">
                    License ����:
                </td>
                <td>
                    <table cellspacing='0' cellpadding='2' border='0'>
                    <tr>
                        <td>
                            <input type="radio" id="evaluation0"
                                name="evaluation" value="false"<c:if test="${!evaluation}"> checked</c:if>>
                        </td>
                        <td>
                            <label for="evaluation0">��ҵ��/��ʽ��</label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="radio" id="evaluation1" disabled="true"  name="evaluation" value="true"/>

                        </td>
                        <td>
                            <label for="evaluation1">������ (�ݲ�֧��)</label>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr valign="top" id="license-key-row" >
                <td nowrap="nowrap" align="right">
                    License ����:
                </td>
                <td>
                    <textarea id="licenseString" name="licenseString"
                        cols="82" rows="12" wrap="virtual"></textarea>
                </td>
            </tr>
            </table>

            <br>

            <div id="button-row-validate" >
                <div class="apps-description" style="padding-bottom:10px;">
                    <input type="submit" name="method:set" value="��֤ License" />
                </div>
            </div>

            <div id="button-row-submit"  >
                <div class="apps-description" style="padding-bottom:10px;">
                    <input type="submit" name="continue" value="����" />
                </div>
            </div>

            </form>


	 </c:if>

</body>
</html>

