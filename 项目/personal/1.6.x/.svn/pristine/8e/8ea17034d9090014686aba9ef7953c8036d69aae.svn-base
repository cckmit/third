package org.opoo.apps.event.v1;


/**
 * 事件分发器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public interface EventDispatcher {
	
	
	/**
	 * 分发事件。
	 * @param event
	 */
	void dispatchEvent(Event event);
	
	/**
	 * 增加事件监听器。
	 * @param type 事件类型
	 * @param l 事件监听器
	 */
	void addEventListener(Enum type, EventListener l);
	
	/**
	 * 判断是否包含指定事件监听器。
	 * @param type 事件类型
	 * @param l 事件监听器
	 * @return
	 */
	boolean hasEventListener(Enum type, EventListener l);
	
	/**
	 * 移除事件监听器。
	 * @param type 事件类型
	 * @param l 事件监听器
	 */
	void removeEventListener(Enum type, EventListener l);
	
}
