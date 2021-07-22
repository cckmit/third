package org.opoo.apps.module.configurators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.module.ConfigurationContext;
import org.opoo.apps.module.ModuleConfigurator;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class EmptyModuleConfigurator implements ModuleConfigurator {
	private static final Log log = LogFactory.getLog(EmptyModuleConfigurator.class);
	public void configure(ConfigurationContext context) {
		log.debug("配置模块：" + context.getModuleMetaData().getName());
	}

	public void destroy(ConfigurationContext context) {
		log.debug("清理模块：" + context.getModuleMetaData().getName());
	}

}
