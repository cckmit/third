package org.opoo.apps.service;

import java.io.Serializable;
import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.ndao.support.PageableList;

public interface QueryService {
	
	/**
	 * ��ѯ�����е���Ч����ֵ������װ��ѯ����ʱ���ᱻ�Զ��޳�����
	 */
	int INVALID_NUMBER = -123;
	
	/**
	 * ��ѯ��������Ч���ַ���������װ��ѯ����ʱ���ᱻ�Զ��޳�����
	 */
	String INVALID_STRING = String.valueOf(INVALID_NUMBER);
	
	/**
	 * 
	 * @param target
	 * @param id
	 * @return
	 */
	Serializable get(String target, String id) throws QueryException;
	
	/**
	 * 
	 * @param target
	 * @param methodName
	 * @param parameters
	 * @param expectedResultType �������صĽ�����ͣ�������list����pageableList
	 * @return
	 * @throws Exception
	 */
	//List<?> query(String target, String methodName, QueryParameters parameters, Class<?> expectedResultType) throws Exception;
	

	/**
	 * @param <L> List����PageableList��������
	 * @param target
	 * @param methodName
	 * @param parameters
	 * @param expectedResultType �������صĽ�����ͣ�������list����pageableList
	 * @return
	 * @throws Exception
	 */
	<L extends List<?>> L query(String target, String methodName, QueryParameters parameters, Class<L> expectedResultType) throws QueryException;
	
	/**
	 * 
	 * @param name
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	List<?> namedQueryList(String name, QueryParameters parameters) throws QueryException;
	
	/**
	 * 
	 * @param name
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	PageableList<?> namedQueryPageableList(String name, QueryParameters parameters) throws QueryException;
	
	
	/**
	 * 
	 * @param target
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	List<Labelable> queryLabelables(String target, QueryParameters parameters) throws QueryException;
	
	
	
	/**
	 * ִ������bean�����ⷽ����
	 * 
	 * @param beanName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	Object invoke(String target, String methodName, QueryParameters parameters) throws QueryException;
	
	

}
