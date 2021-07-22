package org.opoo.apps.dv.office.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.provider.TestConvertibleObject;
import org.opoo.apps.dv.util.Utils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jivesoftware.office.manager.FileManager;

public class OfficeConversionManagerImplTest {
	private static ClassPathXmlApplicationContext ctx;

	@BeforeClass
	public static void init(){
		Utils.setDevMode(true);
		ctx = new ClassPathXmlApplicationContext("dv/spring-dv.xml");
	}
	
	@AfterClass
	public static void destroy(){
		ctx.close();
	} 
	
	@Test
	public void test1() throws IOException {
		Object bean = ctx.getBean("officeConversionManager");
		System.out.println(bean);
		System.out.println(ctx.getBean("officeConverterFactory"));
		
		OfficeConversionManagerImpl manager = (OfficeConversionManagerImpl) bean;
		TestConvertibleObject object = new TestConvertibleObject("pss2.pdf", "application/pdf", new File("D:/pss2.pdf"));
		System.out.println(manager);
		System.out.println(object);
		
		String s = String.format("%s-%s-%s-%s", Utils.getInstanceID(), 100, OfficeConversionArtifactType.Pdf.getName(), 2);
		System.out.println(s);
		
		//FileManager bean2 = (FileManager) ctx.getBean("fileManager");
		//System.out.println(bean2.getWorkingDirectory());
		
		
		//System.out.println(ctx.getBean("pdf2swfOfficeCommandExecutor"));
		
		
		manager.convert(object);
		
		
//		OfficeConversionMetaData metaData = manager.getConversionMetaData(1332404384469L);
//		OfficeConversionArtifact ca = manager.getConversionArtifact(metaData, OfficeConversionArtifactType.Preview, 0);
//		InputStream stream = manager.getConversionArtifactBody(ca);
//		IOUtils.copy(stream, new FileOutputStream("D:\\xxx.0.swf"));
		
		try {
			Thread.sleep(3*60*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
