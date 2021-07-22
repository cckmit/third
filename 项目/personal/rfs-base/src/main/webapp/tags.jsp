<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%--@taglib prefix="s" uri="/struts-tags" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="demo" tagdir="/WEB-INF/tags/demo" %>



<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>用户信息</title>
	<s:head />
</head>
<body>

	这些TAG是以JSP的形式编写的，而不是JAVA类，存储在/WEB-INF/tags目录里。
	<tags:part/>

	<tags:wrapper a="1" b="2" c="3">
	<p> Wrapped Content </p>
	</tags:wrapper>


	<jsp:include page="/WEB-INF/jsp/content.jspf">
		<jsp:param name="a" value="1"/>
		<jsp:param name="b" value="2"/>
		<jsp:param name="c" value="3"/>
	</jsp:include>



	<demo:dynamicAttr separator=" : " x="aaaa" first="1" second="2" third="3" listTag="ol" itemTag="li" fourth="4" fifth="5" sixth="6"/>


<demo:varAttr v="x"/>

<p> JSP:x = ${x} </p>




<demo:fragmentAttr attr1="value1" attr2="value2">
<jsp:attribute name="template">
<p> Template:${data} </p>
</jsp:attribute>
<jsp:body>
<p> Body Content:${data} </p>
</jsp:body>
</demo:fragmentAttr>



</body>
</html>
	