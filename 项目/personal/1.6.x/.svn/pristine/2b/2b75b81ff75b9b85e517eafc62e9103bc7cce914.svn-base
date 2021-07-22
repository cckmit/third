package org.opoo.apps.web.jsp;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * JspFactory 扩展，仅作调试。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsJspFactory extends JspFactory {
	private static final Log log = LogFactory.getLog(AppsJspFactory.class);
	
	private JspFactory jspFactory;
	public AppsJspFactory(JspFactory jspFactory) {
		super();
		this.jspFactory = jspFactory;
		log.info("AppsJspFactory created by " + jspFactory);
	}

	@Override
	public JspEngineInfo getEngineInfo() {
		log.info("getEngineInfo");
		return jspFactory.getEngineInfo();
	}

	@Override
	public PageContext getPageContext(Servlet arg0, ServletRequest arg1, ServletResponse arg2, String arg3,
			boolean arg4, int arg5, boolean arg6) {
		log.info("getPageContext\n" +
				"Servlet: " + arg0
				+ "\nServletRequest: " + arg1
				+ "\nServletRequest: " + arg2
				+ "\n" + arg3
				+ "\n" + arg4
				+ "\n" + arg5
				+ "\n" + arg6);
		return jspFactory.getPageContext(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public void releasePageContext(PageContext arg0) {
		log.info("releasePageContext");
		jspFactory.releasePageContext(arg0);
	}

}
