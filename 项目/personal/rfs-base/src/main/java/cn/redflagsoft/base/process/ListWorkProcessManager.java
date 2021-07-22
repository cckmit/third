/*
 * $Id: ListWorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Alex Lin
 *
 */
public class ListWorkProcessManager extends SimpleWorkProcessManager {
	public static final String TYPE_FIELD_NAME = "TYPE";
	
	protected Integer getType(Class<?> clazz) {
		try {
			Field field = clazz.getField(TYPE_FIELD_NAME);
			return (Integer) field.get(null);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过设置一个List来完成注册。
	 * @param list
	 */
	public void setListWorkProcesses(List<WorkProcess> list){
		for(WorkProcess wp: list){
			Integer type = getType(wp.getClass());
			if(type != null){
				super.addWorkProcess(type, wp);
			}
		}
	}
}
