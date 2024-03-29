/*
 * $Id: BizTrackNode.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.apps.bean.SerializableEntity;
import org.opoo.ndao.Domain;


/**
 * @author ymq
 */
public class BizTrackNode extends SerializableEntity<Long> implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8974783094751055302L;
	public static final byte RESULT_DEFAULT = 0;
	public static final byte RESULT_SUCCESS = 1;
	public static final byte RESULT_RETURN = 2;
	
	
	private Long sn;
	private Long trackSN;
	private byte sectNo;
	private byte stepNo;
	private byte seatNo =1;
	private byte summary;
	private String bizName;
	private Long dutyerID;
	private String dutyer;
	private short timeLimit;
	private byte timeUnit;
	
	private int bizType;
	private byte status;
	private byte result;
	
	/**
	 * @return the seatNo
	 */
	public byte getSeatNo() {
		return seatNo;
	}

	/**
	 * 位号
	 * 
	 * 默认为1
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(byte seatNo) {
		this.seatNo = seatNo;
	}

	

	

	public BizTrackNode() {
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
	 *  流程
	 *  
	 *  不可空.
	 * @return
	 */
	public Long getTrackSN() {
		return this.trackSN;
	}

	/**
	 * @param trackSN
	 */
	public void setTrackSN(Long trackSN) {
		this.trackSN = trackSN;
	}

	/**
	 * 段数
	 * 
	 * 不可空.
	 * @return byte
	 */
	public byte getSectNo() {
		return this.sectNo;
	}

	/**
	 * @param sectNo
	 */
	public void setSectNo(byte sectNo) {
		this.sectNo = sectNo;
	}

	/**
	 * 层数
	 * 
	 * 不可空.
	 * @return byte
	 */
	public byte getStepNo() {
		return this.stepNo;
	}

	/**
	 * @param stepNo
	 */
	public void setStepNo(byte stepNo) {
		this.stepNo = stepNo;
	}

	/**
	 * 摘要
	 * 
	 * @return byte
	 */
	public byte getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 */
	public void setSummary(byte summary) {
		this.summary = summary;
	}
	/**
	 * @return the bizName
	 */
	public String getBizName() {
		return bizName;
	}

	/**
	 * 名称
	 * @param bizName the bizName to set
	 */
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	/**
	 * 责任人
	 * 
	 * @return String
	 */
	public String getDutyer() {
		return dutyer;
	}

	/**
	 * @param dutyer
	 */
	public void setDutyer(String dutyer) {
		this.dutyer = dutyer;
	}

	/**
	 * 时限
	 * 
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
	 * @return the timeUnit
	 */
	public byte getTimeUnit() {
		return timeUnit;
	}

	/**
	 * 时间单位
	 * 
	 * 定义：0 无，1 年，2 月，3 周，4 日 ，5时，6 分，7秒，8毫秒。默认为0，表示无定义或忽略。
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}
	/**
	 * 类型
	 * 
	 * @return short
	 */
	public int getBizType() {
		return bizType;
	}

	/**
	 * @param bizType
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	/**
	 * 状态
	 * 
	 * @return byte
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 结果
	 * 
	 * 1 成功,2 失败, 3 撤回.默认为0,表示忽略.
	 * @return
	 */
	public byte getResult() {
		return this.result;
	}

	/**
	 * @param result
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	public Long getDutyerID() {
		return dutyerID;
	}

	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}
}
