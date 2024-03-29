/*
 * $Id: Perms.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.impl.dao;

import java.io.Serializable;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Perms extends Serializable{

	String getAceId();
	
	long getPermissions();
	
	int getType();
	
	long getSid();
	
	
//	/**
//	 * Ace的标识。
//	 * @return
//	 */
//	String getAceId();
	
//	/**
//	 * 名称
//	 * @return
//	 */
//	String getAceName();
	
//	/**
//	 * 组合权限值。
//	 * @return
//	 */
//	Permissions getPermissions();
//	
//	/**
//	 * 
//	 * @return
//	 */
//	PermissionType getPermissionType();
}
