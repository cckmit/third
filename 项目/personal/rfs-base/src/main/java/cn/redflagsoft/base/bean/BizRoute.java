/*
 * $Id: BizRoute.java 4615 2011-08-21 07:10:37Z lcj $
 * BizRoute.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class BizRoute extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5102416792569485482L;
	private Long id;
	private Byte category;
	private Integer bizType;
	private Long bizID;
	private String name;
	private String abbr;
	private byte sectNum;
	private byte stepNum;
	private byte nodeNum;
	private String note;
	
	
	/**
	 * id
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * ����
	 * 
	 * ���ࣺ1 ��ҵ��2 ����3 ����
	 * @return the category
	 */
	public Byte getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Byte category) {
		this.category = category;
	}
	/**
	 * ҵ��
	 * 
	 * ��ҵ����������߹�������
	 * @return the bizType
	 */
	public Integer getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	/**
	 * ����
	 * 
	 * ����ID��������ID��Ĭ��Ϊ0����ʾ����
	 * @return the bizID
	 */
	public Long getBizID() {
		return bizID;
	}
	/**
	 * @param bizID the bizID to set
	 */
	public void setBizID(Long bizID) {
		this.bizID = bizID;
	}
	/**
	 * ����
	 * 
	 * ��������
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * ���
	 * 
	 * ���̼��
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * ����
	 * 
	 * Ĭ��Ϊ0����ʾδ����
	 * @return the sectNum
	 */
	public byte getSectNum() {
		return sectNum;
	}
	/**
	 * @param sectNum the sectNum to set
	 */
	public void setSectNum(byte sectNum) {
		this.sectNum = sectNum;
	}
	/**
	 * ����
	 * 
	 * Ĭ��Ϊ0����ʾδ����
	 * @return the stepNum
	 */
	public byte getStepNum() {
		return stepNum;
	}
	/**
	 * @param stepNum the stepNum to set
	 */
	public void setStepNum(byte stepNum) {
		this.stepNum = stepNum;
	}
	/**
	 * �ڵ���
	 * 
	 * Ĭ��Ϊ0����ʾδ����
	 * @return the nodeNum
	 */
	public byte getNodeNum() {
		return nodeNum;
	}
	/**
	 * @param nodeNum the nodeNum to set
	 */
	public void setNodeNum(byte nodeNum) {
		this.nodeNum = nodeNum;
	}
	/**
	 * ע��
	 * 
	 * �����̵�������˵��
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	

}
