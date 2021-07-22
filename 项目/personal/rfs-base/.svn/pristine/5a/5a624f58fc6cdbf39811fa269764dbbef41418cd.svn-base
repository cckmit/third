<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.module.*,org.opoo.apps.lifecycle.*,org.apache.struts2.config.DefaultSettings,java.util.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head><title>配置 DBCP 数据源</title></head>
<body>


    <h1>配置 DBCP 数据源</h1>

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

	<c:if test="${success}">
		<div><b>数据库连接测试成功。</b></div>
	</c:if>


  <div class="apps-contentBox">


<div id="apps-error-box-js" class="apps-error-box" style="display:none"></div>
<div id="apps-warn-box-js" class="apps-info-box" style="display:none"></div>
<div id="apps-info-box-js" class="apps-success-box" style="display:none"></div>

<div class="apps-contentBox">


    <script language="JavaScript" type="text/javascript">
    var data = new Array();
    var idx = 0;
    data[idx++] = new Array('IBM DB2 v9',       'com.ibm.db2.jcc.DB2Driver',                'jdbc:db2://[host-name]:50000/[database-name]');
    data[idx++] = new Array('MSSQL',            'net.sourceforge.jtds.jdbc.Driver',         'jdbc:jtds:sqlserver://[host-name]:1433/[database-name]');
    data[idx++] = new Array('MySQL',            'com.mysql.jdbc.Driver',                    'jdbc:mysql://[host-name]:3306/[database-name]');
    data[idx++] = new Array('Oracle',           'oracle.jdbc.driver.OracleDriver',          'jdbc:oracle:thin:@[host-name]:1521:[ORACLE_SID]');
    data[idx++] = new Array('PostgreSQL',       'org.postgresql.Driver',                    'jdbc:postgresql://[host-name]:5432/[database-name]');
    data[idx++] = new Array('HSQLDB',			'org.hsqldb.jdbcDriver',				'jdbc:hsqldb:hsql://[host-name]:[port]/');
	function populate(i) {

		//if(i >= data.length) return;
        
		if (i != '' && i != "custom") {
            document.dbform.elements['driverClassName'].value=data[i][1];
            document.dbform.elements['url'].value=data[i][2];
            if (document.dbform.elements['driverClassName'].value == 'com.mysql.jdbc.Driver') {
                document.getElementById('mysql-setup').style.display = "";
            } else {
                document.getElementById('mysql-setup').style.display = "none";
            }
        } else {
            document.getElementById('mysql-setup').style.display = "none";
        }
    }
	
    
    var submitted = false;
    </script>

    <form action="setup.thirdpartydatasource.jspa" method="post" name="dbform">

    <table cellpadding="3" cellspacing="2" border="0">

    <tr>
        <td width="1%" align="right" valign="top" nowrap>
            <label for="fpreset">数据库:</label>
        </td>
        <td width="99%">
            <select size="1" name="presets" onchange="populate(this.options[this.selectedIndex].value)" id="fpreset">
                <option value="">选择数据库...

                    <option value="0"> &#149; IBM DB2 v9</option>
                    <option value="1"> &#149; MSSQL</option>
                    <option value="2"> &#149; MySQL</option>
                    <option value="3"> &#149; Oracle</option>
                    <option value="4"> &#149; PostgreSQL</option>
					<option value="5"> &#149; HSQLDB</option>
                <option value="custom">Custom</option>
            </select>

            <div id="mysql-setup" class="apps-info-box" style="display:none; margin-top:30px;">
                <br clear="all" />
                <p class="apps-setup-error-text">Note: If you want to allow attachments greater than 1MB in size, youll need to set the max_allowed_packet variable in MySQL to a value greater than 1MB. The <a href="http://dev.mysql.com/doc/">MySQL documentation</a> has <a href="http://dev.mysql.com/doc/refman/5.0/en/packet-too-large.html">the details</a>.</p>
            </div>
        </td>
    </tr>

    <tr>
        <td width="1%" align="right" nowrap>
            <label for="fdriver">JDBC 驱动类:</label>
        </td>
        <td width="99%">
            <input type="text" name="driverClassName" size="50" maxlength="150" value="${driverClassName}" id="fdriver">

			<span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The valid classname of your JDBC driver, ie: com.mydatabase.driver.MyDriver.', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>
        </td>
    </tr>


    <tr>
        <td width="1%" align="right" nowrap>
            <label for="furl">数据库连接 URL:</label>
        </td>
        <td width="99%">
            <input type="text" name="url" size="50" maxlength="250" value="${url}" id="furl">

			<span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The valid URL used to connect to your database, ie: jdbc:mysql://host:port/database', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>


        </td>
    </tr>

    <tr><td colspan="2">&nbsp;</td></tr>

    <tr>
        <td width="1%" align="right" nowrap>
            <label for="fusername">用户名:</label>
        </td>
        <td width="99%">
            <input type="text" name="username" size="20" maxlength="50" value="${username}" id="fusername">

            <span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The user used to connect to your database - note, this may not be required and can be left blank.', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>

        </td>
    </tr>

    <tr>
        <td width="1%" align="right" nowrap>
            <label for="fpassword">密码:</label>
        </td>
        <td width="99%">
            <input type="password" name="password" size="20" maxlength="50" value="${password}" id="fpassword">

			<span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The password for the user account used for this database - note, this may not be required and can be left blank.', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>


        </td>
    </tr>

    <tr><td colspan="2">&nbsp;</td></tr>        

    <tr>
        <td width="1%" align="right" nowrap>
            <label for="fmin">Max Active:</label>
        </td>
        <td width="99%">
            <input type="text" name="maxActive" size="5" maxlength="5" value="${maxActive}" id="fmin">

			<span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The minimum and maximum number of database connections the connection pool should maintain.', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>


        </td>
    </tr>

    <tr>
        <td width="1%" align="right" nowrap>
            <label for="fmin">Max Idle:</label>
        </td>
        <td width="99%">

            <input type="text" name="maxIdle" size="5" maxlength="5" value="${maxIdle}" id="fmax">

			<span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                'The minimum and maximum number of database connections the connection pool should maintain.', 'styleClass',
                'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>


        </td>
    </tr>


    <tr>
        <td width="1%" align="right" nowrap>
            <label for="ftime">Max Wait:</label>
        </td>
        <td width="99%" nowrap>
            <table cellspacing='0' cellpadding='0' border='0'>
            <tr>
                <td>
                    <input type="text" name="maxWait" size="5" maxlength="5" value="${maxWait}" id="ftime">
                </td>
                <td>
                    &nbsp;(毫秒)
                </td>
                <td>
                    <span class="apps-setup-helpicon" onmouseover="domTT_activate(this, event, 'content',
                        'The time (in days) before connections in the connection pool are recycled.', 'styleClass',
                        'appsTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);"></span>

                </td>
            </tr>
            </table>
        </td>
    </tr>

    </table>

    <div align="right">


        <input type="submit" name="method:test" value="测试连接" />
        <input type="submit" id="submitContinue" <c:if test="${!success}">disabled="true"</c:if> value="继续">

        <br />注意，连接数据库可能需要 30-60 秒的时间，请耐心等待。   </div>

    </form>

</div>

</body>
</html>

