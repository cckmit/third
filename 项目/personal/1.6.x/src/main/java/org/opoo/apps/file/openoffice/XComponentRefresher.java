package org.opoo.apps.file.openoffice;

import com.sun.star.lang.XComponent;

/**
 * �ĵ�ˢ������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface XComponentRefresher{
	/**
	 * ˢ���ĵ���
	 * @param document �ĵ�
	 */
	void refresh(XComponent document);
}
