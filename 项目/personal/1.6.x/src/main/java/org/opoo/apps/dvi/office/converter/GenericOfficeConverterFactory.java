package org.opoo.apps.dvi.office.converter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.converter.Converter;
import org.opoo.apps.dvi.converter.ConverterFactory;
import org.opoo.apps.dvi.office.OfficeConversionArtifactType;
import org.opoo.apps.dvi.office.OfficeConversionConfig;
import org.opoo.apps.dvi.office.OfficeConversionStorageService;
import org.opoo.apps.event.v2.EventDispatcher;

public class GenericOfficeConverterFactory implements ConverterFactory{
	private EventDispatcher dispatcher;
    private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
    private ServiceHostObjectFactory serviceHostObjectFactory;
    private OfficeConversionConfig conversionConfig;
	private OfficeConversionStorageService conversionStorageService;
		
	private ConcurrentMap<ConversionArtifactType, GenericOfficeConverter> converters = new ConcurrentHashMap<ConversionArtifactType, GenericOfficeConverter>();
	
	
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}
	public void setConversionConfig(OfficeConversionConfig conversionConfig) {
		this.conversionConfig = conversionConfig;
	}

	public void setServiceHostObjectFactory(
			ServiceHostObjectFactory serviceHostObjectFactory) {
		this.serviceHostObjectFactory = serviceHostObjectFactory;
	}

	public void setConversionStorageService(
			OfficeConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}

	public Converter getConverter(ConversionArtifactType type) {
		GenericOfficeConverter converter = converters.get(type);
		if(converter == null){
			converter = buildConverter(type);
			converters.put(type, converter);
		}
		return converter;
	}
	
	GenericOfficeConverter buildConverter(ConversionArtifactType type){
		GenericOfficeConverter converter = buildConverterInstance();
		converter.setConversionArtifactType((OfficeConversionArtifactType) type);
		converter.setDispatcher(dispatcher);
		converter.setConversionConfig(conversionConfig);
		converter.setHttpClientFactory(httpClientFactory);
		converter.setConversionStorageService(conversionStorageService);
		converter.setServiceHostObjectFactory(serviceHostObjectFactory);
		return converter;
	}
	
	protected GenericOfficeConverter buildConverterInstance(){
		return new GenericOfficeConverter();
	}
}
