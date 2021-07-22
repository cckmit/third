/*
 * $Id: CautionHelper.java 5352 2012-03-01 06:29:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;


/**
 * ��ʾ�����ࡣ
 * @author lcj
 * @deprecated
 */
public abstract class CautionHelper {
	
	public static void copyDutyerFromRiskRule(Caution c, RiskRule rule){
		c.setDutyerDeptId(rule.getDutyerDeptId());
		c.setDutyerDeptName(rule.getDutyerDeptName());
		c.setDutyerId(rule.getDutyerId());
		c.setDutyerLeaderId(rule.getDutyerLeader2Id());
		c.setDutyerLeaderName(rule.getDutyerLeader2Name());
		c.setDutyerManagerId(rule.getDutyerLeader1Id());
		c.setDutyerManagerName(rule.getDutyerLeader1Name());
		c.setDutyerName(rule.getDutyerName());
		c.setDutyerOrgId(rule.getDutyerOrgId());
		c.setDutyerOrgName(rule.getDutyerOrgName());
	}

	public static void copyDutyerFromTask(Caution c, Task task){
		//c.setDutyerId(task.getDutyerID());
		//c.setDutyerName(task.getDutyerName());
		c.setDutyerId(task.getClerkID());
		c.setDutyerName(task.getClerkName());
	}
	
	public static void copyDutyerFromRFSObject(Caution c, RFSObject object){
		c.setDutyerDeptId(object.getDutyDepartmentID());
		c.setDutyerDeptName(object.getDutyDepartmentName());
		c.setDutyerId(object.getDutyClerkID());
		c.setDutyerLeaderId(object.getDutyerLeader2Id());
		c.setDutyerLeaderName(object.getDutyerLeader2Name());
		c.setDutyerManagerId(object.getDutyerLeader1Id());
		c.setDutyerManagerName(object.getDutyerLeader1Name());
		c.setDutyerName(object.getDutyClerkName());
		c.setDutyerOrgId(object.getDutyEntityID());
		c.setDutyerOrgName(object.getDutyEntityName());
	}
	
	
	public static void copyDutyerFromRisk(Caution c, Risk risk){
		c.setDutyerDeptId(risk.getDutyerDeptId());
		c.setDutyerDeptName(risk.getDutyerDeptName());
		c.setDutyerId(risk.getDutyerID());
		c.setDutyerName(risk.getDutyerName());
		c.setDutyerOrgId(risk.getDutyerOrgId());
		c.setDutyerOrgName(risk.getDutyerOrgName());
	}
}
