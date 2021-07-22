/*
 * $Id: RFSObject.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Domain;

import cn.redflagsoft.base.util.CodeMapUtils;
import cn.redflagsoft.base.util.ObjectTypeUtils;
import cn.redflagsoft.base.util.RiskEntryList;
import cn.redflagsoft.base.util.RiskMonitorableUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * ��������
 * ע�⣬�������ʵ��getObjectType������ͨ�����þ�̬�ֶ� OBJECT_TYPE��ͨ��ע��@ObjectType��
 * ���ö������͡� 
 * 
 * 
 * @author Alex Lin
 *
 */
public class RFSObject extends VersionableBean implements Domain<Long>, RiskMonitorable, RFSObjectable, Cloneable, DutyersInfo{
	//private static final Log log = LogFactory.getLog(RFSObject.class);
	private static final long serialVersionUID = -5140188917908034882L;
	//private short category;
	public static final byte MANAGER_TYPE_ORG = 1;
	public static final byte MANAGER_TYPE_DEPARTMENT = 2;
	public static final byte MANAGER_TYPE_PEOPLE = 3;
	
	public static final byte MANAGE_MODE_���� = 0;
	public static final byte MANAGE_MODE_ά�� = 1;
	public static final byte MANAGE_MODE_��� = 2;
	
	private Long systemID;
	private int lifeStage;
	private Long manager;
	private byte managerType;
	private Byte riskGrade;
	private short tag;
	private byte status;
	private String name;
	private Long activeMatter;
	private Long activeTaskSN;
	private Long activeDutyerID;

	private Long dutyClerkID;									//������
	private String dutyClerkName;			
	private Long dutyDepartmentID;								//���β���
	private String dutyDepartmentName;
	private Long dutyEntityID;									//���ε�λ
	private String dutyEntityName;

	private RiskEntryList riskEntries = new RiskEntryList();
	
	private int attachmentCount;
	
	//���ֶβ�һ���ڻ�����ӳ��
	private byte bizStatus;//ҵ��״̬��һ����
	
	private Long dutyerLeader1Id; 								// ֱ���ϼ����
	private String dutyerLeader1Name; 							// ֱ���ϼ�����
	private Long dutyerLeader2Id; 								// �ֹ��쵼���
	private String dutyerLeader2Name; 							// �ֹ��쵼����
	
	
	private byte state = State.STATE_����;			//����״̬������״̬
	
	private Long cancelId;		//ȡ����¼ID
	private Long archiveId;		//�鵵��¼ID
	private Long deleteId;		//���ϡ��߼�ɾ����¼ID
	
	private Long unarchiveId;	//�˵���¼ID
	private Long publishId;		//������¼ID
	private Long pauseId;		//��ͣ��¼ID
	private Long wakeId;		//�ָ���¼ID
	private Long transId;		//ת����¼ID
	private Long shelveId;		//���ã��ݻ�����¼ID
	private Long finishId;		//������¼ID
	
	
	private byte manageMode = MANAGE_MODE_����;
	
	
	public RFSObject() {
		//this.getObjectType();
	}

	/**
	 * ����
	 * 
	 * 1������ͨ�ö���;1�������������
	 * @return short
	 */
	//public short getCategory() {
	//	return category;
	//}

	/**
	 * @param category
	 */
	//public void setCategory(short category) {
	//	this.category = category;
	//}


	/**
	 * ϵͳID
	 * 
	 * Ϊ���ϵͳ�Ĵ���������.Ĭ��0,��ʾԭʼ����.�����뷽ʽ��ʶ��ͬ��ϵͳ,
	 * ����:1-99��ʾ99����,100-9900��ʾ99����,��9901����֪�ǵ�99�еĵ�1����.
	 * @return Long
	 */
	public Long getSystemID() {
		return systemID;
	}

	/**
	 * @param systemID 
	 */
	public void setSystemID(Long systemID) {
		this.systemID = systemID;
	}

	/**
	 * @return the lifeStage
	 */
	public int getLifeStage() {
		return lifeStage;
	}

	/**
	 * @param lifeStage the lifeStage to set
	 */
	public void setLifeStage(int lifeStage) {
		this.lifeStage = lifeStage;
	}

	/**
	 * @return the manager
	 */
	public Long getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Long manager) {
		this.manager = manager;
	}

	/**
	 * @return the managerType
	 */
	public byte getManagerType() {
		return managerType;
	}

	/**
	 * @param managerType the managerType to set
	 */
	public void setManagerType(byte managerType) {
		this.managerType = managerType;
	}
	
	/**
	 * ������־��0�Զ����� 
	 * @return
	 */
	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	/**
	 * ��Ӧ Risk grade �ֶ�
	 * 
	 * @return byte
	 */
	public Byte getRiskGrade() {
		if(riskGrade == null) return 0;
		return riskGrade;
	}

	public void setRiskGrade(Byte riskGrade) {
		this.riskGrade = riskGrade;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getActiveMatter() {
		return activeMatter;
	}

	public void setActiveMatter(Long activeMatter) {
		this.activeMatter = activeMatter;
	}

	public Long getActiveTaskSN() {
		return activeTaskSN;
	}

	public void setActiveTaskSN(Long activeTaskSN) {
		this.activeTaskSN = activeTaskSN;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#addRiskEntry(cn.redflagsoft.base.bean.RiskEntry)
	 */
	public void addRiskEntry(RiskEntry entry) {
		riskEntries.addRiskEntry(entry);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#getObjectType()
	 */
	public int getObjectType() {
		try{
			return ObjectTypeUtils.getObjectType(getClass());
		}catch(Throwable e){
			Log log = LogFactory.getLog(getClass());
			if(log.isDebugEnabled()){
				log.debug("�޷���ȡObjectType(" + getClass().getName() + "): " + e.getMessage());
			}
			return 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#getRiskEntries()
	 */
	@JSON(serialize=false)
	public List<RiskEntry> getRiskEntries() {
		return riskEntries;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#setRiskEntries(java.util.List)
	 */
	public void setRiskEntries(List<RiskEntry> entries) {
		if(entries != null){
			riskEntries = new RiskEntryList(entries);
		}
	}

	/**
	 * @return the riskEntryString
	 */
	public String getRiskEntryDescription() {
		return riskEntries == null ? null : riskEntries.toString();
	}

	/**
	 * @param riskEntryString the riskEntryString to set
	 */
	public void setRiskEntryDescription(String riskEntryDescription) {
		riskEntries = RiskEntryList.valueOf(riskEntryDescription);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#findRiskEntryByObjectAttr(java.lang.String)
	 */
	public RiskEntry getRiskEntryByObjectAttr(String attr) {
		if(riskEntries != null){
			return riskEntries.findRiskEntryByObjectAttr(attr);
		}
		return null;
	}
	
	public void removeRiskEntry(RiskEntry riskEntry) {
		if(riskEntries != null && riskEntries.contains(riskEntry)) {
			riskEntries.remove(riskEntry);
		}
	}

	public void removeAllRiskEntries() {
		if(riskEntries != null) {
			riskEntries = null;
		}
	}


	public BigDecimal getRiskValue(String objectAttr) {
		//ʹ�÷�����ƣ�Ч�ʲ��ߣ�����������ʵ�ָ��Ӹ�Ч�ļ��㷽��
		return RiskMonitorableUtils.getRiskValue(this, objectAttr);
	}

	/**
	 * ��ǰ������ID
	 * @return the activeDutyerID
	 */
	public Long getActiveDutyerID() {
		return activeDutyerID;
	}

	/**
	 * ��ǰ������ID
	 * @param activeDutyerID the activeDutyerID to set
	 */
	public void setActiveDutyerID(Long activeDutyerID) {
		this.activeDutyerID = activeDutyerID;
	}

	/**
	 * ������ID
	 * @return
	 */
	public Long getDutyClerkID() {
		return dutyClerkID;
	}

	public void setDutyClerkID(Long dutyClerkID) {
		this.dutyClerkID = dutyClerkID;
	}
	
	/**
	 * ����������
	 * @return
	 */
	public String getDutyClerkName() {
		return dutyClerkName;
	}

	public void setDutyClerkName(String dutyClerkName) {
		this.dutyClerkName = dutyClerkName;
	}

	/**
	 * ���β���ID
	 * @return
	 */
	public Long getDutyDepartmentID() {
		return dutyDepartmentID;
	}

	public void setDutyDepartmentID(Long dutyDepartmentID) {
		this.dutyDepartmentID = dutyDepartmentID;
	}

	public String getDutyDepartmentName() {
		return dutyDepartmentName;
	}

	public void setDutyDepartmentName(String dutyDepartmentName) {
		this.dutyDepartmentName = dutyDepartmentName;
	}

	/**
	 * ��������
	 * @return the attachmentCount
	 */
	public int getAttachmentCount() {
		return attachmentCount;
	}

	/**
	 * @param attachmentCount the attachmentCount to set
	 */
	public void setAttachmentCount(int attachmentCount) {
		this.attachmentCount = attachmentCount;
	}

	public Long getDutyEntityID() {
		return dutyEntityID;
	}

	public void setDutyEntityID(Long dutyEntityID) {
		this.dutyEntityID = dutyEntityID;
	}

	public String getDutyEntityName() {
		return dutyEntityName;
	}

	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}

//	private boolean isBizStatusGet = false;
	/**
	 * @return the bizStatus
	 */
	public byte getBizStatus() {
//		if(!isBizStatusGet){
//			isBizStatusGet = true;
//			Log log = LogFactory.getLog(getClass());
//			if(log.isDebugEnabled()){
//				log.warn(this + " ֱ�ӵ�����RFSObject��bizStatus��ͨ��Ӧ����������override������Ե�getBizStatus()��");
//			}
//		}
		return bizStatus;
	}

//	private boolean isBizStatusSet = false;
	/**
	 * @param bizStatus the bizStatus to set
	 */
	public void setBizStatus(byte bizStatus) {
//		if(!isBizStatusSet){
//			isBizStatusSet = true;
//			Log log = LogFactory.getLog(getClass());
//			if(log.isDebugEnabled()){
//				log.warn(this + " ֱ�ӵ�����RFSObject��bizStatus��ͨ��Ӧ����������override������Ե�setBizStatus(byte)��");
//			}
//		}
		
		this.bizStatus = bizStatus;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStatusName(){
		return CodeMapUtils.getCodeName(ArchivingStatus.class, "STATUS", getStatus());
	}

	
	
	/**
	 * @return the dutyerLeader1Id
	 */
	public Long getDutyerLeader1Id() {
		return dutyerLeader1Id;
	}

	/**
	 * @param dutyerLeader1Id the dutyerLeader1Id to set
	 */
	public void setDutyerLeader1Id(Long dutyerLeader1Id) {
		this.dutyerLeader1Id = dutyerLeader1Id;
	}

	/**
	 * @return the dutyerLeader1Name
	 */
	public String getDutyerLeader1Name() {
		return dutyerLeader1Name;
	}

	/**
	 * @param dutyerLeader1Name the dutyerLeader1Name to set
	 */
	public void setDutyerLeader1Name(String dutyerLeader1Name) {
		this.dutyerLeader1Name = dutyerLeader1Name;
	}

	/**
	 * @return the dutyerLeader2Id
	 */
	public Long getDutyerLeader2Id() {
		return dutyerLeader2Id;
	}

	/**
	 * @param dutyerLeader2Id the dutyerLeader2Id to set
	 */
	public void setDutyerLeader2Id(Long dutyerLeader2Id) {
		this.dutyerLeader2Id = dutyerLeader2Id;
	}

	/**
	 * @return the dutyerLeader2Name
	 */
	public String getDutyerLeader2Name() {
		return dutyerLeader2Name;
	}

	/**
	 * @param dutyerLeader2Name the dutyerLeader2Name to set
	 */
	public void setDutyerLeader2Name(String dutyerLeader2Name) {
		this.dutyerLeader2Name = dutyerLeader2Name;
	}

	/**
	 * @return the state
	 */
	public byte getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(byte state) {
		this.state = state;
	}
	
	public String getStateName(){
		return CodeMapUtils.getCodeName(State.class, "STATE", getState());
	}

	/**
	 * @return the archiveId
	 */
	public Long getArchiveId() {
		return archiveId;
	}

	/**
	 * @param archiveId the archiveId to set
	 */
	public void setArchiveId(Long archiveId) {
		this.archiveId = archiveId;
	}

	/**
	 * @return the unarchiveId
	 */
	public Long getUnarchiveId() {
		return unarchiveId;
	}

	/**
	 * @param unarchiveId the unarchiveId to set
	 */
	public void setUnarchiveId(Long unarchiveId) {
		this.unarchiveId = unarchiveId;
	}

	/**
	 * @return the publishId
	 */
	public Long getPublishId() {
		return publishId;
	}

	/**
	 * @param publishId the publishId to set
	 */
	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	/**
	 * @return the pauseId
	 */
	public Long getPauseId() {
		return pauseId;
	}

	/**
	 * @param pauseId the pauseId to set
	 */
	public void setPauseId(Long pauseId) {
		this.pauseId = pauseId;
	}

	/**
	 * @return the wakeId
	 */
	public Long getWakeId() {
		return wakeId;
	}

	/**
	 * @param wakeId the wakeId to set
	 */
	public void setWakeId(Long wakeId) {
		this.wakeId = wakeId;
	}

	/**
	 * @return the cancelId
	 */
	public Long getCancelId() {
		return cancelId;
	}

	/**
	 * @param cancelId the cancelId to set
	 */
	public void setCancelId(Long cancelId) {
		this.cancelId = cancelId;
	}

	/**
	 * @return the transId
	 */
	public Long getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(Long transId) {
		this.transId = transId;
	}

	/**
	 * @return the shelveId
	 */
	public Long getShelveId() {
		return shelveId;
	}

	/**
	 * @param shelveId the shelveId to set
	 */
	public void setShelveId(Long shelveId) {
		this.shelveId = shelveId;
	}

	/**
	 * @return the deleteId
	 */
	public Long getDeleteId() {
		return deleteId;
	}

	/**
	 * @param deleteId the deleteId to set
	 */
	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}

	/**
	 * @return the finishId
	 */
	public Long getFinishId() {
		return finishId;
	}

	/**
	 * @param finishId the finishId to set
	 */
	public void setFinishId(Long finishId) {
		this.finishId = finishId;
	}

	/**
	 * @param riskEntries the riskEntries to set
	 */
	public void setRiskEntries(RiskEntryList riskEntries) {
		this.riskEntries = riskEntries;
	}

	/**
	 * @return the manageMode
	 */
	public byte getManageMode() {
		return manageMode;
	}

	/**
	 * @param manageMode the manageMode to set
	 */
	public void setManageMode(byte manageMode) {
		this.manageMode = manageMode;
	}
	
	public String getManageModeName(){
		return CodeMapUtils.getCodeName(RFSObject.class, "MANAGE_MODE", getManageMode());
	}
	
	public Object copy() {
		try {
			return (RFSObject) clone();
		} catch (CloneNotSupportedException e) {
			return new java.lang.RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.DutyersInfo#getDutyerID()
	 * @see #getDutyClerkID()
	 */
	public Long getDutyerID() {
		return getDutyClerkID();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.DutyersInfo#getDutyerName()
	 * @see #getDutyClerkName()
	 */
	public String getDutyerName() {
		return getDutyClerkName();
	}
}