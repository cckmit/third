package org.opoo.apps.event.spring;


/**
 *  Spring �¼��ַ�����
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SpringEventDispatcher {
	
	/**
	 * �ַ��¼���
	 * @param event
	 */
	void dispatchEvent(SpringEvent event);
}
