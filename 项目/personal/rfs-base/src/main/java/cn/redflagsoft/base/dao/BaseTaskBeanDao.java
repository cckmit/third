/*
 * $Id: BaseTaskBeanDao.java 6392 2014-04-17 09:48:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
