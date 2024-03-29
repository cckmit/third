/*
 * $Id: People.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.Date;

import org.opoo.ndao.Domain;

import com.googlecode.jsonplugin.annotations.JSON;

public class People extends VersionableBean implements Observable, LabelDataBean, Domain<Long> {
	private static final long serialVersionUID = 8980832672728675787L;
	private Long id;
	private String code;
	private String name;
	private String alias;
	private Date birthDay;
	private String paperType;
	private String paperNo;
	private byte paperElse;
	private String nativeAddr;
	private String homeTelNo;
	private String homeAddr;
	private String mobNo;
	private String faxNo;
	private String emailAddr;
	private String workEntity;
	private String workAddr;
	private String telNo;
	private Long objectID;
	private String privateMobNo;//PRIVATE_MOBNO

	public People() {
	}
	
	/**
	 * 对象
	 * 
	 * @return Long
	 */
	public Long getObjectID() {
		return objectID;
	}

	/**
	 * @param objectID
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

	/**
	 * 数据
	 * 
	 * @return Serializable
	 */
	public Serializable getData() {
		return getId();
	}

	/**
	 * 标签
	 * 
	 * @return String 
	 */
	public String getLabel() {
		return getName();
	}
	
	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 代码
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 姓名
	 * 
	 * 一般为5个汉字以内,至多15个汉字.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 曾用名
	 * 
	 * 曾经使用过的姓名,一般只有一个.
	 * @return String
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 生日
	 * 
	 * @return Date
	 */
	@JSON(format="yyyy-MM-dd")
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 */
	@JSON(format="yyyy-MM-dd")
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * 证件类别
	 * 
	 * 默认为ID,表示身份证
	 * @return String
	 */
	public String getPaperType() {
		return paperType;
	}

	/**
	 * @param paperType
	 */
	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	/**
	 * 证件号码
	 * 
	 * 一般为30个字符(15个汉字之内).
	 * @return String
	 */
	public String getPaperNo() {
		return paperNo;
	}

	/**
	 * @param paperNo
	 */
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	/**
	 * 其他证件
	 * 
	 * 其他证件,默认为0,表示忽略.
	 * @return byte
	 */
	public byte getPaperElse() {
		return paperElse;
	}

	/**
	 * @param paperElse
	 */
	public void setPaperElse(byte paperElse) {
		this.paperElse = paperElse;
	}

	/**
	 * 籍贯
	 * 
	 * @return String
	 */
	public String getNativeAddr() {
		return nativeAddr;
	}

	/**
	 * @param nativeAddr
	 */
	public void setNativeAddr(String nativeAddr) {
		this.nativeAddr = nativeAddr;
	}

	/**
	 * 家庭电话
	 * 
	 * @return String
	 */
	public String getHomeTelNo() {
		return homeTelNo;
	}

	/**
	 * @param homeTelNo
	 */
	public void setHomeTelNo(String homeTelNo) {
		this.homeTelNo = homeTelNo;
	}

	/**
	 * 家庭住址
	 * 
	 * @return String
	 */
	public String getHomeAddr() {
		return homeAddr;
	}

	/**
	 * @param homeAddr
	 */
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	/**
	 * 移动电话
	 * 
	 * @return String
	 */
	public String getMobNo() {
		return mobNo;
	}

	/**
	 * @param mobNo
	 */
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	/**
	 *  传真电话
	 * 
	 * @return String
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * 电子邮箱
	 * 
	 * @return String
	 */
	public String getEmailAddr() {
		return emailAddr;
	}

	/**
	 * @param emailAddr
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	/**
	 * 工作单位
	 * 
	 * @return String
	 */
	public String getWorkEntity() {
		return workEntity;
	}

	/**
	 * @param workEntity
	 */
	public void setWorkEntity(String workEntity) {
		this.workEntity = workEntity;
	}

	/**
	 * 工作地址
	 * 
	 * @return String
	 */
	public String getWorkAddr() {
		return workAddr;
	}

	/**
	 * @param wordAddr
	 */
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	/**
	 * 办公电话
	 * 
	 * @return String
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getPrivateMobNo() {
		return privateMobNo;
	}

	public void setPrivateMobNo(String privateMobNo) {
		this.privateMobNo = privateMobNo;
	}
	
	
}
