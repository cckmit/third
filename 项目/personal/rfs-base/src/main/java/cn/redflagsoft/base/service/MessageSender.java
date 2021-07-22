/*
 * $Id: MessageSender.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import org.springframework.core.Ordered;

import cn.redflagsoft.base.bean.Msg;


/**
 * @deprecated using {@link SmsgSender}
 *
 */
public interface MessageSender extends Ordered {
	
	/**
	 * 判断发送器是否支持指定消息的发送
	 * 
	 * @param msg
	 * @return
	 */
	boolean supports(Msg msg);
	
	/**
	 * 发送消息
	 * @param msg
	 * @throws Exception
	 */
	void send(Msg msg) throws Exception;
}
