/*
 * $Id: SmsgReceiverDao.java 4872 2011-10-09 07:44:41Z lf $
 * RiskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SmsgReceiver;


/**
 * @author py
 *
 */
public interface SmsgReceiverDao extends Dao<SmsgReceiver,Long> {
	
	/***
	 * 信息阅读(接收人==当前用户)
	 * @param clerk
	 * @param smsgId
	 * @return
	 */
	SmsgReceiver smsgReadForClerk(Clerk clerk,Long smsgId);
}
