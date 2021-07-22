<%@ page contentType="text/xml;charset=UTF-8"
	import="org.opoo.apps.lifecycle.Application,
		org.springframework.web.context.support.WebApplicationContextUtils,
		org.springframework.context.ApplicationContext,
		org.springframework.security.AccessDeniedException,
		cn.redflagsoft.base.menu.*,
		java.util.List,
		org.apache.commons.lang.StringUtils"
%><%
	String roleId = request.getParameter("roleId");
	if(roleId == null){
		throw new IllegalArgumentException("必须指定参数  roleId 。");
	}


	MenuManager menuManager = Application.getContext().get("menuManagerV2", MenuManager.class);
	//out.println(menuManager);
	List<RoleMenu> list = menuManager.findMenusByRoleId(Long.parseLong(roleId));

%><%!

private String template = "<node id=\"%s\" name=\"%s\" label=\"%s\"";

private void writeXML(Menu menu, JspWriter out, int level) throws Exception{
	String blanks = blanks(level);

	//String s;
	StringBuffer sb = new StringBuffer();
	sb.append(blanks);
	//s = "<node id=\"%s\" name=\"%s\" label=\"%s\"";
	String s = String.format(template, menu.getId(), menu.getName(), menu.getLabel());
	sb.append(s);
	if(StringUtils.isNotBlank(menu.getIcon())){
		sb.append(" icon=\"" + menu.getIcon() + "\"");
	}
	if(StringUtils.isNotBlank(menu.getLogo())){
		sb.append(" logo=\"" + menu.getLogo() + "\"");
	}
	if(menu.getParent() != null){
		sb.append(" parentId=\"" + menu.getParent().getId() + "\"");
	}
	if(menu.getAction() != null){
		sb.append(" actionId=\"" + menu.getAction().getId() + "\" location=\"" + menu.getAction().getLocation() + "\"");
	}
	if(menu instanceof Submenu && ((Submenu)menu).isInherited()){
		sb.append(" isInherited=\"true\"");
	}
	if(menu instanceof RoleMenu){
		RoleMenu roleMenu = (RoleMenu) menu;
		if(StringUtils.isNotBlank(roleMenu.getRemarkLabel())){
			sb.append(" remarkLabel=\"" + roleMenu.getRemarkLabel() + "\"");
		}
		if(StringUtils.isNotBlank(roleMenu.getRemarkName())){
			sb.append(" remarkName=\"" + roleMenu.getRemarkName() + "\"");
		}
	}
	sb.append(">");

	out.println(sb.toString());
	
	List<Submenu> list = menu.getSubmenus();
	if(!list.isEmpty()){
		for(Submenu smenu: list){
			writeXML(smenu, out, level + 1);
		}
	}
	out.println(blanks + "</node>");
}

private String blanks(int level){
	StringBuffer sb = new StringBuffer();
	for(int i = 0 ; i < level ; i++){
		sb.append("    ");
	}
	return sb.toString();
}

%><menus id="root" label="root">
<%
for(Menu m: list){
	writeXML(m, out, 1);
}
%>
</menus>