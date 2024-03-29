/*
 * $Id: CategorizedStatistics.java 5128 2011-11-25 07:29:25Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.stat;

import java.io.Serializable;

/**
 * Statistics for a particular "category" (a named entity,
 * collection role, second level cache region or query).
 * 
 * @see org.hibernate.stat.CategorizedStatistics
 * @author Gavin King
 */
public class CategorizedStatistics implements Serializable {
	private static final long serialVersionUID = -2371672758261116496L;
	private final String categoryName;

	CategorizedStatistics(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
}