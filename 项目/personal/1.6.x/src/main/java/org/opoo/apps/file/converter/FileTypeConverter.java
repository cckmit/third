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
	 * �ж��Ƿ�֧���ļ���ʽת����
	 * 
	 * @param inputFormat
	 * @param outputFormat
	 * @return
	 */
	boolean supports(String inputFormat, String outputFormat);
	/**
	 * ִ�и�ʽת����
	 * 
	 * @param input �����ļ�
	 * @param output ����ļ�
	 */
	void convert(File input, File output) throws FileTypeConvertException;
	/**
	 * ִ�и�ʽת����
	 * @param is �����ļ���
	 * @param os ����ļ���
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
