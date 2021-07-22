/*
 * $Id: MsgSendEvent.java 6044 2012-09-28 04:14:42Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event;

import org.springframework.context.ApplicationEvent;

import cn.redflagsoft.base.bean.Msg;

/**
 * @author Alex Lin
 * @deprecated
 */
public class MsgSendEvent extends ApplicationEvent {
	public enum Type{
		SEND, SENDING, SENT, FAIL,DISABLE;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -193014210428381786L;
	
	private Type type;
	public MsgSendEvent(Type type, Msg source) {
		super(source);
		this.type = type;
	}
	
	public Msg getMsg(){
		return (Msg) getSource();
	}
	
	public Type getType(){
		return type;
	}
}
