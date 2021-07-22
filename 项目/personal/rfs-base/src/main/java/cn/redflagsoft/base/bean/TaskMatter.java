/*
 * $Id: TaskMatter.java 3996 2010-10-18 06:56:46Z lcj $
 * TaskMatter.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class TaskMatter extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1699582767228555784L;
	private Long sn;
	private Long taskSN;
	private Long MatterID;
	private Integer bizRoute;
	private byte displayOrder;
	private byte result;
	private Integer createWork;
	private Integer modificationWork;
	private String matterCode;
	private String matterName;
	
	public String getMatterCode() {
		return matterCode;
	}

	public void setMatterCode(String matterCode) {
		this.matterCode = matterCode;
	}

	public String getMatterName() {
		return matterName;
	}

	public void setMatterName(String matterName) {
		this.matterName = matterName;
	}
	
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setSn(id);
	}
	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}
	/**
	 * ���
	 * 
	 * Ψһ
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * @return the taskSN
	 */
	public Long getTaskSN() {
		return taskSN;
	}
	/**
	 * ����
	 * 
	 * ��������
	 * @param taskSN the taskSN to set
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}
	/**
	 * @return the matterID
	 */
	public Long getMatterID() {
		return MatterID;
	}
	/**
	 * ����
	 * 
	 * ��������ID������Ϊ0���
	 * @param matterID the matterID to set
	 */
	public void setMatterID(Long matterID) {
		MatterID = matterID;
	}
	/**
	 * @return the bizRoute
	 */
	public Integer getBizRoute() {
		return bizRoute;
	}
	/**
	 * ����
	 * 
	 * �������̣���ͬʱ�ڣ����õ����̿��ܲ�ͬ
	 * @param bizRoute the bizRoute to set
	 */
	public void setBizRoute(Integer bizRoute) {
		this.bizRoute = bizRoute;
	}
	/**
	 * @return the displayOrder
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * ˳��
	 * 
	 * ��ʾ˳��Ĭ��Ϊ0����ʾ����
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}
	/**
	 * ���
	 * 
	 * 0 �ڰ죬1 �ɹ���2 ʧ�ܣ�3 ����
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}
	/**
	 * @return the createWork
	 */
	public Integer getCreateWork() {
		return createWork;
	}
	/**
	 * ��������
	 * @param createWork the createWork to set
	 */
	public void setCreateWork(Integer createWork) {
		this.createWork = createWork;
	}
	/**
	 * @return the modificationWork
	 */
	public Integer getModificationWork() {
		return modificationWork;
	}
	/**
	 * �޶�����
	 * @param modificationWork the modificationWork to set
	 */
	public void setModificationWork(Integer modificationWork) {
		this.modificationWork = modificationWork;
	}
}
