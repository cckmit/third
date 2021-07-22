/*
 * $Id: MattersHandlerImplV2.java 6454 2015-07-01 09:22:36Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * �°棨20110507����MatterAffair����������Ҫ�����°�ĵ����ù��ߣ�CkStone v1.2��ʹ�á�
 * 
 * Ҫʹ���°��MattersHandler����Ҫ��WorkScheme��ע������ʵ����ͨ���ڸ��������á�
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

	// ���ܽ�2011-05-06���
	@SuppressWarnings("unchecked")
	@Override
	protected void processWorkAffair(Work work, Long bizId, Byte bizAction,	RFSObject object, short tag, String note, WorkScheme ws) {
		List<RFSItemable> relatedItems = ws != null ? ws.getRelatedItems() : null;
		if(IS_DEBUG_ENABLED){
			log.debug(String.format("����processWorkAffair(%s,%s,%s,%s,%s,%s)����ص�ҵ�����Ϊ�� %s", work, bizId, bizAction, object, tag, note, relatedItems));
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
		
		//ȡWork������
		bizType = work.getType();
		
		if (object != null) {
			objectID = object.getId();
		}
		
		Date happenTime = getHappenTime(work, bizAction);

		// ������������
		//List<MatterAffair> matterAffairs = getMatterAffairService().findAffairs(MatterAffair.CATEGORY_WORK, bizId, bizAction, tag);
		List<MatterAffair> matterAffairs = getMatterAffairService().findAffairs(bizCategory, bizId, bizType, bizAction, tag);
		
		if(IS_DEBUG_ENABLED){
			log.debug("��ѯ��MatterAffair���ϣ�" + matterAffairs.size());
		}
		
		// ��ÿ���������д���
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
				log.debug("MatterAffair����: " + element.toString());
			}
			
			if(ws != null){
				Long masn = element.getSn();
				if(ws.getProcessedMatterAffairs().contains(masn)){
					throw new IllegalArgumentException("��ǰWorkScheme ��" + ws + "���Ѿ������ id Ϊ ��" +
							masn + "����MatterAffair���������ú͵��ó���");
				}else{
					ws.getProcessedMatterAffairs().add(masn);
					if(IS_DEBUG_ENABLED){
						log.debug("WorkScheme '" + ws + "' ��ʼ���� MatterAffair: " + masn);
					}
				}
			}
			
			if (affairObjectRelation != 0) {
            	if(IS_DEBUG_ENABLED){
        			log.debug("processTaskAffair - affairObjectRelation=" + affairObjectRelation);
        		}
            	affairObjectID = getObjectsService().getRelatedObjectID(objectID, affairObjectRelation);
				if (affairObjectID != null) {
					log.info("��ѯAffairObject��" + affairObjectID);
					if(ws instanceof AbstractWorkScheme){
						affairObject = ((AbstractWorkScheme)ws).getObjectService().getObject(affairObjectID);
					}else{
						affairObject = getObjectService().getObject(affairObjectID);
					}
					log.info("��ѯAffairObject��" + affairObjectID + " --> " + affairObject);
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
				log.info(String.format("��ǰ�����tagֵ%s����ǰMatterAffair��validTagΪ%s�����Բ�ִ�и�������������", 
						affairObject.getTag(), validTag));
				log.debug("����ValidTag�����˵�ǰMatterAffair�Ĵ���" + element);
				continue;
			}
			
			if (dutyerID == null || dutyerID.intValue() == 0) {
				dutyerID = affairObject.getManager() == null ? 0L : affairObject.getManager();
			}
			

			if (affairCategory == MatterAffair.CATEGORY_TASK) {
				// TASK����
				// ////////////////////////////////////////////////////////////////////////////////////////////
				// 1.���ң����Ҵ������task����
				// ////////////////////////////////////////////////////////////////////////////////////////////
				task = null;// �����
				if (theAction == MatterAffair.THE_ACTION_DIRECT_PARENT_BIZ/*4*/) {
					// ����ֱ�����ϼ�ҵ����Ҫ����һ���������ͬʱ���ڶ���±�ҵ��ʱ�����Σ����磬��Ŀ�Ĺ��̽��㣩��
					taskSn = work.getTaskSN();
					task = getTaskService().getTask(taskSn);
					if (IS_DEBUG_ENABLED) {
						log.debug("��Work��taskSN�в���Task��" + task);
					}
				} else {// ���ҹ���task
					int tokenCreate = 0;// ����task���½�����
					
					//���۴�������½�
					if(theAction == MatterAffair.THE_ACTION_CREATE_NEW/*5*/){
						tokenCreate = 1;
					}else{
						//�ж��Ƿ���Ҫ�½�
						
						//�ȴӹ���ҵ���в���task, 2011-07-01
						task = getTaskByRelatedItemsAndType(relatedItems, affairType);
						
						if(task == null){
							clue = getClueService().getActiveClue(affairObjectID, affairCategory, affairType, affairID);
		//					clue = getClueService().getActiveClue(affairObjectID, affairCategory, bizType, affairID);
							if (IS_DEBUG_ENABLED) {
								log.debug(String.format("processWorkAffair - ��ѯ���Clue�� "
												+ "objectId=%s, affairCategory=%s, "
												+ "affairType=%s, affairID=%s | clue=",
												affairObjectID, affairCategory, affairType, affairID) + clue);
							}
							if (clue != null) {
								taskSn = clue.getBizSN();
								task = getTaskService().getTask(taskSn);
								if (IS_DEBUG_ENABLED) {
									log.debug("��ActiveClue��bizSN�в���Task��" + task);
								}
							}
						}
						
						if ((task != null)) {// ���ҵ�ҵ��
							//if (action == 2 || action == 3) {// ��ֹ���ؽ�
							if(theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_IF_EXISTS 
									|| theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_EXISTS_OR_CREATE_NOT_EXISTS){
								tokenCreate = 1;
								if (IS_DEBUG_ENABLED) {
									log.debug("processWorkAffair - ����ֹTask(action=" + theAction + ")[2,3]��" + task);
								}
								getTaskService().stopTask(task, happenTime);
							}
						} else {// δ�ҵ�ҵ��
							//if (action == 1 || action == 3) {// ֱ���½�
							if(theAction == MatterAffair.THE_ACTION_CREATE_IF_NOT_EXISTS
									|| theAction == MatterAffair.THE_ACTION_STOP_AND_CREATE_EXISTS_OR_CREATE_NOT_EXISTS){
								tokenCreate = 1;
							}
						}
					}
					
					if (tokenCreate == 1) {// �ؽ����½�task
						if (clerkType == 0) {// ��ǰ������
							clerkID = UserClerkHolder.getClerk().getId();
						} else if (clerkType == 1) {// ����������
							if (affairObject.getDutyClerkID() != null) {
								clerkID = affairObject.getDutyClerkID();// �������ȷ���������ͣ���ҵ��İ�����Ա���
																	// �����������ID
							}else{
//								throw new IllegalArgumentException("��MatterAffair���ã� task��clerkIDȡ��ҵ�����������ˣ�����ǰҵ������������Ϊ�գ�" 
//										+ affairObject);
								
								log.error("��MatterAffair���ã� task��clerkIDȡ��ҵ�����������ˣ�����ǰҵ������������Ϊ�գ�" 
										+ affairObject);
								//throw new BusinessException("δ����ҵ����������ˣ��޷������Ӧ��ҵ�������");
								businessExceptionManager.throwBusinessException(1001L, "δ����ҵ����������ˣ��޷������Ӧ��ҵ�������");
							}
						}else if (clerkType == 2){
							//ClerkID��MatterAffair�ж�ȡ
						}else if (clerkType == 3){
							//since 20120-04-16
							//3����object�뵥λ��Ա��ϵ�Ĺ�ϵ�� ObjectOrgClerk��, ���붨��clerkID,clerkID��ֵ��ObjectOrgClerk�е�type��
							Assert.notNull(element.getClerkID(), "MatterAffair��������clerkID����clerkID��object��ObjectOrgClerk�еĹ�ϵ�Ĺ�ϵ����");
							try {
								int objectOrgClerkType = element.getClerkID().intValue();
								ObjectOrgClerk ooc = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, object.getObjectType(), object.getId());
								clerkID = ooc.getClerk1Id();
							} catch (NotFoundException e) {
								throw new IllegalArgumentException("��MatterAffair���ã� task��clerkIDȡ��object��ص�ObjectOrgClerk����ȡʱ��������", e); 
							}
						}else if(clerkType == 4){
							//since 20120-04-16
							//4����affairObject�뵥λ��Ա��ϵ�Ĺ�ϵ�� ObjectOrgClerk��, ���붨��clerkID,clerkID��ֵ��ObjectOrgClerk�е�type��
							Assert.notNull(affairObject, "affairObject ����Ϊ��");
							Assert.notNull(element.getClerkID(), "MatterAffair��������clerkID����clerkID��affairObject��ObjectOrgClerk�еĹ�ϵ�Ĺ�ϵ����");
							try {
								int objectOrgClerkType = element.getClerkID().intValue();
								ObjectOrgClerk ooc = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, affairObject.getObjectType(), affairObject.getId());
								clerkID = ooc.getClerk1Id();
							} catch (NotFoundException e) {
								throw new IllegalArgumentException("��MatterAffair���ã� task��clerkIDȡ��affairObject��ص�ObjectOrgClerk����ȡʱ��������", e); 
							}
						}
						if (clerkID == null) {// ���clerkID��Ϊ��
							clerkID = UserClerkHolder.getClerk().getId();
						}
						if (IS_DEBUG_ENABLED) {
							// log.debug("Taskʹ�õ�ClerkID��" + clerkID);
							String s = String
									.format("processWorkAffair - ����(action=%s)Task��clerkID=%s, affairType=%s, affairID=%s, "
											+ "objectID=%s, dutyerType=%s, dutyerID=%s, note=%s, affairAction=%s",
											theAction, clerkID, affairType, affairID,
											affairObjectID, dutyerType, dutyerID,
											note, affairAction);
							log.debug(s);
						}

						//����MatterAffair����WorkScheme
						if(StringUtils.isNotBlank(element.getSchemeInfo())){
							if(IS_DEBUG_ENABLED){
								log.debug("����MatterAffair���ã�����MattersHandler�е���WorkScheme��" + element.getSchemeInfo());
							}
							WorkScheme newWorkScheme = callWorkScheme(element, work, bizId, bizAction,	object, tag, note, 
									ws, affairObject, clerkID, happenTime, isAffairObjectSameToObject);
							task = newWorkScheme.getTask();
						}else{
							if(IS_DEBUG_ENABLED){
								log.debug("����MatterAffair���ã�����MattersHandler������Task: " + affairType);
							}
							
							task = new Task();
							task.setClerkID(clerkID);
							task.setType(affairType);
							task.setNote(note);
							task.setBeginTime(work.getBeginTime());
							task = getTaskService().createTask(task, new Long[]{affairID}, affairObject, dutyerType, dutyerID);
							
//							task = getTaskService().createTask(clerkID, affairType,	
//									new Long[] { affairID }, affairObjectID, dutyerType,
//									dutyerID, note, work.getBeginTime()/*ȡwork�Ŀ�ʼʱ��*/);
							
							updateTaskEntityObjectsIfRequired(task, relatedItems);
						}
						
					}// if (TokenCreate=1){//�ؽ����½�task
				}// if (action����4)

				// /////////////////////////////////////////////////////////////////////////////////////////
				// 2.��task���д���
				// 0���޴���1�������������룩��2��������ȡ������򳷻����룩��3����ᣨ����ȷ������4����ᣨ������ϣ��ȴ���ȡ����
				// 5�������������������6����ͣ��7��������8����ֹ��9����죻10��ȡ����
				// Ĭ��Ϊ0����ʾ�޴������ڽ��轨��������ϵ�����Ρ�
				// /////////////////////////////////////////////////////////////////////////////////////////
				if (task != null) {

					if (affairAction == MatterAffair.ACTION_END_MATTER/*5*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ����Task��" + task);
						}
						getTaskService().terminateTask(task, null, happenTime);

					} else if (affairAction == MatterAffair.ACTION_HANG/*6*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ��ͣTask(action="
									+ theAction + ")[6]��" + task);
						}
						getTaskService().hangTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_WAKE/*7*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ����Task(action="
									+ theAction + ")[7]��" + task);
						}
						getTaskService().wakeTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_STOP/*8*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ��ֹTask(action="
									+ theAction + ")[8]��" + task);
						}
						getTaskService().stopTask(task, happenTime);

					} else if (affairAction == MatterAffair.ACTION_AVOID/*9*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ���Task(action="
									+ theAction + ")[9]��" + task);
						}
						getTaskService().avoidTask(task, happenTime);
					} else if (affairAction == MatterAffair.ACTION_CANCEL/*10*/) {
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ȡ��Task(action="
									+ theAction + ")[10]��" + task);
						}
						getTaskService().cancelTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_WITHDRAW/*11*/){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ����Task(action="
									+ theAction + ")[11]��" + task);
						}
						getTaskService().withdrawTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_REJECT){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ����Task(action="
									+ theAction + ")[12]��" + task);
						}
						getTaskService().rejectTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_UNDO_MATTER){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ����Task(action="
									+ theAction + ")[2]��" + task);
						}
						getTaskService().undoTask(task, happenTime);
					}else if(affairAction == MatterAffair.ACTION_TRANSFER){
						if (IS_DEBUG_ENABLED) {
							log.debug("processWorkAffair - ת��Task(action="
									+ theAction + ")[15]��" + task);
						}
						getTaskService().transferTask(task, happenTime);
					}
				}
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 3.���ø��ٹ켣
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// �ѵ�ǰwork������task�С�
				if (task != null) {
					getBizTrackNodeInstanceService().createBizTrackNodeInstance(MatterAffair.CATEGORY_WORK,
									work.getType(), work.getSn(), task.getBizTrack());
					if (IS_DEBUG_ENABLED) {
						String s = String
								.format("processWorkAffair - ����(action=%s)BizTrackNodeInstance��category=%s, workType=%s, workSN=%s, task.bizTrack=%s",
										theAction,
										MatterAffair.CATEGORY_WORK,
										(work == null) ? "" : work.getType(),
										(work == null) ? "" : work.getSn(),
										(task == null) ? "" : task.getBizTrack());
						log.debug(s);
					}
				}

				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// 4.����ҵ���������
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				// ����affair��ҵ�������ڲ�״̬Ϊָ��ֵ����0����ԡ�
				if(affairObject != null){
					boolean affairObjectChanged = false;
					
					//Ҫ�ĵ�tag�ȶ���ǰtag��ʱ���޸Ķ����tag��status��lifestage
					short currentTag = affairObject.getTag();
					boolean updateRequired = objectTag >= currentTag;
					
					if (updateRequired && (objectTag != 0)) {
						affairObject.setTag(objectTag);
						log.debug("������AffairObject(" + affairObject + ")�� tag��" + objectTag);
						affairObjectChanged = true;
					}
					// ����affair��ҵ�������ⲿ״̬Ϊָ��ֵ����0����ԡ�
					if (updateRequired && (objectStatus != 0)) {
						//affairObject.setStatus(objectStatus);
						//2011-06-15����Ϊ�޸�ҵ��״̬������״̬
						affairObject.setBizStatus(objectStatus);
						log.debug("������AffairObject(" + affairObject + ")�� BizStatus��" + objectStatus);
						affairObjectChanged = true;
					}
					// ����affair��ҵ�����������׶�Ϊָ��ֵ����0����ԡ�
					if (updateRequired && (lifeStage != 0)) {
						affairObject.setLifeStage(lifeStage);
						log.debug("������AffairObject(" + affairObject + ")�� lifeStage��" + lifeStage);
						affairObjectChanged = true;
					}
					// ����affair��ҵ�����Ĺؼ�����ΪbizId����0����ԡ�
					if ((objectKeyMatter == 1) ) {
						affairObject.setActiveMatter(bizId);
						log.debug("������AffairObject(" + affairObject + ")�� ActiveMatter��" + bizId);
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
				// 5.��task��affair���еݹ鴦��
				// ///////////////////////////////////////////////////////////////////////////////////////////////
				processTaskAffair(task, affairID, affairAction, affairObject, (short) 0);

			}// TASK����
		}// for
	}// processWorkAffair
	
	
	/**
	 * @param ma MatterAffair����
	 * @param work ִ��MatterAffair��Work����
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
			throw new IllegalArgumentException("MatterAffair���ô��󣬱������ó�WorkScheme����Ϣ��schemeName!method����");
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
			log.warn("������û��objectId����ȷ�ϲ�������");
		}else{
			//�����֮ǰ���κ�һ�����󣬶�ȡ�ö��󣬲�����Ҫ WS ����id��ѯ�¶���
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
						log.debug(String.format("Ϊҵ����� ��%s���ҵ�����Ϊ��%s����Task���ϣ�%s", item, taskType, tasks));
					}
				}
			}
			
			//ȥ��Task������
			Set<Task> set = new HashSet<Task>(relatedTasks);
			relatedTasks = new HashSet<Task>();
			for(Task t: set){
				if(t.getStatus() < 9){
					relatedTasks.add(t);
				}else{
					if(IS_DEBUG_ENABLED){
						log.debug("Task״̬���ڵ���9���Ӽ������޳���" + t);
					}
				}
			}
			
			int taskSize = relatedTasks.size();
			if(taskSize > 0 && IS_DEBUG_ENABLED){
				log.debug("�ϲ������ҵ�������������Task���ϣ�" + relatedTasks);
			}
			if(taskSize == 1){
				Task next = relatedTasks.iterator().next();
				
				if(IS_DEBUG_ENABLED){
					log.debug(String.format("�ҵ�����Ϊ %s�ķ���������task,��ʹ֮��ΪMatterAffair����֮��: %s", taskType, next));
				}
				return next;
			}else if(taskSize > 1){
				log.warn("��ǰWorkScheme��ص�����ҵ�������ص�Task������Ψһ���޷����һ��ȷ�е�Task: taskType=" + taskType);
			}else{
				if(IS_DEBUG_ENABLED){
					log.debug("���ҵ�������������Task����Ϊ�գ��޷�ȡ��task: taskType=" + taskType);
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
				log.debug("��������ͨҵ�������MatterAffair�Զ�������Task֮��Ĺ�ϵ��" + task);
			}
		}
	}
}
