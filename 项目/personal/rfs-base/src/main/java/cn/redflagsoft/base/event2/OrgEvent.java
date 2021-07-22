/*
 * $Id: OrgEvent.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import java.util.HashMap;
import java.util.Map;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Org;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgEvent extends Event<OrgEvent.Type, Org>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -641054446871080198L;

	private Map<String, ?> parameters = new HashMap<String, Object>();
	
	/**
	 * @param eventType
	 * @param source
	 */
	public OrgEvent(Type eventType, Org source) {
		super(eventType, source);
	}
	
	public OrgEvent(Type eventType, Org source, Map<String, ?> parameters) {
		super(eventType, source);
		this.parameters = parameters;
	}
	
	public Map<String, ?> getParameters() {
		return parameters;
	}

	/**
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum Type {
		CREATED, UPDATED, DELETED, BATCH_DELETED, BEFORE_DELETE;
	}
}
