/*
 * $Id: EntityObjectToWorkDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.EntityObjectToWork;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface EntityObjectToWorkDao extends Dao<EntityObjectToWork, Long> {
	
	/**
	 * 根据对象信息查询Work的sn集合。
	 * @param objectId 对象ID
	 * @param objectType 对象类型
	 * @return work的sn集合
	 */
	List<Long> findWorkSNs(long objectId, int objectType);
	
	/**
	 * 根据条件查询Work集合，涉及对象Work(w)和 EntityObjectWork(e)两个对象。
	 * 
	 * @param filter 过滤条件
	 * @return work集合
	 */
	List<Work> findWorks(ResultFilter filter);
	
	/**
	 * 根据对象信息查询Task的sn集合。
	 * 
	 * @param objectId 对象ID
	 * @param objectType 对象类型
	 * @return task的sn集合
	 */
	List<Long> findTaskSNs(long objectId, int objectType);
	
	/**
	 * 根据条件查询Task集合，涉及对象Task(t), Work(w)和 EntityObjectToWork(e)三个对象。
	 * 
	 * @param filter 过滤条件
	 * @return task集合
	 */
	List<Task> findTasks(ResultFilter filter);
}
