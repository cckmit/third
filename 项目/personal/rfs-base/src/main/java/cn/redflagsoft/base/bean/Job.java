/*
 * $Id: Job.java 5189 2011-12-12 04:52:47Z lcj $
 * Job.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.opoo.ndao.Domain;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;
import cn.redflagsoft.base.util.RiskEntryList;
import cn.redflagsoft.base.util.RiskMonitorableUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * @author Administrator
 *
 */
@ObjectType(ObjectTypes.JOB)
public class Job extends VersionableBean implements Domain<Long>, RiskMonitorable, Cloneable, RFSEntityObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7053270432686997507L;
	public static final int OBJECT_TYPE = ObjectTypes.JOB;	
	private Long sn;
	private String code;
	private byte objectNum;
	private byte matterNum;
	private byte threadNum;
	private Date beginTime;
	private Date hangTime;
	private Date wakeTime;
	private Date endTime;
	private byte timeUnit;
	private short timeLimit;
	private short timeUsed;
	private short hangLimit;
	private short timeHang;
	private short delayLimit;
	private short timeDelay;
	private byte hangTimes;
	private byte hangUsed;
	private byte delayTimes;
	private byte delayUsed;
	private byte result;
	private Long bizInstance;
	private Long bizTrack;
	private Long clerkID;
	private Long dutyerID;
	private short dutyerType;
	//private String remark;
	
	public final static byte JOB_STATUS_WORK = 1;
	public final static byte JOB_STATUS_HANG = 2;
	public final static byte JOB_STATUS_TERMINATE = 9;
	private RiskEntryList riskEntries = new RiskEntryList();
	
	/**
	 * ���
	 * ���кţ�Ψһ
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * ����
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * ������
	 * ��ض�������Ĭ��Ϊ0����ʾ����
	 * @return the objectNum
	 */
	public byte getObjectNum() {
		return objectNum;
	}
	/**
	 * @param objectNum the objectNum to set
	 */
	public void setObjectNum(byte objectNum) {
		this.objectNum = objectNum;
	}
	/**
	 * ������
	 * �����������Ĭ��Ϊ0����ʾ����
	 * @return the matterNum
	 */
	public byte getMatterNum() {
		return matterNum;
	}
	/**
	 * @param matterNum the matterNum to set
	 */
	public void setMatterNum(byte matterNum) {
		this.matterNum = matterNum;
	}
	/**
	 * �¼���
	 * ����¼�����Ĭ��Ϊ0����ʾ����
	 * @return the threadNum
	 */
	public byte getThreadNum() {
		return threadNum;
	}
	/**
	 * @param threadNum the threadNum to set
	 */
	public void setThreadNum(byte threadNum) {
		this.threadNum = threadNum;
	}
	/**
	 * ��ʼʱ��
	 * ��ʼ�����ʱ��
	 * @return the beginTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * @param beginTime the beginTime to set
	 */
	@JSON(format="yyyy-MM-dd")
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * ��ʼ��ͣ��ʱ�䣬Ĭ��Ϊ��
	 * @return the hangTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getHangTime() {
		return hangTime;
	}
	/**
	 * @param hangTime the hangTime to set
	 */
	@JSON(format="yyyy-MM-dd")
	public void setHangTime(Date hangTime) {
		this.hangTime = hangTime;
	}
	/**
	 * ����ʱ��
	 * ��ʼ���ѵ�ʱ�䣬Ĭ��Ϊ��
	 * @return the wakeTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getWakeTime() {
		return wakeTime;
	}
	/**
	 * @param wakeTime the wakeTime to set
	 */
	@JSON(format="yyyy-MM-dd")
	public void setWakeTime(Date wakeTime) {
		this.wakeTime = wakeTime;
	}
	/**
	 * ����ʱ��
	 * ��ɴ����ʱ�䣬Ĭ��Ϊ��
	 * @return the endTime
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	@JSON(format="yyyy-MM-dd")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * ʱ�䵥λ
	 * 
	 * ʱ�䵥λ�����壺0 �ޣ�1 �꣬2 �£�3 �ܣ�4 �գ�5 ʱ��6 �֣�7 �룬8 ���롣Ĭ��Ϊ0����ʾ�޶�������
	 * @return the timeUnit
	 */
	public byte getTimeUnit() {
		return timeUnit;
	}
	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}
	/**
	 * �涨ʱ��
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the timeLimit
	 */
	public short getTimeLimit() {
		return timeLimit;
	}
	/**
	 * @param timeLimit the timeLimit to set
	 */
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}
	/**
	 * ʵ����ʱ
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the timeUsed
	 */
	public short getTimeUsed() {
		return timeUsed;
	}
	/**
	 * @param timeUsed the timeUsed to set
	 */
	public void setTimeUsed(short timeUsed) {
		this.timeUsed = timeUsed;
	}
	/**
	 * ��ͣʱ��
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the hangLimit
	 */
	public short getHangLimit() {
		return hangLimit;
	}
	/**
	 * @param hangLimit the hangLimit to set
	 */
	public void setHangLimit(short hangLimit) {
		this.hangLimit = hangLimit;
	}
	/**
	 * ��ͣ��ʱ
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the timeHang
	 */
	public short getTimeHang() {
		return timeHang;
	}
	/**
	 * @param timeHang the timeHang to set
	 */
	public void setTimeHang(short timeHang) {
		this.timeHang = timeHang;
	}
	/**
	 * �ӳ�ʱ��
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the delayLimit
	 */
	public short getDelayLimit() {
		return delayLimit;
	}
	/**
	 * @param delayLimit the delayLimit to set
	 */
	public void setDelayLimit(short delayLimit) {
		this.delayLimit = delayLimit;
	}
	/**
	 * �ӳ���ʱ
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the timeDelay
	 */
	public short getTimeDelay() {
		return timeDelay;
	}
	/**
	 * @param timeDelay the timeDelay to set
	 */
	public void setTimeDelay(short timeDelay) {
		this.timeDelay = timeDelay;
	}
	/**
	 * ��ͣ����
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the hangTimes
	 */
	public byte getHangTimes() {
		return hangTimes;
	}
	/**
	 * @param hangTimes the hangTimes to set
	 */
	public void setHangTimes(byte hangTimes) {
		this.hangTimes = hangTimes;
	}
	/**
	 * ʵ����ͣ
	 * @return the hangUsed
	 */
	public byte getHangUsed() {
		return hangUsed;
	}
	/**
	 * @param hangUsed the hangUsed to set
	 */
	public void setHangUsed(byte hangUsed) {
		this.hangUsed = hangUsed;
	}
	/**
	 * �ӳٴ���
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the delayTimes
	 */
	public byte getDelayTimes() {
		return delayTimes;
	}
	/**
	 * @param delayTimes the delayTimes to set
	 */
	public void setDelayTimes(byte delayTimes) {
		this.delayTimes = delayTimes;
	}
	/**
	 * ʵ���ӳ�
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the delayUsed
	 */
	public byte getDelayUsed() {
		return delayUsed;
	}
	/**
	 * @param delayUsed the delayUsed to set
	 */
	public void setDelayUsed(byte delayUsed) {
		this.delayUsed = delayUsed;
	}
	/**
	 * ���
	 * 
	 * Ĭ��Ϊ0����ʾ���޽���
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}
	/**
	 * ҵ��ʵ��
	 * 
	 * Ĭ��Ϊ0����ʾ��ʵ�������ʵ����Ϣ
	 * @return the bizInstance
	 */
	public Long getBizInstance() {
		return bizInstance;
	}
	/**
	 * @param bizInstance the bizInstance to set
	 */
	public void setBizInstance(Long bizInstance) {
		this.bizInstance = bizInstance;
	}
	/**
	 * ҵ��켣
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @return the bizTrack
	 */
	public Long getBizTrack() {
		return bizTrack;
	}
	/**
	 * @param bizTrack the bizTrack to set
	 */
	public void setBizTrack(Long bizTrack) {
		this.bizTrack = bizTrack;
	}
	
	public Long getClerkID() {
		return clerkID;
	}
	
	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}
	
	public Long getId(){
		return getSn();
		
	}
	
	public void setId(Long id){
		setSn(id);
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
	
	
	public Job clone(){
		try {
			return (Job) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String toString(){
		return new ToStringBuilder(this)
		.append("sn", getSn())
		.append("type", getType())
		.toString();
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RiskMonitorable#getRiskValue(java.lang.String)
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
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Job job = new Job();
		job.setCode("AAAAAAAAAAAAAAAAAAAAAa");
		
		System.out.println(job.getCode());
		Job b = job.clone();
		System.out.println(b.getCode());
		
		
		b.setCode("vvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		System.out.println(job.getCode());
		System.out.println(b.getCode());
		
		
		Job c = new Job();
		PropertyUtils.copyProperties(c, b);
		System.out.println(c.toJSONString());
		
	}
	
}
