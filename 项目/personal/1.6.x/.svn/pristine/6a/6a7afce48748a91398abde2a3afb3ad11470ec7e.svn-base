package org.opoo.apps.event.v2;

import java.util.Iterator;



/**
 * 事件监听器集合。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public interface EventListenerSet extends Iterable<EventListener> {
	
	/**
	 * 添加事件监听器。
	 * @param listener
	 * @return
	 */
	boolean add(EventListener listener);
	
	/**
	 * 删除事件监听器。
	 * @param listener
	 * @return
	 */
	boolean remove(EventListener listener);
	
//	boolean contains(EventListener listener);
	
	/**
	 * 
	 * 迭代输出可处理指定事件监听器。
	 * @param event 事件
	 * @return
	 */
	Iterator<EventListener> iterator(Event event);
}
