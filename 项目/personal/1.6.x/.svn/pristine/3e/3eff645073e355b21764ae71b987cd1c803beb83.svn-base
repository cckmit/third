package org.opoo.apps.dvi.converter;

import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dvi.ConversionArtifactType;

public class DefaultConverterFactory implements ConverterFactory {
	private ConcurrentMap<String, Converter> converters;

	public void setConverters(ConcurrentMap<String, Converter> converters) {
		this.converters = converters;
	}

	public Converter getConverter(ConversionArtifactType type) {
		Converter converter = converters.get(type.getName());
		if (converter == null) {
			throw new RuntimeException("ÕÒ²»µ½×ª»»Æ÷");
		}
		return converter;
	}
}
