<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>��������Դ</title></head>
<body>


    <h1>��������Դ</h1>

    <p>������������������Դ��
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
            <label for="rb02"><b>��׼�����ݿ����ӣ�DBCP��</b></label><br />
ʹ��һ���ⲿ�����ݿ⣬��ʹ��DBCP��Ϊ���ݿ����ӳء�</td>
    </tr>
    <tr>
        <td align="center" valign="top">
            <input type="radio" name="mode" value="jndi" id="rb03"<c:if test="${mode == 'jndi'}"> checked</c:if>>
        </td>
        <td>
            <label for="rb03"><b>JNDI ����Դ</b></label><br />
ʹ��Ӧ�÷������ж���� JNDI ����Դ��        </td>
    </tr>
    <tr>
        <td align="center" valign="top">
            <input type="radio" name="mode" value="embedded" id="rb01"<c:if test="${mode == 'embedded'}"> checked</c:if>>
        </td>
        <td>
            <label for="rb01"><b>���������ݿ�</b></label><br />
���ѡ���Ҫ�ⲿ���ݿ⣬ϵͳʹ����Ƕ�����ݿ⣬���������ò�����ϵͳ�����޷���ýϺõ����ܣ�������������</td>
    </tr>
    </table>

    <div align=""><input type="submit" value="����"></div>

    </form>


  

</div>

</body>
</html>

