/*
 * $Id: WorkDefinition.java 6108 2012-11-09 08:24:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.bean.SerializableEntity;
import org.opoo.ndao.domain.Versionable;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.util.CodeMapUtils;

/**��������
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkDefinition extends SerializableEntity<Integer> implements WorkDef, VersionLogable, Versionable{
	private static final long serialVersionUID = 5645196084709182998L;
	
	private String name;								//��������
	private int taskType;								//������������
	private int displayOrder;							//˳��
	private int type;									//����
	private byte status;								//״̬
	private String remark;								//��ע
	private Long creator;								//������
	private Long modifier;								//����޸���
	private Date creationTime;							//����ʱ��
	private Date modificationTime;						//����޸�ʱ��
	private String summaryTemplate;						//ժҪģ��
	private String application;							//��Ӧ��Ӧ�õ�ַ��·������������ļ��ȵ�
	private String typeAlias;							//���͵ı���
	private String dutyer;								//���ε�λ�����壩���������罨�赥λ�����ľֵ�
	private int dutyerType;	//����������
	
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
