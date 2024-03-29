/*
 * $Id: SmsgSender.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import org.springframework.core.Ordered;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;

/**
 * 消息发送器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 2.1.0
 */
public interface SmsgSender extends Ordered{

	/**
	 * 判断该发送器是否支持指定消息的发送。
	 * @param msg 消息
	 * @param r 消息接收人
	 * @return
	 */
	boolean supports(Smsg msg, SmsgReceiver r);
	

	/**
	 * 发送或者提交发送（异步）指定的消息。
	 * 
	 * 用于同步的消息发送时，该方法不能处于长时间的阻塞，
	 * 否则可能导致调用该方法的其它方法事务超时。
	 * 
	 * @param msg 消息
	 * @param r 消息接收人
	 * @return 发送或者提交的消息数量
	 * 
	 * @throws 发送失败可能抛出异常
	 */
	int send(Smsg msg,  SmsgReceiver r) throws SmsgException;
}
