<%--
  ~ $Revision: 4342 $
  ~ $Date: 2011-04-22 10:17:01 +0800 (周五, 22 四月 2011) $
  ~
  ~ Copyright (C) 2009 Alex Lin. All rights reserved.
  ~
  ~ Use is subject to license terms.
  --%>

<%@ page contentType="text/html" %>

<%  // Redirect to the .jspa alias. Unfortunately we have to do an if check here - without
    // it we might get a 'statement not reached' compiler error.
    boolean doRedirect = true;
    if (doRedirect) {
        response.sendRedirect("setup.license!input.jspa");
        return;
    }
%>
