package cn.redflagsoft.base.scheme;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clue;
import cn.redflagsoft.base.bean.Job;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.ClueService;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.JobService;
import cn.redflagsoft.base.service.MatterAffairService;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.ObjectsService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.ThreadService;
import cn.redflagsoft.base.service.WorkService;

/**
 * 
 * @deprecated 仅作备份。
 */
public abstract class MattersHandlerImplBAK implements MattersHandler {
	private static final Log log = LogFactory.getLog(MattersHandlerImplBAK.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	
	private TaskService taskService;
	private WorkService workService;
	private JobService jobService;
	@SuppressWarnings("unchecked")
	private ObjectService objectService;
	private MatterAffairService matterAffairService;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;
	private ThreadService threadService;
	private ClueService clueService;
	private ObjectsService objectsService;
	private EventMsgService eventMsgService;

	
	
	

	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public WorkService getWorkService() {
		return workService;
	}

	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}
	
	public MatterAffairService getMatterAffairService() {
		return matterAffairService;
	}
	
	public void setMatterAffairService(MatterAffairService matterAffairService) {
		this.matterAffairService = matterAffairService;
	}

	public BizTrackNodeInstanceService getBizTrackNodeInstanceService() {
		return bizTrackNodeInstanceService;
	}

	public void setBizTrackNodeInstanceService(
			BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}

	public ThreadService getThreadService() {
		return threadService;
	}

	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	public ClueService getClueService() {
		return clueService;
	}

	public void setClueService(ClueService clueService) {
		this.clueService = clueService;
	}
	
	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public ObjectsService getObjectsService() {
		return objectsService;
	}

	public void setObjectsService(ObjectsService objectsService) {
		this.objectsService = objectsService;
	}

	@SuppressWarnings("unchecked")
	public ObjectService getObjectService() {
		return objectService;
	}

	@SuppressWarnings("unchecked")
	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}

	/**
	 * 
	 */
	public void acceptMatter(Task task, Work work, RFSObject object, Long matterId) {
		acceptMatter( task, work,  object, matterId,(short)0) ;		
	}
	
	public void acceptMatter(Task task, Work work, RFSObject object, Long matterId,short tag) {
		//processWorkAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		
		//处理task的关联操作
		//processTaskAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		//处理work的辅助关联操作
		//processWorkAffair(task, work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object);
		
		processWorkAffair(work, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object, tag, null, null);
		
		processTaskAffair(task, matterId, MatterAffair.ACTION_ACCEPT_MATTER, object,tag);		
	}

	public void acceptMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else acceptMatter(task, work, object, matterId);
			}
		}
	}

	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId) {
		finishMatter(task, work, object,matterId,(short)0);
	}
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId,short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_END_MATTER, object,tag,null, null);
	}

	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else finishMatter(task, work, object, matterId);
			}
		}
	}
	
	public void finishMatters(Task task, Work work, RFSObject object,
			Long[] matterIds,String note) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else finishMatter(task, work, object, matterId,(short)0,note);
			}
		}
	}
	
//	public void finishMatters(Task task, Work work, RFSObject object,
//			Long[] matterIds, short tag, String note,
//			List<RFSItemable> relatedItems) {
//		if (matterIds != null) {
//			for (Long matterId : matterIds) {
//				if (matterId == null) {
//					continue;
//				} else {
//					// finishMatter(task, work, object, matterId,tag,note);
//					processWorkAffair(work, matterId,
//							MatterAffair.ACTION_END_MATTER, object, tag, note,
//							relatedItems);
//				}
//			}
//		}
//	}
	
	public void finishMatter(Task task, Work work, RFSObject object,
			Long matterId,short tag,String note) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_END_MATTER, object,tag,note,null);
	}
	
	
	
	public void readyMatter(Task task, Work work, RFSObject object,	Long matterId) {
		readyMatter(task,work, object,matterId,(short)0) ;
	}
	public void readyMatter(Task task, Work work, RFSObject object,	Long matterId,short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_DO_END_MATTER, object,tag,null,null);
	}

	public void readyMatters(Task task, Work work, RFSObject object,
			Long[] matterIds) {
		if(matterIds != null){
			for(Long matterId : matterIds){
				if(matterId == null) continue;
				else readyMatter(task, work, object, matterId);
			}
		}
	}
	
	/**
	 * 
	 * @param work
	 * @param object
	 * @param matterId
	 */
	public void hang(Work work, RFSObject object, Long matterId){
		hang(work, object,matterId,(short)0);
	}
	public void hang(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_HANG, object,tag,null,null);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#hang(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void hang(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			hang(work, object, matterId);
		}
	}
	
	public void hang(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			hang(work, object, matterId,tag);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.MattersHandler#wake(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSObject, java.util.List)
	 */
	public void wake(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			wake(work, object, matterId);
		}
	}	
	
	public void wake(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			wake(work, object, matterId,tag);
		}
	}	
	/**
	 * 
	 * @param work
	 * @param object
	 * @param matterId
	 */
	public void wake(Work work, RFSObject object, Long matterId){
		wake(work,object,matterId,(short)0);
	}

	public void wake(Work work, RFSObject object, Long matterId, short tag) {
		processWorkAffair(work, matterId, MatterAffair.ACTION_WAKE, object, tag, null,null);
	}
	
	
	
	public void avoid(Work work, RFSObject object, Long matterId){
		avoid(work, object,matterId,(short)0);
	}
	public void avoid(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_AVOID, object,tag,null, null);
	}
	public void avoid(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			avoid(work, object, matterId);
		}
	}
	public void avoid(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			avoid(work, object, matterId,tag);
		}
	}
	
	public void cancel(Work work, RFSObject object, Long matterId){
		cancel(work, object,matterId,(short)0);
	}
	public void cancel(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_CANCEL, object,tag,null, null);
	}
	public void cancel(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			cancel(work, object, matterId);
		}
	}
	public void cancel(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			cancel(work, object, matterId,tag);
		}
	}
	
	
	public void stop(Work work, RFSObject object, Long matterId){
		stop(work, object,matterId,(short)0);
	}
	public void stop(Work work, RFSObject object, Long matterId,short tag){
		processWorkAffair(work, matterId, MatterAffair.ACTION_STOP, object,tag,null,null);
	}
	public void stop(Work work, RFSObject object, List<Long> matterIds) {
		for(Long matterId: matterIds){
			stop(work, object, matterId);
		}
	}
	public void stop(Work work, RFSObject object, List<Long> matterIds,short tag) {
		for(Long matterId: matterIds){
			stop(work, object, matterId,tag);
		}
	}
	
	
	/**************** new ********************/
	protected void processWorkAffair(Work work, Long bizId, Byte bizAction, RFSObject object, short tag, String note, List<RFSItemable> relatedItems) {
        Long objectID = null;
        Long affairID = null;
        Long dutyerID = null;        
        Task task = null;
        Long taskSn = null;
        Clue clue = null;
        int bizType;
        byte affairCategory;
        byte affairAction;
        byte action;
        int lifeStage;
        byte objectStatus;
        byte objectKeyMatter;
		short dutyerType;
		short objectTag;	
	    short clerkType;
	    Long clerkID;
        
        if (work == null){
           return;
        }
        if (object !=null ){
           objectID=object.getId();
        }
        
        List<MatterAffair> matterAffairs = matterAffairService.findAffairs(MatterAffair.CATEGORY_WORK, bizId, bizAction,tag);
        
        for (MatterAffair element : matterAffairs) {
        	affairCategory = element.getAffairCategory();
        	affairID = element.getAffairID();
        	affairAction= element.getAffairAction();
            bizType = element.getBizType();
            action = element.getAction();
            lifeStage = element.getObjectLifeStage();
            objectTag = element.getObjectTag();
            dutyerID = element.getDutyerID();
            dutyerType = element.getDutyerType();
            objectStatus = element.getObjectStatus();
            objectKeyMatter = element.getObjectKeyMatter();
            clerkType=element.getClerkType();
            clerkID=element.getClerkID();
            
            if(dutyerID == null || dutyerID.intValue() == 0) {
            	dutyerID = object.getManager() == null ? 0L : object.getManager();
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
            // 1.查找（查找活动的clue和task。）
            //////////////////////////////////////////////////////////////////////////////////////////////
            task = null;
			//if ((action == 1) || (action == 2) || (action == 3) || (action == 4) || (action == 5) || (action == 21) || (action == 31) || (action == 32) || (action == 33)) {
			if (action == 21 || action == 34 || action == 37 || action == 40 || action == 43 || action == 46) {
				taskSn = work.getTaskSN();
				task = taskService.getTask(taskSn);
				if(IS_DEBUG_ENABLED){
					log.debug("从Work的taskSN中查找Task：" + task);
				}
			} else {
				// System.out.println("=========================================");
				// System.out.println("=====objectID===="+objectID);
				// System.out.println("=====affairCategory===="+affairCategory);
				// System.out.println("=====bizType===="+bizType);
				// System.out.println("=====affairID===="+affairID);
				// System.out.println("=========================================");
				clue = clueService.getActiveClue(objectID, affairCategory, bizType, affairID);

				if (IS_DEBUG_ENABLED) {
					log.debug(String.format("processWorkAffair - 查询活动的Clue： " + "objectId=%s, affairCategory=%s, "
							+ "bizType=%s, affairID=%s | clue=", objectID, affairCategory, bizType, affairID)
							+ clue);
				}

				if (clue != null) {
					taskSn = clue.getBizSN();
					task = taskService.getTask(taskSn);
					if(IS_DEBUG_ENABLED){
						log.debug("从ActiveClue的bizSN中查找Task：" + task);
					}
				}
			}
			// }
           
            /*if ((action == 1)||(action == 2)||(action == 3)||(action == 4)||(action == 5)) {
                clue = clueService.getActiveClue(objectID, affairCategory, bizType, affairID);
                if (clue != null) {
                   taskSn = clue.getBizSN();
                   task = taskService.getTask(taskSn);
                }
            }*/
			
			///////////////////////////////////////////////////////////////////////////////////////////
            // 2.处理
			///////////////////////////////////////////////////////////////////////////////////////////
			if(task != null){
	        	//结束（1：若有则结束并创建并关联，若无直接创建并关联； 4：若有则关联，然后结束。）
	        	if (action == 1 || action == 4 || action == 5 || action == 21 || action == 31 || action == 33) {
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 结束Task：" + task);
	        		}
	        		
	        		taskService.terminateTask(task.getSn(), null);
	        	}else if(action == 34 || action == 36){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 取消Task(action=" + action + ")[34,36]：" + task);
	        		}
	        		
	        		taskService.cancelTask(task);
	        	}else if(action == 37 || action == 39){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 中止Task(action=" + action + ")[37,39]：" + task);
	        		}
	        		
	        		taskService.stopTask(task);
	        	}else if(action == 40 || action == 42){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 免办Task(action=" + action + ")[40,42]：" + task);
	        		}
	        		
	        		taskService.avoidTask(task);
	        	}else if(action == 43 || action == 45){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 暂停Task(action=" + action + ")[43,45]：" + task);
	        		}
	        		
	        		taskService.hangTask(task.getSn());
	        	}else if(action == 46 || action == 48){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 唤醒Task(action=" + action + ")[46,48]：" + task);
	        		}
	        		
	        		taskService.wakeTask(task.getSn());
	        	}
			}
        	
        	
			/////////////////////////////////////////////////////////////////////////////////////////////
			// 3.创建
			////////////////////////////////////////////////////////////////////////////////////////////
        	//创建（1：若有则结束并创建并关联，若无直接创建并关联； 2：若有则关联，若无则创建并关联；）
        	/**
        	 * 修改时间 2009-03-06 14:09 by ymq
        	 * 
        	 * 修改createTask方法，原方法clerkId从work.getClerkID获取，现修改为获取系统登录用UserClerkHolder.getClerk().getId()
        	 * 
        	 * 2009-07-01 ck clerid改从object的dutyClerkID获取
        	 */
        	if (action == 1 || action == 2 || action == 31
        			|| (action == 32 && task == null) 
        			|| (action == 33 && task == null)
        			|| (action == 35 && task == null)
        			|| (action == 36 && task == null)
        			|| (action == 38 && task == null)
        			|| (action == 39 && task == null)
        			|| (action == 41 && task == null)
        			|| (action == 42 && task == null)
        			|| (action == 44 && task == null)
        			|| (action == 45 && task == null)
        			|| (action == 47 && task == null)
        			|| (action == 48 && task == null)) {
        		if(clerkType == 0){//当前创建人
        			clerkID = UserClerkHolder.getClerk().getId();
        		}else if(clerkType==1){//对象责任人
        			if(object.getDutyClerkID() != null){
        				clerkID =  object.getDutyClerkID();//如果不能确定责人类型，则业务的办理人员设成 对象的责任人ID
        			}
        		}
        		if(clerkID == null){//如果clerkID仍为空
        			clerkID = UserClerkHolder.getClerk().getId();
        		}
        		
    			if(IS_DEBUG_ENABLED){
    				//log.debug("Task使用的ClerkID：" + clerkID);
    				String s = String.format("processWorkAffair - 创建(action=%s)Task：clerkID=%s, bizType=%s, affairID=%s, " +
    						"objectID=%s, dutyerType=%s, dutyerID=%s, note=%s", action, clerkID, bizType, affairID, objectID, dutyerType, dutyerID, note);
    				log.debug(s);
    			}
    			
        		task = taskService.createTask(clerkID, bizType, new Long[]{affairID}, objectID, dutyerType, dutyerID, note);
        	}
        	
        	//
        	if(task == null && IS_DEBUG_ENABLED){
        		log.warn("processWorkAffair - 在执行taskService.createTask后，task仍然为null，后续代码不执行。");
        	}
        	
        	/////////////////////////////////////////////////////////////////////////////////////////////////
        	// 4.关联
        	/////////////////////////////////////////////////////////////////////////////////////////////////
        	//关联（1：若有则结束并创建并关联，若无直接创建并关联；2：若有则关联，若无则创建并关联；3：若有则关联，若无不关联；
            // 4：若有则关联，然后结束。其他：无操作。把该结束的，结束。）	        	
        	//task一定不为null
        	if (task != null && (action == 1 || action == 2 || action == 3 || action == 4 || action == 31 || action == 32 || action == 33 
        			|| action == 35 || action == 36 || action == 38 || action == 39
        			|| action == 41 || action == 42 || action == 44 || action == 45
        			|| action == 47 || action == 48)) {
        		bizTrackNodeInstanceService.createBizTrackNodeInstance(MatterAffair.CATEGORY_WORK, work.getType(), work.getSn(), task.getBizTrack());
				if (IS_DEBUG_ENABLED) {
					String s = String.format("processWorkAffair - 创建(action=%s)BizTrackNodeInstance：category=%s, workType=%s, workSN=%s, task.bizTrack=%s",
									action, MatterAffair.CATEGORY_WORK, (work == null) ? "" : work.getType(),
									(work == null) ? "" : work.getSn(), (task == null) ? "" : task.getBizTrack());
					log.debug(s);
				}
        	}
        	
        	////////////////////////////////////////////////////////////////////////////////////////
        	// 5.处理
        	////////////////////////////////////////////////////////////////////////////////////////
        	if(task != null){
				if (action == 31 || action == 32 || action == 33) {
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 结束Task(action=" + action + ")[31,32,33]：" + task);
	        		}
	        		taskService.terminateTask(task.getSn(), null);
				} else if (action == 35 || action == 36) {
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 取消Task(action=" + action + ")[35,36]：" + task);
	        		}
	        		taskService.cancelTask(task);
	        	}else if(action == 38 || action == 39){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 中止Task(action=" + action + ")[38,39]：" + task);
	        		}
	        		taskService.stopTask(task);
	        	}else if(action == 41 || action == 42){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 免办Task(action=" + action + ")[41,42]：" + task);
	        		}
	        		taskService.avoidTask(task);
	        	}else if(action == 44 || action == 45){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 暂停Task(action=" + action + ")[44,45]：" + task);
	        		}
	        		taskService.hangTask(task.getSn());
	        	}else if(action == 47 || action == 48){
	        		if(IS_DEBUG_ENABLED){
	        			log.debug("processWorkAffair - 唤醒Task(action=" + action + ")[47,48]：" + task);
	        		}
	        		taskService.wakeTask(task.getSn());
	        	}
        	}
			
        	
        	if ((objectTag != 0) && (object != null)) {
      		   object.setTag(objectTag);
         	}        	
        	if ((lifeStage != 0) && (object != null)) {
     		   object.setLifeStage(lifeStage);
        	}
        	if ((objectStatus != 0) && (object != null)) {
        		object.setStatus(objectStatus);
        	}
        	if ((objectKeyMatter == 1) && (object != null)) {
        		object.setActiveMatter(bizId);
        	}
			if (task != null) {
//        		System.out.println("========22222222222222222222======");
//            	System.out.println("taskType:"+task.getType());
//            	System.out.println("bizAction:"+bizAction);
//            	System.out.println("workType:"+ work.getType());
//            	System.out.println("bizId:"+bizId);
//            	System.out.println("======================================");
				eventMsgService.dealEventMsg(object.getObjectType(),object.getId(), task.getType(),task.getSn(), work.getType(), affairAction);
            }     
        	processTaskAffair(task, affairID, affairAction, object,(short)0);
        }
	}
	
	/**
	 * 本级为任务，上级为作业
	 * @param task
	 * @param work
	 * @param matterId
	 * @param bizAction
	 * @param object
	 */
	protected void processTaskAffair(Task task, Long bizId, Byte bizAction, RFSObject object,short tag) {
        Long objectID = null;
        Long affairID = null;
        Long dutyerID = null;
        Long jobSn = null;
        Job job = null;
        Clue clue = null;
        int bizType;
        short dutyerType;
        short objectTag;
        byte affairCategory;
        @SuppressWarnings("unused")
		byte affairAction;
        byte action;
        int lifeStage;
        byte objectStatus;
        byte objectKeyMatter;        
        int refType;
        short affairObjectRelation;
        RFSObject affairObject;
        Long affairObjectID;        
        
        if (task == null){
           return;
        }
        if (object !=null ){
           objectID=object.getId();
        }

        List<MatterAffair> matterAffairs = matterAffairService.findAffairs(MatterAffair.CATEGORY_TASK, bizId, bizAction,tag);
        for (MatterAffair element : matterAffairs){
        	affairID = element.getAffairID();
        	affairCategory = element.getAffairCategory();
        	affairAction = element.getAffairAction();
            bizType = element.getBizType();
            action = element.getAction();
            lifeStage = element.getObjectLifeStage();
            objectTag = element.getObjectTag();
            dutyerID = element.getDutyerID();
            dutyerType = element.getDutyerType();
            affairObjectRelation = element.getAffairObjectRelation();
            refType = element.getRefType();
            objectStatus = element.getObjectStatus();
            objectKeyMatter = element.getObjectKeyMatter();
            
            if(dutyerID == null || dutyerID.intValue() == 0) {
            	dutyerID = object.getManager() == null ? 0L : object.getManager();
            }
            
            if (affairObjectRelation != 0) {
            	
            	if(IS_DEBUG_ENABLED){
        			log.debug("processTaskAffair - affairObjectRelation=" + affairObjectRelation);
        		}
            	
            	affairObjectID = objectsService.getRelatedObjectID(objectID, affairObjectRelation);
				if (affairObjectID != null) {
					affairObject = objectService.getObject(affairObjectID);
				} else {
					affairObjectID = 0L;
					affairObject = null;
				}
			} else {
				affairObject = object;
				affairObjectID = objectID;
			}            
            job = null;
            
            // 查找（查找活动的clue和job。）
            if ((action == 1)||(action == 2)||(action == 3)||(action == 4)||(action == 5) || (action == 12)) {
                clue = clueService.getActiveClue(affairObjectID, affairCategory, bizType, affairID);
                
                if(IS_DEBUG_ENABLED){
                	log.debug(String.format("processTaskAffair - 查询活动的Clue： " +
                			"objectId=%s, affairCategory=%s, " +
                			"bizType=%s, affairID=%s |",  affairObjectID, affairCategory, bizType, affairID)
                			+ clue);
                }
                
                if (clue != null) {
                   jobSn = clue.getBizSN();
                   job = jobService.getJobBySn(jobSn);
                }
            }         
        	//结束（1：若有则结束并创建并关联，若无直接创建并关联； 4：若有则关联，然后结束。）
        	if (action == 1 || action == 4 || action == 5) {
        		
          		if(IS_DEBUG_ENABLED){
        			log.debug("processWorkAffair - 结束Job(action=1,4,5)：" + jobSn);
        		}
        		
        		if(job != null) jobService.terminateJob(jobSn, null);
        	}
        	//创建（1：若有则结束并创建并关联，若无直接创建并关联； 2：若有则关联，若无则创建并关联；）			
        	if (action == 1 || ((action == 2 || action == 12) && (job == null))) {
        		if(IS_DEBUG_ENABLED){
        			String s = String.format("processWorkAffair - 创建Job" +
        					"(task.getClerkID()=%s, bizType=%s, affairID=%s, affairObjectID=%s," +
        					" dutyerType=%s, dutyerID=%s)",
        					(task==null)?"":task.getClerkID(), bizType, affairID, affairObjectID, dutyerType, dutyerID);
        			log.debug(s);
        		}
        		
        		job = jobService.createJob(task.getClerkID(), bizType, affairID, affairObjectID, dutyerType, dutyerID);
        	}
        	//关联（1：若有则结束并创建并关联，若无直接创建并关联；2：若有则关联，若无则创建并关联；3：若有则关联，若无不关联；
            // 4：若有则关联，然后结束。其他：无操作。把该结束的，结束。）	
        	if(refType==0){
        		refType=task.getType();
        	}
        	
        	
        	if (action == 1 || action == 2 || action == 3 || action == 4) {
        		if(IS_DEBUG_ENABLED){
        			String s = String.format("processWorkAffair - 创建BizTrackNodeInstance" +
        					"(MatterAffair.CATEGORY_TASK=%s, refType=%s, task.getSn()=%s, job.getBizTrack()=%s)",
        					MatterAffair.CATEGORY_TASK, refType, (task==null)?"":task.getSn(), (job==null)?"":job.getBizTrack());
        			log.debug(s);
        		}
        		
        		if(job != null) bizTrackNodeInstanceService.createBizTrackNodeInstance(MatterAffair.CATEGORY_TASK, refType, task.getSn(), job.getBizTrack());
        	}
        	if (action == 12) {
        		
        		if(IS_DEBUG_ENABLED){
        			String s = String.format("processWorkAffair - moveBizTrackNodeInstance" +
        					"(MatterAffair.CATEGORY_OBJECT=%s, refType=%s, objectID=%s, job.getBizTrack()=%s)",
        					MatterAffair.CATEGORY_OBJECT, refType, objectID, (job==null)?"":job.getBizTrack());
        			log.debug(s);
        		}
        		
        		if(job != null) bizTrackNodeInstanceService.moveBizTrackNodeInstance(MatterAffair.CATEGORY_OBJECT, refType, objectID, job.getBizTrack());
        	}
        	if ((objectTag!= 0) && (affairObject != null)) {
        		affairObject.setTag(objectTag);
         	}
        	if ((lifeStage != 0) && (affairObject != null)) {
        		affairObject.setLifeStage(lifeStage);
        	}
        	if ((objectStatus != 0) && (object != null)) {
        		object.setStatus(objectStatus);
        	}
        	if ((objectKeyMatter == 1) && (object != null)) {
        		object.setActiveMatter(bizId);
        		object.setActiveTaskSN(task.getSn());
        		object.setActiveDutyerID(task.getDutyerID());
        
        	}        	
            processJobAffair(job, affairID, bizAction, affairObject,(short)0);
        	//FIXME:
            //根据processWorkAffair调用processTaskAffair的规律，第三个参数疑似应该
        	//为affairAction而不是bizAction。
        	//processJobAffair(job, affairID, affairAction, affairObject,(short)0);
        }
	}

	/**
	 * 本级为作业，上级也为作业
	 * @param task
	 * @param work
	 * @param matterId
	 * @param bizAction
	 * @param object
	 */
	protected void processJobAffair(Job job, Long bizId, Byte bizAction, RFSObject object,short tag) {
		Long objectID = null;
		Long affairID = null;
        Long dutyerID = null;
//        Job _job = null;
        Long jobSn = null;
        Clue clue = null;
		int bizType;
		byte affairCategory;
		byte affairAction;
		byte action;
		int lifeStage;
        byte objectStatus;
        byte objectKeyMatter;		
		short dutyerType;
		short objectTag;
                
		if (job == null){
			return;
		}
		if (object !=null ){
			objectID=object.getId();
		}

		List<MatterAffair> matterAffairs = matterAffairService.findAffairs(MatterAffair.CATEGORY_JOB, bizId, bizAction,tag);

		for (MatterAffair element : matterAffairs){
			affairID = element.getAffairID();
			affairCategory = element.getAffairCategory();
			affairAction = element.getAffairAction();
			bizType = element.getBizType();
			action = element.getAction();
			lifeStage = element.getObjectLifeStage();
			objectTag = element.getObjectTag();
            dutyerID = element.getDutyerID();
            dutyerType = element.getDutyerType();
            objectStatus = element.getObjectStatus();
            objectKeyMatter = element.getObjectKeyMatter();            
            
            if(dutyerID == null || dutyerID.intValue() == 0) {
            	dutyerID = object.getManager() == null ? 0L : object.getManager();
            }
            
             // 查找（查找活动的clue和job。）
            if ((action == 1)||(action == 2)||(action == 3)||(action == 4)||(action == 5)) {
                clue = clueService.getActiveClue(objectID, affairCategory, bizType, affairID);
                
                if(IS_DEBUG_ENABLED){
                	log.debug(String.format("processJobAffair - 查询活动的Clue： " +
                			"objectId=%s, affairCategory=%s, " +
                			"bizType=%s, affairID=%s |",  objectID, affairCategory, bizType, affairID)
                			+ clue);
                }
                
                if (clue != null) {
                   jobSn=clue.getBizSN();
                   job = jobService.getJobBySn(jobSn);
                }
            }
			//结束（1：若有则结束并创建并关联，若无直接创建并关联； 4：若有则关联，然后结束。）
			if (action == 1 || action == 4 || action == 5) {
				
				if(IS_DEBUG_ENABLED){
        			log.debug("processWorkAffair - 结束Job：" + jobSn);
        		}
				
				if(job != null) jobService.terminateJob(jobSn, null);
			}
			//创建（1：若有则结束并创建并关联，若无直接创建并关联； 2：若有则关联，若无则创建并关联；）			
			if (action == 1 || action == 2) {
				
				if(IS_DEBUG_ENABLED){
        			String s = String.format("processWorkAffair - 创建Job" +
        					"(job.getClerkID()=%s, bizType=%s, affairID=%s, objectID=%s, dutyerType=%s, dutyerID=%s)",
        					(job==null)?"":job.getClerkID(), bizType, affairID, objectID, dutyerType, dutyerID);
        			log.debug(s);
        		}
				
				job = jobService.createJob(job.getClerkID(), bizType, affairID, objectID, dutyerType, dutyerID);
			}
			//关联（1：若有则结束并创建并关联，若无直接创建并关联；2：若有则关联，若无则创建并关联；3：若有则关联，若无不关联；
             // 4：若有则关联，然后结束。其他：无操作。把该结束的，结束。）			
			if (action == 1 || action == 2 || action == 3 || action == 4) {
				
				if(IS_DEBUG_ENABLED){
        			String s = String.format("processWorkAffair - createBizTrackNodeInstance" +
        					"(MatterAffair.CATEGORY_JOB=%s, job.getType()=%s, job.getSn()=%s, job.getBizTrack()=%s)",
        					MatterAffair.CATEGORY_JOB, (job==null)?"":job.getType(), (job==null)?"":job.getSn(), (job==null)?"":job.getBizTrack());
        			log.debug(s);
        		}
				
				if(job != null) bizTrackNodeInstanceService.createBizTrackNodeInstance(MatterAffair.CATEGORY_JOB, job.getType(), job.getSn(), job.getBizTrack());
			}
			if ((objectTag != 0) && (object != null)) {
				object.setTag(objectTag);
			}			
			if ((lifeStage != 0) && (object != null)) {
				object.setLifeStage(lifeStage);
			}
        	if ((objectStatus != 0) && (object != null)) {
        		object.setStatus(objectStatus);
        	}
        	if ((objectKeyMatter == 1) && (object != null)) {
        		object.setActiveMatter(bizId);
        	}			
			processJobAffair(job, affairID, affairAction, object,(short)0);
		}
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * cn.redflagsoft.base.scheme.MattersHandler#finishMatters(cn.redflagsoft
//	 * .base.scheme.WorkScheme, short, java.lang.String)
//	 */
//	public void finishMatters(WorkScheme ws, short tag, String note) {
//		Work work = ws.getWork();
//		RFSObject object = ws.getObject();
//		List<RFSItemable> relatedItems = ws.getRelatedItems();
//		for (Long matterId : ws.getMatterVO().getMatterIds()) {
//			if (matterId == null)
//				continue;
//			else {
//				processWorkAffair(work, matterId, MatterAffair.ACTION_END_MATTER, object, tag, note, relatedItems);
//			}
//		}
//	}

}
