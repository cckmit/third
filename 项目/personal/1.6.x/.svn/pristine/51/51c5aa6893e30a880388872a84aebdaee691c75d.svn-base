package org.opoo.apps.dv.office.converter;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.office.TestUtils;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.dv.office.impl.TestOfficeConversionConfigImpl;
import org.opoo.apps.dv.office.model.OfficeMetaData;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.event.v2.Event;
import org.opoo.apps.event.v2.EventDispatcher;

import com.google.common.collect.Lists;

public class GenericOfficeConverterTest {

	@Test
	public void test() {
		
		/**
		 *  private EventDispatcher dispatcher;
    private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
    private ServiceHostObjectFactory serviceHostObjectFactory;
    private OfficeConversionConfig conversionConfig;
	private OfficeConversionStorageService conversionStorageService;
	private ObjectMapper objectMapper = new ObjectMapper();
		 */
		Utils.setDevMode(true);
		
		File outputDir = new File(System.getProperty("java.io.tmpdir"), "GenericOfficeConverterTest");
		 if(!outputDir.exists()){
			 outputDir.mkdirs();
		 }
		
		HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
		ServiceHostObjectFactory serviceHostObjectFactory = new ServiceHostObjectFactory();
		TestOfficeConversionConfigImpl conversionConfig = new TestOfficeConversionConfigImpl();
		serviceHostObjectFactory.setConnectionParams(conversionConfig.getServiceHosts());
		OfficeConversionStorageService conversionStorageService = null;
//		ObjectMapper objectMapper = new ObjectMapper();
		
		GenericOfficeConverter converter = new GenericOfficeConverter();
		converter.setConversionConfig(conversionConfig);
		converter.setConversionStorageService(conversionStorageService);
		converter.setHttpClientExecutor(new HttpClientExecutor(httpClientFactory));
		converter.setServiceHostObjectFactory(serviceHostObjectFactory);
//		converter.setObjectMapper(objectMapper);
		converter.setConversionArtifactType(OfficeConversionArtifactType.Pdf);

		final File target = new File(outputDir, "pss2-" + System.currentTimeMillis() + ".pdf");
		converter.setEventDispatcher(new EventDispatcher() {
			public <E extends Event> void dispatchEvent(E event) {
				if(event instanceof OfficeConversionEvent){
					OfficeConversionEvent e = (OfficeConversionEvent) event;
					if(e.getType() == OfficeConversionEvent.Type.ERROR){
						String message = e.getSource().getMessage();
						System.out.println("[eeeeeeeeee]" + message);
					}else{
						ConversionEventPayload payload = e.getSource();
						File file = payload.getGeneratedArtifactData();
						System.out.println(file);
						if(file != null){
							System.out.println(file.length());
						}
						try {
							FileUtils.copyFile(file, target);
							FileUtils.deleteQuietly(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		
		File file = TestUtils.getTestFile("test.doc");
		OfficeConversionMetaData metaData = new OfficeMetaData(100L, 1002, 1000L, "ÉÏ´«¸½¼þ.doc", file.length(), 0, 1, null, new Date(), null);
//		InputArtifact artifact = new InputArtifact(file, OfficeConversionArtifactType.File);
//		converter.convert(metaData, artifact, Lists.newArrayList(0));
		
		converter.convert(metaData, file, Lists.newArrayList(0));
		
		System.out.println(target);
	}

}
