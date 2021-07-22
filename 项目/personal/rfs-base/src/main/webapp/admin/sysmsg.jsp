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

String info = "�½�ϵͳ��Ϣ";
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
		showMessage(out, "������Ϣ�ɹ�");
		return;
	}else{
		info = "����ϵͳ��Ϣ����";
	}
}else if("startEdit".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "�༭ʧ�ܣ�ȱ��IDָ���Ķ���");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "ֻ�вݸ���ܱ༭�޸�");
			return;
		}
		subject = sm.getSubject();
		content = sm.getContent();
		action = "edit";
	}else{
		showMessage(out, "�޸�ʧ�ܣ�ȱ��ID");
		return;
	}
}else if("edit".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "�༭ʧ�ܣ�ȱ��IDָ���Ķ���");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "ֻ�вݸ���ܱ༭�޸�");
			return;
		}
		if(subject == null || content == null){
			info = "����������ݲ���Ϊ��";
		}else{
			((SystemMessageImpl)sm).setSubject(subject);
			((SystemMessageImpl)sm).setContent(content);
			showMessage(out, "�޸���Ϣ�ɹ�");
			return;
		}
	}else{
		showMessage(out, "ɾ��ʧ�ܣ�ȱ��ID");
		return;
	}
}else if("delete".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		manager.removeMessage(id);
		showMessage(out, "ɾ����Ϣ�ɹ�");
		return;
	}else{
		showMessage(out, "ɾ��ʧ�ܣ�ȱ��ID");
		return;
	}
}else if("publish".equals(action)){
	if(msgId != null){
		long id = Long.parseLong(msgId);
		SystemMessage sm = manager.getMessage(id);
		if(sm == null){
			showMessage(out, "����ʧ�ܣ�ȱ��IDָ���Ķ���");
			return;
		}
		if(sm.getPublishStatus() != SystemMessage.PublishStatus.DRAFT){
			showMessage(out, "ֻ�вݸ���ܷ���");
			return;
		}
		if(sm.getSubject() == null || sm.getContent() == null){
			showMessage(out, "���ݲ����������ܷ���");
			return;
		}
		sm.setPublishStatus(SystemMessage.PublishStatus.NORMAL);
		showMessage(out, "��Ϣ�ѷ���");
	}else{
		showMessage(out, "����ʧ�ܣ�ȱ��ID");
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
<title>ϵͳ��Ϣ</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
		function checkform(){
			if(!document.f.subject.value){
				alert("��������⣡");
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
			return confirm("ȷ��������Ϣô��");
		}

		function cf2(){
			return confirm("ȷ��ɾ����Ϣô��");
		}
//-->
</SCRIPT>
</head>

<body onload="document.f.subject.focus();" style="color:#444444;font-size:12px;">
<div>
	<h2>ϵͳ��Ϣ</h2>
	<table border="1" cellspacing="0" cellpadding="3" width="100%">
		  <tr>
		  	<th width="40" align="center">ID</th>
		  	<th width="150">����</th>
		  	<th>����</th>
		  	<th width="50" align="right">&nbsp;</th>
		  	<th width="120">&nbsp;</th>
		  	<th width="60">����</th>
		  </tr>
		  
		  <%if(messages.size() == 0){ %>
		  <tr>
            <td colspan="6">û�м�¼</td>
          </tr>
		  <%}else{
			  for(SystemMessage msg: messages){
			  %>
			<tr>
				<td rowspan="2" align="center"><%=msg.getMsgId() %><br/>(<%=msg.getPublishStatus() %>)</td>
				<td rowspan="2"><%=msg.getSubject() %></td>
				<td rowspan="2"><%=msg.getContent() %></td>
				<td align="right">����ʱ��</td>
				<td><%=AppsGlobals.formatDateTime(msg.getCreationTime()) %></td>
				<td rowspan="2" align="center">
				[<%if(msg.getPublishStatus() == SystemMessage.PublishStatus.DRAFT){%><a href="?action=startEdit&msgId=<%=msg.getMsgId()%>">�༭</a><%}else{%>�༭<%}%>]<br>
				
				[<%if(msg.getPublishStatus() == SystemMessage.PublishStatus.DRAFT){%><a href="?action=publish&msgId=<%=msg.getMsgId()%>" onclick="return cf1();">����</a><%}else{%>����<%}%>]<br>
				
				[<a href="?action=delete&msgId=<%=msg.getMsgId()%>" onclick="return cf2();">ɾ��</a>]
				
				</td>
			</tr>
			<tr>
				<td align="right">����ʱ��</td>
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
            <td align="right">����</td>
            <td><input name="subject" type="text" class="text" size="25" value="<%=subject%>"></td>
          </tr>
          <tr>
            <td align="right">����</td>
            <td><textarea name="content" rows="6" cols="80"><%=content%></textarea></td>
          </tr>
		  <%if("create".equals(action)){%>
          <tr>
            <td>&nbsp;</td>
            <td><select name="status">
            	<option value="1">��Ϊ�ݸ�</option>
            	<option value="2">���̷���</option>
            </select></td>
          </tr>
		  <%}%>
          <tr>
            <td>&nbsp;</td>
            <td><input name="Submit" type="submit" class="button" value="�� ��">
              <input name="Submit2" type="reset" class="button" value="�� ��">
			  <%if("edit".equals(action)){%><input name="Submit2" type="button" class="button" value="ȡ���༭" onclick="undo()"><%}%></td>
      </tr>
    </table>
	</form>
</div>

 </body>
</html>


