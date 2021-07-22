/*
 * $Id: ObjectEntityService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.vo.BatchUpdateResult;

/**
 * 业务对象与单位之间的关系。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ObjectEntityService {
	
	/**
	 * 为指定ID的单位批量设置对象。
	 * 如果对象ID集合为空，则清空指定单位指定类型的关联关系。
	 * 
	 * @param entityId 单位ID
	 * @param objectIds 对象ID集合
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @return  批量设置结果
	 */
	BatchUpdateResult setObjectsToEntity(Long entityId, List<Long> objectIds, int type, int objectType);
	
	/**
	 * 根据单位ID查找对象ID集合。
	 * 
	 * @param entityId 单位ID
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @return 对象ID集合
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type, int objectType);
}
