package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

/**
 * 数据访问者。
 * 
 * 定义了对特定对象的“读取”方法。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @version 1.5.2
 * @since 1.5.2
 *
 * @param <T>
 * @param <K>
 */
public interface DataAccessor<T extends Domain<K>, K extends Serializable> {
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
