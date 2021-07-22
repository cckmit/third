package org.opoo.apps.event.v2;


/**
 * 可设置事件监听管理器的对象的接口。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface EventListenerManagerAware {
	/**
	 * 
	 * @param manager
	 */
	void setEventListenerManager(EventListenerManager manager);
}
