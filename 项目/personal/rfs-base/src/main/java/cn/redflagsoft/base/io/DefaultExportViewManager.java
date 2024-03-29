/*
 * $Id: DefaultExportViewManager.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.io;

import org.springframework.util.Assert;

import cn.redflagsoft.base.io.jxls.JXLSExportView;

/**
 * @author Alex Lin
 *
 */
public class DefaultExportViewManager implements ExportViewManager {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.io.ExportViewManager#getExportView(java.lang.String)
	 */
	public ExportView getExportView(String config) {
		Assert.notNull(config, "导出配置不能为空。");
		
		//暂时只支持excel的jxls形式
		if(config.toLowerCase().endsWith(".xls")){//excel
			return createExcelExportView(config);
		}
		
		throw new IllegalArgumentException("无法创建指定配置的导出处理器：" + config);
	}
	
	private ExcelExportView createExcelExportView(String config){
		JXLSExportView exportView = new JXLSExportView();
		exportView.setTemplate(config);
		return exportView;
	}
}
