/*
 * $Id: DatumScheme.java 5044 2011-11-08 09:15:00Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes.datum;



import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.lifecycle.Application;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.ObjectData;
import cn.redflagsoft.base.event2.AttachmentEvent;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.DatumService;
import cn.redflagsoft.base.service.ObjectDataService;
import cn.redflagsoft.base.service.ObjectsService;

public class DatumScheme extends AbstractScheme implements EventDispatcherAware{
	public static final Log log = LogFactory.getLog(DatumScheme.class);
	private DatumService datumService;
	private String fileId;
    private String name;
    private Long categoryID;
    private short orderNo;
    private Long objectId;
    private Long id;
	private ObjectDataService objectDataService;
	private EventDispatcher dispatcher;
	private ObjectsService objectsService;
    
	/**
	 * @param objectDataService the objectDataService to set
	 */
	public void setObjectDataService(ObjectDataService objectDataService) {
		this.objectDataService = objectDataService;
	}

	/**
	 * @param objectsService the objectsService to set
	 */
	public void setObjectsService(ObjectsService objectsService) {
		this.objectsService = objectsService;
	}

	public short getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(short orderNo) {
		this.orderNo = orderNo;
	}

	public Long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public DatumService getDatumService() {
		return datumService;
	}

	public void setDatumService(DatumService datumService) {
		this.datumService = datumService;
	}
	

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String doUpload() {
		Assert.notNull(fileId, "���ϸ�������Ϊ��");
		Assert.notNull(name, "�������Ʋ���Ϊ��");
//		if(fileId!=null&&(name!=null)){//��fileIdΪcontent
		Datum d=new Datum();
		d.setCategoryID(0L);
//		d.setObjectID(0L);
		d.setWorkSN(0L);
		d.setContent(fileId);
		d.setName(name);
		d.setAbbr(name);
		d.setBrief(name);
		d.setOrigin((byte)0);
		d.setCreationTime(new Date());
		d.setFormat((byte)0);
		datumService.saveDatum(d);
		return "��������ɹ���";
//		}
//
//		return "��������ʧ��";
	}
	
	/**
	 * ɾ��ָ��ID�����ϡ�
	 * @return
	 */
	public String doDelete() {
		Assert.notNull(getId(), "����ID����Ϊ��");
		Datum datum = datumService.getDatum(getId());
		if(datum != null){
			datumService.deleteDatum(datum);
		}
		return "ɾ�����ϳɹ���";
	}
	
	/**
	 * ɾ��ָ��ID�ĸ�����
	 * @return
	 */
	public String doDeleteAttachment(){
		Assert.notNull(getId(), "��ɾ���ļ�ID����Ϊ��");
		Application.getContext().getAttachmentManager().removeAttachment(getId());
		return "ɾ�������ɹ���";
	}
	
	/**
	 * ǰ̨�����б���ɾ��ʱ���õķ�����
	 * 
	 * @return
	 */
	public String doTryDeleteAttachment(){
		Assert.notNull(getId(), "��ɾ���ļ�ID����Ϊ��");
		Attachment a = Application.getContext().getAttachmentManager().getAttachment(getId());
		if(a != null){
			//Application.getContext().getAttachmentManager().deleteAttachment(a);
			//��Ϊͨ��AttachmentManager��ѯ�����Ķ������Ͳ���Attachment����
			//Attachment�����࣬�޷�ֱ�ӱ�Hibernateɾ����
			Application.getContext().getAttachmentManager().removeAttachment(a.getId());
			
			//ɾ��������task��work��֮��Ĺ�����ϵ
			objectsService.deleteObjectsByAttachment(a.getId());
			
			if(dispatcher != null){
				log.debug("��������ɾ���¼�");
				dispatcher.dispatchEvent(new AttachmentEvent(AttachmentEvent.Type.DELETED, a));
			}
			return "ɾ�������ɹ���";
		}else{
			log.warn("�ļ������ڣ���ִ��ɾ��: " + getId());
			return "�ļ������ڣ���ִ��ɾ����";
		}
	}
	
	
	public Object doScheme() throws SchemeException {
		return super.doScheme();
	}
	
	
	public String doSpeciallyUpload() {
		Assert.notNull(fileId, "���ϸ�������Ϊ��");
		Assert.notNull(name, "�������Ʋ���Ϊ��");
		Assert.notNull(categoryID, "�������ID����Ϊ��");
//		if(fileId!=null&&(name!=null)&&categoryID!=null){//��fileIdΪcontent
		Datum d=new Datum();
		d.setCategoryID(categoryID);
		//d.setObjectID(0L);
		d.setWorkSN(0L);
		d.setContent(fileId);
		d.setName(name);
		d.setAbbr(name);
		d.setBrief(name);
		d.setOrigin((byte)0);
		d.setCreationTime(new Date());
		Clerk clerk = UserClerkHolder.getClerk();
		d.setPromulgator(clerk.getEntityID());
		d.setPromulgatorAbbr(clerk.getEntityName());
		d.setFormat((byte)0);
		d.setOrderNo(orderNo);
		d.setStatus(Datum.STATUS_��ǰ����);
		d.setType(Datum.TYPE_DATUM);
		
		datumService.saveDatum(d);
		return "��������ɹ���";
//		}
//
//		return "��������ʧ��";
	}
	
	
	public String doUploadDoc(){
		Assert.notNull(fileId, "�������ϴ��ļ���");
		Assert.notNull(objectId, "����ָ��ҵ�����");
		Assert.notNull(name, "�ļ����Ʋ���Ϊ�ա�");
		Assert.notNull(categoryID, "categoryID����Ϊ�ա�");
		
		Datum d = new Datum();
		d.setCategoryID(categoryID);
		//d.setObjectID(objectId);
		d.setContent(fileId);
		d.setName(name);
		d.setAbbr(name);
		d.setBrief(name);
		d.setOrigin((byte)0);
		d.setCreationTime(new Date());
		d.setFormat((byte)0);
		d.setStatus(Datum.STATUS_��ǰ����);
		d.setType(Datum.TYPE_DATUM);
		datumService.saveDatum(d);
		
		ObjectData objectData = new ObjectData();
		objectData.setDatumCategoryID(categoryID);
		objectData.setDatumID(d.getId());
		objectData.setObjectID(objectId);
		objectData.setStatus(Datum.STATUS_��ǰ����);
		objectData.setType(Datum.TYPE_DATUM);
		objectDataService.saveObjectData(objectData);
		
		return "�����ϴ��ɹ�";
	}

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
