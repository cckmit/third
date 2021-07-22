package org.opoo.apps.file.converter.openoffice;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.opoo.apps.file.converter.FileTypeConvertException;
import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.openoffice.XConnectionProvider;
import org.opoo.apps.file.openoffice.XDocumentConverter;

/**
 * 使用OpenOffice(jodconverter)来转换文件类型。
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
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OpenOfficeFileTypeConverter extends	AbstractOpenOfficeFileTypeConverter {
	
	public OpenOfficeFileTypeConverter() {
		super();
		setSupportedFileTypes(ALL_SUPPORTED_TYPES);
	}
	public OpenOfficeFileTypeConverter(XConnectionProvider connectionProvider){
		this();
		setConnectionProvider(connectionProvider);
	}

	
	public void convert(File input, String inputFormat, File output, String outputFormat) throws FileTypeConvertException {
		try {
			getDocumentConverter().convert(input, inputFormat, output, outputFormat);
		} catch (Exception e) {
			throw new FileTypeConvertException(inputFormat, outputFormat, e);
		}
	}
	
	public void convert(final File input, final File output) throws FileTypeConvertException {
		try {
			getDocumentConverter().convert(input, output);
		} catch (Exception e) {
			throw new FileTypeConvertException(e);
		}
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.FileTypeConverter#convert(java.io.InputStream, java.lang.String, java.io.OutputStream, java.lang.String)
	 */
	public void convert(InputStream is, String inputFormat, OutputStream os, String outputFormat) throws FileTypeConvertException {
		try{
			getDocumentConverter().convert(is, inputFormat, os, outputFormat);
		}catch(Exception e){
			throw new FileTypeConvertException(inputFormat, outputFormat, e);
		}
	}
	
	
	protected XDocumentConverter getDocumentConverter(){
		return getConnectionProvider().getConnection().getDocumentConverter();
	}
	
	
	/* *
	 * 
	 * @param action
	 * /
	private void execute(DocumentConverterCallback action){
		/*
		OpenOfficeConnection connection = getConnection();
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
		*   /
	}*/
	
	
	public static void main(String[] args){
		OpenOfficeFileTypeConverter c = new OpenOfficeFileTypeConverter();
		c.setConnectionProvider(new SocketXConnectionProvider("localhost"));
		//c.convert(new File("d:\\2.xls"), new File("d:\\2.pdf"));
		//c.convert(new File("d:\\xxx.doc"), new File("d:\\xxx0.pdf"));
		c.convert(new File("d:\\test.doc"), new File("d:\\test.doc2.pdf"));
	}
	
}
