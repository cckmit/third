/*
 * $Id: BizDef.java 6414 2014-07-08 03:52:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import cn.redflagsoft.base.ObjectTypes;

/**
 * 业务定义。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BizDef {
	//public static final short GLOSSARY_CATEGORY_BIZ_DEF_DUTYER_TYPE = Glossary.CATEGORY_BIZ_DEF_DUTYER_TYPE;

	/**
	 * 无。
	 */
	public static final int DUTYER_TYPE_UNKNOWN = 0;
	/**
	 * 1 从业务三级责任人定义表中获取，以当前用户为负责人，在业务三级责任人定义表查找科室主管和分管领导
	 */
	public static final int DUTYER_TYPE_CONFIG_CURRENT_USER = 1;
	/**
	 * 2 从业务三级责任人定义表中获取，按排序正序取第一条即可
	 */
	public static final int DUTYER_TYPE_CONFIG_FIRST_MATCH = 2;
	/**
	 * 3 从当前业务处理中涉及的对象中取3级责任人
	 */
	public static final int DUTYER_TYPE_FROM_OBJECT = 3;
	
	/**
	 * 4 从业务三级责任人定义表中获取，根据对象的责任单位进行查询，返回第一条满足条件
	 * 的记录。
	 */
	public static final int DUTYER_TYPE_CONFIG_BY_OBJECT_ORG_ID = 4;
	
	/**
	 * 获取当前业务定义中的责任人类型。
	 * @return
	 */
	int getDutyerType();
	
	/**
	 * 业务定义的ID，比如taskType，WorkType
	 * @return
	 */
	Integer getId();
	
	/**
	 * 业务类型，标志该业务时task，work或者job
	 * @see ObjectTypes
	 * @return
	 */
	int getObjectType();
}
