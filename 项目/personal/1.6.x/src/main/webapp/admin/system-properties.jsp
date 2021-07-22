<%@ page 
contentType="text/html;charset=GBK" 
pageEncoding="GBK"
import="org.opoo.apps.security.*,
	org.opoo.apps.module.*,
	org.opoo.apps.lifecycle.*,
	org.apache.struts2.config.DefaultSettings,
	java.util.*,
	org.opoo.apps.bean.core.*,
	org.opoo.apps.dao.*,
	org.opoo.apps.*,
	org.opoo.cache.*,
	org.opoo.apps.database.*,
	org.opoo.apps.id.sequence.*,
	org.opoo.apps.license.*,
	org.opoo.apps.lifecycle.*,
	org.apache.commons.lang.StringUtils"
%><%


String propName = request.getParameter("propName");
String propValue = request.getParameter("propValue");
boolean edit = request.getParameter("edit") != null;
boolean create = request.getParameter("create") != null;
boolean delete = request.getParameter("delete") != null;



Map<String, String> errors = new HashMap<String,String>();
if (create || edit) {
	if (propName == null || "".equals(propName.trim())) {
		errors.put("propName","");
	}
	if (propValue == null || "".equals(propValue.trim())) {
		errors.put("propValue","");
	}
	else if (propValue.length() > 3500) {
		errors.put("propValueLength","");
	}
	if (errors.size() == 0) {
		AppsGlobals.setProperty(propName.trim(), propValue.trim());
		//if (create) {
			response.sendRedirect("system-properties.jsp?success=true");
			return;
		//} else {
		//	response.getWriter().write("ok");
		//}
	}
}

if (delete) {
	if (propName != null) {
		AppsGlobals.deleteProperty(propName);
		response.sendRedirect("system-properties.jsp?success=true");
		return;
	}
}








List<String> properties = AppsGlobals.getPropertyNames();
%>
<style type="text/css">
.prop SPAN, .prop {
    font-size : 8pt !important;
    font-family : verdana, arial, helvetica, sans-serif !important;
}
.jive-error{
	color:red;
	padding-left:100px;
}
.jive-success{
	padding-left:100px;
}
</style>



<a href="index.jsp">管理首页</a>



<%  if (errors.size() > 0) { %>

    <div class="jive-error">
    <table cellpadding="0" cellspacing="0" border="0">
    <tbody>
        <tr><td class="jive-icon"><img src="images/error-16x16.gif" width="16" height="16" border="0" alt="" /></td>
        <td class="jive-icon-label">
        错误 -- 属性保存出错，看以下信息。
        </td></tr>
    </tbody>
    </table>
    </div><br />

<%  } else if ("true".equals(request.getParameter("success"))) { %>

    <div class="jive-success">
    <table cellpadding="0" cellspacing="0" border="0">
    <tbody>
        <tr><td class="jive-icon"><img src="images/success-16x16.gif" width="16" height="16" border="0" alt="" /></td>
        <td class="jive-icon-label">
        属性保存或删除成功。
        </td></tr>
    </tbody>
    </table>
    </div><br />

<%  } %>



<p>
<b>已设置属性：<%=properties.size()%></b>
<table cellpadding="0" cellspacing="0" border="1" width="100%">
<thead>
    <tr>
        <th nowrap="nowrap">属性</th>
        <th style="text-align:center;">编辑</th>
        <th style="text-align:center;">删除</th>
    </tr>
</thead>
<tbody>

    <%  if (properties.size() == 0) { %>

        <tr>
            <td colspan="4">
                没有属性。
            </td>
        </tr>

    <%  } %>

    <%  for (int i=0; i<properties.size(); i++) {
            String n = (String)properties.get(i);
            if (n.startsWith("renderFilter.") || n.startsWith("__apps.")) {
                continue;
            }
            boolean hidden = AppsGlobals.isHidden(n);
            String v = AppsGlobals.getProperty(n);
            v = StringUtils.replace(v, "\n", "<br />");
            //v = StringUtils.wordWrap(v, 60, AppsGlobals.getLocale());
    %>
        <tr id="jive-propertyrow-<%=i%>" class="<%= (n.equals(propName) ? "hilite" : "") %>" >

            <td class="prop">
                <span title="<%= n %>">
                <nobr><a href="#<%= n.replace(' ','-') %>"
                        onclick="togglePropertyForm('jive-property-<%= i %>');return false;"><%= n %></a> &nbsp;=&nbsp; </nobr>
                </span>

                <%  if (hidden) { %>
                    <span style="color:#999;"><i>hidden</i></span>
                <%  } else { %>
                    <span id="jive-propertyvalue-<%= i%>"><%= ("".equals(v) ? "&nbsp;" : v) %></span>

                <%  } %>
            </td>
            <td align="center"><a href="#" onclick="togglePropertyForm('jive-property-<%= i %>');return false;"
                ><img src="images/jive-icon-edit-16x16.gif" width="16" height="16" alt="点击编辑当前属性" border="0"></a
                >
            </td>
            <td align="center"><a href="#" onclick="return dodelete('<%= StringUtils.replace(n,"","") %>', <%= i%>);"
                ><img src="images/jive-icon-delete-16x16.gif" width="16" height="16" alt="点击删除当前属性" border="0"></a
                >
            </td>
        </tr>
        <tr id="jive-property-<%= i %>" style="display:none">
            <td colspan="3">
                <form action="system-properties.jsp" method="post" name="editform">

                <div class="jive-table">
                <table cellpadding="0" cellspacing="0" border="1" width="100%">
                <tbody>
                    <tr valign="top">
                        <td width="150">
                            属性名称:
                        </td>
                        <td>
                            <input type="hidden" class="jive-property-propname" id="jive-propName-<%= i %>"name="propName" value="<%= n %>">
                            <%= n %>

                            <span id="jive-propName-error-<%=i %>" class="jive-error-text" style="display:none"><br />请输入属性名称。</span>

                        </td>
                    </tr>
                    <tr valign="top">
                        <td>
                            属性值:
                        </td>
                        <td>
                            <% if (AppsGlobals.isHidden(n)) { %>
                                <span class="jive-error-text">
								隐藏属性不能被查看，要编辑隐藏属性，需要输入新值，点击“保存属性”。
                     
                                </span>
                                <br />
                                <textarea cols="70" rows="10" id="jive-propValue-<%= i %>" name="propValue" wrap="virtual" style="width:100%;font-family:verdana,arial,helvetica,sans-serif;font-size:8pt;"></textarea>
                            <% } else { %>
                                <textarea cols="70" rows="10" id="jive-propValue-<%= i %>" name="propValue" wrap="virtual" style="width:100%;font-family:verdana,arial,helvetica,sans-serif;font-size:8pt;"><%= (v != null ? AppsGlobals.getProperty(n) : "") %></textarea>
                            <% } %>

                            <span id="jive-propValue-error-<%=i %>" class="jive-error-text" style="display:none"><br />请输入属性值（小于3500个字符）。</span>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <input type="button" name="save" onclick="saveCurrentPropertyForm(<%= i%>, <%= AppsGlobals.isHidden(n)%>);" value="保存属性">
                            <input type="button" name="cancel" onclick="closeCurrentPropertyForm();" value="取消">
                        </td>
                    </tr>
                </tfoot>
                </table>
                </div>

                </form>

            </td>
        </tr>

    <%  } %>

</tbody>
</table>




<br /><br />

<form action="system-properties.jsp" method="post" name="editform">

<div class="jive-table">
<table cellpadding="0" cellspacing="0" border="1" width="100%">
<thead>
    <tr>
        <th colspan="2">
                添加新属性
        </th>
    </tr>
</thead>
<tbody>
    <tr valign="top">
        <td width="150">
            属性名称:
        </td>
        <td>
            <input type="text" class="jive-property-propname" name="propName" size="40" maxlength="100" value="<%= (propName != null ? propName : "") %>">

            <%  if (errors.containsKey("propName")) { %>

                <br /><span class="jive-error-text">请输入属性名称.</span>

            <%  } %>
        </td>
    </tr>
    <tr valign="top">
        <td>
            属性值:
        </td>
        <td>
            <textarea cols="70" rows="10" name="propValue" wrap="virtual" style="width:100%;font-family:verdana,arial,helvetica,sans-serif;font-size:8pt;"><%= (propValue != null ? propValue : "") %></textarea>

            <%  if (errors.containsKey("propValue")) { %>

                <br /><span class="jive-error-text">请输入属性值.</span>

            <%  } else if (errors.containsKey("propValueLength")) { %>

                <br /><span class="jive-error-text">最大字符数 <%= 3500L %></span>

            <%  } %>
        </td>
    </tr>
</tbody>
<tfoot>
    <tr>
        <td colspan="2">
            <input type="submit" name="create" value="保存属性">
        </td>
    </tr>
</tfoot>
</table>
</div>

</form>


<form id="ef" action="system-properties.jsp" method="post" name="ef">
	<input type="hidden" name="edit" value="true"/>
	<input type="hidden" name="propName"/>
	<input type="hidden" name="propValue"/>
</form>


<script type="text/javascript">
	var $ = function(id){
		return document.getElementById(id);
	};

	var Element = {};
	Element.show = function(obj){
		try{
			obj.style.display = "";
		}catch(e){
		}
	};
	Element.hide = function(obj){
		try{
			obj.style.display = "none";
		}catch(e){
		}
	};


    var currentTargetID;

    function closeCurrentPropertyForm() {
        // close current target, if exists
        if (currentTargetID && currentTargetID != "") {
            Element.hide($(currentTargetID));
        }
    }

    function togglePropertyForm(target) {
        // close the current target if exists
        if (target != currentTargetID) {
            closeCurrentPropertyForm();
        }

        Element.show($(target));
        currentTargetID = target;

    }

    function saveCurrentPropertyForm(propertyID) {
        // get the name and value
        var propName = $('jive-propName-' + propertyID).value;
        var propValue = $('jive-propValue-' + propertyID).value;

        if (propName == '') {
            Element.show($('jive-propName-error-' + propertyID));
            return;
        }

        if (propValue == '' || propValue.length > 3500) {
            Element.show($('jive-propValue-error-' + propertyID));
            return;
        }

		document.ef.propName.value = propName;
		document.ef.propValue.value = propValue;
		document.ef.submit();
    }

    function dodelete(propName, propertyID) {
        var dodelete = confirm('您确定要删除当前属性吗？？\n属性名称: ' + propName);
        if (dodelete) {
			var url = 'system-properties.jsp?propName=' + propName + '&delete=true';
            location.href = url;
            return true;
        }
        else {
            return false;
        }
    }



</script>

