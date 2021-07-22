package org.opoo.apps.dv.office.converter;

import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dv.converter.AbstractConverterFactory;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.office.OfficeConversionConfig;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor;
import org.opoo.apps.event.v2.EventDispatcher;

public class GenericOfficeConverterFactory extends AbstractConverterFactory{
	private EventDispatcher dispatcher;
    private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
    private ServiceHostObjectFactory serviceHostObjectFactory;
    private OfficeConversionConfig conversionConfig;
	private OfficeConversionStorageService conversionStorageService;
		
	public void setEventDispatcher(EventDispatcher dispatcher) {
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

	@Override
	protected Converter createConverter(ConversionArtifactType type){
		GenericOfficeConverter converter = buildConverterInstance();
		//done in AbstractConverterFactory
		//converter.setConversionArtifactType(type);
		converter.setEventDispatcher(dispatcher);
		converter.setConversionConfig(conversionConfig);
		converter.setHttpClientExecutor(new HttpClientExecutor(httpClientFactory));
		converter.setConversionStorageService(conversionStorageService);
		converter.setServiceHostObjectFactory(serviceHostObjectFactory);
		return converter;
	}
	
	protected GenericOfficeConverter buildConverterInstance(){
		return new GenericOfficeConverter();
	}
}
