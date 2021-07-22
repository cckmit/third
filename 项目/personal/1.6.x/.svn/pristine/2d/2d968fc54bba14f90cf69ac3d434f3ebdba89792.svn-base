package org.opoo.apps.file.openoffice.sheet;

import static org.opoo.apps.file.util.FileUtils.streamToString;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.file.FileUpdater;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;


/**
 * 实际调用命令行执行XLSRowOptimalHeightCommand的main函数。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CommandLineXLSRowOptimalHeightRefreshUpdater implements FileUpdater, InitializingBean, BeanNameAware {
	public static final String PROP_PREFIX = "attachments.fileUpdaters.";
	private static final Log log = LogFactory.getLog(CommandLineXLSRowOptimalHeightRefreshUpdater.class);
	
	private String name;
	private String command = "java -jar excelrefresh-cli.jar ";
	private File directory;
	
	public void update(File input, File output) {
		refreshInternal(input.getAbsolutePath(), output.getAbsolutePath());
	}
	
	protected void refreshInternal(String inputFileName, String outputFileName){
		String cmd = getCommand() + " " + inputFileName + " " + outputFileName;
		log.info("执行命令：" + cmd);
		try {
			//Process process = Runtime.getRuntime().exec(command, null, dir);
			Process process = Runtime.getRuntime().exec(cmd, null, getDirectory());
			
			String msg = streamToString(process.getInputStream());
			if(msg.length() > 0){
				log.info(msg);
			}
			String error = streamToString(process.getErrorStream());
//			if(error.length() > 0){
//				log.error(error);
//				//注意，有些命令行的正确信息输出在这里
//				throw new RuntimeException(error);
//			}
			
			File file = new File(outputFileName);
			if(!file.exists()){
				throw new RuntimeException(error);
			}else{
				log.info(error);
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the directory
	 */
	public File getDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public void setWorkDir(String dir){
		if(StringUtils.isNotBlank(dir)){
			setDirectory(new File(dir));
		}
	}
	
	public String getWorkDir(){
		return directory != null ? directory.getAbsolutePath() : null;
	}
	

	
	public void setBeanName(String name) {
		this.name = name;
		loadFromGlobalsProperties();
	}
	public void afterPropertiesSet() throws Exception {
		//saveToGlobalsProperties();
	}
	
	protected void loadFromGlobalsProperties(){
		String cmd = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".command");
		String dir = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".workDir");
		if(StringUtils.isNotBlank(cmd)){
			setCommand(cmd);
		}
		if(StringUtils.isNotBlank(dir)){
			setWorkDir(dir);
		}
	}
	
	protected void saveToGlobalsProperties(){
		if(command != null){
			AppsGlobals.setSetupProperty(PROP_PREFIX + name + ".command", getCommand());
		}
		if(directory != null){
			AppsGlobals.setSetupProperty(PROP_PREFIX + name + ".workDir", getDirectory().getAbsolutePath());
		}
	}
	
	
	public static void main(String[] args){
		CommandLineXLSRowOptimalHeightRefreshUpdater s = new CommandLineXLSRowOptimalHeightRefreshUpdater();
		s.setDirectory(new File("D:\\My Documents\\Downloads\\jodconverter-2.2.2\\lib"));
		s.update(new File("E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls"), new File("d:\\excelrefreshed.xls"));
	}
}
