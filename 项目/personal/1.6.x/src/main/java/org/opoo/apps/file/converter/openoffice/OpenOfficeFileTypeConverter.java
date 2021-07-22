package org.opoo.apps.file.converter.openoffice;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.opoo.apps.file.converter.FileTypeConvertException;
import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.openoffice.XConnectionProvider;
import org.opoo.apps.file.openoffice.XDocumentConverter;

/**
 * ʹ��OpenOffice(jodconverter)��ת���ļ����͡�
 * ʹ��jod��Ҫ�ṩopenoffice(Solaris 10��starsuiteҲ���ԣ����񣬷��������webӦ����ͬһ̨�����ϣ�
 * ���oo����������һ̨�������ϣ���Ҫʹ��StreamOpenOfficeDocumentConverter��
 * See��http://sourceforge.net/forum/message.php?msg_id=4401175
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
 * ע�⣺Ŀǰ��֪��Sun Solaris 10 SPARC + Sun Java System Application Server 8.1_02/8.2��
 * Ӧ�û����£���Ȼ������OpenOffice/StarOffice���񣨶˿ڿɷ��ʣ�������Ȼ�޷�ͨ�����ת������
 * ת���ļ�����ʱ��Ҫʹ�û��������е�ת������
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
			//��openoffice������һ̨������ʱ��Ҫʹ��StreamOpenOfficeDocumentConverter,
			//
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);//new StreamOpenOfficeDocumentConverter(connection);
			callback.convert(converter);
		} catch (Exception e) {
			throw new FileTypeConvertException("�ļ�����ת��ʧ��", e);
		}finally{
			// close the connection
			try{
				connection.disconnect();
			}catch(Exception e){
				log.warn("����JODConverter�ر����Ӵ���", e);
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
