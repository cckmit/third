package org.opoo.apps.service;

import java.io.Serializable;
import java.util.List;

import org.opoo.apps.QueryParameter;
import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Criterion;
/**
 * 查询参数解析器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface QueryParametersResolver {
	
	Criterion resolve(List<QueryParameter> parameters, Queryable queryable);
	/**
	 * 
	 * @param parameters 参数列表
	 * @param dao 查询使用的dao
	 * @return
	 */
	Criterion resolve(List<QueryParameter> parameters, Dao<?,?> dao);
	/**
	 * 
	 * @param string string类型的数据
	 * @param targetClass 转换成所需数据类型
	 * @return
	 */
	Serializable convert(String string, Class<?> targetClass);
}
