/*
 * $Id: AnnotationAwareWorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import org.springframework.core.annotation.AnnotationUtils;

import cn.redflagsoft.base.process.annotation.ProcessType;

/**
 * @author Alex Lin
 *
 */
public class AnnotationAwareWorkProcessManager extends AbstractAwareWorkProcessManager{

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.AbstractAwareWorkProcessManager#getType(java.lang.Object)
	 */
	@Override
	protected Integer getType(Object bean) {
		ProcessType type = AnnotationUtils.findAnnotation(bean.getClass(), ProcessType.class);
		if(type != null){
			return type.value();
		}
		return null;
	}   
}
