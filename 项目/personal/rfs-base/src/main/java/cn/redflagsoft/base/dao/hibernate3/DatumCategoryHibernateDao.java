/*
 * $Id: DatumCategoryHibernateDao.java 3996 2010-10-18 06:56:46Z lcj $
 * DatumCategoryHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.dao.DatumCategoryDao;

/**
 * @author Administrator
 *
 */
public class DatumCategoryHibernateDao extends AbstractBaseHibernateDao<DatumCategory, Long> implements DatumCategoryDao{

	@SuppressWarnings("unchecked")
	public DatumCategory getDatumCategory(String name) {
		List<DatumCategory> list = getHibernateTemplate().find("from Datum_Category where name=?", name);
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
