/*
 * $Id: ObjectEntityDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.ObjectEntity;

/**
 * @author mwx
 *
 */
public interface ObjectEntityDao extends Dao<ObjectEntity,Long>{
	
	List<ObjectEntity> findByObjectIdAndType(Long objectID, int objectType);
	
	/**
	 * 根据单位id查询对象id集合
	 * @param entityId 单位id
	 * @param type 关系类型
	 * @return
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type);

	/**
	 * 根据单位id查询对象id集合
	 * @param entityId 单位id
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @return
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type, int objectType);
	
	
	//List<IdAndObjectId> findIdAndObjectIdByEntityId(Long entityId, int type, int objectType);
	
	/**
	 * 删除关系。
	 * 
	 * @param entityId
	 * @param type
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	int remove(Long entityId, int type, int objectType, Long objectId);
	
	/**
	 * 根据对象ID查询单位id集合。
	 * @param objectId 对象id
	 * @param type 关系类型
	 * @return
	 */
	List<Long> findEntityIdsByObjectId(Long objectId, int type);
	
	
	public static class IdAndObjectId{
		private Long id;
		private Long objectId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getObjectId() {
			return objectId;
		}
		public void setObjectId(Long objectId) {
			this.objectId = objectId;
		}
	}
}
