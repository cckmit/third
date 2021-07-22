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
 * ��죨���գ���
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
	 * �� 1
	 */
	@DisplayName("��")
	public static final byte PAUSE_YES = 1;
	/**
	 * �� 0
	 */
	@DisplayName("��")
	public static final byte PAUSE_NO = 0;
	
	
	//Status���壺0���ã�1���ã�2������3��ͣ��Ĭ��1�������������ʱͣ��
	
	/**
	 * ���� 0
	 */
	@DisplayName("����")
	public static final byte STATUS_STORE = 0;
	/**
	 * ���� 1
	 */
	@DisplayName("����")
	public static final byte STATUS_ON_USE = 1;
	/**
	 * ���� 2
	 */
	@DisplayName("����")
	public static final byte STATUS_FINISH = 2;
	/**
	 * ͣ�� 3
	 */
	@DisplayName("ͣ��")
	public static final byte STATUS_UN_USE = 3;
	
	
	/**
	 * ҵ�����1001.
	 */
	public static final int OBJECT_TYPE_PROJECT = 1001;
	
	/**
	 * Task 102.
	 */
	public static final int OBJECT_TYPE_TASK = ObjectTypes.TASK;
	
	//11-�ϼ��Ա�����죻21-�����ڲ���죻31-�������¼����
	@DisplayName("�ϼ��Ա������")
	public static final short CATEGORY_HIGHER_DEPARTMENT = 11;
	@DisplayName("�����ڲ����")
	public static final short CATEGORY_INNER_DEPARTMENT = 21;
	@DisplayName("�������¼����")
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
	private int refType;//����objectType��Typeȷ�����塣
	private Long refID;//����objectType��Typeȷ�����塣
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
    
    
	private String code;								//    ���	
	private String objectCode; 							//    �������	
	private String objectName;							//    ��������	
	private String objectAttrName;						//    ����ض�����������	
	private Long superviseOrgId;						//    ��쵥λID
	private String superviseOrgAbbr;					//    ��쵥λ���	
	private Long dutyerOrgId;							//    ���ε�λID	
	private String dutyerOrgName;						//    ���ε�λ����	
	private Long dutyerDeptId;							//    ���β���ID	
	private String dutyerDeptName;						//    ���β�������	
	private Date lastRunTime;							//    ������ʱ��	
//	private String messageTemplate1;					//    Ĭ����Ϣģ��1	
//	private String messageTemplate2;					//    Ĭ����Ϣģ��2	
//	private String messageTemplate3;					//    Ĭ����Ϣģ��3	
//	private String messageTemplate4;					//    Ĭ����Ϣģ��4	
//	private String messageTemplate5;					//    Ĭ����Ϣģ��5	
//	private String messageTemplate6;					//    Ĭ����Ϣģ��6	
	private int messageConfig1 = 1;							//    Ĭ����Ϣ����1	
	private int messageConfig2 = 3;							//    Ĭ����Ϣ����2	
	private int messageConfig3 = 7;							//    Ĭ����Ϣ����3	
	private int messageConfig4 = 7;							//    Ĭ����Ϣ����4
	private int messageConfig5 = 7;							//    Ĭ����Ϣ����5	
	private int messageConfig6 = 31;							//    Ĭ����Ϣ����6	
	private Date beginTime;								//    ��ʼʱ��	
	private Date hangTime;								//    ��ͣʱ��	
	private Date wakeTime;								//    ����ʱ��
	private Date endTime;								//    ����ʱ��
	
//	private String dutyerName;							//    ����������	DUTYERNAME
	
	private String ruleCode;							//    ������
	private String ruleName;							//��������
	
	private Date gradeChangedTime;
	private Date valueChangedTime;
	//���־û�
	private Date valueCalculateTime;	//����ʱ�䣬ͨ���ڼ���ǰ���ã����־û������ݿ�
	
	
	 //2012-02-24 ������������Ϣ
	private Long dutyerLeaderId;		//	���ε�λ�ֹ��쵼ID 
	private String dutyerLeaderName;	//	���ε�λ�ֹ��쵼���� 
	private String dutyerLeaderMobNo;	//	���ε�λ�ֹ��쵼�ֻ����� 
	private Long dutyerManagerId;		//	���β���ҵ������ID��ֱ���ϼ� 
	private String dutyerManagerName;	//	���β���ҵ���������� ��ֱ���ϼ�
	private String dutyerManagerMobNo;	//	���β���ҵ�������ֻ����� ��ֱ���ϼ�
	private String dutyerMobNo;		//	�������ֻ����� 
	private String refObjectSn;	 	// ��ض����SN
	//ҵ������
    private String itemID;		//ҵ������ID
    private String itemName;	//ҵ����������
    
    // 2012-05-15(�����ֶ�)
    private String ruleSummary;			//������
    private String bizSummary;			//ҵ��״��
    
    //2012-05-16(�����ֶ�)
    private Long superviseClerkId;		//�����ID
    private String superviseClerkName;	//���������
    
    //��������
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
	 * ��������
	 * @return String
	 */
	public String getJuralLimit() {
		return juralLimit;
	}

	public void setJuralLimit(String juralLimit) {
		this.juralLimit = juralLimit;
	}
	/**
	 * ���ս���
	 * @return String
	 */
	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	/**
	 * ID,Ψһ
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
	 * ����ض�������
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
	 * ����������
	 * 
	 * 1 ��λ��2 ���ţ�3 ���ˣ�Ĭ��Ϊ0����ʾ����
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
	 * ������
	 * 
	 * Ĭ��Ϊ0����ʾ����
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
	 * ֵ����
	 * 
	 * 1��ʾ����Ϊ�����򣬼�ֵԽ�����ԽС��-1��ʾ����Ϊ�����򣬼�ֵԽС������Խ��Ĭ��Ϊ1
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
	 * ֵ����
	 * 
	 * 1 ������2 ��3 ʱ�䣬4 ʱ�̣�5 �ٷֱȣ�6������Ĭ��Ϊ1����ʾ����
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
	 * �����������
	 * @return
	 */
	public String getValueTypeName(){
		return CodeMapUtils.getCodeName(RiskRule.class, "VALUE_TYPE", getValueType());
	}
	
	/**
	 * ������
	 * 
	 * @return
	 */
	public String getTypeName(){
		return CodeMapUtils.getCodeName((short)1073, getType());
	}

	/**
	 * ֵ��λ
	 * 
	 * Ĭ��Ϊ0����ʾ������λ�����У���ValueTypeΪ3ʱ��Ĭ��Ϊ4��
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
	 * ֵ��Դ
	 * 
	 * 0 �ⲿ�趨��1 ��ʱ������2 ��ʱ��С��3 ��ʱ��ȡ��Ĭ��Ϊ0����ʾ�ⲿ�趨��
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
	 * ��������
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
	 * ����ID
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
	 * ����ֵ
	 * 
	 * Ĭ��Ϊ0
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
	 * ��������
	 * Ĭ��Ϊ0����ʾ��ͣ�����Խ�����Խ��
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
	 * �����
	 * @return
	 */
	public String getGradeName(){
		//return CodeMapUtils.getCodeName((short)1074, getGrade());
		return Risk.getGradeName(getGrade());
	}
		

	/**
	 * ϵͳID
	 * Ϊ���ϵͳ�Ĵ�����������Ĭ��Ϊ0����ʾԭʼ���ݡ�
	 * ����ģ��ʽ��ʶ��ͬ��ϵͳ�����磺1-99��ʾ99������100-9900��ʾ99���У�
	 * ��9901����֪�ǵ�99�еĵ�һ���������嶨�����������
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
	 * ����
	 * object���е�ID��Ĭ��Ϊ0�������
	 * return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID;
		
	}
	
	/**
	 * ֵ��ʽ
	 * @return String
	 */
	public String getValueFormat() {
		return valueFormat;
	}

	public void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
	}

	/**
	 * ����/����(��ɫ)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue1() {
		return scaleValue1;
	}

	public void setScaleValue1(BigDecimal scaleValue1) {
		this.scaleValue1 = scaleValue1;
	}

	/**
	 * һ��/Ԥ��(��ɫ)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue2() {
		return scaleValue2;
	}

	public void setScaleValue2(BigDecimal scaleValue2) {
		this.scaleValue2 = scaleValue2;
	}

	/**
	 * ����/����(��ɫ)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue3() {
		return scaleValue3;
	}

	public void setScaleValue3(BigDecimal scaleValue3) {
		this.scaleValue3 = scaleValue3;
	}

	/**
	 * ����/����(��ɫ)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue4() {
		return scaleValue4;
	}

	public void setScaleValue4(BigDecimal scaleValue4) {
		this.scaleValue4 = scaleValue4;
	}

	/**
	 * �ر�����/����(��ɫ)
	 * @return BigDecimal
	 */	
	public BigDecimal getScaleValue5() {
		return scaleValue5;
	}

	public void setScaleValue5(BigDecimal scaleValue5) {
		this.scaleValue5 = scaleValue5;
	}

	/**
	 * �ر�����/����(��ɫ)
	 * @return BigDecimal
	 */
	public BigDecimal getScaleValue6() {
		return scaleValue6;
	}

	public void setScaleValue6(BigDecimal scaleValue6) {
		this.scaleValue6 = scaleValue6;
	}

	/**
	 * �Զ�Ӧ��λֵ������ʾ�Ƿ���Ч�����ҿ�ʼ��Ĭ��Ϊ22��ʮ������0x16����
	 * ��ʾ��ScaleValue2��ScaleValue3��ScaleValue5��Ч��
	 * @return Long
	 */
	public byte getScaleMark() {
		return scaleMark;
	}

	public void setScaleMark(byte scaleMark) {
		this.scaleMark = scaleMark;
	}

	/**
	 * 1�ǣ�0��Ĭ��Ϊ0����ͣ��ֹͣ���㡣
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
	 * Ĭ��Ϊ100��
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
	 * ʣ��ֵ��ʣ��ʱ��ȣ�
	 * @return
	 */
	public BigDecimal getRemainValue() {
		return (getValue() != null) ? getScaleValue().subtract(getValue())
				: getScaleValue();
	}

	/**
	 * ����RISK�ĵ�ǰ����
	 * @param newValue ָ���������ض�ֵ
	 * @return risk����ǰ�ļ���
	 * @deprecated using {@link RiskCalculator#calculateGrade(Risk)} 
	 * 			and {@link RiskCalculator#calculateValue(Risk, Date)}.
	 */
	public byte checkGrade(BigDecimal newValue, Date checkTime){        
		//�Ƿ���һ��ʼ��ʼ��valueChanged??
		//valueChanged = false;
		byte gg = grade;
		if ((scaleMark & 64) != 0 && (scaleValue.intValue() == 0)) {//�ٷֱȼ�� && scalevalue=0ʱ���������㼰��ֵ
			if(IS_DEBUG_ENABLED){
				log.debug("[Risk.checkGrade()] object("+objectID+")��risk("+getId()+")�ǰٷֱȼ�죬��scalevalue=0��������ʱ��");
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
                		//����������ʱ��
                		Date lastCheckTime = getLastRunTime();
                		if(lastCheckTime == null){
                			lastCheckTime = beginTime;
                			if(IS_DEBUG_ENABLED){
                				log.debug("ȡbeginTimeΪ�������ʱ�䣺" + lastCheckTime);
                			}
                		}
                		if(lastCheckTime == null){
                			lastCheckTime = getCreationTime();
                			if(IS_DEBUG_ENABLED){
                				log.debug("BeginTimeҲΪ�գ�ȡctime��" + getCreationTime());
                			}
                		}
                		
                		
                		//��ͣ�������������൱��value����
                		//int timeUse = (pause == PAUSE_YES) ? 0 : DateUtil.getTimeUsed(getLastRunTime(), now, valueUnit);
                		int timeUse = (pause == PAUSE_YES) ? 0 : DateUtil.getTimeUsed(lastCheckTime, now, valueUnit);
                		
                		int vs = Integer.parseInt(Byte.valueOf(valueSign).toString()); 
                		valueAdd = new BigDecimal(vs * timeUse);
                		
                		if(IS_DEBUG_ENABLED){
                			log.debug("[Risk.checkGrade()] value ����Ϊ��" + valueAdd);
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
    		
    //	    if (newValue == null) {//Ϊ�գ�˵��scale�仯�ˣ�Ҫ����
    //	       setGrade(getMaxGrade((byte)1, (byte)0));                
    //	    }
    //	    else
    		if (newValue != value){//���ȣ����ò�����	        
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
    	    //��ʱ��ֵ
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
			if((scaleMark & 16) == 0) {//��������Ч
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 4:
			if((scaleMark & 8) == 0) {//��������Ч
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 3:
			if((scaleMark & 4) == 0) {//��������Ч
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 2:
			if((scaleMark & 2) == 0) {//��������Ч
				return getMaxGrade((byte)(grade + 1), maxGrade);
			}
			break;
		case 1:
			if((scaleMark & 1) == 0) {//��������Ч
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
			return grade;//�պ��ٽ�,ȷ�����ռ���Ϊ����
		} else {// x > 0
			if(grade == 6) {
				return (byte)6;//�Ѿ������,ȷ��Ϊ6
			} else {
				return getMaxGrade((byte)(grade + 1), grade);//�������Ը��ߵļ�
			}
		}
	}
	
	private int compareScale(byte grade) {
	    BigDecimal currentScaleValue = new BigDecimal(0);
        if((getScaleMark() & 64) !=0 ) {//�ٷֱ�
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
          
        } else {//�ǰٷֱȼ��
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
	 * ����scaleMark��ȡʵ��������Grade-ScaleValueֵ���б�
	 * @return
	 */
	public List<GradeScaleValue> findAvailableGradeScaleValues(){
		GradeScaleValue[] gradeScaleValues = findGradeScaleValues();
		List<GradeScaleValue> list = new ArrayList<GradeScaleValue>();
		for(int i = 0 ; i < gradeScaleValues.length ; i++){
			GradeScaleValue gsv = gradeScaleValues[i];
			//�ǲ��Ǻ�����һ��
			int ignore = scaleMark & ((int) Math.pow(2, (gsv.grade - 1)));
			if(ignore != 0){
				list.add(gsv);
			}
		}
		return list;
	}
	
	/**
	 * ��ȡ�µ�gradeֵ
	 * @return
	 * @deprecated
	 */
	public byte calculateNewGrade(){
		if(value == null){
			return 0;
		}
		
		List<GradeScaleValue> list = findAvailableGradeScaleValues();
		//��0����ʼ
		byte current = 0;
		for(GradeScaleValue gsv: list){
		    int result=0;
	        if((getScaleMark() & 64) !=0 ) {//�ٷֱ�
	          if(scaleValue != null){
	              result = valueSign * value.subtract(gsv.scaleValue.divide(new BigDecimal(100)).multiply(scaleValue)).compareTo(BigDecimal.ZERO);
	          }else{
	              result=0;
	          }	          
	        } else {//�ǰٷֱȼ��
	            result = valueSign * value.subtract(gsv.scaleValue).compareTo(BigDecimal.ZERO);
	        }
	    
	        
			//ֵ���ϼ��ͱ�����������(m, n)��Ӧ��ȡ�ϼ�,����ѭ��
			if(result < 0){
				break;
			}
			
			//[n, k)
			//����ָ��ǰ��
			current = gsv.grade;
			//����ٽ磬�˳�ѭ��
			if(result == 0){
				break;
			}
			
			//>0�����ִ���´�ѭ��
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
			log.warn("doFormatValue ʱ��ʽ���쳣,����ԭʼֵ...");
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
			if((getScaleMark() & 64) != 0 && scaleValue != null) {//�ٷֱ�
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
			log.warn("doFormatScaleValueLabel ʱ��ʽ���쳣,����ԭʼֵ...");
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
	 * grade ��������
	 * @return String
	 */
	public String getGradeExplain() {
	    return getGradeExplain(getGrade());
	}	
	
	/**
     * ��ȡgrade��������
     * @return String
     */
    public static String getGradeExplain(byte grade) {
        switch (grade) {
        case 0:
            return "����";
        case 1:
            return "����";
        case 2:
            return "Ԥ��";
        case 3:
            return "����";
        case 4:
            return "����";
        case 5:
            return "����";
        case 6:
            return "�\��";
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
	 * ����ʱЧ����ֵ
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
     * ��ȡ��ǰ���ս���Ӧ��messageConfig ��
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
//     * ��ȡ��ǰ���ս���Ӧ��messageTemplate ��
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
     * ��ȡ��ǰ�����scaleValueֵ
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
     * ���״̬
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
     * �Ƿ���ͣ
     * @return
     */
    public String getPauseName2(){
        //return  CodeMapUtils.getCodeName((short)1079, getPause());
    	if(getPause() == (byte)1){
    		return "(��ͣ)";
    	}else {
    		return null;
    	}
    }
    
    public String getPauseName(){
    	return CodeMapUtils.getCodeName(Risk.class, "PAUSE", getPause());
    }
    
    /**
     * ֵ����
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