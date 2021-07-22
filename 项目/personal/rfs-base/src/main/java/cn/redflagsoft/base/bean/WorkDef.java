/*
 * $Id: WorkDef.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;


/**
 * Work定义接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface WorkDef extends BizDef{
	/**
	 * 无效的类型。
	 */
	int INVALID_TYPE = -1;
	
	/**
	 * Work类型。
	 * @return
	 */
	int getWorkType();
	
	/**
	 * 名称。
	 * @return
	 */
	String getName();
	
	/**
	 * 获取所属task的类型。
	 * @return
	 */
	int getTaskType();
	
	/**
	 * 
	 * @return
	 */
	String getTypeAlias();
	
	/**
	 * 
	 * @return
	 */
	String getDutyer();
}
