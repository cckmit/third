<%@ page contentType="text/xml;charset=UTF-8"
	import="org.opoo.apps.lifecycle.Application,
		org.springframework.web.context.support.WebApplicationContextUtils,
		org.springframework.context.ApplicationContext,
		org.springframework.security.AccessDeniedException,
		cn.redflagsoft.base.menu.*,
		java.util.List,
		org.apache.commons.lang.StringUtils,
		org.opoo.apps.security.*"
%><%
	User user = UserHolder.getUser();

	String username = user.getUsername();
	String username2 = request.getParameter("username");

	//当指定了用户并且不是当前用户时，需要检查是否具有管理权限，
	//有管理权限的用户可以查看指定用户的菜单
	if(username2 != null && !username2.equals(username)){
		if(SecurityUtils.isGranted(user, "ROLE_ADMIN") 
			|| SecurityUtils.isGranted(user, "ROLE_SYSTEM")
			|| SecurityUtils.isGranted(user, "ROLE_MANAGER")){
			username = username2;
		}else{
			throw new AccessDeniedException("无权限访问指定资源。");
		}
	}


	MenuManager menuManager = Application.getContext().get("menuManagerV2", MenuManager.class);
	List<RoleMenu> list = menuManager.findMenusByUsername(username);
	
	//out.println(menuManager);

%><%!

private String template = "<node id=\"%s\" name=\"%s\" label=\"%s\"";

private void writeXML(Menu menu, JspWriter out, int level) throws Exception{
	String blanks = blanks(level);
	
	
	//使用 remark 替换本来的name和label
	String name = menu.getName();
	String label = menu.getLabel();
	if(menu instanceof RoleMenu){
		RoleMenu roleMenu = (RoleMenu) menu;
		if(StringUtils.isNotBlank(roleMenu.getRemarkLabel())){
			label = roleMenu.getRemarkLabel();
		}
		if(StringUtils.isNotBlank(roleMenu.getRemarkName())){
			name = roleMenu.getRemarkName();
		}
	}

	//String s;
	StringBuffer sb = new StringBuffer();
	sb.append(blanks);
	//s = "<node id=\"%s\" name=\"%s\" label=\"%s\"";
	String s = String.format(template, menu.getId(), name, label);
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