/**
 * 
 */
package org.opoo.ndao.support;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;

/**
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5.2
 */
public abstract class ResultFilterUtils {

	/**
	 * 在过滤器中增加一个查询条件，并返回过滤器的复制对象（新实例）。
	 * @param filter 原始过滤器
	 * @param c 查询条件
	 * @return 新的过滤器，不为 null
	 */
	public static ResultFilter append(ResultFilter filter, Criterion c){
		filter = ResultFilter.createResultFilter(filter);
		return filter.append(c);
	}
	
	/**
	 * 在过滤器中增加一个排序条件，并返回过滤器的复制对象（新实例）。
	 * @param filter 原始过滤器
	 * @param o 排序条件
	 * @return 新的过滤器，不为 null
	 */
	public static ResultFilter append(ResultFilter filter, Order o){
		filter = ResultFilter.createResultFilter(filter);
		return filter.append(o);
	}
}
