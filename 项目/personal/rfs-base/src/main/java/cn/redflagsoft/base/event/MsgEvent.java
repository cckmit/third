/*
 * $Id: MsgEvent.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event;

import org.opoo.apps.event.Event;

import cn.redflagsoft.base.bean.Msg;

/**
 * @author Alex Lin
 * @deprecated
 */
public class MsgEvent extends Event<Msg> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349152133652558571L;
	public static int SENDING = 0;
	public static int SENT = 1;
	public static int FAIL = -1;
	/**
	 * @param eventType
	 * @param source
	 */
	public MsgEvent(int eventType, Msg source) {
		super(eventType, source);
	}

}
