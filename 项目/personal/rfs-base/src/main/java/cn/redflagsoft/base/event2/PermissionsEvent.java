/*
 * $Id: PermissionsEvent.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.security.PermissionType;

public class PermissionsEvent extends Event<PermissionsEvent.Type, Long>{
	private static final long serialVersionUID = 3089732759154101954L;
	
	
	private String aceId;
	private long userId = -3;
	private long groupId = -3;
	private int permissionType = PermissionType.ADDITIVE.getId();
	
	public PermissionsEvent(Type eventType, Long source) {
		super(eventType, source);
	}
	public long getPermission(){
		return getSource();
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public int getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}
	public String getAceId() {
		return aceId;
	}
	public void setAceId(String aceId) {
		this.aceId = aceId;
	}



	public static enum Type {
        ADDED, REMOVED;
    }
}
