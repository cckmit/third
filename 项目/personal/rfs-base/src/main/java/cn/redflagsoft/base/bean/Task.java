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
import cn.redflagsoft.base.util.TimeUnit;

import com.googlecode.jsonplugin.annotations.JSON;

@ObjectType(Task.OBJECT_TYPE)
public class Task extends VersionableBean implements Domain<Long>, RiskMonitorable, 
	RFSObjectRef, RFSObjectAware, RFSEntityObject, BizInstance, DutyersInfo{
	private static final long serialVersionUID = 451156510609790427L;
	public static final int OBJECT_TYPE = ObjectTypes.TASK;	
	
	
	/**
	 * �ɼ���Χ	Visibility	Tinyint	
	 * 0 δ���޶���
	 * 100 ����ҵ�����ѯ����ϵͳʵ���û���ѯ���ɼ���
	 * 200 ��ҵ��״̬ͼ��ɼ��ԡ���ѯ�ɼ� ��99999 ������
	 * ȱʡΪ100��
	 */
	public static final int VISIBILITY_UNQUALIFIED = 0;// δ���޶�
	public static final int VISIBILITY_GENERAL = 100; // ����ҵ����
	public static final int VISIBILITY_ICON = 200; // ҵ��״̬ͼ��ɼ���
	public static final int VISIBILITY_OTHERS = 99999;// ����
	
	/**
	 * ��ͬ�� VISIBILITY_UNQUALIFIED
	 */
	public static final int VISIBLE_δ���޶� = VISIBILITY_UNQUALIFIED;
	/**
	 * ��ͬ��VISIBILITY_GENERAL
	 */
	public static final int VISIBLE_����ҵ���� = VISIBILITY_GENERAL;
	/**
	 * ��ͬ��VISIBILITY_ICON
	 */
	public static final int VISIBLE_ҵ��״̬ͼ��ɼ��� = VISIBILITY_ICON;
	/**
	 * ��ͬ��VISIBILITY_OTHERS
	 */
	public static final int VISIBLE_���� = VISIBILITY_OTHERS;
	
	
	public final static byte TASK_STATUS_OK = 0; //����
	public final static byte TASK_STATUS_WORK = 1;// �ڰ�
	public final static byte TASK_STATUS_HANG = 2;// ��ͣ
	public final static byte TASK_STATUS_PREPARE = 3;//Ԥ��
	public final static byte TASK_STATUS_TERMINATE = 9;// ����
	public final static byte TASK_STATUS_CANCEL = 10;// ȡ��
	public final static byte TASK_STATUS_STOP = 11;// ��ֹ
	public final static byte TASK_STATUS_AVOID = 12;//��� avoid
	public final static byte TASK_STATUS_WITHDRAW = 13;//����
	public final static byte TASK_STATUS_REJECT = 14;//����
	public final static byte TASK_STATUS_TRANSFER = 15;//ת��
	public final static byte TASK_STATUS_MERGE = 16;//�ϲ�
	
	public final static byte STATUS_���� = TASK_STATUS_OK;
	public final static byte STATUS_�ڰ� = TASK_STATUS_WORK;
	public final static byte STATUS_��ͣ = TASK_STATUS_HANG;
	public final static byte STATUS_Ԥ�� = TASK_STATUS_PREPARE;
	public final static byte STATUS_���� = TASK_STATUS_TERMINATE;
	public final static byte STATUS_ȡ�� = TASK_STATUS_CANCEL;
	public final static byte STATUS_��ֹ = TASK_STATUS_STOP;
	public final static byte STATUS_��� = TASK_STATUS_AVOID;
	public final static byte STATUS_���� = TASK_STATUS_WITHDRAW;
	public final static byte STATUS_���� = TASK_STATUS_REJECT;
	public final static byte STATUS_ת�� = TASK_STATUS_TRANSFER;
	public final static byte STATUS_�ϲ� = TASK_STATUS_MERGE;
	
	
	private Long sn;			//����
	private String code;		//����
	private byte objectNum;		//������
	private byte affairNum;		//�±���
	private byte matterNum;		//������
	private Long sponseEntity;	//����λ
	private int sponser;		//������
	private int applicant;		//������
	private Long brokeEntity;	//����λ
	private int broker;			//������
	private byte proposerRole;	//�����ɫ
	private Long entityID;		//�а쵥λ
	private Long departID;		//�а첿��
	private Long clerkID;		//��ǰ������
	private Long lastClerkID; 	//�ƽ���
	private Date lastTime;		//�ƽ�ʱ��
	private Date beginTime;		//��ʼʱ��
	private Date hangTime;		//��ͣʱ��
	private Date wakeTime;		//����ʱ��
	private Date endTime;		//����ʱ��
	//ʱ�䵥λ ʱ�䵥λ�����壺0�ޣ�1�꣬2�£�3�ܣ�4�գ�5�죬6ʱ��7�֣�8�룬9���룬Ĭ��Ϊ4���գ���Ĭ��Ϊ0����ʾ�޶������ԡ�
	private byte timeUnit = (byte) TimeUnit.DAY.getFlag();		
	private short timeLimit;	//�涨ʱ��
	private short timeused;		//ʵ����ʱ
	private short hangLimit;	//��ͣʱ��
	private short timeHang;		//��ͣ��ʱ
	private short delayLimit;	//�ӳ�ʱ�ޣ��������ʱ���
	private short timeDelay;	//�ӳ���ʱ�����ڵ�ʱ���
	private byte hangTimes;		//��ͣ���ޣ������ͣ����
	private byte hangUsed;		//ʵ����ͣ����
	private byte delayTimes;	//�ӳٴ��ޣ�������ڴ���
	private byte delayUsed;		//ʵ���ӳٴ���
	private byte hang;			//��ͣ
	private byte result;		//���
	private String summary;		//����
	private int bizInstance;	//ҵ��ʵ��
	private Long bizTrack;		//ҵ��켣
	private Long activeWorkSN;	//�Work������
	private Long dutyerID;		//��������ID
	private short dutyerType;	//������������
	private String name;		//��������
	private int visibility;  	//�ɼ�����
	private String note;		//��ע
	private Date busiStartTime; //ʵ�ʿ�ʼʱ��
	private Date busiEndTime;	//ʵ�ʽ���ʱ��
	@SuppressWarnings("unused")
	private int surplus;		//ʣ��ʱ��

	/*
	��������ID			REF_OBJ_ID	NUMBER(19)	19		FALSE	FALSE	FALSE
	������������			REF_OBJ_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	������������			REF_OBJ_TYPE	NUMBER(6)	6		FALSE	FALSE	FALSE
	ְԱ����				CLERK_NAME	VARCHAR2(30)	30		FALSE	FALSE	FALSE
	��λ���ƣ�����λid��	ENTITY_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	�������ƣ���Ӧ����ID��	DEPT_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	SPONSE_ENTITY_NAME	SPONSE_ENTITY_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	SPONSER_NAME		SPONSER_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	APPLICANT_NAME		APPLICANT_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	BROKE_ENTITY_NAME	BROKE_ENTITY_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	BROKER_NAME			BROKER_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	DUTYER_NAME			DUTYER_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	LAST_CLERK_NAME		LAST_CLERK_NAME	VARCHAR2(30)	30		FALSE	FALSE	FALSE
	S0	S0	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	S1	S1	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	S2	S2	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	S3	S3	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	S4	S4	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	*/
	
	
	private Long refObjectId;		//��������ID
	private String refObjectName;	//������������
	private Integer refObjectType;	//������������
	private String clerkName;		//ְԱ����
	private String entityName;		//��λ���ƣ�����λid��
	private String departmentName;	//�������ƣ���Ӧ����ID��
	private String sponseEntityName;//����λ����
	private String sponserName;		//����������
	private String applicantName;	//����������
	private String brokeEntityName;	//����λ����
	private String brokerName;		//����������
	private String dutyerName;		//����������
	private String lastClerkName;	//�ƽ�������
	
	//����timeUsed��ʱ��ָ��
	private Date timeUsedPosition;
	
	private Date timeUsedCalculateTime;   //����ʱ����ʱ��
	private Date timeUsedChangedTime;     //����ʱ�仯ʱ��
	
	
			
	private String string0;			//��ע�ִ�1
	private String string1;			//��ע�ִ�2
	private String string2;			//��ע�ִ�3
	private String string3;			//��ע�ִ�4
	private String string4;			//��ע�ִ�5
	
	//δ�־û�
	private int attachmentCount;	//��������
	
	//��ǰtask��Ӧ��ʱ�޼��ļ��ȼ�����������������ֶΡ�
	private byte timeLimitRiskGrade ;
	
	//��ǰ�����Σ���ͣʱ�䣬��ͣ�ڼ���㲢�仯������ʱΪ0
	private short currentTimeHang = 0;
	
	//private String remark;		//

	private Long dutyEntityID; //���ε�λ�����а쵥λ���ܲ�ͬ
	private String dutyEntityName;
	
	private Long dutyDepartmentID;
	private String dutyDepartmentName;//���β���
	
	private Long dutyerLeader1Id;
	private String dutyerLeader1Name;// ��������
	
	private Long dutyerLeader2Id;
	private String dutyerLeader2Name;// �ֹ��쵼 
	
	
	private RiskEntryList riskEntries = new RiskEntryList();
	
	

	public Task() {
	}
	
	public byte getTimeLimitRiskGrade() {
		return timeLimitRiskGrade;
	}
	
	public String getTimeLimitRiskGradeName(){
		//return CodeMapUtils.getCodeName((short)1074, getTimeLimitRiskGrade());
		return Risk.getGradeName(getTimeLimitRiskGrade());
	}
	
	public String getTimeLimitRiskGradeExplain(){
		return Risk.getGradeExplain(getTimeLimitRiskGrade());
	}

	public void setTimeLimitRiskGrade(byte timeLimitRiskGrade) {
		this.timeLimitRiskGrade = timeLimitRiskGrade;
	}

	/**
	 * ����timeUsed��ʱ��ָ��
	 * @return the timeUsedPosition
	 */
	public Date getTimeUsedPosition() {
		return timeUsedPosition;
	}
	/**
	 * @param timeUsedPosition the timeUsedPosition to set
	 */
	public void setTimeUsedPosition(Date timeUsedPosition) {
		this.timeUsedPosition = timeUsedPosition;
	}

	public Date getBusiStartTime() {
		return busiStartTime;
	}

	public void setBusiStartTime(Date busiStartTime) {
		this.busiStartTime = busiStartTime;
	}

	public Date getBusiEndTime() {
		return busiEndTime;
	}

	public void setBusiEndTime(Date busiEndTime) {
		this.busiEndTime = busiEndTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusName() {
		return CodeMapUtils.getCodeName(Task.class, "STATUS", getStatus());
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
	 * ������
	 * 
	 * ��ض�����,Ĭ��Ϊ0,��ʾ����.�������ס����ObjectTask����.
	 * @return byte
	 */
	public byte getObjectNum() {
		return objectNum;
	}

	/**
	 * @param objectNum
	 */
	public void setObjectNum(byte objectNum) {
		this.objectNum = objectNum;
	}

	/**
	 * ������
	 * 
	 * ����������,Ĭ��Ϊ0,��ʾ����.���������Ϣ��BusyMatter����.
	 * @return byte 
	 */
	public byte getMatterNum() {
		return matterNum;
	}

	/**
	 * @param matterNum
	 */
	public void setMatterNum(byte matterNum) {
		this.matterNum = matterNum;
	}

	/**
	 * �¼���
	 * 
	 * ����¼�,Ĭ��Ϊ0,��ʾ����.�߱�������Ϣ��EventTask����.
	 * @return byte
	 */
	public byte getAffairNum() {
		return affairNum;
	}

	/**
	 * @param eventNum
	 */
	public void setAffairNum(byte affairNum) {
		this.affairNum = affairNum;
	}

	/**
	 * ����λ
	 * 
	 * ������λ,Ĭ��Ϊ0,��ʾ���Ի���.
	 * @return Long
	 */
	public Long getSponseEntity() {
		return sponseEntity;
	}

	/**
	 * @param sponseEntity
	 */
	public void setSponseEntity(Long sponseEntity) {
		this.sponseEntity = sponseEntity;
	}

	/**
	 * ������
	 * 
	 * ��������,���ǵ�λ,���ʾ������,Ĭ��Ϊ0,��ʾ�޷����˻����.
	 * @return int
	 */
	public int getSponser() {
		return sponser;
	}

	/**
	 * @param sponser
	 */
	public void setSponser(int sponser) {
		this.sponser = sponser;
	}

	/**
	 * ������
	 * 
	 * ��������,���ǵ�λ,���ʾ������.Ĭ��Ϊ0,��ʾ�з����˻��ߺ���.
	 * @return int
	 */
	public int getApplicant() {
		return applicant;
	}

	/**
	 * @param applicant
	 */
	public void setApplicant(int applicant) {
		this.applicant = applicant;
	}

	/**
	 * ����λ
	 * 
	 * ����λ,Ĭ��Ϊ0,��ʾ��������.
	 * @return Long
	 */
	public Long getBrokeEntity() {
		return brokeEntity;
	}

	/**
	 * @param brokeEntity
	 */
	public void setBrokeEntity(Long brokeEntity) {
		this.brokeEntity = brokeEntity;
	}

	/**
	 * ������
	 * 
	 * ������,Ĭ��Ϊ0,��ʾ���Ի���.
	 * @return int
	 */
	public int getBroker() {
		return broker;
	}

	/**
	 * @param broker
	 */
	public void setBroker(int broker) {
		this.broker = broker;
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
	 * �а쵥λ
	 * 
	 * �а쵥λ,Ĭ��Ϊ0,��ʾ����.
	 * @return Long 
	 */
	public Long getEntityID() {
		return entityID;
	}

	/**
	 * @param entityID
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	/**
	 * �а첿��
	 * 
	 * �а첿��,Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getDepartID() {
		return departID;
	}

	/**
	 * @param departID
	 */
	public void setDepartID(Long departID) {
		this.departID = departID;
	}

	/**
	 * �ڰ���
	 * 
	 * ����Ϊ��.
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
	 * �ƽ���
	 * 
	 * Ĭ��Ϊ0,��ʾû��.
	 * @return Long
	 */
	public Long getLastClerkID() {
		return lastClerkID;
	}

	/**
	 * @param lastClerkID
	 */
	public void setLastClerkID(Long lastClerkID) {
		this.lastClerkID = lastClerkID;
	}

	/**
	 * �ƽ�ʱ��
	 * 
	 * �ཻ�����ʱ��.���ڿ�ʼʱ����Ϊ��.
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime
	 */
	@JSON(format="yyyy-MM-dd")
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
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
	 * @return byte
	 */
	public byte getTimeUnit() {
		return timeUnit;
	}
	
	public String getTimeUnitName() {
		return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_UNIT", getTimeUnit());
	}

	/**
	 * @param timeUnit
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}
	
//	public String getTimeUnitLabel(){
//		return  CodeMapUtils.getCodeName((short)1072, getTimeUnit());
//	}

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
	 * ��ͣ
	 * 
	 * Ĭ��Ϊ0,��ʾ����ͣ.
	 * @return byte
	 */
	public byte getHang() {
		return hang;
	}

	/**
	 * @param hang
	 */
	public void setHang(byte hang) {
		this.hang = hang;
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
	 * ҵ��ʵ��
	 * 
	 * Ĭ��Ϊ0,��ʾ��ʵ�����ߺ���ʵ����Ϣ.
	 * @return int
	 */
	public int getBizInstance() {
		return bizInstance;
	}

	/**
	 * @param bizInstance
	 */
	public void setBizInstance(int bizInstance) {
		this.bizInstance = bizInstance;
	}

	/**
	 * ҵ��켣
	 * 
	 * ����켣��Ĭ��Ϊ0����ʾ����.
	 * @return Long
	 */
	public Long getBizTrack() {
		return bizTrack;
	}

	/**
	 * @param busyTrack
	 */
	public void setBizTrack(Long bizTrack) {
		this.bizTrack = bizTrack;
	}

	
	public int getTime(){
		return getTimeLimit()-getTimeUsed();
	}


	public short getTimeused() {
		return timeused;
	}

	public void setTimeused(short timeused) {
		this.timeused = timeused;
	}

	public Long getActiveWorkSN() {
		return activeWorkSN;
	}

	public void setActiveWorkSN(Long activeWorkSN) {
		this.activeWorkSN = activeWorkSN;
	}
	

	/**
	 * Ĭ��Ϊ0����ʾ���ԡ�
	 * @return
	 */
	public Long getDutyerID() {
		return dutyerID;
	}

	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}

	public String getRemark() {
		return riskEntries == null ? null : riskEntries.toString();
	}
	
	public void setRemark(String remark) {
		this.riskEntries = RiskEntryList.valueOf(remark);
	}

	/**
	 * 1����λ��2�����ţ�3�����ˣ�Ĭ��Ϊ0����ʾ���ԡ�
	 * @return
	 */
	public short getDutyerType() {
		return dutyerType;
	}

	public void setDutyerType(short dutyerType) {
		this.dutyerType = dutyerType;
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
	public int getOpinion(){
		return dealOpinion(getTimeused(),getTimeLimit());
	}
	@Deprecated
	public static int DealOpinion(short value,short limit){
		return dealOpinion(value, limit);
	}
	public static int dealOpinion(short value,short limit){
		int result=0;		
		if(limit != 0){
			double x = 1 - 1.0 * value / limit;
			//System.out.println("---->" + x);
			if(x <= 0){
				result = 0;
			}else if(x <= 0.2){
				result = 1;
			}else if(x <= 0.4){
				result = 2;
			}else if(x <= 0.6){
				result = 3;
			}else if(x <= 0.8){
				result = 4;
			}else{
				result = 5;
			}
			//System.out.println("--->"+ result);
		}
		
//			float score=(float)(1.0-(Float.parseFloat(String.valueOf((int)value))/Float.parseFloat(String.valueOf((int)limit))));
//			System.out.println("--->" + score);
//			if(0.05<=score&&score<=0.2){
//				result=1;
//			}else if(0.21<=score&&score<=0.4){
//				result=2;
//			}else if(0.41<=score&&score<=0.6){
//				result=3;
//			}else if(0.61<=score&&score<=0.8){
//				result=4;
//			}else if(0.81<=score&&score<=1){
//				result=5;
//			}
		return result;
	}

	/**
	 * @return the refObjectId
	 */
	public Long getRefObjectId() {
		return refObjectId;
	}

	/**
	 * @param refObjectId the refObjectId to set
	 */
	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

	/**
	 * @return the refObjectName
	 */
	public String getRefObjectName() {
		return refObjectName;
	}

	/**
	 * @param refObjectName the refObjectName to set
	 */
	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}

	/**
	 * @return the refObjectType
	 */
	public Integer getRefObjectType() {
		return refObjectType;
	}

	/**
	 * @param refObjectType the refObjectType to set
	 */
	public void setRefObjectType(Integer refObjectType) {
		this.refObjectType = refObjectType;
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
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the sponseEntityName
	 */
	public String getSponseEntityName() {
		return sponseEntityName;
	}

	/**
	 * @param sponseEntityName the sponseEntityName to set
	 */
	public void setSponseEntityName(String sponseEntityName) {
		this.sponseEntityName = sponseEntityName;
	}

	/**
	 * @return the sponserName
	 */
	public String getSponserName() {
		return sponserName;
	}

	/**
	 * @param sponserName the sponserName to set
	 */
	public void setSponserName(String sponserName) {
		this.sponserName = sponserName;
	}

	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * @return the brokeEntityName
	 */
	public String getBrokeEntityName() {
		return brokeEntityName;
	}

	/**
	 * @param brokeEntityName the brokeEntityName to set
	 */
	public void setBrokeEntityName(String brokeEntityName) {
		this.brokeEntityName = brokeEntityName;
	}

	/**
	 * @return the brokerName
	 */
	public String getBrokerName() {
		return brokerName;
	}

	/**
	 * @param brokerName the brokerName to set
	 */
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
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
	 * @return the lastClerkName
	 */
	public String getLastClerkName() {
		return lastClerkName;
	}

	/**
	 * @param lastClerkName the lastClerkName to set
	 */
	public void setLastClerkName(String lastClerkName) {
		this.lastClerkName = lastClerkName;
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
	


	/**
	 * @return the timeUsedCalculateTime
	 */
	public Date getTimeUsedCalculateTime() {
		return timeUsedCalculateTime;
	}

	/**
	 * @param timeUsedCalculateTime the timeUsedCalculateTime to set
	 */
	public void setTimeUsedCalculateTime(Date timeUsedCalculateTime) {
		this.timeUsedCalculateTime = timeUsedCalculateTime;
	}

	/**
	 * @return the timeUsedChangedTime
	 */
	public Date getTimeUsedChangedTime() {
		return timeUsedChangedTime;
	}

	/**
	 * @param timeUsedChangedTime the timeUsedChangedTime to set
	 */
	public void setTimeUsedChangedTime(Date timeUsedChangedTime) {
		this.timeUsedChangedTime = timeUsedChangedTime;
	}
	
	
	/**
	 * ����Task��ʣ��ʱ�䡣
	 * @return
	 */
	public int getSurplus(){
//		if(getTimeLimit() == 0){
//			//��ʱ�޼�죬����ֱ�ӷ���ʣ��ʱ��Ϊ0
//			return 0;
//		}
		return getTimeLimit() - getTimeUsed();
	}

	public String toString(){
		return new ToStringBuilder(this)
		.append("sn", getSn())
		.append("type", getType())
		.append("name", getName())
		.append("status", getStatus())
		.toString();
	}


	public void setObject(RFSObjectable object){
		if(object != null && object.getId() != null){
			this.setRefObjectId(object.getId());
			this.setRefObjectType(object.getObjectType());
			this.setRefObjectName(object.getName());
		}
	}

	/**
	 * ��ȡ�ض���������Ե�ֵ��
	 */
	public BigDecimal getRiskValue(String objectAttr) {
		//��ȷ��ȡֵ����ʹ�÷���Ч�ʸ�
		if("timeUsed".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeUsed());
		}else if("timeHang".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeHang());
		}else if("timeDelay".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeDelay());
		}
		
		//ʹ�÷���
		return RiskMonitorableUtils.getRiskValue(this, objectAttr);
	}

	/**
	 * @return the currentTimeHang
	 */
	public short getCurrentTimeHang() {
		return currentTimeHang;
	}

	/**
	 * @param currentTimeHang the currentTimeHang to set
	 */
	public void setCurrentTimeHang(short currentTimeHang) {
		this.currentTimeHang = currentTimeHang;
	}
	
	/**
	 * ����ͣʱ�䣨������ͣ�ڼ䵱ǰ��ͣʱ�䣩
	 * @return
	 */
	public short getTotalTimeHang(){
		return (short)(getTimeHang() + getCurrentTimeHang());
	}

	
	

	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyEntityID()
	 */
	public Long getDutyEntityID() {
		return dutyEntityID;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyEntityName()
	 */
	public String getDutyEntityName() {
		return dutyEntityName;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyerLeader1Id()
	 */
	public Long getDutyerLeader1Id() {
		return dutyerLeader1Id;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyerLeader1Name()
	 */
	public String getDutyerLeader1Name() {
		return dutyerLeader1Name;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyerLeader2Id()
	 */
	public Long getDutyerLeader2Id() {
		return dutyerLeader2Id;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizInstance#getDutyerLeader2Name()
	 */
	public String getDutyerLeader2Name() {
		return dutyerLeader2Name;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyEntityID(java.lang.Long)
	 */
	public void setDutyEntityID(Long dutyEntityID) {
		this.dutyEntityID = dutyEntityID;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyEntityName(java.lang.String)
	 */
	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyerLeader1Id(java.lang.Long)
	 */
	public void setDutyerLeader1Id(Long dutyerLeader1Id) {
		this.dutyerLeader1Id = dutyerLeader1Id;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyerLeader1Name(java.lang.String)
	 */
	public void setDutyerLeader1Name(String dutyerLeader1Name) {
		this.dutyerLeader1Name = dutyerLeader1Name;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyerLeader2Id(java.lang.Long)
	 */
	public void setDutyerLeader2Id(Long dutyerLeader2Id) {
		this.dutyerLeader2Id = dutyerLeader2Id;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.MutableBizInstance#setDutyerLeader2Name(java.lang.String)
	 */
	public void setDutyerLeader2Name(String dutyerLeader2Name) {
		this.dutyerLeader2Name = dutyerLeader2Name;
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
	
	public String getVisibilityName() {
		if (visibility == 100) {
			return "��";
		}
		if (visibility == 99999) {
			return "��";
		}
		return "��";
	}
}