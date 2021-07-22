package org.opoo.apps.file.converter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface FileTypeConverter {
	/**
	 * 判断是否支持文件格式转换。
	 * 
	 * @param inputFormat
	 * @param outputFormat
	 * @return
	 */
	boolean supports(String inputFormat, String outputFormat);
	/**
	 * 执行格式转换。
	 * 
	 * @param input 输入文件
	 * @param output 输出文件
	 */
	void convert(File input, File output) throws FileTypeConvertException;
	/**
	 * 执行格式转换。
	 * @param is 输入文件流
	 * @param os 输出文件流
	 */
	void convert(InputStream is, String inputFormat, OutputStream os, String outputFormat) throws FileTypeConvertException;
	
	/**
	 * 
	 * @param input
	 * @param inputFormat
	 * @param output
	 * @param outputFormat
	 * @throws Exception
	 */
	void convert(File input, String inputFormat, File output, String outputFormat) throws FileTypeConvertException;
}
