package org.opoo.apps.bean.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.opoo.apps.bean.LongKeyBean;
import org.opoo.apps.file.Details;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 附件类（文件）。
 * 
 * 主要应用于文件上传下载。
 * 
 * @author Alex
 *
 */
public class Attachment extends LongKeyBean implements Details/*, ConvertableObject*/{
	private static final long serialVersionUID = 1980791333237071750L;
	private String fileName;
	private String contentType;
	private String fileType;
	private int fetchCount = 0;
	private String storeName;
	private Date storeTime;
	private String location;
	private String readableFormat;
	private String protectedFormat;
	private long fileSize = 0L;
	private long userId = -1L;

	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getStoreTime() {
		return storeTime;
	}
	
	@JSON(format="yyyy-MM-dd")
	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}
	public int getFetchCount() {
		return fetchCount;
	}
	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	
	public String getOriginalFormat() {
		return getFileType();
	}
	

	public void writeAttachment(OutputStream os){
		
	}
	/**
	 * 用read-而不用set/get，避免被序列化。
	 * @return
	 */
	public InputStream readInputStream(){
		return null;
	}
	/**
	 * 
	 * @param format
	 * @return
	 */
	public InputStream readInputStream(String format){
		return null;
	}
	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
//	public int getObjectType() {
//		// TODO Defination object type here
//		return 9999;
//	}
}
