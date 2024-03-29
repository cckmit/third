/*
 * $Id: TaskBizInfo.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * 	TASK业务信息
 * 	
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskBizInfo extends VersionableBean implements TaskInfo {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3336611394457433861L;
	
	private Long taskSN;
	private String taskName;
	private String acptWorkItemName;				//受理事项名称
	private String acptSN;							//受理编号
	private Long acptApplyOrgId;					//受理申请单位
	private String acptApplyOrgName;				//受理申请单位名称
	private Long acptApplyClerkId;					//受理申请人ID
	private String acptApplyClerkName;				//受理申请人姓名
	private String acptRemark;						//受理备注
	private Date acptTime;							//受理时间
	private Date acptOperateTime;					//受理操作时间
	private Long acptOperateClerkId;				//受理操作者ID
	private String acptOperateClerkName;			//受理操作者姓名
	private int acptDocCount;						//受理资料数量
	private int acptAttacchmentCount;				//受理附件数量
	private Long acptClerkId;						//受理经办人ID
	private String acptClerkName;					//受理经办人姓名
	
	private String daWorkItemName;					//审批决定登记事项名称
	private String daFileNo;						//审批决定登记决定文号
	private Date daAuditTime;						//审批决定登记审定时间
	private String daRemark;						//审批决定登记备注
	private Date daOperateTime;						//审批决定登记操作时间
	private Long daOperateClerkId;					//审批决定登记操作者ID
	private String daOperateClerkName;				//审批决定登记操作者姓名
	private int daDocCount;							//审批决定登记资料数量
	private int daAttacchmentCount;					//审批决定登记附件数量
	private Long daClerkId;//审批决定登记经办人ID
	private String daClerkName;//审批决定登记经办人姓名
	private Date daTime;//审批决定登记时间（用户输入）
	
	private String drWorkItemName;					//审批决定发文事项名称
	private Date drTime;							//审批决定发文决定时间
	private Long drDocReceiveOrgId;					//审批决定发文收文单位id
	private String drDocReceiveOrgName;				//审批决定发文收文单位名称
	private Long drClerkId;							//审批决定发文经办人ID
	private String drClerkName;						//审批决定发文经办人姓名
	private String drRemark;						//审批决定发文备注
	private Date drOperateTime;						//审批决定发文操作时间
	private Long drOperateClerkId;					//审批决定发文操作者ID
	private String drOperateClerkName;				//审批决定发文操作者姓名
	private int drDocCount;							//审批决定发文资料数量
	private int drAttacchmentCount;					//审批决定发文附件数量
	
	private String rcdWorkItemName;					//报审受理备案事项名称
	private Long rcdApplyClerkId;					//报审受理备案申报人id
	private String rcdApplyClerkName;				//报审受理备案申报人姓名
	private Long rcdAcceptOrgId;					//报审受理备案受理单位ID
	private String rcdAcceptOrgName;				//报审受理备案受理单位名称
	private String rcdReceiptFileNo;				//报审受理备案受理回执文号
	private Long rcdAcceptClerkId;					//报审受理备案受理人ID
	private String rcdAcceptClerkName;
	private Date rcdAcceptTime;						//报审受理备案受理时间
	private String rcdRemark;						//报审受理备案备注
	private Date rcdOperateTime;					//报审受理备案操作时间
	private Long rcdOperateClerkId;					//报审受理备案操作者ID
	private String rcdOperateClerkName;				//报审受理备案操作者姓名
	private int rcdDocCount;						//报审受理备案资料数量
	private int rcdAttacchmentCount;				//报审受理备案附件数量
	private Long rcdClerkId;//报审受理备案经办人ID
	private String rcdClerkName;//报审受理备案经办人姓名
	private Date rcdTime;//报审受理备案经办时间
	
	
	private String acvWorkItemName;					//工程资料存档事项名称
	private String acvDocFileNo;					//工程资料存档资料文号
	private Long acvDocPublishOrgId;				//工程资料发布单位ID
	private String acvDocPublishOrgName;			//工程资料发布单位名称
	private Date acvDocPublishTime;					//工程资料发布时间
	private Long acvClerkId;						//工程资料存档人ID
	private String acvClerkName;					//工程资料存档人姓名
	private String acvRemark;						//工程资料存档备注
	private Date acvOperateTime;					//工程资料存档操作时间
	private Long acvOperateClerkId;					//工程资料存档操作者ID
	private String acvOperateClerkName;				//工程资料存档操作者姓名
	private int acvDocCount;						//工程资料存档资料数量
	private int acvAttacchmentCount;				//工程资料存档附件数量
	private Date acvTime;							//工程资料存档经办时间
	
	// private int type; 							//类型
	// private byte status; 						//状态
	// private String remark; 						//备注
	// private Long creator; 						//创建者
	// private Date creationTime; 					//创建时间
	// private Long modifier; 						//最后修改者
	// private Date modificationTime; 				//最后修改时间
	
	public Long getTaskSN() {
		return taskSN;
	}

	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAcptWorkItemName() {
		return acptWorkItemName;
	}

	public void setAcptWorkItemName(String acptWorkItemName) {
		this.acptWorkItemName = acptWorkItemName;
	}

	public String getDaWorkItemName() {
		return daWorkItemName;
	}

	public void setDaWorkItemName(String daWorkItemName) {
		this.daWorkItemName = daWorkItemName;
	}

	public String getDrWorkItemName() {
		return drWorkItemName;
	}

	public void setDrWorkItemName(String drWorkItemName) {
		this.drWorkItemName = drWorkItemName;
	}

	public String getRcdWorkItemName() {
		return rcdWorkItemName;
	}

	public void setRcdWorkItemName(String rcdWorkItemName) {
		this.rcdWorkItemName = rcdWorkItemName;
	}

	public String getAcvWorkItemName() {
		return acvWorkItemName;
	}

	public void setAcvWorkItemName(String acvWorkItemName) {
		this.acvWorkItemName = acvWorkItemName;
	}

	public String getAcptSN() {
		return acptSN;
	}

	public void setAcptSN(String acptSN) {
		this.acptSN = acptSN;
	}

	public Long getAcptApplyOrgId() {
		return acptApplyOrgId;
	}

	public void setAcptApplyOrgId(Long acptApplyOrgId) {
		this.acptApplyOrgId = acptApplyOrgId;
	}

	public String getAcptApplyOrgName() {
		return acptApplyOrgName;
	}

	public void setAcptApplyOrgName(String acptApplyOrgName) {
		this.acptApplyOrgName = acptApplyOrgName;
	}

	public Long getAcptApplyClerkId() {
		return acptApplyClerkId;
	}

	public void setAcptApplyClerkId(Long acptApplyClerkId) {
		this.acptApplyClerkId = acptApplyClerkId;
	}

	public String getAcptApplyClerkName() {
		return acptApplyClerkName;
	}

	public void setAcptApplyClerkName(String acptApplyClerkName) {
		this.acptApplyClerkName = acptApplyClerkName;
	}

	public String getAcptRemark() {
		return acptRemark;
	}

	public void setAcptRemark(String acptRemark) {
		this.acptRemark = acptRemark;
	}

	public Date getAcptTime() {
		return acptTime;
	}

	public void setAcptTime(Date acptTime) {
		this.acptTime = acptTime;
	}

	public Date getAcptOperateTime() {
		return acptOperateTime;
	}

	public void setAcptOperateTime(Date acptOperateTime) {
		this.acptOperateTime = acptOperateTime;
	}

	public Long getAcptOperateClerkId() {
		return acptOperateClerkId;
	}

	public void setAcptOperateClerkId(Long acptOperateClerkId) {
		this.acptOperateClerkId = acptOperateClerkId;
	}

	public String getAcptOperateClerkName() {
		return acptOperateClerkName;
	}

	public void setAcptOperateClerkName(String acptOperateClerkName) {
		this.acptOperateClerkName = acptOperateClerkName;
	}

	public int getAcptDocCount() {
		return acptDocCount;
	}

	public void setAcptDocCount(int acptDocCount) {
		this.acptDocCount = acptDocCount;
	}

	public int getAcptAttacchmentCount() {
		return acptAttacchmentCount;
	}

	public void setAcptAttacchmentCount(int acptAttacchmentCount) {
		this.acptAttacchmentCount = acptAttacchmentCount;
	}

	public String getDaFileNo() {
		return daFileNo;
	}

	public void setDaFileNo(String daFileNo) {
		this.daFileNo = daFileNo;
	}

	public Date getDaAuditTime() {
		return daAuditTime;
	}

	public void setDaAuditTime(Date daAuditTime) {
		this.daAuditTime = daAuditTime;
	}

	public String getDaRemark() {
		return daRemark;
	}

	public void setDaRemark(String daRemark) {
		this.daRemark = daRemark;
	}

	public Date getDaOperateTime() {
		return daOperateTime;
	}

	public void setDaOperateTime(Date daOperateTime) {
		this.daOperateTime = daOperateTime;
	}

	public Long getDaOperateClerkId() {
		return daOperateClerkId;
	}

	public void setDaOperateClerkId(Long daOperateClerkId) {
		this.daOperateClerkId = daOperateClerkId;
	}

	public String getDaOperateClerkName() {
		return daOperateClerkName;
	}

	public void setDaOperateClerkName(String daOperateClerkName) {
		this.daOperateClerkName = daOperateClerkName;
	}

	public int getDaDocCount() {
		return daDocCount;
	}

	public void setDaDocCount(int daDocCount) {
		this.daDocCount = daDocCount;
	}

	public int getDaAttacchmentCount() {
		return daAttacchmentCount;
	}

	public void setDaAttacchmentCount(int daAttacchmentCount) {
		this.daAttacchmentCount = daAttacchmentCount;
	}

	public Date getDrTime() {
		return drTime;
	}

	public void setDrTime(Date drTime) {
		this.drTime = drTime;
	}

	public Long getDrDocReceiveOrgId() {
		return drDocReceiveOrgId;
	}

	public void setDrDocReceiveOrgId(Long drDocReceiveOrgId) {
		this.drDocReceiveOrgId = drDocReceiveOrgId;
	}

	public String getDrDocReceiveOrgName() {
		return drDocReceiveOrgName;
	}

	public void setDrDocReceiveOrgName(String drDocReceiveOrgName) {
		this.drDocReceiveOrgName = drDocReceiveOrgName;
	}

	public Long getDrClerkId() {
		return drClerkId;
	}

	public void setDrClerkId(Long drClerkId) {
		this.drClerkId = drClerkId;
	}

	public String getDrClerkName() {
		return drClerkName;
	}

	public void setDrClerkName(String drClerkName) {
		this.drClerkName = drClerkName;
	}

	public String getDrRemark() {
		return drRemark;
	}

	public void setDrRemark(String drRemark) {
		this.drRemark = drRemark;
	}

	public Date getDrOperateTime() {
		return drOperateTime;
	}

	public void setDrOperateTime(Date drOperateTime) {
		this.drOperateTime = drOperateTime;
	}

	public Long getDrOperateClerkId() {
		return drOperateClerkId;
	}

	public void setDrOperateClerkId(Long drOperateClerkId) {
		this.drOperateClerkId = drOperateClerkId;
	}

	public String getDrOperateClerkName() {
		return drOperateClerkName;
	}

	public void setDrOperateClerkName(String drOperateClerkName) {
		this.drOperateClerkName = drOperateClerkName;
	}

	public int getDrDocCount() {
		return drDocCount;
	}

	public void setDrDocCount(int drDocCount) {
		this.drDocCount = drDocCount;
	}

	public int getDrAttacchmentCount() {
		return drAttacchmentCount;
	}

	public void setDrAttacchmentCount(int drAttacchmentCount) {
		this.drAttacchmentCount = drAttacchmentCount;
	}

	public Long getRcdApplyClerkId() {
		return rcdApplyClerkId;
	}

	public void setRcdApplyClerkId(Long rcdApplyClerkId) {
		this.rcdApplyClerkId = rcdApplyClerkId;
	}

	public String getRcdApplyClerkName() {
		return rcdApplyClerkName;
	}

	public void setRcdApplyClerkName(String rcdApplyClerkName) {
		this.rcdApplyClerkName = rcdApplyClerkName;
	}

	public Long getRcdAcceptOrgId() {
		return rcdAcceptOrgId;
	}

	public void setRcdAcceptOrgId(Long rcdAcceptOrgId) {
		this.rcdAcceptOrgId = rcdAcceptOrgId;
	}

	public String getRcdAcceptOrgName() {
		return rcdAcceptOrgName;
	}

	public void setRcdAcceptOrgName(String rcdAcceptOrgName) {
		this.rcdAcceptOrgName = rcdAcceptOrgName;
	}

	public String getRcdReceiptFileNo() {
		return rcdReceiptFileNo;
	}

	public void setRcdReceiptFileNo(String rcdReceiptFileNo) {
		this.rcdReceiptFileNo = rcdReceiptFileNo;
	}

	public Long getRcdAcceptClerkId() {
		return rcdAcceptClerkId;
	}

	public void setRcdAcceptClerkId(Long rcdAcceptClerkId) {
		this.rcdAcceptClerkId = rcdAcceptClerkId;
	}

	public String getRcdAcceptClerkName(){
		return rcdAcceptClerkName;
	}

	public void setRcdAcceptClerkName(String rcdAcceptClerkName){
		this.rcdAcceptClerkName = rcdAcceptClerkName;
	}

	public Date getRcdAcceptTime() {
		return rcdAcceptTime;
	}

	public void setRcdAcceptTime(Date rcdAcceptTime) {
		this.rcdAcceptTime = rcdAcceptTime;
	}

	public String getRcdRemark() {
		return rcdRemark;
	}

	public void setRcdRemark(String rcdRemark) {
		this.rcdRemark = rcdRemark;
	}

	public Date getRcdOperateTime() {
		return rcdOperateTime;
	}

	public void setRcdOperateTime(Date rcdOperateTime) {
		this.rcdOperateTime = rcdOperateTime;
	}

	public Long getRcdOperateClerkId() {
		return rcdOperateClerkId;
	}

	public void setRcdOperateClerkId(Long rcdOperateClerkId) {
		this.rcdOperateClerkId = rcdOperateClerkId;
	}

	public String getRcdOperateClerkName() {
		return rcdOperateClerkName;
	}

	public void setRcdOperateClerkName(String rcdOperateClerkName) {
		this.rcdOperateClerkName = rcdOperateClerkName;
	}

	public int getRcdDocCount() {
		return rcdDocCount;
	}

	public void setRcdDocCount(int rcdDocCount) {
		this.rcdDocCount = rcdDocCount;
	}

	public int getRcdAttacchmentCount() {
		return rcdAttacchmentCount;
	}

	public void setRcdAttacchmentCount(int rcdAttacchmentCount) {
		this.rcdAttacchmentCount = rcdAttacchmentCount;
	}

	public String getAcvDocFileNo() {
		return acvDocFileNo;
	}

	public void setAcvDocFileNo(String acvDocFileNo) {
		this.acvDocFileNo = acvDocFileNo;
	}

	public Long getAcvDocPublishOrgId() {
		return acvDocPublishOrgId;
	}

	public void setAcvDocPublishOrgId(Long acvDocPublishOrgId) {
		this.acvDocPublishOrgId = acvDocPublishOrgId;
	}

	public String getAcvDocPublishOrgName() {
		return acvDocPublishOrgName;
	}

	public void setAcvDocPublishOrgName(String acvDocPublishOrgName) {
		this.acvDocPublishOrgName = acvDocPublishOrgName;
	}

	public Date getAcvDocPublishTime() {
		return acvDocPublishTime;
	}

	public void setAcvDocPublishTime(Date acvDocPublishTime) {
		this.acvDocPublishTime = acvDocPublishTime;
	}

	public Long getAcvClerkId() {
		return acvClerkId;
	}

	public void setAcvClerkId(Long acvClerkId) {
		this.acvClerkId = acvClerkId;
	}

	public String getAcvClerkName() {
		return acvClerkName;
	}

	public void setAcvClerkName(String acvClerkName) {
		this.acvClerkName = acvClerkName;
	}

	public String getAcvRemark() {
		return acvRemark;
	}

	public void setAcvRemark(String acvRemark) {
		this.acvRemark = acvRemark;
	}

	public Date getAcvOperateTime() {
		return acvOperateTime;
	}

	public void setAcvOperateTime(Date acvOperateTime) {
		this.acvOperateTime = acvOperateTime;
	}

	public Long getAcvOperateClerkId() {
		return acvOperateClerkId;
	}

	public void setAcvOperateClerkId(Long acvOperateClerkId) {
		this.acvOperateClerkId = acvOperateClerkId;
	}

	public String getAcvOperateClerkName() {
		return acvOperateClerkName;
	}

	public void setAcvOperateClerkName(String acvOperateClerkName) {
		this.acvOperateClerkName = acvOperateClerkName;
	}

	public int getAcvDocCount() {
		return acvDocCount;
	}

	public void setAcvDocCount(int acvDocCount) {
		this.acvDocCount = acvDocCount;
	}

	public int getAcvAttacchmentCount() {
		return acvAttacchmentCount;
	}

	public void setAcvAttacchmentCount(int acvAttacchmentCount) {
		this.acvAttacchmentCount = acvAttacchmentCount;
	}

	public Long getAcptClerkId() {
		return acptClerkId;
	}

	public void setAcptClerkId(Long acptClerkId) {
		this.acptClerkId = acptClerkId;
	}

	public String getAcptClerkName() {
		return acptClerkName;
	}

	public void setAcptClerkName(String acptClerkName) {
		this.acptClerkName = acptClerkName;
	}

	public Long getDaClerkId() {
		return daClerkId;
	}

	public void setDaClerkId(Long daClerkId) {
		this.daClerkId = daClerkId;
	}

	public String getDaClerkName() {
		return daClerkName;
	}

	public void setDaClerkName(String daClerkName) {
		this.daClerkName = daClerkName;
	}

	public Date getDaTime() {
		return daTime;
	}

	public void setDaTime(Date daTime) {
		this.daTime = daTime;
	}

	public Long getRcdClerkId() {
		return rcdClerkId;
	}

	public void setRcdClerkId(Long rcdClerkId) {
		this.rcdClerkId = rcdClerkId;
	}

	public String getRcdClerkName() {
		return rcdClerkName;
	}

	public void setRcdClerkName(String rcdClerkName) {
		this.rcdClerkName = rcdClerkName;
	}

	public Date getRcdTime() {
		return rcdTime;
	}

	public void setRcdTime(Date rcdTime) {
		this.rcdTime = rcdTime;
	}

	public Date getAcvTime() {
		return acvTime;
	}

	public void setAcvTime(Date acvTime) {
		this.acvTime = acvTime;
	}
	
	
}
