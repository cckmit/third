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
	private int objectType;						//��������
	private Long objectID;						//����ID
	private int jobType;						//
	private int taskType;						//ҵ������
	private int workType;						//��������
	private int processType;							
	private Long eventType;						//�¼�����
	private short dutyerType;					//����������
	private Long dutyerID;						//������ID
	private short titleType;					//��������
	private String title;						//����
	private short templetType;					//ģ������
	private String templet;
	private byte isCreateMsg;					//��Ϣ
	private byte isCreateSms;					//����
	private byte isCreateEmail;					//�ʼ�
	private byte isSendInternal;
	private byte needConfirm;					//�Ƿ���Ҫȷ��
//	����ʱ������
//	1 ����������
//    101 �����������Ȼ�գ�����һ�������յ�ǰʱ�䷢�ͣ�
	private short publishType;
	private Date publishTime;
//  ����ʱ������	
//	1 ָ������ʱ�䣻
//  1011 ����ʱ���5���ӣ�1021 ����ʱ���10���ӣ�1031 ����ʱ���30���ӣ�
//  1101 ����ʱ���1Сʱ��1102 ����ʱ���2Сʱ�� 1124 ����ʱ���24Сʱ��
//  1201 ����ʱ���1����Ȼ�գ�1202 ����ʱ���2����Ȼ�գ�1203 ����ʱ���3����Ȼ�գ�
//  1301 ����ʱ���1�������գ�1302 ����ʱ���2�������գ�1303 ����ʱ���3�������գ�
//  1401 ����ʱ���1���£�1402 ����ʱ���2���£�1403 ����ʱ���3���£�
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
