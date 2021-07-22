/*
 * $Id: BaseTaskBeanDao.java 6392 2014-04-17 09:48:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.BaseTaskBean;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BaseTaskBeanDao extends Dao<BaseTaskBean, Long>{

	/**
	 * @param id
	 * @param statusRunning
	 */
	void updateStatus(Long id, byte status);
	
	/**
	 * 
	 * @param selectStatus
	 * @param updateStatus
	 * @return
	 */
	List<Long> updateStatusAndReturnIds(byte selectStatus, byte updateStatus);
}
