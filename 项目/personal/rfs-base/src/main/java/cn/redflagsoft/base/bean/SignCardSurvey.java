package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.bean.VersionableBean;
import cn.redflagsoft.base.util.CodeMapUtils;

/****
 * 	过错事实认定
 * 	
 * @author lifeng
 *
 */
public class SignCardSurvey extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final byte DEALTYPE_追究 = 1;
	public static final byte DEALTYPE_不予追究 = 9;

	// private Long id; 						//
	private Long signCardId; 					// 过错编号
	private Long surveyDutyerId; 				// 调查人编号
	private String surveyDutyerName; 			// 调查人姓名
	private String surveySummary; 				// 调查结论摘要
	private String surveyFileNo; 				// 调查报告文号
	private String surveyTruth; 				// 事实说明
	private byte dealType; 						// 处理方式： 1-追究、9-不予追究（撤销过错
	private Long surveyCreator; 				// 调查信息登记人
	private Date surveyActualTime; 				// 调查实际时间
	private Date surveyTime; 					// 调查系统操作时间
	private String surveyRemark; 				// 调查备注

	// private int type; 						//类型
	// private byte status; 					//状态
	// private String remark; 					//备注
	// private Long creator; 					//创建者
	// private Date creationTime; 				//创建时间
	// private Long modifier; 					//最后修改者
	// private Date modificationTime; 			//最后修改时间
	public Long getSignCardId() {
		return signCardId;
	}

	public void setSignCardId(Long signCardId) {
		this.signCardId = signCardId;
	}

	public Long getSurveyDutyerId() {
		return surveyDutyerId;
	}

	public void setSurveyDutyerId(Long surveyDutyerId) {
		this.surveyDutyerId = surveyDutyerId;
	}

	public String getSurveyDutyerName() {
		return surveyDutyerName;
	}

	public void setSurveyDutyerName(String surveyDutyerName) {
		this.surveyDutyerName = surveyDutyerName;
	}

	public String getSurveySummary() {
		return surveySummary;
	}

	public void setSurveySummary(String surveySummary) {
		this.surveySummary = surveySummary;
	}

	public String getSurveyFileNo() {
		return surveyFileNo;
	}

	public void setSurveyFileNo(String surveyFileNo) {
		this.surveyFileNo = surveyFileNo;
	}

	public String getSurveyTruth() {
		return surveyTruth;
	}

	public void setSurveyTruth(String surveyTruth) {
		this.surveyTruth = surveyTruth;
	}

	public byte getDealType() {
		return dealType;
	}

	public void setDealType(byte dealType) {
		this.dealType = dealType;
	}

	public Long getSurveyCreator() {
		return surveyCreator;
	}

	public void setSurveyCreator(Long surveyCreator) {
		this.surveyCreator = surveyCreator;
	}

	public Date getSurveyActualTime() {
		return surveyActualTime;
	}

	public void setSurveyActualTime(Date surveyActualTime) {
		this.surveyActualTime = surveyActualTime;
	}

	public Date getSurveyTime() {
		return surveyTime;
	}

	public void setSurveyTime(Date surveyTime) {
		this.surveyTime = surveyTime;
	}

	public String getSurveyRemark() {
		return surveyRemark;
	}

	public void setSurveyRemark(String surveyRemark) {
		this.surveyRemark = surveyRemark;
	}
	
	public String getDealTypeName() {
		return CodeMapUtils.getCodeName(SignCardSurvey.class, "DEALTYPE", getDealType());
	}
}
