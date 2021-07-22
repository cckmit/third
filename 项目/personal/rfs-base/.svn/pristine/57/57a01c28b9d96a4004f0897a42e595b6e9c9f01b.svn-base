package cn.redflagsoft.base.bean;

import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;


/**
 * 警示调查约谈
 * @author Administrator
 *
 */
public class CautionSurvey extends VersionableBean implements RFSItemable{

	private static final long serialVersionUID = 1L;

	private Long Id;
	private Long cautionId;                  //警示id
	private String cautionName;             //警示名称
	private String cautionCode;             //警示编号

	private String surveyFileNo;            //调查约谈文件编号
	private Long surveyFileId;              //调查约谈文件id
	
	private Long surveyOrgId;               //调查约谈单位id
	private String surveyOrgName;           //调查约谈单位名称
	private Date surveyTime;                 //调查约谈时间

	public Long getCautionId() {
		return cautionId;
	}

	public void setCautionId(Long cautionId) {
		this.cautionId = cautionId;
	}

	public String getCautionName() {
		return cautionName;
	}

	public void setCautionName(String cautionName) {
		this.cautionName = cautionName;
	}

	public String getCautionCode() {
		return cautionCode;
	}

	public void setCautionCode(String cautionCode) {
		this.cautionCode = cautionCode;
	}

	public String getSurveyFileNo() {
		return surveyFileNo;
	}

	public void setSurveyFileNo(String surveyFileNo) {
		this.surveyFileNo = surveyFileNo;
	}

	public Long getSurveyFileId() {
		return surveyFileId;
	}

	public void setSurveyFileId(Long surveyFileId) {
		this.surveyFileId = surveyFileId;
	}


	public Long getSurveyOrgId() {
		return surveyOrgId;
	}

	public void setSurveyOrgId(Long surveyOrgId) {
		this.surveyOrgId = surveyOrgId;
	}

	public String getSurveyOrgName() {
		return surveyOrgName;
	}

	public void setSurveyOrgName(String surveyOrgName) {
		this.surveyOrgName = surveyOrgName;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getSurveyTime() {
		return surveyTime;
	}

	public void setSurveyTime(Date surveyTime) {
		this.surveyTime = surveyTime;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
	
	public CautionSurvey(Long cautionId, String cautionName) {
		this.cautionId = cautionId;
		this.cautionName = cautionName;
	}
	
	public CautionSurvey() {
	}
	
	public int getObjectType() {
		return ObjectTypes.OBJECT_CAUTIONSURVEY;
	}
}
