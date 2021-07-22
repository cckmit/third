/*
 * $Id: TestBeforeAdvice.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author Alex Lin
 *
 */
public class TestBeforeAdvice implements MethodBeforeAdvice {

	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method arg0, Object[] arg1, Object arg2)	throws Throwable {
		//if(AbstractMethodInterceptor.WORK_PROCESS_EXECUTE_METHOD_NAME.equals(arg0.getName())){
			System.out.println("--------TestBeforeAdvice--------");
			System.out.println("开始执行方法：MethodName: " + arg0.getName());
			//System.out.println("--------TestBeforeAdvice--------");
		//}
	}
}
