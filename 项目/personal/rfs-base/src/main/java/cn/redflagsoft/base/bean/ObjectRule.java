/*
 * $Id: ObjectRule.java 3996 2010-10-18 06:56:46Z lcj $
 * ObjectRule.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class ObjectRule extends VersionableBean implements Observable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7242724096396060095L;
	private Long sn;
	private Long objectID;
	private Long ruleID;
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setId(id);
	}
	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}
	/**
	 * 编号
	 * 
	 * 唯一
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * @return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}
	/**
	 * 对象
	 * @param objectID the objectID to set
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	/**
	 * @return the ruleID
	 */
	public Long getRuleID() {
		return ruleID;
	}
	/**
	 * 规则
	 * @param ruleID the ruleID to set
	 */
	public void setRuleID(Long ruleID) {
		this.ruleID = ruleID;
	}
}
