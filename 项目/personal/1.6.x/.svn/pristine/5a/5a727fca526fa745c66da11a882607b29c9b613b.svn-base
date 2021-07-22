package org.opoo.apps.event.v2;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.util.Assert;



/**
 * 事件监听器集合简单实现。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class EventListenerSetImpl extends CopyOnWriteArraySet<EventListener>
	implements EventListenerSet{

	private static final long serialVersionUID = -2158867738292313718L;
	
	/**
	 * 集合中是否包含指定的事件监听器。
	 * 
	 * @param listener
	 * @return
	 */
	public boolean contains(EventListener listener) {
		return super.contains(listener);
	}
	
	/**
	 * 添加事件监听器。
	 */
	public boolean add(EventListener listener){
		Assert.notNull(listener);
		if(!(listener instanceof EventListenerWrapper)){
			listener = new EventListenerWrapper(listener);
		}
		return super.add(listener);
	}

	public Iterator<EventListener> iterator(final Event event) {
		final Iterator<EventListener> it = iterator();
		return new Iterator<EventListener>(){
			private EventListenerWrapper x;
			private boolean next = false;
			public boolean hasNext() {
				while(it.hasNext()){
					EventListener e = it.next();
					if((e instanceof EventListenerWrapper) && 
							((EventListenerWrapper) e).accepts(event)){
						x = (EventListenerWrapper)e;
						next = true;
						return true;
					}
				}
				next = true;
				return false;
			}

			public EventListener next() {
				if(!next){
					hasNext();
				}
				next = false;
				EventListener result = null;
				if(x != null){
					result = x.getListener();
					x = null;
				}
				return result;
			}

			public void remove() {
				
			}
		};
	}
	public boolean remove(EventListener listener) {
		Iterator<EventListener> it = iterator();
		EventListenerWrapper remove = null;
		while(it.hasNext()){
			EventListenerWrapper w = (EventListenerWrapper) it.next();
			if(w.getListener().equals(listener)){
				remove = w;
				break;
			}
		}
		if(remove != null){
			return super.remove(remove);
		}
		return false;
	}
}
