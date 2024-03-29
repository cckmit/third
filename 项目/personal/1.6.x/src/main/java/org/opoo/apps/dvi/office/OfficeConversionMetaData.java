package org.opoo.apps.dvi.office;

import org.opoo.apps.dvi.ConversionMetaData;

public interface OfficeConversionMetaData extends ConversionMetaData{
	
	/**
	 * 获取文档页数
	 * @return 页数
	 * @see #getNumberOfParts()
	 */
	int getNumberOfPages();

	/**
	 * 设置文档的页数
	 * @param numberOfPages 页数
	 * @see #setNumberOfParts(int)
	 */
	void setNumberOfPages(int numberOfPages);
	
}
