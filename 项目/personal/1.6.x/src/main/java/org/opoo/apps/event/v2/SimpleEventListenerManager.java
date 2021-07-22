package org.opoo.apps.event.v2;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * 简单的事件监听器管理器类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class SimpleEventListenerManager implements EventListenerManager {
	private Set<EventListener> listeners = new CopyOnWriteArraySet<EventListener>();
	
	public void addEventListener(EventListener listener) {
		listeners.add(listener);
	}

	public Set<? extends EventListener> getEventListeners() {
		return listeners;
	}

	public Set<? extends EventListener> getEventListeners(Event event) {
		return listeners;
	}

	public boolean hasEventListener(EventListener listener) {
		return listeners.contains(listener);
	}

	public void removeEventListener(EventListener listener) {
		listeners.remove(listener);
	}

}
