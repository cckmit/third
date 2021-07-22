/*
 * $Id: Glossary.java 6172 2013-01-10 03:04:16Z lcj $
 * Glossary.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class Glossary extends VersionableBean{
	/**
	 * 
	 */
	
	public static final short CATEGORY_AFFAIR = 101;         //业务事别
	public static final short CATEGORY_MATTER = 102;         //业务事项	
	public static final short CATEGORY_BIZTYPE=110;          //业务类别
	public static final short CATEGORY_JOBTYPE=111;          //作业类型
	public static final short CATEGORY_TASKTYPE=112;         //任务类型
	public static final short CATEGORY_WORKTYPE=113;         //工作类型
	public static final short CATEGORY_PROCESSTYPE=114;      //事务类型
	public static final short CATEGORY_MATTERAFFAIR_ACTION=131; //事项事别操作
	public static final short CATEGORY_MATTERAFFAIR_TASKACTION=132;//事项业务操作
	public static final short CATEGORY_MATTERAFFAIR_OBJECTACTION=133;//事项对象操作
	public static final short CATEGORY_RECEIVER_CATEGORY=141;        //业务转移类型
	public static final short CATEGORY_MATTERAFFAIR_BIZACTION=151;   //事项处理
	public static final short CATEGORY_TASK_STATUS=152; //任务状态
	public static final short CATEGORY_PHRASE_RESULTTYPE=161;     //短语结果类型
	public static final short CATEGORY_PROJECT_STATUS=162;        //项目状态
	public static final short CATEGORY_DUTYERTYPE=163;            //责任人类型
	public static final short CATEGORY_PROJECT_TYPE=164;          //项目类型
	public static final short CATEGORY_PROJECTPLAN_KIND=165;      //项目计划性质
	public static final short CATEGORY_RISK_TYPE=171; //风险种类
	public static final short CATEGORY_RISK_OBJECTTYPE=172;//风险对象种类
	public static final short CATEGORY_RISK_DUTYERTYPE=173;//风险责任人种类
	public static final short CATEGORY_RISK_VALUETYPE=174; //风险值种类
	public static final short CATEGORY_RISK_VALUE_SOURCE=175;//风险值来源
	public static final short CATEGORY_RISK_REFTYPE=176;  //风险关联种类
	public static final short CATEGORY_INVESTORGIN=181;   //投资来源
	public static final short CATEGORY_OBJECTLIFE_TYPE=1000;   //对象周期类型
	public static final short CATEGORY_PROJECT_LIFESTAGE = 1001;//项目生命周期
	public static final short CATEGORY_SUBPROJECT_LIFESTAGE = 1002; //子项目生命周期
	public static final short CATEGORY_BID_LIFESTAGE = 1003;        //采购生命周期
	public static final short CATEGORY_CONTRACT_LIFESTAGE = 1004;   //合同生命周期
	public static final short CATEGORY_PROJECTMODIFY_LIFESTAGE = 1005;//项目变更生命周期
	public static final short CATEGORY_BIZ_DEF_DUTYER_TYPE = 1107;//业务三级责任人类型
	public static final short CATEGORY_CAUTION_SMSG_YWZR = 1102;
	public static final short CATEGORY_CAUTION_SMSG_TITLE = 1109;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9021132824200466813L;
	private Long rn;
	private short category;
	private short displayOrder;
	private int code;
	private String term;
	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getRn();
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		setRn(id);
	}
	/**
	 * @return the rn
	 */
	public Long getRn() {
		return rn;
	}
	/**记录编号
	 * @param rn the rn to set
	 */
	public void setRn(Long rn) {
		this.rn = rn;
	}
	/**
	 * @return the category
	 */
	public short getCategory() {
		return category;
	}
	/**
	 * 种类
	 * 
	 * 默认为0，表示忽略
	 * @param category the category to set
	 */
	public void setCategory(short category) {
		this.category = category;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 代码
	 * 
	 * 不空
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the displayOrder
	 */
	public short getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * 顺序
	 * 
	 * 默认为0，表示忽略
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * 名称
	 * 
	 * 三十个汉字以内
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
}
