package org.opoo.apps.event.v2;

/**
 * 事件分发器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface EventDispatcher {
	
	/**
	 * 分发事件。
	 * 
	 * @param <E> 事件类型
	 * @param event 事件
	 */
	@SuppressWarnings("unchecked")
	<E extends Event> void dispatchEvent(E event);
}
