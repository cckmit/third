/*
 * $Id: TaskDefinitionServiceImpl.java 6401 2014-05-08 07:19:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.dao.TaskDefinitionDao;
import cn.redflagsoft.base.dao.WorkDefinitionDao;
import cn.redflagsoft.base.service.TaskDefProvider;
import cn.redflagsoft.base.service.TaskDefinitionService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskDefinitionServiceImpl implements TaskDefinitionService,TaskDefProvider {

	private TaskDefinitionDao taskDefinitionDao;
	private WorkDefinitionDao workDefinitionDao;
	
	/**
	 * @return the workDefinitionDao
	 */
	public WorkDefinitionDao getWorkDefinitionDao() {
		return workDefinitionDao;
	}

	/**
	 * @param workDefinitionDao the workDefinitionDao to set
	 */
	public void setWorkDefinitionDao(WorkDefinitionDao workDefinitionDao) {
		this.workDefinitionDao = workDefinitionDao;
	}

	public TaskDefinitionDao getTaskDefinitionDao() {
		return taskDefinitionDao;
	}

	public void setTaskDefinitionDao(TaskDefinitionDao taskDefinitionDao) {
		this.taskDefinitionDao = taskDefinitionDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskDefinitionService#getTaskDefinition(short)
	 */
	public TaskDefinition getTaskDefinition(int taskType) {
		return taskDefinitionDao.get(taskType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskDefinitionService#remove(short)
	 */
	public void remove(int taskType) {
		taskDefinitionDao.remove(taskType);

	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskDefinitionService#updateTaskDefinition(cn.redflagsoft.base.bean.TaskDefinition)
	 */
	public TaskDefinition updateTaskDefinition(TaskDefinition taskDefinition) {
		return taskDefinitionDao.update(taskDefinition);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskDefProvider#getTaskDef(short)
	 */
	public TaskDef getTaskDef(int taskType) {
		if(taskType == TaskDef.INVALID_TYPE){
			return null;
		}
		TaskDefinition definition = getTaskDefinition(taskType);
		if (definition == null) {
			return null;
		}
		
		ResultFilter filter1 = ResultFilter.createEmptyResultFilter();
		filter1.setCriterion(Restrictions.eq("parentTaskType", taskType));
		List<Integer> subTaskTypes = taskDefinitionDao.findIds(filter1);
		
		ResultFilter filter2 = ResultFilter.createEmptyResultFilter();
		filter2.setCriterion(Restrictions.eq("taskType", taskType));
		List<Integer> workTypes = workDefinitionDao.findIds(filter2);
		
		return new TaskDefImpl(definition, buildSet(subTaskTypes), buildSet(workTypes));
	}
	
	Set<Integer> buildSet(List<Integer> list){
		if(list == null || list.isEmpty()){
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet( new HashSet<Integer>(list));
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskDefinitionService#findTaskDefinitions(org.opoo.ndao.support.ResultFilter)
	 */
	public List<TaskDefinition> findTaskDefinitions(ResultFilter filter) {
		return taskDefinitionDao.find(filter);
	}
	
	private static class TaskDefImpl implements TaskDef, Serializable{
		private static final long serialVersionUID = 7349125077849512534L;
//		private final TaskDefinition taskDefinition;
		private final Set<Integer> subTaskTypes;
		private final Set<Integer> workTypes;
		private final String name;
		private final Integer parentTaskType;
		private final int visibility;
		private final String typeAlias;
		private final int taskType;
		private final int dutyRef;
		private final String lifeStageFieldName;
		private final String bizSummaryTemplate;
		private final int calculateFlag;		//计算时限用的标识
		private final int dutyerType;
		private final int hasGuide;
		
		public TaskDefImpl(TaskDefinition taskDefinition, Set<Integer> subTaskTypes, Set<Integer> workTypes){
//			this.taskDefinition = taskDefinition;
			this.subTaskTypes = subTaskTypes;
			this.workTypes = workTypes;
			this.name = taskDefinition.getName();
			this.parentTaskType = taskDefinition.getParentTaskType();
			this.visibility = taskDefinition.getVisibility();
			this.taskType = taskDefinition.getTaskType();
			this.typeAlias = taskDefinition.getTypeAlias();
			this.dutyRef = taskDefinition.getDutyRef();
			this.lifeStageFieldName = taskDefinition.getLifeStageFieldName();
			this.bizSummaryTemplate = taskDefinition.getBizSummaryTemplate();
			this.calculateFlag = taskDefinition.getCalculateFlag();
			this.dutyerType = taskDefinition.getDutyerType();
			this.hasGuide = taskDefinition.getHasGuide();
		}
		

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getTaskType()
		 */
		public int getTaskType() {
			return taskType;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getName()
		 */
		public String getName() {
			return name;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getVisibility()
		 */
		public int getVisibility() {
			return visibility;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getTypeAlias()
		 */
		public String getTypeAlias() {
			return typeAlias;
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getParentTaskType()
		 */
		public Integer getParentTaskType() {
			return parentTaskType;
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getSubTaskTypes()
		 */
		public Set<Integer> getSubTaskTypes() {
			return subTaskTypes;
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getWorkTypes()
		 */
		public Set<Integer> getWorkTypes() {
			return workTypes;
		}

		public int getDutyRef() {
			return dutyRef;
		}

		public String getLifeStageFieldName() {
			return lifeStageFieldName;
		}


		public String getBizSummaryTemplate() {
			return bizSummaryTemplate;
		}


		/**
		 * @return the calculateFlag
		 */
		public int getCalculateFlag() {
			return calculateFlag;
		}
		
		
		public int getDutyerType(){
			return dutyerType;
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.BizDef#getId()
		 */
		public Integer getId() {
			return getTaskType();
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.BizDef#getObjectType()
		 */
		public int getObjectType() {
			return ObjectTypes.TASK;
		}


		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.bean.TaskDef#getHasGuide()
		 */
		public int getHasGuide() {
			return hasGuide;
		}


		
//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.base.bean.TaskDef#getParent()
//		 */
//		@JSON(serialize=false)
//		public TaskDef getParent() {
//			if(parent == null && taskDefinition.getParentTaskType() != null
//					&& taskDefinition.getParentTaskType().shortValue() != TaskDef.INVALID_TYPE){
//				TaskDefProvider provider = getTaskDefProvider();
//				if(provider != null){
//					parent = provider.getTaskDef(taskDefinition.getParentTaskType());
//				}
//			}
//			return parent;
//		}
//
//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.base.bean.TaskDef#getSubTaskDefs()
//		 */
//		@JSON(serialize=false)
//		public List<TaskDef> getSubTaskDefs() {
//			if(subTaskDefIds == null){
//				TaskDefinitionDao dao = getTaskDefinitionDao();
//				if(dao != null){
//					ResultFilter filter = ResultFilter.createEmptyResultFilter();
//					filter.setCriterion(Restrictions.eq("parentTaskType", getTaskType()));
//					subTaskDefIds = dao.findIds(filter);
//				}
//			}
//			if(subTaskDefIds == null){
//				return null;
//			}
//			if(subTaskDefIds.size() == 0){
//				return Collections.emptyList();
//			}
//			
//			final TaskDefProvider provider = getTaskDefProvider();
//			if(provider == null){
//				System.out.println("*** [WANR] 找不到 taskDefProvider.");
//				return Collections.emptyList();
//			}
//			
//			return new GenericListProxy<Short, TaskDef>(subTaskDefIds, new GenericProxyFactory<Short, TaskDef>() {
//				public TaskDef createProxy(Short arg0) {
//					return provider.getTaskDef(arg0);
//				}
//			});
//		}
	}
}
