/*
 * $Id: TaskMatterDao.java 3996 2010-10-18 06:56:46Z lcj $
 * TaskMatterDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.TaskMatter;

/**
 * @author Administrator
 *
 */
public interface TaskMatterDao extends Dao<TaskMatter,Long>{
  List<TaskMatter> findTaskMatterByTaskSn(Long taskSN);
}
