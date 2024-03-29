/*
 * $Id: SmsgSendEvent.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.service.SmsgSender;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgSendEvent extends Event<SmsgSendEvent.Type, SmsgSender> {
	private static final long serialVersionUID = -1300942477892813884L;
	private final SmsgReceiver receiver;
	private final Smsg msg;
	public SmsgSendEvent(Type eventType, SmsgSender sender, Smsg msg, SmsgReceiver receiver) {
		super(eventType, sender);
		this.msg = msg;
		this.receiver = receiver;
	}

	/**
	 * @return the receivers
	 */
	public SmsgReceiver getReceiver() {
		return receiver;
	}

	/**
	 * @return the msg
	 */
	public Smsg getMsg() {
		return msg;
	}

	public static enum Type{
		START, SUCCESS, FAIL;
	} 
}
