package org.opoo.apps.event.v2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * ÊÂ¼þ×¢²áÆ÷¡£
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AutoEventRegistrar implements BeanPostProcessor{
	private static final Log log = LogFactory.getLog(AutoEventRegistrar.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private EventDispatcher eventDispatcher;
	private EventListenerManager eventListenerManager;
	
	/**
	 * @return the eventDispatcher
	 */
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}
	/**
	 * @param eventDispatcher the eventDispatcher to set
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		if(IS_DEBUG_ENABLED){
			log.debug("AutoEventRegistrar will use dispatcher: " + eventDispatcher);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof EventListener){
			eventListenerManager.addEventListener((EventListener<?>) bean);
			//log.info("×¢²áÊÂ¼þ¼àÌýÆ÷: " + bean);
			if(IS_DEBUG_ENABLED){
				log.debug("×¢²áV2°æÊÂ¼þ¼àÌýÆ÷: " + bean);
			}
		}
		if(bean instanceof EventListenerManagerAware){
			((EventListenerManagerAware) bean).setEventListenerManager(eventListenerManager);
			log.info("Registering bean " + beanName + " as an EventListenerManagerAware.");
		}
		return bean;
	}
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof EventDispatcherAware) {
			((EventDispatcherAware) bean).setEventDispatcher(eventDispatcher);
			log.info("Registering bean " + beanName + " as an EventDispatcherAware.");
		}
		return bean;
	}
	/**
	 * @return the eventListenerManager
	 */
	public EventListenerManager getEventListenerManager() {
		return eventListenerManager;
	}
	/**
	 * @param eventListenerManager the eventListenerManager to set
	 */
	public void setEventListenerManager(EventListenerManager eventListenerManager) {
		this.eventListenerManager = eventListenerManager;
	}

}
