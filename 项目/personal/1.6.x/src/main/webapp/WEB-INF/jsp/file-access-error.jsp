<%

Exception ex = (Exception)request.getAttribute("exception");
if(ex != null)
{
	System.err.println(ex);
}
response.sendError(HttpServletResponse.SC_NOT_FOUND);


%>