package org.opoo.apps.security.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.security.User;

public class OnlineEvent extends Event<OnlineEvent.Type, User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7061260095730548220L;

	public OnlineEvent(Type eventType, User source) {
		super(eventType, source);
	}

	public static enum Type{
		ONLINE, OFFLINE;
	}
}
