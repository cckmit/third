package org.opoo.apps.query;

import org.opoo.apps.exception.QueryNotFoundException;


public interface QueryManager {
	
	/**
	 * ע��һ����ѯ�����key�ظ����򸲸�֮ǰע��ġ�
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
