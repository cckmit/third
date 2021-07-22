/*
 * $Id: BizLog.java 5734 2012-05-18 12:36:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * Ҫ��¼�Ķ����λ�ã�-1 �����ķ���ֵ�����ڵ���0ʱ��ʾ���������������
	 * @return
	 */
	int objectIndex() default -100;
	
	/**
	 * ִ����ID��-1��ȡ��ǰ��¼�û���
	 * @return
	 */
	long userId() default -1;
}
