/*
 * $Id: TaskDef.java 6401 2014-05-08 07:19:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Set;

/**
 * Task定义接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskDef extends BizSummaryTemplateProvider, BizDef{
	
	final static int DUTY_REF_IGNORE = 0;
	
	/**
	 * 责任人信息来源于对象。不再使用。参考  {@link BizDef#DUTYER_TYPE_FROM_OBJECT}
	 * @see BizDef#DUTYER_TYPE_FROM_OBJECT
	 */
	@Deprecated
	final static int DUTY_REF_FROM_OBJECT = 1;
	
	/**
	 * 办理人必须等于负责人。
	 */
	final static int DUTY_REF_CLERK_EQUALS_DUTYER = 11;
	
	
	int INVALID_TYPE = -1;
	
	int getTaskType();
	
	String getName();
	
	int getVisibility();
	
	Integer getParentTaskType();
	
	String getTypeAlias();
	
//	TaskDef getParent();
	
//	List<TaskDef> getSubTaskDefs();
	
	Set<Integer> getSubTaskTypes();
	
	Set<Integer> getWorkTypes();
	
	int getDutyRef();
	
	String getLifeStageFieldName();
	
	String getBizSummaryTemplate();      //    业务状况模板

	/**
	 * 计算标识。基于位存储信息。
	 * 
	 * <ol>从右到左：
	 *  <li>第一位 表示计算时限时是否包含第一天。 1包含，0不包含。
	 *  <li>第二位 待定。。。
	 * </ol>
	 * @return
	 * @see http://192.168.18.6/sf/sfmain/do/go/wiki1213
	 */
	int getCalculateFlag();
	
	int getHasGuide();
}
