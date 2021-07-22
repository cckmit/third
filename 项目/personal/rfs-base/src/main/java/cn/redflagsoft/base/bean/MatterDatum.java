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
	
	public static final int TYPE_Ĭ�� = 0;
	public static final int TYPE_���� = 1;

	/**
	 * Ĭ�ϵ�bizActionֵ��
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
	 * ���������ص��ĺ��ڵ�ǰҵ�������е��ֶ����ƣ�
	 * ���û�й����ĺţ���Ϊnull��
	 */
	private String fileNoField;
	/**
	 * �����϶�����ļ��ĵ��ӵ��������ļ�id�ڱ��е��ֶ����ơ�
	 * ���û���ļ�id����Ϊnull��
	 */
	private String fileIdField;
	
	/**
	 * ҵ���������Ҫָ��ͣ��������ȡ������������Ķ��塣
	 * ����MatterAffair���ACTION���塣
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
	 * ��������
	 * 
	 * ����
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
	 * ��������
	 * 
	 * ����
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
	 * ��������
	 * 
	 * ����
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
	 * ����
	 * 
	 * ����
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
	 * ��������
	 * 
	 * ����
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
	 * ����
	 * 
	 * Ĭ��Ϊ1
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
	 * ������
	 * 
	 * Ĭ��Ϊ0
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
	 * ������
	 * 
	 * Ĭ��Ϊ0
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
	 * Ԥѡ
	 * 
	 * Ĭ��Ϊ0����ʾ��ѡ
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
	 * ˳��
	 * 
	 * Ĭ��Ϊ0����ʾ����
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
	 * 1 ���, 2 �滻, 3 ɾ��, Ĭ��Ϊ 0
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
	 * 1 ���, 2 �滻, 3 ɾ��, Ĭ��Ϊ 0
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
