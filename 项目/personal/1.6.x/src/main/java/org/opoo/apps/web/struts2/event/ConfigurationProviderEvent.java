package org.opoo.apps.web.struts2.event;

import org.opoo.apps.event.v2.Event;

import com.opensymphony.xwork2.config.ConfigurationProvider;

public class ConfigurationProviderEvent extends Event<ConfigurationProviderEvent.Type, ConfigurationProvider> {
	private static final long serialVersionUID = -7414562270297753163L;

	public ConfigurationProviderEvent(Type eventType, ConfigurationProvider source) {
		super(eventType, source);
	}

	public static enum Type{
		ADD,REMOVE;
	}
}
