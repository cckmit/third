/*
 * $Id: ObjectTypeUtils.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * ȡ��ĳ���ඨ���ObjectType.
	 * �ȳ���ͨ��Annotation��ȡ���ObjectTypeֵ����û������ȥ��ľ�̬�ֶ�
	 * OBJECT_TYPE��ֵ��
	 * 
	 *  
	 * @param clazz
	 * @return ����ֵ����Ϊnull��
	 */
	public static Integer getObjectType(Class<?> clazz){
		//�ȳ���ʹ��annotation
		ObjectType type = AnnotationUtils.findAnnotation(clazz, ObjectType.class);
		if(type != null){
			return type.value();
		}

		//�ٳ��Է��ʾ�̬�ֶΡ�
		try {
			Field field = clazz.getField(ObjectType.OBJECT_TYPE_FIELD_NAME);
			Integer t = (Integer) field.get(null);
			if(IS_DEBUG_ENABLED){
				log.debug(clazz.getName() + " ��̬���� " + ObjectType.OBJECT_TYPE_FIELD_NAME + ": " + t);
			}
			return t;
		}catch(NoSuchFieldException e){
			//if(IS_DEBUG_ENABLED){
			//	log.debug(clazz.getName() + " û�о�̬����: " + ObjectType.OBJECT_TYPE_FIELD_NAME);
			//}
		}catch (Exception e) {
			//log.warn("ȡ��̬�ֶγ���, " + clazz + ": " + e);	
			//if(IS_DEBUG_ENABLED){
			//	log.debug(clazz.getName() + " ȡ��̬���Գ���: " + ObjectType.OBJECT_TYPE_FIELD_NAME + " : " + e);
			//}
		}
		return null;
	}
}
