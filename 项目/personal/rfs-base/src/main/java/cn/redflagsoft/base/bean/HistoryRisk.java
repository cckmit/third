package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.opoo.ndao.Domain;

public class HistoryRisk extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3996430576910043620L;
	
    private short category;
    private String thingCode;
    private String thingName;
	    
	private Long taskSn;
	private String taskName;
	private String entityName;
	private Date taskBeginTime;
	private Date taskEndTime;
	private byte taskStatus;
	private String taskStatusName;
	private String taskCode;
	private Long id;
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

	public Long getTaskSn() {
		return taskSn;
	}

	public void setTaskSn(Long taskSn) {
		this.taskSn = taskSn;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public byte getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(byte taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskStatusName() {
		return taskStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.taskStatusName = taskStatusName;
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

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
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
}
