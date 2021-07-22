package org.opoo.apps.dao;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;



/**
 * ͨ�ò�ѯ��
 *
 */
public interface QueryDao{
	
	/**
	 * ����ʵ�����ƺ͹���������ѯ���ݡ�
	 * 
	 * @param entityName String ʵ������
	 * @param rf ResultFilter ��������
	 * @return List
	 */
	<T> List<T> findByEntityName(String entityName, ResultFilter rf);
	
	/**
	 * ����ʵ�����ƺ͹���������ҳ��ѯ���ݡ�
	 * 
	 * @param entityName String ʵ������
	 * @param rf ResultFilter ��������
	 * @return PageableList
	 */
	<T> PageableList<T> findPageableListByEntityName(String entityName, ResultFilter rf);
	
	
	/**
	 * ����ʵ�����͹���������ѯʵ��������
	 * 
	 * @param entityName
	 * @param rf
	 * @return
	 */
	int getCountByEntityName(String entityName, Criterion c);
}
