package org.opoo.apps.event.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * 阻塞式事件分发处理器。
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class BlockingEventDispatcher implements EventDispatcher {

	
	private Map<Enum, List<EventListener>> listeners = new ConcurrentHashMap<Enum, List<EventListener>>();
	
	public void addEventListener(Enum type, EventListener l) {
		List<EventListener> ls = listeners.get(type);
		if(ls == null){
			ls = new CopyOnWriteArrayList<EventListener>();
			listeners.put(type, ls);
		}
		ls.add(l);
	}

	public void dispatchEvent(Event event) {
		List<EventListener> ls = listeners.get(event.getType());
		if(ls != null){
			ls = new ArrayList<EventListener>();
			for(EventListener l: ls){
				l.handle(event);
			}
		}
	}

	public boolean hasEventListener(Enum type, EventListener l) {
		List<EventListener> ls = listeners.get(type);
		if(ls == null){
			return false;
		}
		return ls.contains(l);
	}

	public void removeEventListener(Enum type, EventListener l) {
		List<EventListener> ls = listeners.get(type);
		if(ls != null){
			ls.remove(l);
		}
	}
}
