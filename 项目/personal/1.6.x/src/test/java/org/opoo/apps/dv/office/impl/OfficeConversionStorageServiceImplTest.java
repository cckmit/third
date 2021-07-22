package org.opoo.apps.dv.office.impl;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.converter.DefaultConverterFactory;
import org.opoo.apps.dv.office.MockConvertableObject;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionManager;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.provider.ConvertibleObjectProvider;
import org.opoo.apps.dv.provider.ConvertibleObjectProviderFactory;
import org.opoo.apps.dv.provider.SpringConvertibleObjectProviderFactory;
import org.opoo.apps.dv.provider.TestConvertibleObject;
import org.opoo.apps.service.MimeTypeManager;
import org.opoo.apps.util.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OfficeConversionStorageServiceImplTest{

	private static ClassPathXmlApplicationContext ctx;

	@BeforeClass
	public static void init(){
		ctx = new ClassPathXmlApplicationContext("dv/spring-dv.xml");
	}
	
	@AfterClass
	public static void destroy(){
		ctx.close();
	}
	
	public void test1() {
		OfficeConversionStorageServiceImpl service = (OfficeConversionStorageServiceImpl) ctx.getBean("officeConversionStorageService");
		assertNotNull(service);
		System.out.println(service);
		System.out.println(service.getClass());
		
		OfficeConversionStorageService s = (OfficeConversionStorageService) ctx.getBean("officeConversionStorageServiceProxy");
		assertNotNull(s);
		System.out.println(s);
		System.out.println(s.getClass());
		System.out.println(s == service);
		
		
		OfficeConversionMetaData meta = s.getMetaData(12399348L);
		System.out.println(meta);
		
		SpringConvertibleObjectProviderFactory f = (SpringConvertibleObjectProviderFactory) ctx.getBean("coProviderFactory");
		System.out.println(f);
		
		
		TestConvertibleObject co = new MockConvertableObject();
		boolean reg = f.hasRegisteredProvider(co);
		System.out.println(reg);
		if(reg){
			ConvertibleObjectProvider provider = f.get(co);
			System.out.println(provider.getContentLength());
		}
	}
	
	@Test
	public void test2() throws IOException, Exception{
		DefaultConverterFactory f = (DefaultConverterFactory) ctx.getBean("officeConverterFactory");
		System.out.println(f);
		Converter converter = f.getConverter(OfficeConversionArtifactType.Pdf);
		System.out.println(converter);
		
		OfficeConversionManager manager = (OfficeConversionManager) ctx.getBean("officeConversionManager");
		System.out.println(manager);
		TestConvertibleObject co = new TestConvertibleObject("pss2.pdf", "application/pdf",  new File("E:\\pss2.pdf"));
		boolean result = manager.convert(co);
		System.out.println("RESULT =======> " + result);
		
		
		ConvertibleObjectProviderFactory pf = (ConvertibleObjectProviderFactory) ctx.getBean("coProviderFactory");
		ConvertibleObjectProvider cop = pf.get(co);
		System.out.println(cop.getFilename());
		
		
		MimeTypeManager mtm = (MimeTypeManager) ctx.getBean("mimeTypeManagerImpl");
		String mimeType = mtm.getExtensionMimeType(cop.getFilename());
		System.out.println(mimeType);
		
		
		/*
		OfficeConversionMetaData cm = manager.getConversionMetaData(-1988, -768, 1);
		System.out.println(cm);
		
		OfficeConversionArtifact ca = manager.getConversionArtifact(cm, OfficeConversionArtifactType.Pdf, 0);
		System.out.println(ca);
		writeToFile(manager.getConversionArtifactBody(ca), "D:\\temp\\-1988.-768.pdf");
		
//		ca = manager.getConversionArtifact(cm, OfficeConversionArtifactType.Pdf, 0);
//		writeToFile(manager.getConversionArtifactBody(ca), "D:\\temp\\-1988.-768.swf");
		ca = manager.getConversionArtifact(cm, OfficeConversionArtifactType.Preview, 1);
		writeToFile(manager.getConversionArtifactBody(ca), "D:\\temp\\-1988.-768.1.swf");
		
		ca = manager.getConversionArtifact(cm, OfficeConversionArtifactType.Thumbnail, 1);
		writeToFile(manager.getConversionArtifactBody(ca), "D:\\temp\\-1988.-768.png");
		*/
		Thread.sleep(100000L);
	}
	

	void writeToFile(InputStream is, String filename) throws Exception{
		writeToFile(new File(filename), is);
	}
	protected static void writeToFile(File file, InputStream is) throws Exception {
		OutputStream fos = null;
		try {
			fos = FileUtils.openOutputStream(file);
			IOUtils.copy(is, fos);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fos);
		}
	}
}
