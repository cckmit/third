package org.opoo.apps.web.context;

import java.lang.reflect.Constructor;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ClassUtils;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, using AppsContextLoader
 */
public class ConfigurationProviderManager {
	
	private static final Log log = LogFactory.getLog(ConfigurationProviderManager.class);
	public static final String CONTEXT_CONFIGURATION_PROVIDER = "org.opoo.apps.web.context.ConfigurationProvider";
	public static final String CONTEXT_CONFIGURATION_PROVIDER_PARAM = "contextConfigurationProvider";
	
	
	
	public static void setConfigurationProviderClass(Class<? extends ConfigurationProvider> clazz){
		System.setProperty(CONTEXT_CONFIGURATION_PROVIDER, clazz.getName());
	}
	
	/**
	 * 
	 * @return
	 */
	public static ConfigurationProvider getConfigurationProvider(){
		String value = System.getProperty(CONTEXT_CONFIGURATION_PROVIDER);
		if(value != null){
			try {
				ConfigurationProvider cp = (ConfigurationProvider) ClassUtils.forName(value, ConfigurationProvider.class.getClassLoader()).newInstance();
				log.info("Using ConfigurationProvider: " + value);
				return cp;
			} catch (Exception e) {
				log.error("无法创建 ConfigurationProvider 实例", e);
			} 
		}
		log.info("Using default ConfigurationProvider: " + ConfigurationProviderImpl.class.getName());
		return new ConfigurationProviderImpl();
	}
	
	/**
	 * 
	 * @param servletContext
	 * @return
	 */
	public static ConfigurationProvider getConfigurationProvider(ServletContext servletContext){
		if(servletContext == null){
			log.warn("ServletContext is null, finding default ConfigurationProvider.");
			return ConfigurationProviderManager.getConfigurationProvider();
		}
		
		String value = servletContext.getInitParameter(CONTEXT_CONFIGURATION_PROVIDER_PARAM);
		if(value != null){
			try {
				Class<?> clazz = ClassUtils.forName(value, ConfigurationProvider.class.getClassLoader());
				Constructor<?> constructor = clazz.getConstructor(ServletContext.class);
				ConfigurationProvider cp = (ConfigurationProvider) constructor.newInstance(servletContext);
				log.info("Using ConfigurationProvider: " + value);
				return cp;
			} catch (Exception e) {
				log.error("无法创建 ConfigurationProvider 实例", e);
			} 
		}
		log.info("Using default ConfigurationProvider: " + ConfigurationProviderImpl.class.getName());
		return new ConfigurationProviderImpl(servletContext);
	}
}
