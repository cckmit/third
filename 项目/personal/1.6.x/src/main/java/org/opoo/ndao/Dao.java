/*
 * Dao.java
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
package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;


/**
 * 新版的DAO处理
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.5.2
 */
public interface Dao<T extends Domain<K>, K extends Serializable> extends DataAccessor<T, K> {
    /**
     * 保存对象。
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T save(T entity);
    /**
     * 更新对象。
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T update(T entity);
    /**
     * 保存或者更新对象，根据有无ID进行判断，有则更新，无则保存。
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T saveOrUpdate(T entity);
    /**
     * 删除对象。
     * @param entity T
     * @return int
     * @throws DataAccessException
     */
    int delete(T entity);
    /**
     * 根据ID删除对象。
     * @param id K
     * @return int
     * @throws DataAccessException
     */
    int remove(K id);
    /**
     * 根据一组ID删除多个对象。
     * @param ids K[]
     * @return int
     * @throws DataAccessException
     */
    int remove(K[] ids);
    /**
     * 根据条件删除对象。
     *
     * @param criterion Criterion
     * @return int
     * @throws DataAccessException
     */
    int remove(Criterion criterion);
    /**
     * 根据ID查询一个对象。
     *
     * @param id K
     * @return T
     * @throws DataAccessException
     */
    T get(K id);

    /**
     * 查询所有对象。
     * @return List
     * @throws DataAccessException
     */
    List<T> find();
    /**
     * 根据过滤条件查询数据集合。
     * @param resultFilter 结果过滤器
     * @return
     */
    List<T> find(ResultFilter resultFilter);
    
    /**
     * 查询ID集合。
     * @param resultFilter 结果过滤器，可以为<code>null</code>
     * @return id集合
     */
    List<K> findIds(ResultFilter resultFilter);
    
    /**
     * 获取总对象数。
     * @return
     */
    int getCount();
    
    /**
     * 根据过滤条件查询对象数量。
     * @param resultFilter 结果过滤器
     * @return
     */
    int getCount(ResultFilter resultFilter);
    
    /**
     * 根据过滤条件查询分页的对象集合。
     * 
     * @param resultFilter 过滤条件，必须包含分页参数。
     * @return
     */
    PageableList<T> findPageableList(ResultFilter resultFilter);
    
    /**
     * 根据查询条件获取单个对象。
     * 
     * 如果查询结果不唯一，将出现异常。
     * @param criterion
     * @return
     */
    T get(Criterion criterion);
    
    
    /**
     * 分组查询。
     * 查询结果数据结构为Map。
     * @param resultFilter
     * @param groupInfo
     * @return
     */
    List<Map<String, Object>> find(ResultFilter resultFilter, Aggregation aggregation);
}
