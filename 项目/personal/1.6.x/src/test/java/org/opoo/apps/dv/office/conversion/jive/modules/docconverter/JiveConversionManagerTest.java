package org.opoo.apps.dv.office.conversion.jive.modules.docconverter;

import java.io.File;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.TestUtils;
import org.opoo.apps.dv.util.Utils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jivesoftware.office.command.ConversionCommandExecutor.CommandExecutionResult;
import com.jivesoftware.office.manager.ConversionCommandType;
import com.jivesoftware.office.manager.ConversionManager;
import com.jivesoftware.office.manager.ConversionManager.CommandConversionStatistics;

public class JiveConversionManagerTest {
	private static ClassPathXmlApplicationContext context;

	@BeforeClass
	public static void init(){
		Utils.setDevMode(true);
		context = new ClassPathXmlApplicationContext("dv/spring-dv-jive-modules-docconverter.xml");
	}
	
	@AfterClass
	public static void destroy(){
		context.destroy();
	}
	
	@Test
	public void test() {
		ConversionManager conversionManager = (ConversionManager) context.getBean("conversionManagerImpl");
		System.out.println(conversionManager);
		CommandConversionStatistics statistics = conversionManager.getStatistics(ConversionCommandType.OFFICETOPDF);
		System.out.println(statistics);
		
		//executeCommand(String jobID, ConversionCommandType type, File input, int page, String outExt)
		String jobID = UUID.randomUUID().toString();
		ConversionCommandType type = ConversionCommandType.OFFICETOPDF;
		File input = TestUtils.getTestFile("test.doc");
		System.out.println(input);
		
		int page = 0;
		String outExt = "pdf";
		CommandExecutionResult result = conversionManager.executeCommand(jobID, type, input, page, outExt);
		if(result.isSuccessful()){
			System.out.println(result.getResult());
			input = result.getResult();
		}else{
			return;
		}
		
		jobID = UUID.randomUUID().toString();
		type = ConversionCommandType.PDF2SWF;
		page = 0;
		outExt = "swf";
		result = conversionManager.executeCommand(jobID, type, input, page, outExt);
		if(result.isSuccessful()){
			System.out.println(result.getResult());
		}
	}
}
