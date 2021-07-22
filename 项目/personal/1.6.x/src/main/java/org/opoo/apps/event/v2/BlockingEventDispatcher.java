package org.opoo.apps.event.v2;

import java.util.Set;


/**
 * 阻塞的事件分发器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class BlockingEventDispatcher implements EventDispatcher {
	private EventListenerManager holder;
	public BlockingEventDispatcher(EventListenerManager holder){
		this.holder = holder;
	}
	
	public <E extends Event> void dispatchEvent(E event) {
		Set<? extends EventListener> set = holder.getEventListeners(event);
		for(EventListener l: set){
			l.handle(event);
		}
	}
	
	public EventListenerManager getEventListenerManager(){
		return holder;
	}
}
