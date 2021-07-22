package org.opoo.apps.event;

/**
 * �¼���������
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <E>
 */
public interface EventListener<E extends Event<?>> extends java.util.EventListener {
	/**
	 * ִ�С�
	 * @param event
	 */
	void perform(E event);
}
