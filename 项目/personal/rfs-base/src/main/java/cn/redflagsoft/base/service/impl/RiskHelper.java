/*
 * $Id: RiskHelper.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.ObjectOrgClerk;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;

/**
 * 风险处理助手类。
 * @author lcj
 *
 */
public abstract class RiskHelper {
	
	/**
	 * 从监管规则中复制责任信息。
	 * 
	 * @param risk
	 * @param riskRule
	 */
	public static void copyDutyerFromRiskRule(Risk risk, RiskRule riskRule) {
		risk.setDutyerID(riskRule.getDutyerId());
		risk.setDutyerName(riskRule.getDutyerName());
		risk.setDutyerDeptId(riskRule.getDutyerDeptId());
		risk.setDutyerDeptName(riskRule.getDutyerDeptName());
		risk.setDutyerOrgId(riskRule.getDutyerOrgId());
		risk.setDutyerOrgName(riskRule.getDutyerOrgName());
		//分管领导
		risk.setDutyerLeaderId(riskRule.getDutyerLeader2Id());
		risk.setDutyerLeaderName(riskRule.getDutyerLeader2Name());
		//直属上级
		risk.setDutyerManagerId(riskRule.getDutyerLeader1Id());
		risk.setDutyerManagerName(riskRule.getDutyerLeader1Name());
	}

	/**
	 * 从对象中复制责任信息。
	 * @param risk
	 * @param object
	 * @see RiskHelper#copyDutyerFromDutyersInfo(Risk, DutyersInfo)
	 */
	public static void copyDutyerFromRFSObject(Risk risk, RFSObject object) {
		//使用 copyDutyerFromDutyersInfo
		/*
		risk.setDutyerOrgId(object.getDutyEntityID());
		risk.setDutyerOrgName(object.getDutyEntityName());
		risk.setDutyerDeptId(object.getDutyDepartmentID());
		risk.setDutyerDeptName(object.getDutyDepartmentName());
		risk.setDutyerID(object.getDutyClerkID());
		risk.setDutyerName(object.getDutyClerkName());
		//分管领导
		risk.setDutyerLeaderId(object.getDutyerLeader2Id());
		risk.setDutyerLeaderName(object.getDutyerLeader2Name());
		//直属上级
		risk.setDutyerManagerId(object.getDutyerLeader1Id());
		risk.setDutyerManagerName(object.getDutyerLeader1Name());
		*/
		
		copyDutyerFromDutyersInfo(risk, object);
	}

	/**
	 * 从task中负责责任信息。
	 * 
	 * @param risk
	 * @param task
	 * @see RiskHelper#copyDutyerFromDutyersInfo(Risk, DutyersInfo)
	 */
	public static void copyDutyerFromTask(Risk risk, Task task) {
		//使用 copyDutyerFromDutyersInfo
		/*
		//risk.setDutyerID(task.getDutyerID());
		//risk.setDutyerName(task.getDutyerName());
		risk.setDutyerID(task.getClerkID());
		risk.setDutyerName(task.getClerkName());
		risk.setDutyerOrgId(task.getEntityID());
		risk.setDutyerOrgName(task.getEntityName());
		risk.setDutyerDeptId(task.getDepartID());
		risk.setDutyerDeptName(task.getDepartmentName());
		*/
		
		copyDutyerFromDutyersInfo(risk, task);
	}
	
	/**
	 * 从三级责任人信息中复制责任信息。
	 * @param risk
	 * @param dutyers
	 */
	public static void copyDutyerFromDutyersInfo(Risk risk, DutyersInfo dutyers){
		risk.setDutyerID(dutyers.getDutyerID());
		risk.setDutyerName(dutyers.getDutyerName());
		risk.setDutyerDeptId(dutyers.getDutyDepartmentID());
		risk.setDutyerDeptName(dutyers.getDutyDepartmentName());
		risk.setDutyerOrgId(dutyers.getDutyEntityID());
		risk.setDutyerOrgName(dutyers.getDutyEntityName());
		//分管领导
		risk.setDutyerLeaderId(dutyers.getDutyerLeader2Id());
		risk.setDutyerLeaderName(dutyers.getDutyerLeader2Name());
		//直属上级
		risk.setDutyerManagerId(dutyers.getDutyerLeader1Id());
		risk.setDutyerManagerName(dutyers.getDutyerLeader1Name());
	}
	
	/**
	 * 从对象单位人员关系表中复制责任信息。
	 * @param risk
	 * @param object
	 */
	public static void copyDutyerFromObjectOrgClerk(Risk risk, ObjectOrgClerk object){
		risk.setDutyerOrgId(object.getOrgId());
		risk.setDutyerOrgName(object.getOrgName());
//		risk.setDutyerDeptId(object.getDutyDepartmentID());
//		risk.setDutyerDeptName(object.getDutyDepartmentName());
		risk.setDutyerID(object.getClerk1Id());
		risk.setDutyerName(object.getClerk1Name());
		
		//直属上级
		risk.setDutyerManagerId(object.getClerk2Id());
		risk.setDutyerManagerName(object.getClerk2Name());
		
		//分管领导
		risk.setDutyerLeaderId(object.getClerk3Id());
		risk.setDutyerLeaderName(object.getClerk3Name());
	}
}
