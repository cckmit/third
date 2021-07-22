package org.opoo.apps.event.v2;

import java.util.Set;
import java.util.concurrent.Executor;


/**
 * 非阻塞的事件分发器。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
@SuppressWarnings("unchecked")
public class NonBlockingEventDispatcher implements EventDispatcher {
	private EventListenerManager holder;
	private Executor executor;
	public NonBlockingEventDispatcher(EventListenerManager holder){
		this.holder = holder;
	}
	
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}



	public <E extends Event> void dispatchEvent(final E event) {
		Set<? extends EventListener> set = holder.getEventListeners(event);
		for(final EventListener l: set){
			if(executor != null){
				executor.execute(new Runnable() {
					public void run() {
						l.handle(event);
					}
				});
			}else{
				l.handle(event);
			}
		}
	}
	
	public EventListenerManager getEventListenerManager(){
		return holder;
	}
}
