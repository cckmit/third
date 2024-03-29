/*
 * $Id: CountStatistics.java 5128 2011-11-25 07:29:25Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
