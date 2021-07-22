/*
 * $Id: CodeMapValueOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.order;

/**
 * 根据CodeMap的value来查找Map中的对象的排序号。
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
