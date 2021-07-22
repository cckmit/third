package org.opoo.apps.query;

import org.opoo.apps.QueryParameters;


/**
 * ������������
 * 
 * @author lcj
 *
 */
public interface ParametersBuilder extends ResultFilterBuilder{

	/**
	 * ����Ŀ�������
	 * @param bean
	 * @param parameters
	 * @param parameterNames �������Ƽ���
	 * @param parameterTypes �������ͼ��ϣ����ȱ�������Ƽ���һ��
	 * @return
	 */
	Object[] buildParameters(Object bean, QueryParameters parameters, String[] parameterNames, Class<?>[] parameterTypes);
}
