/*
 * $Id: BizInstance.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;


/**
 * 业务实例。
 * 
 * @author Alex Lin(lcql@msn.com)
 */
public interface BizInstance/* extends DutyersInfo*/{
//
//	/**
//	 * 责任单位ID
//	 * @return
//	 */
//	Long getDutyEntityID();
//	
//	/**
//	 * 责任单位名称
//	 * @return
//	 */
//	String getDutyEntityName();
//	
//	/**
//	 * 责任人ID
//	 * @return
//	 */
//	Long getDutyerID();
//	/**
//	 * 责任人姓名
//	 * @return
//	 */
//	String getDutyerName();
//	
//	/**
//	 * 科室主管ID
//	 * @return
//	 */
//	Long getDutyerLeader1Id();
//	/**
//	 * 科室主管姓名
//	 * @return
//	 */
//	String getDutyerLeader1Name();
//	
//	/**
//	 * 分管领导ID
//	 * @return
//	 */
//	Long getDutyerLeader2Id();
//	/**
//	 * 分管领导姓名
//	 * @return
//	 */
//	String getDutyerLeader2Name();
	
	

	/**
	 * 责任单位ID
	 * @return
	 */
	void setDutyEntityID(Long dutyEntityID);
	
	/**
	 * 责任单位名称
	 * @return
	 */
	void setDutyEntityName(String dutyEntityName);
	
	/**
	 * 责任部门ID
	 * @param dutyDepartmentID
	 */
	void setDutyDepartmentID(Long dutyDepartmentID);
	
	/**
	 * 责任部门名称
	 * @param dutyDepartmentName
	 */
	void setDutyDepartmentName(String dutyDepartmentName);
	
	/**
	 * 责任人ID
	 * @return
	 */
	void setDutyerID(Long dutyerID);
	/**
	 * 责任人姓名
	 * @return
	 */
	void setDutyerName(String dutyerName);

	/**
	 * 科室主管ID
	 * @return
	 */
	void setDutyerLeader1Id(Long dutyerLeader1Id);

	/**
	 * 科室主管姓名
	 * @return
	 */
	void setDutyerLeader1Name(String dutyerLeader1Name);
	
	/**
	 * 分管领导ID
	 * @return
	 */
	void setDutyerLeader2Id(Long dutyerLeader2Id);
	
	/**
	 * 分管领导姓名
	 * @return
	 */
	void setDutyerLeader2Name(String dutyerLeader2Name);
}
