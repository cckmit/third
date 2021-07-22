/*
 * $Id: TaskDataDao.java 4444 2011-06-30 03:18:49Z lcj $
 * TaskDataDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.TaskData;

/**
 * @author Administrator
 *
 */
public interface TaskDataDao extends Dao<TaskData,Long>{
	
	List<TaskData> findTaskDataByTaskSN(Long taskSN, Long datumId, Long datumCategoryId);

	/**
	 * @param taskSN
	 * @return
	 */
	List<Datum> findDatum(long taskSN);
}
