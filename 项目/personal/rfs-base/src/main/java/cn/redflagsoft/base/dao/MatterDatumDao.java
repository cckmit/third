/*
 * $Id: MatterDatumDao.java 4677 2011-09-14 01:29:38Z lcj $
 * BizDatumDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithMatterDatum;
import cn.redflagsoft.base.bean.MatterDatum;

/**
 * @author Administrator
 *
 */
public interface MatterDatumDao extends Dao<MatterDatum, Long> {
	
	
	/**
	 * 根据条件查询DatumCategory和MatterDatum组合对象的集合。
	 * 
	 * 其中对象DatumCategory别名为 a，MatterDatum的别名为 b。
	 * @param filter
	 * @return
	 */
	List<DatumCategoryWithMatterDatum> findDatumCategoryWithMatterDatum(ResultFilter filter);

	/**
	 * 根据task类型，work类型，process类型和matterId查询配置的资料。
	 * 
	 * @param taskType task类型
	 * @param workType work类型
	 * @param processType process类型
	 * @param matterID
	 * @return
	 */
	List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID);

	/**
	 * 根据task类型，work类型，process类型、matterId和matterdatum的类型查询配置的资料。
	 * @param taskType task类型
	 * @param workType work类型
	 * @param processType process类型
	 * @param matterID
	 * @param matterDatumType matterdatum类型
	 * @return
	 */
	List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType);
	
	/**
	 * 查询MatterDatum。
	 * 
	 * @param category
	 * @param taksType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @return
	 */
	List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID);

	/**
	 * 查询MatterDatum。
	 * 
	 * @param category
	 * @param taksType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param datumType
	 * @return
	 */
	List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID,
			Long datumType);
}
