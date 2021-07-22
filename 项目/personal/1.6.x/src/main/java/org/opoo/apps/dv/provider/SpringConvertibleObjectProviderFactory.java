package org.opoo.apps.dv.provider;

import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dv.ConvertableObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringConvertibleObjectProviderFactory implements ConvertibleObjectProviderFactory, ApplicationContextAware{
	private ConcurrentMap<String,String> providerBeanMap;
	private ApplicationContext applicationContext;
	 
	public void setProviderBeans(ConcurrentMap<String, String> providerBeanMap) {
		this.providerBeanMap = providerBeanMap;
	}
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	public ConvertibleObjectProvider get(ConvertableObject co) {
		String key = String.valueOf(co.getObjectType());
		String name = providerBeanMap.get(key/*co.getObjectType()*/);
		ConvertibleObjectProviderPrototype bean = (ConvertibleObjectProviderPrototype) applicationContext.getBean(name);
		bean.setContext(co);
		return bean;
	}

	public boolean hasRegisteredProvider(ConvertableObject co) {
		return providerBeanMap.containsKey(String.valueOf(co.getObjectType()));
	}
}
