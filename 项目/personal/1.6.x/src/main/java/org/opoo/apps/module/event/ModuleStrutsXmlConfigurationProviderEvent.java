package org.opoo.apps.module.event;

import org.opoo.apps.module.ModuleStrutsXmlConfigurationProvider;
import org.opoo.apps.web.struts2.event.ConfigurationProviderEvent;


/**
 *  ģ��� Struts2 �����ṩ���¼���
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleStrutsXmlConfigurationProviderEvent extends ConfigurationProviderEvent{
	//extends Event<ModuleStrutsXmlConfigurationProviderEvent.Type, ModuleStrutsXmlConfigurationProvider> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4558146043107317465L;

	public ModuleStrutsXmlConfigurationProviderEvent(Type eventType, ModuleStrutsXmlConfigurationProvider source) {
		super(eventType, source);
	}
}
