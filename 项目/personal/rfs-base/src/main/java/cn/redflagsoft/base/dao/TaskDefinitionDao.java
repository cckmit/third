/*
 * $Id: TaskDefinitionDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.dao;

import java.util.Map;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.TaskDefinition;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskDefinitionDao extends Dao<TaskDefinition, Integer> {

	/**
	 * ��TaskDefinition��IDΪ��������Map��ʽ�����ݡ�
	 * 
	 * @param filter
	 * @return
	 */
	Map<Integer, TaskDefinition> findTaskDefinitionsMap(ResultFilter filter);
	
	
	/**
	 * ��TaskDefinition��IDΪ����NameΪֵ������Map��ʽ�����ݡ�
	 * @param filter
	 * @return
	 */
	Map<Integer, String> findSimpleTaskDefinitionsMap(ResultFilter filter);
}
