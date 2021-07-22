/*
 * $Id: SchemeEvent.java 6349 2014-02-28 07:18:03Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.event;

import java.lang.reflect.Method;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.scheme.Scheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SchemeEvent extends Event<SchemeEvent.Type, Scheme> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5625920921286695291L;

	public static enum Type{
		EXECUTED
	}

	private long time;
	private Method method;
	private Object result;

	public SchemeEvent(Type eventType, Scheme scheme) {
		super(eventType, scheme);
	};
	
	public SchemeEvent(Type eventType, Scheme scheme, long time) {
		this(eventType, scheme);
		this.time = time;
	};
	
	/**
	 * @param eventType
	 * @param source
	 * @param time
	 * @param method
	 * @param result
	 */
	public SchemeEvent(Type eventType, Scheme source, long time, Method method,
			Object result) {
		super(eventType, source);
		this.time = time;
		this.method = method;
		this.result = result;
	}

	public long getTime(){
		return time;
	}
	
	public String getSchemeName(){
		return getSource().getBeanName();
	}
	
	public String getMethodName(){
		return getSource().getMethod();
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
}
