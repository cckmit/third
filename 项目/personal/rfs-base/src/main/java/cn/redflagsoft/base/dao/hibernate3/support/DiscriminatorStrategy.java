/*
 * $Id: DiscriminatorStrategy.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3.support;

/**
 * @author Alex Lin
 *
 */
public interface DiscriminatorStrategy {
	/**
	 * 根据鉴别器的值在所有继承映射中查找该值所鉴别的对象类型。
	 * 
	 * @param discriminatorValue 鉴别值
	 * @return 对象类型
	 */
	Class<?> getEntityClassByDiscriminatorValue(String discriminatorValue);
	/**
	 * 根据鉴别器的值和超类在指定的超类的继承映射中查找该值所鉴别的对象类型。
	 * 
	 * @param <T> 超类类型。
	 * @param discriminatorValue
	 * @param superClass
	 * @return
	 */
	<T> Class<? extends T> getEntityClassByDiscriminatorValue(String discriminatorValue, Class<T> superClass);
}
