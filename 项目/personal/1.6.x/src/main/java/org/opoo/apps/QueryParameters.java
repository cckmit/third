package org.opoo.apps;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Order;

/**
 * 查询参数集合。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface QueryParameters extends java.io.Serializable{
	/**
	 * 查询条件集合。
	 * 
	 * @return
	 */
	List<QueryParameter> getParameters();
	
	
	Map<String, ?> getRawParameters();

	//Serializable getId();
	
	//String getMethodName();
	
	/**
	 * 开始记录索引号。
	 */
	int getStart();
	/**
	 * 最大记录数。
	 * @return
	 */
	int getMaxResults();
	
	/**
	 * 获取排序对象。
	 * @return
	 */
	Order getOrder();
	
	/**
	 * 排序字段。
	 * @return
	 */
	String getSort();
	
	/**
	 * 排序方向。
	 * @return
	 */
	String getDir();
}
