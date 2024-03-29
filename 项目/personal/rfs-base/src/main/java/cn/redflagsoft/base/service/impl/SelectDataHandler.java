/*
 * $Id: SelectDataHandler.java 4741 2011-09-27 07:04:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SelectDataSource;


/**
 * 选择数据处理器。
 * 
 * @author lcj
 *
 * @param <T> 选择数据集每个单项的数据类型。
 */
public interface SelectDataHandler<T> {
	/**
	 * 判断当前选择处理器是否支持处理特定的配置。
	 * 
	 * @param dataSource
	 * @return
	 */
	boolean supports(SelectDataSource dataSource);
//	/**
//	 * 查询指定选择器数据源配置对应的数据结果集合。
//	 * 
//	 * @param <E> 集合中每项的数据类型
//	 * @param dataSource 选择器数据源配置
//	 * @return 
//	 */
//	<E> List<E> findSelectData(SelectDataSource dataSource);
	
	/**
	 * 查询指定选择器数据源配置对应的数据结果集合。
	 * @param <E> 集合中每项的数据类型
	 * @param dataSource 选择器数据源配置
	 * @param filter 查询条件
	 * @return
	 */
	List<T> findSelectData(SelectDataSource dataSource, ResultFilter filter);
	
	
//	/**
//	 * 适配器
//	 *
//	 */
//	public abstract class SelectDataHandlerAdapter implements SelectDataHandler{
//		public <E> List<E> findSelectData(SelectDataSource dataSource){
//			return findSelectData(dataSource, null);
//		}
//	}
}
