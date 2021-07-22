/*
 * $Id: CodeMapValueOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.order;

/**
 * ����CodeMap��value������Map�еĶ��������š�
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CodeMapValueOrderFinder extends AbstractCodeMapOrderFinder {

	/**
	 * @param glossaryCategory
	 */
	public CodeMapValueOrderFinder(short glossaryCategory) {
		super(glossaryCategory);
	}

	/**
	 * @param clazz
	 * @param staticFieldPrefix
	 */
	public CodeMapValueOrderFinder(Class<?> clazz, String staticFieldPrefix) {
		super(clazz, staticFieldPrefix);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrderFinder#getOrder(java.lang.Object)
	 */
	public int getOrder(String value) {
		return super.getOrderByValue(value);
	}

}
