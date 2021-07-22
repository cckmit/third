/*
 * $Id: ObjectAdmin.java 6310 2013-10-28 02:58:50Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean.commons;

import java.util.Date;

import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.VersionableBean;

/**
 * 对象管理/操作日志。
 * <pre>
 * 	private Long archiveId;		//归档记录ID
	private Long unarchiveId;	//退档记录ID
	private Long publishId;		//发布记录ID
	private Long pauseId;		//暂停记录ID
	private Long wakeId;		//恢复记录ID
	private Long cancelId;		//取消记录ID
	private Long transId;		//转交记录ID
	private Long shelveId;		//搁置（暂缓）记录ID
	private Long deleteId;		//作废、逻辑删除记录ID
	private Long finishId;		//结束记录ID
	</pre>
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class ObjectAdmin extends VersionableBean implements RFSItemable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1116052218294068172L;
	private Long clerkId;				//操作者ID
	private String clerkName;			//操作者姓名
	private Long orgId;					//涉及单位ID	
	private String orgName;				//涉及单位名称
	
	private String fileNo;				//涉及文号
	private Long fileId;				//涉及文件ID
	
	private int objType;				//对象类型
	private long objId;					//对象ID
	private String objName;				//对象名称
	
	private Date operateTime;			//操作时间
	private String operateCommand;		//操作指令，操作命令
	private String operateDesc; 		//操作描述
	private String operateMode;			//操作模式	
	
	private String description;			//说明
	
	private Date approveTime;			//批准时间
	private Long approveOrgId;			//批准单位ID	
	private String approveOrgName;		//批准单位名称
	private Long approveClerkId;		//批准人ID
	private String approveClerkName;	//批准人姓名
	
	private String string0;				//备注字串1
	private String string1;				//备注字串2
	private String string2;				//备注字串3
	private String string3;				//备注字串4
	private String string4;				//备注字串5
	
	/**
	 * @return the clerkId
	 */
	public Long getClerkId() {
		return clerkId;
	}
	/**
	 * @param clerkId the clerkId to set
	 */
	public void setClerkId(Long clerkId) {
		this.clerkId = clerkId;
	}
	/**
	 * @return the clerkName
	 */
	public String getClerkName() {
		return clerkName;
	}
	/**
	 * @param clerkName the clerkName to set
	 */
	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}
	/**
	 * @return the fileNo
	 */
	public String getFileNo() {
		return fileNo;
	}
	/**
	 * @param fileNo the fileNo to set
	 */
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	/**
	 * @return the fileId
	 */
	public Long getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the objType
	 */
	public int getObjType() {
		return objType;
	}
	/**
	 * @param objType the objType to set
	 */
	public void setObjType(int objType) {
		this.objType = objType;
	}
	/**
	 * @return the objId
	 */
	public long getObjId() {
		return objId;
	}
	/**
	 * @param objId the objId to set
	 */
	public void setObjId(long objId) {
		this.objId = objId;
	}
	/**
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}
	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}
	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	/**
	 * @return the operateCommand
	 */
	public String getOperateCommand() {
		return operateCommand;
	}
	/**
	 * @param operateCommand the operateCommand to set
	 */
	public void setOperateCommand(String operateCommand) {
		this.operateCommand = operateCommand;
	}
	/**
	 * @return the operateDesc
	 */
	public String getOperateDesc() {
		return operateDesc;
	}
	/**
	 * @param operateDesc the operateDesc to set
	 */
	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	/**
	 * @return the string0
	 */
	public String getString0() {
		return string0;
	}
	/**
	 * @param string0 the string0 to set
	 */
	public void setString0(String string0) {
		this.string0 = string0;
	}
	/**
	 * @return the string1
	 */
	public String getString1() {
		return string1;
	}
	/**
	 * @param string1 the string1 to set
	 */
	public void setString1(String string1) {
		this.string1 = string1;
	}
	/**
	 * @return the string2
	 */
	public String getString2() {
		return string2;
	}
	/**
	 * @param string2 the string2 to set
	 */
	public void setString2(String string2) {
		this.string2 = string2;
	}
	/**
	 * @return the string3
	 */
	public String getString3() {
		return string3;
	}
	/**
	 * @param string3 the string3 to set
	 */
	public void setString3(String string3) {
		this.string3 = string3;
	}
	/**
	 * @return the string4
	 */
	public String getString4() {
		return string4;
	}
	/**
	 * @param string4 the string4 to set
	 */
	public void setString4(String string4) {
		this.string4 = string4;
	}
	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the operateMode
	 */
	public String getOperateMode() {
		return operateMode;
	}
	/**
	 * @param operateMode the operateMode to set
	 */
	public void setOperateMode(String operateMode) {
		this.operateMode = operateMode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}
	/**
	 * @param approveTime the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	/**
	 * @return the approveOrgId
	 */
	public Long getApproveOrgId() {
		return approveOrgId;
	}
	/**
	 * @param approveOrgId the approveOrgId to set
	 */
	public void setApproveOrgId(Long approveOrgId) {
		this.approveOrgId = approveOrgId;
	}
	/**
	 * @return the approveOrgName
	 */
	public String getApproveOrgName() {
		return approveOrgName;
	}
	/**
	 * @param approveOrgName the approveOrgName to set
	 */
	public void setApproveOrgName(String approveOrgName) {
		this.approveOrgName = approveOrgName;
	}
	/**
	 * @return the approveClerkId
	 */
	public Long getApproveClerkId() {
		return approveClerkId;
	}
	/**
	 * @param approveClerkId the approveClerkId to set
	 */
	public void setApproveClerkId(Long approveClerkId) {
		this.approveClerkId = approveClerkId;
	}
	/**
	 * @return the approveClerkName
	 */
	public String getApproveClerkName() {
		return approveClerkName;
	}
	/**
	 * @param approveClerkName the approveClerkName to set
	 */
	public void setApproveClerkName(String approveClerkName) {
		this.approveClerkName = approveClerkName;
	}
}
