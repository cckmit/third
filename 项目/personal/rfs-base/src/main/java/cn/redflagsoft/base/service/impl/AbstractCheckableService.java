/*
 * $Id: AbstractCheckableService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.Checkable;
import cn.redflagsoft.base.service.CheckableService;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractCheckableService<K extends Serializable> implements CheckableService<K> {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.CheckableService#findCheckableList(java.util.Map, java.util.List)
	 */
	public List<Checkable<K>> findCheckableList(Map<?, ?> parameters, List<K> checkedObjectIds) {
		List<Checkable<K>> list = findCheckableList(parameters);
		return buildCheckableList(list, checkedObjectIds);
	}
	
	/**
	 * 根据参数查询未设置选择状态的对象集合。
	 * 
	 * @param parameters 参数
	 * @return 未设置选择状态的对象集合
	 */
	protected abstract List<Checkable<K>> findCheckableList(Map<?,?> parameters);
	
	/**
	 * 根据应设置选择状态的ID集合来设置集合中相应对象的选择状态，并返回设置后的集合。
	 * 
	 * @param <K> 对象ID类型
	 * @param list 未设置选择状态的对象集合
	 * @param checkedObjectIds 应该设置选择状态为true的对象ID的集合
	 * @return 已根据ID集合设置选择状态的对象集合
	 */
	public static <K extends Serializable> List<Checkable<K>> buildCheckableList(List<Checkable<K>> list, List<K> checkedObjectIds){
		for(Checkable<K> obj: list){
			if(checkedObjectIds.contains(obj.getId())){
				obj.setChecked(true);
			}
		}
		return list;
	}
}
