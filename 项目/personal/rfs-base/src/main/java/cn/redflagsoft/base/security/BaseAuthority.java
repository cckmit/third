/*
 * $Id: BaseAuthority.java 5233 2011-12-20 09:50:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;


/**
 * @author Alex Lin
 *
 */
public enum BaseAuthority {
	/**
	 * 普通用户
	 */
	USER("普通用户", 3), 
	/**
	 * 管理
	 */
	ADMIN("超级管理(ADMIN/RFSA)", 1), 
	/**
	 * 管理员，交付给用户的最高级别管理权限。
	 */
	ADMINISTRATOR("管理员(ADMINISTRATOR)", 0),
	
//	/**
//	 * 超级管理
//	 */
//	SUPERVISOR("超级管理（SUPERVISOR）"), 
//	/**
//	 * 系统管理
//	 */
//	SYS("系统管理（SYS）"),
	/**
	 * 发布公告通知
	 */
	POST_PUBLIC_MESSAGE("发布公告通知", 3),
	/**
	 * 文件管理
	 */
	FILE_ADMIN("文件管理", 3),
	
	/**
	 * 部门管理员，部门经理
	 */
	MANAGER("单位管理员", 2),
	
	/**
	 * 系统运行内部用户，不对外使用
	 */
	INTERNAL("内部用户", -1);
	
	
	private final GrantedAuthority grantedAuthority;
	private final String label;
	private final int level;
	private BaseAuthority(String label, int level) {
		grantedAuthority = new GrantedAuthorityImpl(name());
		this.label = label;
		this.level = level;
	}
	
	/**
	 * 返回根据权限封装的授权对象。
	 * @return
	 */
	public GrantedAuthority getGrantedAuthority(){
		return grantedAuthority;
	}
	
	/**
	 * 返回权限的标签（显示名称）。
	 * @return
	 */
	public String getLabel(){
		return label;
	}
	
	/**
	 * 返回权限的值。
	 * @return
	 */
	public String getValue(){
		return name();
	}
	
	public int getLevel(){
		return level;
	}
	
	public static void main(String[] args){
		System.out.println(BaseAuthority.ADMIN.getGrantedAuthority().getClass());
		BaseAuthority a = BaseAuthority.valueOf("ADMIN");
		System.out.println(a == BaseAuthority.ADMIN);
		for(BaseAuthority aa : BaseAuthority.values()){
			System.out.println(aa.name() + " : " + aa.getLabel());
			Authority aaa = new Authority(aa);
			System.out.println(aaa.getLabel());
		}
		
		
	}
}
