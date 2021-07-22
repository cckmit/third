package org.opoo.apps.file.openoffice;

import com.sun.star.lang.XComponent;

/**
 * 文档刷新器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface XComponentRefresher{
	/**
	 * 刷新文档。
	 * @param document 文档
	 */
	void refresh(XComponent document);
}
