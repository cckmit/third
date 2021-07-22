package org.opoo.apps.web.context;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, using AppsContextLoader
 */
public class SpringContextLoader extends ContextLoader {
	private static final Log log = LogFactory.getLog(SpringContextLoader.class);
	
	public SpringContextLoader(){
		super();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ContextLoader#customizeContext(javax.
	 * servlet.ServletContext,
	 * org.springframework.web.context.ConfigurableWebApplicationContext)
	 */
	@Override
	protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext) {
		log.info("customizeContext Apps Application Context.");
		// try {
		//			
		ConfigurationProvider pro = ConfigurationProviderManager.getConfigurationProvider(servletContext);
		//new ConfigurationProviderImpl(servletContext);
		String[] configLocations = pro.getConfigLocations();
			
		//applicationContext.getConfigLocations();
		// StringUtils.tokenizeToStringArray(location,
		// CONFIG_LOCATION_DELIMITERS)

		if (log.isInfoEnabled()) {
			log.info("Using " + configLocations.length + " context files.");
			for (String file : configLocations) {
				log.info("Including '" + file + "' in context files.");
			}
		}

		applicationContext.setConfigLocations(configLocations);
		// } catch (Exception ex) {
		// log.warn("Failed to configure context files. The system will likely be unstable.",
		// ex);
		// }
	}


	


	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoader#determineContextClass(javax.servlet.ServletContext)
	 */
	@Override
	protected Class<?> determineContextClass(ServletContext servletContext) throws ApplicationContextException {
		//return super.determineContextClass(servletContext);
		return SpringContext.class;
	}
	
}
