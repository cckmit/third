/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Objects;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * 
 * @author ymq
 *
 */
public interface ObjectsDao extends Dao<Objects, Long>{
	
	/**
	 * 根据第一个对象的ID和关系类型，查询关联的第二个对象ID
	 * @param objectID 第一个对象的ID
	 * @param type 关联关系类型
	 * @return 关联的第二个对象的ID
	 */
	Long getRelatedObjectID(Long objectID,int type);
	
//	/**
//	 * 根据taskType、workType和对象的ID查找与之相关的附件列表。
//	 * 
//	 * @param taskType task类型
//	 * @param workType work类型
//	 * @param objectID 对象ID
//	 * @return 附件集合
//	 */
//	List<Attachment> findAttachments(int taskType, int workType, final Long objectID);

	/**
	 * 查询附件。
	 * 
	 * @param taskType 为0时忽略这个条件
	 * @param workType 为0时忽略这个条件
	 * @param objectId 不能为0
	 * @param objectType 为0时表示主对象
	 * @return 附件集合
	 */
	List<Attachment> findAttachments(int taskType, int workType, long objectId, int objectType);

	/**
	 * 保存附件与task、work之间的关系。
	 * 
	 * @param task Task
	 * @param work Work
	 * @param attachmentIds 附件ID集合
	 */
	void saveAttachments(Task task, Work work, List<Long> attachmentIds);

	/**
	 * 删除附件与task、work之间的关系。
	 * @param id
	 */
	void removeByAttachment(Long id);
	
	Objects saveOrUpdate(long fstObject, long sndObject, int type, String remark);
}
