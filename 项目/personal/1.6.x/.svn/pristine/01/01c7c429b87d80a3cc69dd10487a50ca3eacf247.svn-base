package org.opoo.apps.file.openoffice;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

public class XDocumentConverterWrapper implements XDocumentConverter, XComponentRefresherAware {
	private final DocumentConverter converter;
	//private final OpenOfficeTemplate template;
	private final OpenOfficeConnection connection;
	private DocumentFormatRegistry documentFormatRegistry;
	
	private static final Log log = LogFactory.getLog(XDocumentConverterWrapper.class);
	
	public XDocumentConverterWrapper(DocumentConverter converter, OpenOfficeConnection connection) {
		super();
		this.converter = converter;
		this.connection = connection;
		//this.template = new OpenOfficeTemplate(connection);
		this.documentFormatRegistry = new DefaultDocumentFormatRegistry();
	}

	private void execute(final OpenOfficeConnectionCallback action){
//		try {
//			template.execute(new OpenOfficeConnectionCallback(){
//				public void doInConnection(OpenOfficeConnection connection) {
//					action.doInConnection(connection);
//				}
//			});
//		} catch (ConnectException e) {
//			throw new RuntimeException(e);
//		}
		
		try{
			connection.connect();
			action.doInConnection(connection);
		} catch (ConnectException e) {
			throw new RuntimeException(e);
		}finally{
			try{
				connection.disconnect();
			}catch(Exception e){
				log.warn("忽略关闭OpenOffice连接错误: " + e.getMessage());
			}
		}
	}
		
	public void convert(final File inputDocument, final  File outputDocument) {
		execute(new OpenOfficeConnectionCallback(){
			public void doInConnection(OpenOfficeConnection connection) {
				converter.convert(inputDocument, outputDocument);
			}
		});
	}

	public void convert(final File inputDocument, final File outputDocument, final DocumentFormat outputFormat) {
		execute(new OpenOfficeConnectionCallback(){
			public void doInConnection(OpenOfficeConnection connection) {
				converter.convert(inputDocument, outputDocument, outputFormat);
			}
		});
	}

	public void convert(final InputStream inputStream, final DocumentFormat inputFormat,
			final OutputStream outputStream, final DocumentFormat outputFormat) {
		execute(new OpenOfficeConnectionCallback(){
			public void doInConnection(OpenOfficeConnection connection) {
				converter.convert(inputStream, inputFormat, outputStream, outputFormat);
			}
		});
	}

	public void convert(final File inputFile, final DocumentFormat inputFormat,
			final File outputFile, final DocumentFormat outputFormat) {
		execute(new OpenOfficeConnectionCallback(){
			public void doInConnection(OpenOfficeConnection connection) {
				converter.convert(inputFile, inputFormat, outputFile, outputFormat);
			}
		});
	}


	public void convert(InputStream inputStream, String inputFormat, OutputStream outputStream, String outputFormat) {
		DocumentFormat inputf = guessDocumentFormat(inputFormat);
		DocumentFormat outputf = guessDocumentFormat(outputFormat);
		convert(inputStream, inputf, outputStream, outputf);
	}

	public void convert(File inputFile, String inputFormat, File outputFile, String outputFormat) {
		DocumentFormat inputf = guessDocumentFormat(inputFormat);
		DocumentFormat outputf = guessDocumentFormat(outputFormat);
		convert(inputFile, inputf, outputFile, outputf);
	}

	public void convert(File inputDocument, File outputDocument, String outputFormat) {
		DocumentFormat outputf = guessDocumentFormat(outputFormat);
		convert(inputDocument, outputDocument, outputf);
	}

	/**
	 * @return the documentFormatRegistry
	 */
	public DocumentFormatRegistry getDocumentFormatRegistry() {
		return documentFormatRegistry;
	}

	/**
	 * @param documentFormatRegistry the documentFormatRegistry to set
	 */
	public void setDocumentFormatRegistry(
			DocumentFormatRegistry documentFormatRegistry) {
		this.documentFormatRegistry = documentFormatRegistry;
	}
	
	private DocumentFormat guessDocumentFormat(String extension) {
		DocumentFormat format = getDocumentFormatRegistry().getFormatByFileExtension(extension);
		if (format == null) {
			throw new IllegalArgumentException("未知的文件类型: " + extension);
		}
		return format;
	}
	
	public XComponentRefresher getComponentRefresher() {
		if(converter instanceof XComponentRefresherAware)
			return ((XComponentRefresherAware) converter).getComponentRefresher();
		return null;
	}

	public void setComponentRefresher(XComponentRefresher componentRefresher) {
		if(converter instanceof XComponentRefresherAware)
			 ((XComponentRefresherAware) converter).setComponentRefresher(componentRefresher);
	}
}
