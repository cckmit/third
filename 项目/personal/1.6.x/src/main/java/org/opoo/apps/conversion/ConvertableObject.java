package org.opoo.apps.conversion;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 *
 */
public interface ConvertableObject {
	/**
	 * ��ת��������ļ�����
	 * @return
	 */
	String getFilename();
	
	/**
	 * ��ת��������ص����ݵ����͡�
	 * @return
	 */
	String getContentType();
	
	/**
	 * ��ȡ��������ÿ�ε���ʱӦ�ô����µ������󣬲��������BufferedInputStream��ʽ�ġ�
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
