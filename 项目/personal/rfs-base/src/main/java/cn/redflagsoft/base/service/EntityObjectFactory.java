/*
 * $Id: EntityObjectFactory.java 5951 2012-08-02 06:22:41Z lcj $
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

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * �ض����͵�ʵ���������
 * @author Alex Lin(alex@opoo.org)
 */
public interface EntityObjectFactory<T extends RFSEntityObject> {
	
	/**
	 * ֧�ֵ�ʵ�����͡�
	 * @return
	 */
	int getObjectType();
	
	/**
	 * ����ָ��ID��ʵ�塣
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	T loadObject(long id) throws NotFoundException;
	
	/**
	 * ����ָ��ID���ϵ�ʵ�弯�ϡ�
	 * @param objectIds
	 * @return
	 * @throws NotFoundException
	 */
	List<T> loadObjects(List<Long> objectIds) throws NotFoundException;
	
	List<T> loadObjects(ResultFilter filter);
}
