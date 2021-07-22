<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>系统正在维护</title>
<style type="text/css">

body {
	margin: 20px;
	padding: 0;
	background: #fff;
	font-family: arial, helvetica, sans-serif;
	}

#apps-body {
	display: block;

    padding: 17px 28px 15px 28px;
	}
#apps-body h1 {
	font-size: 13pt;
	margin: 0;
	padding: 0 0 14px 0;
	}
.apps-contentBox {
	display: block;
	margin: 5px 0 20px 0;
	padding: 15px;
	border: 1px solid #dcdcdc;
	background: #f5f5f5;
	-moz-border-radius: 5px;
    }
.apps-contentBox p.restart-required {
    color:green;
    font-weight: bold;
    font-size:10pt;
    position: relative;
}



</style>    

</head>
<body>
<div id="apps-body">
    <h1>系统维护</h1>
    <div class="apps-contentBox">
        <p class="restart-required">系统正在维护中，请稍后访问。</p>
    </div>
</div>
</body>
</html>
