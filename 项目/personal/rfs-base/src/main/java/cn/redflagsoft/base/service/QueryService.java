/*
 * $Id: QueryService.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.apps.QueryParameters;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SelectResult;


/**
 * @author Alex Lin
 *
 */
public interface QueryService extends org.opoo.apps.service.QueryService{
	
	/**
	 * 
	 * @param queryId
	 * @param resultFilter
	 * @return
	 * @deprecated
	 */
	List<?> find(String queryId, ResultFilter resultFilter);
	
	/**
	 * 
	 * @param queryId
	 * @param resultFilter
	 * @return
	 * @deprecated
	 */
	PageableList<?> findPageableList(String queryId, ResultFilter resultFilter);
	
	
	/**
	 * ��ѯѡ�������ݽ����
	 * 
	 * @param selectId ѡ������ʶ
	 * @param parameters ��ѯ����
	 * @return ���ݽ��
	 */
	SelectResult select(String selectId, QueryParameters parameters);
}
