/*
 * $Id: AbstractSmsgSender.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.event2.SmsgSendEvent;
import cn.redflagsoft.base.service.SmsgSender;

/**
 * 抽象的消息发送器。
 * 
 * 定义了消息发送个各种情况下可以调用的事件。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class AbstractSmsgSender implements SmsgSender {
	
	/**
	 * 消息开始发送时事件调用。
	 * @param msg 消息
	 * @param r 接收者
	 */
	protected final void fireSmsgSendStartEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.START);
	}
	
	/**
	 * 消息发送成功时的事件调用。
	 * @param msg 消息
	 * @param r 接收者
	 */
	protected final void fireSmsgSendSuccessEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.SUCCESS);
	}
	
	/**
	 * 消息发送失败时的事件调用。
	 * @param msg 消息
	 * @param r 接收者
	 */
	protected final void fireSmsgSendFailEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.FAIL);
	}
	
	private void fireEvent(Smsg msg, SmsgReceiver r, SmsgSendEvent.Type type){
		EventDispatcher dispatcher = getEventDispatcher();
		if(dispatcher != null){
			SmsgSendEvent event = new SmsgSendEvent(type, this, msg, r);
			dispatcher.dispatchEvent(event);
		}
	}
	private EventDispatcher getEventDispatcher(){
		return Application.getContext().get("eventDispatcher", EventDispatcher.class);
	}
}
