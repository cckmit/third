/*
 * $Id: Ace.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.util.HashSet;
import java.util.Set;

/**
 * Access control entry definition.
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Ace {
	private String id;
	private String name;
	private Set<Entry> entrySet = new HashSet<Entry>();
	
	public Ace() {
		super();
	}
	
	public Ace(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<Entry> getEntrySet() {
		return entrySet;
	}
	public void setEntrySet(Set<Entry> entrySet) {
		this.entrySet = entrySet;
	}
	
	public static class Entry{
		private String mask;
		private String name;
		private int order = 0;
		private long permission = 0;
		private boolean checked = false;
		
		public Entry() {
			super();
		}
		
		public Entry(String mask, String name, long permission) {
			this.mask = mask;
			this.name = name;
			this.permission = permission;
		}
		public Entry(String mask, String name, long permission, int order) {
			this.mask = mask;
			this.name = name;
			this.permission = permission;
			this.order = order;
		}
		
		public String getMask() {
			return mask;
		}

		public void setMask(String mask) {
			this.mask = mask;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getOrder() {
			return order;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public long getPermission() {
			return permission;
		}
		public void setPermission(long permission) {
			this.permission = permission;
		}

		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
}
