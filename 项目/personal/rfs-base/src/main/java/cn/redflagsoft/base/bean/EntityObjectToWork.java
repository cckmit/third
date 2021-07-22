/*
 * $Id: EntityObjectToWork.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

/**
 * RFSEntity��Work֮��Ĺ�ϵ
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.2
 */
public class EntityObjectToWork extends EntityObjectToBizLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7912052019369639100L;
	private long workSN;
	
	/**
	 * 
	 */
	public EntityObjectToWork() {
		super();
	}

	/**
	 * @param objectId
	 * @param objectType
	 */
	public EntityObjectToWork(long workSN, long objectId, int objectType) {
		super(objectId, objectType);
		this.workSN = workSN;
	}

	/**
	 * @return the workSN
	 */
	public long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN the workSN to set
	 */
	public void setWorkSN(long workSN) {
		this.workSN = workSN;
	}
	
	
}
