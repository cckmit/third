<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="s" uri="/struts-tags"%><%@ page import="org.apache.commons.logging.*"%><%!
private static final Log log = LogFactory.getLog("jsp.exception");

%>{"message":"<s:property value="exception.message"/>","success":false}<%
Exception ex = (Exception)request.getAttribute("exception");
if(ex != null)
{
	if(ex instanceof org.springframework.security.AccessDeniedException){
		throw ex;
	}
	log.error("Action execute error", ex);
}
%>
