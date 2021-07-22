package org.opoo.apps.file.converter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.file.converter.openoffice.OpenOfficeFileTypeConverter;
import org.springframework.util.StopWatch;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

/**
 * 使用JODConverter来转换文件类型。
 * 使用jod需要提供openoffice(Solaris 10的starsuite也可以）服务，服务最好与web应用在同一台机器上，
 * 如果oo服务在另外一台服务器上，需要使用StreamOpenOfficeDocumentConverter。
 * See：http://sourceforge.net/forum/message.php?msg_id=4401175
 * <pre>
 * 
 * RE: Error: URL seems to be an unsupported one
 *	
 *	You need to use StreamOpenOfficeDocumentConverter but OpenOfficeDocumentConverter. 
 *	 
 *	http://www.artofsolving.com/node/10 
 *	 
 *	But there's a bug in OOo 2.2.x that can cause conversions not to work anyway. So at this point I would say running OOo on a different machine is not recommended. Better adopt the "web service" approach 
 *	 
 *	http://www.artofsolving.com/node/15 
 *
 *
 * </pre>
 * 
 * 
 * 注意：目前已知在Sun Solaris 10 SPARC + Sun Java System Application Server 8.1_02/8.2等
 * 应用环境下，虽然启动了OpenOffice/StarOffice服务（端口可访问），但仍然无法通过这个转化器来
 * 转换文件，此时需要使用基于命令行的转换器。
 * 
 * @see CommandLineJODConverter
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated using JODOpenOfficeFileTypeConverter
 */
public class JODConverter extends AbstractFileTypeConverter implements FileTypeConverter {
	private static final Log log = LogFactory.getLog(JODConverter.class);
	
	
	/**
	 * 
	 * 
	 *
	 */
	private static interface ConverterCallback{
		void convert(DocumentConverter converter);
	}
	
//	/**
//	 * 
//	 * 
//	 *
//	 */
//	private static class SupportedFormatPair{
//		private List<String> fromFormats;
//		private List<String> toFormats;
//		public SupportedFormatPair(List<String> fromFormats, List<String> toFormats){
//			this.fromFormats = fromFormats;
//			this.toFormats = toFormats;
//		}
//		public SupportedFormatPair(String[] fromFormats, String[] toFormats){
//			this.fromFormats = Arrays.asList(fromFormats);
//			this.toFormats = Arrays.asList(toFormats);
//		}
//		
//		public boolean supports(String fromFormat, String toFormat){
//			return fromFormats.contains(fromFormat) && toFormats.contains(toFormat);
//		}
//	}
//	
//	public static final List<SupportedFormatPair> SUPPORTED_FORMATS = new ArrayList<SupportedFormatPair>();
//	static{
//		SUPPORTED_FORMATS.add(new SupportedFormatPair(
//				new String[]{"odt", "sxw", "rtf", "doc", "wpd", "txt", "html"},
//				new String[]{"pdf", "odt", "sxw", "rtf", "doc", "txt", "html", "wiki"}
//		));
//		SUPPORTED_FORMATS.add(new SupportedFormatPair(
//				new String[]{"ods", "sxc", "xls", "csv", "tsv"},
//				new String[]{"pdf", "ods", "sxc", "xls", "csv", "tsv", "html"}
//		));
//		SUPPORTED_FORMATS.add(new SupportedFormatPair(
//				new String[]{"odp", "sxi", "ppt"},
//				new String[]{"pdf", "swf", "odp", "sxi", "ppt", "html"}
//		));
//		SUPPORTED_FORMATS.add(new SupportedFormatPair(
//				new String[]{"odg"},
//				new String[]{"svg", "swf"}
//		));
//	}
//	
	
	private String host = SocketOpenOfficeConnection.DEFAULT_HOST;
	private int port = SocketOpenOfficeConnection.DEFAULT_PORT;
	private DocumentFormatRegistry documentFormatRegistry;
	
	public JODConverter(){
		this.documentFormatRegistry = new DefaultDocumentFormatRegistry();
		setSupportedFileTypes(CommandLineJODConverter.SUPPORTED_TYPES);
	}
	
	
	protected DocumentFormatRegistry getDocumentFormatRegistry() {
		return documentFormatRegistry;
	}
	
	
	/**
	 * 注意：目前已知在Sun Solaris 10 SPARC + Sun Java System Application Server 8.1_02/8.2等
	 * 应用环境下，虽然启动了OpenOffice/StarOffice服务（端口可访问），但仍然无法通过这个转化器来
	 * 转换文件，此时需要使用基于命令行的转换器。其原因可能是应用服务器的安全策略限制。
	 * 
	 * @see CommandLineJODConverter
	 * 
	 * @param callback
	 * @throws FileTypeConvertException
	 */
	protected void execute(ConverterCallback callback) throws FileTypeConvertException{
		//File inputFile = new File("document.doc");
		//File outputFile = new File("document.pdf");
		// connect to an OpenOffice.org instance running on port 8100
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, port);
		try{
			connection.connect();
			// convert
			//StreamOpenOfficeDocumentConverter ,OpenOfficeDocumentConverter
			//当openoffice在另外一台机器上时，要使用StreamOpenOfficeDocumentConverter,
			//
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);//new StreamOpenOfficeDocumentConverter(connection);
			callback.convert(converter);
		} catch (Exception e) {
			throw new FileTypeConvertException("文件类型转换失败", e);
		}finally{
			// close the connection
			try{
				connection.disconnect();
			}catch(Exception e){
				log.warn("忽略JODConverter关闭连接错误", e);
			}
		}
	}

	public void convert(final File input, String inputFormat, final File output, String outputFormat) throws FileTypeConvertException {
		final DocumentFormat inputf = guessDocumentFormat(inputFormat);
		final DocumentFormat outputf = guessDocumentFormat(outputFormat);
		try{
			execute(new ConverterCallback(){
				public void convert(DocumentConverter converter) {
					converter.convert(input, inputf, output, outputf);
				}
			});
		}catch(Exception e){
			throw new FileTypeConvertException(inputFormat, outputFormat, e);
		}
	}
	
	

	

	public void convert(final File input, final File output) throws FileTypeConvertException {
		execute(new ConverterCallback(){
			public void convert(DocumentConverter converter) {
				converter.convert(input, output);
			}}
		);
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.FileTypeConverter#convert(java.io.InputStream, java.lang.String, java.io.OutputStream, java.lang.String)
	 */
	public void convert(final InputStream is, String inputFormat, final OutputStream os, String outputFormat) throws FileTypeConvertException {
		final DocumentFormat input = guessDocumentFormat(inputFormat);
		final DocumentFormat output = guessDocumentFormat(outputFormat);
		
		try{
			execute(new ConverterCallback(){
				public void convert(DocumentConverter converter) {
					converter.convert(is, input, os, output);
				}}
			);
		}catch(Exception e){
			throw new FileTypeConvertException(inputFormat, outputFormat, e);
		}
	}
	
	private DocumentFormat guessDocumentFormat(String extension) {
		DocumentFormat format = getDocumentFormatRegistry().getFormatByFileExtension(extension);
		if (format == null) {
			throw new IllegalArgumentException("未知的文件类型: " + extension);
		}
		return format;
	}

//	public boolean supports(String inputFormat, String outputFormat) {
//		if(StringUtils.isBlank(inputFormat) || StringUtils.isBlank(outputFormat)){
//			return false;
//		}
//		
//		inputFormat = inputFormat.toLowerCase().trim();
//		outputFormat = outputFormat.toLowerCase().trim();
//		for(SupportedFormatPair pair: SUPPORTED_FORMATS){
//			if(pair.supports(inputFormat, outputFormat)){
//				return true;
//			}
//		}
//		return false;
//	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	
	public static void main(String[] args){
		StopWatch stop = new StopWatch();
		stop.start("new");
		//JODConverter c = new JODConverter();
		
		OpenOfficeFileTypeConverter c = new OpenOfficeFileTypeConverter();
		//solaris 10 starsuite
		//c.setHost("192.168.18.10");
		//c.setPort(8100);
		
		stop.stop();
		stop.start("support");
		boolean supports = c.supports("doc", "swf");
		stop.stop();
		System.out.println(supports);
		//System.out.println(stop.prettyPrint());
		
		stop.start("support");
		supports = c.supports("xls", "pdf");
		stop.stop();
		System.out.println(supports);
		//System.out.println(stop.prettyPrint());
		
		
		//c.convert(new FileInputStream("/d:/VMware.Service.txt"), "txt", new FileOutputStream("d:/VMware.Serviceee.doc"), "doc");
		//c.convert(new File("d:\\VMware.Service.txt"), new File("d:\\VMware.Service.pdf"));
	
		stop.start("convert");
		//c.convert(new File("d:\\project-output2.xls"), new File("d:\\project-output2.pdf"));
		c.convert(new File("D:\\xxx.doc"), new File("d:\\ssss.pdf"));
	
	
		stop.stop();
		System.out.println(stop.prettyPrint());
		System.out.println(Integer.MAX_VALUE);
		//long i = Long.MAX_VALUE;
	}
}
