/*
 * $Id: DatumScheme.java 5044 2011-11-08 09:15:00Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
		Assert.notNull(fileId, "资料附件不能为空");
		Assert.notNull(name, "资料名称不能为空");
//		if(fileId!=null&&(name!=null)){//该fileId为content
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
		return "附件保存成功！";
//		}
//
//		return "附件保存失败";
	}
	
	/**
	 * 删除指定ID的资料。
	 * @return
	 */
	public String doDelete() {
		Assert.notNull(getId(), "资料ID不能为空");
		Datum datum = datumService.getDatum(getId());
		if(datum != null){
			datumService.deleteDatum(datum);
		}
		return "删除资料成功！";
	}
	
	/**
	 * 删除指定ID的附件。
	 * @return
	 */
	public String doDeleteAttachment(){
		Assert.notNull(getId(), "被删除文件ID不能为空");
		Application.getContext().getAttachmentManager().removeAttachment(getId());
		return "删除附件成功！";
	}
	
	/**
	 * 前台附件列表中删除时调用的方法。
	 * 
	 * @return
	 */
	public String doTryDeleteAttachment(){
		Assert.notNull(getId(), "被删除文件ID不能为空");
		Attachment a = Application.getContext().getAttachmentManager().getAttachment(getId());
		if(a != null){
			//Application.getContext().getAttachmentManager().deleteAttachment(a);
			//因为通过AttachmentManager查询出来的对象类型不是Attachment而是
			//Attachment的子类，无法直接被Hibernate删除。
			Application.getContext().getAttachmentManager().removeAttachment(a.getId());
			
			//删除附件与task，work等之间的关联关系
			objectsService.deleteObjectsByAttachment(a.getId());
			
			if(dispatcher != null){
				log.debug("发出附件删除事件");
				dispatcher.dispatchEvent(new AttachmentEvent(AttachmentEvent.Type.DELETED, a));
			}
			return "删除附件成功！";
		}else{
			log.warn("文件不存在，不执行删除: " + getId());
			return "文件不存在，不执行删除。";
		}
	}
	
	
	public Object doScheme() throws SchemeException {
		return super.doScheme();
	}
	
	
	public String doSpeciallyUpload() {
		Assert.notNull(fileId, "资料附件不能为空");
		Assert.notNull(name, "资料名称不能为空");
		Assert.notNull(categoryID, "资料类别ID不能为空");
//		if(fileId!=null&&(name!=null)&&categoryID!=null){//该fileId为content
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
		d.setStatus(Datum.STATUS_当前资料);
		d.setType(Datum.TYPE_DATUM);
		
		datumService.saveDatum(d);
		return "附件保存成功！";
//		}
//
//		return "附件保存失败";
	}
	
	
	public String doUploadDoc(){
		Assert.notNull(fileId, "必须先上传文件。");
		Assert.notNull(objectId, "必须指定业务对象。");
		Assert.notNull(name, "文件名称不能为空。");
		Assert.notNull(categoryID, "categoryID不能为空。");
		
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
		d.setStatus(Datum.STATUS_当前资料);
		d.setType(Datum.TYPE_DATUM);
		datumService.saveDatum(d);
		
		ObjectData objectData = new ObjectData();
		objectData.setDatumCategoryID(categoryID);
		objectData.setDatumID(d.getId());
		objectData.setObjectID(objectId);
		objectData.setStatus(Datum.STATUS_当前资料);
		objectData.setType(Datum.TYPE_DATUM);
		objectDataService.saveObjectData(objectData);
		
		return "资料上传成功";
	}

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
