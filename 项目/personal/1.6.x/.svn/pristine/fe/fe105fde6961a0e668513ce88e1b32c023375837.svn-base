<%@ attribute name="template" fragment="true" %>
<%@ attribute name="attr1" %>
<%@ attribute name="attr2" %>
<%@ variable name-given="data" scope="NESTED" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="data" value="${attr1}"/>
<jsp:invoke fragment="template"/>

<c:set var="data" value="${attr1} - ${attr2}"/>
<jsp:doBody/>

<c:set var="data" value="${attr2}"/>
<jsp:invoke fragment="template"/>

<c:set var="data" value="${attr2} - ${attr1}"/>
<jsp:doBody/>
