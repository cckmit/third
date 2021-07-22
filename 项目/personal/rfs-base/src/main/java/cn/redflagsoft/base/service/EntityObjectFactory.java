/*
 * $Id: EntityObjectFactory.java 5951 2012-08-02 06:22:41Z lcj $
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

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * 特定类型的实体加载器。
 * @author Alex Lin(alex@opoo.org)
 */
public interface EntityObjectFactory<T extends RFSEntityObject> {
	
	/**
	 * 支持的实体类型。
	 * @return
	 */
	int getObjectType();
	
	/**
	 * 加载指定ID的实体。
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	T loadObject(long id) throws NotFoundException;
	
	/**
	 * 加载指定ID集合的实体集合。
	 * @param objectIds
	 * @return
	 * @throws NotFoundException
	 */
	List<T> loadObjects(List<Long> objectIds) throws NotFoundException;
	
	List<T> loadObjects(ResultFilter filter);
}
