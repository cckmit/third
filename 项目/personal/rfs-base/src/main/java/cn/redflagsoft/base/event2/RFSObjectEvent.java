/*
 * $Id: RFSObjectEvent.java 5161 2011-12-02 03:38:59Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.RFSObject;


/**
 * 业务对象操作事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <O>
 */
public class RFSObjectEvent<O extends RFSObject> extends Event<RFSObjectEvent.Type, O> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7716923111100674977L;

	private O oldObject;
	
	public RFSObjectEvent(Type eventType, O source) {
		super(eventType, source);
	}
	
	public RFSObjectEvent(Type eventType, O oldObj, O newObj) {
		super(eventType, newObj);
		this.oldObject = oldObj;
	}
	
	public O getNewObject(){
		return getSource();
	}
	
	public O getOldObject(){
		return oldObject;
	}

	
	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum Type{
		CREATED, UPDATED, DELETE;
	}
}
