/*
 * $Id: ObjectByIDFinder.java 5277 2011-12-26 09:07:11Z lcj $
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

/**
 * @author Alex Lin(lcql@msn.com)
 * @deprecated using {@link ObjectFinder}
 */
public interface ObjectByIDFinder<T, K> extends ObjectCountQuery {
	/**
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	T getObject(K id);
	
	/**
	 * 
	 * @param rf
	 * @return
	 */
	List<K> findObjectIds(ResultFilter rf);
}
