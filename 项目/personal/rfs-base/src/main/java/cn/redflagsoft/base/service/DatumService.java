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
	 * 处理业务操作的资料。
	 * @param category MatterDatum的category
	 * @param task
	 * @param work
	 * @param processType
	 * @param matters
	 * @param datumList
	 * @param clerk 操作者
	 * @param object 业务主对象
	 * @param objects 相关的业务事项数据对象
	 * @return
	 */
	List<Datum> processDatum(byte category, Task task, Work work,
			int processType, MatterVO matters,
			DatumVOList datumList, Clerk clerk, RFSObject object, RFSEntityObject... objects);
	
	/**
	 * 根据业务主对象查询资料列表。
	 * 
	 * @param objectId
	 * @return
	 */
	List<Datum> findDatum(Long objectId);

	/**
	 * 查询指定对象的资料列表。
	 * @param object 实体类
	 * @return 资料集合
	 */
	List<Datum> findDatum(RFSEntityObject object);
	
	/**
	 * 查询指定Task的资料列表。
	 * 
	 * @param taskSN
	 * @return 资料集合
	 */
	List<Datum> findDatumByTaskSN(long taskSN);
	
	/**
	 * 查询指定Work的资料列表。
	 * @param workSN
	 * @return 资料集合
	 */
	List<Datum> findDatumByWorkSN(long workSN);
	
	/**
	 * 查询指定对象在指定事项（Task）的资料集合。
	 * @param object 实体对象
	 * @param taskType task类型
	 * @return
	 */
	List<Datum> findTaskDatum(RFSEntityObject object, int taskType);
	
	/**
	 * 查询指定对象在指定环节（work）的资料集合。
	 * @param object 实体对象
	 * @param workType work类型
	 * @return
	 */
	List<Datum> findWorkDatum(RFSEntityObject object, int workType);
	
	/**
	 * 保存资料。
	 * 
	 * @param datum
	 * @return
	 */
	Datum saveDatum(Datum datum);
	
	/**
	 * 根据资料ID查询资料。
	 * @param id
	 * @return
	 */
	Datum getDatum(Long id);
	
	/**
	 * 删除Datum及附件。
	 * 
	 * @param datum
	 * @return
	 */
	int deleteDatum(Datum datum);
	
//	/**
//	 * 获取对象指定类型的当前资料。
//	 * 
//	 * @param objectId 主对象ID
//	 * @param categoryId 资料类型
//	 * @return
//	 */
//	Datum getCurrentDatum(Long objectId, Long categoryId);
//	
//	/**
//	 * 获取对象指定类型的当前资料，并查询已上传的文件。
//	 * @param objectId 主对象ID
//	 * @param categoryId 资料类型
//	 * @return
//	 */
//	DatumAttachment getCurrentDatumAttachment(Long objectId, Long categoryId);
	
	/**
	 * 将指定记录更新为历史资料。
	 * @param datum
	 * @return
	 */
	Datum updateToHistory(Datum datum);	

	
	/**
	 * 保存资料。
	 * 
	 * @param datum 资料
	 * @param object 设计的对象
	 * @param task 涉及的task
	 * @param work 设计的work
	 * @return
	 */
	Datum saveDatum(Datum datum, RFSEntityObject object, Task task, Work work);
}
