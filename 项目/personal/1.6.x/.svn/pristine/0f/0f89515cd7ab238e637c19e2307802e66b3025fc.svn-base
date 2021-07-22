package org.opoo.apps.event.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Spring 事件分发器实现类。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SpringEventDispatcherImpl implements ApplicationContextAware, SpringEventDispatcher {
	private ApplicationContext applicationContext;
	
	/**
	 * 设置 Spring 的容器。
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 分发事件。
	 */
	public void dispatchEvent(SpringEvent event) {
		applicationContext.publishEvent(event);
	}
}
