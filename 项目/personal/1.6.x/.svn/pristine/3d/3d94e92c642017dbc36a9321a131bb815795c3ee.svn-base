package org.opoo.apps.conversion.event;

import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.event.v2.Event;

public class ConversionEvent extends Event<ConversionEvent.Type, ConversionRevision> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6777838521972279222L;

	public enum Type{
		Complete,Error
	}

	public ConversionEvent(Type eventType, ConversionRevision payload) {
		super(eventType, payload);
	}
}
