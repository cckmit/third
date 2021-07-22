package org.opoo.apps.service;

import java.io.Serializable;
import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.ndao.support.PageableList;

public interface QueryService {
	
	/**
	 * 查询条件中的无效的数值，在组装查询条件时，会被自动剔除掉。
	 */
	int INVALID_NUMBER = -123;
	
	/**
	 * 查询条件中无效的字符串，在组装查询条件时，会被自动剔除掉。
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
	 * @param expectedResultType 期望返回的结果类型，必须是list或者pageableList
	 * @return
	 * @throws Exception
	 */
	//List<?> query(String target, String methodName, QueryParameters parameters, Class<?> expectedResultType) throws Exception;
	

	/**
	 * @param <L> List或者PageableList及其子类
	 * @param target
	 * @param methodName
	 * @param parameters
	 * @param expectedResultType 期望返回的结果类型，必须是list或者pageableList
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
	 * 执行任意bean的任意方法。
	 * 
	 * @param beanName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	Object invoke(String target, String methodName, QueryParameters parameters) throws QueryException;
	
	

}
