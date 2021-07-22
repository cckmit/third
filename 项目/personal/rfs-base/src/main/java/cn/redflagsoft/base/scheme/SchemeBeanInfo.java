/*
 * $Id: SchemeBeanInfo.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme;

/**
 * Scheme ��������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SchemeBeanInfo {
	/**
	 * ����BeanҪ���õķ�����
	 * 
	 * @return
	 */
	String getMethod();
	
	/**
	 * ��ȡBean���õķ�����
	 * @param method
	 */
	void setMethod(String method);
	
	/**
	 * ��ȡBean���õ����֡�
	 * 
	 * @return
	 */
	String getBeanName();
	
	
	void setBeanName(String name);
}
