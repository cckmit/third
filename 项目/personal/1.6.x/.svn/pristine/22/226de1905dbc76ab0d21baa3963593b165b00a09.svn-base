package org.opoo.apps.conversion;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 *
 */
public interface ConvertableObject {
	/**
	 * 可转换对象的文件名。
	 * @return
	 */
	String getFilename();
	
	/**
	 * 可转换对象相关的内容的类型。
	 * @return
	 */
	String getContentType();
	
	/**
	 * 获取内容流，每次调用时应该创建新的流对象，并且最好是BufferedInputStream格式的。
	 * @return
	 * @throws IOException
	 */
	InputStream getStreamData() throws IOException;
	
	/**
	 * 
	 * @return
	 */
	long getContentLength();
	
	/**
	 * 
	 * @return
	 */
	boolean isAllowedToConvert();
	
	int getRevisionNumber();
	
	int getObjectType();
	
	long getObjectId();
}
