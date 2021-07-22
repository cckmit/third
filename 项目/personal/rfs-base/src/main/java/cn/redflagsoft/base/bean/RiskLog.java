/*
 * $Id: RiskLog.java 6234 2013-06-07 04:06:05Z thh $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alex Lin
 *
 */
public class RiskLog extends VersionableBean implements Observable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6219187341050176827L;

	public static final Log log = LogFactory.getLog(RiskLog.class);


	private Long id;
	private short category;
	private String thingCode;
	private String thingName;
	
	private int objectType;
	private Long objectID; 
	private short dutyerType;
	private Long dutyerID;
	private byte valueSign;
	private byte valueType;
	private byte valueUnit;
	private String valueFormat;
	private byte valueSource;
	private BigDecimal value;
	private byte grade;
	private BigDecimal scaleValue1;
	private BigDecimal scaleValue2;
	private BigDecimal scaleValue3;
	private BigDecimal scaleValue4;
	private BigDecimal scaleValue5;
	private BigDecimal scaleValue6;
	private byte scaleMark;
	private int refType;
	private Long refID;
	private byte pause;
	private Long systemID;
	private String objectAttr;
	private BigDecimal scaleValue;
	private int refObjectType;
	private Long refObjectId;
	private String name;
    private String juralLimit;
    
    private String conclusionTpl1;
    private String conclusionTpl2;
    private String conclusionTpl3;
    private String conclusionTpl4;
    private String conclusionTpl5;
    private String conclusionTpl6;
    private String conclusion;
    
    private String dutyerName;
    private String refTaskTypeName;												
    private Long ruleID;
    private String content;														
    private String itemID;
    private String itemName;
    private Long taskSN;
    private String taskName;
    //新加字段
	private Long projectID;
	private String projectSn;
	private String projectName;
	private Integer timeUsed;
	private Integer timeRemain;
	private Integer busiType;
	private Long p3ID;
	private BigDecimal initValue;
	private Date busiStartTime; //实际开始时间
	private Date busiEndTime;	//实际结束时间
	
	//2012-02-24
	private Long superviseOrgId;						//    监察单位ID
	private String superviseOrgAbbr;					//    监察单位简称	
	private Long dutyerOrgId;							//    责任单位ID	
	private String dutyerOrgName;						//    责任单位名称	
	private Long dutyerDeptId;							//    责任部门ID	
	private String dutyerDeptName;						//    责任部门名称	
	private Long dutyerLeaderId; // 责任单位分管领导ID
	private String dutyerLeaderName; // 责任单位分管领导姓名
	private String dutyerLeaderMobNo; // 责任单位分管领导手机号码
	private Long dutyerManagerId; // 责任部门业务主管ID，直属上级
	private String dutyerManagerName; // 责任部门业务主管姓名 ，直属上级
	private String dutyerManagerMobNo; // 责任部门业务主管手机号码 ，直属上级
	private String dutyerMobNo; // 责任人手机号码
	
	private String objectCode;							//task code
	
	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
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

	public BigDecimal getInitValue() {
		return initValue;
	}

	public void setInitValue(BigDecimal initValue) {
		this.initValue = initValue;
	}
	
	public Long getP3ID() {
		return p3ID;
	}
	public void setP3ID(Long p3id) {
		p3ID = p3id;
	}
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	public Integer getTimeUsed() {
		return timeUsed;
	}
	public void setTimeUsed(Integer timeUsed) {
		this.timeUsed = timeUsed;
	}
	public Integer getTimeRemain() {
		return timeRemain;
	}
	public void setTimeRemain(Integer timeRemain) {
		this.timeRemain = timeRemain;
	}
	public Long getProjectID() {
		return projectID;
	}
	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}
	public String getProjectSn() {
		return projectSn;
	}
	public void setProjectSn(String projectSn) {
		this.projectSn = projectSn;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getTaskSN() {
		return taskSN;
	}
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
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
	public byte getValueSign() {
		return valueSign;
	}
	public void setValueSign(byte valueSign) {
		this.valueSign = valueSign;
	}
	public byte getValueType() {
		return valueType;
	}
	public void setValueType(byte valueType) {
		this.valueType = valueType;
	}
	public byte getValueUnit() {
		return valueUnit;
	}
	public void setValueUnit(byte valueUnit) {
		this.valueUnit = valueUnit;
	}
	public String getValueFormat() {
		return valueFormat;
	}
	public void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
	}
	public byte getValueSource() {
		return valueSource;
	}
	public void setValueSource(byte valueSource) {
		this.valueSource = valueSource;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public byte getGrade() {
		return grade;
	}
	public void setGrade(byte grade) {
		this.grade = grade;
	}
	public BigDecimal getScaleValue1() {
		return scaleValue1;
	}
	public void setScaleValue1(BigDecimal scaleValue1) {
		this.scaleValue1 = scaleValue1;
	}
	public BigDecimal getScaleValue2() {
		return scaleValue2;
	}
	public void setScaleValue2(BigDecimal scaleValue2) {
		this.scaleValue2 = scaleValue2;
	}
	public BigDecimal getScaleValue3() {
		return scaleValue3;
	}
	public void setScaleValue3(BigDecimal scaleValue3) {
		this.scaleValue3 = scaleValue3;
	}
	public BigDecimal getScaleValue4() {
		return scaleValue4;
	}
	public void setScaleValue4(BigDecimal scaleValue4) {
		this.scaleValue4 = scaleValue4;
	}
	public BigDecimal getScaleValue5() {
		return scaleValue5;
	}
	public void setScaleValue5(BigDecimal scaleValue5) {
		this.scaleValue5 = scaleValue5;
	}
	public BigDecimal getScaleValue6() {
		return scaleValue6;
	}
	public void setScaleValue6(BigDecimal scaleValue6) {
		this.scaleValue6 = scaleValue6;
	}
	public byte getScaleMark() {
		return scaleMark;
	}
	public void setScaleMark(byte scaleMark) {
		this.scaleMark = scaleMark;
	}
	public int getRefType() {
		return refType;
	}
	public void setRefType(int refType) {
		this.refType = refType;
	}
	public Long getRefID() {
		return refID;
	}
	public void setRefID(Long refID) {
		this.refID = refID;
	}
	public byte getPause() {
		return pause;
	}
	public void setPause(byte pause) {
		this.pause = pause;
	}
	public Long getSystemID() {
		return systemID;
	}
	public void setSystemID(Long systemID) {
		this.systemID = systemID;
	}
	public String getObjectAttr() {
		return objectAttr;
	}
	public void setObjectAttr(String objectAttr) {
		this.objectAttr = objectAttr;
	}
	public BigDecimal getScaleValue() {
		return scaleValue;
	}
	public void setScaleValue(BigDecimal scaleValue) {
		this.scaleValue = scaleValue;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJuralLimit() {
		return juralLimit;
	}
	public void setJuralLimit(String juralLimit) {
		this.juralLimit = juralLimit;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getDutyerName() {
		return dutyerName;
	}
	public void setDutyerName(String dutyerName) {
		this.dutyerName = dutyerName;
	}
	public String getRefTaskTypeName() {
		return refTaskTypeName;
	}
	public void setRefTaskTypeName(String refTaskTypeName) {
		this.refTaskTypeName = refTaskTypeName;
	}
	public Long getRuleID() {
		return ruleID;
	}
	public void setRuleID(Long ruleID) {
		this.ruleID = ruleID;
	}

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public String getThingCode() {
        return thingCode;
    }

    public void setThingCode(String thingCode) {
        this.thingCode = thingCode;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getConclusionTpl1() {
        return conclusionTpl1;
    }

    public void setConclusionTpl1(String conclusionTpl1) {
        this.conclusionTpl1 = conclusionTpl1;
    }

    public String getConclusionTpl2() {
        return conclusionTpl2;
    }

    public void setConclusionTpl2(String conclusionTpl2) {
        this.conclusionTpl2 = conclusionTpl2;
    }

    public String getConclusionTpl3() {
        return conclusionTpl3;
    }

    public void setConclusionTpl3(String conclusionTpl3) {
        this.conclusionTpl3 = conclusionTpl3;
    }

    public String getConclusionTpl4() {
        return conclusionTpl4;
    }

    public void setConclusionTpl4(String conclusionTpl4) {
        this.conclusionTpl4 = conclusionTpl4;
    }

    public String getConclusionTpl5() {
        return conclusionTpl5;
    }

    public void setConclusionTpl5(String conclusionTpl5) {
        this.conclusionTpl5 = conclusionTpl5;
    }

    public String getConclusionTpl6() {
        return conclusionTpl6;
    }

    public void setConclusionTpl6(String conclusionTpl6) {
        this.conclusionTpl6 = conclusionTpl6;
    }

	/**
	 * @return the superviseOrgId
	 */
	public Long getSuperviseOrgId() {
		return superviseOrgId;
	}

	/**
	 * @param superviseOrgId the superviseOrgId to set
	 */
	public void setSuperviseOrgId(Long superviseOrgId) {
		this.superviseOrgId = superviseOrgId;
	}

	/**
	 * @return the superviseOrgAbbr
	 */
	public String getSuperviseOrgAbbr() {
		return superviseOrgAbbr;
	}

	/**
	 * @param superviseOrgAbbr the superviseOrgAbbr to set
	 */
	public void setSuperviseOrgAbbr(String superviseOrgAbbr) {
		this.superviseOrgAbbr = superviseOrgAbbr;
	}

	/**
	 * @return the dutyerOrgId
	 */
	public Long getDutyerOrgId() {
		return dutyerOrgId;
	}

	/**
	 * @param dutyerOrgId the dutyerOrgId to set
	 */
	public void setDutyerOrgId(Long dutyerOrgId) {
		this.dutyerOrgId = dutyerOrgId;
	}

	/**
	 * @return the dutyerOrgName
	 */
	public String getDutyerOrgName() {
		return dutyerOrgName;
	}

	/**
	 * @param dutyerOrgName the dutyerOrgName to set
	 */
	public void setDutyerOrgName(String dutyerOrgName) {
		this.dutyerOrgName = dutyerOrgName;
	}

	/**
	 * @return the dutyerDeptId
	 */
	public Long getDutyerDeptId() {
		return dutyerDeptId;
	}

	/**
	 * @param dutyerDeptId the dutyerDeptId to set
	 */
	public void setDutyerDeptId(Long dutyerDeptId) {
		this.dutyerDeptId = dutyerDeptId;
	}

	/**
	 * @return the dutyerDeptName
	 */
	public String getDutyerDeptName() {
		return dutyerDeptName;
	}

	/**
	 * @param dutyerDeptName the dutyerDeptName to set
	 */
	public void setDutyerDeptName(String dutyerDeptName) {
		this.dutyerDeptName = dutyerDeptName;
	}

	/**
	 * @return the dutyerLeaderId
	 */
	public Long getDutyerLeaderId() {
		return dutyerLeaderId;
	}

	/**
	 * @param dutyerLeaderId the dutyerLeaderId to set
	 */
	public void setDutyerLeaderId(Long dutyerLeaderId) {
		this.dutyerLeaderId = dutyerLeaderId;
	}

	/**
	 * @return the dutyerLeaderName
	 */
	public String getDutyerLeaderName() {
		return dutyerLeaderName;
	}

	/**
	 * @param dutyerLeaderName the dutyerLeaderName to set
	 */
	public void setDutyerLeaderName(String dutyerLeaderName) {
		this.dutyerLeaderName = dutyerLeaderName;
	}

	/**
	 * @return the dutyerLeaderMobNo
	 */
	public String getDutyerLeaderMobNo() {
		return dutyerLeaderMobNo;
	}

	/**
	 * @param dutyerLeaderMobNo the dutyerLeaderMobNo to set
	 */
	public void setDutyerLeaderMobNo(String dutyerLeaderMobNo) {
		this.dutyerLeaderMobNo = dutyerLeaderMobNo;
	}

	/**
	 * @return the dutyerManagerId
	 */
	public Long getDutyerManagerId() {
		return dutyerManagerId;
	}

	/**
	 * @param dutyerManagerId the dutyerManagerId to set
	 */
	public void setDutyerManagerId(Long dutyerManagerId) {
		this.dutyerManagerId = dutyerManagerId;
	}

	/**
	 * @return the dutyerManagerName
	 */
	public String getDutyerManagerName() {
		return dutyerManagerName;
	}

	/**
	 * @param dutyerManagerName the dutyerManagerName to set
	 */
	public void setDutyerManagerName(String dutyerManagerName) {
		this.dutyerManagerName = dutyerManagerName;
	}

	/**
	 * @return the dutyerManagerMobNo
	 */
	public String getDutyerManagerMobNo() {
		return dutyerManagerMobNo;
	}

	/**
	 * @param dutyerManagerMobNo the dutyerManagerMobNo to set
	 */
	public void setDutyerManagerMobNo(String dutyerManagerMobNo) {
		this.dutyerManagerMobNo = dutyerManagerMobNo;
	}

	/**
	 * @return the dutyerMobNo
	 */
	public String getDutyerMobNo() {
		return dutyerMobNo;
	}

	/**
	 * @param dutyerMobNo the dutyerMobNo to set
	 */
	public void setDutyerMobNo(String dutyerMobNo) {
		this.dutyerMobNo = dutyerMobNo;
	}
    
}