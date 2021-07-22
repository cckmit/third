/*
 * $Id: ResultFilterBuilder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

/**
 * �����������װ����
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ResultFilterBuilder {
	/**
	 * ������ѯ������
	 * @param parameters ԭʼ����
	 * @return
	 */
	ResultFilter buildResultFilter(Map<?,?> parameters);
}
