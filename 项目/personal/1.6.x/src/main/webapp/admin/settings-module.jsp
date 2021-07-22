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
	java.text.DecimalFormat,
	org.apache.commons.fileupload.*,
	org.apache.commons.fileupload.disk.*,
	org.apache.commons.fileupload.servlet.*,
	java.io.File"
%><%!
void showMessage(javax.servlet.jsp.JspWriter out, String msg) throws java.io.IOException{
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

//file upload to server
void upfile(HttpServletRequest request, JspWriter out, File tempFile) throws Exception{
	try{
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//String path=request.getRealPath("/upload");
		//factory.setRepository(new File(path));
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = upload.parseRequest(request);
		for(FileItem file: list){
			if(!file.isFormField()){
				file.write(tempFile);
				System.out.println("Write module file: " + tempFile);
				return;
			}
		}

		throw new Exception("û���ϴ��ļ�");
		//out.print("upload success!");
	}catch(Exception e){
        //out.print("upload error");
		throw e;
    }
}

ModuleResultList installModule(HttpServletRequest request, JspWriter out) throws Exception{
	File file = File.createTempFile("module", ".jar", AppsHome.getTemp());
	try{
		upfile(request, out, file);
		ModuleManager manager = Application.getContext().getModuleManager();
		ModuleResultList results = manager.installModule(file);
		return results;
	}finally{
		file.delete();
	}

}

%><%
ModuleManager manager = Application.getContext().getModuleManager();


String action = request.getParameter("action");
String moduleName = request.getParameter("moduleName");

System.out.println(action + "----------" + moduleName);

if("install".equals(action)){
	try{
		moduleName = "";
		ModuleResultList results = installModule(request, out);
		ModuleMetaData meta = results.getModuleMetaData();
		if(meta != null){
			moduleName = meta.getName();
		}
		boolean requireRestart = results.contains(RequireRestartResult.getRequireRestartResult());
		
		String msg = "ģ��" + moduleName + " ��װ�ɹ���";
		if(requireRestart){
			msg += "ϵͳ��Ҫ������";
		}

		showMessage(out, msg);
		return;
	}catch(Exception e){
		showMessage(out, e.getMessage());
		return;
	}

	
}else if("delete".equals(action)){
	if(moduleName != null){
		Module<?> module = manager.getModule(moduleName);
		if(module != null){
			ModuleResultList results = manager.uninstallModule(module);
			boolean requireRestart = results.contains(RequireRestartResult.getRequireRestartResult());

            String msg = "ģ��" + moduleName + " ж�سɹ���";
			if(requireRestart){
				msg += "ϵͳ��Ҫ������";
			}

			showMessage(out, msg);
			return;
		}
	}
}else if("deleteBroken".equals(action)){
	if(moduleName != null){
		manager.deleteBrokenModule(moduleName);

		showMessage(out, "�Ѿ�ɾ����ģ��" + moduleName);
		return;
	}
}










Map<String, String> brokenModules = manager.getBrokenModules();
Collection<Module<?>> modules = manager.getModules();


%>
<a href="index.jsp">������ҳ</a>


<%if(!brokenModules.isEmpty()){%>
<p>
<b>����ģ�鲻�ܱ�����</b>
<ul>
	<%for(Map.Entry<String, String> en: brokenModules.entrySet()){%>
		<li><%=en.getKey()%> - <%=en.getValue()%>
			<a href="settings-module.jsp?action=deleteBroken&moduleName=<%=en.getKey()%>" onclick="return deleteModule('<%=en.getKey()%>')" title="ɾ��" >
			   <img valign="middle" src="images/delete-16x16.gif" alt="delete" border="0"/>
			</a>
		</li>
	<%}%>
<ul>

<%}%>





<p>
<b>�Ѱ�װģ�飺<%=modules.size()%></b>
<table border="1" cellpadding="3" cellspacing="0" width="100%">
	<tr>
		<th colspan="4">ģ��</th>
		<th>����</th>
		<th>�汾</th>
		<th>λ��</th>
		<th nowrap>ɾ��</th>
	</tr>

	<%
	for(Module<?> module: modules){
		ModuleMetaData meta = manager.getMetaData(module);
	%>
		<tr>
			<td width="1%">
				<%if(meta.isSmallLogoExists()){%>
				<img src="<%=request.getContextPath()%>/modules/<%=meta.getName()%>/logo_small.png" width="16" height="16" alt="ģ��">
				<%}else{%>
				<img src="<%=request.getContextPath()%>/admin/images/plugin-16x16.gif" width="16" height="16" alt="ģ��">
				<%}%>
			</td>
			<td><%=meta.getName()%> 
			<%if(meta.isInstalled()){%> <b>(����ϵͳ��������Ч)</b><%}else if(meta.isUninstalled()){%><b>(��ж�أ�������ϵͳʹ֮��Ч)</b><%}%>
			</td>

			<td width="1%">
				<%if(meta.isReadmeExists()){%>
					<a href="<%=request.getContextPath()%>/modules/<%=meta.getName()%>/readme.html"><img src="images/info-16x16.gif" width="16" height="16" border="0" alt="README"></a>
				<%}else{%>
				&nbsp;
				<%}%>
			</td>
			<td width="1%">
				<img src="images/changelog-16x16.gif" width="16" height="16" border="0" alt="ChangeLog"></a>				
			</td>
			<td width="40%"><%=meta.getDescription()%></td>
			<td><%=meta.getVersion() %></td>
			<td><%=meta.getModuleDirectory() %></td>
			<td width="1%" align="center" nowrap>
				 <a href="settings-module.jsp?action=delete&moduleName=<%=meta.getName()%>" onclick="return deleteModule('<%=meta.getName()%>')" alt="ɾ��" >
				   <img src="images/delete-16x16.gif" alt="" border="0"/>
				</a>
			</td>
		</tr>
	<%}%>
</table>




<p>
&nbsp;
<p>

<form action="?action=install" method="post" enctype="multipart/form-data">
	<table width="100%" cellpadding="3" cellspacing="0" border="0">
		<thead>
			<tr>
				<th colspan="2">��װ��ģ��</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td width="1%">
					<nobr>ģ���ļ���</nobr>
				</td>
				<td width="99%">
					<input type="file" name="moduleFile" value="" size="60"/>
					��ѡ��ģ��� jar �ļ���
					<input type="submit" value="�ϴ�"/>
				</td>
			</tr>
		</tbody>
	</table>
</form>




<script language="JavaScript">
<!--
function deleteModule(moduleName) {
    var msg = "��Ҫɾ��ģ�� "+moduleName+ "��ȷ�ϣ���";
    msg = msg + "\n ɾ�����ģ����Ҫ����ϵͳ����Ч��";
    return confirm(msg);
}
//-->
</script>


