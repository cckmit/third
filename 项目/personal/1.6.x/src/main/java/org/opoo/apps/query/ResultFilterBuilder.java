package org.opoo.apps.query;

import org.opoo.apps.QueryParameters;
import org.opoo.ndao.support.ResultFilter;

/**
 * 查询过滤器构建器。
 * 
 * @author lcj
 *
 */
public interface ResultFilterBuilder {
	
	/**
	 * 构建查询过滤器。
	 * @param bean
	 * @param parameters
	 * @return
	 */
	ResultFilter buildResultFilter(Object bean, QueryParameters parameters);
}
