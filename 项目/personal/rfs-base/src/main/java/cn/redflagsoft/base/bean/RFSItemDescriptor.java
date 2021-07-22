/*
 * $Id: RFSItemDescriptor.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RFSItemDescriptor extends RFSEntityDescriptor implements RFSItemable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3262282680341789835L;

	/**
	 * 
	 */
	public RFSItemDescriptor() {
		super();
	}

	/**
	 * @param bean
	 */
	public RFSItemDescriptor(RFSEntityObject bean) {
		super(bean);
	}

	/**
	 * @param objectType
	 * @param id
	 */
	public RFSItemDescriptor(int objectType, long id) {
		super(objectType, id);
	}
}
