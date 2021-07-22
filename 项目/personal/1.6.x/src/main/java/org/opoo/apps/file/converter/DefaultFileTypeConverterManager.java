package org.opoo.apps.file.converter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultFileTypeConverterManager implements	FileTypeConverterManager {
	private static final Log log = LogFactory.getLog(DefaultFileTypeConverterManager.class);
	private List<FileTypeConverter> converters;
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.FileTypeConverterManager#convert(java.io.File, java.lang.String, java.io.File, java.lang.String)
	 */
	public boolean convert(File input, String inputFormat, File output,	String outputFormat) {
		FileTypeConverter converter = findConverter(inputFormat, outputFormat);
		if(converter != null){
			//log.debug("使用转换器：" + converter);
			try{
				if(log.isDebugEnabled()){
					log.debug(inputFormat + " -> " + outputFormat + " ：使用转化器 " + converter);
				}
				
				converter.convert(input, inputFormat, output, outputFormat);
				//log.debug("转换文件：" + inputFormat + " -> " + outputFormat);
				return true;
			}catch(Exception e){
				log.error("转换文件  " + inputFormat + " -> " + outputFormat + " 失败", e);
				//e.printStackTrace();
			}
		}else{
			log.warn("找不到适当的文件格式转换器：" + inputFormat + " -> " + outputFormat);
		}
		return false;
	}

	public boolean convert(File input, File output) {
		String inputFormat = FilenameUtils.getExtension(input.getName());
		String outputFormat = FilenameUtils.getExtension(output.getName());
		return convert(input, inputFormat, output, outputFormat);
	}

	public boolean convert(InputStream is, String inputFormat, OutputStream os,	String outputFormat) {
		FileTypeConverter converter = findConverter(inputFormat, outputFormat);
		if(converter != null){
			try{
				if(log.isDebugEnabled()){
					log.debug(inputFormat + " -> " + outputFormat + " ：使用转化器 " + converter);
				}
				converter.convert(is, inputFormat, os, outputFormat);
				return true;
			}catch(Exception e){
				log.error(e);
			}
		}else{
			log.warn("找不到适当的文件格式转换器：" + inputFormat + " - >" + outputFormat);
		}
		return false;
	}
	
	
	protected FileTypeConverter findConverter(String inputFormat, String outputFormat){
		if(converters != null){
			for(FileTypeConverter converter: converters){
				if(converter.supports(inputFormat, outputFormat)){
					return converter;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 */
	public FileTypeConverter findFileTypeConverter(String inputFormat, String outputFormat){
		return findConverter(inputFormat, outputFormat);
	}

	/**
	 * @return the converters
	 */
	public List<FileTypeConverter> getConverters() {
		return converters;
	}

	/**
	 * @param converters the converters to set
	 */
	public void setConverters(List<FileTypeConverter> converters) {
		this.converters = converters;
	}

}
