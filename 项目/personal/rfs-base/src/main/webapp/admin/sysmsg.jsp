<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" 
import="org.apache.commons.logging.Log,
org.apache.commons.logging.LogFactory,
org.opoo.apps.AppsGlobals,
org.opoo.apps.lifecycle.Application,
org.opoo.apps.security.SecurityUtils,
org.opoo.apps.security.User,
org.opoo.apps.security.UserHolder,
cn.redflagsoft.base.bean.Clerk,
cn.redflagsoft.base.service.*,
cn.redflagsoft.base.service.impl.SystemMessageImpl,
java.util.*"
%><%!
private static final Log logger = LogFactory.getLog("System.Message");

private void showMessage(javax.servlet.jsp.JspWriter out, String msg) throws java.io.IOException{
	StringBuffer sb = new StringBuffer();
	sb.append("<script language='JavaScript'>\n");
	sb.append("<!--\n");
	sb.append("alert('" + msg + "');\n");
	sb.append("location.href = '?';\n");
	sb.append("//-->\n");
	sb.append("</script>\n");
	out.println(sb.toString());
	System.out.println(msg);
}
%><%
request.setCharacterEncoding("GBK");

String info = "新建系统消息";
ClerkService clerkService = Application.getContext().get("clerkService", ClerkService.class);
SystemMessageManager manager = Application.getContext().get("systemMessageManager", SystemMessageManager.class);



String action = request.getParameter("action");
String subject = request.getParameter("subject");
String content = request.getParameter("content");
String status = request.getParameter("status");
String msgId = request.getParameter("msgId");

if("create".equals(action)){
	if(subject != null && content != null){
		SystemMessage.PublishStatus ps = "2".equals(status) ? SystemMessage.PublishStatus.NORMAL : SystemMessage.PublishStatus.DRAFT;
		manager.createMessage(subject, content, null, 0, ps);
		showMessage(out, "创建消息成功");
		return;
	}else{
		info = "创建系统消息出错";
	}
}else if("startEdit".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "编辑失败，缺少ID指定的对象");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "只有草稿才能编辑修改");
			return;
		}
		subject = sm.getSubject();
		content = sm.getContent();
		action = "edit";
	}else{
		showMessage(out, "修改失败，缺少ID");
		return;
	}
}else if("edit".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "编辑失败，缺少ID指定的对象");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "只有草稿才能编辑修改");
			return;
		}
		if(subject == null || content == null){
			info = "标题或者内容不能为空";
		}else{
			((SystemMessageImpl)sm).setSubject(subject);
			((SystemMessageImpl)sm).setContent(content);
			showMessage(out, "修改消息成功");
			return;
		}
	}else{
		showMessage(out, "删除失败，缺少ID");
		return;
	}
}else if("delete".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		manager.removeMessage(id);
		showMessage(out, "删除消息成功");
		return;
	}else{
		showMessage(out, "删除失败，缺少ID");
		return;
	}
}else if("publish".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "发布失败，缺少ID指定的对象");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "只有草稿才能发布");
			return;
		}
		if(sm.getSubject() == null || sm.getContent() == null){
			showMessage(out, "内容不完整，不能发布");
			return;
		}
		sm.setPublishStatus(SystemMessage.PublishStatus.NORMAL);
		showMessage(out, "消息已发布");
	}else{
		showMessage(out, "发布失败，缺少ID");
		return;
	}
}else{
	action = "create";
}


//System.out.println(action + "." + subject + "." + content + "." + status);

if(action == null){
	action = "create";
}
if(subject == null){
	subject = "";
}
if(content == null){
	content = "";
}
if(msgId == null){
	msgId = "";
}


List<SystemMessage> messages = manager.findMessages();

/////////////////////////////////////////////////////////////////
response.setHeader("Cache-Control", "Public"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires", 0); 


%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>系统消息</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
		function checkform(){
			if(!document.f.subject.value){
				alert("请输入标题！");
				document.f.subject.focus();
				return false;
			}


			if(!document.f.content.value){
				document.f.content.focus();
				return false;
			}

			return true;
		}

		function undo(){
			location.href = "?";
		}

		function cf1(){
			return confirm("确定发布消息么？");
		}

		function cf2(){
			return confirm("确定删除消息么？");
		}
//-->
</SCRIPT>
</head>

<body onload="document.f.subject.focus();" style="color:#444444;font-size:12px;">
<div>
	<h2>系统消息</h2>
	<table border="1" cellspacing="0" cellpadding="3" width="100%">
		  <tr>
		  	<th width="40" align="center">ID</th>
		  	<th width="150">标题</th>
		  	<th>内容</th>
		  	<th width="50" align="right">&nbsp;</th>
		  	<th width="120">&nbsp;</th>
		  	<th width="60">操作</th>
		  </tr>
		  
		  <%if(messages.size() == 0){ %>
		  <tr>
            <td colspan="6">没有记录</td>
          </tr>
		  <%}else{
			  for(SystemMessage msg: messages){
			  %>
			<tr>
				<td rowspan="2" align="center"><%=msg.getMsgId() %><br/>(<%=msg.getPublishStatus() %>)</td>
				<td rowspan="2"><%=msg.getSubject() %></td>
				<td rowspan="2"><%=msg.getContent() %></td>
				<td align="right">创建时间</td>
				<td><%=AppsGlobals.formatDateTime(msg.getCreationTime()) %></td>
				<td rowspan="2" align="center">
				[<%if(msg.getPublishStatus() == SystemMessage.PublishStatus.DRAFT){%><a href="?action=startEdit&msgId=<%=msg.getMsgId()%>">编辑</a><%}else{%>编辑<%}%>]<br>
				
				[<%if(msg.getPublishStatus() == SystemMessage.PublishStatus.DRAFT){%><a href="?action=publish&msgId=<%=msg.getMsgId()%>" onclick="return cf1();">发布</a><%}else{%>发布<%}%>]<br>
				
				[<a href="?action=delete&msgId=<%=msg.getMsgId()%>" onclick="return cf2();">删除</a>]
				
				</td>
			</tr>
			<tr>
				<td align="right">过期时间</td>
				<td><%=msg.getExpirationTime() %></td>
			</tr>
			<%
			List<SystemMessage.Reader> readers = msg.getReaders();
			if(readers.size() > 0){
				for(SystemMessage.Reader reader: readers){
					Clerk clerk = clerkService.getClerk(reader.getUser().getUserId());
			%>
			<tr>
				<td>&nbsp;</td>
				<td colspan="5">
					<table width="100%">
						<tr>
							<td><%=reader.getUser().getUsername() %></td>
							<td><%=clerk.getName() %></td>
							<td><%=reader.getLoadedTime()%></td>
							<td><%=reader.getConfirmedTime() %></td>
							<td><%=reader.getSessionId() %></td>
						</tr>
					</table>
				</td>
			</tr>
			<%} }%>
		  <%} }%>
	</table>
</div>

  <div style="height:40px;margin-top:10px;border:1px solid #000;">&nbsp;<%=info%></div>
  <div id="d01">
  <form name='f' action="sysmsg.jsp" method='POST' onsubmit="return checkform();">
	<input type="hidden" name="action" value="<%=action%>"/>
	<input type="hidden" name="msgId" value="<%=msgId%>"/>
	<table border="0" cellspacing="0" cellpadding="3">
		  <tr>
            <td align="right">标题</td>
            <td><input name="subject" type="text" class="text" size="25" value="<%=subject%>"></td>
          </tr>
          <tr>
            <td align="right">内容</td>
            <td><textarea name="content" rows="6" cols="80"><%=content%></textarea></td>
          </tr>
		  <%if("create".equals(action)){%>
          <tr>
            <td>&nbsp;</td>
            <td><select name="status">
            	<option value="1">存为草稿</option>
            	<option value="2">立刻发布</option>
            </select></td>
          </tr>
		  <%}%>
          <tr>
            <td>&nbsp;</td>
            <td><input name="Submit" type="submit" class="button" value="保 存">
              <input name="Submit2" type="reset" class="button" value="重 输">
			  <%if("edit".equals(action)){%><input name="Submit2" type="button" class="button" value="取消编辑" onclick="undo()"><%}%></td>
      </tr>
    </table>
	</form>
</div>

 </body>
</html>


