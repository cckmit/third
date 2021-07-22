package org.opoo.apps.event.v2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 包装的事件监听器管理器实现类。
 * 
 * 内部存储的都是经过包装的事件监听器对象。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class WrappedEventListenerManager implements EventListenerManager {
	private static final Log log = LogFactory.getLog(WrappedEventListenerManager.class);
	private Set<EventListenerWrapper> listeners = new CopyOnWriteArraySet<EventListenerWrapper>();
	
	public WrappedEventListenerManager(){
	}
	
	public void addEventListener(EventListener listener) {
		if(listener == null){
			throw new NullPointerException();
		}
		if(listener instanceof EventListenerWrapper){
			listeners.add((EventListenerWrapper) listener);
		}else{
			listeners.add(new EventListenerWrapper(listener));
		}
		if(log.isDebugEnabled()){
			log.debug("注册事件监听器：" + listener.getClass().getName());
		}
	}

	public Set<? extends EventListener> getEventListeners() {
//		Set<EventListener> set = new HashSet<EventListener>();
//		Iterator<EventListenerWrapper> it = listeners.iterator();
//		while(it.hasNext()){
//			set.add(it.next().getListener());
//		}
//		return set;
//		
		return new HashSet<EventListener>(listeners);
	}

	public Set<? extends EventListener> getEventListeners(Event event) {
		Set<EventListener> set = new HashSet<EventListener>();
		Iterator<EventListenerWrapper> it = listeners.iterator();
		while(it.hasNext()){
			EventListenerWrapper w = it.next();
			if(w.accepts(event)){
				set.add(w.getListener());
			}
		}
		return set;
	}

	public boolean hasEventListener(EventListener listener) {
		Iterator<EventListenerWrapper> it = listeners.iterator();
		while(it.hasNext()){
			EventListenerWrapper w = it.next();
			if(w.getListener().equals(listener)){
				return true;
			}
		}
		return false;
	}

	public void removeEventListener(EventListener listener) {
		Iterator<EventListenerWrapper> it = listeners.iterator();
		EventListenerWrapper remove = null;
		while(it.hasNext()){
			EventListenerWrapper w = it.next();
			if(w.getListener().equals(listener)){
				remove = w;
				break;
			}
		}
		if(remove != null){
			listeners.remove(remove);
			if(log.isDebugEnabled()){
				log.debug("移除事件监听器：" + listener.getClass().getName());
			}
		}
	}
}
