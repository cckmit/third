package org.opoo.apps.event.v2;

import java.util.Set;


/**
 * �¼���������������
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public interface EventListenerManager {
	/**
	 * ����¼���������
	 * @param listener �¼�������
	 */
	void addEventListener(EventListener listener);
	
	/**
	 * �ж��Ƿ����ָ�����¼���������
	 * @param listener �¼�������
	 * @return
	 */
	boolean hasEventListener(EventListener listener);
	
	/**
	 * ɾ���¼���������
	 * @param listener
	 */
	void removeEventListener(EventListener listener);
	
	/**
	 * ��ȡ�¼����������ϡ�
	 * @return
	 */
	Set<? extends EventListener> getEventListeners();
	
	/**
	 * ��ȡ���Դ���ָ���¼����¼����������ϡ�
	 * @param event �¼�
	 * @return
	 */
	Set<? extends EventListener> getEventListeners(Event event);
}
