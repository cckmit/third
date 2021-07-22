/*
 * $Id: SelectResult.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 选择器数据结果。包括一个List类型的数据和一个选择器数据源配置对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SelectResult implements Serializable{
	private static final long serialVersionUID = -2481043393298255886L;
	
	private List<?> selectDataList;
	private SelectDataSource selectDataSource;
	
	public SelectResult() {
		super();
	}
	public SelectResult(List<?> selectDataList, SelectDataSource selectDataSource) {
		this.selectDataList = selectDataList;
		this.selectDataSource = selectDataSource;
	}
	public List<?> getSelectDataList() {
		return selectDataList;
	}
	public void setSelectDataList(List<?> selectDataList) {
		this.selectDataList = selectDataList;
	}
	public SelectDataSource getSelectDataSource() {
		return selectDataSource;
	}
	public void setSelectDataSource(SelectDataSource selectDataSource) {
		this.selectDataSource = selectDataSource;
	}
}
