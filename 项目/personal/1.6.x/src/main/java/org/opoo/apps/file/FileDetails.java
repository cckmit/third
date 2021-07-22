package org.opoo.apps.file;


/**
 * 文件存储详情。
 * 当文件被存储在文件系统或数据库中之后，请储存的细节由这个类来表示。
 * 例如文件存储的路径，存储的格式，在数据库中的位置等等。
 * 
 * @author Alex Lin
 *
 */
public class FileDetails implements Details, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String storeName;
	private String location;
	private String originalFormat;
	private String readableFormat;
	private String protectedFormat;
	private long fileSize;
	
	/**
	 * 文件所在的存储器。
	 * 
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
	 * 文件在存储器中的位置。
	 * 
	 * 如果是存储在文件系统中，位置指文件保存路径和不带文件后缀的文件名，
	 * 如 /2008/11/24/12345678, 格式是/path-to-filesaved/filename-no-ext。
	 * 
	 * 如果在存储在数据库总，位置值文件所保存的数据表的名称和表中字段的名称，
	 * 如 /table_name/file_field。
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
	 * 文件的原始格式，及文件的原始后缀。
	 * 不能为空。
	 * @return the originalFormat
	 */
	public String getOriginalFormat() {
		return originalFormat;
	}
	/**
	 * @param originalFormat the originalFormat to set
	 */
	public void setOriginalFormat(String originalFormat) {
		this.originalFormat = originalFormat;
	}

	/**
	 * 受保护的文件格式。如果文件本身是指定的受保护的格式（如PDF）或者在存储是将
	 * 文件转成了指定的受保护格式（如pdf),则将格式记录在这个属性中，否则属性值
	 * 为null。
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
	 * 文件的阅读格式。如果文件本身是指定的阅读格式（如swf）或者在存储时将文件转
	 * 化成了指定的阅读格式（如swf），则将格式记录在这个属性中，否则属性值为null。
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
	 * 返回文件大小。
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * 设置文件大小。
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
