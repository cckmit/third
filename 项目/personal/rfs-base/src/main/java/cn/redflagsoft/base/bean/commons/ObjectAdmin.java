/*
 * $Id: ObjectAdmin.java 6310 2013-10-28 02:58:50Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean.commons;

import java.util.Date;

import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.VersionableBean;

/**
 * �������/������־��
 * <pre>
 * 	private Long archiveId;		//�鵵��¼ID
	private Long unarchiveId;	//�˵���¼ID
	private Long publishId;		//������¼ID
	private Long pauseId;		//��ͣ��¼ID
	private Long wakeId;		//�ָ���¼ID
	private Long cancelId;		//ȡ����¼ID
	private Long transId;		//ת����¼ID
	private Long shelveId;		//���ã��ݻ�����¼ID
	private Long deleteId;		//���ϡ��߼�ɾ����¼ID
	private Long finishId;		//������¼ID
	</pre>
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class ObjectAdmin extends VersionableBean implements RFSItemable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1116052218294068172L;
	private Long clerkId;				//������ID
	private String clerkName;			//����������
	private Long orgId;					//�漰��λID	
	private String orgName;				//�漰��λ����
	
	private String fileNo;				//�漰�ĺ�
	private Long fileId;				//�漰�ļ�ID
	
	private int objType;				//��������
	private long objId;					//����ID
	private String objName;				//��������
	
	private Date operateTime;			//����ʱ��
	private String operateCommand;		//����ָ���������
	private String operateDesc; 		//��������
	private String operateMode;			//����ģʽ	
	
	private String description;			//˵��
	
	private Date approveTime;			//��׼ʱ��
	private Long approveOrgId;			//��׼��λID	
	private String approveOrgName;		//��׼��λ����
	private Long approveClerkId;		//��׼��ID
	private String approveClerkName;	//��׼������
	
	private String string0;				//��ע�ִ�1
	private String string1;				//��ע�ִ�2
	private String string2;				//��ע�ִ�3
	private String string3;				//��ע�ִ�4
	private String string4;				//��ע�ִ�5
	
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
