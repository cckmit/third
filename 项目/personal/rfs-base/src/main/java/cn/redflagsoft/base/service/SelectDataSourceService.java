/*
 * $Id: SelectDataSourceService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.bean.SelectResult;


/**
 * 选择器数据源配置服务类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SelectDataSourceService {
	
	/**
	 * 查询指定选择器数据源配置的选择器数据结果集合。
	 * 
	 * @param dataSource 选择器数据源配置
	 * @param filter 其他查询条件，可为空
	 * @return 选择器数据结果集合
	 */
	List<?> findSelectData(SelectDataSource dataSource, ResultFilter filter);
	
	/**
	 * 查询指定id的选择器的数据结果。
	 * @param selectId 选择器ID
	 * @return
	 */
	SelectResult findSelectResult(String selectId);
	
	/**
	 * 根据条件查询指定id的选择器的数据结果。
	 * 
	 * @param selectId 选择器ID
	 * @param filter 查询条件
	 * @return
	 */
	SelectResult findSelectResult(String selectId, ResultFilter filter);
}
