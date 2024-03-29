/*
 * $Id: TaskWorkHandlerForWorkSchemeInterceptor.java 6240 2013-06-24 01:37:53Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.interceptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.service.AttachmentManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import cn.redflagsoft.base.aop.Callback;
import cn.redflagsoft.base.bean.BizTrackNodeInstance;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.MattersHandler;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeTask;
import cn.redflagsoft.base.scheme.SchemeTaskManager;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.event.WorkSchemeEvent;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.DatumService;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.ObjectsService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.vo.DatumVOList;

/**
 * 这个拦截器一定要放置在ParametersInterceptor之后。
 * 
 * @author Alex Lin
 *
 */
public class TaskWorkHandlerForWorkSchemeInterceptor implements MethodInterceptor, InitializingBean  {
	public static final int DATUM_UPLOAD_PROCESS_TYPE = 2204;
	private Log log = LogFactory.getLog(TaskWorkHandlerForWorkSchemeInterceptor.class);
	
	//private IdMultiPartHashGenerator hashGenerator = new IdMultiPartHashGenerator(2, 100, 3);
	private AttachmentManager attachmentManager;
	private ObjectsService objectsService;
	private WorkService workService;
	private TaskService taskService;
	private DatumService datumService;
	private EventMsgService eventMsgService;
//	private MattersHandler mattersHandler;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;
	private EventDispatcher eventDispatcher;
	private Executor executor;
//	private WorkDefProvider workDefProvider;
	
	
//	/**
//	 * @param workDefProvider the workDefProvider to set
//	 */
//	@Required
//	public void setWorkDefProvider(WorkDefProvider workDefProvider) {
//		this.workDefProvider = workDefProvider;
//	}

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Object object = invocation.getThis();
		Callback action = new Callback.MethodCallback(invocation);
		return handle(action, object);
	}
	
	public Object handle(Callback action, Object object) throws Throwable {
		if(log.isDebugEnabled()){
			log = LogFactory.getLog(object.getClass());
		}

//		System.out.println("TaskWorkHandlerForWorkSchemeInterceptor - " + action);
//		System.out.println("TaskWorkHandlerForWorkSchemeInterceptor - " + object);
		
		if(!(object instanceof WorkScheme)){
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 拦截的对象不是WorkScheme，放弃去Work的处理：" + object);
			}
			return action.doInCallback();
		}
		
		WorkScheme ws = (WorkScheme) object;
//		EventDispatcher<WorkSchemeEvent> dispatcher = (ws instanceof EventDispatcher) ? (EventDispatcher<WorkSchemeEvent>) ws : null;
		List<Long> matterIds = ws.getMatterIds();
		Task task = ws.getTask();
		Work work = ws.getWork();
		RFSObject rfsobj = ws.getObject();
		MattersHandler mattersHandler = ws.getMattersHandler();
		SchemeTaskManager schemeTaskManager = ws.getSchemeTaskManager();
		
		//TODO 使用UserClerkHolder
		Clerk clerk = UserClerkHolder.getClerk();
		if(ws instanceof AbstractWorkScheme){
			clerk = ((AbstractWorkScheme)ws).getClerk();
		}
		Long clerkID = clerk.getId();//100L;
//		Long entityID = clerk.getEntityID();//100L;
		
		if(matterIds == null){
			throw new SchemeException("缺少事项ID，WorkScheme无法执行。");
		}
		//是不是报错？
		if(rfsobj == null){
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 业务对象为空: " + this);
			}
			//通常不会出现这种情况，在ws.getObject()中已经判断
			throw new SchemeException("TaskWorkHandlerForWorkSchemeInterceptor - 业务对象为空");
		}else{
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 业务对象: " + rfsobj);
			}
		}
	        
		Long[] mids = matterIds.toArray(new Long[matterIds.size()]);
		Long objectId = rfsobj.getId();
		
		//如果没有work，则需要创建新的work
		//Long workSN = work == null ? workService.generateId() : work.getSn();
		Long taskSN = task == null ? taskService.generateId() : task.getSn();

		/*
		if(task == null){
			//task = taskService.createTask(clerkId, type, matterIds, objectId, sn)
			task = new Task();
			task.setSn(taskSN);
			task.setType(ws.getTaskType());
			task.setActiveWorkSN(workSN);
			task.setClerkID(clerkId);
			task = taskService.createTask(task, mids, objectId);
			ws.setTask(task);
			if(dispatcher != null){
				dispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.TASK_CREATED, ws));
			}
		}*/
		
		if(work == null){
			//work = workService.createWork(clerkID, taskSN, ws.getWorkType(), mids);
			work = workService.createWork(clerk, rfsobj, taskSN, ws.getWorkType(), mids, null, ws.getWorkBeginTime(), ws.getWorkEndTime());
			ws.setWork(work);
//			if(dispatcher != null){
//				dispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.WORK_CREATED, ws));
//			}
			if(eventDispatcher != null){
				eventDispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.Type.WORK_CREATED, ws));
			}
			
			
			if(task == null){
//				//执行前已经通过workScheme的validate()方法检查了workType不为空
//				//在2011-06-21去除了taskType的检查
//				Short tempTaskType = ws.getTaskType();
//				if(tempTaskType == null){
//					Short tempWorkType = ws.getWorkType();
//					if(tempWorkType == null){
//						tempWorkType = work.getType();
//					}
//					if(tempWorkType != null){
//						WorkDef workDef = workDefProvider.getWorkDef(tempWorkType);
//						if(workDef != null){
//							tempTaskType = workDef.getTaskType();
//							if(ws instanceof AbstractWorkScheme){
//								((AbstractWorkScheme)ws).setTaskType(tempTaskType);
//							}
//						}
//					}
//				}
//				if(tempTaskType == null){
//					throw new SchemeException("TaskType不能为空，请配置或者指定。");
//				}
				
				task = new Task();
				task.setSn(taskSN);
				task.setType(/*tempTaskType*/ws.getTaskType());
				task.setActiveWorkSN(work.getSn());
				task.setClerkID(clerkID);			//当前办理人
				task.setClerkName(clerk.getName());
				//@since 2.0.2
				task.setBeginTime(work.getBeginTime());
				//task = taskService.createTask(task, mids, objectId, (short) 1, entityID);
				//FIXME: 责任人暂时没有，责任人可能应该从配置中读取
				Long dutyerID = clerkID;//2012-11-02 该为clerkID
				task = taskService.createTask(task, mids, rfsobj, (short) 1, dutyerID);
				//2013-06-24  最后一个参数 bizAction 由 1 改为 0。 为 1 时，在 MatterAffair 的处理中，可能会重复调用为 1 的dealEventMsg方法，
				//导致产生重复的消息。
				eventMsgService.dealEventMsg(rfsobj.getObjectType(),objectId, task.getType(),task.getSn(), work.getType(), (short)0);
				ws.setTask(task);
//				if(dispatcher != null){
//					dispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.TASK_CREATED, ws));
//				}
				if(eventDispatcher != null){
					eventDispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.Type.TASK_CREATED, ws));
				}
				mattersHandler.acceptMatters(task, work, rfsobj, mids);
			}
			
			//关联work和task
			//task.setActiveWorkSn(work.getSn());
		    bizTrackNodeInstanceService.createBizTrackNodeInstance(BizTrackNodeInstance.CATEGORY_WORK, work.getType(), work.getSn(), task.getBizTrack());      
		}
		
		if(ws.getWorkEndTime() != null){
			work.setEndTime(ws.getWorkEndTime());
		}
		if(ws.getWorkHangTime() != null){
			work.setHangTime(ws.getWorkHangTime());
		}
		if(ws.getWorkWakeTime() != null){
			work.setWakeTime(ws.getWorkWakeTime());
		}
		
		int executedSchemeTasks = schemeTaskManager.executeAllSchemeTasks();
		if(log.isDebugEnabled()){
			log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 在Scheme方法执行前执行的SchemeTask数量为: " + executedSchemeTasks);
		}
		
		if(eventDispatcher != null){
			eventDispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.Type.BEFORE_EXECUTE, ws));
		}
		 
		Object result;
		try{
			result = action.doInCallback();
		}finally{
			//记录 Work 调用情况
			saveLogToFileSystemInTask(ws, action, rfsobj, task, work);
		}
		
		Task taskAfter = ws.getTask();
		Work workAfter = ws.getWork();//可能为空，为空时不结束Work
		
		if(workAfter == null){
			//为空时不结束Work，从新查询Work信息
			workAfter = getWorkService().getWork(work.getSn());
			ws.setWork(workAfter);
		}else{
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - terminateWork：" + workAfter);
			}
			workService.terminateWork(workAfter, (byte) 0, ws.getWorkEndTime());
			//task.setActionWorkSn(0);
			//新获取更改后的work，用于之后的MatterAffair处理。
			//workAfter = getWorkService().getWork(work.getSn());
			//ws.setWork(workAfter);
		}
		
		//since 2.0.2, 2011-6-24
		//处理task，work与普通业务对象之间的关系
		List<RFSEntityObject> bizItemSet = ws.getRelatedObjects();
		int bizItemSize = bizItemSet.size();
		RFSEntityObject[] bizItems = new RFSEntityObject[bizItemSize];
		if(bizItemSize > 0){
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 保存业务对象与task、work之间的关系");
			}
			//则保存业务对象与work之间的关系
			bizItems = bizItemSet.toArray(new RFSEntityObject[bizItemSize]);
			getWorkService().updateWorkEntityObjects(workAfter, bizItems);
			getTaskService().updateTaskEntityObjects(taskAfter, bizItems);
		}
		
		
		//处理资料
		DatumVOList datumList = ws.getDatumVOList();
		if(datumList != null && datumList.size() > 0){
			if(log.isDebugEnabled()){
				log.debug("TaskWorkHandlerForWorkSchemeInterceptor - 处理资料：" + datumList);
			}
			/*List<Datum> ld =*/ 
//			datumService.processDatum((byte)1, task, work, DATUM_UPLOAD_PROCESS_TYPE, rfsobj, ws.getMatterVO(), datumList, clerk);
			datumService.processDatum((byte)1, taskAfter, workAfter, DATUM_UPLOAD_PROCESS_TYPE,  ws.getMatterVO(), datumList, clerk, rfsobj, bizItems);
		}
		
		if(ws.getFileIds() != null){
			saveAttachements(rfsobj, taskAfter, workAfter, ws.getFileIds());
		}
		
		if(eventDispatcher != null){
			eventDispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.Type.AFTER_EXECUTE, ws));
		}
		
		//调用SchemeTask
		//SchemeTaskManager schemeTaskManager = ws.getSchemeTaskManager();
		int noTagFinishMattersTaskCount = schemeTaskManager.getNumberOfSchemeTasks(SchemeTask.MATTERS_HANDLER_FINISH_NO_TAG);
		if(noTagFinishMattersTaskCount == 0){
			//log.debug("在WorkScheme的do方法中没有调用finishMatters方法，所以在这里重新调用。");
			//mattersHandler.finishMatters(taskAfter, workAfter, rfsobj, mids);
			//noTagFinishMattersTaskCount = 1;
			//XXX:
			//在部分操作，比如转发、分发中，自动调用finishMatters是有问题的，所以取消自动调用机制。
			log.debug("*** 在WorkScheme的do方法中没有调用finishMatters方法 ****");
		}
		
		int executedTaskCount = schemeTaskManager.executeAllSchemeTasks();
		if(log.isDebugEnabled()){
			log.debug("延迟调用了SchemeTask，执行数量：" + executedTaskCount
					+ ", 其中没有tag的fininMatters调用次数为：" + noTagFinishMattersTaskCount);
		}
		
		if(eventDispatcher != null){
			eventDispatcher.dispatchEvent(new WorkSchemeEvent(WorkSchemeEvent.Type.BEFORE_RETURN, ws));
		}
		return result;
	}
	
	
	/**
	 * 在线程池中执行saveLogToFileSystem方法。
	 * 
	 * @param ws
	 * @param action
	 * @param rfsobj
	 * @param task
	 * @param work
	 */
	private void saveLogToFileSystemInTask(final WorkScheme ws, final Callback action, final RFSObject rfsobj, 
			final Task task, final Work work) {
		if(AppsGlobals.getSetupProperty("workscheme.logToFile", true)){
			getExecutor().execute(new Runnable(){
				public void run() {
					saveLogToFileSystem(ws, action, rfsobj, task, work);
				}}
			);
		}
	}
	/**
	 * @param parameters
	 * @param rfsobj
	 * @param task
	 * @param work
	 */
	@SuppressWarnings("unchecked")
	private void saveLogToFileSystem(WorkScheme ws, Callback action, RFSObject rfsobj, Task task, Work work) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("WorkScheme");
		root.addAttribute("callback", action.toString());
		root.addAttribute("class", ws.getClass().getName());
		root.addAttribute("version", "2");
		
		Element config = root.addElement("config");
		config.addAttribute("taskType", ws.getTaskType() + "");
		config.addAttribute("workType", ws.getWorkType() + "");
		config.addAttribute("matterIds", ws.getMatterIds() + "");
		config.addAttribute("name", ws.getDisplayName());
		config.addAttribute("beanName", ws.getBeanName());
		config.addAttribute("method", ws.getMethod());

		if(ws.getParameters() != null){
			Element params = root.addElement("parameters");
			Map<?,?> map = new HashMap<Object,Object>(ws.getParameters());
			for(Map.Entry<?, ?> en: map.entrySet()){
				Object key = en.getKey();
				Object value = en.getValue();
				Element element = params.addElement("param");
				if(key != null){
					element.addAttribute("name", key.toString());
				}
				if(value != null){
					if(value instanceof String[]){
						String[] values = (String[]) value;
						if(values.length > 0){
							element.setText(values[0]);
						}
					}else{
						element.setText(value.toString());
					}
				}
			}
		}


		Element node = root.addElement("object");
		node.addAttribute("id", rfsobj.getId() + "");
		node.addAttribute("objectType", rfsobj.getObjectType() + "");
		node.addAttribute("name", rfsobj.getName());
		node.addAttribute("class", rfsobj.getClass().getName());

		node = root.addElement("task");
		node.addAttribute("sn", task.getSn() + "");
		node.addAttribute("type", task.getType() + "");
		node.addAttribute("name", task.getName());
		
		node = root.addElement("work");
		node.addAttribute("sn", work.getSn() + "");
		node.addAttribute("type", work.getType() + "");
		
		Element env = root.addElement("env");
		UserClerk user = UserClerkHolder.getNullableUserClerk();
		if(user != null){
			Element userNode = env.addElement("user");
			userNode.addAttribute("userId", user.getClerk().getId() + "");
			userNode.addAttribute("username", user.getUser().getUsername());
			userNode.addAttribute("name", user.getClerk().getName());
			userNode.addAttribute("orgName", user.getClerk().getEntityName());
		}
		node = env.addElement("timestamp");
		node.addAttribute("value", AppsGlobals.formatDateTime(new Date()));
		
		
		List<RFSItemable> items = ws.getRelatedItems();
		if(items != null && items.size() > 0){
			Element itemsNode = root.addElement("items");
			for(RFSItemable item: items){
				Element itemNode = itemsNode.addElement("item");
				itemNode.addAttribute("id", item.getId() + "");
				itemNode.addAttribute("objectType", item.getObjectType() + "");
				itemNode.addAttribute("class", item.getClass().getName());
			}
		}
		
		
		try {
			File saveDir = buildWorkSchemeSaveDir(ws, action, rfsobj, task, work);
			File file = new File(saveDir, task.getSn() + "-" + work.getSn() + ".xml");
			FileWriter writer = new FileWriter(file);
			XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
			xmlWriter.write(doc);
			
			xmlWriter.close();
			IOUtils.closeQuietly(writer);
			
			log.debug("保存WorkScheme请求信息到：" + file);
		} catch (IOException e) {
			log.error("保存WorkScheme请求信息到文件时出错", e);
		}
	}
	
	
	private static final SimpleDateFormat dirFormat = new SimpleDateFormat("yyyyMM");
	private File buildWorkSchemeSaveDir(WorkScheme ws, Callback action, RFSObject rfsobj, Task task, Work work){
		File dir = new File(AppsHome.getAppsHome(), "workscheme");
//		List<Integer> list = hashGenerator.generate(work.getSn());
//		for(Integer id: list){
//			dir = new File(dir, String.valueOf(id));
//		}
//		
//		if(!dir.exists()){
//			dir.mkdirs();
//		}
		
		dir = new File(dir, ws.getClass().getName());
		dir = new File(dir, dirFormat.format(new Date()));
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}
	

	/**
	 * @param object
	 * @param task
	 * @param work
	 * @param list 
	 */
	private void saveAttachements(RFSObject object, Task task, Work work, List<Long> list) {
		objectsService.saveAttachments(task, work, list);
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
	 * @return the datumService
	 */
	public DatumService getDatumService() {
		return datumService;
	}
	/**
	 * @param datumService the datumService to set
	 */
	public void setDatumService(DatumService datumService) {
		this.datumService = datumService;
	}
//	/**
//	 * @return the mattersHandler
//	 */
//	public MattersHandler getMattersHandler() {
//		return mattersHandler;
//	}
//	/**
//	 * @param mattersHandler the mattersHandler to set
//	 */
//	public void setMattersHandler(MattersHandler mattersHandler) {
//		this.mattersHandler = mattersHandler;
//	}
	/**
	 * @return the bizTrackNodeInstanceService
	 */
	public BizTrackNodeInstanceService getBizTrackNodeInstanceService() {
		return bizTrackNodeInstanceService;
	}
	/**
	 * @param bizTrackNodeInstanceService the bizTrackNodeInstanceService to set
	 */
	public void setBizTrackNodeInstanceService(
			BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}
	
	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(workService);
		Assert.notNull(taskService);
		Assert.notNull(datumService);
//		Assert.notNull(mattersHandler);
		Assert.notNull(bizTrackNodeInstanceService);		
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher arg0) {
		this.eventDispatcher = arg0;
	}

	/**
	 * @return the eventDispatcher
	 */
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	/**
	 * @return the attachmentManager
	 */
	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	/**
	 * @param attachmentManager the attachmentManager to set
	 */
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
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
	public void setObjectsService(ObjectsService objectsService) {
		this.objectsService = objectsService;
	}

	public Executor getExecutor() {
		return executor;
	}
	@Required
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
}
