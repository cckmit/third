/*
 * $Id: RFSObjectRef.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

/**
 * ��ҵ���������á�
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.1
 */
public interface RFSObjectRef {
	
	Long getRefObjectId();

	Integer getRefObjectType();

	String getRefObjectName();
}
