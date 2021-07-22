package org.opoo.apps.web.context;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Bootstrap;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, using AppsContextLoaderListener
 */
public class SpringContextLoaderListener extends ContextLoaderListener {
	public static final String PRODUCT_CLASS_NAME = "productClassName";
	private static final Log log = LogFactory.getLog(SpringContextLoaderListener.class);
	private ContextLoader contextLoader;
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
        log.info("Initializing Apps via web application context event.");
		Bootstrap system = Bootstrap.getInstance();
		contextLoader = new SpringContextLoader();

		if (!system.start(contextLoader, event.getServletContext())) {
			String msg = "Apps service failed to start. Please see the system logs.";
			log.fatal(msg);
			throw new RuntimeException(msg);
		} else {
			// return;
		}
		
		
//		try {
//			String className = event.getServletContext().getInitParameter(PRODUCT_CLASS_NAME);
//			log.info("产品：" + className);
//
//			Product product = (Product) ClassUtils.newInstance(className);
//			if (product != null) {
//				log.info("产品名称：" + product.getName());
//				log.info("产品版本：" + product.getVersion());
//				//AppsGlobals.getBeansHolder().put("product", product);
//
//				//System.out.println(AppsGlobals.getBeansHolder().get("product", Product.class));
//				// super.contextInitialized(event);
//			} else {
//				throw new Exception("找不到产品：" + className);
//			}
//		} catch (Exception e) {
//			throw new IllegalArgumentException("注册出错", e);
//		}
//
//		if (AppsLicenseManager.getInstance().isInitialized()) {
//			// super.contextInitialized(event);
//		}
	}
	
	

    public void contextDestroyed(ServletContextEvent event) {
		try {
			Bootstrap.getInstance().stop();
			super.contextDestroyed(event);
		} catch (Exception ex) {
			log.warn("Apps system did not shutdown cleanly.", ex);
		}
	}
    

}
