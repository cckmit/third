/*
 * $Id: DatumService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;

public interface DatumService {

	/**
	 * ����ҵ����������ϡ�
	 * @param category MatterDatum��category
	 * @param task
	 * @param work
	 * @param processType
	 * @param matters
	 * @param datumList
	 * @param clerk ������
	 * @param object ҵ��������
	 * @param objects ��ص�ҵ���������ݶ���
	 * @return
	 */
	List<Datum> processDatum(byte category, Task task, Work work,
			int processType, MatterVO matters,
			DatumVOList datumList, Clerk clerk, RFSObject object, RFSEntityObject... objects);
	
	/**
	 * ����ҵ���������ѯ�����б�
	 * 
	 * @param objectId
	 * @return
	 */
	List<Datum> findDatum(Long objectId);

	/**
	 * ��ѯָ������������б�
	 * @param object ʵ����
	 * @return ���ϼ���
	 */
	List<Datum> findDatum(RFSEntityObject object);
	
	/**
	 * ��ѯָ��Task�������б�
	 * 
	 * @param taskSN
	 * @return ���ϼ���
	 */
	List<Datum> findDatumByTaskSN(long taskSN);
	
	/**
	 * ��ѯָ��Work�������б�
	 * @param workSN
	 * @return ���ϼ���
	 */
	List<Datum> findDatumByWorkSN(long workSN);
	
	/**
	 * ��ѯָ��������ָ�����Task�������ϼ��ϡ�
	 * @param object ʵ�����
	 * @param taskType task����
	 * @return
	 */
	List<Datum> findTaskDatum(RFSEntityObject object, int taskType);
	
	/**
	 * ��ѯָ��������ָ�����ڣ�work�������ϼ��ϡ�
	 * @param object ʵ�����
	 * @param workType work����
	 * @return
	 */
	List<Datum> findWorkDatum(RFSEntityObject object, int workType);
	
	/**
	 * �������ϡ�
	 * 
	 * @param datum
	 * @return
	 */
	Datum saveDatum(Datum datum);
	
	/**
	 * ��������ID��ѯ���ϡ�
	 * @param id
	 * @return
	 */
	Datum getDatum(Long id);
	
	/**
	 * ɾ��Datum��������
	 * 
	 * @param datum
	 * @return
	 */
	int deleteDatum(Datum datum);
	
//	/**
//	 * ��ȡ����ָ�����͵ĵ�ǰ���ϡ�
//	 * 
//	 * @param objectId ������ID
//	 * @param categoryId ��������
//	 * @return
//	 */
//	Datum getCurrentDatum(Long objectId, Long categoryId);
//	
//	/**
//	 * ��ȡ����ָ�����͵ĵ�ǰ���ϣ�����ѯ���ϴ����ļ���
//	 * @param objectId ������ID
//	 * @param categoryId ��������
//	 * @return
//	 */
//	DatumAttachment getCurrentDatumAttachment(Long objectId, Long categoryId);
	
	/**
	 * ��ָ����¼����Ϊ��ʷ���ϡ�
	 * @param datum
	 * @return
	 */
	Datum updateToHistory(Datum datum);	

	
	/**
	 * �������ϡ�
	 * 
	 * @param datum ����
	 * @param object ��ƵĶ���
	 * @param task �漰��task
	 * @param work ��Ƶ�work
	 * @return
	 */
	Datum saveDatum(Datum datum, RFSEntityObject object, Task task, Work work);
}
