/*
 * $Id: ObjectDataHibernateDao.java 4615 2011-08-21 07:10:37Z lcj $
 * ObjectDateHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.ObjectData;
import cn.redflagsoft.base.dao.ObjectDataDao;

/**
 * @author mwx
 *
 */
public class ObjectDataHibernateDao extends AbstractBaseHibernateDao<ObjectData,Long> implements ObjectDataDao{
	
	@SuppressWarnings("unchecked")
	public List<ObjectData> findObjectDataByObjectId(Long objectId, Long datumId, Long datumCategoryId) {
		String hql = "select a from ObjectData a where a.objectID=? and a.datumID=? and a.datumCategoryID=?";
		return getHibernateTemplate().find(hql, new Object[]{objectId, datumId, datumCategoryId});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ObjectDataDao#findDatum(long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findDatum(long objectId, int objectType) {
		String qs = "select distinct d from Datum d, ObjectData od where d.id=od.datumID and od.objectID=? and od.objectType=? order by d.orderNo";
		return getHibernateTemplate().find(qs, new Object[]{objectId, objectType});
	}
}
