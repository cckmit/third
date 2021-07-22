package org.opoo.apps.dao;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;



/**
 * 通用查询。
 *
 */
public interface QueryDao{
	
	/**
	 * 根据实体名称和过滤条件查询数据。
	 * 
	 * @param entityName String 实体名称
	 * @param rf ResultFilter 过滤条件
	 * @return List
	 */
	<T> List<T> findByEntityName(String entityName, ResultFilter rf);
	
	/**
	 * 根据实体名称和过滤条件分页查询数据。
	 * 
	 * @param entityName String 实体名称
	 * @param rf ResultFilter 过滤条件
	 * @return PageableList
	 */
	<T> PageableList<T> findPageableListByEntityName(String entityName, ResultFilter rf);
	
	
	/**
	 * 根据实体名和过滤条件查询实体数量。
	 * 
	 * @param entityName
	 * @param rf
	 * @return
	 */
	int getCountByEntityName(String entityName, Criterion c);
}
