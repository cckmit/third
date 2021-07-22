package org.opoo.apps.event.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Spring �¼��ַ���ʵ���ࡣ
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SpringEventDispatcherImpl implements ApplicationContextAware, SpringEventDispatcher {
	private ApplicationContext applicationContext;
	
	/**
	 * ���� Spring ��������
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * �ַ��¼���
	 */
	public void dispatchEvent(SpringEvent event) {
		applicationContext.publishEvent(event);
	}
}
