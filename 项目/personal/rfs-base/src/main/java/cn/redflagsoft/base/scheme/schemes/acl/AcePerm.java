/*
 * $Id: AcePerm.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.acl;

import java.io.Serializable;
import java.util.StringTokenizer;

import cn.redflagsoft.base.security.Permissions;

public class AcePerm implements Serializable {
	private static final long serialVersionUID = 6895371514473629492L;
	private String aceId;
	private long perms;

	public String getAceId() {
		return aceId;
	}
	public void setAceId(String aceId) {
		this.aceId = aceId;
	}
	public long getPerms() {
		return perms;
	}
	public void setPerms(long perms) {
		this.perms = perms;
	}

	public void setStringPerms(String stringPerms){
		StringTokenizer st = new StringTokenizer(stringPerms, ", ");
		long permission = 0;
		while(st.hasMoreTokens()){
			long value = Long.parseLong(st.nextToken());
			permission |= value;
		}
		perms = permission;
	}

	public String getStringPerms(){
		if(perms <= 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Permissions permissions = new Permissions(perms);
		for(int i = 0; i <= 63 ; i++){
			long mask = (long) Math.pow(2, i);
			if(mask > perms){
				break;
			}
			if(permissions.hasPermission(mask)){
				if(sb.length() > 0){
					sb.append(",");
				}
				sb.append(mask);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		AcePerm perm = new AcePerm();
		perm.setAceId("project");
		perm.setPerms(484L);
		System.out.println(perm.getStringPerms());
		
		perm.setStringPerms("16,32,20");
		System.out.println(perm.getPerms());
		System.out.println(perm.getStringPerms());
	}
}
