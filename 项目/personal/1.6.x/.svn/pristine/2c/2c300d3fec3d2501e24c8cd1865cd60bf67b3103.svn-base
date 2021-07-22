package org.opoo.apps.web.jasper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.jasper.servlet.JspServlet;


/**
 * 运行模块部署目录中的JSP文件。
 * 
 * 该功能使用apache jasper实现，如果系统运行的应用服务器也是基于jasper的，
 * 可能会导致一些版本问题。
 * 
 * 该功能在jetty 6.1,tomcat 6.0测试可行。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsJspServlet_BAK extends JspServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006624799837791889L;

	/* (non-Javadoc)
	 * @see org.apache.jasper.servlet.JspServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		config = AppsServletConfig.getRequiredAppsServletConfig(config);
		super.init(config);
	}	

}
