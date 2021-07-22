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
 * 超级对象。
 * 注意，子类必须实现getObjectType方法，通过设置静态字段 OBJECT_TYPE或通过注释@ObjectType来
 * 设置对象类型。 
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
	
	public static final byte MANAGE_MODE_正常 = 0;
	public static final byte MANAGE_MODE_维护 = 1;
	public static final byte MANAGE_MODE_封存 = 2;
	
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

	private Long dutyClerkID;									//责任人
	private String dutyClerkName;			
	private Long dutyDepartmentID;								//责任部门
	private String dutyDepartmentName;
	private Long dutyEntityID;									//责任单位
	private String dutyEntityName;

	private RiskEntryList riskEntries = new RiskEntryList();
	
	private int attachmentCount;
	
	//该字段不一定在基类中映射
	private byte bizStatus;//业务状态，一般在
	
	private Long dutyerLeader1Id; 								// 直属上级编号
	private String dutyerLeader1Name; 							// 直属上级姓名
	private Long dutyerLeader2Id; 								// 分管领导编号
	private String dutyerLeader2Name; 							// 分管领导姓名
	
	
	private byte state = State.STATE_正常;			//运行状态，辅助状态
	
	private Long cancelId;		//取消记录ID
	private Long archiveId;		//归档记录ID
	private Long deleteId;		//作废、逻辑删除记录ID
	
	private Long unarchiveId;	//退档记录ID
	private Long publishId;		//发布记录ID
	private Long pauseId;		//暂停记录ID
	private Long wakeId;		//恢复记录ID
	private Long transId;		//转交记录ID
	private Long shelveId;		//搁置（暂缓）记录ID
	private Long finishId;		//结束记录ID
	
	
	private byte manageMode = MANAGE_MODE_正常;
	
	
	public RFSObject() {
		//this.getObjectType();
	}

	/**
	 * 分类
	 * 
	 * 1万描述通用对象;1万描述领域对象
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
	 * 系统ID
	 * 
	 * 为监管系统的大联网保留.默放0,表示原始数据.以掩码方式标识不同的系统,
	 * 例如:1-99表示99个区,100-9900表示99个市,则9901即可知是第99市的第1个区.
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
	 * 创建标志，0自动创建 
	 * @return
	 */
	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	/**
	 * 对应 Risk grade 字段
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
				log.debug("无法获取ObjectType(" + getClass().getName() + "): " + e.getMessage());
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
		//使用反射机制，效率不高，可在子类中实现更加高效的计算方法
		return RiskMonitorableUtils.getRiskValue(this, objectAttr);
	}

	/**
	 * 当前责任人ID
	 * @return the activeDutyerID
	 */
	public Long getActiveDutyerID() {
		return activeDutyerID;
	}

	/**
	 * 当前责任人ID
	 * @param activeDutyerID the activeDutyerID to set
	 */
	public void setActiveDutyerID(Long activeDutyerID) {
		this.activeDutyerID = activeDutyerID;
	}

	/**
	 * 责任人ID
	 * @return
	 */
	public Long getDutyClerkID() {
		return dutyClerkID;
	}

	public void setDutyClerkID(Long dutyClerkID) {
		this.dutyClerkID = dutyClerkID;
	}
	
	/**
	 * 责任人姓名
	 * @return
	 */
	public String getDutyClerkName() {
		return dutyClerkName;
	}

	public void setDutyClerkName(String dutyClerkName) {
		this.dutyClerkName = dutyClerkName;
	}

	/**
	 * 责任部门ID
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
	 * 附件数量
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
//				log.warn(this + " 直接调用了RFSObject的bizStatus，通常应该在子类中override这个属性的getBizStatus()。");
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
//				log.warn(this + " 直接调用了RFSObject的bizStatus，通常应该在子类中override这个属性的setBizStatus(byte)。");
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