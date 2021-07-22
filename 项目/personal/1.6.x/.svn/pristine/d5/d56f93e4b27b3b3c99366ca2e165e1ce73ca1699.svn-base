package org.opoo.apps.file.converter;

import static org.opoo.apps.file.util.FileUtils.streamToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 在JODConverter无法正常使用时，可以使用此基于命令行的转化器。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class CommandLineJODConverter extends CommandLineFileTypeConverter {
	private static final Log log = LogFactory.getLog(CommandLineJODConverter.class);
	
	public static final List<FileTypesPair> SUPPORTED_TYPES;
	static{
		List<FileTypesPair> list = new ArrayList<FileTypesPair>();
		list.add(new FileTypesPair(
				new String[]{"odt", "sxw", "rtf", "doc", "wpd", "txt", "html"},
				new String[]{"pdf", "odt", "sxw", "rtf", "doc", "txt", "html", "wiki"}
		));
		list.add(new FileTypesPair(
				new String[]{"ods", "sxc", "xls", "csv", "tsv"},
				new String[]{"pdf", "ods", "sxc", "xls", "csv", "tsv", "html"}
		));
		list.add(new FileTypesPair(
				new String[]{"odp", "sxi", "ppt"},
				new String[]{"pdf", "swf", "odp", "sxi", "ppt", "html"}
		));
		list.add(new FileTypesPair(
				new String[]{"odg"},
				new String[]{"svg", "swf"}
		));
		SUPPORTED_TYPES = Collections.unmodifiableList(list);
	}
	
	public CommandLineJODConverter(){
		super();
		//默认命令行
		setCommand("java -jar jodconverter-cli-2.2.2.jar ");
		setSupportedFileTypes(SUPPORTED_TYPES);
	}
	

	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.CommandLineFileTypeConverter#convertInternal(java.lang.String, java.lang.String)
	 */
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
			
			if(error.length() > 0){
				log.error(error);
				//注意，命令行的正确信息输出在这里
				//throw new FileTypeConvertException(error);
			}
		} catch (IOException e) {
			throw new FileTypeConvertException(e);
		}
	}
	
	
	public static void main(String[] args){
		CommandLineJODConverter c = new CommandLineJODConverter();
		c.setDirectory(new File("D:\\My Documents\\Downloads\\jodconverter-2.2.2\\lib"));
		c.convert(new File("d:\\培训提纲.doc"), new File("d:\\培训提纲.pdf"));

	}

}
