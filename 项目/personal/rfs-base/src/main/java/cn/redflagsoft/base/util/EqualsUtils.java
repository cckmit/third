/*
 * $Id: EqualsUtils.java 5914 2012-06-28 01:42:08Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public final class EqualsUtils {
	
	public static boolean equals(Object x, Object y) {
		return x == y || ( x != null && y != null && x.equals(y) );
	}
	
	private EqualsUtils() {}

}
