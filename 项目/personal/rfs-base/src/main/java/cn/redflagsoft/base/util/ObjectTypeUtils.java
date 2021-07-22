/*
 * $Id: ObjectTypeUtils.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;

import cn.redflagsoft.base.annotation.ObjectType;

/**
 * @author Alex Lin
 *
 */
public class ObjectTypeUtils {
	private static final Log log = LogFactory.getLog(ObjectTypeUtils.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	/**
	 * 取得某个类定义的ObjectType.
	 * 先尝试通过Annotation获取类的ObjectType值，若没有则尝试去类的静态字段
	 * OBJECT_TYPE的值。
	 * 
	 *  
	 * @param clazz
	 * @return 返回值可能为null。
	 */
	public static Integer getObjectType(Class<?> clazz){
		//先尝试使用annotation
		ObjectType type = AnnotationUtils.findAnnotation(clazz, ObjectType.class);
		if(type != null){
			return type.value();
		}

		//再尝试访问静态字段。
		try {
			Field field = clazz.getField(ObjectType.OBJECT_TYPE_FIELD_NAME);
			Integer t = (Integer) field.get(null);
			if(IS_DEBUG_ENABLED){
				log.debug(clazz.getName() + " 静态属性 " + ObjectType.OBJECT_TYPE_FIELD_NAME + ": " + t);
			}
			return t;
		}catch(NoSuchFieldException e){
			//if(IS_DEBUG_ENABLED){
			//	log.debug(clazz.getName() + " 没有静态属性: " + ObjectType.OBJECT_TYPE_FIELD_NAME);
			//}
		}catch (Exception e) {
			//log.warn("取静态字段出错, " + clazz + ": " + e);	
			//if(IS_DEBUG_ENABLED){
			//	log.debug(clazz.getName() + " 取静态属性出错: " + ObjectType.OBJECT_TYPE_FIELD_NAME + " : " + e);
			//}
		}
		return null;
	}
}
