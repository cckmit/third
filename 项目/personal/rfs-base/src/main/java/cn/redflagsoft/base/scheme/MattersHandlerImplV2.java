/*
 * $Id: MattersHandlerImplV2.java 6454 2015-07-01 09:22:36Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.Clue;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.ObjectOrgClerk;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.BusinessExceptionManager;
import cn.redflagsoft.base.service.ObjectOrgClerkService;
import cn.redflagsoft.base.service.ObjectService;

/**
 * 新版（20110507）的MatterAffair驱动程序，需要配置新版的的配置工具（CkStone v1.2）使用。
 * 
 * 要使用新版的MattersHandler，需要在WorkScheme中注入该类的实例，通常在父类中设置。
 */
public class MattersHandlerImplV2 extends MattersHandlerImpl implements	MattersHandler {
	private static final Log log = LogFactory.getLog(MattersHandlerImplV2.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private ObjectOrgClerkService objectOrgClerkService;
	private BusinessExceptionManager businessExceptionManager;
	
	/**
	 * @return the objectOrgClerkService
	 */
	public ObjectOrgClerkService getObjectOrgClerkService() {
		return objectOrgClerkService;
	}


	/**
	 * @param objectOrgClerkService the objectOrgClerkService to set
	 */
	public void setObjectOrgClerkService(ObjectOrgClerkService objectOrgClerkService) {
		this.objectOrgClerkService = objectOrgClerkService;
	}
	
	public void setBusinessExceptionManager(BusinessExceptionManager businessExceptionManager){
		this.businessExceptionManager = businessExceptionManager;
	}

	// 温绍杰2011-05-06大改
	@SuppressWarnings("unchecked")
	@Override
	protected void processWorkAffair(Work work, Long bizId, Byte bizAction,	RFSObject object, short tag, String note, WorkScheme ws) {
		List<RFSItemable> relatedItems = ws != null ? ws.getRelatedItems() : null;
		if(IS_DEBUG_ENABLED){
			log.debug(String.format("调用processWorkAffair(%s,%s,%s,%s,%s,%s)，相关的业务对象为： %s", work, bizId, bizAction, object, tag, note, relatedItems));
		}

		Long objectID = null;
		Long dutyerID = null;
		
		int bizType;
		byte bizCategory = MatterAffair.CATEGORY_WORK;
		
		byte affairCategory;
		byte affairAction;
		Long affairID = null;
		int affairType;
		short affairObjectRelation;
		
		byte theAction;
		int lifeStage;
		byte objectStatus;
		byte objectKeyMatter;
		short dutyerType;
		short objectTag;
		short clerkType;
		Long clerkID;
		
		short validTag;

		if (work == null) {
			return;
		}
		
		//取Work的类型
		bizType = work.getType();
		
		if (object != null) {
			objectID = object.getId();
		}
		
		Date happenTime = getHappenTime(work, bizAction);

		// 查找相关事项定义
		//List<MatterAffair> matterAffairs = getMatterAffairService().findAffairs(MatterAffair.CATEGORY_WORK, bizId, bizAction, tag);
		List<MatterAffair> matterAffairs = getMatterAffairService().findAffairs(bizCategory, bizId, bizType, bizAction, tag);
		
		if(IS_DEBUG_ENABLED){
			log.debug("查询到MatterAffair集合：" + matterAffairs.size());
		}
		
		// 对每个事项定义进行处理
		for (MatterAffair element : matterAffairs) {// for

			affairCategory = element.getAffairCategory();
			affairID = element.getAffairID();
			affairType = element.getAffairType();
			affairAction = element.getAffairAction();

			//bizType = element.getBizType();
			theAction = element.getAction();
			lifeStage = element.getObjectLifeStage();
			objectTag = element.getObjectTag();
			dutyerID = element.getDutyerID();
			dutyerType = element.getDutyerType();
			objectStatus = element.getObjectStatus();
			objectKeyMatter = element.getObjectKeyMatter();
			clerkType = element.getClerkType();
			clerkID = element.getClerkID();
			affairObjectRelation = element.getAffairObjectRelation();
			validTag = element.getValidTag();

			Long affairObjectID;
			RFSObject affairObject;
			
			Task task = null;
			Long taskSn = null;
			Clue clue = null;
			boolean isAffairObjectSameToObject = false;
			
			if(IS_DEBUG_ENABLED){
				log.debug("MatterAffair定义: " + element.toString());
			}
			
			if(ws != null){
				Long masn = element.getSn();
				if(ws.getProcessedMatterAffairs().contains(masn)){
					throw new IllegalArgumentException("当前WorkScheme ‘" + ws + "’已经处理过 id 为 ‘" +
							masn + "’的MatterAffair，请检查配置和调用程序。");
				}else{
					ws.getProcessedMatterAffairs().add(masn);
					if(IS_DEBUG_ENABLED){
						log.debug("WorkScheme '" + ws + "' 开始处理 MatterAffair: " + masn);
					}
				}
			}
			
			if (affairObjectRelation != 0) {
            	if(IS_DEBUG_ENABLED){
        			log.debug("processTaskAffair - affairObjectRelation=" + affairObjectRelation);
        		}
            	affairObjectID = getObjectsService().getRelatedObjectID(objectID, affairObjectRelation);
				if (affairObjectID != null) {
					log.info("查询AffairObject：" + affairObjectID);
					if(ws instanceof AbstractWorkScheme){
						affairObject = ((AbstractWorkScheme)ws).getObjectService().getObject(affairObjectID);
					}else{
						affairObject = getObjectService().getObject(affairObjectID);
					}
					log.info("查询AffairObject：" + affairObjectID + " --> " + affairObject);
				} else {
					affairObjectID = 0L;
					affairObject = null;
				}
			} else {
				log.debug("Using object as AffairObject: " + object);
				affairObject = object;
				affairObjectID = objectID;
				isAffairObjectSameToObject = true;
			}

			if(affairObject.getTag() >= validTag){
				log.info(String.format("当前对象的tag值%s，当前MatterAffair的validTag为%s，所以不执行该条处理，跳过。", 
						affairObject.getTag(), validTag));
				log.debug("根据ValidTag跳过了当前MatterAffair的处理：" + element);
				continue;
			}
			
			if (dutyerID == null || dutyerID.intValue() == 0) {
				dutyerID = affairObject.getManager() == null ? 0L : affairObject.getManager();
			}
			

			if (affairCategory == MatterAffair.CATEGORY_TASK) {
				// TASK级别
				// ////////////////////////////////////////////////////////////////////////////////////////////
				// 1.查找（查找待处理的task。）
				// ////////////////////////////////////////////////////////////////////////////////////////////
				task = null;// 必须的
				if (theAction == MatterAffair.THE_ACTION_DIRECT_PARENT_BIZ/*4*/) {
					// 查找直属的上级业务，主要用于一个对象可能同时存在多个事别业务时的情形（例如，项目的工程结算）。
					taskSn = work.getTaskSN();
					task = getTaskService().getTask(taskSn);
					if (IS_DEBUG_ENABLED) {
						log.debug("从Work的taskSN中查找Task：" + task);
					}
				} else {// 查找关联task
					int tokenCreate = 0;// 关联task的新建令牌
					
					//无论存在与否都新建
					if(theAction == MatterAffair.THE_ACTION_CREATE_NEW/*5*/){
						tokenCreate = 1;
					}else{
						//判断是否需要新建
						
						//先从关联业务中查找task, 2011-07-01
						task = getTaskByRelatedItemsAndType(relatedItems, affairType);
						
						if(task == null){
							clue = getClueService().getActiveClue(affairObjectID, affairCategory, affairType, affairID);
		//					clue = getClueService().getActiveClue(affairObjectID, affairCategory, bizType, affairID);
							if (IS_DEBUG_ENABLED) {
								log.debug(String.format("processWorkAffair - 查询活动的Clue： "
												+ "objectId=%s, affairCategory=%s, "
												+ "affairType=%s, affairID=%s | clue=",
												affairObjectID, affairCategory, affairType, affairID) + clue);
							}
							if (clue != null) {
								taskSn = clue.getBizSN();
								task = getTaskService().getTask(taskSn);
								if (IS_DEBUG_ENABLED) {
									log.debug("从ActiveClue的bizSN中查找Task：" + task);
								}
							}
						}
						
						if ((task != null)) {// 已找到业务
							//if (action == 2 || action == 3) {// 中止并重建
							if(theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_IF_EXISTS 
									|| theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_EXISTS_OR_CREATE_NOT_EXISTS){
								tokenCreate = 1;
								if (IS_DEBUG_ENABLED) {
									log.debug("processWorkAffair - 先中止Task(action=" + theAction + ")[2,3]：" + task);
								}
								getTaskService().stopTask(task, happenTime);
							}
						} else {// 未找到业务
							//if (action == 1 || action == 3) {// 直接新建
							if(theAction == MatterAffair.THE_ACTION_CREATE_IF_NOT_EXISTS
									|| theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_EXISTS_OR_CREATE_NOT_EXISTS){
								tokenCreate = 1;
							}
						}
					}
					
					if (tokenCreate == 1) {// 重建或新建task
						if (clerkType == 0) {// 当前创建人
							clerkID = UserClerkHolder.getClerk().getId();
						} else if (clerkType == 1) {// 对象责任人
							if (affairObject.getDutyClerkID() != null) {
								clerkID = affairObject.getDutyClerkID();// 如果不能确定责人类型，则业务的办理人员设成
																	// 对象的责任人ID
							}else{
//								throw new IllegalArgumentException("按MatterAffair设置， task的clerkID取自业务对象的责任人，但当前业务对象的责任人为空：" 
//										+ affairObject);
								
								log.error("按MatterAffair设置， task的clerkID取自业务对象的责任人，但当前业务对象的责任人为空：" 
										+ affairObject);
								//throw new BusinessException("未设置业务对象责任人，无法完成相应的业务操作。");
								businessExceptionManager.throwBusinessException(1001L, "未设置业务对象责任人，无法完成相应的业务操作。");
							}
						}else if (clerkType == 2){
							//ClerkID从MatterAffair中读取
						}else if (clerkType == 3){
							//since 20120-04-16
							//3来自object与单位人员关系的关系表 ObjectOrgClerk中, 必须定义clerkID,clerkID的值即ObjectOrgClerk中的type。
							Assert.notNull(element.getClerkID(), "MatterAffair必须配置clerkID，且clerkID是object在ObjectOrgClerk中的关系的关系类型");
							try {
								int objectOrgClerkType = element.getClerkID().intValue();
								ObjectOrgClerk ooc = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, object.getObjectType(), object.getId());
								clerkID = ooc.getClerk1Id();
							} catch (NotFoundException e) {
								throw new IllegalArgumentException("按MatterAffair设置， task的clerkID取自object相关的ObjectOrgClerk，获取时发生错误", e); 
							}
						}else if(clerkType == 4){
							//since 20120-04-16
							//4来自affairObject与单位人员关系的关系表 ObjectOrgClerk中, 必须定义clerkID,clerkID的值即ObjectOrgClerk中的type。
							Assert.notNull(affairObject, "affairObject 不能为空");
							Assert.notNull(element.getClerkID(), "MatterAffair必须配置clerkID，且clerkID是affairObject在ObjectOrgClerk中的关系的关系类型");
							try {
								int objectOrgClerkType = element.getClerkID().intValue();
								ObjectOrgClerk ooc = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, affairObject.getObjectType(), affairObject.getId());
								clerkID = ooc.getClerk1Id();
							} catch (NotFoundException e) {
								throw new IllegalArgumentException("按MatterAffair设置， task的clerkID取自affairObject相关的ObjectOrgClerk，获取时发生错误", e); 
							}
						}
						if (clerkID == null) {// 如果clerkID仍为空
							clerkID = UserClerkHolder.getClerk().getId();
						}
						if (IS_DEBUG_ENABLED) {
							// log.debug("Task使用的ClerkID：" + clerkID);
							String s = String
									.format("processWorkAffair - 创建(action=%s)Task：clerkID=%s, affairType=%s, affairID=%s, "
											+ "objectID=%s, dutyerType=%s, dutyerID=%s, note=%s, affairAction=%s",
											theAction, clerkID, affairType, affairID,
											affairObjectID, dutyerType, dutyerID,
											note, affairAction);
							log.debug(s);
						}

						//根据MatterAffair调用WorkScheme
						if(StringUtils.isNotBlank(element.getSchemeInfo())){
							if(IS_DEBUG_ENABLED){
								log.debug("根据MatterAffair配置，将在MattersHandler中调用WorkScheme：" + element.getSchemeInfo());
							}
							WorkScheme newWorkScheme = callWorkScheme(element, work, bizId, bizAction,	object, tag, note, 
									ws, affairObject, clerkID, happenTime, isAffairObjectSameToObject);
							task = newWorkScheme.getTask();
						}else{
							if(IS_DEBUG_ENABLED){
								log.debug("根据MatterAffair配置，将在MattersHandler创建新Task: " + affairType);
							}
							
							task = new Task();
							task.setClerkID(clerkID);
							task.setType(affairType);
							task.setNote(note);
							task.setBeginTime(work.getBeginTime());
							task = getTaskService().createTask(task, new Long[]{affairID}, affairObject, dutyerType, dutyerID);
							
//							task = getTaskService().createTask(clerkID, affairType,	
//									new Long[] { affairID }, affairObjectID, dutyerType,
//									dutyerID, note, work.getBeginTime()/*取work的开始时间*/);
							
							updateTaskEntityObjectsIfRequired(task, relatedItems);
						}
						
					}// if (TokenCreate=1){//重建或新建task
				}// if (action＝＝4)

				// /////////////////////////////////////////////////////////////////////////////////////////
				// 2.对task进行处理
				// 0：无处理；1：受理（接受申请）；2：撤消（取消申请或撤回申请）；3：审结（结论确定）；4：办结（处理完毕，等待领取）；
				// 5：结束（任务结束）；6：暂停；7：重启。8：中止；9：免办；10：取消。
				// 默认为0，表示无处理，用于仅需建立关联关系的情形。
				// /////////////////////////////////////////////////////////////////////////////////////////
				if (task != null) {

					if (affairAction == MatterAffair.ACTION_END_MATTER/*5*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 结束Task：" + task);
						}
						getTaskService().terminateTask(task, null, happenTime);

					} else if (affairAction == MatterAffair.ACTION_HANG/*6*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 暂停Task(action="
									+ theAction + ")[6]：" + task);
						}
						getTaskService().hangTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_WAKE/*7*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 唤醒Task(action="
									+ theAction + ")[7]：" + task);
						}
						getTaskService().wakeTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_STOP/*8*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 中止Task(action="
									+ theAction + ")[8]：" + task);
						}
						getTaskService().stopTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_AVOID/*9*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 免办Task(action="
									+ theAction + ")[9]：" + task);
						}
						getTaskService().avoidTask(task, happenTime);
					} else if (affairAction == MatterAffair.ACTION_CANCEL/*10*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 取消Task(action="
									+ theAction + ")[10]：" + task);
						}
						getTaskService().cancelTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_WITHDRAW/*11*/){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 撤回Task(action="
									+ theAction + ")[11]：" + task);
						}
						getTaskService().withdrawTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_REJECT){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 驳回Task(action="
									+ theAction + ")[12]：" + task);
						}
						getTaskService().rejectTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_UNDO_MATTER){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 撤销Task(action="
									+ theAction + ")[2]：" + task);
						}
						getTaskService().undoTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_TRANSFER){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - 转出Task(action="
									+ theAction + ")[15]：" + task);
						}
						getTaskService().transferTask(task, happenTime);
					}
				}
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 3.设置跟踪轨迹
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 把当前work关联到task中。
				if (task != null) {
					getBizTrackNodeInstanceService().createBizTrackNodeInstance(MatterAffair.CATEGORY_WORK,
									work.getType(), work.getSn(), task.getBizTrack());
					if (IS_DEBUG_ENABLED) {
						String s = String
								.format("processWorkAffair - 创建(action=%s)BizTrackNodeInstance：category=%s, workType=%s, workSN=%s, task.bizTrack=%s",
										theAction,
										MatterAffair.CATEGORY_WORK,
										(work == null) ? "" : work.getType(),
										(work == null) ? "" : work.getSn(),
										(task == null) ? "" : task.getBizTrack());
						log.debug(s);
					}
				}

				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 4.更改业务对象属性
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 设置affair的业务对象的内部状态为指定值，若0则忽略。
				if(affairObject != null){
					boolean affairObjectChanged = false;
					
					//要改的tag比对象当前tag大时才修改对象的tag，status和lifestage
					short currentTag = affairObject.getTag();
					boolean updateRequired = objectTag >= currentTag;
					
					if (updateRequired && (objectTag != 0)) {
						affairObject.setTag(objectTag);
						log.debug("设置了AffairObject(" + affairObject + ")的 tag：" + objectTag);
						affairObjectChanged = true;
					}
					// 设置affair的业务对象的外部状态为指定值，若0则忽略。
					if (updateRequired && (objectStatus != 0)) {
						//affairObject.setStatus(objectStatus);
						//2011-06-15更改为修改业务状态而不是状态
						affairObject.setBizStatus(objectStatus);
						log.debug("设置了AffairObject(" + affairObject + ")的 BizStatus：" + objectStatus);
						affairObjectChanged = true;
					}
					// 设置affair的业务对象的生命阶段为指定值，若0则忽略。
					if (updateRequired && (lifeStage != 0)) {
						affairObject.setLifeStage(lifeStage);
						log.debug("设置了AffairObject(" + affairObject + ")的 lifeStage：" + lifeStage);
						affairObjectChanged = true;
					}
					// 设置affair的业务对象的关键事项为bizId，若0则忽略。
					if ((objectKeyMatter == 1) ) {
						affairObject.setActiveMatter(bizId);
						log.debug("设置了AffairObject(" + affairObject + ")的 ActiveMatter：" + bizId);
						affairObjectChanged = true;
					}
					
					if(affairObjectChanged ){
						@SuppressWarnings("rawtypes")
						ObjectService service = getObjectService();
						if(ws instanceof AbstractWorkScheme){
							service = ((AbstractWorkScheme)ws).getObjectService();
							//affairObject = ((AbstractWorkScheme)ws).getObjectService().updateObject(affairObject);
						}else{
							//affairObject = getObjectService().updateObject(affairObject);
						}
						affairObject = service.updateObject(affairObject);
					}
					
					if (task != null) {
						getEventMsgService().dealEventMsg(affairObject.getObjectType(), affairObject.getId(), task.getType(), task.getSn(), work.getType(), affairAction);
					}
				}
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 5.对task及affair进行递归处理
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				processTaskAffair(task, affairID, affairAction, affairObject, (short) 0);

			}// TASK级别
		}// for
	}// processWorkAffair
	
	
	/**
	 * @param ma MatterAffair配置
	 * @param work 执行MatterAffair的Work对象
	 * @param bizId
	 * @param bizAction
	 * @param object
	 * @param tag
	 * @param note
	 * @param ws
	 * @param affairObject 
	 * @param happenTime 
	 * @param clerkID 
	 * @return 
	 */
	private WorkScheme callWorkScheme(MatterAffair ma, Work work, Long bizId,
			Byte bizAction, RFSObject object, short tag, String note,
			WorkScheme ws, RFSObject affairObject, Long clerkID, Date happenTime, 
			boolean isAffairObjectSameToObject) {
		SchemeInfo schemeInfo = SchemeInvoker.parseSchemeInfo(ma.getSchemeInfo());
		SchemeManager manager = Application.getContext().get("schemeManager", SchemeManager.class);
		Scheme scheme = manager.getScheme(schemeInfo.getName());
		if(scheme == null || !(scheme instanceof WorkScheme)){
			throw new IllegalArgumentException("MatterAffair配置错误，必须配置成WorkScheme的信息“schemeName!method”。");
		}
		WorkScheme s = (WorkScheme) scheme;
		Map<String, Object> map = null;
		if(s instanceof SchemeParametersBuilder){
			map = ((SchemeParametersBuilder) s).buildParameters(ma, ws, object, affairObject);
		}
		if(map == null){
			map = Maps.newHashMap();
		}
		
		Object objectId = map.get("objectId");
		
		//if(!map.containsKey("objectId")){
		if(objectId == null){
		//map.put("objectId", affairObject.getId());
			log.warn("参数中没有objectId，请确认参数创建");
		}else{
			//如果是之前的任何一个对象，都取该对象，不再需要 WS 根据id查询新对象。
			//http://192.168.18.6/sf/go/artf1486
			long id = NumberUtils.toLong(objectId.toString());
			if(id == affairObject.getId().longValue()){
				s.setRFSObject(affairObject);
			}else if(id == object.getId().longValue()){
				s.setRFSObject(object);
			}
		}
		
		if(clerkID != null && !map.containsKey("clerkID")){
			map.put("clerkID", clerkID);
		}
		if(happenTime != null){
			String timeString = AppsGlobals.formatDate(happenTime);
			if(!map.containsKey("workBeginTime")){
				map.put("workBeginTime", timeString);
			}
			if(!map.containsKey("workEndTime")){
				map.put("workEndTime", timeString);
			}
		}
		s.setParameters(map);
		try {
			SchemeInvoker.invoke(s, schemeInfo.getMethod());
			return s;
		} catch (Exception e) {
			if(e instanceof SchemeException){
				throw (SchemeException)e;
			}else if(e instanceof RuntimeException){
				throw (RuntimeException)e;
			}else{
				throw new SchemeException(e);
			}
		}
	}

	/**
	 * @param work
	 * @param bizAction
	 * @return
	 */
	public static Date getHappenTime(Work work, Byte bizAction) {
		switch (bizAction.byteValue()) {
		case MatterAffair.ACTION_END_MATTER:
			return work.getEndTime();
		case MatterAffair.ACTION_HANG:
			return work.getHangTime();
		case MatterAffair.ACTION_WAKE:
			return work.getWakeTime();
		case MatterAffair.ACTION_STOP:
			return work.getEndTime();
		case MatterAffair.ACTION_AVOID:
			return work.getEndTime();
		case MatterAffair.ACTION_CANCEL:
			return work.getEndTime();
		case MatterAffair.ACTION_UNDO_MATTER:
			return work.getEndTime();
		case MatterAffair.ACTION_REJECT:
			return work.getEndTime();
		case MatterAffair.ACTION_WITHDRAW:
			return work.getEndTime();
		}
		return null;
	}

	private Task getTaskByRelatedItemsAndType(List<RFSItemable> relatedItems, Integer taskType){
		if(IS_DEBUG_ENABLED){
			log.debug(String.format("calling getTaskByRelatedItemsAndType(%s, %s)", relatedItems, taskType));
		}
		if(relatedItems != null && relatedItems.size() > 0){
			Set<Task> relatedTasks = new HashSet<Task>();
			for(RFSItemable item: relatedItems){
				List<Task> tasks = getTaskService().findTasks(item, taskType, null, null);
				if(tasks != null && !tasks.isEmpty()){
					relatedTasks.addAll(tasks);
					if(IS_DEBUG_ENABLED){
						log.debug(String.format("为业务对象 ‘%s’找到类型为‘%s’的Task集合：%s", item, taskType, tasks));
					}
				}
			}
			
			//去掉Task结束的
			Set<Task> set = new HashSet<Task>(relatedTasks);
			relatedTasks = new HashSet<Task>();
			for(Task t: set){
				if(t.getStatus() < 9){
					relatedTasks.add(t);
				}else{
					if(IS_DEBUG_ENABLED){
						log.debug("Task状态大于等于9，从集合中剔除：" + t);
					}
				}
			}
			
			int taskSize = relatedTasks.size();
			if(taskSize > 0 && IS_DEBUG_ENABLED){
				log.debug("合并后相关业务对象的所有相关Task集合：" + relatedTasks);
			}
			if(taskSize == 1){
				Task next = relatedTasks.iterator().next();
				
				if(IS_DEBUG_ENABLED){
					log.debug(String.format("找到类型为 %s的符合条件的task,并使之作为MatterAffair驱动之用: %s", taskType, next));
				}
				return next;
			}else if(taskSize > 1){
				log.warn("当前WorkScheme相关的所有业务对象相关的Task数量不唯一，无法获得一个确切的Task: taskType=" + taskType);
			}else{
				if(IS_DEBUG_ENABLED){
					log.debug("相关业务对象的所有相关Task集合为空，无法取得task: taskType=" + taskType);
				}
			}
		}
		return null;
	}
	
	/**
	 * @param task
	 * @param relatedItems
	 */
	private void updateTaskEntityObjectsIfRequired(Task task, List<RFSItemable> relatedItems) {
		if(IS_DEBUG_ENABLED){
			log.debug(String.format("calling updateTaskEntityObjectsIfRequired(%s, %s)", task, relatedItems));
		}
		if(relatedItems != null && relatedItems.size() > 0){
			RFSEntityObject[] objects = relatedItems.toArray(new RFSEntityObject[relatedItems.size()]);
			getTaskService().updateTaskEntityObjects(task, objects);
			if(IS_DEBUG_ENABLED){
				log.debug("保存了普通业务对象与MatterAffair自动创建的Task之间的关系：" + task);
			}
		}
	}
}
