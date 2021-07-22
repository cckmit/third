<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%--@taglib prefix="s" uri="/struts-tags" --%>

<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><decorator:title default="实例"/></title><s:url value='/styles/main.css'/>
    <link href="<%=request.getContextPath()%>/styles/main.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body id="page-home">

<div id="header" class="clearfix">
	这是公共的头部分
	<hr />
	<span style="float:right;"><a href="<%=request.getContextPath()%>">回到首页</a></span>
</div>
        
<div class="clearfix">
	<h3>主工作区</h3>
	<decorator:body/>
	<hr />
</div>
            
        
<div id="footer" class="clearfix">
	Footer
</div>
 
</body>
</html>
