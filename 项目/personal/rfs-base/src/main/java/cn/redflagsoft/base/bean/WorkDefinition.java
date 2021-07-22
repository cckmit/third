/*
 * $Id: WorkDefinition.java 6108 2012-11-09 08:24:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.bean.SerializableEntity;
import org.opoo.ndao.domain.Versionable;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.util.CodeMapUtils;

/**工作定义
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkDefinition extends SerializableEntity<Integer> implements WorkDef, VersionLogable, Versionable{
	private static final long serialVersionUID = 5645196084709182998L;
	
	private String name;								//工作名称
	private int taskType;								//所属任务类型
	private int displayOrder;							//顺序
	private int type;									//性质
	private byte status;								//状态
	private String remark;								//备注
	private Long creator;								//创建者
	private Long modifier;								//最后修改者
	private Date creationTime;							//创建时间
	private Date modificationTime;						//最后修改时间
	private String summaryTemplate;						//摘要模板
	private String application;							//对应的应用地址、路径、命令、场景文件等等
	private String typeAlias;							//类型的别名
	private String dutyer;								//责任单位（主体）描述，例如建设单位、发改局等
	private int dutyerType;	//责任人类型
	
	public String getTypeAlias() {
		return typeAlias;
	}

	public void setTypeAlias(String typeAlias) {
		this.typeAlias = typeAlias;
	}

	public String getSummaryTemplate() {
		return summaryTemplate;
	}

	public void setSummaryTemplate(String summaryTemplate) {
		this.summaryTemplate = summaryTemplate;
	}

	public int getWorkType(){
		return getId();
	}
	
	public void setWorkType(int workType){
		setId(workType);
	}
	
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
	 * @return the taskType
	 */
	public int getTaskType() {
		return taskType;
	}
	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the dutyer
	 */
	public String getDutyer() {
		return dutyer;
	}

	/**
	 * @param dutyer the dutyer to set
	 */
	public void setDutyer(String dutyer) {
		this.dutyer = dutyer;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizDef#getDutyerType()
	 */
	public int getDutyerType() {
		return dutyerType;
	}

	/**
	 * @param dutyerType the dutyerType to set
	 */
	public void setDutyerType(int dutyerType) {
		this.dutyerType = dutyerType;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizDef#getObjectType()
	 */
	public int getObjectType() {
		return ObjectTypes.WORK;
	}

	public String getDutyerTypeName(){
		return CodeMapUtils.getCodeName(Glossary.CATEGORY_BIZ_DEF_DUTYER_TYPE, getDutyerType());
	}
}
