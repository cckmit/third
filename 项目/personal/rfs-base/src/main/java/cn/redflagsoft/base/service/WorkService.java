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
	 * 创建 Work
	 * 
	 * @param work
	 * @return Work
	 */
	Work createWork(Work work);
	
	/**
	 * 创建 work
	 * 
	 * @param clerkid
	 * @param tasksn
	 * @param type
	 * @return
	 */
	Work createWork(Long clerkId,Long taskSn, int type);
	/**
	 * 创建 work
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param type
	 * @param matterId
	 * @return
	 */
	Work createWork(Long clerkId, Long taskSn, int type, Long matterId);
	
	/**
	 * 创建 work
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param type
	 * @param matterId
	 * @return
	 */
	Work createWork(Long clerkId, Long taskSn, int type, Long[] matterIds);
	
	/**
	 * 创建Work。
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
	 * 创建Work。
	 * 
	 * @param clerk Work 操作者
	 * @param object 相关的业务对象，可为 null
	 * @param taskSn 相关的 task 的 sn
	 * @param type Work 的类型
	 * @param matterIds 
	 * @param sn Work 的 sn，可为空
	 * @param beginTime 
	 * @param endTIme 
	 * @return
	 */
	Work createWork(Clerk clerk, RFSEntityObject object, Long taskSn, int type, Long[] matterIds, Long sn, Date beginTime, Date endTime);
	/**
	 * 暂停。
	 * 修改hangTime和hangUsed属性
	 * 
	 * @param workSn
	 */
	void hangWork(Work work, Date hangTime);
	
	void hangWork(Long workSn);
	/**
	 * 续办，重启。
	 * 修改wakeTime和timeHang属性
	 * 
	 * @param workSn
	 */
	void wakeWork (Work work, Date wakeTime);
	/**
	 * 续办，重启。
	 * @param workSn
	 */
	void wakeWork(Long workSn);
	/**
	 * 结束工作
	 * 
	 * @param workSn
	 * @param result
	 * @param endTime 
	 */
	void terminateWork (Work work, Byte result, Date endTime);
	/**
	 * 结束
	 * @param workSn
	 * @param result
	 */
	void terminateWork(Long workSn, Byte result);
	/**
	 * 修改Work
	 * 
	 * @param work
	 * @return
	 */
	Work updateWork(Work work);
	
	/**
	 * 删除Work
	 * 
	 * @param work
	 * @return int,为1时删除成功
	 */
	int deleteWork(Work work);
	
	/**
	 * 获取指定Work。
	 * @param sn
	 * @return
	 */
	Work getWork(Long sn);
	/**
	 * 生成新Work的ID。
	 * @return
	 */
	Long generateId();
	/**
	 * 重设Work的时限。
	 * 
	 * @param work
	 * @param matter
	 * @return
	 */
	boolean setTimeLimits(Work work, Long matter);
	/**
	 * 创建Work的流程图。
	 * 
	 * @param workSn
	 * @param workType
	 * @param matterIds
	 * @return
	 */
	Long createWorkTrack(Long workSn, int workType,Long ...matterIds);
	
	/**
	 * 根据TaskSN查询Work, 按创建时间倒序。
	 * 
	 * @param taskSN
	 * @return List<Work>
	 */
	List<Work> findWorkByTaskSN(Long taskSN);
	
	/**
	 * 查询带附件数量的 Work 列表。
	 * 
	 * @param filter
	 * @return
	 */
	List<Work> findWorkWithAttachmentCount(ResultFilter filter);
	
	/**
	 * 条件查询Work集合。
	 * 
	 * @param object 主对象。
	 * @param taskType Task类型，查询指定task类型的work，null表示忽略该条件。
	 * @param workType Work类型，查询指定work类型的work，null表示忽略该条件。
	 * @param status 查询指定状态的work，null表示忽略该条件。
	 * @return 符合条件的work集合，按创建时间倒序
	 */
	List<Work> findWorks(RFSObjectable object, Integer taskType, Integer workType, Byte status);

	/**
	 * 条件查询Work集合。
	 * 
	 * @param object 业务对象。
	 * @param taskType Task类型，查询指定task类型的work，null表示忽略该条件。
	 * @param workType Work类型，查询指定work类型的work，null表示忽略该条件。
	 * @param status 查询指定状态的work，null表示忽略该条件。
	 * @return 符合条件的work集合，按创建时间倒序
	 */
	List<Work> findWorks(RFSItemable object, Integer taskType, Integer workType, Byte status);
	
	/**
	 * 更新Work关联的业务对象关系。
	 * 保存在RFSEntityObjectToWork中，对外部透明。
	 * 
	 * @param work
	 * @param objects
	 */
	Work updateWorkEntityObjects(Work work, RFSEntityObject... objects);

	/**
	 * 取消
	 * @param work
	 */
	void cancelWork(Work work);

	/**
	 * 免办
	 * @param work
	 */
	void avoidWork(Work work);

	/**
	 * 驳回
	 * @param work
	 */
	void rejectWork(Work work);

	/**
	 * 中止
	 * @param work
	 */
	void stopWork(Work work);

	/**
	 * 转交、转出
	 * @param work
	 */
	void transferWork(Work work);

	/**
	 * 撤回
	 * @param work
	 */
	void withdrawWork(Work work);

	/**
	 * 撤销
	 * @param work
	 */
	void undoWork(Work work);
	
	void workInvalid(RFSObject rfsObject);
}
