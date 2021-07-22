package org.opoo.apps.event.v2;

import java.util.Iterator;



/**
 * �¼����������ϡ�
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public interface EventListenerSet extends Iterable<EventListener> {
	
	/**
	 * ����¼���������
	 * @param listener
	 * @return
	 */
	boolean add(EventListener listener);
	
	/**
	 * ɾ���¼���������
	 * @param listener
	 * @return
	 */
	boolean remove(EventListener listener);
	
//	boolean contains(EventListener listener);
	
	/**
	 * 
	 * ��������ɴ���ָ���¼���������
	 * @param event �¼�
	 * @return
	 */
	Iterator<EventListener> iterator(Event event);
}
