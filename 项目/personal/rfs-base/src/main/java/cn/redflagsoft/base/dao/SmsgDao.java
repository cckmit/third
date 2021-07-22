/*
 * $Id: SmsgDao.java 5420 2012-03-09 00:49:14Z lcj $
 * RiskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.Map;

import cn.redflagsoft.base.bean.Smsg;


/**
 * @author Administrator
 *
 */
public interface SmsgDao extends ObjectDao<Smsg> {
	
//	/**
//	 * 信息阅读
//	 * @param clerk
//	 * @param smsgId
//	 * @return
//	 */
//	List<Object> smsgRead(Clerk clerk,Long smsgId);
//	
//	
//	/***
//	 * 信息阅读(接收人==当前用户)
//	 * @param clerk
//	 * @param smsgId
//	 * @return
//	 */
//	Smsg smsgReadForClerk(Clerk clerk,Long smsgId);
	
	/**
	 * 获取指定用户收到的指定类型的消息数量，并且可以根据阅读状态进行过滤。
	 * 
	 * 消息的状态必须是已经发送的。
	 * 
	 * @param userId 用户ID
	 * @param msgKind 消息类型
	 * @param readStatus 阅读状态：已读、未读，取其它值时，忽略这个条件。
	 * @return 消息数量
	 */
	int getUserSmsgCount(long userId, byte msgKind, byte readStatus);
	
	/**
	 * 获取指定用户收到的指定类型消息的数量，按阅读状态进行分组。
	 * 
	 * @param userId
	 * @param msgKind
	 * @return key 阅读状态，value 消息数量
	 */
	Map<Byte, Integer> getUserSmsgCount(long userId, byte msgKind);
}
