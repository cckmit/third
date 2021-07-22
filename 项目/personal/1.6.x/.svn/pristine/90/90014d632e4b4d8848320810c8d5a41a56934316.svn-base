package org.opoo.apps.event.v2;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;


/**
 * 事件监听器包装类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class EventListenerWrapper<E extends Event> implements EventListener<E>{
	
	private static final Log log = LogFactory.getLog(EventListenerWrapper.class);
	private final EventListener<E> listener;
	private Class<E> supportsEventClass;
	private AtomicBoolean supportsEventClassSet = new AtomicBoolean(false);
	
	public EventListenerWrapper(EventListener<E> listener) {
		super();
		Assert.notNull(listener);
		this.listener = listener;
	}

	/**
	 * 处理事件。
	 */
	public void handle(E event) {
		if(accepts(event)){
			listener.handle(event);
		}
	}

	/**
	 * 判断指定事件是否可被当前事件监听器处理。
	 * @param event
	 * @return
	 */
	public boolean accepts(E event) {
		if(!supportsEventClassSet.get()){
			supportsEventClass = EventUtils.getParameterizedEventClass(listener);
			supportsEventClassSet.set(true);
			if(log.isDebugEnabled()){
				log.debug("Looking event class for listener: " + listener 
						+ " -> " + supportsEventClass);
			}
		}
		return supportsEventClass == null ? true : supportsEventClass.isInstance(event);
	}

	/**
	 * 获取原始的事件监听器。
	 * @return the listener
	 */
	public EventListener<E> getListener() {
		return listener;
	}
}
