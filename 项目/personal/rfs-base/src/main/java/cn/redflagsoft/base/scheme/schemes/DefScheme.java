/*
 * $Id: DefScheme.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes;

import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.TaskDefProvider;
import cn.redflagsoft.base.service.WorkDefProvider;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DefScheme extends AbstractScheme{
	private TaskDefProvider taskDefProvider;
	private WorkDefProvider workDefProvider;
	
	private Integer type;
	
	/**
	 * @return the taskDefProvider
	 */
	public TaskDefProvider getTaskDefProvider() {
		return taskDefProvider;
	}

	/**
	 * @param taskDefProvider the taskDefProvider to set
	 */
	public void setTaskDefProvider(TaskDefProvider taskDefProvider) {
		this.taskDefProvider = taskDefProvider;
	}

	/**
	 * @return the workDefProvider
	 */
	public WorkDefProvider getWorkDefProvider() {
		return workDefProvider;
	}

	/**
	 * @param workDefProvider the workDefProvider to set
	 */
	public void setWorkDefProvider(WorkDefProvider workDefProvider) {
		this.workDefProvider = workDefProvider;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public Object viewGetTaskDef(){
		Assert.notNull(type, "type不能为空");
		TaskDef def = taskDefProvider.getTaskDef(type);
		if(def != null){
			Map<String, Object> map = Maps.newHashMap();
			map.put("taskType", def.getTaskType());
			map.put("parentTaskType", def.getParentTaskType());
			map.put("name", def.getName());
			map.put("typeAlias", def.getTypeAlias());
			return map;
		}else{
			return null;
		}
	}
	
	public Object viewGetWorkDef(){
		Assert.notNull(type, "type不能为空");
		WorkDef def = workDefProvider.getWorkDef(type);
		if(def != null){
			Map<String, Object> map = Maps.newHashMap();
			map.put("workType", def.getWorkType());
			map.put("taskType", def.getTaskType());
			map.put("name", def.getName());
			map.put("workTypeAlias", def.getTypeAlias());
			
			TaskDef def2 = taskDefProvider.getTaskDef(def.getTaskType());
			if(def2 != null){
				map.put("taskTypeAlias", def2.getTypeAlias());
			}
			return map;
		}else{
			return null;
		}
	}
}
