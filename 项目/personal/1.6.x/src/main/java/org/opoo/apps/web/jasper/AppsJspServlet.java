package org.opoo.apps.web.jasper;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.ClassUtils;


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
public class AppsJspServlet implements Serializable{
	private static final Log log = LogFactory.getLog(AppsJspServlet.class);
	private static final long serialVersionUID = 2006624799837791889L;
	
	public static final String JSP_SERVLET_CLASSNAME = "org.apache.jasper.servlet.JspServlet";

	private Object jspServlet;
	
	private Method initByServletConfig;
	private Method init;
	private Method destroy;
	private Method service;
	
	public AppsJspServlet(){
		try {
			Class<?> clazz = ClassUtils.forName(JSP_SERVLET_CLASSNAME/*"org.apache.jasper.servlet.JspServlet"*/);
			jspServlet = clazz.newInstance();
			initByServletConfig = clazz.getMethod("init", ServletConfig.class);
			init = clazz.getMethod("init");
			destroy = clazz.getMethod("destroy");
			service = clazz.getMethod("service", HttpServletRequest.class, HttpServletResponse.class);
		} catch (Throwable e) {
			log.error("构建JspServlet时出错，JspServlet将不可用：" + e.getMessage());
		}
	}


	/* (non-Javadoc)
	 * @see org.apache.jasper.servlet.JspServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		config = AppsServletConfig.getRequiredAppsServletConfig(config);
		try {
			if(jspServlet != null && initByServletConfig != null){
				initByServletConfig.invoke(jspServlet, config);
			}
		}catch (Exception e) {
			if(e instanceof ServletException){
				throw (ServletException) e;
			}else{
				log.error("init(ServletConfig) error", e);
			}
		}
	}

	public void init() throws ServletException {
		try {
			if(jspServlet != null && init != null){
				init.invoke(jspServlet);
			}
		}catch (Exception e) {
			if(e instanceof ServletException){
				throw (ServletException) e;
			}else{
				log.error("init() error", e);
			}
		}
	}
	
	public void destroy() {
		try {
			if(jspServlet != null && destroy != null){
				destroy.invoke(jspServlet);
			}
		}catch (Exception e) {
			log.error("destroy() error", e);
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(jspServlet != null && service != null){
				service.invoke(jspServlet, request, response);
			}
		}catch (Exception e) {
			log.error("service() error", e);
		}
	}	
}
