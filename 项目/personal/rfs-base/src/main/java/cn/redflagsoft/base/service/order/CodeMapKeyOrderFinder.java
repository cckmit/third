/*
 * $Id: CodeMapKeyOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.order;

/**
 * ����CodeMap��key������Map�еĶ��������š�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CodeMapKeyOrderFinder extends AbstractCodeMapOrderFinder {
	/**
	 * @param clazz
	 * @param staticFieldPrefix
	 */
	public CodeMapKeyOrderFinder(Class<?> clazz, String staticFieldPrefix) {
		super(clazz, staticFieldPrefix);
	}

	public CodeMapKeyOrderFinder(short glossaryCategory) {
		super(glossaryCategory);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrderFinder#getOrder(java.lang.Object)
	 */
	public int getOrder(String key) {
		return super.getOrderByKey(key);
	}

}
