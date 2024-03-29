/*
 * $Id: ObjectProgressReportWorkScheme.java 6130 2012-11-23 02:21:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.event2.AttachmentEvent;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.service.ObjectsService;
import cn.redflagsoft.base.service.ProgressService;


/**
 * 进度报告WorkScheme，注意配置必须指定具体的 ObjectService实现类。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ObjectProgressReportWorkScheme extends AbstractWorkScheme {
	private static final Log log = LogFactory.getLog(ObjectProgressReportWorkScheme.class);

	public static final int OBJECTS_TYPE_进度报告与附件之间关系 = 20001;
	
	private Progress progress;
	
	private ProgressService progressService;

	/**
	 * @return the progressService
	 */
	public ProgressService getProgressService() {
		return progressService;
	}

	/**
	 * @param progressService the progressService to set
	 */
	public void setProgressService(ProgressService progressService) {
		this.progressService = progressService;
	}

	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	
	/**
	 * 获取上一次的进度报告。
	 * @return
	 */
	public Object viewLoadLastProgress(){
		RFSObject object = getObject();
		Progress progress = getProgressService().getLastProgress(object);

//		Map<String, ?> map = BeanUtils.getProperties(progress);
		Map<String, Object> newMap = new HashMap<String, Object>();
//		for(Map.Entry<String, ?> en: map.entrySet()){
//			newMap.put("last_" + en.getKey(), en.getValue());
//		}
		
		log.debug("上次报告者：" + progress.getReporterName());
		
		newMap.put("last_reporterName", progress.getReporterName());
		newMap.put("last_description", progress.getDescription());
		
		if(progress.getCreationTime() != null){
			newMap.put("last_creationTime", AppsGlobals.formatDate(progress.getCreationTime()));
		}
		if(progress.getReportTime() != null){
			newMap.put("last_reportTime", AppsGlobals.formatDate(progress.getReportTime()));
		}
		if(progress.getModificationTime() != null){
			newMap.put("last_modificationTime", progress.getModificationTime());
		}
		return newMap;
	}
	
	
	/**
	 * 上报进度。
	 * @return
	 */
	public Object doSubmitProgress(){
		RFSObject object = getObject();
		progress.setRefObjectId(object.getId());
		progress.setRefObjectName(object.getName());
		progress.setRefObjectType(object.getObjectType());
		progress = getProgressService().saveProgress(progress);
		saveProgressAttachments(progress, getFileIds());
		return "上报进度操作成功";
	}
	
	
	public Object doReport(){
		RFSObject object = getObject();
//		progress.setRefObjectId(object.getId());
//		progress.setRefObjectName(object.getName());
//		progress.setRefObjectType(object.getObjectType());
		processProgressBeforeSave(progress);
		progress = getProgressService().createProgress(object, progress);
		saveProgressAttachments(progress, getFileIds());
		return "上报进度操作成功";
	}
	
	protected void processProgressBeforeSave(Progress progress2) {
		
	}

	/**
	 * 
	 * @param prss
	 * @param attachmentIds
	 */
	public static void saveProgressAttachments(Progress prss, List<Long> attachmentIds) {
		if(attachmentIds != null){
			ObjectsService objectsService = Application.getContext().get("objectsService", ObjectsService.class);
			Assert.notNull(objectsService, "objectsService is required.");
			for(Long attachmentId: attachmentIds){
				//暂时不判断附件是否存在
				/**
				Attachment attachment = attachmentManager.getAttachment(attachmentId);
				if(attachment == null){
					throw new IllegalArgumentException("找不到上传的附件：" + attachmentId);
				}
				*/
//				ResultFilter filter = ResultFilter.createEmptyResultFilter();
//				SimpleExpression eq = Restrictions.eq("fstObject", prss.getId());
//				SimpleExpression eq2 = Restrictions.eq("type", OBJECTS_TYPE_进度报告与附件之间关系);
//				SimpleExpression eq3 = Restrictions.eq("sndObject", attachmentId);
//				SimpleExpression eq4 = Restrictions.eq("status", (byte) 1);
//				
//				Logic and = Restrictions.logic(eq).and(eq2).and(eq3).and(eq4);
//				filter.setCriterion(and);
//				List<Objects> objectsList = objectsService.findObjects(filter);
//				
//				if(objectsList == null || objectsList.isEmpty()){
//					//进度与附件之间
//					Objects a = new Objects();
//					a.setFstObject(prss.getId());
//					a.setSndObject(attachmentId);
//					a.setStatus((byte) 1);
//					a.setType(OBJECTS_TYPE_进度报告与附件之间关系);
//					a.setRemark("进度报告与附件之间关系");
//					objectsService.createObjects(a);
//				}
				
				objectsService.saveOrUpdateObjects(prss.getId(), attachmentId, OBJECTS_TYPE_进度报告与附件之间关系, "进度报告与附件之间关系");
			}
		}
	}
	
	
	
	/**
	 * 附件删除时，删除关系表中的相关记录。
	 *
	 */
	public static class ProgressAttachmentEventListener implements EventListener<AttachmentEvent>{
		private ObjectsDao objectsDao;
		
		/**
		 * @param objectsDao the objectsDao to set
		 */
		public void setObjectsDao(ObjectsDao objectsDao) {
			this.objectsDao = objectsDao;
		}

		/* (non-Javadoc)
		 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
		 */
		public void handle(AttachmentEvent event) {
			if(event.getType() == AttachmentEvent.Type.DELETED){
				Long attachmentId = event.getSource().getId();
				Logic logic = Restrictions.logic(Restrictions.eq("type", OBJECTS_TYPE_进度报告与附件之间关系))
					.and(Restrictions.eq("sndObject", attachmentId));
				objectsDao.remove(logic);
			}
		}
	}
}
