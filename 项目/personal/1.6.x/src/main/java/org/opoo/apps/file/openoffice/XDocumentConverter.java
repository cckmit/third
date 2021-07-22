package org.opoo.apps.file.openoffice;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.artofsolving.jodconverter.DocumentConverter;


/**
 * 文档格式转换器。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface XDocumentConverter extends DocumentConverter {
	/**
	 * 文件格式转换。
	 * @param inputStream 文件输入流
	 * @param inputFormat 输入文件的格式
	 * @param outputStream 文件输出流
	 * @param outputFormat 输出文件的格式
	 */
	public void convert(InputStream inputStream, String inputFormat, OutputStream outputStream, String outputFormat);

    /**
     * 文件格式转换。
     * 
     * @param inputFile 输入文件
     * @param inputFormat 输入文件的格式
     * @param outputFile 输出文件
     * @param outputFormat 输出问价的格式
     */
    public void convert(File inputFile, String inputFormat, File outputFile, String outputFormat);


    /**
     * 文件格式转换。
     * 
     * @param inputDocument
     * @param outputDocument
     * @param outputFormat
     */
    public void convert(File inputDocument, File outputDocument, String outputFormat);
}
