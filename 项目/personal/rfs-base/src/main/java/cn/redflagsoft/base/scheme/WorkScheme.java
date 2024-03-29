/*
 * $Id: WorkScheme.java 6364 2014-04-04 02:39:33Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 */
package cn.redflagsoft.base.scheme;


import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;
/**
 * 
 * @author Alex Lin
 *
 */
public interface WorkScheme extends Scheme, ParametersAware, SchemeBeanInfo/*, EventDispatcher<WorkSchemeEvent>*/ {
	
//	void setWorkType(Integer workType);
//	Short getWorkType();
//	
//	void setTaskType(Integer taskType);
//	Short getTaskType();
	
	//Object doScheme() throws SchemeException;
	
	/**
	 * 通过WorkScheme调用WorkProcess。
	 */
	Object process() throws SchemeException;
	

	Work getWork();
	void setWork(Work work);
	
	Task getTask();
	void setTask(Task task);
	
	/**
	 * 获得业务对象。
	 * 
	 * 此业务对象可能是从前台传来的ID查询出来的，也可能是后台创建的。
	 * 
	 * @return
	 */
	RFSObject getObject();
	/**
	 * 设置业务对象。
	 * 
	 * 此方法不与 {@link #getObject()} 进行配对，避免被当着一般的 属性处理。
	 * 该方法只在特殊情况下调用。
	 * @param object
	 */
	void setRFSObject(RFSObject object);
	
	
	Integer getWorkType();
	Integer getTaskType();
	List<Long> getMatterIds();
	
	/**
	 * 资料列表。
	 * @return
	 */
	DatumVOList getDatumVOList();
	MatterVO getMatterVO();
	
	/**
	 * 附件集合。
	 * @return
	 */
	List<Long> getFileIds();
	
	/**
	 * 返回整个WorkScheme执行过程中相关的业务对象，可能没有，可能一个，也可能有多个，
	 * 但通常不应该重复。
	 * 该集合是 {@link #getRelatedObjects()} 的一个子集。
	 * 
	 * @return
	 * @since 2.0.2
	 */
	List<RFSItemable> getRelatedItems();
	
	/**
	 * 返回整个WorkScheme执行过程相关的实体对象，可能没有，可能一个，也可能多个，但通常不应该重复。
	 * 该返回集合包含 {@link #getRelatedItems()} 中的所有元素。
	 * @return
	 */
	List<RFSEntityObject> getRelatedObjects();
	
//	List<Long> getRelatedTaskSNs();
	
	Date getWorkBeginTime();
	
	Date getWorkEndTime();
	
	Date getWorkHangTime();
	
	Date getWorkWakeTime();
	
	SchemeTaskManager getSchemeTaskManager();
	
	MattersHandler getMattersHandler();
	
	/**
	 * 获取已经处理过得MatterAffair主键集合。
	 * @return
	 */
	List<Long> getProcessedMatterAffairs();
}
