/*
 * $Id: AbstractWorkScheme.java 6376 2014-04-15 10:24:25Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.redflagsoft.base.annotation.DisplayNameUtils;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.aop.interceptor.Prepareable;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Phrase;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.process.WorkProcess;
import cn.redflagsoft.base.process.WorkProcessManager;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.PhraseService;
import cn.redflagsoft.base.service.PrintHandler;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.WorkDefProvider;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.service.impl.ObjectServiceImpl;
import cn.redflagsoft.base.util.MapUtils;
import cn.redflagsoft.base.vo.DatumVO;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;


/**
 * 抽象的WorkScheme，WorkScheme继承体系重要类。
 * 
 * 此类必须在用户登录后才能调用其方法，方法调用了当前登录用户的
 * Clerk信息。
 * 
 * @author Alex Lin
 *
 */
public abstract class AbstractWorkScheme /*extends SimpleEventDispatcher<WorkSchemeEvent>*/ 
		implements WorkScheme, ParametersAware, InitializingBean, Validateable, BeanNameAware, Prepareable, ValidationAware {
	
	private final Log log = LogFactory.getLog(getClass());
	private ValidationAware validation = new ValidationAwareSupport();
	
	private WorkProcessManager workProcessManager;
	private TaskService taskService;
	private WorkService workService;
	private PhraseService phraseService;
	private CallLaterMattersHandler mattersHandler;
	@SuppressWarnings("rawtypes")
	private ObjectService objectService;
	private ClerkService clerkService;
	private String method = "scheme";
	private WorkDefProvider workDefProvider;
	private PrintHandler printHandler;
	
	//参数总集合
	@SuppressWarnings("rawtypes")
	private Map parameters;
	//配置参数
	private Integer taskType;
	private Integer workType;
	
	//前台输入参数
	private Integer processType;
	private String processName;
	private Long workSN;
	private Long taskSN;
	private List<DatumVO> datumList;
	private List<DatumVO> datumList2;
	private List<Long> matterIds;
	//业务对象ID
	private Long objectId;
	
	private Long clerkID;
	
	//根据参数的
	private Task task;
	private Work work;
	private MatterVO matterVO;
	private DatumVOList datumVOList;
	private RFSObject object;
	//选择的标题
	private Long title ;
	
	private Clerk clerk;
	
	//附件id集合
	private List<Long> fileIds;
	
	private String displayName = DisplayNameUtils.getDisplayName(getClass());
	
	private String beanName;
	
	private Set<RFSItemable> relatedItems = Sets.newHashSet();//new HashSet<RFSItemable>();
	
	private Set<RFSEntityObject> relatedObjects = Sets.newHashSet();
	
	private Date workBeginTime;
	private Date workEndTime;
	private Date workHangTime;
	private Date workWakeTime;
	
	
	private List<Long> processedMatterAffairs = new ArrayList<Long>();
	
	private SchemeTaskManager schemeTaskManager = new SchemeTaskManagerImpl();
	
	/**
	 * 特别程序时是否直接处理task。
	 * false表示不直接处理，而是通过matteraffair的配置来处理。
	 */
	private boolean handleTaskDirectly = false;
	
	private String receiptTemplate;
	
	
	/**
	 * @return the receiptTemplate
	 */
	public String getReceiptTemplate() {
		return receiptTemplate;
	}


	/**
	 * @param receiptTemplate the receiptTemplate to set
	 */
	public void setReceiptTemplate(String receiptTemplate) {
		this.receiptTemplate = receiptTemplate;
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if(objectService == null){
			setObjectService(new ObjectService.InvalidObjectService<RFSObject>());
			log.warn("objectService 在 WorkScheme 没有配置，必须指定 workSN 或者 taskSN 以解析 ObjectService 的类型。");
		}
		Assert.notNull(objectService, "必须配置 objectService，注意指定具体的ObjectService实现类。");
		Assert.notNull(taskService, "必须配置 taskService");
		Assert.notNull(workService, "必须配置 workService");
		Assert.notNull(workProcessManager, "必须配置 workProcessManager");
		Assert.notNull(phraseService, "必须配置 phraseService");
		Assert.notNull(mattersHandler, "必须配置 mattersHandler");
		Assert.notNull(clerkService, "必须设置clerkService");
		
		if(ObjectServiceImpl.class.equals(objectService.getClass())){
			//throw new IllegalArgumentException("ObjectService 必须是 ObjectServiceImpl " +
			//		"的继承类而不是 ObjectServiceImpl 本身。");
			log.warn("注意大部分应用中 ObjectService 必须是 ObjectServiceImpl 的继承类，而不是 ObjectServiceImpl 本身。");
		}
	}
	
	
	/**
	 * 在参数设置之后，validate之前执行。
	 * 可在这个方法中对缺失的参数进行处理，通常设置参数时不应该执行的数据访问，也
	 * 可在这里执行。
	 */
	public void prepare() {
		//objectId可以为空， 不为空时查询
		if(objectId != null && object == null){
			log.debug("主对象不存在，根据 ID 查询主对象：" + objectId);
			if(objectService != null && !(objectService instanceof ObjectService.InvalidObjectService)){
				object = objectService.getObject(objectId);
				if(object == null){
					throw new SchemeException("指定的主对象不存在：" + objectId);
				}
			}else{
				log.debug("objectService 无效，不使用 objectId 查询主对象，稍后使用 task 中的 refObjectType 解析 objectService。");
			}
		}
		
		//查询Clerk
		if(clerk == null && clerkID != null){
			if(log.isDebugEnabled()){
				log.debug("Get clerk in scheme.prepare() for id: " + clerkID);
			}
			clerk = clerkService.getClerk(clerkID);
		}
		if(clerk == null){
			clerk = UserClerkHolder.getClerk();
		}
		if(clerkID == null){
			clerkID = clerk.getId();
		}
		
		if(workSN != null && workSN.longValue() <= 0L){
			workSN = null;
		}
		
		//查询Work
		if(work == null && workSN != null){
			if(log.isDebugEnabled()){
				log.debug("Get work in scheme.prepare() for sn: " + workSN);
			}
			work = workService.getWork(workSN);
			if(work == null){
				workSN = null;
			}
		}
		//如果没有设置workType
		if(work != null && workType == null){
			workType = work.getType();
		}
		
		//使用WorkDef中定义的taskType, 公用的Work其taskType为0，要除外。
		if(workType != null && taskType == null){
			WorkDef workDef = workDefProvider.getWorkDef(workType);
			if(workDef != null && workDef.getTaskType() != 0){
				taskType = workDef.getTaskType();
			}
		}
		
		if(work != null && taskSN == null){
			taskSN = work.getTaskSN();
		}
		
		//将无效的TaskSN设为空
		if(taskSN != null && taskSN.longValue() <= 0L){
			taskSN = null;
		}

		//查询task
		if(task == null && taskSN != null){
			if(log.isDebugEnabled()){
				log.debug("Get task in scheme.prepare() for sn: " + taskSN);
			}
			
			task = taskService.getTask(taskSN);
			
			if(task != null && (objectService == null || objectService instanceof ObjectService.InvalidObjectService)){
				objectService = resolveObjectService(task.getRefObjectType(), task.getRefObjectId());
				log.debug("WorkScheme 配置的 ObjectService 无效，重新解析：" + objectService);
			}
			
			if(task != null && object == null){
				object = objectService.getObject(task.getRefObjectId());
				if(object == null){
					throw new SchemeException("指定的主对象不存在(task.refObjectId)：" + objectId);
				}
			}
			
			if(task != null && task.getActiveWorkSN() != null && task.getActiveWorkSN().longValue() != 0){
				if(work == null){
					if(log.isDebugEnabled()){
						log.debug("根据 task 的 activeWorkSN 设置 Work(task=" + task + ", workSN=" + task.getActiveWorkSN() + ").");
					}
					workSN = task.getActiveWorkSN();
					work = workService.getWork(task.getActiveWorkSN());
					if(work == null){
						workSN = null;
					}
				}
			}
			
			if(task == null){
				taskSN = null;
			}
		}
		//如果没有设置taskType
		if(task != null && taskType == null){
			taskType = task.getType();
		}
		
		
		
		
		if(datumVOList == null){
			List<DatumVO> list = new ArrayList<DatumVO>();
			if(datumList != null && !datumList.isEmpty()){
				list.addAll(datumList);
			}
			if(datumList2 != null && !datumList2.isEmpty()){
				list.addAll(datumList2);
			}
			if(!list.isEmpty()){
				datumVOList = new DatumVOList(list);
			}
		}
		
		if(matterVO == null && matterIds != null && !matterIds.isEmpty()){
			matterVO = new MatterVO(matterIds.toArray(new Long[matterIds.size()]));
		}
		
		if(object == null){
			object = createObject();
			if(log.isDebugEnabled()){
				log.debug("创建主对象：" + object);
			}
		}
//		if(object == null){
//			throw new SchemeException("必须在WorkScheme中创建指定的业务对象。");
//		}
		
		
		if(matterIds == null){
			matterIds = Lists.newArrayList();
		}
	}


	/**
	 * @param refObjectType
	 * @param refObjectId
	 * @return
	 */
	protected ObjectService<RFSObject> resolveObjectService(int objectType, Long bjectId) {
		throw new SchemeException("必须配置 objectService，注意指定具体的ObjectService实现类，或者在子类实现resolveObjectService()方法。");
	}


	/**
	 * 在每个 do 方法的参数设置后并执行prepare()之后执行。
	 * 
	 * <p>以下的参数，既可以配置，也可以动态赋值。
	 */
	public void validate() {
//		if(taskType == null && workType != null){
//			WorkDef workDef = workDefProvider.getWorkDef(workType);
//			if(workDef != null){
//				taskType = workDef.getTaskType();
//			}
//			if(log.isDebugEnabled()){
//				String msg = String.format("Scheme 只配置了work的type '%s'， 没有指定 task的type，通过查询Work的定义，获取的taskType为 %s.",
//						workType, taskType);
//				log.debug(msg);
//			}
//		}
		
		Assert.notNull(taskType, "必须设置 taskType");
		Assert.notNull(workType, "必须设置 workType");
		Assert.notNull(matterIds, "必须设置 matterIds");
		if(object == null){
			throw new SchemeException("必须在WorkScheme中创建指定的业务对象。");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.Scheme#doScheme()
	 */
	public Object doScheme() {
		return null;
	}



	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.WorkScheme#process()
	 */
	public Object process() throws SchemeException {
		WorkProcess wp = null;
		if(processType != null){
			wp = workProcessManager.getProcess(processType);
		}else if(processName != null){
			wp = workProcessManager.getProcess(processName);
		}else{
			throw new SchemeException("必须指定事务的类型或者名称。");
		}
		
		if(log.isDebugEnabled()){
			log.debug("发现WorkProcess: " + wp);
		}
		
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = getParameters();
		if(taskType != null){
			map.put("taskType", taskType);
		}
		if(workType != null){
			map.put("workType", workType);
		}
		return wp.execute(map, true);
	}
	
	/**
	 * 暂停。
	 * 
	 * @return
	 */
	public Object doHang(){
		if(task.getStatus() == Task.STATUS_暂停){
			return "业务已经暂停，不能重复操作。";
		}
		
		
		//子类中实现具体业务逻辑。
		Object result = hang();		
		
		//workService.hangWork(work.getSn(), work.getHangTime());
		//taskService.hangTask(task.getSn());
//		addSchemeTask(new Runnable() {
//			public void run() {
//				workService.hangWork(getWork(), getWork().getHangTime());
//				taskService.hangTask(getTask(), getWork().getHangTime());
//			}
//		});
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().hangWork(work, work.getHangTime());
		if(handleTaskDirectly){
			getTaskService().hangTask(task, work.getHangTime());
		}
		getMattersHandler().hang(work, object, getMatterIds());
		
		//mattersHandler.hang(work, getObject(), matterIds);
		if(title!=null){
			Phrase phrase=phraseService.getPhraseBySn(title);
			if(phrase!=null){
				List<Long> temp=new ArrayList<Long>();
				temp.add(getMatterVO().getMatterIds()[0]);
				getMattersHandler().hang(work, object, temp, phrase.getTag());
			}
		}	
		
		//暂停不结束work
		setWork(null);
		return result == null ? task.getName()+"暂停成功！" : result;
	}
	
	/**
	 * 暂停时要做的业务处理，在子类中实现。
	 * 
	 */
	protected Object hang(){
		return null;
	}
	
	
	/**
	 * 唤醒、续办。
	 * 
	 * @return
	 */
	public Object doWake(){
		//子类中实现具体业务逻辑。
		Object result = wake();
		
		
		//taskService.wakeTask(task.getSn());
		//workService.wakeWork(work.getSn());
		
//		addSchemeTask(new Runnable() {
//			public void run() {
//				workService.wakeWork(getWork(), getWork().getWakeTime());
//				taskService.wakeTask(getTask(), getWork().getWakeTime());
//			}
//		});
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().wakeWork(work, work.getWakeTime());
		if(handleTaskDirectly){
			getTaskService().wakeTask(task, work.getWakeTime());
		}
		getMattersHandler().wake(work, getObject(), getMatterIds());
		
		//mattersHandler.wake(work, getObject(), matterIds);
		if(title!=null){
			Phrase phrase=phraseService.getPhraseBySn(title);
			if(phrase!=null){
				List<Long> temp=new ArrayList<Long>();
				temp.add(getMatterVO().getMatterIds()[0]);	
				getMattersHandler().wake(work, object,temp, phrase.getTag());
			}
		}	
		//唤醒不结束work
		setWork(null);
		return result == null ? task.getName() + "重启成功！" : result;
	}
	
	/**
	 * 唤醒时要做的业务处理，在子类中实现。
	 */
	protected Object wake(){
		return null;
	}
	

	/**
	 * 取消。
	 * 
	 * @return
	 */
	public Object doCancel(){
		//子类中实现具体业务逻辑。
		Object result = cancel();
//		getMattersHandler().finishMatters(task, work, object, getMatterVO().getMatterIds());
//
//		addSchemeTask(new Runnable(){
//			public void run() {
//				taskService.cancelTask( getTask(), getWork().getEndTime());
//			}}
//		);
		//taskService.cancelTask(task, work.getEndTime());	
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		getWorkService().cancelWork(getWork());
		if(handleTaskDirectly){
			getTaskService().cancelTask(task, work.getEndTime());
		}
		getMattersHandler().cancel(getWork(), object, getMatterIds());
		setWork(null);
		
		return result == null ? task.getName()+"免办成功！" : result;
		
	}
	
	/**
	 * 免办时要做的业务处理，在子类中实现。
	 */
	protected Object cancel(){
		return null;
	}
	
	/**
	 * 中止。
	 * 
	 * @return
	 */
	public Object doStop(){
		//子类中实现具体业务逻辑。
		Object result = stop();
//		getMattersHandler().finishMatters(task, work, object, getMatterVO().getMatterIds());
//		
//		//taskService.stopTask(task);
//		addSchemeTask(new Runnable() {
//			public void run() {
//				taskService.stopTask(getTask(), getWork().getEndTime());
//			}
//		});
//		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().stopWork(getWork());
		if(handleTaskDirectly){
			getTaskService().stopTask(task, work.getEndTime());
		}
		getMattersHandler().stop(getWork(), getObject(), getMatterIds());
		setWork(null);
		
		return result == null ? task.getName()+"中止成功！" : result;
	}
	
	/**
	 * 中止时要做的业务处理，在子类中实现。
	 */
	protected Object stop(){
		return null;
	}
	
	/**
	 * 免办
	 * @return Scheme执行结果
	 */
	public Object doAvoid() {
		Object result = avoid();
		
//		getMattersHandler().finishMatters(task, work, object, getMatterVO().getMatterIds());
//		
//		log.debug("生成免办操作的延迟任务...");
//		addSchemeTask(new Runnable() {
//			public void run() {
//				/**
//				 * 最终调用Work时，其参数可能已经变了
//				 */
//				taskService.avoidTask(getTask(), getWork().getEndTime());
//			}
//		});
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().avoidWork(work);
		if(handleTaskDirectly){
			getTaskService().avoidTask(getTask(), work.getEndTime());
		}
		getMattersHandler().avoid(work, getObject(), getMatterIds());
		setWork(null);
		
		//taskService.avoidTask(task, work.getEndTime());
		return result == null ? task.getName() + "免办成功！" : result;
	}
	
	/**
	 * 免办具体处理，在子类中实现。
	 * @return Scheme执行结果
	 */
	protected Object avoid() {
		return null;
	}
	
	/**
	 * 驳回。
	 * @return
	 */
	public Object doReject(){
		Object result = reject();
		log.debug("mattersHandler.reject");
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().rejectWork(getWork());
		if(handleTaskDirectly){
			getTaskService().rejectTask(task, work.getEndTime());
		}
		getMattersHandler().reject(getWork(), getObject(), getMatterIds());
		setWork(null);
		return result == null ? task.getName() + "驳回成功！" : result;
	}
	
	/**
	 * @return
	 */
	protected Object reject() {
		return null;
	}
	
	/**
	 * 撤回。
	 * @return
	 */
	public Object doWithdraw(){
		Object result = withdraw();
		log.debug("mattersHandler.withdraw");
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().withdrawWork(getWork());
		if(handleTaskDirectly){
			getTaskService().withdrawTask(task, work.getEndTime());
		}
		getMattersHandler().withdraw(getWork(), getObject(), getMatterIds());
		setWork(null);
		return result == null ? task.getName() + "撤回成功！" : result;
	}
	
	
	/**
	 * @return
	 */
	protected Object withdraw() {
		return null;
	}
	
	public Object doFinishTask(){
		Object result = finishTask();
		log.debug("mattersHandler.finishTask");
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		String remark = MapUtils.getString(getParameters(), "remark");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		if(remark != null){
			work.setRemark(remark);
		}
		finishMatters(remark);
		return result == null ? task.getName() + "结束成功！" : result;
	}
	
	/**
	 * @return
	 */
	protected Object finishTask() {
		return null;
	}


	/**
	 * 转交、转出。
	 * @return
	 */
	public Object doTransfer(){
		//子类中实现具体业务逻辑。
		Object result = transfer();
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		getWorkService().transferWork(getWork());
		if(handleTaskDirectly){
			getTaskService().transferTask(task, work.getEndTime());
		}
		getMattersHandler().transfer(getWork(), getObject(), getMatterIds());
		setWork(null);
		
		return result == null ? task.getName()+"转出成功！" : result;
	}
	
	/**
	 * @return
	 */
	protected Object transfer() {
		return null;
	}
	
	/**
	 * 撤销。
	 * @return
	 */
	public Object doUndo(){
		//子类中实现具体业务逻辑。
		Object result = undo();
		
		String reasonCategory = MapUtils.getString(getParameters(), "s0");
		String reason = MapUtils.getString(getParameters(), "s1");
		if(reasonCategory != null){
			work.setString0(reasonCategory);
		}
		if(reason != null){
			work.setString1(reason);
		}
		
		work.setString2(task.getStatus() + "");
		getWorkService().undoWork(getWork());
		if(handleTaskDirectly){
			getTaskService().undoTask(task, work.getEndTime());
		}
		getMattersHandler().undo(getWork(), getObject(), getMatterIds());
		setWork(null);
		
		return result == null ? task.getName() + "撤销成功！" : result;
	}
	
	protected Object undo(){
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.aop.interceptor.ParametersAware#getParameters()
	 */
	@SuppressWarnings("rawtypes")
	public Map getParameters() {
		return parameters;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.aop.interceptor.ParametersAware#setParameters(java.util.Map)
	 */
	public void setParameters(@SuppressWarnings("rawtypes") Map parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the taskType
	 */
	public Integer getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the workType
	 */
	public Integer getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	/**
	 * @return the workProcessManager
	 */
	public WorkProcessManager getWorkProcessManager() {
		return workProcessManager;
	}

	/**
	 * @param workProcessManager the workProcessManager to set
	 */
	public void setWorkProcessManager(WorkProcessManager workProcessManager) {
		this.workProcessManager = workProcessManager;
	}

	/**
	 * @return the processType
	 */
	public Integer getProcessType() {
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the task
	 */
	public final Task getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public final void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the work
	 */
	public final Work getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public final void setWork(Work work) {
		this.work = work;
	}



	/**
	 * @return the workSN
	 */
	public Long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN the workSN to set
	 */
	public void setWorkSN(Long workSN) {
		this.workSN = workSN;
//		if(workSN != null){
//			work = workService.getWork(workSN);
//		}
//
//		if(work == null){
//			this.workSN = null;
//		}
	}

	/**
	 * @return the taskSN
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * @param taskSN the taskSN to set
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
//		if(taskSN != null){
//			task = taskService.getTask(taskSN);
//			if(task != null && task.getActiveWorkSN() != null && task.getActiveWorkSN().longValue() != 0){
//				log.debug("&&&&&&&&&&&&&&&&&& 根据task的activeWorkSN设置Work：" + task + ", " + task.getActiveWorkSN());
//				setWorkSN(task.getActiveWorkSN());
//			}
//		}
//
//		if(task == null){
//			this.taskSN = null;
//		}
	}

	/**
	 * @return the datumList
	 */
	public List<DatumVO> getDatumList() {
		return datumList;
	}

	/**
	 * @param datumList the datumList to set
	 */
	public void setDatumList(List<DatumVO> datumList) {
		this.datumList = datumList;
	}

	/**
	 * @return the datumList2
	 */
	public List<DatumVO> getDatumList2() {
		return datumList2;
	}

	/**
	 * @param datumList the datumList to set
	 */
	public void setDatumList2(List<DatumVO> datumList2) {
		this.datumList2 = datumList2;
	}

	/**
	 * @return the matterIds
	 */
	public List<Long> getMatterIds() {
		return matterIds;
	}

	/**
	 * @param matterIds the matterIds to set
	 */
	public void setMatterIds(List<Long> matterIds) {
		this.matterIds = matterIds;	

	}
	
	public MatterVO getMatterVO(){
		if(matterVO == null && matterIds != null && !matterIds.isEmpty()){
			matterVO = new MatterVO(matterIds.toArray(new Long[matterIds.size()]));
		}
		//System.out.println("matterIds: " + matterIds);
		return matterVO;
	}
	
	
	
	
	
	public void setClerkID(Long clerkID){
		this.clerkID = clerkID;
//		if(clerkID != null){
//			clerk = clerkService.getClerk(clerkID);
//		}
	}
	/**
	 * 
	 * @return
	 */
	public Clerk getClerk(){
//		if(clerk == null){
//			clerk = UserClerkHolder.getClerk();
//		}
		return clerk;
	}
	
	public Long getClerkID(){
//		return getClerk().getId();
		//return 100L;
		return clerkID;
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
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
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
	public void setWorkService(WorkService workService) {
		this.workService = workService;
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
	 * @return the datumVOList
	 */
	public DatumVOList getDatumVOList() {
		/*if(datumVOList == null && datumList != null && !datumList.isEmpty()){
			datumVOList = new DatumVOList(datumList);
		}*/
//		if(datumVOList == null){
//			List<DatumVO> list = new ArrayList<DatumVO>();
//			if(datumList != null && !datumList.isEmpty()){
//				list.addAll(datumList);
//			}
//			if(datumList2 != null && !datumList2.isEmpty()){
//				list.addAll(datumList2);
//			}
//			if(!list.isEmpty()){
//				datumVOList = new DatumVOList(list);
//			}
//		}
		return datumVOList;
	}



	/**
	 * @return the mattersHandler
	 */
	public MattersHandler getMattersHandler() {
		return mattersHandler;
	}



	/**
	 * @param mattersHandler the mattersHandler to set
	 */
	public void setMattersHandler(MattersHandler mattersHandler) {
		if(mattersHandler != null){
			if(!(mattersHandler instanceof WorkSchemeMattersHandler) && mattersHandler instanceof AbstractMattersHandler){
				mattersHandler = new WorkSchemeMattersHandler((AbstractMattersHandler)mattersHandler, this);
			}
			this.mattersHandler = new CallLaterMattersHandler(mattersHandler);
		}
	}



	/**
	 * @return the objectService
	 */
	@SuppressWarnings("rawtypes")
	public ObjectService getObjectService() {
		return objectService;
	}



	/**
	 * @param objectService the objectService to set
	 */
	public void setObjectService(@SuppressWarnings("rawtypes") ObjectService objectService) {
		this.objectService = objectService;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}


	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
//		if(objectId != null){
//			object = objectService.getObject(objectId);
//			if(object == null){
//				throw new SchemeException("指定的业务对象不存在：" + objectId);
//			}
//		}
	}
	

	public void setPhraseService(PhraseService phraseService) {
		this.phraseService = phraseService;
	}
	
	
	
	public PhraseService getPhraseService() {
		return phraseService;
	}


	public RFSObject getObject(){
//		if(object == null){
//			object = createObject();
//			log.debug("创建业务对象：" + object);
//		}
//		if(object == null){
//			throw new SchemeException("必须在WorkScheme中创建指定的业务对象。");
//		}
		return object;
	}
	
	public void setRFSObject(RFSObject object){
		log.debug("直接为 WS(" + this + ")指定了主对象：" + object);
		this.object = object;
	}
	
	/**
	 * 如果是新建业务对象，需要在子类中实现覆盖这个方法。
	 * 
	 * @return
	 */
	protected RFSObject createObject(){
		//super.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.OBJECT_CREATED, this));
		return null;
	}



	
	

	public Long getTitle() {
		return title;
	}


	public void setTitle(Long title) {
		this.title = title;
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
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}


	/**
	 * @return the fileIds
	 */
	public List<Long> getFileIds() {
		return fileIds;
	}


	/**
	 * @param fileIds the fileIds to set
	 */
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public void setBeanName(String name){
		this.beanName = name;
	}
	
	public String getBeanName(){
		return beanName;
	}
	
	
	/**
	 * 事项办结。
	 * @since 2.0.1
	 */
	protected void finishMatters(){
		//System.out.println("getMattersHandler(): " + getMattersHandler());
		//System.out.println("getMatterVO(): " +  getMatterVO());
		getMattersHandler().finishMatters(getTask(), getWork(),	getObject(), getMatterVO().getMatterIds());
	}
	
	/**
	 * 事项办结。
	 * @param note
	 * @since 2.0.1
	 */
	protected void finishMatters(String note){
		getMattersHandler().finishMatters(getTask(), getWork(),	getObject(), getMatterVO().getMatterIds(), note);
	}
	
	/**
	 * 事项办结。
	 * @param tag
	 * @since 2.0.1
	 */
	protected void finishMatter(short tag){
		getMattersHandler().finishMatter(getTask(), getWork(), getObject(), getMatterVO().getMatterIds()[0], tag);
	}
	
	/**
	 * 事项办结。
	 * @param tag
	 * @param note
	 * @since 2.0.1
	 */
	protected void finishMatter(short tag, String note){
		getMattersHandler().finishMatter(getTask(), getWork(), getObject(), getMatterVO().getMatterIds()[0], tag, note);
	}
	
	/**
	 * @return the relatedBizItems
	 */
	public List<RFSItemable> getRelatedItems() {
		if(relatedItems == null){
			return Collections.emptyList();
		}
//		return new ArrayList<RFSItemable>(relatedItems);
		return Lists.newArrayList(relatedItems);
	}
	
	public List<RFSEntityObject> getRelatedObjects(){
		if(relatedObjects == null){
			return Collections.emptyList();
		}
		return Lists.newArrayList(relatedObjects);
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	protected AbstractWorkScheme addRelatedItem(RFSItemable object){
		relatedItems.add(object);
		relatedObjects.add(object);
		return this;
	}
	
	protected AbstractWorkScheme addRelatedObject(RFSEntityObject object){
		if(object instanceof RFSItemable){
			relatedItems.add((RFSItemable)object);
		}
		relatedObjects.add(object);
		return this;
	}
	
	/**
	 * @return the workBeginTime
	 */
	public Date getWorkBeginTime() {
		return workBeginTime;
	}
	/**
	 * @param workBeginTime the workBeginTime to set
	 */
	public void setWorkBeginTime(Date workBeginTime) {
		this.workBeginTime = workBeginTime;
	}
	/**
	 * @return the workEndTime
	 */
	public Date getWorkEndTime() {
		return workEndTime;
	}
	/**
	 * @param workEndTime the workEndTime to set
	 */
	public void setWorkEndTime(Date workEndTime) {
		this.workEndTime = workEndTime;
	}
	
	/**
	 * @return the workHangTime
	 */
	public Date getWorkHangTime() {
		return workHangTime;
	}
	/**
	 * @param workHangTime the workHangTime to set
	 */
	public void setWorkHangTime(Date workHangTime) {
		this.workHangTime = workHangTime;
	}
	/**
	 * @return the workWakeTime
	 */
	public Date getWorkWakeTime() {
		return workWakeTime;
	}
	/**
	 * @param workWakeTime the workWakeTime to set
	 */
	public void setWorkWakeTime(Date workWakeTime) {
		this.workWakeTime = workWakeTime;
	}
	protected void addSchemeTask(Runnable schemeTask){
		//log.debug("Add scheme task: " + schemeTask);
		schemeTaskManager.addSchemeTask(schemeTask);
	}
	
	protected void addSchemeTask(int type, Runnable schemeTask){
		//log.debug("Add scheme task: " + schemeTask);
		schemeTaskManager.addSchemeTask(type, schemeTask);
	}

	public SchemeTaskManager getSchemeTaskManager(){
		return schemeTaskManager;
	}

	/**
	 * 生成业务办理的回执文件，在workscheme的do*中最后调用，如果涉及
	 * 到业务对象，调用前应该先调用addRelatedItem()方法。
	 * @return
	 */
	protected Attachment generateReceiptFile()
	{
		if(!AppsGlobals.getProperty("WorkScheme.generateReceiptFile.enabled", true)){
			if(log.isDebugEnabled()){
				log.debug("根据配置不生成受理回执文件，启用该特性，请配置属性“WorkScheme.generateReceiptFile.enabled”为true");
			}
			return null;
		}
		
		if(StringUtils.isNotBlank(receiptTemplate)){
			Model model = new Model();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("rfsobject", getObject());//model.data.rfsobject.name;
			
			Task t = getTask();
			if(t != null){
				map.put("task",t);
			}
			Work w = getWork();
			if( w != null){
				map.put("work",w);
			}
			
			List<RFSItemable> items = getRelatedItems();
			if(items != null && !items.isEmpty()){
				for(RFSItemable item: items){
					if(item != null){
						String name = item.getClass().getSimpleName().toLowerCase();//model.data.prjproposal.shouliDate
						map.put(name, item);
					}
				}
			}
			model.setData(map);
			
			try {
				Model result = printHandler.preparePrint(receiptTemplate, model, false, false);
				Attachment a = (Attachment) result.getData();
				
				//将回执文件ID和Work关联起来
				Work work2 = getWork();
				work2.setString4(a.getId() + "");
				getWorkService().updateWork(work2);
				
				return a;
			} catch (Exception e) {
				throw new SchemeException(e);
			}
		}else{
			log.debug("回执模板为空，不生成回执文件。");
			return null;
		}
	}
	
	
	public PrintHandler getPrintHandler() {
		return printHandler;
	}


	public void setPrintHandler(PrintHandler printHandler) {
		this.printHandler = printHandler;
	}

	
	/**
	 * 设置当前 WorkScheme 执行时所对应的资料的发文单位ID。
	 * 在办理业务时，批复时间和批复单位为发文时间和发文单位。
	 * @param datumDispatchOrgId the datumDispatchOrgId to set
	 */
	protected void setDatumDispatchOrgId(Long datumDispatchOrgId) {
		if(this.datumVOList != null){
			this.datumVOList.setDatumDispatchOrgId(datumDispatchOrgId);
		}
	}

	/**
	 * 设置当前 WorkScheme 执行时所对应的资料的发文单位名称。
	 * 在办理业务时，批复时间和批复单位为发文时间和发文单位。
	 * @param datumDispatchOrgName the datumDispatchOrgName to set
	 */
	protected void setDatumDispatchOrgName(String datumDispatchOrgName) {
		if(this.datumVOList != null){
			this.datumVOList.setDatumDispatchOrgName(datumDispatchOrgName);
		}
	}

	/**
	 * 设置当前 WorkScheme 执行时所对应的资料的发文时间。
	 * 在办理业务时，批复时间和批复单位为发文时间和发文单位。
	 * @param datumDispatchTime the datumDispatchTime to set
	 */
	protected void setDatumDispatchTime(Date datumDispatchTime) {
		if(this.datumVOList != null){
			this.datumVOList.setDatumDispatchTime(datumDispatchTime);
		}
	}
	/**
	 * 设置当前WorkScheme执行时所对应的资料的发文信息，包括发文单位、发文时间。
	 * <p>在办理业务时，批复时间和批复单位为发文时间和发文单位。
	 * @param datumDispatchOrgId
	 * @param datumDispatchOrgName
	 * @param datumDispatchTime
	 */
	protected void setDatumDispatchInfo(Long datumDispatchOrgId,
				String datumDispatchOrgName,Date datumDispatchTime){
		setDatumDispatchOrgId(datumDispatchOrgId);
		setDatumDispatchOrgName(datumDispatchOrgName);
		setDatumDispatchTime(datumDispatchTime);
	}
	
	
	/**
	 * @return the processedMatterAffairs
	 */
	public List<Long> getProcessedMatterAffairs() {
		return processedMatterAffairs;
	}


	private class CallLaterMattersHandler implements MattersHandler{
		private final MattersHandler internalMattersHandler;
		/**
		 * @param mattersHandler
		 * @param abstractWorkScheme 
		 */
		private CallLaterMattersHandler(MattersHandler mattersHandler) {
			this.internalMattersHandler = mattersHandler;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
		 */
		public void acceptMatters(final Task task, final Work work, final RFSObject object, final Long[] matterIds) {
			addSchemeTask( new Runnable() {
				public void run() {
					log.debug("MattersHandler.acceptMatters(task, work, object, matterIds);");
					internalMattersHandler.acceptMatters(task, work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
		 */
		public void acceptMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.acceptMatter(task, work, object, matterId);");
					internalMattersHandler.acceptMatter(task, work, object, matterId);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#acceptMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
		 */
		public void acceptMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId, final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.acceptMatter(task, work, object, matterId, tag);");
					internalMattersHandler.acceptMatter(task, work, object, matterId, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
		 */
		public void readyMatters(final Task task, final Work work, final RFSObject object,
				final Long[] matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.readyMatters(task, work, object, matterIds);");
					internalMattersHandler.readyMatters(task, work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
		 */
		public void readyMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.readyMatter(task, work, object, matterId);");
					internalMattersHandler.readyMatter(task, work, object, matterId);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#readyMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
		 */
		public void readyMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId, final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.readyMatter(task, work, object, matterId, tag);");
					internalMattersHandler.readyMatter(task, work, object, matterId, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[])
		 */
		public void finishMatters(final Task task, final Work work, final RFSObject object,
				final Long[] matterIds) {
			addSchemeTask(SchemeTask.MATTERS_HANDLER_FINISH_NO_TAG, new Runnable() {
				public void run() {
					log.debug("MattersHandler.finishMatters(task, work, object, matterIds);");
					internalMattersHandler.finishMatters(task, work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
		 */
		public void finishMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId) {
			addSchemeTask(SchemeTask.MATTERS_HANDLER_FINISH_NO_TAG, new Runnable() {
				public void run() {
					log.debug("MattersHandler.finishMatter(task, work, object, matterId);	");
					internalMattersHandler.finishMatter(task, work, object, matterId);					
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short)
		 */
		public void finishMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId, final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.finishMatter(task, work, object, matterId, tag);");
					internalMattersHandler.finishMatter(task, work, object, matterId, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatter(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long, short, java.lang.String)
		 */
		public void finishMatter(final Task task, final Work work, final RFSObject object,
				final Long matterId, final short tag, final String note) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.finishMatter(task, work, object, matterId, tag, note);");
					internalMattersHandler.finishMatter(task, work, object, matterId, tag, note);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#finishMatters(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.lang.Long[], java.lang.String)
		 */
		public void finishMatters(final Task task, final Work work, final RFSObject object,
				final Long[] matterIds, final String note) {
			addSchemeTask(SchemeTask.MATTERS_HANDLER_FINISH_NO_TAG, new Runnable() {
				public void run() {
					log.debug("MattersHandler.finishMatters(task, work, object, matterIds, note);");
					internalMattersHandler.finishMatters(task, work, object, matterIds, note);
				}});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void hang(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.hang(work, object, matterIds);");
					internalMattersHandler.hang(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void hang(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.hang(work, object, matterIds, tag);");
					internalMattersHandler.hang(work, object, matterIds, tag);
				}
				
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void wake(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.wake(work, object, matterIds);");
					internalMattersHandler.wake(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void wake(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.wake(work, object, matterIds, tag);");
					internalMattersHandler.wake(work, object, matterIds, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#avoid(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void avoid(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.avoid(work, object, matterIds);");
					internalMattersHandler.avoid(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#avoid(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void avoid(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.avoid(work, object, matterIds, tag);");
					internalMattersHandler.avoid(work, object, matterIds, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#cancel(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void cancel(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.cancel(work, object, matterIds);");
					internalMattersHandler.cancel(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#cancel(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void cancel(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.cancel(work, object, matterIds, tag)");
					internalMattersHandler.cancel(work, object, matterIds, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#stop(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void stop(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.stop(work, object, matterIds)");
					internalMattersHandler.stop(work, object, matterIds);
				}
			});
		}

		
		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#stop(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void stop(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.stop(work, object, matterIds, tag)");
					internalMattersHandler.stop(work, object, matterIds, tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void withdraw(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.withdraw(work, object, matterIds)");
					internalMattersHandler.withdraw(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#withdraw(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void withdraw(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.withdraw(work, object, matterIds, tag)");
					internalMattersHandler.withdraw(work, object, matterIds,tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void reject(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.reject(work, object, matterIds)");
					internalMattersHandler.reject(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#reject(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void reject(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.reject(work, object, matterIds, tag)");
					internalMattersHandler.reject(work, object, matterIds,tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void transfer(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.transfer(work, object, matterIds)");
					internalMattersHandler.transfer(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#transfer(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void transfer(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.transfer(work, object, matterIds, tag)");
					internalMattersHandler.transfer(work, object, matterIds,tag);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#undo(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
		 */
		public void undo(final Work work, final RFSObject object, final List<Long> matterIds) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.transfer(work, object, matterIds, tag)");
					internalMattersHandler.undo(work, object, matterIds);
				}
			});
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.scheme.MattersHandler#undo(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List, short)
		 */
		public void undo(final Work work, final RFSObject object, final List<Long> matterIds,
				final short tag) {
			addSchemeTask(new Runnable() {
				public void run() {
					log.debug("MattersHandler.transfer(work, object, matterIds, tag)");
					internalMattersHandler.undo(work, object, matterIds,tag);
				}
			});
		}
	}
	
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addActionError(java.lang.String)
	 */
	public void addActionError(String anErrorMessage) {
		validation.addActionError(anErrorMessage);		
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addActionMessage(java.lang.String)
	 */
	public void addActionMessage(String message) {
		validation.addActionMessage(message);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addFieldError(java.lang.String, java.lang.String)
	 */
	public void addFieldError(String fieldName, String errorMessage) {
		validation.addFieldError(fieldName, errorMessage);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getActionErrors()
	 */
	public Collection<?> getActionErrors() {
		return validation.getActionErrors();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getActionMessages()
	 */
	public Collection<?> getActionMessages() {
		return validation.getActionMessages();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getFieldErrors()
	 */
	public Map<?, ?> getFieldErrors() {
		return validation.getFieldErrors();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasActionErrors()
	 */
	public boolean hasActionErrors() {
		return validation.hasActionErrors();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasActionMessages()
	 */
	public boolean hasActionMessages() {
		return validation.hasActionMessages();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasErrors()
	 */
	public boolean hasErrors() {
		return validation.hasErrors();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasFieldErrors()
	 */
	public boolean hasFieldErrors() {
		return validation.hasFieldErrors();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setActionErrors(java.util.Collection)
	 */
	public void setActionErrors(@SuppressWarnings("rawtypes") Collection errorMessages) {
		validation.setActionErrors(errorMessages);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setActionMessages(java.util.Collection)
	 */
	public void setActionMessages(@SuppressWarnings("rawtypes") Collection messages) {
		validation.setActionMessages(messages);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setFieldErrors(java.util.Map)
	 */
	public void setFieldErrors(@SuppressWarnings("rawtypes") Map errorMap) {
		validation.setFieldErrors(errorMap);
	}
}
