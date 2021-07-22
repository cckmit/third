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
	 * 可见范围	Visibility	Tinyint	
	 * 0 未做限定；
	 * 100 常规业务类查询（即系统实际用户查询）可见；
	 * 200 ‘业务状态图标可见性’查询可见 ；99999 其他。
	 * 缺省为100。
	 */
	public static final int VISIBILITY_UNQUALIFIED = 0;// 未做限定
	public static final int VISIBILITY_GENERAL = 100; // 常规业务类
	public static final int VISIBILITY_ICON = 200; // 业务状态图标可见性
	public static final int VISIBILITY_OTHERS = 99999;// 其他
	
	/**
	 * 等同于 VISIBILITY_UNQUALIFIED
	 */
	public static final int VISIBLE_未做限定 = VISIBILITY_UNQUALIFIED;
	/**
	 * 等同于VISIBILITY_GENERAL
	 */
	public static final int VISIBLE_常规业务类 = VISIBILITY_GENERAL;
	/**
	 * 等同于VISIBILITY_ICON
	 */
	public static final int VISIBLE_业务状态图标可见性 = VISIBILITY_ICON;
	/**
	 * 等同于VISIBILITY_OTHERS
	 */
	public static final int VISIBLE_其他 = VISIBILITY_OTHERS;
	
	
	public final static byte TASK_STATUS_OK = 0; //待办
	public final static byte TASK_STATUS_WORK = 1;// 在办
	public final static byte TASK_STATUS_HANG = 2;// 暂停
	public final static byte TASK_STATUS_PREPARE = 3;//预办
	public final static byte TASK_STATUS_TERMINATE = 9;// 结束
	public final static byte TASK_STATUS_CANCEL = 10;// 取消
	public final static byte TASK_STATUS_STOP = 11;// 中止
	public final static byte TASK_STATUS_AVOID = 12;//免办 avoid
	public final static byte TASK_STATUS_WITHDRAW = 13;//撤回
	public final static byte TASK_STATUS_REJECT = 14;//驳回
	public final static byte TASK_STATUS_TRANSFER = 15;//转出
	public final static byte TASK_STATUS_MERGE = 16;//合并
	
	public final static byte STATUS_待办 = TASK_STATUS_OK;
	public final static byte STATUS_在办 = TASK_STATUS_WORK;
	public final static byte STATUS_暂停 = TASK_STATUS_HANG;
	public final static byte STATUS_预办 = TASK_STATUS_PREPARE;
	public final static byte STATUS_结束 = TASK_STATUS_TERMINATE;
	public final static byte STATUS_取消 = TASK_STATUS_CANCEL;
	public final static byte STATUS_中止 = TASK_STATUS_STOP;
	public final static byte STATUS_免办 = TASK_STATUS_AVOID;
	public final static byte STATUS_撤回 = TASK_STATUS_WITHDRAW;
	public final static byte STATUS_驳回 = TASK_STATUS_REJECT;
	public final static byte STATUS_转出 = TASK_STATUS_TRANSFER;
	public final static byte STATUS_合并 = TASK_STATUS_MERGE;
	
	
	private Long sn;			//主键
	private String code;		//代码
	private byte objectNum;		//对象数
	private byte affairNum;		//事别数
	private byte matterNum;		//事项数
	private Long sponseEntity;	//发起单位
	private int sponser;		//发起人
	private int applicant;		//申请人
	private Long brokeEntity;	//代理单位
	private int broker;			//代理人
	private byte proposerRole;	//经办角色
	private Long entityID;		//承办单位
	private Long departID;		//承办部门
	private Long clerkID;		//当前办理人
	private Long lastClerkID; 	//移交人
	private Date lastTime;		//移交时间
	private Date beginTime;		//开始时间
	private Date hangTime;		//暂停时间
	private Date wakeTime;		//唤醒时间
	private Date endTime;		//结束时间
	//时间单位 时间单位。定义：0无，1年，2月，3周，4日，5天，6时，7分，8秒，9毫秒，默认为4（日）。默认为0，表示无定义或忽略。
	private byte timeUnit = (byte) TimeUnit.DAY.getFlag();		
	private short timeLimit;	//规定时限
	private short timeused;		//实际用时
	private short hangLimit;	//暂停时限
	private short timeHang;		//暂停用时
	private short delayLimit;	//延迟时限，最大延期时间段
	private short timeDelay;	//延迟用时，延期的时间段
	private byte hangTimes;		//暂停次限，最大暂停次数
	private byte hangUsed;		//实际暂停次数
	private byte delayTimes;	//延迟次限，最大延期次数
	private byte delayUsed;		//实际延迟次数
	private byte hang;			//暂停
	private byte result;		//结果
	private String summary;		//结论
	private int bizInstance;	//业务实例
	private Long bizTrack;		//业务轨迹
	private Long activeWorkSN;	//活动Work的主键
	private Long dutyerID;		//责任主体ID
	private short dutyerType;	//责任主体类型
	private String name;		//任务名称
	private int visibility;  	//可见级别
	private String note;		//备注
	private Date busiStartTime; //实际开始时间
	private Date busiEndTime;	//实际结束时间
	@SuppressWarnings("unused")
	private int surplus;		//剩余时间

	/*
	关联对象ID			REF_OBJ_ID	NUMBER(19)	19		FALSE	FALSE	FALSE
	关联对象名称			REF_OBJ_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	关联对象类型			REF_OBJ_TYPE	NUMBER(6)	6		FALSE	FALSE	FALSE
	职员名称				CLERK_NAME	VARCHAR2(30)	30		FALSE	FALSE	FALSE
	单位名称（对象单位id）	ENTITY_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
	部门名称（对应部门ID）	DEPT_NAME	VARCHAR2(150)	150		FALSE	FALSE	FALSE
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
	
	
	private Long refObjectId;		//关联对象ID
	private String refObjectName;	//关联对象名称
	private Integer refObjectType;	//关联对象类型
	private String clerkName;		//职员名称
	private String entityName;		//单位名称（对象单位id）
	private String departmentName;	//部门名称（对应部门ID）
	private String sponseEntityName;//发起单位名称
	private String sponserName;		//发起人姓名
	private String applicantName;	//申请人姓名
	private String brokeEntityName;	//代理单位名称
	private String brokerName;		//代理人姓名
	private String dutyerName;		//责任人姓名
	private String lastClerkName;	//移交人姓名
	
	//计算timeUsed的时间指针
	private Date timeUsedPosition;
	
	private Date timeUsedCalculateTime;   //已用时计算时间
	private Date timeUsedChangedTime;     //已用时变化时间
	
	
			
	private String string0;			//备注字串1
	private String string1;			//备注字串2
	private String string2;			//备注字串3
	private String string3;			//备注字串4
	private String string4;			//备注字串5
	
	//未持久化
	private int attachmentCount;	//附件数量
	
	//当前task对应的时限监察的监察等级，监察计算后回填这个字段。
	private byte timeLimitRiskGrade ;
	
	//当前（本次）暂停时间，暂停期间计算并变化，正常时为0
	private short currentTimeHang = 0;
	
	//private String remark;		//

	private Long dutyEntityID; //责任单位，跟承办单位可能不同
	private String dutyEntityName;
	
	private Long dutyDepartmentID;
	private String dutyDepartmentName;//责任部门
	
	private Long dutyerLeader1Id;
	private String dutyerLeader1Name;// 科室主管
	
	private Long dutyerLeader2Id;
	private String dutyerLeader2Name;// 分管领导 
	
	
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
	 * 计算timeUsed的时间指针
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
	 * 唯一标示
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
	 * 唯一标示
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
	 * 代码编号
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
	 * 对象数
	 * 
	 * 相关对象数,默认为0,表示忽略.具体对象住处在ObjectTask表中.
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
	 * 事项数
	 * 
	 * 相亲事项数,默认为0,表示忽略.具体对象信息在BusyMatter表中.
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
	 * 事件数
	 * 
	 * 相关事件,默认为0,表示忽略.具本对象信息在EventTask表中.
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
	 * 发起单位
	 * 
	 * 任务发起单位,默认为0,表示忽略或列.
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
	 * 发起人
	 * 
	 * 任务发起人,若是单位,则表示申请人,默认为0,表示无发起人或忽略.
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
	 * 申请人
	 * 
	 * 任务发起人,若是单位,则表示申请人.默认为0,表示列发起人或者忽略.
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
	 * 代理单位
	 * 
	 * 代理单位,默认为0,表示忽略者无.
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
	 * 代理人
	 * 
	 * 代理人,默认为0,表示忽略或无.
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
	 * 经办角色
	 * 
	 * 经办人角色: 0 发起人, 1 代理人
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
	 * 承办单位
	 * 
	 * 承办单位,默认为0,表示忽略.
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
	 * 承办部门
	 * 
	 * 承办部门,默认为0,表示忽略.
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
	 * 在办人
	 * 
	 * 不能为空.
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
	 * 移交人
	 * 
	 * 默认为0,表示没有.
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
	 * 移交时间
	 * 
	 * 多交处理的时间.早于开始时间则为空.
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
	 * 开始时间
	 * 
	 * 开始处理的时间.
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
	 * 暂停时间
	 * 
	 * 开始暂停的时间,早于开始时间则为空.
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
	 * 唤醒时间
	 * 
	 * 开始唤醒的时间.早于开始时间则为空.
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
	 * 结速时间
	 * 
	 * 完成处理的时间.早于开始时间则为空.
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
	 * 时间单位
	 * 
	 * 时间单位
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
	 * 规定时限
	 * 
	 * 默认为0,表示忽略.
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
	 * 实际用时
	 * 
	 * 默认为0,表示忽略.
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
	 * 暂停时限
	 * 
	 * 默认为0,表示忽略.
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
	 * 暂停用时
	 * 
	 * 默认为0,表示未暂停.
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
	 * 延迟时限
	 * 
	 * 默认为0,表示忽略.
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
	 * 延迟用时
	 * 
	 * 默认为0,表示忽略.
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
	 * 暂停次限
	 * 
	 * 默认为0,表示忽略.
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
	 * 实际暂停
	 * 
	 * 默认为0,表示忽略.
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
	 * 延迟次限
	 * 
	 * 默认为0,表示未暂停.
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
	 * 实际延迟
	 * 
	 * 默认为0,表示忽略.
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
	 * 暂停
	 * 
	 * 默认为0,表示非暂停.
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
	 * 结果
	 *
	 * 默认为0,表示尚无结论.
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
	 * 结论
	 * 
	 * 结论内容.
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
	 * 业务实例
	 * 
	 * 默认为0,表示无实例或者忽略实例信息.
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
	 * 业务轨迹
	 * 
	 * 任务轨迹，默认为0，表示忽略.
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
	 * 默认为0，表示忽略。
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
	 * 1：单位；2：部门；3：个人；默认为0，表示忽略。
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
	 * 计算Task的剩余时间。
	 * @return
	 */
	public int getSurplus(){
//		if(getTimeLimit() == 0){
//			//无时限监察，所以直接返回剩余时间为0
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
	 * 获取特定被监控属性的值。
	 */
	public BigDecimal getRiskValue(String objectAttr) {
		//明确的取值，比使用反射效率高
		if("timeUsed".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeUsed());
		}else if("timeHang".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeHang());
		}else if("timeDelay".equalsIgnoreCase(objectAttr)){
			return new BigDecimal(getTimeDelay());
		}
		
		//使用反射
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
	 * 总暂停时间（包括暂停期间当前暂停时间）
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
			return "否";
		}
		if (visibility == 99999) {
			return "是";
		}
		return "是";
	}
}