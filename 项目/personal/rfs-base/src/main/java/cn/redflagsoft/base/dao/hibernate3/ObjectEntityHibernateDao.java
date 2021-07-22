/*
 * $Id: ObjectEntityHibernateDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.apps.id.IdGeneratorProvider;

import cn.redflagsoft.base.bean.ObjectEntity;
import cn.redflagsoft.base.dao.ObjectEntityDao;

/**
 * @author mwx
 * @author Alex Lin
 */
public class ObjectEntityHibernateDao extends AbstractBaseHibernateDao<ObjectEntity,Long> implements ObjectEntityDao{
	@Override
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
		//super.setIdGeneratorProvider(idGeneratorProvider);
		setIdGenerator(idGeneratorProvider.getIdGenerator("objectEntity"));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ObjectEntity> findByObjectIdAndType(Long objectID, int objectType) {
		String hql = "select a from ObjectEntity a where a.objectID=? and a.objectType=? and a.type=?";
		return getHibernateTemplate().find(hql, new Object[] { objectID, objectType, ObjectEntity.KEY_PROJECT_TYPE });
	}


	@SuppressWarnings("unchecked")
	public List<Long> findObjectIdsByEntityId(Long entityId, int type) {
		String hql = "select a.objectID from ObjectEntity a where a.entityID=? and a.type=?";
		return getHibernateTemplate().find(hql, new Object[] { entityId, type});
	}


	@SuppressWarnings("unchecked")
	public List<Long> findObjectIdsByEntityId(Long entityId, int type, int objectType) {
		String hql = "select a.objectID from ObjectEntity a where a.entityID=? and a.type=? and a.objectType=?";
		return getHibernateTemplate().find(hql, new Object[] { entityId, type, objectType});
	}


	public int remove(Long entityId, int type, int objectType, Long objectId) {
		String hql = "delete from ObjectEntity a where a.entityID=? and a.type=? and a.objectType=? and a.objectID=?";
		return getQuerySupport().executeUpdate(hql, new Object[]{entityId, type, objectType, objectId});
	}


	@SuppressWarnings("unchecked")
	public List<Long> findEntityIdsByObjectId(Long objectId, int type) {
		String hql = "select a.entityID from ObjectEntity a where a.objectID=? and a.type=?";
		return getHibernateTemplate().find(hql, new Object[]{objectId, type});
	}
}
