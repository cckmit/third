/*
 * $Id: ObjectPeopleServiceImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.CriterionBuffer;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectPeople;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectPeopleDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ObjectPeopleService;
import cn.redflagsoft.base.vo.BatchUpdateResult;

/**
 *
 */
public class ObjectPeopleServiceImpl implements ObjectPeopleService {
	private static final Log log = LogFactory.getLog(ObjectPeopleServiceImpl.class);

	private ObjectPeopleDao objectPeopleDao;	 
    
	public void setObjectPeopleDao(ObjectPeopleDao objectPeopleDao) {
		this.objectPeopleDao = objectPeopleDao;
	}
	
	public ObjectPeople createObjectPeople(Long objectId, Long clerkID,	int type, int objectType){
		if(objectId == null || clerkID == null){
			log.warn("objectId or clerkId is null for createObjectPeople.");
			return null;
		}
		
		ObjectPeople op = new ObjectPeople();
		op.setObjectID(objectId);
		op.setObjectType(objectType);
		op.setPeopleID(clerkID);
		op.setType(type);
		op.setStatus((byte) 1);
		return createObjectPeople(op);
	}

	public ObjectPeople createObjectPeople(ObjectPeople op) {	
		return objectPeopleDao.save(op);
	}

	public List<Clerk> findClerksByObjectId(Long objectId, int type) {		
		return objectPeopleDao.findClerksByObjectId(objectId, type);
	}

	public List<RFSObject> findObjectsByPeopleId(Long peopleId, int type) {		
		return objectPeopleDao.findObjectsByPeopleId(peopleId, type);
	}

	public void removeObjectPeople(Long objectId, Long clerkID,
			int type,int objectType) {
		objectPeopleDao.removeObjectPeople(objectId, clerkID, type,objectType);
	}

	public int getObjectPeopleCount(Long objectId, Long peopleId,int type,int objectType) {
		return objectPeopleDao.getObjectPeopleCount(objectId, peopleId,type,objectType);
	}

	public int addObjectFocus(RFSObject object, Clerk clerk) {
		int count = getObjectPeopleCount(object.getId(), clerk.getId(), ObjectPeople.TYPE_FOCUS, object.getObjectType());
		if(count > 0){
			if(log.isDebugEnabled()){
				log.debug(String.format("�����ע�Ѿ����(objectId=%s, clerkId=%s)", object.getId(), clerk.getId()));
			}
			return 0;
		}
		
		createObjectPeople(object.getId(), clerk.getId(), ObjectPeople.TYPE_FOCUS, object.getObjectType());
//		ObjectPeople op = new ObjectPeople();
//		op.setPeopleID(clerk.getId());
//		op.setObjectID(object.getId());
//		op.setType(ObjectPeople.TYPE_FOCUS);
//		op.setObjectType(object.getObjectType());
//		op.setCreationTime(new Date());
//		op.setModificationTime(new Date());
//		createObjectPeople(op);
		return 1;
	}

	public int removeObjectFocus(RFSObject object, Clerk clerk) {
		int count = getObjectPeopleCount(object.getId(), clerk.getId(), ObjectPeople.TYPE_FOCUS, object.getObjectType());
		if(count > 0){
			return objectPeopleDao.removeObjectPeople(object.getId(), clerk.getId(), ObjectPeople.TYPE_FOCUS, object.getObjectType());
		}
		return 0;
	}

	public List<Long> findObjectIdsByPeopleId(Long peopleId, int type) {
		return objectPeopleDao.findObjectIdsByPeopleId(peopleId, type);
	}

	public List<Long> findPeopleIdsByObjectId(Long objectId, int type) {
		return objectPeopleDao.findPeopleIdsByObjectId(objectId, type);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectPeopleService#setObjectsToPeople(java.lang.Long, java.util.List, short, short)
	 */
	public BatchUpdateResult setObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType) {
		if(peopleId == null){
			//��ǰ��Ա
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		
		CriterionBuffer buffer = new CriterionBuffer();
		buffer.and(Restrictions.eq("type", type));
		buffer.and(Restrictions.eq("peopleID", peopleId));
		buffer.and(Restrictions.eq("objectType", objectType));
		
		//��������ύΪ�գ����ԭ������
		if(objectIds == null || objectIds.isEmpty()){
			int rows = objectPeopleDao.remove(buffer.toCriterion());
			return new BatchUpdateResult(0, rows, 0);
		}
		
		//��ѯ�����õ���Ŀ
		//Ҫɾ����ObjectPeople��ID����
		List<Long> deletingObjectPeopleIds = new ArrayList<Long>();
		//ԭ�����еĶ���ID����
		List<Long> oldObjectIds = new ArrayList<Long>();
		
		ResultFilter filter = new ResultFilter(buffer.toCriterion(), null);
		List<ObjectPeople> list = objectPeopleDao.find(filter);
		
		for(ObjectPeople op:list){
			if(objectIds.contains(op.getObjectID())){
				oldObjectIds.add(op.getObjectID());
				//log.debug("����Ŀ�Ѿ����ã�" + oe.getObjectID());
			}else{
				deletingObjectPeopleIds.add(op.getId());
			}
		}
		
		int deletedRows = 0;
		int createdRows = 0;
		
		//ɾ����ǰ�е�ǰ̨�ύû�е�
		if(!deletingObjectPeopleIds.isEmpty()){
			deletedRows = objectPeopleDao.remove(Restrictions.in("id", deletingObjectPeopleIds));
		}
		
		//���±�����������ǰû�е�
		for(Long id: objectIds){
			if(!oldObjectIds.contains(id)){
//				ObjectPeople op = new ObjectPeople();
//				op.setObjectID(id);
//				op.setObjectType(objectType);
//				op.setPeopleID(peopleId);
//				op.setStatus((byte) 1);
//				op.setType(type);
//				objectPeopleDao.save(op);
				
				ObjectPeople op = createObjectPeople(id, peopleId, type, objectType);
				if(op != null){
					createdRows++;
				}
			}else{
				log.debug("�ö����Ѿ����ã�" + id);
			}
		}
		return new BatchUpdateResult(createdRows, deletedRows, 0);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectPeopleService#findObjectIdsByPeopleId(java.lang.Long, short, short)
	 */
	public List<Long> findObjectIdsByPeopleId(Long peopleId, int type, int objectType) {
		return objectPeopleDao.findObjectIdsByPeopleId(peopleId, type, objectType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectPeopleService#removeObjectsOfPeople(java.lang.Long, java.util.List, short, short)
	 */
	public int removeObjectsOfPeople(Long peopleId, List<Long> objectIds, int type, int objectType) {
		if(objectIds == null || objectIds.isEmpty()){
			log.warn("��ɾ���Ķ��󼯺�Ϊ�գ�����0");
			return 0;
		}
		if(peopleId == null){
			//��ǰ��Ա
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		
		CriterionBuffer buffer = new CriterionBuffer();
		buffer.and(Restrictions.eq("type", type));
		buffer.and(Restrictions.eq("peopleID", peopleId));
		buffer.and(Restrictions.eq("objectType", objectType));
		buffer.and(Restrictions.in("objectID", objectIds));
		return objectPeopleDao.remove(buffer.toCriterion());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectPeopleService#addObjectsToPeople(java.lang.Long, java.util.List, short, short)
	 */
	public BatchUpdateResult addObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType) {
		if(peopleId == null){
			//��ǰ��Ա
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		
		int deletedRows = removeObjectsOfPeople(peopleId, objectIds, type, objectType);
		int createdRows = 0;
		for(Long objectId: objectIds){
			ObjectPeople op = createObjectPeople(objectId, peopleId, type, objectType);
			if(op != null){
				createdRows++;
			}
		}
		return new BatchUpdateResult(createdRows - deletedRows, deletedRows, 0);
	}
	
	
//	public BatchUpdateResult setObjectsToPeople_pss2(Long peopleId, List<Long> objectIds, int type, int objectType) {
//		if(peopleId == null){
//			//��ǰ��Ա
//			Clerk uc = UserClerkHolder.getClerk();
//			peopleId = uc.getId();
//		}
//		
//		int deletedRows = 0;
//		int createdRows = 0;
//		
//		//�����ύid
//		for(Long id: objectIds){
//			ObjectPeople op = new ObjectPeople();
//			op.setObjectID(id);
//			op.setObjectType(objectType);
//			op.setPeopleID(peopleId);
//			op.setStatus((byte) 1);
//			op.setType(type);
//			objectPeopleDao.save(op);
//			createdRows++;
//		}
//		return new BatchUpdateResult(createdRows, deletedRows, 0);
//	}
}
