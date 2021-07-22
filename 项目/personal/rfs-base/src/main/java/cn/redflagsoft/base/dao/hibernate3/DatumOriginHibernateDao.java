/*
 * $Id: DatumOriginHibernateDao.java 5971 2012-08-03 05:57:13Z lcj $
 * DatumCategoryHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.DatumOrigin;
import cn.redflagsoft.base.dao.DatumOriginDao;

/**
 * @author Administrator
 *
 */
public class DatumOriginHibernateDao extends AbstractBaseHibernateDao<DatumOrigin, Long> implements DatumOriginDao{

	@SuppressWarnings("unchecked")
	public DatumOrigin getDatumOrigin(String name) {
		List<DatumOrigin> list = getHibernateTemplate().find("from Datum_Origin where name=?", name);
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	protected String getDataProperty() {
		return "id";
	}

	@Override
	protected String getLabelProperty() {
		return "abbr";
	}

}
