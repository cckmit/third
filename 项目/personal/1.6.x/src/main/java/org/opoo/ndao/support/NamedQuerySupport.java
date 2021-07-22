package org.opoo.ndao.support;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;

/**
 * 命名查询支持类。
 * 
 * @author Alex Lin
 * @version 2.0
 */
public interface NamedQuerySupport {
	/**
	 * 执行更新。
	 * @param baseSqlName 基本查询语句（SQL，HQL等）的名称
	 * @param c 查询条件
	 * @return
	 */
    int executeUpdate(String baseSqlName, Criterion c);
    /**
     * 查询结果集合。
     * @param baseSqlName 基本查询语句（SQL，HQL等）的名称
     * @param resultFilter 结果过滤器
     * @return 结果集合
     */
	<T> List<T> find(String baseSqlName, ResultFilter resultFilter);
    /**
     * 查询分页结果集合。
     * @param baseSelectSqlName 查询数据记录的查询语句（SQL，HQL等）的名称
     * @param baseCountSqlName 查询数据记录数量的查询语句（SQL，HQL等）的名称
     * @param resultFilter 结果过滤器
     * @return 分页结果集合
     */
	<T> PageableList<T> find(String baseSelectSqlName, String baseCountSqlName, ResultFilter resultFilter);
    
    /**
     * 查询数据记录数量。
     * 
     * @param baseSqlName 查询语句（SQL，HQL等）的名称
     * @param c 查询条件
     * @return 记录数量
     */
    int getInt(String baseSqlName, Criterion c);
}
