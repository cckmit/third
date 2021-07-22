package org.opoo.apps.event.v2;



/**
 * �¼���������
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E>
 */
@SuppressWarnings("unchecked")
public interface EventListener<E extends Event> extends java.util.EventListener{
	/**
	 * �����¼���
	 * @param event
	 */
	void handle(E event);
}
