package cn.redflagsoft.base.vo;

import java.io.Serializable;

public class BizTrackNodeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4708943490922700325L;
	private Long sn;
	private byte seatNo;
	private byte sectNo;
	private byte stepNo;
	private String dutyer;
	private short timeLimit;
	private int bizType;
	private byte status;
	private byte result;
	private byte timeUnit;
	private String timeUnitName;
	private short timeUsed;
	private String bizName;
	private byte summary;
	private int count;
	
	/**
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
	 * @return the seatNo
	 */
	public byte getSeatNo() {
		return seatNo;
	}
	/**
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(byte seatNo) {
		this.seatNo = seatNo;
	}
	/**
	 * @return the sectNo
	 */
	public byte getSectNo() {
		return sectNo;
	}
	/**
	 * @param sectNo the sectNo to set
	 */
	public void setSectNo(byte sectNo) {
		this.sectNo = sectNo;
	}
	/**
	 * @return the stepNo
	 */
	public byte getStepNo() {
		return stepNo;
	}
	/**
	 * @param stepNo the stepNo to set
	 */
	public void setStepNo(byte stepNo) {
		this.stepNo = stepNo;
	}
	/**
	 * @return the dutyer
	 */
	public String getDutyer() {
		return dutyer;
	}
	/**
	 * @param dutyer the dutyer to set
	 */
	public void setDutyer(String dutyer) {
		this.dutyer = dutyer;
	}
	/**
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
	 * @return the bizType
	 */
	public int getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}
	/**
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
	 * @return the bizName
	 */
	public String getBizName() {
		return bizName;
	}
	/**
	 * @param bizName the bizName to set
	 */
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	/**
	 * @return the summary
	 */
	public byte getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(byte summary) {
		this.summary = summary;
	}
	/**
	 * @return the timeUnitName
	 */
	public String getTimeUnitName() {
		return timeUnitName;
	}
	/**
	 * @param timeUnitName the timeUnitName to set
	 */
	public void setTimeUnitName(String timeUnitName) {
		this.timeUnitName = timeUnitName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
