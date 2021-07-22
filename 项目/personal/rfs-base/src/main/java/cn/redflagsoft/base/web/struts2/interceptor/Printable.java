/*
 * $Id: Printable.java 5827 2012-06-01 10:11:22Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.web.struts2.interceptor;

import org.opoo.apps.ModelAware;

/**
 * @author Alex Lin
 *
 */
public interface Printable extends ModelAware {
	
	/**
	 * 
	 * @return
	 */
	String getPrintConfig();
	
	/**
	 * 
	 */
	//void setPrintConfig(String config);
	
	
	boolean isXlsExport();
	
	
	//void setXlsExport(boolean xlsExport);
	
	boolean isRequireRefreshExcelRowHeight();
}	
