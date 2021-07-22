/*
 * $Id: FormDataLoaderImpl.java 6455 2015-07-01 09:23:26Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemDescriptor;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObjectDescriptor;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.bean.VersionableCreationTimeComparator;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.dao.hibernate3.SmsgHibernateDao;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.FormDataBuilder;
import cn.redflagsoft.base.service.FormDataLoader;
import cn.redflagsoft.base.service.ObjectsService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskDefProvider;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.WorkDefProvider;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.util.BeanUtils;

import com.google.common.collect.Maps;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FormDataLoaderImpl implements FormDataLoader {
	private static final Log log = LogFactory.getLog(FormDataLoaderImpl.class);
	private static final boolean IS_DEBUG_ENABLED = Boolean.getBoolean("BeanUtils.debugEnabled") && log.isDebugEnabled();
	
	private WorkService workService;
	private TaskService taskService;
	private ClerkService clerkService;
	private ObjectsService objectsService;
	private RiskService riskService;
	private WorkDefProvider workDefProvider;
	private TaskDefProvider taskDefProvider;
	
	
	public WorkDefProvider getWorkDefProvider() {
		return workDefProvider;
	}

	public void setWorkDefProvider(WorkDefProvider workDefProvider) {
		this.workDefProvider = workDefProvider;
	}

	public TaskDefProvider getTaskDefProvider() {
		return taskDefProvider;
	}

	public void setTaskDefProvider(TaskDefProvider taskDefProvider) {
		this.taskDefProvider = taskDefProvider;
	}

	/**
	 * @return the objectsService
	 */
	public ObjectsService getObjectsService() {
		return objectsService;
	}

	/**
	 * @param objectsService the objectsService to set
	 */
	@Required
	public void setObjectsService(ObjectsService objectsService) {
		this.objectsService = objectsService;
	}

	/**
	 * @return the clerkService
	 */
	public ClerkService getClerkService() {
		return clerkService;
	}

	/**
	 * @param clerkService the clerkService to set
	 */
	@Required
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	/**
	 * @return the workService
	 */
	public WorkService getWorkService() {
		return workService;
	}

	/**
	 * @param workService the workService to set
	 */
	@Required
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	/**
	 * @return the taskService
	 */
	public TaskService getTaskService() {
		return taskService;
	}

	/**
	 * @param taskService the taskService to set
	 */
	@Required
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	

	/**
	 * @param riskService the riskService to set
	 */
	@Required
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	
	/**
	 * @return the riskService
	 */
	public RiskService getRiskService() {
		return riskService;
	}
	
	public FormDataBuilder createBuilder(Object baseData, long objectId,
			int objectType, int taskType) {
		return createBuilder(baseData, objectId, objectType, new int[]{taskType});
	}

	public FormDataBuilder createBuilder(Object baseData, long objectId,
			int objectType, int[] taskTypes) {
		if(taskTypes == null || taskTypes.length == 0){
			throw new IllegalArgumentException("应至少执行一个taskType的值");
		}
		
		FormDataBuilder builder = createBuilder().append(baseData);
		RFSEntityObject object = null;
		if(ObjectTypes.isRFSObject(objectType)
				|| cn.redflagsoft.base.ObjectTypes.isRFSObject(objectType)){
			
			object = new RFSObjectDescriptor(objectType, objectId, "");
			log.debug("Build RFSObjectDescriptor: " + objectId + "(type=" + objectType + ")");
		}else{
			object = new RFSItemDescriptor(objectType, objectId);
			log.debug("Build RFSItemDescriptor: " + objectId + "(type=" + objectType + ")");
		}
//		builder.appendTask(object, taskType);
		
		for(int taskType: taskTypes){
			builder.appendTask(object, taskType);
		}
		
		return builder;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.milestone.pss2.service.FormDataLoader#buildFormData(java.lang.Object, long, short, short)
	 */
	public Map<String, Object> buildFormData(Object baseData, long objectId, int objectType, int taskType) {
		return buildFormData(baseData, objectId, objectType, new int[]{taskType});
	}
	
	public Map<String, Object> buildFormData(Object baseData, long objectId, int objectType, int[] taskTypes) {
		return createBuilder(baseData, objectId, objectType, taskTypes).toMap();
	}

//	/**
//	 * @param map
//	 * @param objectId
//	 * @param objectType
//	 */
//	private void populateTaskRiskInfo(Map<String, Object> map, long objectId, int objectType) {
//		List<Task> tasks = taskService.findTaskByObjectId(objectId);
//		Set<Short> taskTypes = new HashSet<Short>();
//		for (Task task : tasks) {
//			int taskType = task.getType();
//			taskTypes.add(taskType);
//			// if (isLifeStageTask(taskType)) {
//			map.put("__" + taskType + "_taskStatus", task.getStatus());
//			map.put("__" + taskType + "_taskOpinion", task.getOpinion());
//			// }
//		}
//
//		// risks
//		List<Risk> risks = getRiskService().findTaskRiskByObjectIdAndTaskType(
//				objectId, objectType,
//				taskTypes.toArray(new Short[taskTypes.size()]));
//		for (Risk risk : risks) {
//			int taskType = risk.getRefType();
//			map.put("__" + taskType + "_riskGrade", risk.getGrade());
//
//			if (risk.getScaleValue() != null) {
//				map.put("__" + taskType + "_riskScaleValue",
//						risk.getScaleValue());
//
//				Object remainValue = (risk.getValue() != null) ? risk
//						.getScaleValue().subtract(risk.getValue()) : risk
//						.getScaleValue();
//				map.put("__" + taskType + "_riskRemainValue", remainValue);
//			}
//		}
//		
//		//FIXME
//		///////////////////////////////////////////////////////////
//		// 注意，以下代码测试用，正式使用请移除
//		////////////////////////////////////////////////////////////
//		System.out.println("////////////////////////////////////////////////////////////");
//		System.out.println("// 注意，组装假的task的risk数据，正式运行请在代码中移除");
//		//start here
//		if("true".equalsIgnoreCase(System.getProperty("dev.taskrisk"))){
//			System.out.println("// 当前环境启动了组装测试数据的功能 dev.taskrisk=true");
//			System.out.println("////////////////////////////////////////////////////////////");
//			
//			for(Long taskType : taskTypes){
//				map.put("__" + taskType + "_riskGrade", randomValue(1,2,3,5));
//				map.put("__" + taskType + "_riskScaleValue", randomValue(190,180,200,230,340,690));
//				Object remainValue = randomValue(32,43,-12,9,-300,-4,1,234,43);
//				map.put("__" + taskType + "_riskRemainValue", remainValue);
//				
//				String key1 = "__" + taskType + "_riskGrade";
//				String key2 = "__" + taskType + "_riskScaleValue";
//				String key3 = "__" + taskType + "_riskRemainValue";
//				System.out.println(key1 + " --> " + map.get(key1));
//				System.out.println(key2 + " --> " + map.get(key2));
//				System.out.println(key3 + " --> " + map.get(key3));
//			}
//			
//			map.put("w_sampleWorkType_title", "建议书批复" + randomValue(1,2,3,5));
//			map.put("w_sampleWorkType_status", randomValue(1,2,9));
//			map.put("w_sampleWorkType_clerkName", "人口与计生局刘小英");
//			map.put("w_sampleWorkType_operateTime", "2011-05-" + randomValue(1,2,3,5));
//		}else{
//			System.out.println("// 当前环境没有启动组装测试数据的功能，设置系统属性 dev.taskrisk=true 可以启用");
//		}
//		//end
//		System.out.println("////////////////////////////////////////////////////////////");
//	}
//	
//	private <K> K randomValue(K... array){
//		SecureRandom random = new SecureRandom();
//		int i = random.nextInt(array.length);
//		return array[i];
//	}

//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.milestone.pss2.service.FormDataLoader#saveObjects(long, cn.redflagsoft.base.bean.Task, short, cn.redflagsoft.base.bean.Work, short)
//	 */
//	public void saveObjects(long objectId, Task task, short objectsTypeOfTask, Work work, short objectsTypeOfWork) {
//		objectsService.createObjects(objectId, task.getSn(), objectsTypeOfTask, "合同支付与任务之间的关系");
//		objectsService.createObjects(objectId, work.getSn(), objectsTypeOfWork, "合同支付与工作之间的关系");
//	}
	

//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.milestone.pss2.service.FormDataLoader#buildObjectsData(java.lang.Object, long, short, short...)
//	 */
//	public Map<String, Object> buildObjectsData(Object baseData, long objectId,	short objectsTypeOfTask, short... objectsTypeOfWorks) {
//		//thh
//		FormDataBuilder builder = createBuilder().append(baseData);
//		builder.appendTask(objectId, objectsTypeOfTask);
//		for(short objectsTypeOfWork: objectsTypeOfWorks){
//			builder.appendWork(objectId, objectsTypeOfWork);
//		}
//		return builder.toMap();
//	}
	
	
	
	public FormDataBuilder createBuilder(){
		return new FormDataBuilderImpl();
	}
	
	
	public static Map<String, ?> getPropertiesMap(Object obj, String[] includeFields, String[] excludeFields){
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<String> includes = null;
		if(includeFields != null && includeFields.length > 0){
			includes = Arrays.asList(includeFields);
		}
		
		List<String> excludes = null;
		if(excludeFields != null && excludeFields.length > 0){
			excludes = Arrays.asList(excludeFields);
		}
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor desc : descriptors) {
				//System.out.println(desc.getReadMethod() + " >> " + desc.getName());
//				System.out.println(desc.getName());
//				System.out.println(desc.getReadMethod().invoke(obj, (Object[])null));
				String name = desc.getName();
				if(includes != null && !includes.contains(name)){
					continue;
				}

				if(excludes != null && excludes.contains(name)){
					continue;
				}

				Method method = desc.getReadMethod();
				if(method != null){
					Object value = method.invoke(obj);
					if(IS_DEBUG_ENABLED){
						log.debug("属性 " + name + " = " + value);
					}
					if(value != null){
						if(value instanceof Date){
							value = AppsGlobals.formatDate((Date) value);
						}
						map.put(name, value);
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return map;
	}
	
	
	public class FormDataBuilderImpl implements FormDataBuilder{
		private Map<String, Object> map = Maps.newHashMap();
		
		private <T> String buildTaskFieldName(T taskType, String field){
			return "t_" + taskType + "_" + field;
		}
		private <T> String buildWorkFieldName(T workType, String field){
			return "w_" + workType + "_" + field;
		}

		public Map<String, Object> toMap() {
			return map;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#append(java.lang.Object)
		 */
		public FormDataBuilder append(Object object) {
			if (object != null) {
				if(object instanceof Map){
					map.putAll((Map)object);
				}else{
					Map<String, ?> map2 = BeanUtils.getPropertiesMap(object);
					map.putAll(map2);
				}
			}
			return this;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendExcludes(java.lang.Object, java.lang.String[])
		 */
		public FormDataBuilder appendExcludes(Object object, String... excludeFields) {
			if(object != null){
				Map<String, ?> map2 = getPropertiesMap(object, null, excludeFields);
				map.putAll(map2);
			}
			return this;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendIncludes(java.lang.Object, java.lang.String[])
		 */
		public FormDataBuilder appendIncludes(Object object, String... includeFields) {
			if(object != null){
				Map<String, ?> map2 = getPropertiesMap(object, includeFields, null);
				map.putAll(map2);
			}
			return this;
		}
		
		public FormDataBuilder appendTask(RFSEntityObject object, int taskType){
			//查询的集合应该按照时间倒序
			List<Task> tasks = null;
			if(object instanceof RFSObjectable){
				tasks = getTaskService().findTasks((RFSObjectable) object, taskType, null, null);
				log.debug("Find tasks by (RFSObjectable, taskType, null, null)");
			}else if(object instanceof RFSItemable){
				tasks = getTaskService().findTasks((RFSItemable) object, taskType, null, null);
				log.debug("Find tasks by (RFSItemable, taskType, null, null)");
			}
			
			Task task = null;
			//取第一条，即取集合中创建时间最大的一条
			if(tasks != null && !tasks.isEmpty()){
				task = tasks.iterator().next();
				
				if(log.isDebugEnabled()){
					for(Task t: tasks){
						String s = String.format("Task(sn=%s, type=%s, name=%s, status=%s)", t.getSn(), t.getType(), t.getName(), t.getStatus());
						log.debug(s);
					}
					log.debug("取第一条：" + task);
				}
			}
			
			if(task != null){
				//将Task中相关信息提取到Map中
				appendTask(task);
			}
			
			//如果查询到task应该查询下级task的信息，如果没有查询到task，至少应该将task的名字取出来
			TaskDef def = getTaskDefProvider().getTaskDef(taskType);
			if(def != null){
				//task不为空时，在#appendTask(Task)方法里已经调用过以下逻辑
				if(task == null){
					map.put(buildTaskFieldName(def.getTaskType(), "taskName"), def.getName());
				}
				map.put(buildTaskFieldName(def.getTypeAlias(), "taskName"), def.getName());
				map.put(buildTaskFieldName(def.getTypeAlias(), "taskType"), def.getTaskType());
				map.put(buildTaskFieldName(def.getTypeAlias(), "taskTypeHasGuide"), def.getHasGuide());
			
				Set<Integer> taskTypes = def.getSubTaskTypes();
				for(int type: taskTypes){
					appendTask(object, type);
				}
				Set<Integer> workTypes = def.getWorkTypes();
				for(int type: workTypes){
					WorkDef workDef = getWorkDefProvider().getWorkDef(type);
					if(workDef != null){
						String workNameField = buildWorkFieldName(type, "workName");
						if(!map.containsKey(workNameField)){
							map.put(workNameField, workDef.getName());
						}
						map.put(buildWorkFieldName(type, "workDutyer"), workDef.getDutyer());
						
						workNameField = buildWorkFieldName(workDef.getTypeAlias(), "workName");
						if(!map.containsKey(workNameField)){
							map.put(workNameField, workDef.getName());
						}
						map.put(buildWorkFieldName(workDef.getTypeAlias(), "workDutyer"), workDef.getDutyer());
					}
				}
			}
			return this;
		}
		
		/* (non-Javadoc)
		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendTask(cn.redflagsoft.base.bean.Task)
		 */
		public FormDataBuilder appendTask(Task task) {
			if (task != null) {
				int type = task.getType();
				Clerk clerk = null;
				if(task.getClerkID() != null){
					clerk = getClerkService().getClerk(task.getClerkID());
				}
				Clerk dutyer = null;
				if(task.getDutyerID() != null){
					dutyer = getClerkService().getClerk(task.getDutyerID());
				}
				
				// append task
				map.put(buildTaskFieldName(type, "taskStatus"), task.getStatus());
				map.put(buildTaskFieldName(type, "taskName"), task.getName());
				map.put(buildTaskFieldName(type, "taskSn") , task.getSn());
				map.put(buildTaskFieldName(type, "taskOpinion") , task.getOpinion());
				map.put(buildTaskFieldName(type, "taskClerkId"), task.getClerkID());
				map.put(buildTaskFieldName(type, "taskClerkName"), task.getClerkName());
				if(clerk != null){
					map.put(buildTaskFieldName(type, "taskEntityName"), clerk.getEntityName());
				}
				if(dutyer != null){
					map.put(buildTaskFieldName(type, "taskDutyerName"), dutyer.getName());
				}
				map.put(buildTaskFieldName(type, "taskDutyEntityName"), task.getDutyEntityName());
				
				
				TaskDef taskDef = taskDefProvider.getTaskDef(type);
				String alias = null;
				if(taskDef != null && taskDef.getTypeAlias() != null){
					alias = taskDef.getTypeAlias();
					map.put(buildTaskFieldName(alias, "taskStatus"), task.getStatus());
					map.put(buildTaskFieldName(alias, "taskName"), task.getName());
					map.put(buildTaskFieldName(alias, "taskSn") , task.getSn());
					map.put(buildTaskFieldName(alias, "taskOpinion") , task.getOpinion());
					map.put(buildTaskFieldName(alias, "taskClerkId"), task.getClerkID());
					map.put(buildTaskFieldName(alias, "taskClerkName"), task.getClerkName());
					if(clerk != null){
						map.put(buildTaskFieldName(alias, "taskEntityName"), clerk.getEntityName());
					}
					
					if(dutyer != null){
						map.put(buildTaskFieldName(alias, "taskDutyerName"), dutyer.getName());
					}
					map.put(buildTaskFieldName(alias, "taskDutyEntityName"), task.getDutyEntityName());
				}
				// end append task
				
				
				//start risks//new
				Risk risk = null;
				List<RiskEntry> entries = task.getRiskEntries();
				if(entries != null) {
					for(RiskEntry re : entries) {
						Risk r = riskService.getRiskById(re.getRiskID());
						if(r != null && r.getRuleType() == RiskRule.RULE_TYPE_监察){
							if(risk != null){
								log.warn(String.format("该 task (%s, %s) 已经存在一个监察 %s, 现在发现第二个 %s", task.getId(), task.getName(), risk.getId(), r.getId()));
							}else{
								risk = r;
							}
						}
					}
				}

				
//				// start risks
//				List<Risk> risks = getRiskService().findTaskRiskByObjectIdAndTaskType(task.getRefObjectId(), task.getRefObjectType(), task.getType());
				boolean querySmsgCountForRisk = AppsGlobals.getProperty("formDataLoader.querySmsgCountForRisk", true);
//				for (Risk risk : risks) {
//					//int taskType = risk.getRefType();
//					if(risk.getRuleType() != RiskRule.RULE_TYPE_监察){
//						continue;
//					}
				if(risk != null){
					map.put(buildTaskFieldName(type, "riskId") , risk.getId());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskId") , risk.getId());
					}
					
					map.put(buildTaskFieldName(type, "riskDutyerID") , risk.getDutyerID());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskDutyerID") , risk.getDutyerID());
					}
					
					map.put(buildTaskFieldName(type, "riskDutyerName") , risk.getDutyerName());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskDutyerName") , risk.getDutyerName());
					}
					
					map.put(buildTaskFieldName(type, "riskDutyOrgName") , risk.getDutyerOrgName());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskDutyOrgName") , risk.getDutyerOrgName());
					}
					
					map.put(buildTaskFieldName(type, "riskGrade") , risk.getGrade());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskGrade") , risk.getGrade());
					}
					
					map.put(buildTaskFieldName(type, "riskValueUnitName"), risk.getValueUnitName());
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskValueUnitName"), risk.getValueUnitName());
					}
					
					if (risk.getScaleValue() != null) {
						map.put(buildTaskFieldName(type, "riskScaleValue") , risk.getScaleValue());
						if(alias != null){
							map.put(buildTaskFieldName(alias, "riskScaleValue") , risk.getScaleValue());
						}

						Object remainValue = (risk.getValue() != null) ? 
								risk.getScaleValue().subtract(risk.getValue()) 
								: risk.getScaleValue();
						map.put(buildTaskFieldName(type, "riskRemainValue"), remainValue);
						if(alias != null){
							map.put(buildTaskFieldName(alias, "riskRemainValue") , remainValue);
						}
					}
					
					int smsgCountForRisk = 0;
					if(querySmsgCountForRisk){
						smsgCountForRisk = getSmsgCountForRisk(risk);
					}
					map.put(buildTaskFieldName(type, "riskSmsgCount"), smsgCountForRisk);
					if(alias != null){
						map.put(buildTaskFieldName(alias, "riskSmsgCount"), smsgCountForRisk);
					}
				}
				/// end risks
				
				
				///append all works
				List<Work> works = getWorkService().findWorkByTaskSN(task.getSn());
				// 排序及其他处理
				// 按时间排序再放置在MAP中，这样相同type的work就会由最新的覆盖
				List<Work> list = new ArrayList<Work>(works);
				Collections.sort(list, new VersionableCreationTimeComparator());
				for (Work w : list) {
					appendWork(w);
				}
				/// end append all works

				
//				//append sub tasks
//				if(taskDef != null){
//					Set<Short> subTaskTypes = taskDef.getSubTaskTypes();
//					if(subTaskTypes != null){
//						for(int taskType: subTaskTypes){
//							appendObjectLastTaskAndWorks(task.getRefObjectId(), task.getObjectType(), taskType);
//						}
//					}
//				}
//				//end append sub tasks
			}
			return this;
		}


//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendTask(java.lang.Long)
//		 */
//		public FormDataBuilder appendTask(Long taskSN) {
//			if(taskSN != null){
//				Task task = getTaskService().getTask(taskSN);
//				return appendTask(task);
//			}
//			return this;
//		}

//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendTask(long, short)
//		 */
//		public FormDataBuilder appendTask(long objectId, short objectsTypeOfTask) {
//			Long taskSN = getObjectsService().getRelatedObjectID(objectId, objectsTypeOfTask);
//			return appendTask(taskSN);
//		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendWork(cn.redflagsoft.base.bean.Work)
		 */
		public FormDataBuilder appendWork(Work work) {
			if (work != null) {
				int type = work.getType();
				Clerk clerk = null;
				if (work.getClerkID() != null) {
					clerk = getClerkService().getClerk(work.getClerkID());
				}
				//thh
				map.put(buildWorkFieldName(type, "workSn"), work.getSn());
				map.put(buildWorkFieldName(type, "workName"), work.getName());
				map.put(buildWorkFieldName(type, "workStatus"), work.getStatus());
				map.put(buildWorkFieldName(type, "workClerkId"), work.getClerkID());
				map.put(buildWorkFieldName(type, "workClerkName"), work.getClerkName());
				
				if(work.getString4() != null){
					map.put(buildWorkFieldName(type, "receiptFileId"), work.getString4());
				}
				
				if (work.getBeginTime() != null) {
					map.put(buildWorkFieldName(type, "workBeginTime"),
							AppsGlobals.formatDate(work.getBeginTime()));
				}
				if(work.getCreationTime() != null){
					map.put(buildWorkFieldName(type, "workCreationTime"), AppsGlobals.formatDate(work.getCreationTime()));
				}
	
				if (clerk != null) {
					map.put(buildWorkFieldName(type, "workEntityName"), clerk.getEntityName());
					map.put(buildWorkFieldName(type, "workEntityAndClerkName"), clerk.getEntityName() + work.getClerkName());
				}
				
				WorkDef workDef = workDefProvider.getWorkDef(type);
				if(workDef != null && workDef.getTypeAlias() != null){
					String alias = workDef.getTypeAlias();
					map.put(buildWorkFieldName(alias, "workSn"), work.getSn());
					map.put(buildWorkFieldName(alias, "workName"), work.getName());
					map.put(buildWorkFieldName(alias, "workStatus"), work.getStatus());
					map.put(buildWorkFieldName(alias, "workClerkId"), work.getClerkID());
					map.put(buildWorkFieldName(alias, "workClerkName"), work.getClerkName());
					
					if(work.getString4() != null){
						map.put(buildWorkFieldName(alias, "receiptFileId"), work.getString4());
					}
					
					if (work.getBeginTime() != null) {
						map.put(buildWorkFieldName(alias, "workBeginTime"), AppsGlobals.formatDate(work.getBeginTime()));
					}
					if(work.getCreationTime() != null){
						map.put(buildWorkFieldName(alias, "workCreationTime"), AppsGlobals.formatDate(work.getCreationTime()));
					}
		
					if (clerk != null) {
						map.put(buildWorkFieldName(alias, "workEntityName"), clerk.getEntityName());
						map.put(buildWorkFieldName(alias, "workEntityAndClerkName"), clerk.getEntityName() + work.getClerkName());
					}
				}
			}
			return this;
		}
//
//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendWork(java.lang.Long)
//		 */
//		public FormDataBuilder appendWork(Long workSN) {
//			if(workSN != null){
//				Work work = getWorkService().getWork(workSN);
//				return appendWork(work);
//			}
//			return this;
//		}

//		/* (non-Javadoc)
//		 * @see cn.redflagsoft.milestone.pss2.service.FormDataBuilder#appendWork(long, short)
//		 */
//		public FormDataBuilder appendWork(long objectId, short objectsTypeOfWork) {
//			Long workSN = getObjectsService().getRelatedObjectID(objectId, objectsTypeOfWork);
//			return appendWork(workSN);
//		}


	
//		public FormDataBuilder appendObjectLastTaskAndWorks(long objectId, int objectType, int taskType) {
//			
//			//基本数据对象是否存在不能决定task是否存在，所以没有放在上面的if中
//			Task lastTask = getTaskService().getLastTask(objectId, objectType, taskType);
//			appendTask(lastTask);
//			
////			//查询建议书编制、申报、批复的work信息	
////			if(lastTask != null){
////				List<Work> works = getWorkService().findWorkByTaskSN(lastTask.getSn());
////				//排序及其他处理
////				//按时间排序再放置在MAP中，这样相同type的work就会由最新的覆盖
////				List<Work> list = new ArrayList<Work>(works);
////				Collections.sort(list, new VersionableCreationTimeComparator());
////				for(Work w:list){
////					appendWork(w);
////				}
////			}
//			return this;
//		}
	}
	
	
	/**
	 * @param risk
	 * @return
	 */
	private int getSmsgCountForRisk(final Risk risk) {
		final String hql = "select count(*) from Smsg s, Caution c where s.refObjectType=? and s.refObjectId=c.id and s.sendStatus=? and c.riskId=?";
		SmsgHibernateDao smsgDao = Application.getContext().get("smsgDao", SmsgHibernateDao.class);
		return ((Number)smsgDao.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createQuery(hql)
				.setInteger(0, Caution.OBJECT_TYPE)
				.setByte(1, Smsg.SEND_STATUS_全部发送)
				.setLong(2, risk.getId())
				.uniqueResult();
			}
		})).intValue();
	}
}
