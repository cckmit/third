/*
 * $Id: LifeStageableObject.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
 package cn.redflagsoft.base.bean;


public abstract class LifeStageableObject extends RFSObject implements LifeStageable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5056327202604341619L;

	/**
	 * ��ת��ΪLifeStage�Ķ���
	 * 
	 * ��Ҫ���õ�����Ϊ��id, name, managerId, managerName, objectType, 
	 * remark, status, type.
	 */
	public LifeStage toLifeStage() {
		return new LifeStage(this);
	}
}
