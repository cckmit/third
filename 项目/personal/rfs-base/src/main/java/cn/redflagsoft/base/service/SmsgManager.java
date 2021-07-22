/*
 * $Id: SmsgManager.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;



/**
 * 消息管理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SmsgManager {

	/**
	 * 发送指定的消息。对于可以同步发送的消息（例如内部消息），
	 * 可以立刻返回发送后的消息对象，但对于异步消息（例如手机
	 * 短信），则只能反应出消息发送提交成功，异步消息发送器的
	 * 每个处理阶段通过消息机制通知消息管理器更新消息状态。
	 * 
	 * @param smsgId 消息的ID
	 * @return 发送或者提交的消息数量
	 */
	int sendMsg(long smsgId);	
	
	/**
	 * 立刻发送可以发送的所有消息。
	 * 
	 * @return 处理的Smsg的数量
	 */
	int sendAvailableMsgs();
}