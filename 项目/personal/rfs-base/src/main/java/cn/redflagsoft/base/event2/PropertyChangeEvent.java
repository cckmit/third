/*
 * $Id: PropertyChangeEvent.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import java.io.Serializable;

import org.opoo.apps.event.v2.Event;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PropertyChangeEvent<O, V extends Serializable> extends Event<PropertyChangeEvent.Type, O> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4852162142746769150L;
	
	private V oldValue;
	private V newValue;
	private String propertyName;

	/**
	 * 
	 * @param object
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public PropertyChangeEvent(O object, String propertyName, V oldValue, V newValue){
		super(Type.CHANGE, object);
		this.propertyName = propertyName;
		this.newValue = newValue;
		this.oldValue = oldValue;
	}
	
	public V getOldValue() {
		return oldValue;
	}

	public V getNewValue() {
		return newValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 
	 *
	 */
	protected static enum Type {
		CHANGE;
	}
}
