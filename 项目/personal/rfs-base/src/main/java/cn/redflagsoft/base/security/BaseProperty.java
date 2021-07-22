/*
 * $Id: BaseProperty.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;


/**
 * 
 * 基本属性，系统内部使用的。
 * @author Alex Lin
 *
 */
public enum BaseProperty {
	pageSize;
	
	
//	private static final StringManager strings = StringManager.getManager(BaseProperty.class);
//
//	public String getLabel() {
//		String label = strings.getString(name());
//		if(label == null){
//			label = name();
//		}
//		return label;
//	}
	
	public String getName(){
		return name();
	}
}
