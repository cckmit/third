package org.opoo.apps.event.v1;


/**
 * 事件监听器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E>
 */
@SuppressWarnings("unchecked")
public interface EventListener<E extends Event> {
	/**
	 * 处理事件。
	 * @param event 事件
	 */
	void handle(E event);
}
