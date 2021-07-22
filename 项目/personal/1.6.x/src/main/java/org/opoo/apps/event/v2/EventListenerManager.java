package org.opoo.apps.event.v2;

import java.util.Set;


/**
 * 事件监听器管理器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public interface EventListenerManager {
	/**
	 * 添加事件监听器。
	 * @param listener 事件监听器
	 */
	void addEventListener(EventListener listener);
	
	/**
	 * 判断是否包含指定的事件监听器。
	 * @param listener 事件监听器
	 * @return
	 */
	boolean hasEventListener(EventListener listener);
	
	/**
	 * 删除事件监听器。
	 * @param listener
	 */
	void removeEventListener(EventListener listener);
	
	/**
	 * 获取事件监听器集合。
	 * @return
	 */
	Set<? extends EventListener> getEventListeners();
	
	/**
	 * 获取可以处理指定事件的事件监听器集合。
	 * @param event 事件
	 * @return
	 */
	Set<? extends EventListener> getEventListeners(Event event);
}
