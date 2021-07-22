package org.opoo.apps.security.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.security.OldPresence;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PresenceEvent extends Event<PresenceEvent.Type, OldPresence> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812676850629956832L;

	public PresenceEvent(Type eventType, OldPresence source) {
		super(eventType, source);
	}

	public static enum Type{
		LAST_UPDATE_TIME_CHANGE;
	}
}
