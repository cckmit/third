/*
 * $Id: PermsBean.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.impl.dao.hibernate3;

import cn.redflagsoft.base.security.Permissions;
import cn.redflagsoft.base.security.impl.dao.Perms;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PermsBean implements Perms{
	private static final long serialVersionUID = -247733462626024685L;
	private PermsKey id;
	private long permissions = Permissions.NONE;

	/**
	 * 
	 */
	public PermsBean() {
	}
	public PermsBean(String aceId, long sid, int type, long permissions) {
		this.id = new PermsKey(aceId, sid, type);
		this.permissions = permissions;
	}
	public PermsBean(String aceId, long sid, int type) {
		this.id = new PermsKey(aceId, sid, type);
	}
	public PermsBean(PermsKey id) {
		this.id = id;
	}
	
	public PermsKey getId() {
		return id;
	}
	public void setId(PermsKey id) {
		this.id = id;
	}

	public long getPermissions() {
		return permissions;
	}

	public void setPermissions(long permissions) {
		this.permissions = permissions;
	}

	public String getAceId() {
		return getId().getAceId();
	}

	public int getType() {
		return getId().getType();
	}

	public long getSid() {
		return getId().getSid();
	}
}
