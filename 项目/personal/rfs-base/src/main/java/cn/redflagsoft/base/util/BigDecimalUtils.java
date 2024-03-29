/*
 * $Id: BigDecimalUtils.java 5305 2012-01-05 07:22:15Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.math.BigDecimal;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class BigDecimalUtils {
	
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	/**
	 * 大数相加，当两个数都为 null 时，返回 null，否则 null 数按 0 计算。
	 * @param one
	 * @param two
	 * @return
	 */
	public static BigDecimal add(BigDecimal one, BigDecimal two){
		if(one != null){
			if(two != null){
				return one.add(two);
			}else{
				return one;
			}
		}else{
			return two;
		}
	}
	
	public static BigDecimal nullToZero(BigDecimal bd){
		return bd != null ? bd : BigDecimal.ZERO;
	}
	
	
	/**
	 * 大数相减，当两个数都为 null 时，返回 null，否则 null 数按 0 计算。
	 * @param one
	 * @param two
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal one, BigDecimal two){
		if(one != null){
			if(two != null){
				return one.subtract(two);
			}else{
				return one;
			}
		}else{
			if(two != null){
				return BigDecimal.ZERO.subtract(two);
			}else{
				return null;
			}
		}
	}
	
	
	/**
	 * 两个大数相除。
	 * 使用ROUND_HALF_UP模式。
	 * 
	 * @param one
	 * @param two
	 * @param scale 小数位数。
	 * @return
	 */
	public static BigDecimal divide(BigDecimal one, BigDecimal two, int scale){
		if(one == null || BigDecimal.ZERO.equals(one)){
			return BigDecimal.ZERO;
		}
		two = nullToZero(two);
		if(BigDecimal.ZERO.equals(two)){
			throw new IllegalArgumentException("除数不能为空或者0");
		}
		return nullToZero(one).divide(two, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	
	/**
	 * 两个大数相乘。
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal one, BigDecimal two){
		return nullToZero(one).multiply(nullToZero(two));
	}
	
	
	/**
	 * 获取两数比率。
	 * @param one
	 * @param two
	 * @return
	 */
	public static BigDecimal getRate(BigDecimal one, BigDecimal two){
		if(two == null || BigDecimal.ZERO.equals(two)){
			return null;
		}
		return BigDecimalUtils.divide(one, two, 4);
	}
	
	/**
	 * 获取两数比率百分比形式的字串。
	 * @param one
	 * @param two
	 * @return
	 */
	public static String getRateString(BigDecimal one, BigDecimal two){
		if(two == null || BigDecimal.ZERO.equals(two)){
			return "-";
		}
		try {
			BigDecimal rate = getRate(one, two);
			if(rate == null){
				return null;
			}
			return "" + rate.multiply(new BigDecimal(100)).setScale(2) + "%";
		} catch (Exception e) {
			return "-";
		}
	}
}
