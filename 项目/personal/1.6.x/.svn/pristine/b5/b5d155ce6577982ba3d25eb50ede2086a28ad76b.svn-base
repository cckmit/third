package org.opoo.apps.file.converter;

import static org.junit.Assert.*;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.AppsHome;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dv.office.OfficeConversionConfig;
import org.opoo.apps.dv.office.impl.TestOfficeConversionConfigImpl;
import org.opoo.apps.file.converter.GenericFileTypeConverter.ConversionCommand;
import org.opoo.apps.json.jackson.JSONObjectMapper;

public class GenericFileTypeConverterTest {
	
	private static GenericFileTypeConverter genericFileTypeConverter;

	@BeforeClass
	public static void init() throws Exception{
		System.setProperty("apps.home", "E:\\work.home");
		System.setProperty(AppsHome.APPS_HOME_KEY, "E:\\work.home\\applications\\opoo-apps\\default-0001");
		
		OfficeConversionConfig conversionConfig = new TestOfficeConversionConfigImpl();
		ServiceHostObjectFactory serviceHostObjectFactory = new ServiceHostObjectFactory();
		serviceHostObjectFactory.setConnectionParams(conversionConfig.getServiceHosts());
		
		HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
		ObjectMapper objectMapper = new JSONObjectMapper();
		
		genericFileTypeConverter = new GenericFileTypeConverter();
		genericFileTypeConverter.setConversionConfig(conversionConfig);
		genericFileTypeConverter.setConversionType(GenericFileTypeConverter.ConversionType.Pdf);
		genericFileTypeConverter.setHttpClientFactory(httpClientFactory);
		genericFileTypeConverter.setObjectMapper(objectMapper);
		genericFileTypeConverter.setServiceHostObjectFactory(serviceHostObjectFactory);
		genericFileTypeConverter.setBeanName("pdfc");
		genericFileTypeConverter.afterPropertiesSet();
		genericFileTypeConverter.setEnabled(true);
	}

	@Test
	public void test() throws Exception {
		GenericFileTypeConverter genericFileTypeConverter2 = genericFileTypeConverter;
		System.out.println(genericFileTypeConverter2);
		File pdf = new File("E:/pss2" + System.currentTimeMillis() + ".pdf");
		genericFileTypeConverter2.convert(new File("E:/pss2.doc"), pdf);
		System.out.println(pdf.length());
		
		File swf = new File("E:/pss2" + System.currentTimeMillis() + ".swf");
		genericFileTypeConverter2.convert(ConversionCommand.PDF2SWF, pdf, swf);
		System.out.println(swf.length());
	}

}
