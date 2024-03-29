/*
 * $Id: ParametersCheckBeforeAdvice.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;
import org.springframework.aop.MethodBeforeAdvice;

import cn.redflagsoft.base.scheme.WorkScheme;

/**
 * 
 * @author Alex Lin
 *
 */
public class ParametersCheckBeforeAdvice implements MethodBeforeAdvice {
	private static final Log log = LogFactory.getLog(ParametersCheckBeforeAdvice.class);
	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method arg0, Object[] args, Object object) throws Throwable {
		if(!(object instanceof WorkScheme)){
			if(log.isDebugEnabled()){
				log.debug("拦截的对象不是WorkScheme，放弃参数检查：" + object);
			}
			return;
		}
		
		WorkScheme ws = (WorkScheme) object;
		Assert.notNull(ws.getWork(), "工作不能为空。");
		Assert.notNull(ws.getTask(), "任务不能为空。");
		Assert.notNull(ws.getObject(), "业务对象不能为空。");
	}
}
