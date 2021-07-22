/*
 * $Id: WorkDefinitionDao.java 4615 2011-08-21 07:10:37Z lcj $
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

import cn.redflagsoft.base.bean.WorkDefinition;



/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface WorkDefinitionDao extends Dao<WorkDefinition, Integer> {

	/**
	 * ��WorkDefinition��IDΪ��������Map��ʽ�����ݡ�
	 * 
	 * @param filter
	 * @return
	 */
	Map<Integer, WorkDefinition> findWorkDefinitionsMap(ResultFilter filter);
	
	
	/**
	 * ��WorkDefinition��IDΪ����NameΪֵ������Map��ʽ�����ݡ�
	 * @param filter
	 * @return
	 */
	Map<Integer, String> findSimpleWorkDefinitionsMap(ResultFilter filter);
}
