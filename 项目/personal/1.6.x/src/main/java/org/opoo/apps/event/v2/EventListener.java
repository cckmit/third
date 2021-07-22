package org.opoo.apps.event.v2;



/**
 * 事件监听器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E>
 */
@SuppressWarnings("unchecked")
public interface EventListener<E extends Event> extends java.util.EventListener{
	/**
	 * 处理事件。
	 * @param event
	 */
	void handle(E event);
}
