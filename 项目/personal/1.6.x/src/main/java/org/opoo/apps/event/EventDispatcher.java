package org.opoo.apps.event;

/**
 * 事件分发器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <E>
 */
public interface EventDispatcher<E extends Event<?>>{
	/**
	 * 添加事件监听器。
	 * 
	 * @param eventType 事件类型
	 * @param listener 事件监听器
	 */
	void addEventListener(int eventType, EventListener<E> listener);
	/**
	 * 移除事件监听器
	 * @param eventType 事件类型
	 * @param listener 事件监听器
	 */
	void removeEventListener(int eventType, EventListener<E> listener);
	/**
	 * 判断是否存在指定的事件监听器
	 * @param eventType 事件类型
	 * @param listener 事件监听器
	 * @return
	 */
	boolean hasEventListener(int eventType, EventListener<E> listener);
	/**
	 * 分发事件。
	 * @param e 事件
	 */
	void dispatchEvent(E e);
}
