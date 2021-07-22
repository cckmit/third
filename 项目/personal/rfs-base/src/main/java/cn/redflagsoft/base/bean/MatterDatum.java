/*
 * $Id: MatterDatum.java 4680 2011-09-14 02:14:51Z lcj $
 * BizDatum.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class MatterDatum extends VersionableBean{
	private static final long serialVersionUID = -541748810627581437L;
	
	public static final byte ACTION_DEFAULT = 0;
	public static final byte ACTION_ADDITION = 1;
	public static final byte ACTION_REPLACE = 2;
	public static final byte ACTION_DELETE = 3;
	public static final byte ACTION_DELETE_REPLACE = 4;
	
	public static final int TYPE_默认 = 0;
	public static final int TYPE_隐藏 = 1;

	/**
	 * 默认的bizAction值。
	 */
	public static final byte BIZ_ACTION_DEFAULT = MatterAffair.ACTION_UNKOWN;
	
	private Long id;
	private byte category;
	private int taskType;
	private int workType;
	private int processType;
	private Long matterID;
	private Long datumType;
	private short count=1;
	private byte orgNum;
	private byte copyNum;
	private byte selected;
	private byte displayOrder;
	private byte taskAction;
	private byte objectAction;
	/**
	 * 与该资料相关的文号在当前业务办理表单中的字段名称，
	 * 如果没有关联文号，则为null。
	 */
	private String fileNoField;
	/**
	 * 该资料对象的文件的电子档保存后的文件id在表单中的字段名称。
	 * 如果没有文件id，则为null。
	 */
	private String fileIdField;
	
	/**
	 * 业务操作，主要指暂停、重启、取消等特殊操作的定义。
	 * 参照MatterAffair里的ACTION定义。
	 * 
	 * @see MatterAffair#ACTION_ACCEPT_MATTER
	 */
	private byte bizAction = BIZ_ACTION_DEFAULT;
	
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
	
	
	public byte getCategory() {
		return category;
	}
	public void setCategory(byte category) {
		this.category = category;
	}
	/**
	 * 任务种类
	 * 
	 * 不空
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
	 * 工作种类
	 * 
	 * 不空
	 * @return the workType
	 */
	public int getWorkType() {
		return workType;
	}
	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(int workType) {
		this.workType = workType;
	}
	/**
	 * 事务种类
	 * 
	 * 不空
	 * @return the processType
	 */
	public int getProcessType() {
		return processType;
	}
	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(int processType) {
		this.processType = processType;
	}
	/**
	 * 事项
	 * 
	 * 不空
	 * @return the matterID
	 */
	public Long getMatterID() {
		return matterID;
	}
	/**
	 * @param matterID the matterID to set
	 */
	public void setMatterID(Long matterID) {
		this.matterID = matterID;
	}
	/**
	 * 资料种类
	 * 
	 * 不空
	 * @return the datumType
	 */
	public Long getDatumType() {
		return datumType;
	}
	/**
	 * @param datumType the datumType to set
	 */
	public void setDatumType(Long datumType) {
		this.datumType = datumType;
	}
	/**
	 * 数量
	 * 
	 * 默认为1
	 * @return the count
	 */
	public short getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(short count) {
		this.count = count;
	}
	/**
	 * 正本数
	 * 
	 * 默认为0
	 * @return the orgNum
	 */
	public byte getOrgNum() {
		return orgNum;
	}
	/**
	 * @param orgNum the orgNum to set
	 */
	public void setOrgNum(byte orgNum) {
		this.orgNum = orgNum;
	}
	/**
	 * 副本数
	 * 
	 * 默认为0
	 * @return the copyNum
	 */
	public byte getCopyNum() {
		return copyNum;
	}
	/**
	 * @param copyNum the copyNum to set
	 */
	public void setCopyNum(byte copyNum) {
		this.copyNum = copyNum;
	}
	/**
	 * 预选
	 * 
	 * 默认为0，表示不选
	 * @return the selected
	 */
	public byte getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(byte selected) {
		this.selected = selected;
	}
	/**
	 * 顺序
	 * 
	 * 默认为0，表示忽略
	 * @return the displayOrder
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	/**
	 * 1 添加, 2 替换, 3 删除, 默认为 0
	 * 
	 * @return byte
	 */
	public byte getTaskAction() {
		return taskAction;
	}
	
	public void setTaskAction(byte taskAction) {
		this.taskAction = taskAction;
	}
	
	/**
	 * 1 添加, 2 替换, 3 删除, 默认为 0
	 * 
	 * @return byte
	 */	
	public byte getObjectAction() {
		return objectAction;
	}
	
	public void setObjectAction(byte objectAction) {
		this.objectAction = objectAction;
	}
	public String getFileNoField() {
		return fileNoField;
	}
	public void setFileNoField(String fileNoField) {
		this.fileNoField = fileNoField;
	}
	public String getFileIdField() {
		return fileIdField;
	}
	public void setFileIdField(String fileIdField) {
		this.fileIdField = fileIdField;
	}
	/**
	 * @return the bizAction
	 */
	public byte getBizAction() {
		return bizAction;
	}
	/**
	 * @param bizAction the bizAction to set
	 */
	public void setBizAction(byte bizAction) {
		this.bizAction = bizAction;
	}
}
