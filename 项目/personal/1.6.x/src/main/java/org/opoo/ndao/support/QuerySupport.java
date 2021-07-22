/*
 * $Id: QuerySupport.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao.support;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;

/**
 * 查询支持类。
 * @author Alex Lin
 * @version 1.0
 */
public interface QuerySupport {
	/**
	 * 执行更新。
	 * @param baseSql
	 * @param c
	 * @return
	 */
    int executeUpdate(String baseSql, Criterion c);
    /**
     * 查询结果集合。
     * @param baseSql 基本查询语句（SQL，HQL等）
     * @param resultFilter 结果过滤器
     * @return 结果集合
     */
	<T> List<T> find(String baseSql, ResultFilter resultFilter);
    /**
     * 查询分页结果集合。
     * @param baseSelectSql 查询数据记录的查询语句（SQL，HQL等）
     * @param baseCountSql 查询数据记录数量的查询语句（SQL，HQL等）
     * @param resultFilter 结果过滤器
     * @return 分页结果集合
     */
	<T> PageableList<T> find(String baseSelectSql, String baseCountSql, ResultFilter resultFilter);
    
    /**
     * 查询数据记录数量。
     * 
     * @param baseSql 查询语句（SQL，HQL等）
     * @param c 查询条件
     * @return 记录数量
     */
    int getInt(String baseSql, Criterion c);
}
