package org.opoo.apps.web.jasper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.opoo.web.servlet.ServletConfigWrapper;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsServletConfig extends ServletConfigWrapper {
//	private static final Log log = LogFactory.getLog(AppsServletConfig.class);
	private ServletContext context = null;
	
	public AppsServletConfig(ServletConfig config) {
		super(config);
	}
	
	
	/* (non-Javadoc)
	 * @see org.opoo.web.servlet.ServletConfigWrapper#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		ServletContext sc = super.getServletContext();
		if(sc != context){
			context = AppsServletContext.getRequiredAppsServletContext(sc);
//			if(log.isDebugEnabled()){
//				log.debug("创建封装的ServletContext：" + context);
//			}
		}
		return context;
	}
	
	
	public static AppsServletConfig getRequiredAppsServletConfig(ServletConfig config){
		if(config instanceof AppsServletConfig){
			return (AppsServletConfig) config;
		}else{
			return new AppsServletConfig(config);
		}
	}
}
