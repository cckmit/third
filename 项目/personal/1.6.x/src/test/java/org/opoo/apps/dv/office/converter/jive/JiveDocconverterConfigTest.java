package org.opoo.apps.dv.office.converter.jive;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.TestUtils;

import com.jivesoftware.office.command.ConversionCommandExecutor.CommandExecutionResult;
import com.jivesoftware.office.command.commandline.GenericBashExecutionCommand;
import com.jivesoftware.office.command.openoffice.UnoRuntimeProvider;
import com.jivesoftware.office.command.openoffice.impl.OOKillExecutorImpl;
import com.jivesoftware.office.command.openoffice.impl.OpenOfficeConversionExecutorImpl;
import com.jivesoftware.office.command.openoffice.impl.OpenOfficeManagerImpl;
import com.jivesoftware.office.config.CommandConfig;
import com.jivesoftware.office.config.Configuration;
import com.jivesoftware.office.config.ConversionConfig.FileCacheConfig;
import com.jivesoftware.office.config.OpenOfficeConfig;
import com.jivesoftware.office.config.impl.ConfigManagerImpl;
import com.jivesoftware.office.manager.ConversionCommandType;
import com.jivesoftware.office.manager.impl.FileManagerImpl;


/**
 *    <!-- configuration manager -->
    <bean id="conversionConfigManagerImpl" class="com.jivesoftware.office.config.impl.ConfigManagerImpl">
        <!--- names ofJava system proeprties that have teh default and override configs(for different environments) -->
        <constructor-arg value="config.xml"/>
        <constructor-arg value="overrideConfig.xml"/>
    </bean>
    
        <bean id="fileManager" class="com.jivesoftware.office.manager.impl.FileManagerImpl" destroy-method="destroy">
        <constructor-arg value="#{conversionConfigManagerImpl.getFileCacheConfig()}"/>
    </bean>
    <bean id="pdf2swfOfficeCommandExecutor"
          class="com.jivesoftware.office.command.commandline.GenericBashExecutionCommand" destroy-method="destroy">
        <constructor-arg value="#{T(com.jivesoftware.office.manager.ConversionCommandType).PDF2SWF.name()}"/>
        <constructor-arg
                value="#{conversionConfigManagerImpl.getCommandConfig(T(com.jivesoftware.office.manager.ConversionCommandType).PDF2SWF)}"/>
        <constructor-arg ref="fileManager"/>
    </bean>
 *
 */
public class JiveDocconverterConfigTest {
	static ConfigManagerImpl configManager;
	static FileManagerImpl fileManager;
	
	static File outputDir;
	
	@BeforeClass
	public static void init() throws Exception{
		ConfigManagerImpl impl = new ConfigManagerImpl("config.xml", "overrideConfig.xml");
		FileManagerImpl fm = new FileManagerImpl(impl.getFileCacheConfig());
		configManager = impl;
		fileManager = fm;
		
		File file = new File(System.getProperty("java.io.tmpdir"), "JiveDocconverterConfigTest");
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 outputDir = file;
	}
	
	@Test
	public void testConfig(){
		FileCacheConfig config = configManager.getFileCacheConfig();
		System.out.println(config.getFrequency());
		System.out.println(config.getPath());
		System.out.println(config.getTtl());
	}

	//@Test
	public void testPdf2swf() throws Exception {
		Configuration pdf2swf = configManager.getCommandConfig(ConversionCommandType.PDF2SWF);
		System.out.println(pdf2swf.getCommand());
		
		GenericBashExecutionCommand command = new GenericBashExecutionCommand("PDF2SWF", (CommandConfig) pdf2swf, fileManager);
		//Executor.execute(jobID, input, page, outExt);
		String jobID = UUID.randomUUID().toString();
		File input = TestUtils.getTestFile("test.pdf");
		String outExt = "swf";
		int page = 2;
		CommandExecutionResult result = command.execute(jobID, input, page, outExt);
		if(result.isSuccessful()){
			System.out.println(result.getResult());
			FileUtils.copyFile(result.getResult(), new File(outputDir, "pss2-p" + page + ".swf"));
		}else{
			System.out.println(result.getError());
		}
	}
	
	//@Test
	public void testImager() throws Exception{
		Configuration cfg = configManager.getCommandConfig(ConversionCommandType.IMAGER);
		System.out.println(cfg.getCommand());
		
		GenericBashExecutionCommand command = new GenericBashExecutionCommand("IMAGER", (CommandConfig) cfg, fileManager);
		//Executor.execute(jobID, input, page, outExt);
		String jobID = UUID.randomUUID().toString();
		File input = TestUtils.getTestFile("test.pdf");
		String outExt = "png";
		int page = 1;
		CommandExecutionResult result = command.execute(jobID, input, page, outExt);
		if(result.isSuccessful()){
			System.out.println(result.getResult());
			FileUtils.copyFile(result.getResult(), new File(outputDir, "pss2-p" + page + ".png"));
		}else{
			System.out.println(result.getError());
		}
	}
	
	@Test
	public void testOffice2pdf() throws InterruptedException, IOException{
		OpenOfficeConfig cfg = (OpenOfficeConfig) configManager.getCommandConfig(ConversionCommandType.OFFICETOPDF);
		System.out.println(cfg);
		UnoRuntimeProvider unoProvider = new UnoRuntimeProvider(cfg);
		
		OOKillExecutorImpl killExecutor = new OOKillExecutorImpl(cfg.getKillConfig());
		
		OpenOfficeManagerImpl officeManager = new OpenOfficeManagerImpl();
		officeManager.setUnoRuntimeProvider(unoProvider);
		officeManager.setOoKillExecutor(killExecutor);
		officeManager.setPorts(toIntegerArray(cfg.getPorts()));
		officeManager.setOpenOfficeConfig(cfg);
		
		officeManager.init();
		
		OpenOfficeConversionExecutorImpl command = new OpenOfficeConversionExecutorImpl("OFFICETOPDF", cfg, fileManager);
		command.setConnectionManager(officeManager);
		command.setUnoProvider(unoProvider);
		
		
		//Executor.execute(jobID, input, page, outExt);
		String jobID = UUID.randomUUID().toString();
		File input = TestUtils.getTestFile("test.doc");
		String outExt = "pdf";
		int page = 3;
		CommandExecutionResult result = command.execute(jobID, input, page, outExt);
		if(result.isSuccessful()){
			System.out.println(result.getResult());
			FileUtils.copyFile(result.getResult(), new File(outputDir, "pss2-0.pdf"));
		}else{
			System.out.println(result.getError());
		}
		
		command.destroy();
		
		//killExecutor.kill(8888);
		//Thread.sleep(10000L);
		officeManager.destroy();
	}

	Integer[] toIntegerArray(int[] arr){
		Integer[] results = new Integer[arr.length];
		for(int i = 0 ; i < arr.length ; i++){
			results[i] = arr[i];
		}
		return results;
	}
}
