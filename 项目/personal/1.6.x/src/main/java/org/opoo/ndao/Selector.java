package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;


/**
 * 通用查询器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5.1
 * @version $Id: Selector.java 13 2010-11-26 05:04:02Z alex $
 */
public interface Selector {
	
	/**
	 * 根据ID查询指定类型的对象。
	 * 
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
	 * @param id 对象的id
	 * @return 查找出的对象，如果没有查询到返回null。
	 */
	<T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, K id);
	
	/**
	 * 查询指定类型的对象集合。
	 * 
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz);
	
	/**
	 * 根据条件查询指定类型的对象集合。
	 * 
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
	 * @param filter 过滤器
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz, ResultFilter filter);

	/**
	 * 根据条件查询指定类型的对象的ID集合。
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
	 * @param idPropertyName id属性的名称，通常是“id”
	 * @param filter 过滤器
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<K> findIds(Class<T> clazz, String idPropertyName, ResultFilter filter);
    
	/**
	 * 查询指定类型的对象的总数。
	 * 
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz);
    
    /**
     * 根据过滤条件查询对象数量。
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
     * @param filter 过滤器
     * @return
     */
	<T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz, ResultFilter filter);
    
    /**
     * 根据过滤条件查询分页的对象集合。
     * 
	 * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
     * @param filter 过滤条件，必须包含分页参数。
     * @return
     */
	<T extends Domain<K>, K extends Serializable> PageableList<T> findPageableList(Class<T> clazz, ResultFilter filter);
    
    /**
     * 根据查询条件获取单个指定类型的对象。
     * 
     * 如果查询结果不唯一，将出现异常。
     * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
     * @param criterion 查询条件
     * @return
     */
	<T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, Criterion criterion);
    
    
    /**
     * 分组查询。查询结果数据结构为Map。
     * 
     * @param <T> 对象类型
	 * @param <K> 对象的主键类型
	 * @param clazz 对象类
     * @param filter 过滤条件
     * @param aggregation 分组信息
     * @return
     */
	<T extends Domain<K>, K extends Serializable> List<Map<String, Object>> find(Class<T> clazz, ResultFilter filter, Aggregation aggregation);
}
