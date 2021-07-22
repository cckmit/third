package cn.redflagsoft.base.bean;

import java.util.Date;

/****
 * 	过错整改核查
 * 
 * @author lifeng
 *
 */
public class SignCardCheck extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private Long id; 							//
	private Long signCardId; 						//过错编号
	private Long checkDutyerId;						//整改核查人编号
	private String checkDutyerName;					//整改核查人姓名
	private String checkSummary;					//整改情况摘要
	private String checkFileNo;						//整改核查报告文号
	private Long checkCreator;						//整改核查信息登记人
	private Date checkActualTime;					//整改核查时间
	private Date checkTime;							//整改核查系统操作时间
	private String checkRemark;						//整改核查备注

	// private int type; 							//类型
	// private byte status; 						//状态
	// private String remark; 						//备注
	// private Long creator; 						//创建者
	// private Date creationTime; 					//创建时间
	// private Long modifier;						//最后修改者
	// private Date modificationTime; 				//最后修改时间
	public Long getSignCardId() {
		return signCardId;
	}

	public void setSignCardId(Long signCardId) {
		this.signCardId = signCardId;
	}

	public Long getCheckDutyerId() {
		return checkDutyerId;
	}

	public void setCheckDutyerId(Long checkDutyerId) {
		this.checkDutyerId = checkDutyerId;
	}

	public String getCheckDutyerName() {
		return checkDutyerName;
	}

	public void setCheckDutyerName(String checkDutyerName) {
		this.checkDutyerName = checkDutyerName;
	}

	public String getCheckSummary() {
		return checkSummary;
	}

	public void setCheckSummary(String checkSummary) {
		this.checkSummary = checkSummary;
	}

	public String getCheckFileNo() {
		return checkFileNo;
	}

	public void setCheckFileNo(String checkFileNo) {
		this.checkFileNo = checkFileNo;
	}

	public Long getCheckCreator() {
		return checkCreator;
	}

	public void setCheckCreator(Long checkCreator) {
		this.checkCreator = checkCreator;
	}

	public Date getCheckActualTime() {
		return checkActualTime;
	}

	public void setCheckActualTime(Date checkActualTime) {
		this.checkActualTime = checkActualTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

}
