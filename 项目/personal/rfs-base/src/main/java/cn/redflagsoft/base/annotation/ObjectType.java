/*
 * $Id: ObjectType.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象类型。
 * 
 * 用于标识某业务对象的类型。通常，业务对象是指继承自 RFSObject 的对象。
 * 
 * @author Alex Lin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ObjectType {
	/**
	 * 业务对象类中定义的对象类型的常量属性名称。
	 */
	String OBJECT_TYPE_FIELD_NAME = "OBJECT_TYPE";
	
	/**
	 * 
	 * @return
	 */
	int value();
}
