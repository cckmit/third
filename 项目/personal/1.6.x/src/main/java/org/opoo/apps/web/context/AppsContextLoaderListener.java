package org.opoo.apps.web.context;

import java.io.FileNotFoundException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.Apps;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;


/**
 * Custom Context Loader listener that returns a Apps Context Loader.
 * 
 * @author lcj
 * @since 1.6.0
 */
public class AppsContextLoaderListener implements ServletContextListener{
	
	/**
	 * Servlet上下文参数名称，用于标示ContextLoader的类。
	 *  "<code>contextLoaderClass</code>"
	 */
	public static final String CONTEXT_LOADER_CLASS_PARAM = "contextLoaderClass";
	
	/**
	 * Servlet上下文参数名称，用于标示出产品实例配置文件所在的位置。
	 *  "<code>instanceLocation</code>"
	 */
	public static final String INSTANCE_LOCATION_PARAM = "instanceLocation";

	/**
	 * 默认的实例配置文件的位置。
	 */
	private static final String DEFAULT_INSTANCE_LOCATION = "classpath:apps_build.xml";
	
	private static final Logger log = LogManager.getLogger(AppsContextLoaderListener.class);
	private static Apps systemInstance;
	//private static final AppsContextLoader loader = new AppsContextLoader();
	
	public void contextInitialized(ServletContextEvent event) {
		contextInitialized(event.getServletContext());
	}

	public void contextInitialized(ServletContext servletContext) {
		log.info("Initializing Apps via web application context event.");
		String instanceLocation = servletContext.getInitParameter(INSTANCE_LOCATION_PARAM);
		if(StringUtils.isBlank(instanceLocation)){
			instanceLocation = DEFAULT_INSTANCE_LOCATION;
		}
		if(log.isDebugEnabled()){
			log.debug("Using instanceLocation " + instanceLocation);
		}

		try {
			URL url = ResourceUtils.getURL(instanceLocation);
			AppsInstanceLoader.initialize(url);
		} catch (FileNotFoundException e) {
			log.fatal(e.getMessage());
            throw new RuntimeException(e);
		}
		
        systemInstance = Apps.getInstance();
        AppsContextLoader loader = createContextLoader(servletContext);
        if(!systemInstance.start(loader, servletContext)) {
            final String msg = "Apps service failed to start. Please see the system logs.";
            log.fatal(msg);
            throw new RuntimeException(msg);
        }
	}
	
	
	public void contextDestroyed(ServletContextEvent event){
		contextDestroyed(event.getServletContext());
	}

	public void contextDestroyed(ServletContext servletContext) {
		try {
			systemInstance.stop(servletContext);
		} catch (Exception ex) {
			log.warn("Apps system did not shutdown cleanly.", ex);
			// eat the exception
		}
	}
	
	protected AppsContextLoader createContextLoader(ServletContext servletContext) {
		Class<?> contextLoaderClass = determineContextLoaderClass(servletContext);
		if (!AppsContextLoader.class.isAssignableFrom(contextLoaderClass)) {
			throw new ApplicationContextException("Custom context class ["
					+ contextLoaderClass.getName() + "] is not of type ["
					+ AppsContextLoader.class.getName() + "]");
		}
		if(log.isDebugEnabled()){
			log.debug("AppsContextLoader class " + contextLoaderClass.getName());
		}
		AppsContextLoader loader = (AppsContextLoader) BeanUtils.instantiateClass(contextLoaderClass);
		return loader;
	}
	
	protected Class<?> determineContextLoaderClass(ServletContext servletContext) throws ApplicationContextException {
		String contextLoaderClassName = servletContext.getInitParameter(CONTEXT_LOADER_CLASS_PARAM);
		if (contextLoaderClassName != null) {
			try {
				return ClassUtils.forName(contextLoaderClassName);
			}
			catch (ClassNotFoundException ex) {
				throw new ApplicationContextException("Failed to load custom context class [" + contextLoaderClassName + "]", ex);
			}
		}else{
//			ClassUtils.forName(AppsConstants.DEFAULT_CONTEXT_LOADER_CLASS_NAME);
			return AppsContextLoader.class;
		}
	}
}
