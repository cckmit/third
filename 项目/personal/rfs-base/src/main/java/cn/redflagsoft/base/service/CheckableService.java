/*
 * $Id: CheckableService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.Checkable;

/**
 * ��ѡ�����ķ����ࡣ
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <K> �������������
 */
public interface CheckableService<K extends Serializable> {
	/**
	 * ������ѡ�����ļ��ϡ�
	 * @param parameters ��ѯ����
	 * @param checkedObjectIds ��ѡ��Ķ���ID����
	 * @return ��ѡ�����ļ���
	 */
	List<Checkable<K>> findCheckableList(Map<?,?> parameters, List<K> checkedObjectIds);
}
