package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.opoo.apps.Labelable;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;
import cn.redflagsoft.base.util.CodeMapUtils;

import com.googlecode.jsonplugin.annotations.JSON;

@ObjectType(ObjectTypes.CAUTION)
public class Caution extends LifeStageableObject implements Labelable, Cloneable /*VersionableBean*//* implements Observable*/ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final byte RECTIFICATION_TYPE__延期 = 1;
	public static final byte RECTIFICATION_TYPE__办结 = 2;
	
	public static final int OBJECT_TYPE = ObjectTypes.CAUTION;

	private String code;			//警示编号 CODE  VARCHAR2(60) 
	
	 private String name;			//	警示名称  NAME VARCHAR2(80) 
	
	 private byte grade;			//	警示级别  GRADE  NUMBER(3) 	
	 
	 private Date happenTime;			//	产生时间 HAPPENTIME  TIMESTAMP 
 
	 private Long riskId;				//	监察ID RISKID  NUMBER(19) 
	 
	 private String riskCode;			//	监察编号 	 RISKCODE 	 VARCHAR2(60) 

	 private String riskName;			//	监察名称 
	 
	 private int riskType;				//	监察类别 
	 
	 private Long ruleId;				//	监察规则ID 
	 
	 private String ruleCode;			//	监察规则编号 
	 
	 private Long superviseOrgId;		//	监察单位ID 
	 
	 private String superviseOrgAbbr;	//	监察单位简称 
	 
	 private Long superviseClerkId;		//	监察单位人员ID 
	 
	 private String superviseClerkName;	//	监察单位人员姓名 
	 
	 private int objType;			//	监察对象类型 
	 
	 private Long objId;				//	监察对象ID 
	 
	 private String objCode;			//	监察对象编码 
	 
	 private String objName;			//	监察对象名称 
	 
	 private String objAttr;			//	监察内容对象属性 
	 
	 private String objAttrName;		//	监察内容对象属性名称 
	 
	 private java.math.BigDecimal objAttrValue;		//	监察内容对象属性值 
	 
	 private byte objAttrUnit = RiskRule.VALUE_UNIT_WORKDAY;		//	监察内容对象属性值的单位 
	 
	 private BigDecimal scaleValue;			//	相应级别的标度值 
	 
	 private Long dutyerOrgId;			//	责任单位ID 
	 
	 private String dutyerOrgName;		//	责任单位名称 
	 
	 private Long dutyerLeaderId;		//	责任单位分管领导ID 
	 
	 private String dutyerLeaderName;	//	责任单位分管领导姓名 
	 
	 private String dutyerLeaderMobNo;	//	责任单位分管领导手机号码 
	 
	 private Long dutyerDeptId;			//	责任部门ID 
	 
	 private String dutyerDeptName;		//	责任部门名称 
	 
	 private Long dutyerManagerId;		//	责任部门业务主管ID，直属上级 
	 
	 private String dutyerManagerName;	//	责任部门业务主管姓名 ，直属上级
	 
	 private String dutyerManagerMobNo;	//	责任部门业务主管手机号码 ，直属上级
	 
	 private Long dutyerId;				//	责任人ID 
	 
	 private String dutyerName;			//	责任人姓名 
	 
	 private String dutyerMobNo;		//	责任人手机号码 
	 
	 private String conclusion;			//	监察结论 
	 
	 private String juralLimit;			//	监察依据 
	 
	 private int messageConfig;		//	监察消息发送 
	 
	 private String messageTemplate;		//	监察消息模板 
	 
	 private byte pause;				//	暂停 
	 
	 private int refObjectType;			//	相关对象类型 
	 
	 private Long refObjectId;			//	 相关对象ID 
	 
	 private String refObjectName;		// 相关对象名称
	 
	 private Long systemId;				//	系统编号 
	 
	 // 2012-05-15(新增字段)
	 private String ruleSummary;		//监察规则
	 private String bizSummary;			//业务状况
	 
	// 2012-05-16(新增字段)
	 private String cautionSummary;		//警示概况
	 private Long regClerkId;			//警示登记人ID
	 private String regClerkName;		//警示登记人姓名
	 
	 private byte rectificationType = 0;	//整改类型  
	 private String rectificationDesc;	//整改说明
	 private Date rectificationActualTime;	//整改时间
	 private Long rectificationClerkId;		//整改人ID
	 private String rectificationClerkName;	//整改人Name
	 
	 private Long rectificationOrgId;		//整改单位ID
	 private String rectificationOrgName;	//整改单位Name
	 private Date rectificationTime;		//整改系统操作时间
	 private String rectificationRemark;	//整改备注
	 
	public Date getRectificationActualTime() {
		return rectificationActualTime;
	}

	public void setRectificationActualTime(Date rectificationActualTime) {
		this.rectificationActualTime = rectificationActualTime;
	}

	public Long getRectificationOrgId() {
		return rectificationOrgId;
	}

	public void setRectificationOrgId(Long rectificationOrgId) {
		this.rectificationOrgId = rectificationOrgId;
	}

	public String getRectificationOrgName() {
		return rectificationOrgName;
	}

	public void setRectificationOrgName(String rectificationOrgName) {
		this.rectificationOrgName = rectificationOrgName;
	}

	public Date getRectificationTime() {
		return rectificationTime;
	}

	public void setRectificationTime(Date rectificationTime) {
		this.rectificationTime = rectificationTime;
	}

	public String getRectificationRemark() {
		return rectificationRemark;
	}

	public void setRectificationRemark(String rectificationRemark) {
		this.rectificationRemark = rectificationRemark;
	}

	public byte getRectificationType() {
		return rectificationType;
	}

	public void setRectificationType(byte rectificationType) {
		this.rectificationType = rectificationType;
	}

	public String getRectificationDesc() {
		return rectificationDesc;
	}

	public void setRectificationDesc(String rectificationDesc) {
		this.rectificationDesc = rectificationDesc;
	}

	public Long getRectificationClerkId() {
		return rectificationClerkId;
	}

	public void setRectificationClerkId(Long rectificationClerkId) {
		this.rectificationClerkId = rectificationClerkId;
	}

	public String getRectificationClerkName() {
		return rectificationClerkName;
	}

	public void setRectificationClerkName(String rectificationClerkName) {
		this.rectificationClerkName = rectificationClerkName;
	}

	public String getCautionSummary() {
		return cautionSummary;
	}

	public void setCautionSummary(String cautionSummary) {
		this.cautionSummary = cautionSummary;
	}


	public Long getRegClerkId() {
		return regClerkId;
	}


	public void setRegClerkId(Long regClerkId) {
		this.regClerkId = regClerkId;
	}


	public String getRegClerkName() {
		return regClerkName;
	}


	public void setRegClerkName(String regClerkName) {
		this.regClerkName = regClerkName;
	}


	public String getRuleSummary() {
		return ruleSummary;
	}


	public void setRuleSummary(String ruleSummary) {
		this.ruleSummary = ruleSummary;
	}


	public String getBizSummary() {
		return bizSummary;
	}


	public void setBizSummary(String bizSummary) {
		this.bizSummary = bizSummary;
	}


	public String getTypeName() {
		return Risk.getGradeName(getGrade());
	}
	
	public void setType(int type){
		super.setType(type);
	}
		
	 public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public byte getGrade() {
		return grade;
	}
	
	public String getGradeName() {
		//return CodeMapUtils.getCodeName((short)1075, getGrade());
		return Risk.getGradeName(getGrade());
	}
	
	public String getGradeExplain(){
		return Risk.getGradeExplain(getGrade());
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

	public Long getRiskId() {
		return riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public int getRiskType() {
		return riskType;
	}

	public void setRiskType(int riskType) {
		this.riskType = riskType;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public Long getSuperviseOrgId() {
		return superviseOrgId;
	}

	public void setSuperviseOrgId(Long superviseOrgId) {
		this.superviseOrgId = superviseOrgId;
	}

	public String getSuperviseOrgAbbr() {
		return superviseOrgAbbr;
	}

	public void setSuperviseOrgAbbr(String superviseOrgAbbr) {
		this.superviseOrgAbbr = superviseOrgAbbr;
	}

	public Long getSuperviseClerkId() {
		return superviseClerkId;
	}

	public void setSuperviseClerkId(Long superviseClerkId) {
		this.superviseClerkId = superviseClerkId;
	}

	public String getSuperviseClerkName() {
		return superviseClerkName;
	}

	public void setSuperviseClerkName(String superviseClerkName) {
		this.superviseClerkName = superviseClerkName;
	}

	public int getObjType() {
		return objType;
	}

	public void setObjType(int objectType) {
		this.objType = objectType;
	}
	
	public String getObjTypeName() {
		return "("+CodeMapUtils.getCodeName((short)172, getObjType()) +")";
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objectId) {
		this.objId = objectId;
	}

	public String getObjCode() {
		return objCode;
	}

	public void setObjCode(String objectCode) {
		this.objCode = objectCode;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objectName) {
		this.objName = objectName;
	}

	public String getObjAttr() {
		return objAttr;
	}

	public void setObjAttr(String objectAttr) {
		this.objAttr = objectAttr;
	}

	public String getObjAttrName() {
		return objAttrName;
	}

	public void setObjAttrName(String objectAttrName) {
		this.objAttrName = objectAttrName;
	}
//	changed 2011-10-12
//	public String getObjectAttrNameLabel(){
//		return "("+getObjectAttrName() + getObjectAttrUnitName()+")";
//	}
	
	public String getObjAttrValueLabel(){
		return getObjAttrValue() + getObjAttrUnitName();
	}
	
	public java.math.BigDecimal getObjAttrValue() {
		return objAttrValue;
	}

	public void setObjAttrValue(java.math.BigDecimal objectAttrValue) {
		this.objAttrValue = objectAttrValue;
	}

	public byte getObjAttrUnit() {
		return objAttrUnit;
	}

	public void setObjAttrUnit(byte objectAttrUnit) {
		this.objAttrUnit = objectAttrUnit;
	}
	
	public String getObjAttrUnitName(){
		return CodeMapUtils.getCodeName((short)1072, getObjAttrUnit());
	}

	public java.math.BigDecimal getScaleValue() {
		return scaleValue;
	}

	public void setScaleValue(java.math.BigDecimal scaleValue) {
		this.scaleValue = scaleValue;
	}

	public Long getDutyerOrgId() {
		return dutyerOrgId;
	}

	public void setDutyerOrgId(Long dutyerOrgId) {
		this.dutyerOrgId = dutyerOrgId;
	}

	public String getDutyerOrgName() {
		return dutyerOrgName;
	}

	public void setDutyerOrgName(String dutyerOrgName) {
		this.dutyerOrgName = dutyerOrgName;
	}

	public Long getDutyerLeaderId() {
		return dutyerLeaderId;
	}

	public void setDutyerLeaderId(Long dutyerLeaderId) {
		this.dutyerLeaderId = dutyerLeaderId;
	}

	public String getDutyerLeaderName() {
		return dutyerLeaderName;
	}

	public void setDutyerLeaderName(String dutyerLeaderName) {
		this.dutyerLeaderName = dutyerLeaderName;
	}

	public String getDutyerLeaderMobNo() {
		return dutyerLeaderMobNo;
	}

	public void setDutyerLeaderMobNo(String dutyerLeaderMobNo) {
		this.dutyerLeaderMobNo = dutyerLeaderMobNo;
	}

	public Long getDutyerDeptId() {
		return dutyerDeptId;
	}

	public void setDutyerDeptId(Long dutyerDeptId) {
		this.dutyerDeptId = dutyerDeptId;
	}

	public String getDutyerDeptName() {
		return dutyerDeptName;
	}

	public void setDutyerDeptName(String dutyerDeptName) {
		this.dutyerDeptName = dutyerDeptName;
	}

	public Long getDutyerManagerId() {
		return dutyerManagerId;
	}

	public void setDutyerManagerId(Long dutyerManagerId) {
		this.dutyerManagerId = dutyerManagerId;
	}

	public String getDutyerManagerName() {
		return dutyerManagerName;
	}

	public void setDutyerManagerName(String dutyerManagerName) {
		this.dutyerManagerName = dutyerManagerName;
	}

	public String getDutyerManagerMobNo() {
		return dutyerManagerMobNo;
	}

	public void setDutyerManagerMobNo(String dutyerManagerMobNo) {
		this.dutyerManagerMobNo = dutyerManagerMobNo;
	}

	public Long getDutyerId() {
		return dutyerId;
	}

	public void setDutyerId(Long dutyerId) {
		this.dutyerId = dutyerId;
	}

	public String getDutyerName() {
		return dutyerName;
	}

	public void setDutyerName(String dutyerName) {
		this.dutyerName = dutyerName;
	}

	public String getDutyerMobNo() {
		return dutyerMobNo;
	}

	public void setDutyerMobNo(String dutyerMobNo) {
		this.dutyerMobNo = dutyerMobNo;
	}

	/**
	 * @return the conclusion
	 */
	public String getConclusion() {
		return conclusion;
	}

	/**
	 * @param conclusion the conclusion to set
	 */
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getJuralLimit() {
		return juralLimit;
	}

	public void setJuralLimit(String juralLimit) {
		this.juralLimit = juralLimit;
	}

	public int getMessageConfig() {
		return messageConfig;
	}

	public void setMessageConfig(int messageConfig) {
		this.messageConfig = messageConfig;
	}


	/**
	 * @return the messageTemplate
	 */
	public String getMessageTemplate() {
		return messageTemplate;
	}

	/**
	 * @param messageTemplate the messageTemplate to set
	 */
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public byte getPause() {
		return pause;
	}

	public void setPause(byte pause) {
		this.pause = pause;
	}

	public int getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(int refObjectType) {
		this.refObjectType = refObjectType;
	}

	public Long getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

    /**
     * 是否暂停
     * @return
     */
    public String getPauseName(){
        //return  CodeMapUtils.getCodeName((short)1079, getPause());
    	
    	if(getPause() == (byte)1){
    		return "(暂停)";
    	}else {
    		return null;
    	}
    }

    public String getBizStatusName(){
    	return  CodeMapUtils.getCodeName((short)1076, getBizStatus());
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

//	public Long getObjectID() {
//		return null;
//	}
//
//	public void setObjectID(Long id) {
//		
//	}

	public int getObjectType() {
		return OBJECT_TYPE;
	}


	public Serializable getData() {
		return this.getId();
	}


	public String getLabel() {
		return this.getName();
	}
	
	public String getRiskTypeName(){
		return  CodeMapUtils.getCodeName((short)1073, getRiskType());
	}
}
