package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.opoo.ndao.Domain;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;
import cn.redflagsoft.base.util.CodeMapUtils;
import cn.redflagsoft.base.util.RiskEntryList;
import cn.redflagsoft.base.util.RiskMonitorableUtils;

import com.googlecode.jsonplugin.annotations.JSON;

@ObjectType(ObjectTypes.WORK)
public class Work extends VersionableBean implements Domain<Long>,
		RiskMonitorable, RFSObjectRef, RFSObjectAware,RFSEntityObject, BizInstance, DutyersInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3447506790993942567L;
	public static final int OBJECT_TYPE = ObjectTypes.WORK;
	
	public final static byte WORK_STATUS_OK = 0; //����
	public final static byte WORK_STATUS_WORK = 1;// �ڰ�
	public final static byte WORK_STATUS_HANG = 2;// ��ͣ
	public final static byte WORK_STATUS_PREPARE = 3;//Ԥ��
	public final static byte WORK_STATUS_TERMINATE = 9;// ����
	public final static byte WORK_STATUS_CANCEL = 10;// ȡ��
	public final static byte WORK_STATUS_STOP = 11;// ��ֹ
	public final static byte WORK_STATUS_AVOID = 12;//��� avoid
	public final static byte WORK_STATUS_WITHDRAW = 13;//����
	public final static byte WORK_STATUS_REJECT = 14;//����
	public final static byte WORK_STATUS_TRANSFER = 15;//ת��
	public final static byte WORK_STATUS_MERGE = 16;//�ϲ�
	
	public final static byte STATUS_���� = WORK_STATUS_OK;
	public final static byte STATUS_�ڰ� = WORK_STATUS_WORK;
	public final static byte STATUS_��ͣ = WORK_STATUS_HANG;
	public final static byte STATUS_Ԥ�� = WORK_STATUS_PREPARE;
	public final static byte STATUS_���� = WORK_STATUS_TERMINATE;
	public final static byte STATUS_ȡ�� = WORK_STATUS_CANCEL;
	public final static byte STATUS_��ֹ = WORK_STATUS_STOP;
	public final static byte STATUS_��� = WORK_STATUS_AVOID;
	public final static byte STATUS_���� = WORK_STATUS_WITHDRAW;
	public final static byte STATUS_���� = WORK_STATUS_REJECT;
	public final static byte STATUS_ת�� = WORK_STATUS_TRANSFER;
	public final static byte STATUS_�ϲ� = WORK_STATUS_MERGE;
	
	
	private Long sn;
	private String code;
	private Long taskSN;
	private byte proposerRole;
	private Long proposer;
	private Long clerkID;
	private byte processNum;
	private Date beginTime;
	private Date hangTime;
	private Date wakeTime;
	private Date endTime;
	private byte timeUnit;
	private short timeLimit;
	private short timeused;
	private short hangLimit;
	private short timeHang;
	private short delayLimit;
	private short timeDelay;
	private byte hangTimes;
	private byte hangUsed;
	private byte delayTimes;
	private byte delayUsed;
	private byte result;
	private String summary;
	private Long bizRoute;
	private Long bizTrack;	
//	private String remark;
	private int attachmentCount;
	
	
	private RiskEntryList riskEntries = new RiskEntryList();
	
	
	/**
	Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
����	NAME	VARCHAR2(200)	200		FALSE	FALSE	FALSE
Work��������	WORK_ITEM_NAME	VARCHAR2(200)	200		FALSE	FALSE	FALSE
��������ID	REF_OBJ_ID	NUMBER(19)	19		FALSE	FALSE	FALSE
������������	REF_OBJ_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
������������	REF_OBJ_TYPE	NUMBER(6)	6		FALSE	FALSE	FALSE
ְԱ����	CLERK_NAME	VARCHAR2(30)	30		FALSE	FALSE	FALSE
PROPOSER_NAME	PROPOSER_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
S0	S0	VARCHAR2(150)	150		FALSE	FALSE	FALSE
S1	S1	VARCHAR2(150)	150		FALSE	FALSE	FALSE
S2	S2	VARCHAR2(150)	150		FALSE	FALSE	FALSE
S3	S3	VARCHAR2(150)	150		FALSE	FALSE	FALSE
S4	S4	VARCHAR2(150)	150		FALSE	FALSE	FALSE
CREATOR	CREATOR	NUMBER(19)	19		FALSE	FALSE	FALSE
CTIME	CTIME	TIMESTAMP			FALSE	FALSE	FALSE
MODIFIER	MODIFIER	NUMBER(19)	19		FALSE	FALSE	FALSE
MTIME	MTIME	TIMESTAMP			FALSE	FALSE	FALSE
	 * 
	 */
	
	
	private String name;
	private String workItemName;
	private Long refObjectId;
	private String refObjectName;
	private Integer refObjectType;
	private String clerkName;
	private String proposerName;
	
	private String string0;
	private String string1;
	private String string2;
	private String string3;
	private String string4;
	
	
	private Long dutyEntityID;
	private String dutyEntityName;
	
	private Long dutyDepartmentID;
	private String dutyDepartmentName;//���β���
	
	private Long dutyerID;
	private String dutyerName;
	
	private Long dutyerLeader1Id;
	private String dutyerLeader1Name;// ��������
	
	private Long dutyerLeader2Id;
	private String dutyerLeader2Name;// �ֹ��쵼 
	
	public Work() {
	}

	/**
	 * Ψһ��ʾ
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * Ψһ��ʾ
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * ������
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * ����
	 * 
	 * ��������
	 * @return Long
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * @param taskSN
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}

	/**
	 * �����ɫ
	 * 
	 * �����˽�ɫ: 0 ������, 1 ������
	 * @return byte
	 */
	public byte getProposerRole() {
		return proposerRole;
	}

	/**
	 * @param proposerRole
	 */
	public void setProposerRole(byte proposerRole) {
		this.proposerRole = proposerRole;
	}

	/**
	 * ������
	 * 
	 * @return Long
	 */
	public Long getProposer() {
		return proposer;
	}

	/**
	 * @param proposer
	 */
	public void setProposer(Long proposer) {
		this.proposer = proposer;
	}

	/**
	 * ������
	 * 
	 * @return Long
	 */
	public Long getClerkID() {
		return clerkID;
	}

	/**
	 * @param clerkID
	 */
	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}
	
	/**
	 * ������
	 * 
	 * Ĭ��Ϊ0,��ʾ��������.
	 * @return byte
	 */
	public byte getProcessNum() {
		return processNum;
	}

	/**
	 * @param processNum
	 */
	public void setProcessNum(byte processNum) {
		this.processNum = processNum;
	}

	/**
	 * ��ʼʱ��
	 * 
	 * ��ʼ�����ʱ��.
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 */
	@JSON(format="yyyy-MM-dd")
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * ��ͣʱ��
	 * 
	 * ��ʼ��ͣ��ʱ��,���ڿ�ʼʱ����Ϊ��.
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getHangTime() {
		return hangTime;
	}

	/**
	 * @param hangTime
	 */
	@JSON(format="yyyy-MM-dd")
	public void setHangTime(Date hangTime) {
		this.hangTime = hangTime;
	}

	/**
	 * ����ʱ��
	 * 
	 * ��ʼ���ѵ�ʱ��.���ڿ�ʼʱ����Ϊ��.
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getWakeTime() {
		return wakeTime;
	}

	/**
	 * @param wakeTime
	 */
	@JSON(format="yyyy-MM-dd")
	public void setWakeTime(Date wakeTime) {
		this.wakeTime = wakeTime;
	}

	/**
	 * ����ʱ��
	 * 
	 * ��ɴ����ʱ��.���ڿ�ʼʱ����Ϊ��.
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 */
	@JSON(format="yyyy-MM-dd")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * ʱ�䵥λ
	 * 
	 * ʱ�䵥λ
	 * ����: 0 ��, 1 ��, 2 ��, 3 ��, 4 ��, 5 ʱ, 6 ��, 7 ��, 8 ����, 9 ��
	 * Ĭ��Ϊ0,��ʾ�޶�������.
	 * @return byte
	 */
	public byte getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @param timeUnit
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * �涨ʱ��
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return short
	 */
	public short getTimeLimit() {
		return timeLimit;
	}

	/**
	 * @param timeLimit
	 */
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * ʵ����ʱ
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return short
	 */
	public short getTimeUsed() {
		return timeused;
	}

	/**
	 * @param timeused
	 */
	public void setTimeUsed(short timeused) {
		this.timeused = timeused;
	}

	/**
	 * ��ͣʱ��
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return short
	 */
	public short getHangLimit() {
		return hangLimit;
	}

	/**
	 * @param hangLimit
	 */
	public void setHangLimit(short hangLimit) {
		this.hangLimit = hangLimit;
	}

	/**
	 * ��ͣ��ʱ
	 * 
	 * Ĭ��Ϊ0,��ʾδ��ͣ.
	 * @return short
	 */
	public short getTimeHang() {
		return timeHang;
	}

	/**
	 * @param timeHang
	 */
	public void setTimeHang(short timeHang) {
		this.timeHang = timeHang;
	}

	/**
	 * �ӳ�ʱ��
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return short
	 */
	public short getDelayLimit() {
		return delayLimit;
	}

	/**
	 * @param delayLimit
	 */
	public void setDelayLimit(short delayLimit) {
		this.delayLimit = delayLimit;
	}

	/**
	 * �ӳ���ʱ
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return short
	 */
	public short getTimeDelay() {
		return timeDelay;
	}

	/**
	 * @param timeDelay
	 */
	public void setTimeDelay(short timeDelay) {
		this.timeDelay = timeDelay;
	}

	/**
	 * ��ͣ����
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return byte
	 */
	public byte getHangTimes() {
		return hangTimes;
	}

	/**
	 * @param hangTimes
	 */
	public void setHangTimes(byte hangTimes) {
		this.hangTimes = hangTimes;
	}

	/**
	 * ʵ����ͣ
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return byte
	 */
	public byte getHangUsed() {
		return hangUsed;
	}

	/**
	 * @param hangUsed
	 */
	public void setHangUsed(byte hangUsed) {
		this.hangUsed = hangUsed;
	}

	/**
	 * �ӳٴ���
	 * 
	 * Ĭ��Ϊ0,��ʾδ��ͣ.
	 * @return byte 
	 */
	public byte getDelayTimes() {
		return delayTimes;
	}

	/**
	 * @param delayTimes
	 */
	public void setDelayTimes(byte delayTimes) {
		this.delayTimes = delayTimes;
	}

	/**
	 * ʵ���ӳ�
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return byte
	 */
	public byte getDelayUsed() {
		return delayUsed;
	}

	/**
	 * @param delayUsed
	 */
	public void setDelayUsed(byte delayUsed) {
		this.delayUsed = delayUsed;
	}

	/**
	 * ���
	 *
	 * Ĭ��Ϊ0,��ʾ���޽���.
	 * @return byte
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	/**
	 * ����
	 * 
	 * ��������.
	 * @return String
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * ҵ������
	 * 
	 * ��������,Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getBizRoute() {
		return bizRoute;
	}

	/**
	 * @param bizRoute
	 */
	public void setBizRoute(Long bizRoute) {
		this.bizRoute = bizRoute;
	}

	/**
	 * ҵ��켣
	 * 
	 * �����켣,Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getBizTrack() {
		return bizTrack;
	}

	/**
	 * @param bizTrack
	 */
	public void setBizTrack(Long bizTrack) {
		this.bizTrack = bizTrack;
	}

	public String getRemark() {
		return riskEntries == null ? null : riskEntries.toString();
	}
	
	public void setRemark(String remark) {
		this.riskEntries = RiskEntryList.valueOf(remark);
	}
	
	public void addRiskEntry(RiskEntry entry) {
		riskEntries.addRiskEntry(entry);
	}

	public RiskEntry getRiskEntryByObjectAttr(String attr) {
		if(riskEntries != null){
			return riskEntries.findRiskEntryByObjectAttr(attr);
		}
		return null;
	}

	public int getObjectType() {
		return OBJECT_TYPE;
	}

	public List<RiskEntry> getRiskEntries() {
		return riskEntries;
	}

	public void setRiskEntries(List<RiskEntry> entries) {
		if(entries != null){
			riskEntries = new RiskEntryList(entries);
		}
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
	
	
	/**
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



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkItemName() {
		return workItemName;
	}

	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}

	public Long getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

	public String getRefObjectName() {
		return refObjectName;
	}

	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}

	public Integer getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(Integer refObjectType) {
		this.refObjectType = refObjectType;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getString0() {
		return string0;
	}

	public void setString0(String string0) {
		this.string0 = string0;
	}

	public String getString1() {
		return string1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getString2() {
		return string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getString3() {
		return string3;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}

	public String getString4() {
		return string4;
	}

	public void setString4(String string4) {
		this.string4 = string4;
	}

	public String toString(){
		return new ToStringBuilder(this)
		.append("sn", getSn())
		.append("type", getType())
		.toString();
	}

	public void setObject(RFSObjectable object){
		if(object != null && object.getId() != null){
			this.setRefObjectId(object.getId());
			this.setRefObjectType(object.getObjectType());
			this.setRefObjectName(object.getName());
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#getRiskValue(java.lang.String)
	 */
	public BigDecimal getRiskValue(String objectAttr) {
		// ��ȷ��ȡֵ����ʹ�÷���Ч�ʸ�
		if ("timeUsed".equalsIgnoreCase(objectAttr)) {
			return new BigDecimal(getTimeUsed());
		} else if ("timeHang".equalsIgnoreCase(objectAttr)) {
			return new BigDecimal(getTimeHang());
		} else if ("timeDelay".equalsIgnoreCase(objectAttr)) {
			return new BigDecimal(getTimeDelay());
		}

		// ʹ�÷���
		return RiskMonitorableUtils.getRiskValue(this, objectAttr);
	}
	
	public String getWorkStatusName() {
		return CodeMapUtils.getCodeName(Work.class, "STATUS", getStatus());
	}

	/**
	 * @return the dutyEntityID
	 */
	public Long getDutyEntityID() {
		return dutyEntityID;
	}

	/**
	 * @param dutyEntityID the dutyEntityID to set
	 */
	public void setDutyEntityID(Long dutyEntityID) {
		this.dutyEntityID = dutyEntityID;
	}

	/**
	 * @return the dutyEntityName
	 */
	public String getDutyEntityName() {
		return dutyEntityName;
	}

	/**
	 * @param dutyEntityName the dutyEntityName to set
	 */
	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}

	/**
	 * @return the dutyDepartmentID
	 */
	public Long getDutyDepartmentID() {
		return dutyDepartmentID;
	}

	/**
	 * @param dutyDepartmentID the dutyDepartmentID to set
	 */
	public void setDutyDepartmentID(Long dutyDepartmentID) {
		this.dutyDepartmentID = dutyDepartmentID;
	}

	/**
	 * @return the dutyDepartmentName
	 */
	public String getDutyDepartmentName() {
		return dutyDepartmentName;
	}

	/**
	 * @param dutyDepartmentName the dutyDepartmentName to set
	 */
	public void setDutyDepartmentName(String dutyDepartmentName) {
		this.dutyDepartmentName = dutyDepartmentName;
	}

	/**
	 * @return the dutyerID
	 */
	public Long getDutyerID() {
		return dutyerID;
	}

	/**
	 * @param dutyerID the dutyerID to set
	 */
	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}

	/**
	 * @return the dutyerName
	 */
	public String getDutyerName() {
		return dutyerName;
	}

	/**
	 * @param dutyerName the dutyerName to set
	 */
	public void setDutyerName(String dutyerName) {
		this.dutyerName = dutyerName;
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

	public String getStatusName(){
		return CodeMapUtils.getCodeName(Work.class, "STATUS", getStatus());
	}
}