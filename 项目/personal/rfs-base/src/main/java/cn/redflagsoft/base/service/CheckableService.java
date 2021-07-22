/*
 * $Id: CheckableService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.Checkable;

/**
 * 可选择对象的服务类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <K> 对象的主键类型
 */
public interface CheckableService<K extends Serializable> {
	/**
	 * 构建可选择对象的集合。
	 * @param parameters 查询条件
	 * @param checkedObjectIds 已选择的对象ID集合
	 * @return 可选择对象的集合
	 */
	List<Checkable<K>> findCheckableList(Map<?,?> parameters, List<K> checkedObjectIds);
}
