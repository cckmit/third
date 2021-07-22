package org.opoo.ndao.support;

import java.util.List;
import java.util.Map;


/**
 * 简单查询支持类。
 * 
 * 只要增加了一个泛型的方法支持。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SimpleQuerySupport extends QuerySupport {

	/**
	 * 执行更新。
	 * @param queryString 查询语句（SQL,HQL等）
	 * @param args 参数集合
	 * @return 被更新的对象数量
	 */
	int executeUpdate(String queryString, Object... args);
	/**
	 * 指定更新。
	 * @param queryString 查询语句（SQL,HQL等）
	 * @param args 命名参数集合
	 * @return 被更新的对象数量
	 */
	int executeUpdate(String queryString, Map<String, Object> args);
	
	/**
	 * 执行查询。
	 * @param queryString 查询语句（SQL,HQL等）
	 * @param args 参数集合
	 * @return 查询结果集合
	 */
	<T> List<T> find(String queryString, Object... args);
	
	/**
	 * 执行查询。
	 * @param queryString 查询语句（SQL,HQL等）
	 * @param args 命名参数结合
	 * @return 查询结果集合
	 */
	<T> List<T> find(String queryString, Map<String, Object> args);
}
