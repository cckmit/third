/*
 * $Id: ObjectAdminServiceImpl.java 5926 2012-07-12 09:30:28Z zf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;

import cn.redflagsoft.base.bean.ArchivingStatus;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.State;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;
import cn.redflagsoft.base.bean.commons.ObjectArchive;
import cn.redflagsoft.base.bean.commons.ObjectCancel;
import cn.redflagsoft.base.bean.commons.ObjectFinish;
import cn.redflagsoft.base.bean.commons.ObjectInvalid;
import cn.redflagsoft.base.bean.commons.ObjectPause;
import cn.redflagsoft.base.bean.commons.ObjectPublish;
import cn.redflagsoft.base.bean.commons.ObjectShelve;
import cn.redflagsoft.base.bean.commons.ObjectTrans;
import cn.redflagsoft.base.bean.commons.ObjectUnarchive;
import cn.redflagsoft.base.bean.commons.ObjectWake;
import cn.redflagsoft.base.dao.ObjectAdminDao;
import cn.redflagsoft.base.event2.ObjectAdminEvent;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.ObjectAdminService;
import cn.redflagsoft.base.service.ObjectService;

/**
 * 对象管理.
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectAdminServiceImpl implements ObjectAdminService, EventDispatcherAware {
	private static final Log log = LogFactory.getLog(ObjectAdminServiceImpl.class);
	@SuppressWarnings("unchecked")
	private ObjectService objectService;
	private ClerkService clerkService;
	private ObjectAdminDao objectAdminDao;
	private EventDispatcher dispatcher;
	
	/**
	 * @param objectService the objectService to set
	 */
	@SuppressWarnings({"unchecked" })
	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}
	/**
	 * @param clerkService the clerkService to set
	 */
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}
	/**
	 * @param objectAdminDao the objectAdminDao to set
	 */
	public void setObjectAdminDao(ObjectAdminDao objectAdminDao) {
		this.objectAdminDao = objectAdminDao;
	}
	
	private final ObjectAdmin saveObjectAdmin(RFSObject object, ObjectAdmin oa, Class<? extends ObjectAdmin> clazz){
		//处理前台的PrjCancel
		if(oa == null){
			try {
				oa = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		if(oa.getOperateTime() == null){
			oa.setOperateTime(new Date());
		}
		checkClerk(oa);
		
		oa.setObjId(object.getId());
		oa.setObjName(object.getName());
		oa.setObjType(object.getObjectType());
	
//		beforeSaveObjectAdmin(object, oa, clazz);
		
		ObjectAdmin admin = objectAdminDao.save(oa);
		if(log.isDebugEnabled()){
			log.debug("保存对象管理日志：" + admin);
		}
		return admin;
	}

	private void checkClerk(ObjectAdmin objectAdminLog){
		Clerk clerk = UserClerkHolder.getClerk();
		
		if(objectAdminLog.getClerkId() == null){
			objectAdminLog.setClerkId(clerk.getId());
			objectAdminLog.setClerkName(clerk.getName());
		}
		//判断ClerkId是否有效
		if(objectAdminLog.getClerkName() == null){
			Clerk c = clerkService.getClerk(objectAdminLog.getClerkId());
			if(c == null){
				log.warn("Clerk not exists: " + objectAdminLog.getClerkId());
				if(log.isDebugEnabled()){
					log.debug("Set current user as clerk.");
				}
				objectAdminLog.setClerkId(clerk.getId());
				objectAdminLog.setClerkName(clerk.getName());
			}else{
				objectAdminLog.setClerkName(c.getName());
			}
		}		
	}
	
	protected static interface Callback{
		void doInCallback(RFSObject oldObject, RFSObject newObject, ObjectAdmin objectAdmin);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends ObjectAdmin> T saveObjectAdmin(RFSObject object, T admin, Class<T> clazz, Callback callback){
		//保存管理日志
		ObjectAdmin admin2 = saveObjectAdmin(object, admin, clazz);
		
		//更新对象
		RFSObject old = (RFSObject) object.copy();
		callback.doInCallback(old, object, admin2);
		objectService.updateObject(old, object);
		
		//返回管理日志
		return (T) admin2;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectAdminService#cancelObject(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.commons.ObjectCancel)
	 */
	public ObjectCancel cancelObject(RFSObject object, ObjectCancel cancel) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setCancelId(objectAdmin.getId());
				newObject.setState(State.STATE_取消);
			}
		};
		ObjectCancel admin = saveObjectAdmin(object, cancel, ObjectCancel.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.CANCEL);
		return admin;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectAdminService#invalidObject(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.commons.ObjectInvalid)
	 */
	public ObjectInvalid invalidObject(RFSObject object, ObjectInvalid invalid) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setDeleteId(objectAdmin.getId());
//				prj.setState(State.STATE_作废);
				newObject.setStatus(ArchivingStatus.STATUS_作废档);
			}
		};
		ObjectInvalid admin = saveObjectAdmin(object, invalid, ObjectInvalid.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.INVALID);
		return admin;
	}
	
	
	protected void dispatchEvent(RFSObject object, ObjectAdmin admin, ObjectAdminEvent.Type type){
		if(dispatcher != null){
			ObjectAdminEvent event = new ObjectAdminEvent(type, object, admin);

			if(log.isDebugEnabled()){
				log.debug("发出事件：" + event);
			}
			dispatcher.dispatchEvent(event);
		}else{
			log.warn("没有事件派发器，无法派发事件。");
		}
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher arg0) {
		this.dispatcher = arg0;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectAdminService#archiveObject(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.commons.ObjectArchive)
	 */
	public ObjectArchive archiveObject(RFSObject object, ObjectArchive archive) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setArchiveId(objectAdmin.getId());
				newObject.setStatus(ArchivingStatus.STATUS_归档);
			}
		};
		ObjectArchive admin = saveObjectAdmin(object, archive, ObjectArchive.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.ARCHIVE);
		return admin;
	}
	
	public ObjectUnarchive unarchiveObject(RFSObject object,
			ObjectUnarchive unarchive) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setUnarchiveId(objectAdmin.getId());
				newObject.setStatus(ArchivingStatus.STATUS_公开档);
			}
		};
		ObjectUnarchive admin = saveObjectAdmin(object, unarchive, ObjectUnarchive.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.UNARCHIVE);
		return admin;
	}
	
	public ObjectFinish finishObject(RFSObject object, ObjectFinish finish) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setFinishId(objectAdmin.getId());
				//newObject.setState(Status.)
			}
		};
		ObjectFinish admin = saveObjectAdmin(object, finish, ObjectFinish.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.FINISH);
		return admin;
	}
	
	public ObjectPause pauseObject(RFSObject object, ObjectPause pause) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setPauseId(objectAdmin.getId());
				newObject.setState(State.STATE_暂停);
			}
		};
		ObjectPause admin = saveObjectAdmin(object, pause, ObjectPause.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.PAUSE);
		return admin;
	}
	
	public ObjectPublish publishObject(RFSObject object, ObjectPublish publish) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setPublishId(objectAdmin.getId());
				newObject.setStatus(ArchivingStatus.STATUS_公开档);
			}
		};
		ObjectPublish admin = saveObjectAdmin(object, publish, ObjectPublish.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.PUBLISH);
		return admin;
	}
	
	public ObjectShelve shelveObject(RFSObject object, ObjectShelve shelve) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setShelveId(objectAdmin.getId());
				newObject.setState(State.STATE_暂缓);
			}
		};
		ObjectShelve admin = saveObjectAdmin(object, shelve, ObjectShelve.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.SHELVE);
		return admin;
	}
	
	public ObjectTrans transObject(RFSObject object, ObjectTrans trans) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setTransId(objectAdmin.getId());
				newObject.setState(State.STATE_转出);
			}
		};
		ObjectTrans admin = saveObjectAdmin(object, trans, ObjectTrans.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.TRANS);
		return admin;
	}
	
	public ObjectWake wakeObject(RFSObject object, ObjectWake wake) {
		Callback callback = new Callback(){
			public void doInCallback(RFSObject oldObject, RFSObject newObject,	ObjectAdmin objectAdmin) {
				newObject.setWakeId(objectAdmin.getId());
				newObject.setState(State.STATE_正常);
			}
		};
		ObjectWake admin = saveObjectAdmin(object, wake, ObjectWake.class, callback);
		dispatchEvent(object, admin, ObjectAdminEvent.Type.WAKE);
		return admin;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectAdminService#getObjectAdmin(long)
	 */
	@SuppressWarnings("unchecked")
	public <T extends ObjectAdmin> T getObjectAdmin(long objectAdminID) {
		return (T) objectAdminDao.get(objectAdminID);
	}
}
