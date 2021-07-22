package org.opoo.apps.file.converter.openoffice;


import static org.opoo.apps.file.util.FileUtils.streamToString;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.file.converter.CommandLineFileTypeConverter;
import org.opoo.apps.file.converter.FileTypeConvertException;


/**
 * 
 * 当JODOpenOfficeFileTypeConverter无法正常工作时，使用命令行的运行方式。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OpenOfficeCommandLineFileTypeConverter extends CommandLineFileTypeConverter {
	private static final Log log = LogFactory.getLog(OpenOfficeCommandLineFileTypeConverter.class);
	
	
	public OpenOfficeCommandLineFileTypeConverter() {
		super();
		//默认命令行
		setCommand("java -jar jodconverter-cli-2.2.2.jar ");
		setSupportedFileTypes(AbstractOpenOfficeFileTypeConverter.ALL_SUPPORTED_TYPES);
	}

	@Override
	protected void convertInternal(String inputFileName, String outputFileName)	throws FileTypeConvertException {
		String cmd = getCommand() + " " + inputFileName + " " + outputFileName;
		log.info("执行命令：" + cmd);
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, getDirectory());
				
			
			String msg = streamToString(process.getInputStream());
			if(msg.length() > 0){
				log.info(msg);
			}
			
			String error = streamToString(process.getErrorStream());
//			if(error.length() > 0){
//				log.error(error);
//				//注意，命令行的正确信息输出在这里
//				throw new FileTypeConvertException(error);
//			}
			
			File file = new File(outputFileName);
			if(!file.exists()){
				throw new FileTypeConvertException(error);
			}else{
				log.info(error);
			}
			
		} catch (IOException e) {
			throw new FileTypeConvertException(e);
		}
	}
	
	public static void main(String[] args){
		OpenOfficeCommandLineFileTypeConverter c = new OpenOfficeCommandLineFileTypeConverter();
		c.setDirectory(new File("D:\\My Documents\\Downloads\\jodconverter-2.2.2\\lib"));
		c.convert(new File("d:\\xxx.doc"), new File("d:\\培训提纲.pdf"));
	}
}
