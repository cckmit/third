/*
 * $Id: MessageManager.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Msg;

/**
 * @author Alex Lin
 * @deprecated using {@link SmsgManager}
 */
public interface MessageManager {

	/**
	 * 发送单条已拆分的消息。
	 * 
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	Msg sendMsg(Msg msg) throws Exception;
	
	/**
	 * 拆分指定消息并发送。
	 * 
	 * @param sendingMsgId
	 * @return
	 * @throws Exception
	 */
	List<Msg> sendMsgs(long sendingMsgId) throws Exception;
	
}
