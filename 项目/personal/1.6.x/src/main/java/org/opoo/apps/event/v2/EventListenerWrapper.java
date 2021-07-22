package org.opoo.apps.event.v2;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;


/**
 * �¼���������װ�ࡣ
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
	 * �����¼���
	 */
	public void handle(E event) {
		if(accepts(event)){
			listener.handle(event);
		}
	}

	/**
	 * �ж�ָ���¼��Ƿ�ɱ���ǰ�¼�����������
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
	 * ��ȡԭʼ���¼���������
	 * @return the listener
	 */
	public EventListener<E> getListener() {
		return listener;
	}
}
