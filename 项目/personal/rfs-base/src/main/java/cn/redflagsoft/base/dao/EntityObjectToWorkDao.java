/*
 * $Id: EntityObjectToWorkDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * ���ݶ�����Ϣ��ѯWork��sn���ϡ�
	 * @param objectId ����ID
	 * @param objectType ��������
	 * @return work��sn����
	 */
	List<Long> findWorkSNs(long objectId, int objectType);
	
	/**
	 * ����������ѯWork���ϣ��漰����Work(w)�� EntityObjectWork(e)��������
	 * 
	 * @param filter ��������
	 * @return work����
	 */
	List<Work> findWorks(ResultFilter filter);
	
	/**
	 * ���ݶ�����Ϣ��ѯTask��sn���ϡ�
	 * 
	 * @param objectId ����ID
	 * @param objectType ��������
	 * @return task��sn����
	 */
	List<Long> findTaskSNs(long objectId, int objectType);
	
	/**
	 * ����������ѯTask���ϣ��漰����Task(t), Work(w)�� EntityObjectToWork(e)��������
	 * 
	 * @param filter ��������
	 * @return task����
	 */
	List<Task> findTasks(ResultFilter filter);
}
