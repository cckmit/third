package org.opoo.apps.web.jasper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.jasper.servlet.JspServlet;


/**
 * ����ģ�鲿��Ŀ¼�е�JSP�ļ���
 * 
 * �ù���ʹ��apache jasperʵ�֣����ϵͳ���е�Ӧ�÷�����Ҳ�ǻ���jasper�ģ�
 * ���ܻᵼ��һЩ�汾���⡣
 * 
 * �ù�����jetty 6.1,tomcat 6.0���Կ��С�
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
