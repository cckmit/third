package org.opoo.apps.query;

import org.opoo.apps.QueryParameters;
import org.opoo.ndao.support.ResultFilter;

/**
 * ��ѯ��������������
 * 
 * @author lcj
 *
 */
public interface ResultFilterBuilder {
	
	/**
	 * ������ѯ��������
	 * @param bean
	 * @param parameters
	 * @return
	 */
	ResultFilter buildResultFilter(Object bean, QueryParameters parameters);
}
