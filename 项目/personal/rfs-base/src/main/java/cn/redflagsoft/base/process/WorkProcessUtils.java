/*
 * $Id: WorkProcessUtils.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;

import cn.redflagsoft.base.process.annotation.ProcessType;

/**
 * @author Alex Lin
 *
 */
public class WorkProcessUtils {
	public static final String WORK_PROCESS_TYPE_FIELD_NAME = "TYPE";
	private static final Log log = LogFactory.getLog(WorkProcessUtils.class);

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Integer getProcessType(Class<WorkProcess> clazz){
		//œ»≥¢ ‘ π”√annotation
		ProcessType type = AnnotationUtils.findAnnotation(clazz, ProcessType.class);
		if(type != null){
			return type.value();
		}
		
		//‘Ÿ≥¢ ‘∑√Œ æ≤Ã¨◊÷∂Œ°£
		try {
			Field field = clazz.getField(WORK_PROCESS_TYPE_FIELD_NAME);
			return (Integer) field.get(null);
		} catch (Exception e) {
			log.warn("»°æ≤Ã¨◊÷∂Œ≥ˆ¥Ì, " + clazz + ": " + e);
			return null;
		}
	}
}
