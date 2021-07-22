/*
 * $Id: DatumCategory.java 6427 2014-08-22 04:08:26Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.ndao.domain.Versionable;

import cn.redflagsoft.base.annotation.DisplayName;
import cn.redflagsoft.base.util.CodeMapUtils;

public class DatumCategory extends VersionableBean implements Versionable {
	public static final String YES = "√";
	
	@DisplayName("市区档案馆")
	public static final int TO_SQDAG = 1;
	@DisplayName("局档案馆")
	public static final int TO_JDAG = 2;
	
	//'1计划协调部；2合同财务部；3工程部；4工程技术部；5办公室';
	@DisplayName("计划协调部")
	public static final int TRANSFER_TO_JHXTB = 1;
	@DisplayName("合同财务部")
	public static final int TRANSFER_TO_HTCWB = 2;
	@DisplayName("工程部")
	public static final int TRANSFER_TO_GCB = 3;
	@DisplayName("工程技术部")
	public static final int TRANSFER_TO_GCJSB = 4;
	@DisplayName("办公室")
	public static final int TRANSFER_TO_BGS = 5;
	
	@DisplayName("施工单位")
	public static final int DATUM_RELATIVE_ORG_SGDW = 1;
	@DisplayName("监理单位")
	public static final int DATUM_RELATIVE_ORG_JLDW = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2689089084323844523L;
	private String name;
	private String abbr;
	private String form;
	private byte store = 9;
	private byte origin;
	private Date creationTime;
	private Date modificationTime;
	private String remark;
	private Integer to;//档案去向
	private boolean szgc;//项目种类：市政工程
	private boolean jzgc;//项目种类：建筑工程
	private boolean xxgc;//项目种类：小型工程
	private boolean accessory1;//管理规定：附件1
	private boolean accessory2;//管理规定：附件2
	private boolean accessory3;//管理规定：附件3
	private Integer transferTo;//移交责任部门
	private Integer datumRelativeOrg;//资料相关单位
	private Long folderId;//分类目录ID
	private String documentNameTemplate;
	
	/**
	 * 创建者。
	 */
	private Long creator;
	/**
	 * 修改者。
	 */
	private Long modifier;
	/**
	 * 状态。
	 * 
	 */
	private byte status;
	/**
	 * 种类、类型。
	 */
//	private int type;


	/**
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
	 * @return the form
	 */
	public String getForm() {
		return form;
	}
	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}
	/**
	 * @return the store
	 */
	public byte getStore() {
		return store;
	}
	/**
	 * @param store the store to set
	 */
	public void setStore(byte store) {
		this.store = store;
	}
	/**
	 * @return the origin
	 */
	public byte getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(byte origin) {
		this.origin = origin;
	}
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}
	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the creator
	 */
	public Long getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * @return the modifier
	 */
	public Long getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(Long modifier) {
		this.modifier = modifier;
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
	 * @return the to
	 */
	public Integer getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(Integer to) {
		this.to = to;
	}
	/**
	 * @return the szgc
	 */
	public boolean isSzgc() {
		return szgc;
	}
	
	public String getSzgcLabel(){
		return isSzgc() ? YES : "";
	}
	/**
	 * @param szgc the szgc to set
	 */
	public void setSzgc(boolean szgc) {
		this.szgc = szgc;
	}
	
	/**
	 * @return the jzgc
	 */
	public boolean isJzgc() {
		return jzgc;
	}
	
	public String getJzgcLabel(){
		return isJzgc() ? YES : "";
	}
	/**
	 * @param jzgc the jzgc to set
	 */
	public void setJzgc(boolean jzgc) {
		this.jzgc = jzgc;
	}
	/**
	 * @return the xxgc
	 */
	public boolean isXxgc() {
		return xxgc;
	}
	
	public String getXxgcLabel(){
		return isXxgc() ? YES : "";
	}
	
	/**
	 * @param xxgc the xxgc to set
	 */
	public void setXxgc(boolean xxgc) {
		this.xxgc = xxgc;
	}
	/**
	 * @return the accessory1
	 */
	public boolean isAccessory1() {
		return accessory1;
	}
	
	public String getAccessory1Label(){
		return isAccessory1() ? YES : "";
	}
	/**
	 * @param accessory1 the accessory1 to set
	 */
	public void setAccessory1(boolean accessory1) {
		this.accessory1 = accessory1;
	}
	/**
	 * @return the accessory2
	 */
	public boolean isAccessory2() {
		return accessory2;
	}
	
	public String getAccessory2Label(){
		return isAccessory2() ? YES : "";
	}
	/**
	 * @param accessory2 the accessory2 to set
	 */
	public void setAccessory2(boolean accessory2) {
		this.accessory2 = accessory2;
	}
	/**
	 * @return the accessory3
	 */
	public boolean isAccessory3() {
		return accessory3;
	}
	
	public String getAccessory3Label(){
		return isAccessory3() ? YES : "";
	}
	/**
	 * @param accessory3 the accessory3 to set
	 */
	public void setAccessory3(boolean accessory3) {
		this.accessory3 = accessory3;
	}
	/**
	 * @return the transferTo
	 */
	public Integer getTransferTo() {
		return transferTo;
	}
	/**
	 * @param transferTo the transferTo to set
	 */
	public void setTransferTo(Integer transferTo) {
		this.transferTo = transferTo;
	}
	/**
	 * @return the datumRelativeOrg
	 */
	public Integer getDatumRelativeOrg() {
		return datumRelativeOrg;
	}
	/**
	 * @param datumRelativeOrg the datumRelativeOrg to set
	 */
	public void setDatumRelativeOrg(Integer datumRelativeOrg) {
		this.datumRelativeOrg = datumRelativeOrg;
	}
	/**
	 * @return the folderId
	 */
	public Long getFolderId() {
		return folderId;
	}
	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	/**
	 * @return the documentNameTemplate
	 */
	public String getDocumentNameTemplate() {
		return documentNameTemplate;
	}
	/**
	 * @param documentNameTemplate the documentNameTemplate to set
	 */
	public void setDocumentNameTemplate(String documentNameTemplate) {
		this.documentNameTemplate = documentNameTemplate;
	}
	
	/**
	 * 档案去向
	 * @return 档案去向
	 */
	public String getToName(){
		if(getTo() == null){
			return null;
		}
		return CodeMapUtils.getCodeName(DatumCategory.class, "TO", getTo());
	}
	
	/**
	 * 移交责任部门
	 * @return 移交责任部门
	 */
	public String getTransferToName(){
		Integer transferTo2 = getTransferTo();
		if(transferTo2 == null){
			return null;
		}
		return CodeMapUtils.getCodeName(DatumCategory.class, "TRANSFER_TO", transferTo2);
	}
	
	/**
	 * 资料相关单位
	 * @return
	 */
	public String getDatumRelativeOrgName(){
		Integer xx = getDatumRelativeOrg();
		if(xx == null){
			return null;
		}
		return CodeMapUtils.getCodeName(DatumCategory.class, "DATUM_RELATIVE_ORG", xx); 
	}
}
