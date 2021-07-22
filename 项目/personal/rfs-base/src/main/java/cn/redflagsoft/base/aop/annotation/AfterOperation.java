/*
 * $Id: AfterOperation.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opoo.ndao.Domain;

import cn.redflagsoft.base.audit.Auditor;
import cn.redflagsoft.base.audit.impl.AfterOperationAuditor;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterOperation {
	/**
	 * 对象名
	 * 
	 * @return
	 */
	String targetName() default "";

	/**
	 * 操作方式
	 * 
	 * @return
	 */
	String operation();

	/***
	 * 被操作对象在参数中的下标.
	 * 如果该值为-1,则表示domain是方法的返回值。
	 * 
	 * @return
	 */
	int domainArgIndex() default -100;

	/**
	 * 被操作对象的类型
	 * 
	 * @return
	 */
	String domainType() default "";
	
	/**
	 * 被操作对象的类型。
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	Class<? extends Domain> domainClass() default Domain.class;

	/**
	 * 被操作对象的ID 在参数中的 下标
	 * 
	 * @return
	 */
	int domainIdArgIndex() default -1;

	/**
	 * 审核者。
	 * @return
	 */
	Class<? extends Auditor> auditor() default AfterOperationAuditor.class;

	/**
	 * 是都允许匿名访问。默认false。
	 * 
	 * @return
	 */
	boolean allowAnonymousCalls() default false;
	
	/**
	 * 详情。
	 * @return
	 */
	String details() default "";
	
	/**
	 * 描述。
	 */
	String description() default "";
}
