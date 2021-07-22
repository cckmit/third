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
	 * ���ݵ�һ�������ID�͹�ϵ���ͣ���ѯ�����ĵڶ�������ID
	 * @param objectID ��һ�������ID
	 * @param type ������ϵ����
	 * @return �����ĵڶ��������ID
	 */
	Long getRelatedObjectID(Long objectID,int type);
	
//	/**
//	 * ����taskType��workType�Ͷ����ID������֮��صĸ����б�
//	 * 
//	 * @param taskType task����
//	 * @param workType work����
//	 * @param objectID ����ID
//	 * @return ��������
//	 */
//	List<Attachment> findAttachments(int taskType, int workType, final Long objectID);

	/**
	 * ��ѯ������
	 * 
	 * @param taskType Ϊ0ʱ�����������
	 * @param workType Ϊ0ʱ�����������
	 * @param objectId ����Ϊ0
	 * @param objectType Ϊ0ʱ��ʾ������
	 * @return ��������
	 */
	List<Attachment> findAttachments(int taskType, int workType, long objectId, int objectType);

	/**
	 * ���渽����task��work֮��Ĺ�ϵ��
	 * 
	 * @param task Task
	 * @param work Work
	 * @param attachmentIds ����ID����
	 */
	void saveAttachments(Task task, Work work, List<Long> attachmentIds);

	/**
	 * ɾ��������task��work֮��Ĺ�ϵ��
	 * @param id
	 */
	void removeByAttachment(Long id);
	
	Objects saveOrUpdate(long fstObject, long sndObject, int type, String remark);
}
