package org.opoo.apps.file;

public interface Details {

	String PROTECTED_FORMAT_NONE = "none";
    String READABLE_FORMAT_NONE = "none";

	/**
	 * 文件所在的存储器。
	 * 
	 * @return the storeName
	 */
	String getStoreName();
	
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
	String getLocation();

	/**
	 * 文件的原始格式，及文件的原始后缀。
	 * 不能为空。
	 * @return the originalFormat
	 */
	String getOriginalFormat();

	/**
	 * 受保护的文件格式。如果文件本身是指定的受保护的格式（如PDF）或者在存储是将
	 * 文件转成了指定的受保护格式（如pdf),则将格式记录在这个属性中，否则属性值
	 * 为null。
	 * @return the protectedFormat
	 */
	String getProtectedFormat();
	/**
	 * 文件的阅读格式。如果文件本身是指定的阅读格式（如swf）或者在存储时将文件转
	 * 化成了指定的阅读格式（如swf），则将格式记录在这个属性中，否则属性值为null。
	 * @return the readableFormat
	 */
	String getReadableFormat();
	
	
	/**
	 * 文件大小。
	 * @return
	 */
	long getFileSize();
}
