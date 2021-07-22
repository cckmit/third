/*
 * $Id: BizLog.java 5734 2012-05-18 12:36:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BizLog {
	
	int taskType();
	
	int workType();
	
	/**
	 * 要记录的对象的位置：-1 方法的返回值，大于等于0时表示方法的输入参数。
	 * @return
	 */
	int objectIndex() default -100;
	
	/**
	 * 执行者ID。-1则取当前登录用户。
	 * @return
	 */
	long userId() default -1;
}
