package org.opoo.apps.file.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class SwftoolConverter implements FileTypeConverter {
	private static final Log log = LogFactory.getLog(SwftoolConverter.class);
	private String command = "pdf2swf";
	private File dir;
	

	public void convert(File input, File output) throws FileTypeConvertException {
		convertInternal(input.getAbsolutePath(), output.getAbsolutePath());
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.FileTypeConverter#convert(java.io.File, java.lang.String, java.io.File, java.lang.String)
	 */
	public void convert(File input, String inputFormat, File output, String outputFormat) throws FileTypeConvertException {
		if(supports(inputFormat, outputFormat)){
			convert(input, output);
		}else{
			throw new FileTypeConvertException("不支持转换：" + inputFormat + " -> " + outputFormat);
		}
	}
	public void convert(InputStream is, String inputFormat, OutputStream os, String outputFormat) throws FileTypeConvertException {
		File inputFile = null;
		File outputFile = null;
		try {
			inputFile = File.createTempFile("document", "." + inputFormat);
			OutputStream inputFileStream = null;
			try {
				inputFileStream = new FileOutputStream(inputFile);
				IOUtils.copy(is, inputFileStream);
			} finally {
				IOUtils.closeQuietly(inputFileStream);
			}
			
			outputFile = File.createTempFile("document", "." + outputFormat);
			convert(inputFile, outputFile);
			InputStream outputFileStream = null;
			try {
				outputFileStream = new FileInputStream(outputFile);
				IOUtils.copy(outputFileStream, os);
			} finally {
				IOUtils.closeQuietly(outputFileStream);
			}
		} catch (IOException ioException) {
			throw new FileTypeConvertException(inputFormat, outputFormat, ioException);
		} finally {
			if (inputFile != null) {
				inputFile.delete();
			}
			if (outputFile != null) {
				outputFile.delete();
			}
		}
	}


	public boolean supports(String inputFormat, String outputFormat) {
		return "pdf".equalsIgnoreCase(inputFormat) && "swf".equalsIgnoreCase(outputFormat);
	}
	
	
	protected void convertInternal(String inputFileName, String outputFileName) throws FileTypeConvertException{
		String cmd = command + " " + inputFileName + " " + outputFileName;
		//cmd = "cmd /C D:/Downloads/swftools-2008-11-19-1013/p2swf d:\\VMware.Service.pdf d:\\aaa.swf";
		log.info("执行命令：" + cmd);
		try {
			//Process process = Runtime.getRuntime().exec(command, null, dir);
			Process process = Runtime.getRuntime().exec(cmd, null, dir);
			String msg = streamToString(process.getInputStream());
			if(msg.length() > 0){
				log.info(msg);
			}
			String error = streamToString(process.getErrorStream());
			
			if(error.length() > 0){
				//log.error(error);
				throw new FileTypeConvertException(error);
			}
		} catch (IOException e) {
			throw new FileTypeConvertException(e);
		}
	}
	
	private static String streamToString(final InputStream is) throws IOException{
		StringBuffer result = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = in.readLine()) != null) {
			result.append(line).append("\n");
		}
		return result.toString();
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
	 * @return the dir
	 */
	public File getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(File dir) {
		this.dir = dir;
	}
	
	public void setWorkDir(String dir){
		if(StringUtils.isNotBlank(dir)){
			this.dir = new File(dir);
		}
	}
	
	public static void main2(String[] args) throws Exception{
//		String[] commands = new String[]{"cmd /C \"D:/Downloads/swftools-2008-11-19-1013/pdf2swf.exe -o __tmp__.swf d.pdf\"",
//		                     "D:/Downloads/swftools-2008-11-19-1013/swfcombine -o ddd.swf rfxview.swf swf=__tmp__.swf",
//		                     "del __tmp__.swf"}; 
//		commands = new String[]{"cmd /C", "D:/Downloads/swftools-2008-11-19-1013/p2swf springsecurity.pdf d:/aaa.swf"};
//		Process process = Runtime.getRuntime().exec("cmd /C D:/Downloads/swftools-2008-11-19-1013/p2swf d:\\VMware.Service.pdf  d:\\aaa.swf"
//				
//				, null, new File("D:\\Downloads\\swftools-2008-11-19-1013\\"));
//		//process.waitFor();
//		
//		
//		String msg = streamToString(process.getInputStream());
//		log.debug(msg);
//		//System.out.println("\\\\");
//		
//		String error = streamToString(process.getErrorStream());
//		log.error(error);

		
		//StringWriter w = new StringWriter();
		
		//System.out.println(process.getInputStream());
		//String s = new String(FileCopyUtils.copyToByteArray(process.getErrorStream()));
		//System.out.println(s);
		
		//System.out.println(String.format("aaa{0}", "AAA"));
		
		SwftoolConverter c = new SwftoolConverter();
		//c.command = "cmd /C D:/Downloads/swftools-2008-11-19-1013/p2swf";
		//c.dir = new File("D:\\Downloads\\swftools-2008-11-19-1013\\");
		//c.convertInternal("d:\\VMware.Service.pdf", "d:\\aasd.swf");
		c.setCommand("cmd /C D:/Downloads/swftools-2008-11-19-1013/p2swf");
		c.setDir(new File("D:\\Downloads\\swftools-2008-11-19-1013"));
		//c.convert(new File("d:\\VMware.Service.pdf"), new File("c:\\VMware.Service.swf"));
		c.convert(new FileInputStream("d:\\VMware.Service.pdf"), "pdf", new FileOutputStream("c:\\VMware.Service.swf"), "swf");
	
	
	
	
	}
	public static void main(String[] args) throws Exception{
//		//com.docverse.common.FileFormats.DocumentType.isOffice2007(arg0)
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
//		context.refresh();
//		DocVerseApplication.initialize(context);
//		System.out.println(context);
//	
//		String[] names = context.getBeanDefinitionNames();
//		for(String name:names){
//			System.out.println(name);
//		}
//		
//		String[] beanNamesForType = context.getBeanNamesForType(JobActivator.class);
//		System.out.println(beanNamesForType.length);
	}

}
