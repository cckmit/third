/*
 * $Id: DatumDao.java 4615 2011-08-21 07:10:37Z lcj $
 * DatumDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.DatumAttachment;

/**
 * @author Administrator
 *
 */
public interface DatumDao extends Dao<Datum,Long>{
	
	Datum getDatum(String name);

	List<DatumAttachment> findDatumAttachments(ResultFilter rf);
	
	List<DatumAttachment> findDatumAttachmentByType(Long objectId, int type);
	
	List<DatumAttachment> findDatumAttachmentByTypeAndCategoryID(Long objectId, int type, Long categoryID);

//	/**
//	 * @param id
//	 * @param taskType
//	 * @return
//	 */
//	List<Datum> findRFSObjectTaskDatum(long objectID, int taskType);
//
//	/**
//	 * @param id
//	 * @param objectType
//	 * @param taskType
//	 * @return
//	 */
//	List<Datum> findEntityObjectTaskDatum(long objectID, int objectType,	int taskType);
//
//	/**
//	 * @param id
//	 * @param workType
//	 * @return
//	 */
//	List<Datum> findRFSObjectWorkDatum(long objectID, int workType);
//
//	/**
//	 * @param id
//	 * @param objectType
//	 * @param workType
//	 * @return
//	 */
//	List<Datum> findEntityObjectWorkDatum(long objectID, int objectType,	int workType);

	/**
	 * @param id
	 * @param i
	 * @param taskType
	 * @return
	 */
	List<Datum> findTaskDatum(long objectID, int objectType, int taskType);

	/**
	 * @param id
	 * @param objectType
	 * @param workType
	 * @return
	 */
	List<Datum> findWorkDatum(long objectID, int objectType, int workType);

	//int removeDatumByTypeAndCategoryID(Long objectID, int type, Long categoryID);
}
