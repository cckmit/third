package org.opoo.apps.module.configurators;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.module.ConfigurationContext;
import org.opoo.apps.module.ModuleConfigurator;
import org.opoo.apps.module.ModuleMetaData;
import org.opoo.apps.module.ModuleStrutsXmlConfigurationProvider;
import org.opoo.apps.module.event.ModuleStrutsXmlConfigurationProviderEvent;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class Struts2Configurator implements ModuleConfigurator {
	private static final Log log = LogFactory.getLog(Struts2Configurator.class);
	
	
    protected Map<String, ModuleStrutsXmlConfigurationProvider> moduleProviders
    	= new ConcurrentHashMap<String, ModuleStrutsXmlConfigurationProvider>();
    protected EventDispatcher dispatcher;
    
    
    
    public void configure(ConfigurationContext context)
    {
        ModuleMetaData metaData = context.getModuleMetaData();
        if(!moduleProviders.containsKey(metaData.getName()))
        {
            //File moduleDir = metaData.getModuleDirectory();
        	File moduleDir = metaData.getModuleInfDirectory();
            if((new File(moduleDir, "struts.xml")).exists()){
                try
                {
                	ModuleStrutsXmlConfigurationProvider moduleProvider = new ModuleStrutsXmlConfigurationProvider(metaData);
                    moduleProviders.put(metaData.getName(), moduleProvider);
                    dispatcher.dispatchEvent(new ModuleStrutsXmlConfigurationProviderEvent(ModuleStrutsXmlConfigurationProviderEvent.Type.ADD, moduleProvider));
                }
                catch(Throwable e)
                {
                    log.error(e.getMessage(), e);
                }
            }
        } else
        {
            log.warn(String.format("StrutsConfigurator: Module %s must be destroyed before being module config can be reloaded.", new Object[] {
                metaData.getName()
            }));
        }
    }

    public void destroy(ConfigurationContext context) {
		ModuleMetaData metaData = context.getModuleMetaData();
		ModuleStrutsXmlConfigurationProvider provider = (ModuleStrutsXmlConfigurationProvider) moduleProviders.remove(metaData.getName());
		if (provider != null)
			dispatcher.dispatchEvent(new ModuleStrutsXmlConfigurationProviderEvent(
					ModuleStrutsXmlConfigurationProviderEvent.Type.REMOVE, provider));
	}


	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
