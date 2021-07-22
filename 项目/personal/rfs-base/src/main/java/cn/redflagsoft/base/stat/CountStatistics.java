/*
 * $Id: CountStatistics.java 5128 2011-11-25 07:29:25Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.stat;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CountStatistics extends CategorizedStatistics {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4660970451484979672L;

	/**
	 * @param categoryName
	 */
	CountStatistics(String categoryName) {
		super(categoryName);
	}

	long count;
}
