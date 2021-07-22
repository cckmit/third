<%@ page contentType="text/xml;charset=UTF-8"
	import="org.opoo.apps.lifecycle.Application,
		org.springframework.web.context.support.WebApplicationContextUtils,
		org.springframework.context.ApplicationContext,
		org.springframework.security.AccessDeniedException,
		cn.redflagsoft.base.menu.*,
		cn.redflagsoft.base.bean.Action,
		java.util.List"
%><%
	ActionManager actionManager = Application.getContext().get("actionManager", ActionManager.class);
	//out.println(actionManager);
	List<Action> list = actionManager.findActions();



%><actions>
	<action id="null" name="无"/>
<%
String template = "<action id=\"%s\" uid=\"%s\" name=\"%s\" location=\"%s\"%s/>";
String s;
for(Action action: list){
	String info = "";
	if(action.getIcon() != null){
		info += " icon=\"" + action.getIcon() + "\"";
	}
	if(action.getLogo() != null){
		info += " logo=\"" + action.getLogo() + "\"";
	}

	s = String.format(template, action.getId(), action.getUid(), action.getName(), action.getLocation(), info);

	out.println(s);
}
%></actions>
