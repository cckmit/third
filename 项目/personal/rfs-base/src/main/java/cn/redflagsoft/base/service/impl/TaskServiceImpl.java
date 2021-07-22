/*
 * $Id: TaskServiceImpl.java 6410 2014-05-29 03:47:47Z lcj $
 * TaskServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.hibernate3.CachedHibernateDao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.ClerkNotFoundException;
import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.EntityObjectToTask;
import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.bean.Job;
import cn.redflagsoft.base.bean.Matter;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.ObjectTask;
import cn.redflagsoft.base.bean.Objects;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.RiskValue;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.bean.TaskMatter;
import cn.redflagsoft.base.bean.Thread;
import cn.redflagsoft.base.bean.TimeLimit;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.EntityObjectToTaskDao;
import cn.redflagsoft.base.dao.EntityObjectToWorkDao;
import cn.redflagsoft.base.dao.LifeStageDao;
import cn.redflagsoft.base.dao.MatterAffairDao;
import cn.redflagsoft.base.dao.MatterDao;
import cn.redflagsoft.base.dao.ObjectTaskDao;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.dao.TaskMatterDao;
import cn.redflagsoft.base.dao.TimeLimitDao;
import cn.redflagsoft.base.event.RiskLogEvent;
import cn.redflagsoft.base.event2.TaskEvent;
import cn.redflagsoft.base.service.BizRouteService;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.BizTrackService;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.ClueService;
import cn.redflagsoft.base.service.DutyersInfoManager;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.GlossaryService;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.RiskMonitorableService;
import cn.redflagsoft.base.service.RiskRuleService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.RiskValueProvider;
import cn.redflagsoft.base.service.TaskDefProvider;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.TemplateProcessor;
import cn.redflagsoft.base.service.TemplateProcessorManager;
import cn.redflagsoft.base.service.ThreadService;
import cn.redflagsoft.base.service.WorkDefProvider;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.util.BatchHelper;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.vo.BizVO;
import cn.redflagsoft.base.vo.MonitorVO;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Task��������
 * 
 * 
 * @author mwx
 * @author lcj
 */
public class TaskServiceImpl implements TaskService, InitializingBean, EventDispatcherAware, RiskValueProvider {
	/**
	 * ����ʱ���ԣ�
	 * ��calculateAllRunningTasksTimeUsed.optimized=true����
	 * 
	 * <pre>
	 * ˵�����ڼ�����������״̬��task��timeUsedʱ�����ʹ���Ż��㷨������ϴ�����ʱ��ͱ�������ʱ����ͬһ�죬
	 * �������м��㡣��ʹ���Ż�ʱ��ÿ�ζ����㡣
	 * </pre>
	 */
	public static final String CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_OPTIMIZED_KEY = "calculateAllRunningTasksTimeUsed.optimized";
	public static final String CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_INCLUDE_HANG_TASKS_KEY = "calculateAllRunningTasksTimeUsed.includeHangTasks";
	public static final String TEMPLATE_PROCESSOR_TYPE_BIZ_SUMMARY = "taskBizSummary";
	public static final String CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_THREADS_KEY = "calculateAllRunningTasksTimeUsed.nThreads";
	
	
	private static final Log log = LogFactory.getLog(TaskServiceImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	//private Clerk clerk;
	
	public static final byte TASK_CATEGORY = 3;
	public static final byte CATEGORY_TASK = 2;
	
	private TaskDao taskDao;
	private TimeLimitDao timeLimitDao;
	private ObjectTaskDao objectTaskDao;
	private MatterAffairDao matterAffairDao;
	private TaskMatterDao taskMatterDao;
	private MatterDao matterDao;
	private BizRouteService bizRouteService;
	private BizTrackService bizTrackService;
	private ThreadService threadService;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;
	private CodeGeneratorProvider codeGeneratorProvider;
	private IdGenerator<Long> idGenerator;
	private RiskService riskService;
	private RiskMonitorableService riskMonitorableService;
	private ClueService clueService;
	private GlossaryService glossaryService;
	private ObjectService<RFSObject> objectService;
	private WorkService workService;
	private ClerkService clerkService;
	private OrgDao orgDao;
	private EventDispatcher dispatcher;
	private ObjectsDao objectsDao;
	private TaskDefProvider taskDefProvider;
	private WorkDefProvider workDefProvider;
	private EntityObjectToWorkDao entityObjectToWorkDao;
	private EntityObjectToTaskDao entityObjectToTaskDao;
	private Date lastCalculateTime;
	private EntityObjectLoader entityObjectLoader;
	private TemplateProcessorManager templateProcessorManager;
	private DutyersInfoManager dutyersInfoManager;
	private RiskRuleService riskRuleService;
	private LifeStageDao lifeStageDao;

	/**
	 * @param templateProcessorManager the templateProcessorManager to set
	 */
	public void setTemplateProcessorManager(
			TemplateProcessorManager templateProcessorManager) {
		this.templateProcessorManager = templateProcessorManager;
	}
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}
	
	public LifeStageDao getLifeStageDao() {
		return lifeStageDao;
	}
	public void setLifeStageDao(LifeStageDao lifeStageDao) {
		this.lifeStageDao = lifeStageDao;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}
	public OrgDao getOrgDao() {
		return orgDao;
	}
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

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
	public void setGlossaryService(GlossaryService glossaryService) {
		this.glossaryService = glossaryService;
	}
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}
	public void setBizTrackNodeInstanceService(
			BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}
	public void setBizRouteService(BizRouteService bizRouteService) {
		this.bizRouteService = bizRouteService;
	}
	public void setBizTrackService(BizTrackService bizTrackService) {
		this.bizTrackService = bizTrackService;
	}
	public void setMatterAffairDao(MatterAffairDao matterAffairDao) {
		this.matterAffairDao = matterAffairDao;
	}
	public void setObjectTaskDao(ObjectTaskDao objectTaskDao) {
		this.objectTaskDao = objectTaskDao;
	}
	public void setTimeLimitDao(TimeLimitDao timeLimitDao) {
		this.timeLimitDao = timeLimitDao;
	}
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	public void setClueService(ClueService clueService) {
		this.clueService = clueService;
	}
	public void setRiskMonitorableService(
			RiskMonitorableService riskMonitorableService) {
		this.riskMonitorableService = riskMonitorableService;
	}
	public void setObjectService(ObjectService<RFSObject> objectService) {
		this.objectService = objectService;
	}
	
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}
	
	@SuppressWarnings("unchecked")
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
		idGenerator = ((IdGeneratable<Long>) taskDao).getIdGenerator();
	}
	
	public void setMatterDao(MatterDao matterDao) {
		this.matterDao = matterDao;
	}
	
	public void setTaskMatterDao(TaskMatterDao taskMatterDao) {
		this.taskMatterDao = taskMatterDao;
	}
	
	/**
	 * @param entityObjectToWorkDao the entityObjectToWorkDao to set
	 */
	public void setEntityObjectToWorkDao(EntityObjectToWorkDao entityObjectToWorkDao) {
		this.entityObjectToWorkDao = entityObjectToWorkDao;
	}
	/**
	 * @param entityObjectToTaskDao the entityObjectToTaskDao to set
	 */
	public void setEntityObjectToTaskDao(EntityObjectToTaskDao entityObjectToTaskDao) {
		this.entityObjectToTaskDao = entityObjectToTaskDao;
	}
	
	/**
	 * @param dutyersInfoManager the dutyersInfoManager to set
	 */
	public void setDutyersInfoManager(DutyersInfoManager dutyersInfoManager) {
		this.dutyersInfoManager = dutyersInfoManager;
	}
	
	/**
	 * @param riskRuleService the riskRuleService to set
	 */
	public void setRiskRuleService(RiskRuleService riskRuleService) {
		this.riskRuleService = riskRuleService;
	}
	
	@Deprecated
	public Task createTask(Long clerkId, int type ) {
		return createTask(clerkId, type,  (Long) null);
	}
	@Deprecated
	public Task createTask(Long clerkId, int type,  Long matterId) {
		if (matterId != null) {
			return createTask(clerkId,type,  new Long[] { matterId });
		} else {
			return createTask(clerkId,type,  (Long[]) null);
		}
	}
	@Deprecated
	public Task createTask(Long clerkId,int type,  Long[] matterIds) {
		return createTask(clerkId,type, matterIds,(Long) null);
	}
	@Deprecated
	public Task createTask(Long clerkId,int type, Long[] matterIds,Long objectId) {
		return createTask(clerkId, type, matterIds, objectId, null, (short)0, null);
	}
	@Deprecated
	public Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, Long sn, short dutyerType, Long dutyerID) {
		Task task = new Task();
		task.setSn(sn);
		task.setClerkID(clerkId);
		task.setType(type);
		return createTask(task, matterIds, objectId, dutyerType, dutyerID);
	}
	@Deprecated
	public Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID) {
		Task task = new Task();
		task.setClerkID(clerkId);
		task.setType(type);
		return createTask(task, matterIds, objectId, dutyerType, dutyerID);
	}
	@Deprecated
	public Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, 
			short dutyerType, Long dutyerID, String note){
		return createTask(clerkId, type, matterIds, objectId, dutyerType, dutyerID, note, null);
	}
	@Deprecated
	public Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, 
			short dutyerType, Long dutyerID, String note, Date beginTime) {
		Task task = new Task();
		task.setClerkID(clerkId);
		task.setType(type);
		task.setNote(note);
		task.setBeginTime(beginTime);
		return createTask(task, matterIds, objectId, dutyerType, dutyerID);
	}
	
	protected String getTaskName(int taskType){
		String typeName = glossaryService.getByCategoryAndCode(Glossary.CATEGORY_TASKTYPE, taskType);
		if(StringUtils.isNotBlank(typeName)){
			return typeName;
		}

		TaskDef taskDef = taskDefProvider.getTaskDef(taskType);
		if(taskDef != null){
			return taskDef.getName();
		}
		return null;
	}
	
	protected String getWorkName(int workType){
		String typeName = glossaryService.getByCategoryAndCode(Glossary.CATEGORY_WORKTYPE, workType);
		if(StringUtils.isNotBlank(typeName)){
			return typeName;
		}
		WorkDef workDef = workDefProvider.getWorkDef(workType);
		if(workDef != null){
			return workDef.getName();
		}
		return null;
	}
	
	@Deprecated
	public Task createTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID){
		RFSObject object = objectService.getObject(objectId);
		return createTask(task, matterIds, object, dutyerType, dutyerID);
	}
	
	/////////////////////////////////////////////////////////
	// FIXME: ��Ҫ����
	/////////////////////////////////////////////////////////
	public Task createTask(Task task, Long[] matterIds, RFSEntityObject object, short dutyerType, Long dutyerID){
		Assert.notNull(task);
		Assert.notNull(task.getType());
		Assert.notNull(task.getClerkID());
		
		int type = task.getType();
		TaskDef taskDef = taskDefProvider.getTaskDef(type);
		
		String typeName = getTaskName(type);//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_TASKTYPE, type);
		if(StringUtils.isNotBlank(typeName)){
			task.setName(typeName);
		}
		
		if(task.getSn() == null){
			task.setSn(generateId());
		}
		if(task.getVisibility() == 0){
			if(taskDef != null){
				task.setVisibility(taskDef.getVisibility());
			}else{
				task.setVisibility(Task.VISIBILITY_GENERAL);
			}
		}
		//Task task = new Task();
		//IdGenerator<Long> taskGen = ((IdGeneratable<Long>) taskDao).getIdGenerator();
		//Long taskSn = taskGen.getNext();
		//task.setSn(sn);
		//task.setType(type);
		
//		if(task.getCreator() == null){
//			//task.setClerkID(clerkId);
//			task.setCreator(task.getClerkID());
//		}
		if(task.getBeginTime() == null){
			task.setBeginTime(new Date());
		}
//		if(task.getCreationTime()==null){
//			task.setCreationTime(new Date());
//		}
		if(task.getStatus() == 0){
			task.setStatus(Task.TASK_STATUS_WORK);
		}
		task.setDutyerID(dutyerID);
		task.setDutyerType(dutyerType);
		
		Clerk clerk = clerkService.getClerk(task.getClerkID());
		if(clerk == null){
			throw new ClerkNotFoundException("ID: " + task.getClerkID());
		}
		task.setClerkName(clerk.getName());
		task.setEntityID(clerk.getEntityID());
		task.setEntityName(clerk.getEntityName());
		
		//������������Ϣ
		log.debug("��ҵ������������������Ϣ");
		dutyersInfoManager.findAndSetDutyers(task, taskDef, object, dutyerID);
		
		//����TaskΪժҪ
		if(object != null){
			//task.setSummary(object.getName());
			//task.setNote(clerk.getName());
			task.setRefObjectId(object.getId());
			task.setRefObjectType(object.getObjectType());
			
			if(object instanceof RFSObject){
				RFSObject obj = (RFSObject) object;
				
				task.setRefObjectName(obj.getName());
				//��Ŀ����
				task.setString0(obj.getDutyClerkName());
				//������ܲ���
				task.setString1(obj.getDutyDepartmentName());
			}
			
			try {
				Method method = object.getClass().getMethod("getSn");
				if(String.class.equals(method.getReturnType())){
					String sn = (String)method.invoke(object);
					if(sn != null){
						task.setString4(sn);
					}
				}
			} catch (SecurityException e) {
				//ignore
			} catch (NoSuchMethodException e) {
				//ignore
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			}
		}
		
		if(StringUtils.isBlank(task.getCode())){
			String code = codeGeneratorProvider.generateCode(task);
			task.setCode(code);
		}
		
		
		List<RiskEntry> entries = Collections.emptyList();
		if(object instanceof RFSObject){
			/***
			 * Ϊ���� Task ��ӷ��ռ��,���ʱ�� 2008/12/14 start ymq
			 */
			//FIXME ���ܻ�������
			entries = riskService.createRisk(task, task.getType(), dutyerID, (RFSObject)object);
			task.setRiskEntries(entries);
		}
		
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "timeUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "timeUsed", scaleId);
			task.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "hangUsed", scaleId);
			task.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "delayUsed", scaleId);
			task.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(task, "hangTimes", scaleId);
			task.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(task, "delayTimes", scaleId);
			task.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		
		/******************************************** end ymq ***/
		
		if (matterIds != null && matterIds.length > 0) {
			
			/***
			 * 1.�޸� TimeLimits ���,�޸�ʱ�� 2008/12/14 start ymq
			 * 2.����matter��taskmatter��ȥ,�޸�ʱ�� 2008/03/01 by ymq
			 */
			Matter matter;
			TaskMatter taskMatter;
			for (Long matterId : matterIds) {
				//�˴�����matterIds ������
				matter = matterDao.get(matterId);
				taskMatter = new TaskMatter();
				taskMatter.setTaskSN(task.getSn());
				taskMatter.setMatterID(matterId);
				if (matter != null) {
					taskMatter.setMatterCode(matter.getCode());
					taskMatter.setMatterName(matter.getName());
				}
				taskMatterDao.save(taskMatter);
				Long taskTrack = createTaskTrack(task.getSn(), type, matterId);
				if(taskTrack!=null){
					task.setBizTrack(taskTrack);
				}
				if(object != null){
					clueService.createClue(object.getId(), CATEGORY_TASK, task.getType(), matterId, task.getSn(), task.getBizTrack());
				}
			}
		
			
//			for (int i = 0; i < matterIds.length; i++) {
//				setTimeLimits(task, matterIds[i]);
//			}
//			//�˴�����matterIds ������
//			Long taskTrack = createTaskTrack(task.getSn(), type, matterIds);
//			if(taskTrack!=null){
//				task.setBizTrack(taskTrack);
//			}
		}
		
		
		
		
		
		
		task = taskDao.save(task);

		if (object != null) {
			updateTaskEntityObjects(task, object);
			
			//if (matterIds != null && matterIds.length > 0) {
			//	attachToJobs(type, task.getSn(), objectId, matterIds);
			//}
			//���������
			if(object instanceof RFSObject){
				createObjectTask(object.getId(), task.getSn());
			}
		}
		for(RiskEntry re:entries){
			Risk r = riskService.getRiskById(re.getRiskID());
			riskService.sendRiskLogEvent(r,task,RiskLogEvent.START);		
		}
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.CREATED, task));
		}
		return task;
	}

	public void hangTask(Task task, Date hangTime) {
//		Task task = taskDao.get(taskSn);
		if (task != null) {
			if(task.getStatus() != Task.STATUS_�ڰ�){
				throw new IllegalArgumentException("ֻ���ڰ�״̬��Task������ͣ��" + task);
			}
			
			// ��ͣʱ������Ϊ��ǰ��
			task.setHangTime(hangTime != null ? hangTime : new Date());
//			byte hangTimes = (byte) (task.getHangTimes() + 1);
			// ��ͣ����+1��
			//task.setHangTimes(hangTimes);
			//��ͣ����
			byte hangUsed = (byte)(task.getHangUsed() + 1);
			task.setHangUsed(hangUsed);
			
			//���¼���
			//calculateTimeUsed(task, task.getHangTime());
			calculateTaskTimeUsed(task, task.getHangTime(), false);
			
			task.setStatus(Task.TASK_STATUS_HANG);
			
			List<RiskEntry> res = task.getRiskEntries();
			if(res != null){
				for( RiskEntry re:res){
					if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
//					if(re.getRiskID() != null){
						//��ͣ�ض���risk
						//FIXME: 2011-12-08 �������㷨������ͣrisk
						//riskService.hangRisk(re.getRiskID(), hangTime);
					}
				}
			}
			
			taskDao.update(task);
			bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, task.getSn(), Task.TASK_STATUS_HANG);
			
			if(dispatcher != null){
				dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.HANG, task));
			}
		}
	}

	public void wakeTask(Task task, Date wakeTime) {
//		Task task = taskDao.get(taskSn);
		if (task != null) {
			if(task.getStatus() != Task.STATUS_��ͣ){
				throw new IllegalArgumentException("ֻ����ͣ״̬��Task��������");
			}
			
			// ����ʱ������Ϊ��ǰ
			task.setWakeTime(wakeTime != null ? wakeTime : new Date());
			
			//���ü�����ʱ��ָ��
			//task.setTimeUsedPosition(task.getWakeTime());
			
			//����ʱ - ʵ����ʱ������ʵ����ͣ��ʱ��
			//short timeHang = (short) (DateUtil.getTimeUsed(task.getBeginTime(), task.getWakeTime(), task.getTimeUnit()) - task.getTimeUsed());
			//FIXME ������㷨û�п���һ�������֮ǰһֱû�н��й�ʱ�޼��㣬������ʱ�ŵ�һ�μ��㣬��ʱ�Ѿ���ʱΪ0��������ı�����ͣʱ�䲻׼
			//����ȫ�����¼�����
			boolean includeFirstDay = includeFirstDay(task);
			int timeUsedWhenHang = DateUtil.getTimeUsed(task.getBeginTime(), task.getHangTime(), task.getTimeUnit(), includeFirstDay) - task.getTimeHang();
			int timeUsedWhenWake = DateUtil.getTimeUsed(task.getBeginTime(), task.getWakeTime(), task.getTimeUnit(), includeFirstDay) - task.getTimeHang();
			short currentTimeHang = (short)(timeUsedWhenWake - timeUsedWhenHang);
			
			//int currentTimeHang = DateUtil.getTimeUsed(task.getHangTime(), task.getWakeTime(), task.getTimeUnit());
//			byte flag = 9;
//			int date = DateUtil.getTimeUsed(task.getHangTime(), task.getWakeTime(), flag);
			short timeHang = (short) (task.getTimeHang() + currentTimeHang);
			// ��ͣ��ʱ�ۼ�
			task.setTimeHang(timeHang);
			task.setCurrentTimeHang((short)0);
			task.setTimeUsed((short) timeUsedWhenHang);//��ʵ�ʾ�����ͣʱ����ʱ
			task.setTimeUsedCalculateTime(task.getWakeTime());
			
			task.setStatus(Task.TASK_STATUS_WORK);
		
			List<RiskEntry> res=task.getRiskEntries();
			if(res != null){
				for( RiskEntry re:res){
					if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
						//FIXME: 2011-12-08 �������㷨������ͣtask�����Բ�����task
						//riskService.wakeRisk(re.getRiskID(), wakeTime);
					}
				}
			}
			
			taskDao.update(task);
			bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, task.getSn(), Task.TASK_STATUS_WORK);	
			
			if(dispatcher != null){
				dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.WAKE, task));
			}
		}
	}

	public void terminateTask(Task t, Byte result, Date endTime) {
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() 
					+ "��������������ִ�С�����������ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		t.setStatus(Task.TASK_STATUS_TERMINATE);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		if(result != null){
			t.setResult(result);
		}
		
		/** �������� Task ����, add by ymq 2009-1-3 **/
		//riskService.deleteRisk(t);
		/////////////////////////////////////////////////////
		//2012-05-07 ������Ĵ����滻ɾ����ֻ��������ɾ��
		terminateRisks(t, endTime);
		//////////////////////////////////////////////////////

		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.TERMINATE, t));
		}
	}
	
	private void terminateRisks(Task t, Date endTime){
		List<RiskEntry> entries = t.getRiskEntries();
		if(entries != null) {
			for(RiskEntry re : entries) {
				Risk r = riskService.getRiskById(re.getRiskID());
				if(r != null){
					riskService.terminateRisk(r, t, endTime);
				}else {
					log.warn("�Ҳ���Task.riskEntries�е�RiskID��Ӧ��Risk��" 
							+ re.getRiskID() + " " 
							+ re.getObjectAttr() + " "
							+ re.getValueSource());
				}
			}
		}
	}
	
	private boolean isEnd(Task task){
		//�Ѿ�����
		/*if (Task.TASK_STATUS_AVOID == status
				|| Task.TASK_STATUS_CANCEL == status
				|| Task.TASK_STATUS_REJECT == status
				|| Task.TASK_STATUS_STOP == status
				|| Task.TASK_STATUS_TERMINATE == status
				|| Task.TASK_STATUS_TRANSFER == status
				|| Task.TASK_STATUS_WITHDRAW == status) {
		*/
		return task.getEndTime() != null;
	}
	
	public void terminateTask(Task t, Date endTime) {
		/*
		short timeUsed = (short) (t.getTimeUsed() - t.getTimeHang());
		t.setTimeUsed(timeUsed);
		// ʵ����ʱ���ܰ�����ͣʱ�����ӳ�ʱ��
		t.setStatus(Task.TASK_STATUS_TERMINATE);	
		t.setEndTime(endTime != null ? endTime : new Date());
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		riskService.deleteRisk(t);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.TERMINATE, t));
		}
		*/
		
		terminateTask(t, null, endTime);
	}
	
	public void delayTask(Long taskSn, short delayTime){
		Task task = taskDao.get(taskSn);
		delayTask(task, delayTime);
	}
	public void delayTask(Task task, short delayTime){
		//Task task = taskDao.get(taskSn);
		//ʵ�����ڴ���
		task.setDelayUsed((byte) (task.getDelayUsed() + 1));
		//����ʱ��
		task.setTimeDelay((short) (task.getTimeDelay() + delayTime));
		//�޸�ʱ��
		task.setTimeLimit((short) (task.getTimeLimit() + delayTime));
		
		//�޸�Risk��ScaleValue
		List<RiskEntry> entries = task.getRiskEntries();
		if(entries != null){
			for(RiskEntry e: entries){
				//�����ض��ļ����
				if("timeUsed".equalsIgnoreCase(e.getObjectAttr())){
					riskService.delayRisk(e.getRiskID(), delayTime);
				}
			}
		}
		
		taskDao.update(task);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.DELAY, task));
		}
	}


	public  boolean setTimeLimits(Task task, Long matter) {
		byte category = 2;
		boolean flag = true;
		TimeLimit tl = timeLimitDao.getTimeLimt(category, task.getType(),
				matter);
		if (tl != null) {
			if (tl.getTimeLimit() > task.getTimeLimit()) {
				task.setTimeLimit(tl.getTimeLimit());
			}
			if (tl.getHangLimit() > task.getHangLimit()) {
				task.setHangLimit(tl.getHangLimit());
			}
			if (tl.getDelayLimit() > task.getDelayLimit()) {
				task.setDelayLimit(tl.getDelayLimit());
			}
			if (tl.getHangTimes() > task.getHangTimes()) {
				task.setHangTimes(tl.getHangTimes());
			}
			if (tl.getDelayTimes() > task.getDelayTimes()) {
				task.setDelayTimes(tl.getDelayTimes());
			}
		} else {
			flag = false;
		}
		return flag;
	}

	

	public Long createTaskTrack(Long taskSn, int taskType, Long... matterIds) {
		byte category = 2;// task
		BizRoute taskRoute;
		Long bizTrackSn = null;

		//�˴�ѭ�����޸� �ݿ���matterids�ĳ��ȵ���1�����  matterIds[i]������
		for (int i = 0; i < matterIds.length; i++) {
			taskRoute = bizRouteService.getBizRoute(category, taskType,	matterIds[i]);
			if (taskRoute != null) {
				bizTrackSn = bizTrackService.createBizTrack(category, taskSn, taskRoute);
			}
		}
		return bizTrackSn;
	}

	/**
	 * 
	 * @param taskType
	 * @param taskSn
	 * @param objectId
	 * @param matterIds
	 * @deprecated
	 */
	void attachToJobs(int taskType, Long taskSn, Long objectId, Long... matterIds) {
		// �����⣬tasktype��threadtype����һ������
		Thread myThread;
		Long myMatter;
		MatterAffair myAffair;
		int myType;
		byte bizAction = 1;
		for (int i = 0; i < matterIds.length; i++) {
			// ÿ��matter���԰������affair
			myMatter = matterIds[i];
			List<MatterAffair> myAffairList = matterAffairDao.findAffairs(CATEGORY_TASK,myMatter,bizAction);
			for (int j = 0; j < myAffairList.size(); j++) {
				myAffair = myAffairList.get(j);
				myType=myAffair.getBizType();
				// ���Ҷ�Ӧ����������Ϊ��
				Byte action = myAffair.getAction();
				// ���һ������������Ϊ��
				myThread = threadService.getActiveThread(objectId,myType, myAffair.getAffairID());
				
				// ������±�Ĳ�����
				// 1�����������������������������ֱ�Ӵ�����������
				// 2������������������򴴽���������
				// 3����������������޲�������
				// 4�������������Ȼ�������
				// �������޲�����
				// �Ѹý����ģ�������
				if (action == 1) {
					if (myThread != null) {
						threadService.terminateThread(myThread);
					}
					myThread = threadService.createThread(objectId,myType,myAffair.getAffairID());
					
				}
				if (action == 2) {
					if (myThread == null) {
						myThread = threadService.createThread(objectId,myType,
								myAffair.getAffairID());
					}
				}
				
				if (action == 4) {
					if (myThread != null) {
						threadService.terminateThread(myThread);
					}
				}
				if (action == 1||action ==2||action==3||action==4) {
					if (myThread != null) {
						bizTrackNodeInstanceService.createBizTrackNodeInstance((byte) 2, taskType, taskSn, myThread.getBizTrack());
					}
				}
			}
		}
	}

	private boolean createObjectTask(Long objectId, Long taskSn) {
		return createObjectTask(objectId, taskSn, (short) 0);
	}

	private boolean createObjectTask(Long objectId, Long taskSn, int type) {
		boolean flag = true;
		ObjectTask myObjectTask = new ObjectTask();
		myObjectTask.setObjectID(objectId);
		myObjectTask.setTaskSN(taskSn);
//		myObjectTask.setCreationTime(new Date());
		myObjectTask.setCreateTask((Integer) taskSn.intValue());
		myObjectTask.setType(type);
		myObjectTask = objectTaskDao.save(myObjectTask);
		if (myObjectTask != null) {
			return flag;
		} else {
			flag = false;
			return flag;
		}
	}
	
	@Deprecated
	void  attachToTrack (int taskType, Long taskSn,Long trackSn){  
		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte)2,taskType,taskSn,trackSn);
	}
	@Deprecated
	void attachToJob (int taskType,Long taskSn,Job upJob){  
		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte)2,taskType,taskSn,upJob.getBizTrack());
	}

	@Deprecated
	void attachToJob(int taskType, Long taskSn, Long upJobSn) {
		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte) 2, taskType, taskSn, upJobSn);
	}
	
	public Task createTask(Task task) {
		task = taskDao.save(task);
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.CREATED, task));
		}
		return task;
	}
	
	/*
	public Task createTask(Task task, Long[] matterIds, Long objectId){
		if(task.getSn() == null){
			task.setSn(generateId());
		}
		String code = codeGeneratorProvider.generateCode(Task.class, TASK_CATEGORY, task.getType());
		task.setCode(code);
		task.setBeginTime(new Date());
		task.setCreationTime(new Date());
		task.setStatus(Task.TASK_STATUS_WORK);
		if (matterIds != null && matterIds.length > 0) {
			for (int i = 0; i < matterIds.length; i++) {
				setTimeLimits(task, matterIds[i]);
			}
			//�˴�����matterIds ������
			Long taskTrack =createTaskTrack(task.getSn(), task.getType(), matterIds);
			if(taskTrack != null ){
				task.setBizTrack(taskTrack);
			}
		}
		task = taskDao.save(task);

		if (objectId != null) {
			if (matterIds != null && matterIds.length > 0) {
				attachToJobs(task.getType(), task.getSn(), objectId, matterIds);
			}
			createObjectTask(objectId, task.getSn());
		}

		return task;
	}*/
	
	public int deleteTask(Task task) {
		//riskService.deleteRisk(task);
		riskService.deleteRiskByTaskSn(task.getSn());
		int r = taskDao.delete(task);
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.DELETED, task));
		}
		return r;
	}
	
	public Task updateTask(Task task) {
		task =  taskDao.update(task);
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.UPDATED, task));
		}
		return task;
	}
	
	/* add ymq */
	
	public List<BizVO> findTasksByObjectId(Long objectId) {
		return findTasksByObjectId(objectId, null);
	}
	
	public List<BizVO> findTasksByObjectId(Long objectId, Integer taskType) {
		List<BizVO> taskVO = new ArrayList<BizVO>();
		List<Task> tasks;
		if (taskType == null) {
			tasks = taskDao.findTaskByObjectID(objectId);
		} else {
			tasks = taskDao.findTasks(objectId, taskType);
		}
		Task task;
		BizVO vo;
		for(int i = 0; i < tasks.size(); i++){
			task = tasks.get(i);
			vo = new BizVO();
			vo.setOrder(Long.valueOf(i + 1));
			vo.setCode(task.getCode());
			vo.setType(task.getType());
			vo.setStatus(task.getStatus());
			//vo.setTypeName(glossaryService.getByCategoryAndCode(BizVO.GLOSSARY_TASK_CODE, task.getType()));
			vo.setTypeName(task.getName() != null ? task.getName() : getTaskName(task.getType()));
			vo.setBeginTime(task.getBeginTime());
			vo.setEndTime(task.getEndTime());
			vo.setTimeLimit(task.getTimeLimit());
			vo.setSn(task.getSn());
//			int date = DateUtil.getTimeUsed(task.getBeginTime(), task.getEndTime(), (byte)9);
			short timeUsed = (short) (task.getTimeUsed() - task.getTimeHang() - task.getTimeDelay());
			vo.setTimeUsed(timeUsed);
			vo.setRemarks(task.getNote());
			vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
			taskVO.add(vo);
		}
		return taskVO;
	}
	
	
	private void setTypeNameForBizVOGroup(List<BizVO> group, String typeName){
		String name = typeName + " (" + group.size() + " ��)";
		for(BizVO biz: group){
			biz.setTypeName(name);
		}
	}
	
	@Deprecated
	public List<BizVO> findTaskForTodayHint(Long orgID,Long clerkID,int index) {
		List<BizVO> taskVO = new ArrayList<BizVO>();
		List<Task> tasks = null;
		if(index==1){//�����û�ID������
			tasks=taskDao.findTaskByClerkID(clerkID);
		}else{
			tasks=taskDao.findTaskByDutyerID(orgID);	
		}
		Task task;
		BizVO vo;
		//int tmp=0;
		
		
		int currentTaskType = 0;
		String currentTaskTypeName = "";
		List<BizVO> group = new ArrayList<BizVO>();
		
		for(int i = 0; i < tasks.size(); i++){
			task = tasks.get(i);
			//�����ڻ����TASKʱ
			if(task.getType() != currentTaskType){
				//�ȴ�����һ�������
				if(currentTaskType != 0){
					setTypeNameForBizVOGroup(group, currentTaskTypeName);
				}//else ��һ�������ݣ� ������
				
				
				//�ٴ����������
				currentTaskTypeName = getTaskName(task.getType());//glossaryService.getByCategoryAndCode(BizVO.GLOSSARY_TASK_CODE, task.getType());
				currentTaskType = task.getType();
				group.clear();
			}

			
			vo = new BizVO();
			vo.setOrder(Long.valueOf(i + 1));
			vo.setCode(task.getCode());
			vo.setType(task.getType());
			vo.setStatus(task.getStatus());	
			vo.setBeginTime(task.getBeginTime());
			vo.setEndTime(task.getEndTime());
			vo.setTimeLimit(task.getTimeLimit());
			vo.setSn(task.getSn());
//			int date = DateUtil.getTimeUsed(task.getBeginTime(), task.getEndTime(), (byte)9);
			short timeUsed = (short) (task.getTimeUsed() - task.getTimeHang() - task.getTimeDelay());
			vo.setTimeUsed(timeUsed);
			vo.setRemarks(task.getRemark());
			vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
			
			
			List<RFSObject> objects = objectTaskDao.findObjectsByTaskSN(task.getSn());
			if(objects.size() > 0){
				//throw new DataAccessException("�Ҳ���TASK��Ӧ����Ŀ");
				RFSObject object = objects.iterator().next();
				
				//see  artf1134: ҵ����ʾ�Ż�  
				//��������: Bugs - flex base 
				//����: ҵ����ʾ�Ż� 
				//˵��: ҵ����ʾӦ�Ż�Ϊ��ҵ���š�ҵ�����״̬���涨ʱ�ޣ�ʣ��ʱ�䣬��ע�����У���ע���ܺܳ���Ӧ��tip��һ������Ϊ��
				//09030310040001�� �ƻ����գ����ƣ�3�죬-1�죬��ɽ�������ġ� 
				vo.setRemarks(object.getName() + getCode(object));
			}//else �Ҳ�����Ӧ��
			
			
			taskVO.add(vo);
			group.add(vo);
		}
		
		//�������һ��
		if(group.size() > 0){
			this.setTypeNameForBizVOGroup(group, currentTaskTypeName);
		}
		
		
		/*
		for(int i = 0; i < tasks.size(); i++){
			task = tasks.get(i);
			vo = new BizVO();
			vo.setOrder(Long.valueOf(i + 1));
			vo.setCode(task.getCode());
			vo.setType(task.getType());
			vo.setStatus(task.getStatus());					
			if(currentTaskType!=task.getType()){
				
			}
			
			
			vo.setTypeName(glossaryService.getByCategoryAndCode(BizVO.GLOSSARY_TASK_CODE, task.getType())+"("+tmp+")");
			
			vo.setBeginTime(task.getBeginTime());
			vo.setEndTime(task.getEndTime());
			vo.setTimeLimit(task.getTimeLimit());
			int date = DateUtil.getTimeUsed(task.getBeginTime(), task.getEndTime(), (byte)9);
			short timeUsed = (short) (date - task.getTimeHang() - task.getTimeDelay());
			vo.setTimeUsed(timeUsed);
			vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
			taskVO.add(vo);
		}*/
		return taskVO;
	}
	
	private String getCode(RFSObject object){
		try {
			String s = (String) PropertyUtils.getProperty(object, "sn");
			if(s != null){
				return "(��ţ�" + s + ")";
			}
		}catch (Exception e) {	
		}
		return "";
	}
	
	
	
	
	/** add by ymq *************************************/
	
	
	public BizVO getTaskBusinessSurvey(Long taskId) {
		Task task = getTask(taskId);
		BizVO vo = null;
		if (task != null) {
			vo = new BizVO();
			vo.setOrder(1L);
			vo.setCode(task.getCode());
			vo.setType(task.getType());
			vo.setStatus(task.getStatus());
			vo.setTypeName(task.getName() != null ? task.getName() : getTaskName(task.getType()));//glossaryService.getByCategoryAndCode(BizVO.GLOSSARY_TASK_CODE, task.getType()));
			vo.setBeginTime(task.getBeginTime());
			vo.setEndTime(task.getEndTime());
			vo.setTimeLimit(task.getTimeLimit());
			vo.setSn(task.getSn());
//			int date = DateUtil.getTimeUsed(task.getBeginTime(), task.getEndTime(), (byte)9);
//[2009-11-27]short timeUsed = (short) (task.getTimeUsed() - task.getTimeHang() );
			short timeUsed = (short) (task.getTimeUsed());
			
			vo.setTimeUsed(timeUsed);
			vo.setRemarks(task.getRemark());
			vo.setClerkID(task.getClerkID());
			vo.setDutyer(task.getDutyerID());
			if(task.getClerkID()!=null){
				Clerk clerk=clerkService.getClerk(task.getClerkID());
				if(clerk!=null){
					vo.setClerkName(clerk.getName());
				}	
			}	
			if(task.getDutyerID()!=null){
				Org org=orgDao.get(task.getDutyerID());
				if(org!=null){
					vo.setDutyName(org.getAbbr());
				}
			}
			
			//vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
			RiskEntry re=task.getRiskEntryByObjectAttr("timeUsed");
			if(re!=null){
				Risk r=riskService.getRiskById(re.getRiskID());
				if(r!=null){
					vo.setSuperviseStatus(r.getGradeExplain());
					vo.setGrade(r.getGrade());
				}
			}else{
				vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
				vo.setGrade(BizVO.GRADE_DEFAULT);
			}
		}
		return vo;
	}
	
	public List<BizVO> findTaskBusinessLog(Long taskId) {
		List<Work> works = workService.findWorkByTaskSN(taskId);
		List<BizVO> list = new ArrayList<BizVO>();
		if (works != null && !works.isEmpty()) {
			BizVO vo;
			for (Work work : works) {
				vo = new BizVO();
				vo.setCode(work.getCode());
				vo.setType(work.getType());
				vo.setStatus(work.getStatus());
				//vo.setTypeName(glossaryService.getByCategoryAndCode(BizVO.GLOSSARY_WORK_CODE, work.getType()));
				vo.setTypeName(work.getName() != null ? work.getName() : getWorkName(work.getType()));
				vo.setBeginTime(work.getBeginTime());
				vo.setEndTime(work.getEndTime());
				vo.setTimeLimit(work.getTimeLimit());
				vo.setSn(work.getSn());
				vo.setClerkID(work.getClerkID());
				if(work.getClerkID()!=null){
					Clerk clerk=clerkService.getClerk(work.getClerkID());
					if(clerk!=null){
						vo.setClerkName(clerk.getName());
					}
				}
//				int date = DateUtil.getTimeUsed(work.getBeginTime(), work.getEndTime(), (byte)9);
				short timeUsed = (short) (work.getTimeUsed() - work.getTimeHang() - work.getTimeDelay());
				vo.setTimeUsed(timeUsed);
				vo.setRemarks(work.getRemark());
				vo.setSuperviseStatus(BizVO.SUPERVISESTATUS_DEFAULT);
				list.add(vo);
			}
		}
		return list;
	}
	
	
	public MonitorVO getTaskMonitorRiskInfo(Long taskId) {
		Task task = getTask(taskId);
		if (task != null) {
			Risk risk = riskService.querySingleRisk(task.getId(), Short.parseShort("102"));
			MonitorVO vo = null;
			if (risk != null) {
				vo = new MonitorVO();
				vo.setConclusion(risk.getConclusion());
				vo.setJuralLimit(risk.getJuralLimit());
				vo.setGrade(risk.getGrade());
				vo.setTaskCode(task.getCode());
				vo.setTimeLimit(task.getTimeLimit());
				vo.setGradeName(risk.getGradeExplain());
				
				//�����ա������
				if(RiskRule.VALUE_UNIT_WORKDAY==risk.getValueUnit()){
				    vo.setLimitTime(DateUtil.workdaysLater(task.getBeginTime(), task.getTimeLimit()));
				}else if(RiskRule.VALUE_UNIT_DAY==risk.getValueUnit()){//
//				    vo.setLimitTime(DateUtil.getDays(task.getBeginTime(), task.getTimeLimit(), Calendar.DATE));
				    vo.setLimitTime(DateUtil.daysLater(task.getBeginTime(), task.getTimeLimit()));
				}

				BigDecimal remainTime=risk.getScaleValue();
				if(remainTime!=null){
					if(risk.getValue()!=null){
						vo.setRemainTime(remainTime.subtract(risk.getValue()));
					}
				}
				
				vo.setScaleValue(risk.getScaleValue());
				vo.setValue(risk.getValue());
				vo.setModificationTime(risk.getModificationTime());
				vo.setStatusName(task.getStatusName());
				vo.setStatus(task.getStatus());
				vo.setBeginTime(task.getBeginTime());
				vo.setEndTime(task.getEndTime());
				vo.setScaleMark(risk.getScaleMark());
				vo.setScaleValue1(risk.getScaleValue1());
				vo.setScaleValue2(risk.getScaleValue2());
				vo.setScaleValue3(risk.getScaleValue3());
				vo.setScaleValue4(risk.getScaleValue4());
				vo.setScaleValue5(risk.getScaleValue5());
				vo.setScaleValue6(risk.getScaleValue6());
				vo.setRemark(risk.getRemark());	
			}
			return vo;
		}
		return null;
	}

	/** end add *************************************/
	
	
	
	/**
	 * @deprecated
	 */
	public List<Task> getTask(Long objectID, int taskType) {
		return taskDao.findTasks(objectID, taskType);
	}

	public Task getTask(Long sn) {
		return taskDao.get(sn);
	}

	public Long generateId() {
		return idGenerator.getNext();
	}

	/**
	 * @return the objectsDao
	 */
	public ObjectsDao getObjectsDao() {
		return objectsDao;
	}
	/**
	 * @param objectsDao the objectsDao to set
	 */
	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(taskDao);
		Assert.notNull(idGenerator);
		Assert.notNull(timeLimitDao);
		Assert.notNull(objectTaskDao);
		Assert.notNull(matterAffairDao);
		Assert.notNull(bizRouteService);
		Assert.notNull(bizTrackService);
		Assert.notNull(threadService);
		Assert.notNull(bizTrackNodeInstanceService);
		Assert.notNull(codeGeneratorProvider);
		
	}
	
	public List<Task> findTaskByObjectId(Long objectId) {		
		return taskDao.findTaskByObjectID(objectId);
	}
	
    public List<Task> findTaskAllVisibilityByObjectID(Long objectId) {       
        return taskDao.findTaskAllVisibilityByObjectID(objectId);
    }
	   
//	public void updateTimeLimit(Long sn,short timeLimit){
//		Task t=taskDao.get(sn);
//		Assert.notNull(t);
//		if(t!=null){
//		t.setTimeLimit(timeLimit);
//		taskDao.update(t);
//		List<RiskEntry> res=t.getRiskEntries();
//		if(res!=null){
//			for( RiskEntry re:res){
//				if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
//					riskService.updateRiskScale(re.getRiskID(), new BigDecimal(timeLimit), (byte)0);		
//				}
//			}
//		}else{
//			log.warn("taskSn=["+t.getSn()+"]û�ж�Ӧ��RiskEntry����!");
//		}
//		}
//	}
	
	/**
	 * �������Task��Risk��ʱ�������Ϣ��ע�⣺autoTuneScale��д����Ϊ�˼���Touchstone�оɵ�ʵ�ֶ�������
	 * 
	 * @param sn 
	 * @param timeLimit 
	 * @param autoTuneScale ���Ϊtrue����ScaleValue2/3/5��ScaleValue�ֶ�ֵ��80%/100%/120%�Ĺ̶��������������Ƿ��ǰٷֱȼ����򣩽��м������£����������㡣
	 */
	public void updateTimeLimit(Long sn,short timeLimit,boolean autoTuneScale){
		Task t=taskDao.get(sn);
		Assert.notNull(t);
		if(t!=null){
		t.setTimeLimit(timeLimit);
		taskDao.update(t);
		List<RiskEntry> res=t.getRiskEntries();
		if(res!=null){
			for( RiskEntry re:res){
				if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
					riskService.updateRiskScale(re.getRiskID(), new BigDecimal(timeLimit), (byte)0);	
					if(autoTuneScale){
						riskService.updateRiskScale(re.getRiskID(), new BigDecimal(timeLimit+1), (byte)3);
						//�˴���80%��0.8����д����ʱ��ٷֱ�
						BigDecimal scaleValue2=new BigDecimal(timeLimit).multiply(BigDecimal.valueOf(0.8));
						if(scaleValue2.compareTo(BigDecimal.valueOf(scaleValue2.intValue()))>0){
							scaleValue2=BigDecimal.valueOf(scaleValue2.intValue()+2);
						}else{
							scaleValue2=BigDecimal.valueOf(scaleValue2.intValue()+1);
						}
						riskService.updateRiskScale(re.getRiskID(), scaleValue2, (byte)2);
						
						//�˴���120%��1.2����д����ʱ��ٷֱ�
						BigDecimal scaleValue5=new BigDecimal(timeLimit).multiply(BigDecimal.valueOf(1.2));
						if(scaleValue5.compareTo(BigDecimal.valueOf(scaleValue5.intValue()))>0){
							scaleValue5=BigDecimal.valueOf(scaleValue5.intValue()+2);
						}else{
							scaleValue5=BigDecimal.valueOf(scaleValue5.intValue()+1);
						}
						riskService.updateRiskScale(re.getRiskID(), scaleValue5, (byte)5);
					}
					Risk r=riskService.getRiskById(re.getRiskID());
					riskService.checkRiskGrade(r);
				}
			}
		}else{
			log.warn("taskSn=["+t.getSn()+"]û�ж�Ӧ��RiskEntry����!");
		}
		}
	}
	
	/**
     * �������Task��Risk��ʱ�������Ϣ��֧�ְٷֱȺͷǰٷֱȼ������timelimit����
     * @param sn 
     * @param timeLimit 
     */
    public void updateTimeLimit2(Long sn,short timeLimit){
        Task t=taskDao.get(sn);
        Assert.notNull(t);
        if(t!=null){
        t.setTimeLimit(timeLimit);
        taskDao.update(t);
        
        List<RiskEntry> res=t.getRiskEntries();
        if(res!=null){            
            for( RiskEntry re:res){
                if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
                    Risk r=riskService.getRiskById(re.getRiskID());
                    if(r!=null){
                        riskService.updateRiskScaleValue(re.getRiskID(), new BigDecimal(timeLimit));
                        riskService.checkRiskGrade(r);
                    }else{
                        log.warn("taskSn=["+t.getSn()+"]��Risk��id="+re.getRiskID()+"�������ڡ��������ݴ���");
                    }
                }
            }
        }else{
            log.warn("taskSn=["+t.getSn()+"]û�ж�Ӧ��RiskEntry����!");
        }
        }
    }
    
	public void updateTimeLimitWithScale235(Long sn,short timeLimit,short scaleValue2,short scaleValue3,short scaleValue5){
		Task t=taskDao.get(sn);
		Assert.notNull(t);
		if(t!=null){
    		t.setTimeLimit(timeLimit);
    		taskDao.update(t);
    		List<RiskEntry> res=t.getRiskEntries();
    		if(res!=null){
    			for( RiskEntry re:res){
    				if("timeUsed".equalsIgnoreCase(re.getObjectAttr())){
    //				    log.debug("׼������scale");
    				    riskService.updateRiskScale(re.getRiskID(), new BigDecimal(timeLimit), (byte)0);
    					riskService.updateRiskScale(re.getRiskID(), new BigDecimal(scaleValue2), (byte)2);
    					riskService.updateRiskScale(re.getRiskID(), new BigDecimal(scaleValue3), (byte)3);
    					riskService.updateRiskScale(re.getRiskID(), new BigDecimal(scaleValue5), (byte)5);
    					
    //					log.debug("׼��checkRiskGrade");
    					Risk r=riskService.getRiskById(re.getRiskID());
    					riskService.checkRiskGrade(r);
    //					log.debug("checkRiskGrade��ִ��");
    				}
    			}
    		}else{
    			log.warn("taskSn=["+t.getSn()+"]û�ж�Ӧ��RiskEntry����!");
    		}
		}
	}
	/**
	 * ��������task��clerkID,��δ����task��clerkName��
	 */
	public void updateTaskClerkIdByTaskIds(final List<Long> ids,final Long clerkID){
		taskDao.updateTaskClerkIdByTaskIds(ids, clerkID);
	}
	
	public void cancelTask(Task t, Date endTime) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task����ȡ����" + t);
//		}
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() 
					+ "��������������ִ�С�ȡ��������ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		
		//short timeUsed = (short) (t.getTimeUsed() - t.getTimeHang() - t.getTimeDelay());
		//t.setTimeUsed(timeUsed);
		// ʵ����ʱ���ܰ�����ͣʱ�����ӳ�ʱ��
		
		
		t.setStatus(Task.TASK_STATUS_CANCEL);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);
		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);

		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.STOP, t));
		}
	}
	public void stopTask(Task t, Date endTime) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task������ֹ��" + t);
//		}
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() 
					+ "��������������ִ�С���ֹ������ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		t.setStatus(Task.TASK_STATUS_STOP);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);
		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.STOP, t));
		}
		
	}
	
	/**
	 * ����task��ʵ����ʱ��һ����ʱ�䡢״̬���ֶ����ú��ٵ��á�
	 * @param task
	 * @return
	 */
//	protected int calculateTimeUsed(Task task){
////		Date beginTime = task.getBeginTime();
//		Date endTime = task.getEndTime();
////		byte timeUnit = task.getTimeUnit();
////		Assert.notNull(beginTime, "��ʼʱ�䲻��Ϊ��");
//		Assert.notNull(endTime, "����ʱ�䲻��Ϊ��");
////		
////		int timeUsed = DateUtil.getTimeUsed(beginTime, endTime, timeUnit);
////		timeUsed = timeUsed - task.getTimeHang();
////		task.setTimeUsed((short) timeUsed);
////		
////		return timeUsed;
//		
//		return calculateTimeUsed(task, endTime);
//	}
	
//	/**
//	 * ����task��ʵ����ʱ��һ����ʱ�䡢״̬���ֶ����ú��ٵ��á�
//	 * 
//	 * @param task
//	 * @param calculateTime ���ڼ����ʱ��
//	 * @return
//	 */
//	protected int calculateTimeUsed(Task task, Date calculateTime){
//		Assert.notNull(calculateTime, "����ָ�����ڼ����ʱ��");
//		Date beginTime = task.getBeginTime();
//		Date hangTime = task.getHangTime();
//		Date endTime = task.getEndTime();
//		byte timeUnit = task.getTimeUnit();
//		byte status = task.getStatus();
//		
//		//����ʱ��ȿ�ʼʱ��С
//		if(calculateTime.before(beginTime)){
//			if(IS_DEBUG_ENABLED){
//				log.debug("���ڼ����ʱ���task��ʼʱ�仹С����������ʱ��");
//			}
//			return 0;
//		}
//		
//		//�������ͣ���Ҽ���ʱ�����ͣʱ�����ȡ��ͣʱ��
//		if(Task.TASK_STATUS_HANG == status && calculateTime.after(hangTime)){
//			if(IS_DEBUG_ENABLED){
//				log.debug("Task��ͣ�Ҽ���ʱ�������ͣʱ�䣬ȡ��ͣʱ����м��㣺" + task);
//			}
//			calculateTime = hangTime;
//		}
//
//		//����Ѿ������Ҽ���ʱ��Ƚ���ʱ�����ȡ����ʱ��
//		if(endTime != null && calculateTime.after(endTime)){
//			if(IS_DEBUG_ENABLED){
//				log.debug("Task�����Ҽ���ʱ����ڽ���ʱ�䣬ȡ����ʱ����м��㣺" + task);
//			}
//			calculateTime = endTime;
//		}
//		
//		int timeUsed = DateUtil.getTimeUsed(beginTime, calculateTime, timeUnit);
//		//ȥ������ͣʱ��
//		timeUsed = timeUsed - task.getTimeHang();
//		task.setTimeUsed((short) timeUsed);
//		return timeUsed;
//	}
	
	public void avoidTask(Task t, Date endTime){
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task�������");
//		}
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() + "��������������ִ�С���족����ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
				
		//short timeUsed = (short) (t.getTimeUsed() - t.getTimeHang() - t.getTimeDelay());
		//t.setTimeUsed(timeUsed);
		// ʵ����ʱ���ܰ�����ͣʱ�����ӳ�ʱ��
		
		t.setStatus(Task.TASK_STATUS_AVOID);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//ɾ��Riskʱ��ı�t����
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);
		
		taskDao.update(t);
		
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.AVOID, t));
		}
	}
	
	public List<Task> findTaskByObjectIDandVisibilitys(Long objectId,int visibility) {
		return taskDao.findTaskByObjectIDandVisibilitys(objectId, visibility);
	}
	
	public Task createTaskNotWithRisk(Task task, Long objectId, short dutyerType, Long dutyerID){
		Assert.notNull(task);
		Assert.notNull(task.getType());
		Assert.notNull(task.getClerkID());
		int type = task.getType();
		String typeName= getTaskName(type);//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_TASKTYPE, type);
		if(StringUtils.isNotBlank(typeName)){
			task.setName(typeName);
		}
		
		if(task.getSn() == null){
			task.setSn(generateId());
		}
		
		if(task.getCreator() == null){
			task.setCreator(task.getClerkID());
		}
		if(task.getBeginTime() == null){
			task.setBeginTime(new Date());
		}
		
//		task.setCreationTime(new Date());
		if(task.getStatus()==0){
			task.setStatus(Task.TASK_STATUS_WORK);
		}
		task.setDutyerID(dutyerID);
		task.setDutyerType(dutyerType);

		/***
		 * Ϊ���� Task ��ӷ��ռ��,���ʱ�� 
		 */
        
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "timeUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "timeUsed", scaleId);
			task.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "hangUsed", scaleId);
			task.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "delayUsed", scaleId);
			task.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(task, "hangTimes", scaleId);
			task.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(task, "delayTimes", scaleId);
			task.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		
		
		if(StringUtils.isBlank(task.getCode())){
			String code = codeGeneratorProvider.generateCode(task);
			task.setCode(code);
		}
		
		taskDao.save(task);
		
		if (objectId != null) {
			createObjectTask(objectId, task.getSn());
		}
		return task;
	}
	
	public Task createTaskWithTrack(Task task,Long[] matterIds,Long objectId, short dutyerType, Long dutyerID){
		Assert.notNull(task);
		Assert.notNull(task.getType());
		Assert.notNull(task.getClerkID());
		int type = task.getType();
		String typeName= getTaskName(type);//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_TASKTYPE, type);
		if(StringUtils.isNotBlank(typeName)){
			task.setName(typeName);
		}
		
		if(task.getSn() == null){
			task.setSn(generateId());
		}
		
		
		if(task.getCreator() == null){
			task.setCreator(task.getClerkID());
		}
		if(task.getBeginTime() == null){
			task.setBeginTime(new Date());
		}
		
//		task.setCreationTime(new Date());
		if(task.getStatus()==0){
			task.setStatus(Task.TASK_STATUS_WORK);
		}
		task.setDutyerID(dutyerID);
		task.setDutyerType(dutyerType);

		/***
		 * Ϊ���� Task ��ӷ��ռ��,���ʱ�� 
		 */
        
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "timeUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "timeUsed", scaleId);
			task.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "hangUsed", scaleId);
			task.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "delayUsed", scaleId);
			task.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(task, "hangTimes", scaleId);
			task.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(task, "delayTimes", scaleId);
			task.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		
		if (matterIds != null && matterIds.length > 0) {
			/***
			 * 1.�޸� TimeLimits ���,�޸�ʱ�� 2008/12/14 start ymq
			 * 2.����matter��taskmatter��ȥ,�޸�ʱ�� 2008/03/01 by ymq
			 */
			Matter matter;
			TaskMatter taskMatter;
			for (Long matterId : matterIds) {
				//�˴�����matterIds ������
				matter = matterDao.get(matterId);
				taskMatter = new TaskMatter();
				taskMatter.setTaskSN(task.getSn());
				taskMatter.setMatterID(matterId);
				if (matter != null) {
					taskMatter.setMatterCode(matter.getCode());
					taskMatter.setMatterName(matter.getName());
				}
				taskMatterDao.save(taskMatter);
				Long taskTrack = createTaskTrack(task.getSn(), type, matterId);
				if(taskTrack!=null){
					task.setBizTrack(taskTrack);
				}
				clueService.createClue(objectId, CATEGORY_TASK, task.getType(), matterId, task.getSn(), task.getBizTrack());
			}
		}
		
		if(StringUtils.isBlank(task.getCode())){
			String code = codeGeneratorProvider.generateCode(task);
			task.setCode(code);
		}
		
		taskDao.save(task);
		
		if (objectId != null) {
			createObjectTask(objectId, task.getSn());
		}
		return task;
	}
	
	public List<Task> getTaskAllVisibility(Long objectID, int taskType){
		return taskDao.findTaskAllVisibility(objectID, taskType);
	}
	
	public Task makeUpTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID){
		Assert.notNull(task);
		Assert.notNull(task.getType());
		//Assert.notNull(task.getClerkID());
		int type = task.getType();
		String typeName= getTaskName(type);//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_TASKTYPE, type);
		if(StringUtils.isNotBlank(typeName)){
			task.setName(typeName);
		}
		
		if(task.getSn() == null){
			task.setSn(generateId());
		}
		if(task.getVisibility()==0){
			task.setVisibility(Task.VISIBILITY_GENERAL);
		}
		
		
		if(task.getCreator() == null){
			//task.setClerkID(clerkId);
			task.setCreator(task.getClerkID());
		}
		if(task.getBeginTime() == null){
			task.setBeginTime(new Date());
		}
//		if(task.getCreationTime()==null){
//			task.setCreationTime(new Date());
//		}
		if(task.getStatus()==0){
			task.setStatus(Task.TASK_STATUS_WORK);
		}
		task.setDutyerID(dutyerID);
		task.setDutyerType(dutyerType);
		
		//Ӧ���ڴ���risk֮ǰ����code
		if(StringUtils.isBlank(task.getCode())){
			String code = codeGeneratorProvider.generateCode(task);
			task.setCode(code);
		}

		/***
		 * Ϊ���� Task ��ӷ��ռ��,���ʱ�� 2008/12/14 start ymq
		 */
        
		RFSObject object = objectService.getObject(objectId);
		
		List<RiskEntry> entries = riskService.createRisk(task, task.getType(), dutyerID, object);
		task.setRiskEntries(entries);
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "timeUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "timeUsed", scaleId);
			task.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "hangUsed", scaleId);
			task.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayUsed");
		if(valueUnit != null) {
			task.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(task, "delayUsed", scaleId);
			task.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(task, "hangTimes", scaleId);
			task.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(task, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(task, "delayTimes", scaleId);
			task.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		
		/******************************************** end ymq ***/
		
		if (matterIds != null && matterIds.length > 0) {
			
			/***
			 * 1.�޸� TimeLimits ���,�޸�ʱ�� 2008/12/14 start ymq
			 * 2.����matter��taskmatter��ȥ,�޸�ʱ�� 2008/03/01 by ymq
			 */
			Matter matter;
			TaskMatter taskMatter;
			for (Long matterId : matterIds) {
				//�˴�����matterIds ������
				matter = matterDao.get(matterId);
				taskMatter = new TaskMatter();
				taskMatter.setTaskSN(task.getSn());
				taskMatter.setMatterID(matterId);
				if (matter != null) {
					taskMatter.setMatterCode(matter.getCode());
					taskMatter.setMatterName(matter.getName());
				}
				taskMatterDao.save(taskMatter);
				Long taskTrack = createTaskTrack(task.getSn(), type, matterId);
				if(taskTrack!=null){
					task.setBizTrack(taskTrack);
				}
				clueService.createClue(objectId, CATEGORY_TASK, task.getType(), matterId, task.getSn(), task.getBizTrack());
			}
		
			
//			for (int i = 0; i < matterIds.length; i++) {
//				setTimeLimits(task, matterIds[i]);
//			}
//			//�˴�����matterIds ������
//			Long taskTrack = createTaskTrack(task.getSn(), type, matterIds);
//			if(taskTrack!=null){
//				task.setBizTrack(taskTrack);
//			}
		}
		
		
		
		taskDao.save(task);

		if (objectId != null) {
			//if (matterIds != null && matterIds.length > 0) {
			//	attachToJobs(type, task.getSn(), objectId, matterIds);
			//}
			createObjectTask(objectId, task.getSn());
		}
		for(RiskEntry re:entries){
			Risk r=riskService.getRiskById(re.getRiskID());
			riskService.sendRiskLogEvent(r,task,RiskLogEvent.START);
			r.setCreationTime(task.getBeginTime());
			r.setModificationTime(task.getBeginTime());
			riskService.checkRiskGrade(r);
		}
		return task;
	}
	
	public void updateTaskDutyerIDByTaskId(Long sn,short dutyerType,Long dutyerID){
		Task t=taskDao.get(sn);
		if(t!=null){
			if(dutyerID==t.getDutyerID()&&dutyerType==t.getDutyerType()){
			}else{
				t.setDutyerType(dutyerType);
				t.setDutyerID(dutyerID);
				taskDao.update(t);
				List<RiskEntry> res=t.getRiskEntries();
				if(res!=null){
					for( RiskEntry re:res){
						riskService.updateRiskDutyer(re.getRiskID(), dutyerID);
					}
				}
			}
		}
	}
	
	public void updateTaskBeginTimeByTaskId(Long sn,Date beginTime){
		Task t=taskDao.get(sn);
		if(t!=null){	
				t.setBeginTime(beginTime);
				taskDao.update(t);
				List<RiskEntry> res=t.getRiskEntries();
				if(res!=null){
					for( RiskEntry re:res){
						Risk r=riskService.getRiskById(re.getRiskID());
						r.setValue(null);
						r.setGrade((byte)0);
						r.setCreationTime(beginTime);
						r.setModificationTime(beginTime);
						riskService.checkRiskGrade(r);
					}
				}
		}
	}
	public void updateBusiTimeByTaskSn(Long sn,Date startTime,Date endTime){
		Task t=taskDao.get(sn);
		if(t!=null){	
			if(startTime!=null){
				t.setBusiStartTime(startTime);
				//taskDao.save(t);
				List<RiskEntry> res=t.getRiskEntries();
				if(res!=null){
					for( RiskEntry re:res){
						Risk r=riskService.getRiskById(re.getRiskID());
						r.setValue(r.getInitValue());
						r.setGrade((byte)0);
						r.setModificationTime(startTime);
						riskService.checkRiskGrade(r);
					}
				}
			}
			if(endTime!=null){
				t.setBusiEndTime(endTime);
			}
			taskDao.save(t);
		}
		
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#findTaskWithAttachmentCount(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable
	public List<Task> findTaskWithAttachmentCount(ResultFilter filter) {
		List<Task> list = filter != null ? taskDao.find(filter) : taskDao.find();
		for(Task task: list){
			ResultFilter f2 = ResultFilter.createEmptyResultFilter();
			Logic logic = Restrictions.logic(Restrictions.eq("type", Objects.TYPE_�����븽��֮���ϵ))
				.and(Restrictions.eq("fstObject", task.getSn()));
			f2.setCriterion(logic);
			int count = objectsDao.getCount(f2);
			task.setAttachmentCount(count);
		}
		return list;
	}
	public List<Task> findTasks(ResultFilter filter) {
		List<Task> list =filter !=null ? taskDao.find(filter): taskDao.find();
		return list;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#getLastTask(java.lang.Long, short, short)
	 */
	public Task getLastTask(Long objectId, int objectType, int taskType) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("creationTime"));
		SimpleExpression c1 = Restrictions.eq("refObjectId", objectId);
		SimpleExpression c2 = Restrictions.eq("refObjectType", objectType);
		SimpleExpression c3 = Restrictions.eq("type", taskType);
		Logic logic = Restrictions.logic(c1).and(c2).and(c3);
		filter.setCriterion(logic);
		filter.setFirstResult(0);
		filter.setMaxResults(1);
		List<Task> list = taskDao.find(filter);
		if(!list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#getLastTask(java.lang.Long, short, short, byte)
	 */
	public Task getLastTask(Long objectId, int objectType, int taskType, byte status) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("creationTime"));
		SimpleExpression c1 = Restrictions.eq("refObjectId", objectId);
		SimpleExpression c2 = Restrictions.eq("refObjectType", objectType);
		SimpleExpression c3 = Restrictions.eq("type", taskType);
		SimpleExpression c4 = Restrictions.eq("status", status);
		Logic logic = Restrictions.logic(c1).and(c2).and(c3).and(c4);
		filter.setCriterion(logic);
		filter.setFirstResult(0);
		filter.setMaxResults(1);
		List<Task> list = taskDao.find(filter);
		if(!list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#findTasks(cn.redflagsoft.base.bean.RFSObjectable, java.lang.Short, java.lang.Integer, java.lang.Byte)
	 */
	public List<Task> findTasks(RFSObjectable object, Integer type, Integer visibility, Byte status) {
		Assert.notNull(object, "������Ϊ��");
		Assert.notNull(object.getId(), "����ID����Ϊ��");

		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("creationTime"));
		SimpleExpression c1 = Restrictions.eq("refObjectId", object.getId());
		SimpleExpression c2 = Restrictions.eq("refObjectType", object.getObjectType());
		Logic logic = Restrictions.logic(c1).and(c2);
		
		if(type != null){
			logic.and(Restrictions.eq("type", type));
		}
		if(status != null){
			logic.and(Restrictions.eq("status", status));
		}
		if(visibility != null){
			logic.and(Restrictions.eq("visibility", visibility));
		}
		filter.setCriterion(logic);
		return taskDao.find(filter);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#findTasks(cn.redflagsoft.base.bean.RFSItemable, java.lang.Short, java.lang.Integer, java.lang.Byte)
	 */
	public List<Task> findTasks(RFSItemable object, Integer type, Integer visibility, Byte status) {
		Assert.notNull(object, "������Ϊ��");
		Assert.notNull(object.getId(), "����ID����Ϊ��");
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("t.creationTime"));
		SimpleExpression c1 = Restrictions.eq("e.objectId", object.getId());
		SimpleExpression c2 = Restrictions.eq("e.objectType", object.getObjectType());
		Logic logic = Restrictions.logic(c1).and(c2);
		
		if(type != null){
			logic.and(Restrictions.eq("t.type", type));
		}
		if(status != null){
			logic.and(Restrictions.eq("t.status", status));
		}
		if(visibility != null){
			logic.and(Restrictions.eq("t.visibility", visibility));
		}
		
		filter.setCriterion(logic);
		List<Task> list1 = entityObjectToWorkDao.findTasks(filter);
		List<Task> list2 = entityObjectToTaskDao.findTasks(filter);

		Set<Task> tasks = new HashSet<Task>();
		if(!list1.isEmpty()){
			tasks.addAll(list1);
		}
		if(!list2.isEmpty()){
			tasks.addAll(list2);
		}
		ArrayList<Task> list = new ArrayList<Task>(tasks);
//		Collections.sort(list, new Comparator<Task>(){
//			public int compare(Task o1, Task o2) {
//				if(o1.getCreationTime() == null){
//					o1.setCreationTime(new Date(0L));
//				}
//				if(o2.getCreationTime() == null){
//					o2.setCreationTime(new Date(0L));
//				}
//				return o2.getCreationTime().compareTo(o1.getCreationTime());
//			}});
		Collections.sort(list, new CreationTimeDescComparator());
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#updateTaskEntityObjects(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.RFSEntityObject[])
	 */
	public Task updateTaskEntityObjects(Task task, RFSEntityObject... objects) {
		long taskSN = task.getSn();
		for(RFSEntityObject obj: objects){
			saveEntityObjectToTaskIfRequired(taskSN, obj.getId(), /*(obj instanceof RFSObjectable) ? 0: */obj.getObjectType());
		}
		return task;
	}
	
	protected EntityObjectToTask saveEntityObjectToTaskIfRequired(long taskSN, long objectId, int objectType) {
		Logic logic = Restrictions.logic(Restrictions.eq("taskSN", taskSN))
				.and(Restrictions.eq("objectId", objectId))
				.and(Restrictions.eq("objectType", objectType));
		ResultFilter filter = new ResultFilter(logic, null);
		int count = entityObjectToTaskDao.getCount(filter);
		if(count == 0){
			EntityObjectToTask objectTask = new EntityObjectToTask(taskSN, objectId, objectType);
			return entityObjectToTaskDao.save(objectTask);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#hangTask(java.lang.Long)
	 */
	public void hangTask(Long taskSn) {
		hangTask(taskDao.get(taskSn), null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#wakeTask(java.lang.Long)
	 */
	public void wakeTask(Long taskSn) {
		wakeTask(taskDao.get(taskSn), null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#terminateTask(java.lang.Long, java.lang.Byte)
	 */
	public void terminateTask(Long tasksn, Byte result) {
		terminateTask(taskDao.get(tasksn), result, null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#cancelTask(cn.redflagsoft.base.bean.Task)
	 */
	public void cancelTask(Task t) {
		cancelTask(t, null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#stopTask(cn.redflagsoft.base.bean.Task)
	 */
	public void stopTask(Task t) {
		stopTask(t, null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#terminateTask(cn.redflagsoft.base.bean.Task)
	 */
	public void terminateTask(Task t) {
		terminateTask(t, (Date)null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#avoidTask(cn.redflagsoft.base.bean.Task)
	 */
	public void avoidTask(Task task) {
		avoidTask(task, null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#withdrawTask(cn.redflagsoft.base.bean.Task, java.util.Date)
	 */
	public void withdrawTask(Task t, Date endTime) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task���ܳ��أ�" + t);
//		}
		
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() 
					+ "��������������ִ�С����ء�����ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		t.setStatus(Task.TASK_STATUS_WITHDRAW);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);

		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		//ͨ��ִ��withdrawTaskǰ���������Ӧ��workScheme�����work�Ľ���ͨ���Ѿ����á�
//		List<Work> works = workService.findWorkByTaskSN(t.getSn());
//		for(Work work: works){
//			workService.terminateWork(work, (byte)0, endTime);
//		}
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.WITHDRAW, t));
		}
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#rejectTask(cn.redflagsoft.base.bean.Task, java.util.Date)
	 */
	public void rejectTask(Task t, Date endTime) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task���ܲ��أ�" + t);
//		}
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() 
					+ "��������������ִ�С����ء�����ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		t.setStatus(Task.TASK_STATUS_REJECT);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);
		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.REJECT, t));
		}
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#undoTask(cn.redflagsoft.base.bean.Task, java.util.Date)
	 */
	public void undoTask(Task t, Date time) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task���ܳ�����" + t);
//		}
		
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, time);
		}
		//t.setEndTime(endTime != null ? endTime : new Date());
		
		//short timeUsed = (short) (t.getTimeUsed() - t.getTimeHang() - t.getTimeDelay());
		//t.setTimeUsed(timeUsed);
		// ʵ����ʱ���ܰ�����ͣʱ�����ӳ�ʱ��
		
		//calculateTimeUsed(t);
		
		
		//TODO �����ľ������δ������ʱ��״̬��0
		if(time == null){
			time = new Date();
		}
		//��ʱ����
		t.setTimeUsed((short) 0);
		t.setTimeUsedPosition(time);
		t.setBeginTime(time);
		t.setEndTime(null);
		t.setTimeHang((short) 0);
		t.setTimeDelay((short) 0);
		
		
		t.setStatus(Task.TASK_STATUS_OK);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//TODO �Ƿ�Ҫɾ��RISK��
		//riskService.deleteRisk(t);
		terminateRisks(t, time);
		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.UNDO, t));
		}
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#transferTask(cn.redflagsoft.base.bean.Task, java.util.Date)
	 */
	public void transferTask(Task t, Date endTime) {
//		if(t.getStatus() != Task.STATUS_�ڰ�){
//			throw new IllegalArgumentException("ֻ���ڰ�״̬��Task����ת����" + t);
//		}
		
		if(t.getStatus() == Task.STATUS_��ͣ){
			//������
			wakeTask(t, endTime);
		}
		
		if(isEnd(t)){
			log.warn("Task��" + t.getSn() + "���Ѿ��ڡ�" + t.getEndTime() + "����������ǰ״̬�ǣ�" + t.getStatusName());
			return;
		}
		calculateTaskTimeUsedAndSetEndTime(t, endTime);
		
		t.setStatus(Task.TASK_STATUS_TRANSFER);		
		//	����CLUE״̬
		clueService.updateClueStatus((byte)2, t.getSn(), Task.TASK_STATUS_TERMINATE);		
		
		//riskService.deleteRisk(t);
		terminateRisks(t, endTime);
		
		taskDao.update(t);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_TASK, t.getSn(), Task.TASK_STATUS_TERMINATE);
		
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.TRANSFER, t));
		}
	}
	
	/**
	 * ��������ѯ
	 * @param filter
	 * @return
	 */
	@Queryable
	public PageableList<Map<String,Object>> findTaskMyWork(ResultFilter filter) {
		
		List<Task> taskList = taskDao.find(filter);
		int count = taskDao.getCount(filter);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Task task:taskList) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sn", task.getSn());
			map.put("type", task.getType());
			map.put("name", task.getName());
			map.put("code", task.getCode());
			map.put("statusName", task.getStatusName());
			map.put("surplus", task.getSurplus());
			map.put("beginTime", task.getBeginTime());
			map.put("endTime", task.getEndTime());
			map.put("abbr", task.getRefObjectName());
			map.put("clerkName", task.getClerkName());
			map.put("objectType", task.getRefObjectType());
			map.put("id", task.getRefObjectId());
			map.put("objectId", task.getRefObjectId());
			map.put("safeTaskSN",task.getSn());
			map.put("status", task.getStatus());
			
			ResultFilter filter1 = ResultFilter.createEmptyResultFilter();
			SimpleExpression c1 = Restrictions.eq("objectID", task.getSn());
			SimpleExpression c2 = Restrictions.eq("objectType", 102);
			SimpleExpression c3 = Restrictions.eq("type", 1);
			
			filter1.setOrder(Order.asc("creationTime"));
			filter1.setCriterion(Restrictions.logic(c1).and(c2).and(c3));
			
			List<Risk> riskList = riskService.findRisks(filter1);
			if(!riskList.isEmpty()){
				map.put("riskStatusName", riskList.get(0).getStatusName());
				map.put("riskStatus", riskList.get(0).getStatus());
				map.put("grade", riskList.get(0).getGrade());
				map.put("gradeName", riskList.get(0).getGradeName());
			}
			
			list.add(map);
		}
		return new PageableList<Map<String,Object>>(list, filter.getFirstResult(), filter.getMaxResults(), count);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#getTaskCount(org.opoo.ndao.support.ResultFilter)
	 */
	public int getTaskCount(ResultFilter filter) {
		return taskDao.getCount(filter);
	}
	/**
	 * @return the taskDao
	 */
	public TaskDao getTaskDao() {
		return taskDao;
	}
	
	/**
	 * ����task����ʱ����ʱ������������EndTime�����ý���״̬֮ǰ���㡣
	 * task����ʱ��timeUsed��1��
	 * @param task
	 * @param updateIfChanged
	 * @return
	 */
	protected void calculateTaskTimeUsedAndSetEndTime(Task task, Date endTime){
		if(IS_DEBUG_ENABLED){
			log.debug("calculateTaskTimeUsedAndSetEndTime: " + task 
					+ " BeginTime:" + task.getBeginTime()
					+ ", CreationTime:" + task.getCreationTime() 
					+ ", EndTime:" + task.getEndTime());
		}
		if(Task.TASK_STATUS_TERMINATE == task.getStatus()){
			throw new IllegalArgumentException("task�Ѿ�����������Ӧ��������status֮ǰ����timeUsed��" + task);
		}
		Assert.isNull(task.getEndTime(), "����������EndTime֮ǰ����: " + task);
		//Assert.notNull(endTime, "����ʱ�䲻��Ϊ��");
		if(endTime == null){
			endTime = new Date();
		}
		calculateTaskTimeUsed(task, endTime, false);
		
		//����ʱӦ�ö��1��
		task.setTimeUsed((short) (task.getTimeUsed() + 1));
		task.setEndTime(endTime);
	}
	
	/**
	 * ������ʱ��
	 * ��ͣʱӦ���ȵ��ø÷������㣬�ٸ��޸�״̬��
	 * 
	 * @param task
	 * @param calculateTime
	 * @param updateIfChanged
	 * @return timeUsed
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	protected short calculateTaskTimeUsed(Task task, Date calculateTime, boolean updateIfChanged){
		if(task.getBeginTime() == null){
			log.warn("��task�Ŀ�ʼʱ��Ϊ�գ��޷����㣺" + task);
			return -1;
		}
		
		if(calculateTime == null){
			calculateTime = new Date();
		}
		
		//����ʱ��ȿ�ʼʱ�仹С��������
		if(!calculateTime.after(task.getBeginTime())){
			log.warn("���ڼ����ʱ���task��ʼʱ�仹С��������Task��");
			return -1;
		}
		
		byte status = task.getStatus();
		if(Task.TASK_STATUS_WORK == status){
			return calculateRunningTaskTimeUsed(task, calculateTime, updateIfChanged);
		}else if(Task.TASK_STATUS_HANG == status){
			return calculateHangTaskTimeUsed(task, calculateTime, updateIfChanged);
		}
		
		log.warn("��ǰ״̬��task��Ӧ�ò������㣺" + status);
		return -1;
	}
	
	/**
	 * �����������е�Task������ʱ��
	 * 
	 * @param task
	 * @param calculateTime
	 * @param updateIfChanged
	 * @return
	 */
	private short calculateRunningTaskTimeUsed(Task task, Date calculateTime, boolean updateIfChanged) {
		/*Date timeUsedPosition = task.getTimeUsedPosition();
		if(timeUsedPosition == null){
			timeUsedPosition = task.getBeginTime();
		}
		
		if(calculateTime.before(timeUsedPosition)){
			log.warn("���ڼ����ʱ���task�ļ���ָ�뻹С������ָ��Ϊtask��ʼʱ�䡣");
			timeUsedPosition = task.getBeginTime();//ָ��
			task.setTimeUsed((short)(-task.getTimeHang()));//����ʱ
			//���������
		}
		
		int delta = DateUtil.getTimeUsed(timeUsedPosition, calculateTime, task.getTimeUnit());
		
		if(delta != 0){
			timeUsedPosition = DateUtil.getDate(timeUsedPosition, delta, task.getTimeUnit(), false);
			task.setTimeUsedPosition(timeUsedPosition);
			task.setTimeUsed((short) (task.getTimeUsed() + delta));
			task.setTimeUsedChangedTime(calculateTime);
			task.setTimeUsedCalculateTime(calculateTime);
			
		}else{
			task.setTimeUsedCalculateTime(calculateTime);
		}

		if(updateIfChanged){
			taskDao.update(task);
		}*/
		
		
		//ÿ�ζ����¼��㣬������ָ��
		int timeUsed = DateUtil.getTimeUsed(task.getBeginTime(), calculateTime, task.getTimeUnit(), includeFirstDay(task)) - task.getTimeHang();
		int delta = timeUsed - task.getTimeUsed();
		if(delta != 0){
			task.setTimeUsed((short)timeUsed);
			task.setTimeUsedChangedTime(calculateTime);
		}
		task.setTimeUsedCalculateTime(calculateTime);

		if(updateIfChanged){
			taskDao.update(task);
		}
		return task.getTimeUsed();
	}
	
	/**
	 * ������ͣ�ڼ����ͣʱ�䡣
	 * @param task
	 * @param calculateTime
	 * @param updateIfChanged
	 * @return
	 */
	private short calculateHangTaskTimeUsed(Task task, Date calculateTime, boolean updateIfChanged) {
		//���ڼ����ʱ�����ͣʱ�仹С������ͣʱ��϶�Ϊ0��ֻ��������ʱ���ɡ�
		if(calculateTime.before(task.getHangTime())){
			log.warn("���ڼ����ʱ�䲻����ͣ�ڼ䣬���¼���timeUsed��");
			return calculateRunningTaskTimeUsed(task, calculateTime, updateIfChanged);
		}
		
		//����δ��ʼ��������ϵͳ��������󣬱������¼�����ʱ����ʱ����
		//task��״̬ʱ��ͣ��������ʱΪ0��
		if(task.getTimeUsed() <= 0){
			calculateRunningTaskTimeUsed(task, task.getHangTime(), false);
		}else{
			// >0 ʱ����Ϊ��ͣʱ�Ѿ�������ˣ��������㣬���task���ǵ��÷�����ͣ������ֱ���޸�״̬��ͣ
			// ������ʱ��������
		}
        
		int currentTimeHang = DateUtil.getTimeUsed(task.getHangTime(), calculateTime, task.getTimeUnit(), includeFirstDay(task));
		int currentTimeHangDelta = currentTimeHang - task.getCurrentTimeHang();
		
		if(currentTimeHangDelta != 0){
			task.setCurrentTimeHang((short) currentTimeHang);
		}
		task.setTimeUsedCalculateTime(calculateTime);
		
		if(updateIfChanged){
			taskDao.update(task);
		}
		return task.getTimeUsed();
	}
	
	/**
	 * @param task
	 * @param calculateTime
	 * @param updateIfChanged
	 * @return
	 * @deprecated
	 */
	protected int calculateTaskTimeUsed_20120607(Task task, Date calculateTime, boolean updateIfChanged){
		if(task.getBeginTime() == null){
			log.error("��task�Ŀ�ʼʱ��Ϊ�գ��޷����㣺" + task);
			return 0;
		}
		
		byte status = task.getStatus();
		
		//������undo���ģ�ֱ�ӷ���0
		if(Task.TASK_STATUS_OK == status){
			if(IS_DEBUG_ENABLED){
				log.debug("״̬Ϊ0������0");
			}
			return 0;
		}
		
		//�Ѿ���������ͣ��
		/*if (Task.TASK_STATUS_AVOID == status
				|| Task.TASK_STATUS_CANCEL == status
				|| Task.TASK_STATUS_HANG == status
				|| Task.TASK_STATUS_REJECT == status
				|| Task.TASK_STATUS_STOP == status
				|| Task.TASK_STATUS_TERMINATE == status
				|| Task.TASK_STATUS_TRANSFER == status
				|| Task.TASK_STATUS_WITHDRAW == status) {
		*/
		if(task.getEndTime() != null || Task.TASK_STATUS_HANG == status){
			if(task.getTimeUsed() > 0){
				if(IS_DEBUG_ENABLED){
					log.debug("�Ż��㷨��Task�Ѿ��������ߴ�����ͣ״̬��ֱ�ӷ���timeUsedֵ��Ϊrisk��valueֵ��" + task);
				}
				return task.getTimeUsed();
			}else{
				if(Task.TASK_STATUS_HANG == status){
					if(IS_DEBUG_ENABLED){
						log.debug("��task֮ǰ����û�м����timeusedֵ����Ȼ������ͣ״̬������Ȼ����һ�Σ�ʹ����ͣʱ����Ϊ����ʱ�䣺" + task.getHangTime());
					}
					calculateTime = task.getHangTime();
				}
			}
		}
		
		//��ͣʱ������Ҫ����
		if(Task.TASK_STATUS_WORK != status && Task.TASK_STATUS_HANG != status){
			//״̬�����ڰ�ģ�
			//return new BigDecimal(task.getTimeUsed());
			throw new IllegalStateException("Task״̬δ֪�������㣺" + task);
		}
		
		
		if(calculateTime == null){
			calculateTime = new Date();
		}
		
		//����ʱ��ȿ�ʼʱ�仹С��������
		if(calculateTime.before(task.getBeginTime())){
			if(IS_DEBUG_ENABLED){
				log.debug("���ڼ����ʱ���task��ʼʱ�仹С����������ʱ��");
			}
			return 0;
		}
		
		Date timeUsedPosition = task.getTimeUsedPosition();
		if(timeUsedPosition == null){
			timeUsedPosition = task.getBeginTime();
		}
		//begintime������ֵ
//		if(timeUsedPosition == null){
//			timeUsedPosition = task.getCreationTime();
//		}
		
		int delta = DateUtil.getTimeUsed(timeUsedPosition, calculateTime, task.getTimeUnit(), includeFirstDay(task));
		
		
		
		if(delta > 0){
			timeUsedPosition = DateUtil.getDate(timeUsedPosition, delta, task.getTimeUnit(), false);
			task.setTimeUsedPosition(timeUsedPosition);
			task.setTimeUsed((short) (task.getTimeUsed() + delta));
			task.setTimeUsedChangedTime(calculateTime);
			task.setTimeUsedCalculateTime(calculateTime);
			
			if(updateIfChanged){
				taskDao.update(task);
			}
		}else{
			task.setTimeUsedCalculateTime(calculateTime);

			if(updateIfChanged){
				taskDao.update(task);
			}
		}
		return delta;//task.getTimeUsed();
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#calculateTaskTimeUsed(cn.redflagsoft.base.bean.Task, java.util.Date)
	 */
	public int calculateTaskTimeUsed(Task task, Date calculateTime) {
		return calculateTaskTimeUsed(task, calculateTime, true);
	}
	
	
	public int calculateAllRunningTasksTimeUsedInThreads(Date calculateTime){
		if(calculateTime == null){
			calculateTime = new Date();
		}
		final Date time = calculateTime;
		
		//ʹ���Ż��㷨ʱ������ϴ�����ʱ��ͱ�������ʱ����ͬһ�죬���������ⷽ����
		//��ʹ���Ż�ʱ��ÿ�ζ����С�
		//�Ż�����������ϵͳ����ʱ�����У�Ĭ��ֵtrue��
		//calculateAllRunningTasksTimeUsed.optimized=true
		boolean optimized = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_OPTIMIZED_KEY, true);
		if(optimized && DateUtil.isSameDay(time, lastCalculateTime)){
			log.info("ͬһ���Ѿ����й�������ִ��calculateAllRunningTasksTimeUsed()���ϴ�ִ��ʱ�䣺" + lastCalculateTime);
			return 0;
		}
		
		SimpleExpression c = Restrictions.eq("status", Task.TASK_STATUS_WORK);
		ResultFilter filter = new ResultFilter(c, Order.asc("sn"));
		List<Long> ids = taskDao.findIds(filter);
		
		//�������ʱҪ������ͣ��task�������ø�����Ϊtrue��Ĭ��false
		if(AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_INCLUDE_HANG_TASKS_KEY, false)){
			SimpleExpression c2 = Restrictions.eq("status", Task.TASK_STATUS_HANG);
			ResultFilter filter2 = new ResultFilter(c2, Order.asc("sn"));
			List<Long> ids2 = taskDao.findIds(filter2);
			
			if(!ids2.isEmpty()){
				ids = Lists.newArrayList(ids);
				ids.addAll(ids2);
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("���ι������task��" + ids.size());
		}
		if(ids.isEmpty()){
			log.info("������㡣");
			return 0;
		}
		
		
		int result = calculateInThreads(ids, time);
		
		//������û�г������������һ������ʱ��Ϊtime
		lastCalculateTime = time;
		if(IS_DEBUG_ENABLED){
			log.debug("[TaskServiceImpl.calculateAllRunningTasksTimeUsed()]�������� " + result + "������ʱ " + (System.currentTimeMillis() - time.getTime()) + " ���롣");
		}
		return result;
	}
	
	
	private int calculateInThreads(List<Long> ids, final Date time){
		class Result{
			Task task; 
			int delta;
			Result(Task t, int delta){this.task = t;this.delta = delta;}
		};
		
		int nThreads = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_THREADS_KEY, 10);
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
		ExecutorCompletionService<Result> service = new ExecutorCompletionService<Result>(executorService);
		
		for(final Long sn: ids){
			service.submit(new Callable<Result>() {
				public Result call() throws Exception {
					Task object = taskDao.get(sn);
					int delta = calculateTaskTimeUsed(object, time, true);
					return new Result(object, delta);
				}
			});
		}
		
		int result = 0;
		try{
			for(int i = 0, n = ids.size() ; i < n ; i++){
				Result r = service.take().get();
				if(log.isDebugEnabled()){
					log.debug("��ȡ�� task(sn=" + r.task.getSn() + ")�ļ�����: " + r.delta);
				}
				if(r != null && r.delta != 0){
					result++;
				}
			}
		}catch(Throwable e){
			log.error("calculateInThreads ��ȡ������ʱ����", e);
		}
		
		executorService.shutdown();
		executorService = null;//GC
		
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#calculateAllRunningTasksTimeUsed(java.util.Date)
	 */
	public int calculateAllRunningTasksTimeUsed(Date calculateTime) {
		if(calculateTime == null){
			calculateTime = new Date();
		}
		final Date time = calculateTime;
		
		//ʹ���Ż��㷨ʱ������ϴ�����ʱ��ͱ�������ʱ����ͬһ�죬���������ⷽ����
		//��ʹ���Ż�ʱ��ÿ�ζ����С�
		//�Ż�����������ϵͳ����ʱ�����У�Ĭ��ֵtrue��
		//calculateAllRunningTasksTimeUsed.optimized=true
		boolean optimized = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_OPTIMIZED_KEY, true);
		if(optimized && DateUtil.isSameDay(time, lastCalculateTime)){
			log.info("ͬһ���Ѿ����й�������ִ��calculateAllRunningTasksTimeUsed()���ϴ�ִ��ʱ�䣺" + lastCalculateTime);
			return 0;
		}
		
		ObjectFinder<Task> finder = new ObjectFinder<Task>() {
			public int getObjectCount(ResultFilter rf) {
				return taskDao.getCount(rf);
			}
			public List<Task> findObjects(ResultFilter rf) {
				return taskDao.find(rf);
			}
		};
		
		ObjectHandler<Task, Task> handler = new ObjectHandler<Task, Task>(){
			public Task handle(Task object) {
				int delta = calculateTaskTimeUsed(object, time, true);
				return delta != 0 ? object: null;
			}
		};
		
		//ֻ�������ڰ��task��ÿ��10��
		SimpleExpression c = Restrictions.eq("status", Task.TASK_STATUS_WORK);
		ResultFilter filter = new ResultFilter(c, Order.asc("sn"), 0, 20);
		int objects = BatchHelper.batchHandleObject(finder, handler, filter, false);
		
		//�������ʱҪ������ͣ��task�������ø�����Ϊtrue��Ĭ��false
		if(AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_TASKS_TIMEUSED_INCLUDE_HANG_TASKS_KEY, false)){
			SimpleExpression c2 = Restrictions.eq("status", Task.TASK_STATUS_HANG);
			ResultFilter filter2 = new ResultFilter(c2, Order.asc("sn"), 0, 20);
			objects += BatchHelper.batchHandleObject(finder, handler, filter2, false);
		}
		
		//������û�г������������һ������ʱ��Ϊtime
		lastCalculateTime = time;
		if(IS_DEBUG_ENABLED){
			log.debug("[TaskServiceImpl.calculateAllRunningTasksTimeUsed()]�������� " + objects + "����");
		}
		return objects;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#getLastCalculateAllRunningTasksTimeUsedTime()
	 */
	public Date getLastCalculateAllRunningTasksTimeUsedTime() {
		return lastCalculateTime;
	}
	
	public void resetLastCalculateAllRunningTasksTimeUsedTime(){
		this.lastCalculateTime = null;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.RiskValueProvider#supportsGetRiskValue(cn.redflagsoft.base.bean.Risk)
	 */
	public boolean supportsGetRiskValue(Risk risk) {
		if(ObjectTypes.TASK == risk.getObjectType()){
			if("timeUsed".equalsIgnoreCase(risk.getObjectAttr())){
				return true;
			}
			//���������ݲ�֧��
			//else{
			//}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.RiskValueProvider#getRiskValue(cn.redflagsoft.base.bean.Risk)
	 */
	public RiskValue getRiskValue(Risk risk) {
		if(ObjectTypes.TASK == risk.getObjectType()){
			if("timeUsed".equalsIgnoreCase(risk.getObjectAttr())){
				Task task = getTask(risk.getObjectID());
				if(task != null){
					String bizSummary = "";
					TaskDef taskDef = getTaskDefProvider().getTaskDef(task.getType());
					if(taskDef != null){
						String bizSummaryTemplate = taskDef.getBizSummaryTemplate();
						if(bizSummaryTemplate != null){
							bizSummary = processTaskBizSummary(task, bizSummaryTemplate);
						}
					}
					//return new RiskValueImpl(new BigDecimal(task.getTimeUsed()), task.getTimeUsedChangedTime(), task);
					return new TaskRiskValueImpl(new BigDecimal(task.getTimeUsed()),  task.getTimeUsedChangedTime(), bizSummary, task);
				}else{
					throw new ObjectNotFoundException("risk����Ӧ�ļ�ض���taskû���ҵ���" + risk.getObjectID());
				}
			}
		}
		throw new UnsupportedOperationException("Ӧ���ȵ���supportsGetRiskValue()ȷ�����Ե��õ�ǰ����");
	}
	
	
	private static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy��MM��dd��");
	private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy��MM��dd��hhʱmm��ss��");
	public static String processTaskBizSummaryStatic(Task t, String bizSummaryTemplate){
		//��Ŀǰ��ҵ��ҵ���ţ�${task.code}����Ŀ���ƣ�${task.refObjectName}����${task_beginTime}��${date}���ۼ���ʱ${task.timeused}��ʣ��ʱ��${task.surplusTime}${task.timeUnitName}�����У���ͣ${task.hangUsed}�Σ��ۼ���ͣ${task.timeHang}������${task.delayUsed}�Σ��ۼ�����${task.timeDelay}��Ŀǰҵ��${task.statusName}��');
		
		//�����ҵ����Ŀ���裨ҵ���ţ�${task.code}����Ŀ���ƣ�${task.refObjectName}����${task_beginTime}��${date}���ۼ���ʱ${task.timeused}${task.timeUnitName}��
		//ʣ��ʱ��/����ʱ��${task.surplus}${task.timeUnitName},�Ѱ��/��δ���${task.statusName}��
		//�����У���ͣ${task.hangUsed}�Σ��ۼ���ͣ${task.timeHang}${task.timeUnitName}������${task.delayUsed}�Σ��ۼ�����${task.timeDelay}${task.timeUnitName}��
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("task", t);
		map.put("date", df1.format(new Date()));
		map.put("dateTime", df2.format(new Date()));
		map.put("task_beginTime", df1.format(t.getBeginTime()));
		
		int surplus = t.getSurplus();
		if(surplus >= 0){
			map.put("surplusDesc", "ʣ��ʱ��" + surplus);
		}else{
			map.put("surplusDesc", "����ʱ��" + (0 - surplus));
		}
		
		byte status = t.getStatus();
		if(status >= Task.TASK_STATUS_TERMINATE){
			map.put("statusDesc", "�Ѱ��");
		}else{
			map.put("statusDesc", "��δ���");
		}

		try {
//			return new ExpressionFactoryImpl().createExpression(bizSummaryTemplate).getValue(map);
			return  org.opoo.apps.util.StringUtils.processExpression(bizSummaryTemplate, map);
		} catch (Exception e) {
			log.error("ҵ��״��ģ���������", e);
		}
		return null;
	}
	
	/**
	 * ����ͨ�� <code>templateProcessor.taskBizSummary.className</code> 
	 * ���� <code>templateProcessor.taskBizSummary.beanName</code> ��ָ��ģ�崦������
	 * @param t
	 * @param bizSummaryTemplate
	 * @return
	 */
	protected String processTaskBizSummary(Task t, String bizSummaryTemplate){
		TemplateProcessor processor = templateProcessorManager.getTemplateProcessor(TEMPLATE_PROCESSOR_TYPE_BIZ_SUMMARY);
		if(processor != null){
			return processor.process(bizSummaryTemplate, t);
		}else{
			return processTaskBizSummaryStatic(t, bizSummaryTemplate);
		}
	}
	
	public Task updateTaskDutyersInfo(long taskSN, DutyersInfo dutyersInfo){
		return updateTaskDutyersInfo(getTask(taskSN), dutyersInfo);
	}
	
	
	/**
	 * @param task
	 * @param dutyersInfo
	 * @return
	 */
	public Task updateTaskDutyersInfo(Task task, DutyersInfo dutyersInfo) {
		Clerk dutyer = null;
		Clerk leader1 = null;
		Clerk leader2 = null;
		if(dutyersInfo.getDutyerID() != null){
			dutyer = getClerkService().getClerk(dutyersInfo.getDutyerID());
		}
		if(dutyersInfo.getDutyerLeader1Id() != null){
			leader1 = getClerkService().getClerk(dutyersInfo.getDutyerLeader1Id());
		}
		if(dutyersInfo.getDutyerLeader2Id() != null){
			leader2 = getClerkService().getClerk(dutyersInfo.getDutyerLeader2Id());
		}
		return updateTaskDutyersInfo(task, dutyer, leader1, leader2);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#updateDutyersInfo(long, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk)
	 */
	public Task updateTaskDutyersInfo(long taskSN, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2) {
		return updateTaskDutyersInfo(getTask(taskSN), dutyer, dutyerLeader1, dutyerLeader2);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#updateDutyersInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk)
	 */
	public Task updateTaskDutyersInfo(Task task, Clerk dutyer, Clerk dutyerLeader1,	Clerk dutyerLeader2) {
		if(task.getStatus() == Task.STATUS_����){
			log.warn("ҵ���Ѿ���������������������Ϣ��" + task);
			return null;
		}
		
		if(dutyer != null){
			task.setDutyEntityID(dutyer.getEntityID());
			task.setDutyEntityName(dutyer.getEntityName());
			task.setDutyDepartmentID(dutyer.getDepartmentID());
			task.setDutyDepartmentName(dutyer.getDepartmentName());
			
			task.setDutyerID(dutyer.getId());
			task.setDutyerName(dutyer.getName());
		}
		
		if(dutyerLeader1 != null){
			task.setDutyerLeader1Id(dutyerLeader1.getId());
			task.setDutyerLeader1Name(dutyerLeader1.getName());
		}
		
		if(dutyerLeader2 != null){
			task.setDutyerLeader2Id(dutyerLeader2.getId());
			task.setDutyerLeader2Name(dutyerLeader2.getName());
		}
		
		task = taskDao.update(task);
		
		//set risk duty clerk
		List<RiskEntry> riskEntries = task.getRiskEntries();
		if(riskEntries != null && !riskEntries.isEmpty()){
			for(RiskEntry re: riskEntries){
				Risk risk = riskService.getRiskById(re.getRiskID());
				RiskRule riskRule = riskRuleService.getRiskRule(risk.getRuleID());
				short dutyerType = riskRule.getDutyerType();
				
				//ֻ��risk����������Ϣ��Դ��taskʱ���Ÿ���
				if(dutyerType == RiskRule.DUTYER_TYPE_FROM_MONITORABLE_OBJECT){
					riskService.updateRiskDutyersInfo(re.getRiskID(), dutyer, dutyerLeader1, dutyerLeader2);
				}
			}
		}
		return task;
	}
	
	@Deprecated
	public void updateTaskDutyClerk(Long taskSN, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2){
		updateTaskDutyClerk(getTask(taskSN), dutyClerk, dutyerLeader1, dutyerLeader2);
	}
	
	@Deprecated
	public void updateTaskDutyClerk(Task task, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2){
		//�ж��Ƿ���Ҫ�޸�
		if(requireUpdateDutyClerk(task) && task.getStatus() != Task.STATUS_����){
			//set task duty clerk
			
			if(dutyClerk != null){
				task.setEntityID(dutyClerk.getEntityID());
				task.setEntityName(dutyClerk.getEntityName());
				task.setClerkID(dutyClerk.getId());
				task.setClerkName(dutyClerk.getName());
				task.setString0(dutyClerk.getName());
			}
			
			taskDao.update(task);
			
			//set risk duty clerk
			List<RiskEntry> riskEntries = task.getRiskEntries();
			if(riskEntries != null && !riskEntries.isEmpty()){
				for(RiskEntry re: riskEntries){
					riskService.updateRiskDutyersInfo(re.getRiskID(), dutyClerk, dutyerLeader1, dutyerLeader2);
				}
			}
		}
	}
	
	private boolean requireUpdateDutyClerk(Task task) {
		TaskDef def = taskDefProvider.getTaskDef(task.getType());
		boolean result = false;
		if(def != null){
			//int dutyRef = def.getDutyRef();
			//if(dutyRef == TaskDef.DUTY_REF_FROM_OBJECT){
			//2012-12-7���ٰ�����������жϣ����Ǹ���dutyerType�ж�
			if(def.getDutyerType() == BizDef.DUTYER_TYPE_FROM_OBJECT){
				result = true;
			}
		}
		return result;
	}
	
	
	
	public void taskDelete(Long taskSN){
		Task task = getTask(taskSN);
		taskDelete(task);
	}
	
	public void taskDelete(Task task){
		
		if(task != null){
			//ɾ��risk
			riskService.deleteRisk(task);
			
			//ɾ��work
			List<Work> workList = workService.findWorkByTaskSN(task.getSn());
			if(workList != null && !workList.isEmpty()){
				for (Work work : workList) {
					entityObjectToWorkDao.remove(Restrictions.eq("workSN", work.getSn()));
					objectsDao.remove(Restrictions.logic(Restrictions.eq("type",Objects.TYPE_�����븽��֮���ϵ)).and(Restrictions.eq("fstObject", work.getSn())));
					workService.deleteWork(work);	
				}
				clearCache(entityObjectToWorkDao);	
				clearCache(objectsDao);
			}
			
			taskMatterDao.remove(Restrictions.ge("taskSN",task.getSn()));
			clearCache(taskMatterDao);
			
			objectsDao.remove(Restrictions.logic(Restrictions.eq("type",Objects.TYPE_�����븽��֮���ϵ)).and(Restrictions.eq("fstObject", task.getSn())));
			clearCache(objectsDao);
			
			entityObjectToTaskDao.remove(Restrictions.eq("taskSN", task.getSn()));
			clearCache(entityObjectToTaskDao);
			
			//objectDataDao.remove(Restrictions.eq("createTask",Integer.parseInt(task.getSn().toString())));
			//clearCache(objectDataDao);
			
			objectTaskDao.remove(Restrictions.eq("taskSN", task.getSn()));
			clearCache(objectTaskDao);
			
			//matterDatumDao.remove(Restrictions.eq("taskType", task.getType()));
			//clearCache(matterDatumDao);
			
			
			//���RFSObject�����е�ActiveTaskSN�ֶΡ�
			RFSEntityObject obj = entityObjectLoader.getEntityObject(task.getRefObjectType(), task.getRefObjectId());
			if(obj instanceof RFSObject){
				RFSObject object = (RFSObject) obj;
				Long activeTaskSN = ((RFSObject) obj).getActiveTaskSN();
				if(activeTaskSN != null && activeTaskSN.longValue() == task.getSn().longValue()){
					object.setActiveTaskSN(null);
					objectService.updateObject(object);
				}
			}
			
			//LifeStage
			dispatcher.dispatchEvent(new TaskEvent(TaskEvent.Type.DELETED, task));
			
//			TaskDef def = taskDefProvider.getTaskDef(task.getType());
//			if(def != null && def.getLifeStageFieldName() != null){
//				LifeStage lifeStage = lifeStageDao.get(task.getRefObjectId());
//				if(lifeStage != null){
//					try {
//						PropertyUtils.setProperty(lifeStage, def.getLifeStageFieldName(),(byte)0);
//						lifeStageDao.update(lifeStage);
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					} catch (InvocationTargetException e) {
//						e.printStackTrace();
//					} catch (NoSuchMethodException e) {
//						e.printStackTrace();
//					}
//				}
//			}
			//...........
			taskDao.delete(task);
			clearCache(taskDao);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	private void clearCache(Object object){
		if(object instanceof CachedHibernateDao){
			CachedHibernateDao c = (CachedHibernateDao) object;
			if(c.getCache() != null){
				c.getCache().clear();
			}
		}
	}
	public void taskInvalid(RFSObject rfsObject) {
		taskDao.taskInvalid(rfsObject);
	}
	
	
	/**
	 * CalculateFlag ����λ�洢��
	 * 
	 * @see http://192.168.18.6/sf/sfmain/do/go/wiki1213
	 * @param task
	 * @return
	 */
	protected boolean includeFirstDay(Task task){
		TaskDef def = getTaskDefProvider().getTaskDef(task.getType());
		if(def == null){
			throw new NotFoundException("TaskDef not found for type: " + task.getType());
		}
		return (def.getCalculateFlag() & 1) != 0;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskService#updateTasksDutyersInfoByRefObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	public int updateTasksDutyersInfoByRefObject(RFSObject object) {
		if(object == null){
			log.warn("updateTasksDutyersInfoByObject(RFSObject) ����Ϊ��");
			return 0;
		}
			
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		// ��װ��ѯ����.
		SimpleExpression eqRefObjectId = Restrictions.eq("refObjectId", object.getId());
		SimpleExpression eqRefObjectType = Restrictions.eq("refObjectType",object.getObjectType());
		Criterion inStatus = Restrictions.ne("status",Task.STATUS_����);
		filter.setCriterion(Restrictions.logic(eqRefObjectId).and(eqRefObjectType).and(inStatus));
		List<Task> taskList = findTasks(filter);
		
		if(taskList == null || taskList.isEmpty()){
			return 0;
		}
		
		Clerk dutyer = null;
		Clerk leader1 = null;
		Clerk leader2 = null;
		if(object.getDutyClerkID() != null){
			dutyer = getClerkService().getClerk(object.getDutyClerkID());
		}
		if(object.getDutyerLeader1Id() != null){
			leader1 = getClerkService().getClerk(object.getDutyerLeader1Id());
		}
		if(object.getDutyerLeader2Id() != null){
			leader2 = getClerkService().getClerk(object.getDutyerLeader2Id());
		}

		int n = 0;
		for (Task task : taskList) {
			//�ж��Ƿ�ø��£������ҵ��û�гɸ��ݶ�����������ˣ��򲻸���
			if(requireUpdateDutyClerk(task)){
				//����������ʱͬʱ���ð�����Ϊ������
				//2012-12-07
				if(dutyer != null){
					task.setClerkID(dutyer.getId());
					task.setClerkName(dutyer.getName());
					task.setEntityID(dutyer.getEntityID());
					task.setEntityName(dutyer.getEntityName());
				}else{
					log.warn("���������˱��ʱҪ�ı䵱ǰtask�İ�����Ϊ�����ˣ���Ҫ���õ�������Ϊ�ա�" + task);
				}
				
				updateTaskDutyersInfo(task, dutyer, leader1, leader2);
				n++;
			}
			//����task�Ϲҵ�risk
			updateRisksDutyersInfoIfNeeded(task.getRiskEntries(), RiskRule.DUTYER_TYPE_FROM_REF_OBJECT, dutyer, leader1, leader2);
		}
		
		//���������ֱ�ӹҵ�Risk
		updateRisksDutyersInfoIfNeeded(object.getRiskEntries(), RiskRule.DUTYER_TYPE_FROM_MONITORABLE_OBJECT, dutyer, leader1, leader2);
		/*
		List<RiskEntry> list = object.getRiskEntries();
		if(list != null && !list.isEmpty()){
			for (RiskEntry re : list) {
				Risk risk = riskService.getRiskById(re.getRiskID());
				RiskRule riskRule = riskRuleService.getRiskRule(risk.getRuleID());
				short dutyerType = riskRule.getDutyerType();
				
				//ֻ��risk����������Ϣ��Դ��taskʱ���Ÿ���
				if(dutyerType == RiskRule.DUTYER_TYPE_FROM_MONITORABLE_OBJECT){
					riskService.updateRiskDutyersInfo(re.getRiskID(), dutyer, leader1, leader2);
				}
			}
		}*/
		return n;
	}
	
	//������Ҫ����risk����������Ϣ
	private void updateRisksDutyersInfoIfNeeded(List<RiskEntry> list, short riskRuleDutyerType, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2){
		if(list != null && !list.isEmpty()){
			for (RiskEntry re : list) {
				Risk risk = riskService.getRiskById(re.getRiskID());
				RiskRule riskRule = riskRuleService.getRiskRule(risk.getRuleID());
				short dutyerType = riskRule.getDutyerType();
				
				//ֻ��risk����������Ϣ��Դ��ָ������ʱ�Ÿ���
				if(dutyerType == riskRuleDutyerType){
					riskService.updateRiskDutyersInfo(re.getRiskID(), dutyer, dutyerLeader1, dutyerLeader2);
				}
			}
		}
	}
}

