package org.opoo.apps.file;

import static org.apache.commons.io.FilenameUtils.getExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.opoo.apps.file.converter.FileTypeConverterManager;
import org.opoo.apps.file.converter.FileTypeConvertibleAware;
import org.springframework.util.Assert;

public abstract class AbstractFileStorage implements FileStorage, FileTypeConvertibleAware {
	/**
	 * 默认的文件后缀。
	 */
	public static final String DEFAULT_FILE_EXTENSION_NAME = "file";
	//private static final SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
	private FileTypeConverterManager fileTypeConverterManager;
	private String storeName;
	private String protectedFormat;
	private String readableFormat;
	
	
	public AbstractFileStorage(String storeName){
		this.storeName = storeName;
	}
	public AbstractFileStorage(String storeName, FileTypeConverterManager fileTypeConverterManager){
		this.storeName = storeName;
		this.fileTypeConverterManager = fileTypeConverterManager;
	}
	
	public Details store(InputStream is, String fileName, String contentType, Long id) throws FileStorageException {
		return store(is, fileName, contentType, id, null, null);
	}

	public Details store(File tmpFile, String fileName, String contentType, Long id)  throws FileStorageException{
		return store(tmpFile, fileName, contentType, id, null, null) ;
	}

	public Details store(File tmpFile, String fileName, String contentType, Long id,
						 String protectedFormat, String readableFormat) throws FileStorageException {
		try {
			return store(new FileInputStream(tmpFile), fileName, contentType, id, protectedFormat, readableFormat) ;
		} catch (FileNotFoundException e) {
			throw new FileStorageException("文件存储失败", e);
		}
	}

	public Details store(InputStream is, String fileName, String contentType, Long id,
						 String protectedFormat, String readableFormat) throws FileStorageException {
		Assert.notNull(is, "InputStream is required.");
		Assert.hasText(fileName, "FileName is required.");
		Assert.hasText(contentType, "ContentType is required.");
		Assert.notNull(id, "File id is required.");

		String ext = getExtension(fileName);

		if(ext.length() == 0){
			ext = DEFAULT_FILE_EXTENSION_NAME;
		}

		FileDetails details = new FileDetails();
		details.setOriginalFormat(ext);
		details.setStoreName(storeName);
		details.setProtectedFormat(protectedFormat);
		details.setReadableFormat(readableFormat);

		//文件系统的时候
		//details.setLocation(sdf.format(new Date()) + id);

		return save(is, details, id);
	}

	/**
	 * 
	 * @param is
	 * @param details
	 * @return
	 */
	protected abstract FileDetails save(InputStream is, FileDetails details, Long id) throws FileStorageException;

	
	/**
	 * 
	 */
	public InputStream fetch(Details details) throws FileStorageException {
		return fetch(details, details.getOriginalFormat());
	}

	/**
	 * @return the fileTypeConverterManager
	 */
	public FileTypeConverterManager getFileTypeConverterManager() {
		return fileTypeConverterManager;
	}

	/**
	 * @param fileTypeConverterManager the fileTypeConverterManager to set
	 */
	public void setFileTypeConverterManager(
			FileTypeConverterManager fileTypeConverterManager) {
		this.fileTypeConverterManager = fileTypeConverterManager;
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @return the protectedFormat
	 */
	public String getProtectedFormat() {
		return protectedFormat;
	}
	/**
	 * @param protectedFormat the protectedFormat to set
	 */
	public void setProtectedFormat(String protectedFormat) {
		this.protectedFormat = protectedFormat;
	}
	/**
	 * @return the readableFormat
	 */
	public String getReadableFormat() {
		return readableFormat;
	}
	/**
	 * @param readableFormat the readableFormat to set
	 */
	public void setReadableFormat(String readableFormat) {
		this.readableFormat = readableFormat;
	}
}
