/*
 * $Id: BeanTypeAwareWorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.lang.reflect.Field;

/**
 * @author Alex Lin
 *
 */
public class BeanTypeAwareWorkProcessManager extends AbstractAwareWorkProcessManager {
	public static final String TYPE_FIELD_NAME = "TYPE";

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.AbstractAwareWorkProcessManager#getType(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Integer getType(Object bean) {
		try {
			Class clazz = bean.getClass();
			Field field = clazz.getField(TYPE_FIELD_NAME);
			return (Integer) field.get(null);
		} catch (Exception e) {
			log.warn("È¡¾²Ì¬×Ö¶Î³ö´í: " + bean + ", " + e.getMessage());
			return null;
		}
	}
}
