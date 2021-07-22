/*
 * $Id: SelectDataSourceService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.bean.SelectResult;


/**
 * ѡ��������Դ���÷����ࡣ
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SelectDataSourceService {
	
	/**
	 * ��ѯָ��ѡ��������Դ���õ�ѡ�������ݽ�����ϡ�
	 * 
	 * @param dataSource ѡ��������Դ����
	 * @param filter ������ѯ��������Ϊ��
	 * @return ѡ�������ݽ������
	 */
	List<?> findSelectData(SelectDataSource dataSource, ResultFilter filter);
	
	/**
	 * ��ѯָ��id��ѡ���������ݽ����
	 * @param selectId ѡ����ID
	 * @return
	 */
	SelectResult findSelectResult(String selectId);
	
	/**
	 * ����������ѯָ��id��ѡ���������ݽ����
	 * 
	 * @param selectId ѡ����ID
	 * @param filter ��ѯ����
	 * @return
	 */
	SelectResult findSelectResult(String selectId, ResultFilter filter);
}
