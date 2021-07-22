/*
 * $Id: WorkService.java 5848 2012-06-07 07:44:16Z lf $
 * WorkService.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * @author mwx
 *
 */
public interface WorkService {
	
	/**
	 * ���� Work
	 * 
	 * @param work
	 * @return Work
	 */
	Work createWork(Work work);
	
	/**
	 * ���� work
	 * 
	 * @param clerkid
	 * @param tasksn
	 * @param type
	 * @return
	 */
	Work createWork(Long clerkId,Long taskSn, int type);
	/**
	 * ���� work
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param type
	 * @param matterId
	 * @return
	 */
	Work createWork(Long clerkId, Long taskSn, int type, Long matterId);
	
	/**
	 * ���� work
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param type
	 * @param matterId
	 * @return
	 */
	Work createWork(Long clerkId, Long taskSn, int type, Long[] matterIds);
	
	/**
	 * ����Work��
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param type
	 * @param matterIds
	 * @param sn
	 * @return
	 */
	Work createWork(Long clerkId, Long taskSn, int type, Long[] matterIds, Long sn);
	
	/**
	 * ����Work��
	 * 
	 * @param clerk Work ������
	 * @param object ��ص�ҵ����󣬿�Ϊ null
	 * @param taskSn ��ص� task �� sn
	 * @param type Work ������
	 * @param matterIds 
	 * @param sn Work �� sn����Ϊ��
	 * @param beginTime 
	 * @param endTIme 
	 * @return
	 */
	Work createWork(Clerk clerk, RFSEntityObject object, Long taskSn, int type, Long[] matterIds, Long sn, Date beginTime, Date endTime);
	/**
	 * ��ͣ��
	 * �޸�hangTime��hangUsed����
	 * 
	 * @param workSn
	 */
	void hangWork(Work work, Date hangTime);
	
	void hangWork(Long workSn);
	/**
	 * ���죬������
	 * �޸�wakeTime��timeHang����
	 * 
	 * @param workSn
	 */
	void wakeWork (Work work, Date wakeTime);
	/**
	 * ���죬������
	 * @param workSn
	 */
	void wakeWork(Long workSn);
	/**
	 * ��������
	 * 
	 * @param workSn
	 * @param result
	 * @param endTime 
	 */
	void terminateWork (Work work, Byte result, Date endTime);
	/**
	 * ����
	 * @param workSn
	 * @param result
	 */
	void terminateWork(Long workSn, Byte result);
	/**
	 * �޸�Work
	 * 
	 * @param work
	 * @return
	 */
	Work updateWork(Work work);
	
	/**
	 * ɾ��Work
	 * 
	 * @param work
	 * @return int,Ϊ1ʱɾ���ɹ�
	 */
	int deleteWork(Work work);
	
	/**
	 * ��ȡָ��Work��
	 * @param sn
	 * @return
	 */
	Work getWork(Long sn);
	/**
	 * ������Work��ID��
	 * @return
	 */
	Long generateId();
	/**
	 * ����Work��ʱ�ޡ�
	 * 
	 * @param work
	 * @param matter
	 * @return
	 */
	boolean setTimeLimits(Work work, Long matter);
	/**
	 * ����Work������ͼ��
	 * 
	 * @param workSn
	 * @param workType
	 * @param matterIds
	 * @return
	 */
	Long createWorkTrack(Long workSn, int workType,Long ...matterIds);
	
	/**
	 * ����TaskSN��ѯWork, ������ʱ�䵹��
	 * 
	 * @param taskSN
	 * @return List<Work>
	 */
	List<Work> findWorkByTaskSN(Long taskSN);
	
	/**
	 * ��ѯ������������ Work �б�
	 * 
	 * @param filter
	 * @return
	 */
	List<Work> findWorkWithAttachmentCount(ResultFilter filter);
	
	/**
	 * ������ѯWork���ϡ�
	 * 
	 * @param object ������
	 * @param taskType Task���ͣ���ѯָ��task���͵�work��null��ʾ���Ը�������
	 * @param workType Work���ͣ���ѯָ��work���͵�work��null��ʾ���Ը�������
	 * @param status ��ѯָ��״̬��work��null��ʾ���Ը�������
	 * @return ����������work���ϣ�������ʱ�䵹��
	 */
	List<Work> findWorks(RFSObjectable object, Integer taskType, Integer workType, Byte status);

	/**
	 * ������ѯWork���ϡ�
	 * 
	 * @param object ҵ�����
	 * @param taskType Task���ͣ���ѯָ��task���͵�work��null��ʾ���Ը�������
	 * @param workType Work���ͣ���ѯָ��work���͵�work��null��ʾ���Ը�������
	 * @param status ��ѯָ��״̬��work��null��ʾ���Ը�������
	 * @return ����������work���ϣ�������ʱ�䵹��
	 */
	List<Work> findWorks(RFSItemable object, Integer taskType, Integer workType, Byte status);
	
	/**
	 * ����Work������ҵ������ϵ��
	 * ������RFSEntityObjectToWork�У����ⲿ͸����
	 * 
	 * @param work
	 * @param objects
	 */
	Work updateWorkEntityObjects(Work work, RFSEntityObject... objects);

	/**
	 * ȡ��
	 * @param work
	 */
	void cancelWork(Work work);

	/**
	 * ���
	 * @param work
	 */
	void avoidWork(Work work);

	/**
	 * ����
	 * @param work
	 */
	void rejectWork(Work work);

	/**
	 * ��ֹ
	 * @param work
	 */
	void stopWork(Work work);

	/**
	 * ת����ת��
	 * @param work
	 */
	void transferWork(Work work);

	/**
	 * ����
	 * @param work
	 */
	void withdrawWork(Work work);

	/**
	 * ����
	 * @param work
	 */
	void undoWork(Work work);
	
	void workInvalid(RFSObject rfsObject);
}
