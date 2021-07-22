package org.opoo.apps.dv.converter;

import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dv.ConversionArtifactType;

public class DefaultConverterFactory implements ConverterFactory {
	private ConcurrentMap<String,Converter> converters;

	public void setConverters(ConcurrentMap<String, Converter> converters) {
		this.converters = converters;
	}

	public Converter getConverter(ConversionArtifactType type) {
		Converter converter = converters.get(type.getName());
		if(converter == null){
			throw new RuntimeException("ÕÒ²»µ½×ª»»Æ÷£º" + type);
		}
		return converter;
	}
}
