/*
 * $Id: WorkDef.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;


/**
 * Work����ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface WorkDef extends BizDef{
	/**
	 * ��Ч�����͡�
	 */
	int INVALID_TYPE = -1;
	
	/**
	 * Work���͡�
	 * @return
	 */
	int getWorkType();
	
	/**
	 * ���ơ�
	 * @return
	 */
	String getName();
	
	/**
	 * ��ȡ����task�����͡�
	 * @return
	 */
	int getTaskType();
	
	/**
	 * 
	 * @return
	 */
	String getTypeAlias();
	
	/**
	 * 
	 * @return
	 */
	String getDutyer();
}
