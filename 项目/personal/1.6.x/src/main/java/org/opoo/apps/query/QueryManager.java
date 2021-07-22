package org.opoo.apps.query;

import org.opoo.apps.exception.QueryNotFoundException;


public interface QueryManager {
	
	/**
	 * 注册一个查询。如果key重复，则覆盖之前注册的。
	 * 
	 * @param key
	 * @param query
	 */
	void registerQuery(String key, Query<?> query);

	/**
	 * 
	 * @param key
	 * @return
	 */
	Query<?> getQuery(String key) throws QueryNotFoundException;
}
