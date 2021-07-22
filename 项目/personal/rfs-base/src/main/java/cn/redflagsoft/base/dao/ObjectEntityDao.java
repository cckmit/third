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
	 * ���ݵ�λid��ѯ����id����
	 * @param entityId ��λid
	 * @param type ��ϵ����
	 * @return
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type);

	/**
	 * ���ݵ�λid��ѯ����id����
	 * @param entityId ��λid
	 * @param type ��ϵ����
	 * @param objectType ��������
	 * @return
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type, int objectType);
	
	
	//List<IdAndObjectId> findIdAndObjectIdByEntityId(Long entityId, int type, int objectType);
	
	/**
	 * ɾ����ϵ��
	 * 
	 * @param entityId
	 * @param type
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	int remove(Long entityId, int type, int objectType, Long objectId);
	
	/**
	 * ���ݶ���ID��ѯ��λid���ϡ�
	 * @param objectId ����id
	 * @param type ��ϵ����
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
