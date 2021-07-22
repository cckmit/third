package org.opoo.apps.module.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.module.Module;


/**
 * 模块事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleEvent extends Event<ModuleEvent.Type, Module<?>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6429967372400904845L;

	public ModuleEvent(Type eventType, Module<?> source) {
		super(eventType, source);
	}

	public static enum Type{
		/**
		 * 模块以创建。
		 */
		CREATED, 
		/**
		 * 模块已销毁。
		 */
		DESTROYED;
	}
}
