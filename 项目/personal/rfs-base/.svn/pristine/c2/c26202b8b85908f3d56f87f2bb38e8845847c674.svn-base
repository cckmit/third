package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.ndao.Domain;

public class EventMsg extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5409686420995663451L;
	
	public static final byte IS_TRUE = 1;
	public static final byte IS_FALSE = 0;
	public static short DUTYER_TYPE_PROJECT_DIRECTOR = 2;
	public static short DUTYER_TYPE_TASK_CLERK = 7;
	private Long id;
	private int objectType;						//对象类型
	private Long objectID;						//对象ID
	private int jobType;						//
	private int taskType;						//业务类型
	private int workType;						//工作类型
	private int processType;							
	private Long eventType;						//事件类型
	private short dutyerType;					//责任人类型
	private Long dutyerID;						//责任人ID
	private short titleType;					//标题类型
	private String title;						//标题
	private short templetType;					//模板类型
	private String templet;
	private byte isCreateMsg;					//消息
	private byte isCreateSms;					//短信
	private byte isCreateEmail;					//邮件
	private byte isSendInternal;
	private byte needConfirm;					//是否需要确认
//	发布时间类型
//	1 立即发布；
//    101 如果本日是自然日，则下一个工作日当前时间发送；
	private short publishType;
	private Date publishTime;
//  过期时间类型	
//	1 指定日期时间；
//  1011 发布时间后5分钟；1021 发布时间后10分钟；1031 发布时间后30分钟；
//  1101 发布时间后1小时；1102 发布时间后2小时； 1124 发布时间后24小时；
//  1201 发布时间后1个自然日；1202 发布时间后2个自然日；1203 发布时间后3个自然日；
//  1301 发布时间后1个工作日；1302 发布时间后2个工作日；1303 发布时间后3个工作日；
//  1401 发布时间后1个月；1402 发布时间后2个月；1403 发布时间后3个月；
	private short expiresType;
	private Date expirationTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public Long getObjectID() {
		return objectID;
	}
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	public int getJobType() {
		return jobType;
	}
	public void setJobType(int jobType) {
		this.jobType = jobType;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getWorkType() {
		return workType;
	}
	public void setWorkType(int workType) {
		this.workType = workType;
	}
	public int getProcessType() {
		return processType;
	}
	public void setProcessType(int processType) {
		this.processType = processType;
	}
	public Long getEventType() {
		return eventType;
	}
	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}
	public short getDutyerType() {
		return dutyerType;
	}
	public void setDutyerType(short dutyerType) {
		this.dutyerType = dutyerType;
	}
	
	public Long getDutyerID() {
		return dutyerID;
	}
	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}
	public short getTempletType() {
		return templetType;
	}
	public void setTempletType(short templetType) {
		this.templetType = templetType;
	}
	public String getTemplet() {
		return templet;
	}
	public void setTemplet(String templet) {
		this.templet = templet;
	}
	public byte getIsCreateMsg() {
		return isCreateMsg;
	}
	public void setIsCreateMsg(byte isCreateMsg) {
		this.isCreateMsg = isCreateMsg;
	}
	public byte getIsCreateSms() {
		return isCreateSms;
	}
	public void setIsCreateSms(byte isCreateSms) {
		this.isCreateSms = isCreateSms;
	}
	public byte getIsSendInternal() {
		return isSendInternal;
	}
	public void setIsSendInternal(byte isSendInternal) {
		this.isSendInternal = isSendInternal;
	}
	public byte getNeedConfirm() {
		return needConfirm;
	}
	public void setNeedConfirm(byte needConfirm) {
		this.needConfirm = needConfirm;
	}
	public byte getIsCreateEmail() {
		return isCreateEmail;
	}
	public void setIsCreateEmail(byte isCreateEmail) {
		this.isCreateEmail = isCreateEmail;
	}
	public short getExpiresType() {
		return expiresType;
	}
	public void setExpiresType(short expiresType) {
		this.expiresType = expiresType;
	}
	public short getTitleType() {
		return titleType;
	}
	public void setTitleType(short titleType) {
		this.titleType = titleType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public short getPublishType() {
		return publishType;
	}
	public void setPublishType(short publishType) {
		this.publishType = publishType;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	
	
}
