package org.opoo.apps.query;

import org.opoo.apps.QueryParameters;


/**
 * 参数构建器。
 * 
 * @author lcj
 *
 */
public interface ParametersBuilder extends ResultFilterBuilder{

	/**
	 * 构建目标参数。
	 * @param bean
	 * @param parameters
	 * @param parameterNames 参数名称集合
	 * @param parameterTypes 参数类型集合，长度必须和名称集合一致
	 * @return
	 */
	Object[] buildParameters(Object bean, QueryParameters parameters, String[] parameterNames, Class<?>[] parameterTypes);
}
