/*
 * $Id: DatumCategoryDao.java 3996 2010-10-18 06:56:46Z lcj $
 * DatuCategoryDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.DatumCategory;

/**
 * @author Administrator
 *
 */
public interface DatumCategoryDao extends Dao<DatumCategory, Long>,LabelDataBeanDao{
	
	DatumCategory getDatumCategory(String name);
	
}
