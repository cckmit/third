package org.opoo.apps.file.converter;

import static org.opoo.apps.file.util.FileUtils.streamToString;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public class CommandLineFileTypeConverter extends AbstractFileTypeConverter implements BeanNameAware, InitializingBean{
	
	private static final Log log = LogFactory.getLog(CommandLineFileTypeConverter.class);
	
	
	private File directory;
	private String command;
	//private String name;
	
	public void convert(File input, File output) throws FileTypeConvertException {
		convertInternal(input.getAbsolutePath(), output.getAbsolutePath());
	}

	protected void convertInternal(String inputFileName, String outputFileName) throws FileTypeConvertException{
		String cmd = getCommand() + " " + inputFileName + " " + outputFileName;
		//cmd = "cmd /C D:/Downloads/swftools-2008-11-19-1013/p2swf d:\\VMware.Service.pdf d:\\aaa.swf";
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
//				throw new FileTypeConvertException(error);
//			}
			
			File file = new File(outputFileName);
//			if(!file.exists()){
//				throw new FileTypeConvertException("文件转换失败");
//			}
			
			if(file.exists() && file.length() > 10){
				if(error.length() > 0){
					log.warn(error);
				}
			}else{
				if(error.length() > 0){
					log.error(error);
				}
				throw new FileTypeConvertException(error);
			}
			
		} catch (IOException e) {
			throw new FileTypeConvertException(e);
		}
	}
	



	/**
	 * @return the workDir
	 */
	public String getWorkDir() {
		return directory != null ? directory.getAbsolutePath() : null;
	}

	/**
	 * @param workDir the workDir to set
	 */
	public void setWorkDir(String workDir) {
		if(StringUtils.isNotBlank(workDir)){
			this.directory = new File(workDir);
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

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		String name = getBeanName();
		String cmd = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".command");
		String dir = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".workDir");
		if(StringUtils.isNotBlank(cmd)){
			setCommand(cmd);
		}
		if(StringUtils.isNotBlank(dir)){
			setWorkDir(dir);
		}
	}
	
//	protected void loadFromGlobalsProperties(){
//		String cmd = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".command");
//		String dir = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".workDir");
//		boolean enabled = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".enabled", true);
//		if(StringUtils.isNotBlank(cmd)){
//			setCommand(cmd);
//		}
//		if(StringUtils.isNotBlank(dir)){
//			setWorkDir(dir);
//		}
//		setEnabled(enabled);
//	}
//	
//	protected void saveToGlobalsProperties(){
//		if(command != null){
//			AppsGlobals.setSetupProperty(PROP_PREFIX + name + ".command", getCommand());
//		}
//		if(directory != null){
//			AppsGlobals.setSetupProperty(PROP_PREFIX + name + ".workDir", getDirectory().getAbsolutePath());
//		}
//		AppsGlobals.setSetupProperty(PROP_PREFIX + name + ".enabled", String.valueOf(isEnabled()));
//	}


}
