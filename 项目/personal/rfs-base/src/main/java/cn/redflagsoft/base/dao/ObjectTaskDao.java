/*
 * $Id: ObjectTaskDao.java 3996 2010-10-18 06:56:46Z lcj $
 * ObjectTaskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.ObjectTask;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;

/**
 * @author mwx
 *
 */
public interface ObjectTaskDao extends Dao<ObjectTask,Long>{

	/**
	 * 
	 * @param objectID
	 * @return
	 */
	List<Task> findTasksByObjectID(Long objectID);
	
	/**
	 * 
	 * @param taskSN
	 * @return
	 */
	List<RFSObject> findObjectsByTaskSN(Long taskSN);
}
