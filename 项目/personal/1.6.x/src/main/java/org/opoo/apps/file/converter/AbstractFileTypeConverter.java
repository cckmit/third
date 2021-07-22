package org.opoo.apps.file.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractFileTypeConverter implements FileTypeConverter, BeanNameAware, InitializingBean {
	public static final String PROP_PREFIX = "attachments.fileConverters.";
	
	private List<FileTypesPair> supportedFileTypes;
	private boolean enabled = true;
	private String name;
	
	
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

	public void convert(File input, String inputFormat, File output, String outputFormat) throws FileTypeConvertException {
		if(supports(inputFormat, outputFormat)){
			convert(input, output);
		}else{
			throw new FileTypeConvertException("不支持转换：" + inputFormat + " -> " + outputFormat);
		}
	}

	public boolean supports(String inputFormat, String outputFormat) {
		if(!enabled){
			return false;
		}
		
		if(StringUtils.isBlank(inputFormat) || StringUtils.isBlank(outputFormat)){
			return false;
		}
		
		inputFormat = inputFormat.toLowerCase().trim();
		outputFormat = outputFormat.toLowerCase().trim();
		for(FileTypesPair pair: getSupportedFileTypes()){
			if(pair.supports(inputFormat, outputFormat)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the supportedFileTypes
	 */
	public List<FileTypesPair> getSupportedFileTypes() {
		return supportedFileTypes;
	}

	/**
	 * @param supportedFileTypes the supportedFileTypes to set
	 */
	public void setSupportedFileTypes(List<FileTypesPair> supportedFileTypes) {
		this.supportedFileTypes = supportedFileTypes;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setBeanName(String name) {
		this.name = name;
	}
	
	public String getBeanName(){
		return name;
	}
	public void afterPropertiesSet() throws Exception {
		//saveToGlobalsProperties();
		boolean enabled = AppsGlobals.getSetupProperty(PROP_PREFIX + name + ".enabled", true);
		setEnabled(enabled);
	}
}
