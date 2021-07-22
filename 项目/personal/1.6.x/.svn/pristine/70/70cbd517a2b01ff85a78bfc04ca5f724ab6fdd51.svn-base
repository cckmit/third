package org.opoo.apps.dv.office.conversion;

import java.io.File;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.office.TestUtils;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor.ExecutorCommand;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor.ConversionException;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor.FileDataProvider;
import org.opoo.apps.dv.util.Utils;

public class HttpClientExecutorTest {

	private static HttpClientExecutor executor;
	private static File outputDir;

	@BeforeClass
	public static void init(){
		Utils.setDevMode(true);
		 executor = new HttpClientExecutor(new DummyHttpClientFactory());
		 //executor.setHttpClientFactory(new DummyHttpClientFactory());
//		 executor.setObjectMapper(new ObjectMapper());
		 //executor.setServiceAddresses(Lists.newArrayList("http://localhost:60018/conversion/v1"));
		 
		 File file = new File(System.getProperty("java.io.tmpdir"), "HttpClientExecutorTest");
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 outputDir = file;
	}
	
	@Test
	public void test1(){
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(outputDir);
	}
	
	@Test
	public void test() throws ConversionException {
		String service = "http://localhost:60018/conversion/v1";
		
		String jobID = UUID.randomUUID().toString();
		ExecutorCommand command = ExecutorCommand.OFFICETOPDF;
		File file = TestUtils.getTestFile("test.doc");
		System.out.println(file);
		File target = new File(outputDir, "pss2" + System.currentTimeMillis() + ".pdf");
		
		FileDataProvider provider = new FileDataProvider(file, file.getName());
		boolean inputFileCached = true;
		boolean alwaysPassInputFile = true;
		int executorTimeout = 120000;
		executor.execute(service, jobID, command, provider, target, 0, inputFileCached, alwaysPassInputFile, executorTimeout);
		System.out.println(target.length());
		
		if(target.exists()){
			file = target;
			provider = new FileDataProvider(file, file.getName());
			
			jobID = UUID.randomUUID().toString();
			command = ExecutorCommand.PDF2SWF;
			target =  new File(outputDir, "pss2" + System.currentTimeMillis() + ".swf");
			
			executor.execute(service, jobID, command, provider, target, 0, inputFileCached, alwaysPassInputFile, executorTimeout);
			System.out.println(target.length());
		}
	}

}
