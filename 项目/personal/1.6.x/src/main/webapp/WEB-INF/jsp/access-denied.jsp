<%

out.println(request.getPathInfo());
out.println(request.getRequestURL());
out.println(request.getAttributeNames());
java.util.Enumeration en = request.getAttributeNames();
while(en.hasMoreElements()){
	out.println(en.nextElement() + "<br>");
}

en = session.getAttributeNames();
while(en.hasMoreElements()){
	out.println(en.nextElement() + "<br>");
}
%>