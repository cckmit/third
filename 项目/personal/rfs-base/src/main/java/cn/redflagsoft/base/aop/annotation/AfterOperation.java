/*
 * $Id: AfterOperation.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * ������
	 * 
	 * @return
	 */
	String targetName() default "";

	/**
	 * ������ʽ
	 * 
	 * @return
	 */
	String operation();

	/***
	 * �����������ڲ����е��±�.
	 * �����ֵΪ-1,���ʾdomain�Ƿ����ķ���ֵ��
	 * 
	 * @return
	 */
	int domainArgIndex() default -100;

	/**
	 * ���������������
	 * 
	 * @return
	 */
	String domainType() default "";
	
	/**
	 * ��������������͡�
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	Class<? extends Domain> domainClass() default Domain.class;

	/**
	 * �����������ID �ڲ����е� �±�
	 * 
	 * @return
	 */
	int domainIdArgIndex() default -1;

	/**
	 * ����ߡ�
	 * @return
	 */
	Class<? extends Auditor> auditor() default AfterOperationAuditor.class;

	/**
	 * �Ƕ������������ʡ�Ĭ��false��
	 * 
	 * @return
	 */
	boolean allowAnonymousCalls() default false;
	
	/**
	 * ���顣
	 * @return
	 */
	String details() default "";
	
	/**
	 * ������
	 */
	String description() default "";
}
