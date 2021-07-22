package org.opoo.apps.dv.office.converter.jive;

import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.converter.AbstractConverterFactory;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.event.v2.EventDispatcher;

import com.jivesoftware.office.manager.ConversionManager;


public class JiveConversionManagerConverterFactory extends AbstractConverterFactory {
	private EventDispatcher dispatcher;
	private ConversionManager conversionManager;

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setConversionManager(ConversionManager conversionManager) {
		this.conversionManager = conversionManager;
	}

	@Override
	protected Converter createConverter(ConversionArtifactType type) {
		JiveConversionManagerConverter converter = new JiveConversionManagerConverter();
		converter.setEventDispatcher(dispatcher);
		converter.setConversionManager(conversionManager);
		//done in AbstractConverterFactory
		//converter.setConversionArtifactType(type);
		return converter;
	}
}
