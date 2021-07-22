/*
 * $Id: Risk.java 6123 2012-11-20 03:54:11Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.DisplayName;
import cn.redflagsoft.base.service.RiskCalculator;
import cn.redflagsoft.base.util.CodeMapUtils;
import cn.redflagsoft.base.util.DateUtil;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 监察（风险）。
 * @author Alex Lin
 * @see RiskRule
 */
public class Risk extends VersionableBean implements Observable {
	public static final Log log = LogFactory.getLog(Risk.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private static final long serialVersionUID = 2752231991326653433L;
	public static final byte GRADE_NORMAL = 0;
	public static final byte GRADE_WHITE = 1;
	public static final byte GRADE_BLUE = 2;
	public static final byte GRADE_YELLOW = 3;
	public static final byte GRADE_ORANGE = 4;
	public static final byte GRADE_RED = 5;
	public static final byte GRADE_BLACK = 6;
	public static final byte MAX_GRADE = GRADE_BLACK;
	
	/**
	 * 是 1
	 */
	@DisplayName("是")
	public static final byte PAUSE_YES = 1;
	/**
	 * 否 0
	 */
	@DisplayName("否")
	public static final byte PAUSE_NO = 0;
	
	
	//Status定义：0待用；1在用；2结束；3暂停。默认1。对象主体结束时停用
	
	/**
	 * 备用 0
	 */
	@DisplayName("待用")
	public static final byte STATUS_STORE = 0;
	/**
	 * 在用 1
	 */
	@DisplayName("在用")
	public static final byte STATUS_ON_USE = 1;
	/**
	 * 结束 2
	 */
	@DisplayName("结束")
	public static final byte STATUS_FINISH = 2;
	/**
	 * 停用 3
	 */
	@DisplayName("停用")
	public static final byte STATUS_UN_USE = 3;
	
	
	/**
	 * 业务对象1001.
	 */
	public static final int OBJECT_TYPE_PROJECT = 1001;
	
	/**
	 * Task 102.
	 */
	public static final int OBJECT_TYPE_TASK = ObjectTypes.TASK;
	
	//11-上级对本级监察；21-本级内部监察；31-本级对下级监察
	@DisplayName("上级对本级监察")
	public static final short CATEGORY_HIGHER_DEPARTMENT = 11;
	@DisplayName("本级内部监察")
	public static final short CATEGORY_INNER_DEPARTMENT = 21;
	@DisplayName("本级对下级监察")
	public static final short CATEGORY_LOWER_DEPARTMENT = 31;
	
	
	private Long id;
	private short category;
	private String thingCode;
	private String thingName;
	private int objectType;
	
	private Long objectID; 
	private short dutyerType;
	private Long dutyerID;
	private byte valueSign = RiskRule.VALUE_SIGN_POSITIVE;
	private byte valueType = RiskRule.VALUE_TYPE_NUMBER;
	private byte valueUnit = RiskRule.VALUE_UNIT_WORKDAY;
	private String valueFormat;
	private byte valueSource = RiskRule.VALUE_SOURCE_EXTERIOR;
	private BigDecimal value;
	private byte grade;
	private BigDecimal scaleValue1;
	private BigDecimal scaleValue2;
	private BigDecimal scaleValue3;
	private BigDecimal scaleValue4;
	private BigDecimal scaleValue5;
	private BigDecimal scaleValue6;
	private byte scaleMark;
	private int refType;//根据objectType和Type确定含义。
	private Long refID;//根据objectType和Type确定含义。
	private byte pause = PAUSE_NO;
	private Long systemID;
	private String objectAttr;
	private BigDecimal scaleValue;
	private int refObjectType;
	private Long refObjectId;
	private String refObjectName;
	private String name;
    private String juralLimit;
    
//    private String conclusionTpl1;
//    private String conclusionTpl2;
//    private String conclusionTpl3;
//    private String conclusionTpl4;
//    private String conclusionTpl5;
//    private String conclusionTpl6;
    private String conclusion;
    
    private String dutyerName;
    private String refTaskTypeName;
    private Long ruleID;
    @Deprecated
    private boolean valueChanged;
    private BigDecimal initValue;
    private byte status = STATUS_ON_USE;
    
    
	private String code;								//    编号	
	private String objectCode; 							//    对象编码	
	private String objectName;							//    对象名称	
	private String objectAttrName;						//    被监控对象属性名称	
	private Long superviseOrgId;						//    监察单位ID
	private String superviseOrgAbbr;					//    监察单位简称	
	private Long dutyerOrgId;							//    责任单位ID	
	private String dutyerOrgName;						//    责任单位名称	
	private Long dutyerDeptId;							//    责任部门ID	
	private String dutyerDeptName;						//    责任部门名称	
	private Date lastRunTime;							//    最近监察时间	
//	private String messageTemplate1;					//    默认消息模板1	
//	private String messageTemplate2;					//    默认消息模板2	
//	private String messageTemplate3;					//    默认消息模板3	
//	private String messageTemplate4;					//    默认消息模板4	
//	private String messageTemplate5;					//    默认消息模板5	
//	private String messageTemplate6;					//    默认消息模板6	
	private int messageConfig1 = 1;							//    默认消息发送1	
	private int messageConfig2 = 3;							//    默认消息发送2	
	private int messageConfig3 = 7;							//    默认消息发送3	
	private int messageConfig4 = 7;							//    默认消息发送4
	private int messageConfig5 = 7;							//    默认消息发送5	
	private int messageConfig6 = 31;							//    默认消息发送6	
	private Date beginTime;								//    开始时间	
	private Date hangTime;								//    暂停时间	
	private Date wakeTime;								//    唤醒时间
	private Date endTime;								//    结束时间
	
//	private String dutyerName;							//    责任人姓名	DUTYERNAME
	
	private String ruleCode;							//    规则编号
	private String ruleName;							//规则名称
	
	private Date gradeChangedTime;
	private Date valueChangedTime;
	//不持久化
	private Date valueCalculateTime;	//计算时间，通常在计算前设置，不持久化到数据库
	
	
	 //2012-02-24 三级责任人信息
	private Long dutyerLeaderId;		//	责任单位分管领导ID 
	private String dutyerLeaderName;	//	责任单位分管领导姓名 
	private String dutyerLeaderMobNo;	//	责任单位分管领导手机号码 
	private Long dutyerManagerId;		//	责任部门业务主管ID，直属上级 
	private String dutyerManagerName;	//	责任部门业务主管姓名 ，直属上级
	private String dutyerManagerMobNo;	//	责任部门业务主管手机号码 ，直属上级
	private String dutyerMobNo;		//	责任人手机号码 
	private String refObjectSn;	 	// 相关对象的SN
	//业务事项
    private String itemID;		//业务事项ID
    private String itemName;	//业务事项名称
    
    // 2012-05-15(新增字段)
    private String ruleSummary;			//监察规则
    private String bizSummary;			//业务状况
    
    //2012-05-16(新增字段)
    private Long superviseClerkId;		//监察人ID
    private String superviseClerkName;	//监察人姓名
    
    //规则类型
    private int ruleType;
   
	/**
	 * 
	 */
	public Risk() {
		super();
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

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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

	public Long getDutyerOrgId() {
		return dutyerOrgId;
	}

	public void setDutyerOrgId(Long dutyerOrgId) {
		this.dutyerOrgId = dutyerOrgId;
	}

	public Long getDutyerDeptId() {
		return dutyerDeptId;
	}

	public void setDutyerDeptId(Long dutyerDeptId) {
		this.dutyerDeptId = dutyerDeptId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectAttrName() {
		return objectAttrName;
	}

	public void setObjectAttrName(String objectAttrName) {
		this.objectAttrName = objectAttrName;
	}

	

	public String getDutyerOrgName() {
		return dutyerOrgName;
	}

	public void setDutyerOrgName(String dutyerOrgName) {
		this.dutyerOrgName = dutyerOrgName;
	}


	public String getDutyerDeptName() {
		return dutyerDeptName;
	}

	public void setDutyerDeptName(String dutyerDeptName) {
		this.dutyerDeptName = dutyerDeptName;
	}


//	public String getMessageTemplate1() {
//		return messageTemplate1;
//	}
//
//	public void setMessageTemplate1(String messageTemplate1) {
//		this.messageTemplate1 = messageTemplate1;
//	}
//
//	public String getMessageTemplate2() {
//		return messageTemplate2;
//	}
//
//	public void setMessageTemplate2(String messageTemplate2) {
//		this.messageTemplate2 = messageTemplate2;
//	}
//
//	public String getMessageTemplate3() {
//		return messageTemplate3;
//	}
//
//	public void setMessageTemplate3(String messageTemplate3) {
//		this.messageTemplate3 = messageTemplate3;
//	}
//
//	public String getMessageTemplate4() {
//		return messageTemplate4;
//	}
//
//	public void setMessageTemplate4(String messageTemplate4) {
//		this.messageTemplate4 = messageTemplate4;
//	}
//
//	public String getMessageTemplate5() {
//		return messageTemplate5;
//	}
//
//	public void setMessageTemplate5(String messageTemplate5) {
//		this.messageTemplate5 = messageTemplate5;
//	}
//
//	public String getMessageTemplate6() {
//		return messageTemplate6;
//	}
//
//	public void setMessageTemplate6(String messageTemplate6) {
//		this.messageTemplate6 = messageTemplate6;
//	}


	public int getMessageConfig1() {
		return messageConfig1;
	}

	public void setMessageConfig1(int messageConfig1) {
		this.messageConfig1 = messageConfig1;
	}

	public int getMessageConfig2() {
		return messageConfig2;
	}

	public void setMessageConfig2(int messageConfig2) {
		this.messageConfig2 = messageConfig2;
	}

	public int getMessageConfig3() {
		return messageConfig3;
	}

	public void setMessageConfig3(int messageConfig3) {
		this.messageConfig3 = messageConfig3;
	}

	public int getMessageConfig4() {
		return messageConfig4;
	}

	public void setMessageConfig4(int messageConfig4) {
		this.messageConfig4 = messageConfig4;
	}

	public int getMessageConfig5() {
		return messageConfig5;
	}

	public void setMessageConfig5(int messageConfig5) {
		this.messageConfig5 = messageConfig5;
	}

	public int getMessageConfig6() {
		return messageConfig6;
	}

	public void setMessageConfig6(int messageConfig6) {
		this.messageConfig6 = messageConfig6;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getHangTime() {
		return hangTime;
	}

	public void setHangTime(Date hangTime) {
		this.hangTime = hangTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getWakeTime() {
		return wakeTime;
	}

	public void setWakeTime(Date wakeTime) {
		this.wakeTime = wakeTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getInitValue() {
		return initValue;
	}
	//2011-10-12 lzl add
	public String getInitValueLabel() {
		return doFormatValue(getInitValue());
	}
	
	public void setInitValue(BigDecimal initValue) {
		this.initValue = initValue; 
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

	/**
	 * 法律依据
	 * @return String
	 */
	public String getJuralLimit() {
		return juralLimit;
	}

	public void setJuralLimit(String juralLimit) {
		this.juralLimit = juralLimit;
	}
	/**
	 * 风险结论
	 * @return String
	 */
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	/**
	 * ID,唯一
	 * @return the iD
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the iD to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 被监控对象种类
	 * 
	 * @return short
	 */
	public int getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	
	public String getObjectTypeName() {
		return CodeMapUtils.getCodeName((short)172, getObjectType());
	}

	/**
	 * 责任人种类
	 * 
	 * 1 单位，2 部门，3 个人，默认为0，表示忽略
	 * @return short
	 */
	public short getDutyerType() {
		return dutyerType;
	}

	/**
	 * @param dutyerType the dutyerType to set
	 */
	public void setDutyerType(short dutyerType) {
		this.dutyerType = dutyerType;
	}

	/**
	 * 责任人
	 * 
	 * 默认为0，表示忽略
	 * @return Long
	 */
	public Long getDutyerID() {
		return dutyerID;
	}

	/**
	 * @param dutyerID the dutyerID to set
	 */
	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}

	/**
	 * 值符号
	 * 
	 * 1表示风险为正方向，即值越大风险越小，-1表示风险为负方向，即值越小，风险越大，默认为1
	 * @return byte
	 */
	public byte getValueSign() {
		return valueSign;
	}

	/**
	 * @param valueSign the valueSign to set
	 */
	public void setValueSign(byte valueSign) {
		this.valueSign = valueSign;
	}

	/**
	 * 值种类
	 * 
	 * 1 数量，2 金额，3 时间，4 时刻，5 百分比，6分数。默认为1，表示数量
	 * @return byte
	 */
	public byte getValueType() {
		return valueType;
	}

	/**
	 * @param valueType the valueType to set
	 */
	public void setValueType(byte valueType) {
		this.valueType = valueType;
	}
	
	/**
	 * 监察内容类型
	 * @return
	 */
	public String getValueTypeName(){
		return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_TYPE", getValueType());
	}
	
	/**
	 * 监察类别
	 * 
	 * @return
	 */
	public String getTypeName(){
		return CodeMapUtils.getCodeName((short)1073, getType());
	}

	/**
	 * 值单位
	 * 
	 * 默认为0，表示基本单位，其中，若ValueType为3时，默认为4日
	 * @return byte
	 */
	public byte getValueUnit() {
		return valueUnit;
	}

	/**
	 * @param valueUnit the valueUnit to set
	 */
	public void setValueUnit(byte valueUnit) {
		this.valueUnit = valueUnit;
	}
	
	public String getValueUnitName() {
		//return  CodeMapUtils.getCodeName((short)1072, getValueUnit());
		return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_UNIT", getValueUnit());
	}
	
	

	/**
	 * 值来源
	 * 
	 * 0 外部设定，1 定时增长，2 定时减小，3 定时提取，默认为0，表示外部设定。
	 * @return byte
	 */
	public byte getValueSource() {
		return valueSource;
	}
	
	public String getValueSourceName(){
		return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_SOURCE", getValueSource());
	}

	/**
	 * @param valueSource the valueSource to set
	 */
	public void setValueSource(byte valueSource) {
		this.valueSource = valueSource;
	}

	/**
	 * 关联种类
	 * 
	 * @return short
	 */
	public int getRefType() {
		return refType;
	}

	/**
	 * @param refType the refType to set
	 */
	public void setRefType(int refType) {
		this.refType = refType;
	}

	/**
	 * 关联ID
	 * 
	 * @return Long
	 */
	public Long getRefID() {
		return refID;
	}

	/**
	 * @param refID the refID to set
	 */
	public void setRefID(Long refID) {
		this.refID = refID;
	}

	/**
	 * 风险值
	 * 
	 * 默认为0
	 * @return BigDecimal
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param riskValue the riskValue to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * 风险评级
	 * 默认为0，表示最低，数字越大风险越大
	 * @return the riskGrade
	 */
	public byte getGrade() {
		return grade;
	}

	/**
	 * @param riskGrade the riskGrade to set
	 */
	public void setGrade(byte grade) {
		this.grade = grade;
	}
	
	/**
	 * 监察结果
	 * @return
	 */
	public String getGradeName(){
		//return CodeMapUtils.getCodeName((short)1074, getGrade());
		return Risk.getGradeName(getGrade());
	}
		

	/**
	 * 系统ID
	 * 为监管系统的大联网保留，默认为0，表示原始数据。
	 * 以掩模方式标识不同的系统，例如：1-99表示99个区，100-9900表示99个市，
	 * 则9901即可知是第99市的第一个区，具体定义根据领域定义
	 * @return the systemID
	 */
	public Long getSystemID() {
		return systemID;
	}

	/**
	 * @param systemID the systemID to set
	 */
	public void setSystemID(Long systemID) {
		this.systemID = systemID;
	}

	/**
	 * 对象
	 * object表中的ID，默认为0，表忽略
	 * return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID;
		
	}
	
	/**
	 * 值格式
	 * @return String
	 */
	public String getValueFormat() {
		return valueFormat;
	}

	public void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
	}

	/**
	 * 正常/提醒(白色)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue1() {
		return scaleValue1;
	}

	public void setScaleValue1(BigDecimal scaleValue1) {
		this.scaleValue1 = scaleValue1;
	}

	/**
	 * 一般/预警(蓝色)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue2() {
		return scaleValue2;
	}

	public void setScaleValue2(BigDecimal scaleValue2) {
		this.scaleValue2 = scaleValue2;
	}

	/**
	 * 较重/黄牌(黄色)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue3() {
		return scaleValue3;
	}

	public void setScaleValue3(BigDecimal scaleValue3) {
		this.scaleValue3 = scaleValue3;
	}

	/**
	 * 严重/橙牌(橙色)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue4() {
		return scaleValue4;
	}

	public void setScaleValue4(BigDecimal scaleValue4) {
		this.scaleValue4 = scaleValue4;
	}

	/**
	 * 特别严重/红牌(红色)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue5() {
		return scaleValue5;
	}

	public void setScaleValue5(BigDecimal scaleValue5) {
		this.scaleValue5 = scaleValue5;
	}

	/**
	 * 特别严重/红牌(黑色)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue6() {
		return scaleValue6;
	}

	public void setScaleValue6(BigDecimal scaleValue6) {
		this.scaleValue6 = scaleValue6;
	}

	/**
	 * 以对应的位值决定表示是否有效，从右开始。默认为22（十六进制0x16），
	 * 表示仅ScaleValue2、ScaleValue3、ScaleValue5有效。
	 * @return Long
	 */
	public byte getScaleMark() {
		return scaleMark;
	}

	public void setScaleMark(byte scaleMark) {
		this.scaleMark = scaleMark;
	}

	/**
	 * 1是，0否，默认为0。暂停是停止计算。
	 * @return byte
	 */
	public byte getPause() {
		return pause;
	}

	public void setPause(byte pause) {
		this.pause = pause;
	}

	public String getObjectAttr() {
		return objectAttr;
	}

	public void setObjectAttr(String objectAttr) {
		this.objectAttr = objectAttr;
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

	/**
	 * 默认为100。
	 * @return
	 */
	public BigDecimal getScaleValue() {
		return scaleValue;
	}

	public void setScaleValue(BigDecimal scaleValue) {
		this.scaleValue = scaleValue;
	}
	
	public void setRemainValue(BigDecimal remainValue){
		//ignore
	}
	/**
	 * 剩余值（剩余时间等）
	 * @return
	 */
	public BigDecimal getRemainValue() {
		return (getValue() != null) ? getScaleValue().subtract(getValue())
				: getScaleValue();
	}

	/**
	 * 计算RISK的当前级别。
	 * @param newValue 指定监控项的特定值
	 * @return risk计算前的级别
	 * @deprecated using {@link RiskCalculator#calculateGrade(Risk)} 
	 * 			and {@link RiskCalculator#calculateValue(Risk, Date)}.
	 */
	public byte checkGrade(BigDecimal newValue, Date checkTime){        
		//是否在一开始初始化valueChanged??
		//valueChanged = false;
		byte gg = grade;
		if ((scaleMark & 64) != 0 && (scaleValue.intValue() == 0)) {//百分比监察 && scalevalue=0时，跳过计算及赋值
			if(IS_DEBUG_ENABLED){
				log.debug("[Risk.checkGrade()] object("+objectID+")的risk("+getId()+")是百分比监察，且scalevalue=0，跳过计时。");
			}
		}else{
			Date now = checkTime;
			if(now == null){
				now = new Date();
			}
//    		Date now = new Date();
			
    		if(newValue == null) {
                BigDecimal valueAdd = new BigDecimal(0);
                if(valueSource == RiskRule.VALUE_SOURCE_TIMER_ADD) {
                	if(valueType == RiskRule.VALUE_TYPE_TIME) {
                		//处理最后计算时间
                		Date lastCheckTime = getLastRunTime();
                		if(lastCheckTime == null){
                			lastCheckTime = beginTime;
                			if(IS_DEBUG_ENABLED){
                				log.debug("取beginTime为最后运行时间：" + lastCheckTime);
                			}
                		}
                		if(lastCheckTime == null){
                			lastCheckTime = getCreationTime();
                			if(IS_DEBUG_ENABLED){
                				log.debug("BeginTime也为空，取ctime：" + getCreationTime());
                			}
                		}
                		
                		
                		//暂停不计算增量，相当于value不变
                		//int timeUse = (pause == PAUSE_YES) ? 0 : DateUtil.getTimeUsed(getLastRunTime(), now, valueUnit);
                		int timeUse = (pause == PAUSE_YES) ? 0 : DateUtil.getTimeUsed(lastCheckTime, now, valueUnit);
                		
                		int vs = Integer.parseInt(Byte.valueOf(valueSign).toString()); 
                		valueAdd = new BigDecimal(vs * timeUse);
                		
                		if(IS_DEBUG_ENABLED){
                			log.debug("[Risk.checkGrade()] value 增量为：" + valueAdd);
                		}
                	}
                	
                	if (valueAdd.compareTo(BigDecimal.ZERO) != 0){
                		if(value != null)
                			newValue = value.add(valueAdd);
                		else
                			newValue = valueAdd;
                		//setObjectValue(objectType, objectAttr, objectid, newValue);
                	}else{// if (valueAdd.compareTo(new BigDecimal(0)) == 0) {
                		newValue = value;//new BigDecimal(0);
                	}
                }
    		}
    
    		if(IS_DEBUG_ENABLED){
    			log.debug("[Risk.checkGrade()] object=" + objectID + ", value=" + value + ", " + "newValue=" + newValue);
    		}
    		
    //	    if (newValue == null) {//为空，说明scale变化了，要计算
    //	       setGrade(getMaxGrade((byte)1, (byte)0));                
    //	    }
    //	    else
    		if (newValue != value){//不等，设置并计算	        
    	       setValue(newValue);
    	       setGrade(getMaxGrade((byte)1, (byte)0));       
    	       valueChanged = true;
    	    }else{
    	    	 setGrade(getMaxGrade((byte)1, (byte)0));    
    	    }
    		if(IS_DEBUG_ENABLED){
    			log.debug("[Risk.checkGrade()] now grade is " + getGrade() + ", old grade is " + gg);
    		}
    		
    	    setModificationTime(now);
    	    //
    	    setLastRunTime(now);
    	    //临时填值
    	    //setConclusion(getJuralLimit());
		}
		
	    return gg;// != grade;
	}
		
	private byte getMaxGrade(byte grade, byte maxGrade){
		if(value == null) {
			return (byte)0;
	    }
		
		switch (grade){
		case 6:
			return maxGrade;
		case 5:
			if((scaleMark & 16) == 0) {//本级别无效
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 4:
			if((scaleMark & 8) == 0) {//本级别无效
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 3:
			if((scaleMark & 4) == 0) {//本级别无效
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 2:
			if((scaleMark & 2) == 0) {//本级别无效
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 1:
			if((scaleMark & 1) == 0) {//本级别无效
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		}
		
		
//			if(grade == 6) {
//				return maxGrade;
//			} else {
//				return getMaxGrade((byte)(grade + 1), maxGrade);
//			}
		int x = compareScale(grade);
		if(x < 0){
			return maxGrade;
		} else if(x == 0) {
			return grade;//刚好临界,确定风险级别为本级
		} else {// x > 0
			if(grade == 6) {
				return (byte)6;//已经最大了,确定为6
			} else {
				return getMaxGrade((byte)(grade + 1), grade);//继续尝试更高的级
			}
		}
	}
	
	private int compareScale(byte grade) {
	    BigDecimal currentScaleValue = new BigDecimal(0);
        if((getScaleMark() & 64) !=0 ) {//百分比
          if(scaleValue != null){
              switch(grade) {
              case 1: currentScaleValue = scaleValue1.divide(new BigDecimal(100)).multiply(scaleValue); break;
              case 2: currentScaleValue = scaleValue2.divide(new BigDecimal(100)).multiply(scaleValue); break;
              case 3: currentScaleValue = scaleValue3.divide(new BigDecimal(100)).multiply(scaleValue); break;
              case 4: currentScaleValue = scaleValue4.divide(new BigDecimal(100)).multiply(scaleValue); break;
              case 5: currentScaleValue = scaleValue5.divide(new BigDecimal(100)).multiply(scaleValue); break;
              case 6: currentScaleValue = scaleValue6.divide(new BigDecimal(100)).multiply(scaleValue); break;
              }

              return valueSign * value.subtract(currentScaleValue.add(new BigDecimal(valueSign))).compareTo(new BigDecimal(0));
          }else{
              return 0;
          }
          
        } else {//非百分比监察
            switch(grade) {
            case 1: currentScaleValue = scaleValue1; break;
            case 2: currentScaleValue = scaleValue2; break;
            case 3: currentScaleValue = scaleValue3; break;
            case 4: currentScaleValue = scaleValue4; break;
            case 5: currentScaleValue = scaleValue5; break;
            case 6: currentScaleValue = scaleValue6; break;
            }
            
//		int vs = Integer.parseInt(Byte.valueOf(valueSign).toString());
            
            return valueSign * value.subtract(currentScaleValue.add(new BigDecimal(valueSign))).compareTo(new BigDecimal(0));
            /*
		int vs = Integer.parseInt(Byte.valueOf(valueSign).toString());
		value.subtract(new BigDecimal(scaleValue.longValue()));
		value.multiply(new BigDecimal(vs));
		
		return value.compareTo(new BigDecimal(0));*/
            //return value.byteValue();
    
        }
	}
	
	/**
	 * 
	 * @return
	 */
	public GradeScaleValue[] findGradeScaleValues(){
		return new GradeScaleValue[]{
				new GradeScaleValue((byte) 1, scaleValue1),
				new GradeScaleValue((byte) 2, scaleValue2),
				new GradeScaleValue((byte) 3, scaleValue3),
				new GradeScaleValue((byte) 4, scaleValue4),
				new GradeScaleValue((byte) 5, scaleValue5),
				new GradeScaleValue((byte) 6, scaleValue6)
		};
	}
	
	/**
	 * 根据scaleMark获取实际起作用Grade-ScaleValue值对列表
	 * @return
	 */
	public List<GradeScaleValue> findAvailableGradeScaleValues(){
		GradeScaleValue[] gradeScaleValues = findGradeScaleValues();
		List<GradeScaleValue> list = new ArrayList<GradeScaleValue>();
		for(int i = 0 ; i < gradeScaleValues.length ; i++){
			GradeScaleValue gsv = gradeScaleValues[i];
			//是不是忽略这一级
			int ignore = scaleMark & ((int) Math.pow(2, (gsv.grade - 1)));
			if(ignore != 0){
				list.add(gsv);
			}
		}
		return list;
	}
	
	/**
	 * 获取新的grade值
	 * @return
	 * @deprecated
	 */
	public byte calculateNewGrade(){
		if(value == null){
			return 0;
		}
		
		List<GradeScaleValue> list = findAvailableGradeScaleValues();
		//从0级开始
		byte current = 0;
		for(GradeScaleValue gsv: list){
		    int result=0;
	        if((getScaleMark() & 64) !=0 ) {//百分比
	          if(scaleValue != null){
	              result = valueSign * value.subtract(gsv.scaleValue.divide(new BigDecimal(100)).multiply(scaleValue)).compareTo(BigDecimal.ZERO);
	          }else{
	              result=0;
	          }	          
	        } else {//非百分比监察
	            result = valueSign * value.subtract(gsv.scaleValue).compareTo(BigDecimal.ZERO);
	        }
	    
	        
			//值在上级和本级的区间内(m, n)，应该取上级,跳出循环
			if(result < 0){
				break;
			}
			
			//[n, k)
			//级数指向当前级
			current = gsv.grade;
			//如果临界，退出循环
			if(result == 0){
				break;
			}
			
			//>0则继续执行下次循环
		}
		return current;
	}
	
	public String getValueLabel() {
		return doFormatValue(getValue());
	}
	
	public String getScaleValueLabel() {
		return doFormatValue(getScaleValue());
	}
	
	private String doFormatValue(BigDecimal value) {
		String result = null;
		try {
			if(value != null) {
				String vf = getValueFormat();
				if(vf == null || (vf != null && vf.trim().equals(""))) {
					result = value.toString();
				} else {
					result = new DecimalFormat(vf).format(value);
				}
			} else {
				result = "";
			}
		} catch(Exception e) {
			log.warn("doFormatValue 时格式化异常,返回原始值...");
			if(value != null) {
				result = value.toString();
			}
			if(result == null) {
				result = "";
			}
		}
		return result;
	}

	public String getScaleValue1Label() {
		return doFormatScaleValueLabel(getScaleValue1());
	}	
	
	public String getScaleValue2Label() {
		return doFormatScaleValueLabel(getScaleValue2());
	}
	
	public String getScaleValue3Label() {
		return doFormatScaleValueLabel(getScaleValue3());
	}
	
	public String getScaleValue4Label() {
		return doFormatScaleValueLabel(getScaleValue4());
	}
	
	public String getScaleValue5Label() {
		return doFormatScaleValueLabel(getScaleValue5());
	}
	
	public String getScaleValue6Label() {
		return doFormatScaleValueLabel(getScaleValue6());
	}
	
	private String doFormatScaleValueLabel(BigDecimal scaleValue) {
		String result = null;
		try {
			if((getScaleMark() & 64) != 0 && scaleValue != null) {//百分比
				result = format.format(scaleValue);
			} else {
				if(scaleValue != null) {
					String vf = getValueFormat();
					if(vf == null || (vf != null && vf.trim().equals(""))) {
						result = scaleValue.toString();
					} else {
						result = new DecimalFormat(vf).format(scaleValue);	
					}
				} else {
					result = "";
				}
			}
		} catch(Exception e) {
			log.warn("doFormatScaleValueLabel 时格式化异常,返回原始值...");
			if(scaleValue != null) {
				result = scaleValue.toString();
			}
			if(result == null) {
				result = "";
			}
		}
		return result;
	}
	
	private static DecimalFormat format = new DecimalFormat("#0.00%");

	
	
	/**
	 * 
	 *
	 */
	public static class GradeScaleValue{
		public byte grade;
		public BigDecimal scaleValue;
		public GradeScaleValue(byte grade, BigDecimal scaleValue){
			this.grade = grade;
			this.scaleValue = scaleValue;
		}
		public GradeScaleValue(){
			
		}
	}
	

	/**
	 * @return the valueChanged
	 * @deprecated
	 */
	public boolean isValueChanged() {
		return valueChanged;
	}

	/**
	 * @param valueChanged the valueChanged to set
	 * @deprecated
	 */
	public void setValueChanged(boolean valueChanged) {
		this.valueChanged = valueChanged;
	}
	
	/**
	 * grade 中文名称
	 * @return String
	 */
	public String getGradeExplain() {
	    return getGradeExplain(getGrade());
	}	
	
	/**
     * 获取grade中文名称
     * @return String
     */
    public static String getGradeExplain(byte grade) {
        switch (grade) {
        case 0:
            return "正常";
        case 1:
            return "提醒";
        case 2:
            return "预警";
        case 3:
            return "黄牌";
        case 4:
            return "橙牌";
        case 5:
            return "红牌";
        case 6:
            return "黒牌";
        default:
            return "";
        }
    }   
    
    public static String getGradeName(byte grade){
    	return CodeMapUtils.getCodeName((short)1074, grade);
    }
    
	public int getOpinion(){
		return dealOpinion(getValue(),getScaleValue());
	}
	
	/**
	 * 计算时效评价值
	 * @deprecated
	 * @param value
	 * @param scaleValue
	 * @return
	 */
	public static int dealOpinion(BigDecimal value,BigDecimal scaleValue){
		int result=0;
		if(value==null){
			value=BigDecimal.ZERO;
		}
		if(scaleValue!=null)
		{
			float score=1-(value.floatValue()/scaleValue.floatValue());
			if(0.05<=score&&score<=0.2){
				result=1;
			}else if(0.21<=score&&score<=0.4){
				result=2;
			}else if(0.41<=score&&score<=0.6){
				result=3;
			}else if(0.61<=score&&score<=0.8){
				result=4;
			}else if(0.81<=score&&score<=1){
				result=5;
			}
		}
		return result;
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

//    public String getConclusionTpl1() {
//        return conclusionTpl1;
//    }
//
//    public void setConclusionTpl1(String conclusionTpl1) {
//        this.conclusionTpl1 = conclusionTpl1;
//    }
//
//    public String getConclusionTpl2() {
//        return conclusionTpl2;
//    }
//
//    public void setConclusionTpl2(String conclusionTpl2) {
//        this.conclusionTpl2 = conclusionTpl2;
//    }
//
//    public String getConclusionTpl3() {
//        return conclusionTpl3;
//    }
//
//    public void setConclusionTpl3(String conclusionTpl3) {
//        this.conclusionTpl3 = conclusionTpl3;
//    }
//
//    public String getConclusionTpl4() {
//        return conclusionTpl4;
//    }
//
//    public void setConclusionTpl4(String conclusionTpl4) {
//        this.conclusionTpl4 = conclusionTpl4;
//    }
//
//    public String getConclusionTpl5() {
//        return conclusionTpl5;
//    }
//
//    public void setConclusionTpl5(String conclusionTpl5) {
//        this.conclusionTpl5 = conclusionTpl5;
//    }
//
//    public String getConclusionTpl6() {
//        return conclusionTpl6;
//    }
//
//    public void setConclusionTpl6(String conclusionTpl6) {
//        this.conclusionTpl6 = conclusionTpl6;
//    }
    
    /**
     * 获取当前风险界别对应的messageConfig 。
     * @return
     */
    @JSON(serialize=false)
    public int getGradeMessageConfig(){
    	return Risk.getGradeMessageConfig(this);
    }
    
    public static int getGradeMessageConfig(Risk risk){
    	int grade = risk.getGrade();
    	if(grade < 1 || grade > 6){
    		return -1;
    	}
    	int[] configs = {-1, 
    			risk.getMessageConfig1(), risk.getMessageConfig2(),
    			risk.getMessageConfig3(), risk.getMessageConfig4(),
    			risk.getMessageConfig5(), risk.getMessageConfig6()};
    	return configs[grade];
    }
    
//    /**
//     * 获取当前风险界别对应的messageTemplate 。
//     * @return
//     */
//    @JSON(serialize=false)
//    public String getMessageTemplate(){
//    	if(grade < 1 || grade > 6){
//    		return "";
//    	}
//    	
//    	try {
//			Method method = Risk.class.getMethod("getMessageTemplate" + grade);
//			return (String) method.invoke(this);
//		} catch (Exception e) {
//			log.debug(e.getMessage());
//		}
//    	return "";
//    }
    
    
    /**
     * 获取当前级别的scaleValue值
     * @return
     */
    @JSON(serialize=false)
    public BigDecimal getGradeScaleValue(){
    	return Risk.getGradeScaleValue(this);
    }
    
    
    public static BigDecimal getGradeScaleValue(Risk risk){
    	int grade = risk.getGrade();
    	if(grade < 1 || grade > 6){
    		return BigDecimal.ZERO;
    	}
    	BigDecimal[] scaleValues = {BigDecimal.ZERO,
    			risk.getScaleValue1(), risk.getScaleValue2(),
    			risk.getScaleValue3(), risk.getScaleValue4(),
    			risk.getScaleValue5(), risk.getScaleValue6()};
    	return scaleValues[grade];
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
     * 监察状态
     * @return
     */
    public String getStatusName(){
//    	return  CodeMapUtils.getCodeName((short)1078,getStatus());
    	return CodeMapUtils.getCodeName(Risk.class, "STATUS", getStatus());
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

	/**
     * 是否暂停
     * @return
     */
    public String getPauseName2(){
        //return  CodeMapUtils.getCodeName((short)1079, getPause());
    	if(getPause() == (byte)1){
    		return "(暂停)";
    	}else {
    		return null;
    	}
    }
    
    public String getPauseName(){
    	return CodeMapUtils.getCodeName(Risk.class, "PAUSE", getPause());
    }
    
    /**
     * 值方向
     * @return
     */
    public String getValueSignName(){
    	return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_SIGN", getValueSign());
    }

	/**
	 * @return the gradeChangedTime
	 */
	public Date getGradeChangedTime() {
		return gradeChangedTime;
	}

	/**
	 * @param gradeChangedTime the gradeChangedTime to set
	 */
	public void setGradeChangedTime(Date gradeChangedTime) {
		this.gradeChangedTime = gradeChangedTime;
	}

	/**
	 * @return the valueChangedTime
	 */
	public Date getValueChangedTime() {
		return valueChangedTime;
	}

	/**
	 * @param valueChangedTime the valueChangedTime to set
	 */
	public void setValueChangedTime(Date valueChangedTime) {
		this.valueChangedTime = valueChangedTime;
	}

	/**
	 * @return the valueCalculateTime
	 */
	public Date getValueCalculateTime() {
		return valueCalculateTime;
	}

	/**
	 * @param valueCalculateTime the valueCalculateTime to set
	 */
	public void setValueCalculateTime(Date valueCalculateTime) {
		this.valueCalculateTime = valueCalculateTime;
	}
	
	public String getRonon(){
		return getRefObjectName()+getObjectName();
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

	/**
	 * @return the refObjectSn
	 */
	public String getRefObjectSn() {
		return refObjectSn;
	}

	/**
	 * @param refObjectSn the refObjectSn to set
	 */
	public void setRefObjectSn(String refObjectSn) {
		this.refObjectSn = refObjectSn;
	}

	/**
	 * @return the itemID
	 */
	public String getItemID() {
		return itemID;
	}

	/**
	 * @param itemID the itemID to set
	 */
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the ruleType
	 */
	public int getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}
}