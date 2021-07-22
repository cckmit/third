package cn.redflagsoft.base.bean;

import java.math.BigDecimal;

import cn.redflagsoft.base.annotation.DisplayName;



/**
 * 监察规则（风险规则）。
 * 
 * 其中MessageConfig的定义为：
 * 以对应的位值决定表示是否发送消息，从右开始依次为0-4位表示责任人、业务主管、分管领导、
 * 监察员、监察领导，1表示仅责任人、3表示带业务主管、7表示带分管领导、15表示带监察员。
 * 
 * @author lcj
 *
 */
public class RiskRule extends VersionableBean {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4164637440189615842L;
    
    /**
	 * 正方向 1
	 */
	@DisplayName("正方向 ")
	public static final byte VALUE_SIGN_POSITIVE = 1;
	
	/**
	 * 负方向 -1
	 */
	@DisplayName("负方向")
	public static final byte VALUE_SIGN_NEGATIVE = -1;
    
    
	/**
	 * 数量 1
	 */
	@DisplayName("数量")
	public static final byte VALUE_TYPE_NUMBER = 1;
	/**
	 * 金额 2
	 */
	@DisplayName("金额")
	public static final byte VALUE_TYPE_MONEY = 2;
	/**
	 * 时间 3
	 */
	@DisplayName("时间")
	public static final byte VALUE_TYPE_TIME = 3;
	/**
	 * 时刻 4
	 */
	@DisplayName("时刻")
	public static final byte VALUE_TYPE_TIME_DAY = 4;
	/**
	 * 百分比 5
	 */
	@DisplayName("百分比")
	public static final byte VALUE_TYPE_PERCENT = 5;
	/**
	 * 分数 6
	 */
	@DisplayName("分数")
	public static final byte VALUE_TYPE_FRACTION = 6;
	

	/**
	 * 无 0
	 */
	@DisplayName("无")
	public static final byte VALUE_UNIT_NONE = 0;
	/**
	 * 年 1
	 */
	@DisplayName("年")
	public static final byte VALUE_UNIT_YEAR = 1;
	/**
	 * 月 2
	 */
	@DisplayName("月")
	public static final byte VALUE_UNIT_MONTH = 2;
	
	/**
	 * 周 3
	 */
	@DisplayName("周")
	public static final byte VALUE_UNIT_WEEK = 3;
	
	/**
     * 日 4
     */
	@DisplayName("工作日")
    public static final byte VALUE_UNIT_WORKDAY = 4;
    
    /**
     * 天 5
     */
	@DisplayName("日历天")
    public static final byte VALUE_UNIT_DAY = 5;
    /**
     * 时 6
     */
	@DisplayName("时")
    public static final byte VALUE_UNIT_HOUR = 6;
    /**
     * 分 7
     */
	@DisplayName("分")
    public static final byte VALUE_UNIT_MINUTE = 7;
    /**
     * 秒 8
     */
	@DisplayName("秒")
    public static final byte VALUE_UNIT_SECOND = 8;
    /**
     * 毫秒 9
     */
	@DisplayName("毫秒 ")
    public static final byte VALUE_UNIT_MILLISECOND = 9;
	
	
    /**
     * 外部设定 0
     */
    @DisplayName("外部设定")
    public static final byte VALUE_SOURCE_EXTERIOR = 0;
    /**
     * 定时增长 1
     */
    @DisplayName("定时增长")
    public static final byte VALUE_SOURCE_TIMER_ADD = 1;
    /**
     * 定时减少 2
     */
    @DisplayName("定时减少")
    public static final byte VALUE_SOURCE_TIMER_SUB = 2;
    /**
     * 定时提取 3
     */
    @DisplayName("定时提取")
    public static final byte VALUE_SOURCE_DISTILL = 3;
    
    
    
    /**
     * 监察规则标签：创建标志，0：监察对象创建时自动创建，1：监察对象创建后自动创建；其他：按条件编码创建。
     */
    /**
     * 创建标志：自动创建 0
     */
    @DisplayName("自动创建")
    public static final short TAG_AUTO = 0;
    /**
     * 创建标志：半自动创建 1
     */
    @DisplayName("半自动创建")
    public static final short TAG_HALF_AUTO = 1;
    @DisplayName("其它")
    public static final short TAG_HALF_OTHER = 2;
    
    /**
     * 责任人信息抄自有关地方,0来自监察规则,必须定义dutyerId
     */
    public static final short DUTYER_TYPE_FROM_RISK_RULE = 0;
    /**
     * 责任人信息抄自有关地方,2来自object，不必定义dutyerId
     */
    public static final short DUTYER_TYPE_FROM_MONITORABLE_OBJECT = 2;
    /**
     * 责任人信息抄自有关地方,3来自ref_object，不必定义dutyerId
     */
    public static final short DUTYER_TYPE_FROM_REF_OBJECT = 3;
    
    /**
     * 责任人信息抄自有关地方,4来自object与单位人员关系的关系表 ObjectOrgClerk中,
     * 必须定义dutyerId,dutyerId的值即ObjectOrgClerk中的type。
     * @since 2.1.5 2012-04-16
     */
    public static final short DUTYER_TYPE_FROM_MONITORABLE_OBJECT_ORG_CLERK = 4;

    /**
     * 责任人信息抄自有关地方,5来自refobject与单位人员关系的关系表 ObjectOrgClerk中
     * 必须定义dutyerId,dutyerId的值即ObjectOrgClerk中的type。
     * @since 2.1.5 2012-04-16
     */
    public static final short DUTYER_TYPE_FROM_REF_OBJECT_ORG_CLERK = 5; 
    
    
    public static final int TYPE_时限监察 = 1;
    public static final int TYPE_比例监察 = 2;
    
    /**
     * 1
     */
    public static final int RULE_TYPE_监察 = 1;
    /**
     * 2
     */
    public static final int RULE_TYPE_督查 = 2;
    
    @DisplayName("在用")
    public static final byte STATUS_ON_USE = 1;
    @DisplayName("停用")
    public static final byte STATUS_UN_USE = 0;
    
    private Long id;
    private short category;
    private int objectType;
    private String objectAttr;
    private short dutyerType;	//责任主体类型：责任人信息抄自有关地方，按dutyer_type的定义，0来自监察规则；2来自object；3来自ref_object。
    private byte valueSign = VALUE_SIGN_POSITIVE;
    private byte valueType = VALUE_TYPE_NUMBER;
    private byte valueUnit = VALUE_UNIT_WORKDAY;
    private String valueFormat;
    private byte valueSource = VALUE_SOURCE_EXTERIOR;
    private BigDecimal scaleValue1;
    private BigDecimal scaleValue2;
    private BigDecimal scaleValue3;
    private BigDecimal scaleValue4;
    private BigDecimal scaleValue5;
    private BigDecimal scaleValue6;
    private byte scaleMark;
    private int refType;
    private String note;
    private BigDecimal scaleValue;
    private short tag;
    private String name;
    private String juralLimit;
    
    private String conclusionTpl1;
    private String conclusionTpl2;
    private String conclusionTpl3;
    private String conclusionTpl4;
    private String conclusionTpl5;
    private String conclusionTpl6;
    
    private String code;
    private BigDecimal initValue;
    
    private String objectAttrName; 				//被监控对象属性名称
    private Long superviseOrgId; 				//监察单位ID
    private String superviseOrgAbbr; 			//监察单位简称
    private Long dutyerOrgId; 					//默认责任单位ID
    private String dutyerOrgName; 				//默认责任单位名称
    private Long dutyerDeptId; 					//默认责任部门ID
    private String dutyerDeptName; 				//默认责任部门名称
    private Long dutyerId; 						//默认责任人ID
    private String dutyerName; 					//默认责任人姓名
    private String messageTemplate1; 				//默认消息模板1
    private String messageTemplate2; 				//默认消息模板2
    private String messageTemplate3; 				//默认消息模板3
    private String messageTemplate4; 				//默认消息模板4
    private String messageTemplate5; 				//默认消息模板5
    private String messageTemplate6; 				//默认消息模板6
	private int messageConfig1 = 1; // 默认消息发送1
	private int messageConfig2 = 3; // 默认消息发送2
	private int messageConfig3 = 7; // 默认消息发送3
	private int messageConfig4 = 7; // 默认消息发送4
	private int messageConfig5 = 7; // 默认消息发送5
	private int messageConfig6 = 31; // 默认消息发送6
    private int refObjectType; 					//相关对象类型
    private Long systemId; 						//系统编号
    
    private Long dutyerLeader1Id;					//直属上级领导ID
    private String dutyerLeader1Name;				//直属上级领导姓名
    private Long dutyerLeader2Id;					//分管领导ID
    private String dutyerLeader2Name;				//分管领导姓名
    
    //业务事项
    private String itemID;		//业务事项ID
    private String itemName;	//业务事项名称
    
    private String remarkTemplate;//备注模板
    
    // 2012-05-15(新增字段)
    private String ruleSummary;			//监察规则
    
    private String bizSummaryTemplate;      //    业务状况模板

    private int ruleType = RULE_TYPE_监察;
    
    public String getBizSummaryTemplate() {
		return bizSummaryTemplate;
	}

	public void setBizSummaryTemplate(String bizSummaryTemplate) {
		this.bizSummaryTemplate = bizSummaryTemplate;
	}

	public String getRuleSummary() {
		return ruleSummary;
	}

	public void setRuleSummary(String ruleSummary) {
		this.ruleSummary = ruleSummary;
	}

	public String getObjectAttrName() {
		return objectAttrName;
	}

	public void setObjectAttrName(String objectAttrName) {
		this.objectAttrName = objectAttrName;
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

	public String getDutyerOrgName() {
		return dutyerOrgName;
	}

	public void setDutyerOrgName(String dutyerOrgName) {
		this.dutyerOrgName = dutyerOrgName;
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

	public String getMessageTemplate1() {
		return messageTemplate1;
	}

	public void setMessageTemplate1(String messageTemplate1) {
		this.messageTemplate1 = messageTemplate1;
	}

	public String getMessageTemplate2() {
		return messageTemplate2;
	}

	public void setMessageTemplate2(String messageTemplate2) {
		this.messageTemplate2 = messageTemplate2;
	}

	public String getMessageTemplate3() {
		return messageTemplate3;
	}

	public void setMessageTemplate3(String messageTemplate3) {
		this.messageTemplate3 = messageTemplate3;
	}

	public String getMessageTemplate4() {
		return messageTemplate4;
	}

	public void setMessageTemplate4(String messageTemplate4) {
		this.messageTemplate4 = messageTemplate4;
	}

	public String getMessageTemplate5() {
		return messageTemplate5;
	}

	public void setMessageTemplate5(String messageTemplate5) {
		this.messageTemplate5 = messageTemplate5;
	}

	public String getMessageTemplate6() {
		return messageTemplate6;
	}

	public void setMessageTemplate6(String messageTemplate6) {
		this.messageTemplate6 = messageTemplate6;
	}

	/**
	 * 以对应的位值决定表示是否发送消息，从右开始依次为0-4位表示责任人、
	 * 业务主管、分管领导、监察员、监察领导，1表示仅责任人、3表示带业务
	 * 主管、7表示带分管领导、15表示带监察员。
	 * @return
	 */
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

	public int getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(int refObjectType) {
		this.refObjectType = refObjectType;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public BigDecimal getInitValue() {
		return initValue;
	}

	public void setInitValue(BigDecimal initValue) {
		this.initValue = initValue;
	}
    /**
     * 法律依据
     * 
     * @return String
     */
    public String getJuralLimit() {
        return juralLimit;
    }

    public void setJuralLimit(String juralLimit) {
        this.juralLimit = juralLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 对象种类
     * 
     * @return short
     */
    public int getObjectType() {
        return objectType;
    }

    /**
     * @param objectType
     *            the objectType to set
     */
    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    /**
     * 对象属性
     * 
     * @return String
     */
    public String getObjectAttr() {
        return objectAttr;
    }

    /**
     * @param objectAttr
     *            the objectAttr to set
     */
    public void setObjectAttr(String objectAttr) {
        this.objectAttr = objectAttr;
    }
    
    //1 单位，2 部门，3 个人，默认为0，表示忽略
    /**
     * 责任人种类
     * 
     * 责任主体类型：责任人信息抄自有关地方，按dutyer_type的定义，0来自监察规则；2来自object；3来自ref_object。
     * 
     * @return short
     */
    public short getDutyerType() {
        return dutyerType;
    }

    /**
     * @param dutyerType
     *            the dutyerType to set
     */
    public void setDutyerType(short dutyerType) {
        this.dutyerType = dutyerType;
    }

    /**
     * 值符号
     * 
     * 0表示风险为正方向，即值越大风险越小，1表示风险为负方向，即值越小，风险越大，默认为0
     * 
     * @return byte
     */
    public byte getValueSign() {
        return valueSign;
    }

    /**
     * @param valueSign
     *            the valueSign to set
     */
    public void setValueSign(byte valueSign) {
        this.valueSign = valueSign;
    }

    /**
     * 值种类
     * 
     * 1 数量，2 金额，3 时间，4 时刻，5 百分比，默认为1，表示忽略
     * 
     * @return byte
     */
    public byte getValueType() {
        return valueType;
    }

    /**
     * @param valueType
     *            the valueType to set
     */
    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }

    /**
     * 值单位
     * 
     * 默认为0，表示基本单位，其中，若ValueType为3时，默认为4日
     * 
     * @return byte
     */
    public byte getValueUnit() {
        return valueUnit;
    }

    /**
     * @param valueUnit
     *            the valueUnit to set
     */
    public void setValueUnit(byte valueUnit) {
        this.valueUnit = valueUnit;
    }

    /**
     * 值来源
     * 
     * 0 外部设定，1 定时增长，2 定时减小，3 定时提取，默认为0，表示忽略
     * 
     * @return byte
     */
    public byte getValueSource() {
        return valueSource;
    }

    /**
     * @param valueSource
     *            the valueSource to set
     */
    public void setValueSource(byte valueSource) {
        this.valueSource = valueSource;
    }

    /**
     * 值格式
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @return byte
     */
    public byte getScaleMark() {
        return scaleMark;
    }

    public void setScaleMark(byte scaleMark) {
        this.scaleMark = scaleMark;
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
     * @param refType
     *            the refType to set
     */
    public void setRefType(int refType) {
        this.refType = refType;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     *            the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 不空，默认为100。
     * 
     * @return
     */
    public BigDecimal getScaleValue() {
        return scaleValue;
    }

    public void setScaleValue(BigDecimal scaleValue) {
        this.scaleValue = scaleValue;
    }

    /**
     * 创建标志，0自动创建
     * 
     * @return
     */
    public short getTag() {
        return tag;
    }

    public void setTag(short tag) {
        this.tag = tag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
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

	public Long getDutyerLeader1Id() {
		return dutyerLeader1Id;
	}

	public void setDutyerLeader1Id(Long dutyerLeader1Id) {
		this.dutyerLeader1Id = dutyerLeader1Id;
	}

	public String getDutyerLeader1Name() {
		return dutyerLeader1Name;
	}

	public void setDutyerLeader1Name(String dutyerLeader1Name) {
		this.dutyerLeader1Name = dutyerLeader1Name;
	}

	public Long getDutyerLeader2Id() {
		return dutyerLeader2Id;
	}

	public void setDutyerLeader2Id(Long dutyerLeader2Id) {
		this.dutyerLeader2Id = dutyerLeader2Id;
	}

	public String getDutyerLeader2Name() {
		return dutyerLeader2Name;
	}

	public void setDutyerLeader2Name(String dutyerLeader2Name) {
		this.dutyerLeader2Name = dutyerLeader2Name;
	}
	
	public String getCodeName(){
		return getName() + "(" + getCode() + ")";
	}
	
	
	public static int getGradeMessageConfig(RiskRule rule, byte grade){
		if(grade < 1 || grade > 6){
			return -1;
		}
		int[] configs = {-1, 
				rule.getMessageConfig1(), rule.getMessageConfig2(),
    			rule.getMessageConfig3(), rule.getMessageConfig4(),
    			rule.getMessageConfig5(), rule.getMessageConfig6()};
    	return configs[grade];
	}
	
	public static String getGradeMessageTemplate(RiskRule rule, byte grade){
		if(grade < 1 || grade > 6){
			return "";
		}
		String[] templates = {"", 
				rule.getMessageTemplate1(), rule.getMessageTemplate2(),
    			rule.getMessageTemplate3(), rule.getMessageTemplate4(),
    			rule.getMessageTemplate5(), rule.getMessageTemplate6()};
    	return templates[grade];
	}
	
	public static BigDecimal getGradeScaleValue(RiskRule rule, byte grade){
    	if(grade < 1 || grade > 6){
    		return BigDecimal.ZERO;
    	}
    	BigDecimal[] scaleValues = {BigDecimal.ZERO,
    			rule.getScaleValue1(), rule.getScaleValue2(),
    			rule.getScaleValue3(), rule.getScaleValue4(),
    			rule.getScaleValue5(), rule.getScaleValue6()};
    	return scaleValues[grade];
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
	 * @return the remarkTemplate
	 */
	public String getRemarkTemplate() {
		return remarkTemplate;
	}

	/**
	 * @param remarkTemplate the remarkTemplate to set
	 */
	public void setRemarkTemplate(String remarkTemplate) {
		this.remarkTemplate = remarkTemplate;
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
