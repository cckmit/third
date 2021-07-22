/*
 * $Id: Code.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.apps.bean.SerializableEntity;

/**
 * @author mwx
 *
 */
public class Code extends SerializableEntity<String>{
	
	private static final long serialVersionUID = 363438618311755642L;
	private String id;
	private long base;
	private long current;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getBase() {
		return base;
	}
	public void setBase(long base) {
		this.base = base;
	}
	public long getCurrent() {
		return current;
	}
	public void setCurrent(long current) {
		this.current = current;
	}
}
