/*
 * $Id: TaskBizInfoServiceImpl.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.NonUniqueResultException;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskBizInfo;
import cn.redflagsoft.base.dao.TaskBizInfoDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.TaskBizInfoService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskBizInfoServiceImpl implements TaskBizInfoService {
	private TaskBizInfoDao taskBizInfoDao;
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#createTaskBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo createTaskBizInfo(Task task, TaskBizInfo taskBizInfo) {
		taskBizInfo.setTaskSN(task.getSn());
		taskBizInfo.setTaskName(task.getName());
		
		return taskBizInfoDao.save(taskBizInfo);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#getTaskBizInfo(java.lang.Long)
	 */
	public TaskBizInfo getTaskBizInfo(Long id) {
		return taskBizInfoDao.get(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#getTaskBizInfoByTaskSN(java.lang.Long)
	 */
	public TaskBizInfo getTaskBizInfoByTaskSN(Long taskSN) throws NonUniqueResultException {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("taskSN", taskSN));
		List<TaskBizInfo> list = taskBizInfoDao.find(filter);
		if(list.isEmpty()){
			return null;
		}
		if(list.size() == 1){
			return list.get(0);
		}
		throw new NonUniqueResultException();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#update(cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo update(TaskBizInfo taskBizInfo) {
		return taskBizInfoDao.update(taskBizInfo);
	}

	/**
	 * @param taskBizInfoDao the taskBizInfoDao to set
	 */
	public void setTaskBizInfoDao(TaskBizInfoDao taskBizInfoDao) {
		this.taskBizInfoDao = taskBizInfoDao;
	}

	/**
	 * @return the taskBizInfoDao
	 */
	public TaskBizInfoDao getTaskBizInfoDao() {
		return taskBizInfoDao;
	}

	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#saveAcptBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo saveAcptBizInfo(Task task, TaskBizInfo taskBizInfo) {
		Clerk clerk = UserClerkHolder.getNullableClerk();
		TaskBizInfo info = getTaskBizInfoByTaskSN(task.getSn());
		boolean isNew = (info == null);
		if(isNew) {
			info = new TaskBizInfo();
		}
		
//		BeanUtils.copyProperties(info, taskBizInfo, "acptApplyClerkId", "acptApplyClerkName", "")
		
		info.setAcptApplyClerkId(taskBizInfo.getAcptApplyClerkId());
		info.setAcptApplyClerkName(taskBizInfo.getAcptApplyClerkName());
		info.setAcptApplyOrgId(taskBizInfo.getAcptApplyOrgId());
		info.setAcptApplyOrgName(taskBizInfo.getAcptApplyOrgName());
		info.setAcptAttacchmentCount(taskBizInfo.getAcptAttacchmentCount());
		info.setAcptDocCount(taskBizInfo.getAcptDocCount());
		if(clerk != null){
			info.setAcptOperateClerkId(clerk.getId());
			info.setAcptOperateClerkName(clerk.getName());
		}
		info.setAcptOperateTime(new Date());
		info.setAcptRemark(taskBizInfo.getAcptRemark());
		info.setAcptSN(taskBizInfo.getAcptSN());
		info.setAcptTime(taskBizInfo.getAcptTime() == null ? new Date() : taskBizInfo.getAcptTime());
		info.setAcptWorkItemName(taskBizInfo.getAcptWorkItemName());
		
		return isNew ? createTaskBizInfo(task, info) : update(info);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#saveAcvBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo saveAcvBizInfo(Task task, TaskBizInfo taskBizInfo) {
		Clerk clerk = UserClerkHolder.getNullableClerk();
		TaskBizInfo info = getTaskBizInfoByTaskSN(task.getSn());
		boolean isNew = (info == null);
		if(isNew) {
			info = new TaskBizInfo();
		}
		
		info.setAcvAttacchmentCount(taskBizInfo.getAcvAttacchmentCount());
		info.setAcvClerkId(taskBizInfo.getAcvClerkId());
		info.setAcvClerkName(taskBizInfo.getAcvClerkName());
		info.setAcvDocCount(taskBizInfo.getAcvDocCount());
		info.setAcvDocFileNo(taskBizInfo.getAcvDocFileNo());
		info.setAcvDocPublishOrgId(taskBizInfo.getAcvDocPublishOrgId());
		info.setAcvDocPublishOrgName(taskBizInfo.getAcvDocPublishOrgName());
		info.setAcvDocPublishTime(taskBizInfo.getAcvDocPublishTime());
		if(clerk != null){
			info.setAcvOperateClerkId(clerk.getId());
			info.setAcvOperateClerkName(clerk.getName());
		}
		info.setAcvOperateTime(new Date());
		info.setAcvRemark(taskBizInfo.getAcvRemark());
		info.setAcvWorkItemName(taskBizInfo.getAcvWorkItemName());
		
		return isNew ? createTaskBizInfo(task, info) : update(info);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#saveDaBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo saveDaBizInfo(Task task, TaskBizInfo taskBizInfo) {
		Clerk clerk = UserClerkHolder.getNullableClerk();
		TaskBizInfo info = getTaskBizInfoByTaskSN(task.getSn());
		boolean isNew = (info == null);
		if(isNew) {
			info = new TaskBizInfo();
		}
		
		info.setDaAttacchmentCount(taskBizInfo.getDaAttacchmentCount());
		info.setDaAuditTime(taskBizInfo.getDaAuditTime());
		info.setDaDocCount(taskBizInfo.getDaDocCount());
		info.setDaFileNo(taskBizInfo.getDaFileNo());
		if(clerk != null){
			info.setDaOperateClerkId(clerk.getId());
			info.setDaOperateClerkName(clerk.getName());
		}
		info.setDaOperateTime(new Date());
		info.setDaRemark(taskBizInfo.getDaRemark());
		info.setDaWorkItemName(taskBizInfo.getDaWorkItemName());
		
		return isNew ? createTaskBizInfo(task, info) : update(info);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#saveDrBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo saveDrBizInfo(Task task, TaskBizInfo taskBizInfo) {
		Clerk clerk = UserClerkHolder.getNullableClerk();
		TaskBizInfo info = getTaskBizInfoByTaskSN(task.getSn());
		boolean isNew = (info == null);
		if(isNew) {
			info = new TaskBizInfo();
		}
		
		info.setDrAttacchmentCount(taskBizInfo.getDrAttacchmentCount());
		info.setDrClerkId(taskBizInfo.getDrClerkId());
		info.setDrClerkName(taskBizInfo.getDrClerkName());
		info.setDrDocCount(taskBizInfo.getDrDocCount());
		info.setDrDocReceiveOrgId(taskBizInfo.getDrDocReceiveOrgId());
		info.setDrDocReceiveOrgName(taskBizInfo.getDrDocReceiveOrgName());
		if(clerk != null){
			info.setDrOperateClerkId(clerk.getId());
			info.setDrOperateClerkName(clerk.getName());
		}
		info.setDrOperateTime(new Date());
		info.setDrRemark(taskBizInfo.getDrRemark());
		info.setDrWorkItemName(taskBizInfo.getDrWorkItemName());
		
		//决定文号
		if(taskBizInfo.getDaFileNo() != null){
			info.setDaFileNo(taskBizInfo.getDaFileNo());
		}
		//受理编号
		if(taskBizInfo.getAcptSN() != null){
			info.setAcptSN(taskBizInfo.getAcptSN());
		}
		
		return isNew ? createTaskBizInfo(task, info) : update(info);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TaskBizInfoService#saveRcdBizInfo(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.TaskBizInfo)
	 */
	public TaskBizInfo saveRcdBizInfo(Task task, TaskBizInfo taskBizInfo) {
		Clerk clerk = UserClerkHolder.getNullableClerk();
		TaskBizInfo info = getTaskBizInfoByTaskSN(task.getSn());
		boolean isNew = (info == null);
		if(isNew) {
			info = new TaskBizInfo();
		}
		
		info.setRcdAcceptClerkId(taskBizInfo.getRcdAcceptClerkId());
		info.setRcdAcceptOrgId(taskBizInfo.getRcdAcceptOrgId());
		info.setRcdAcceptOrgName(taskBizInfo.getRcdAcceptOrgName());
		info.setRcdAcceptTime(taskBizInfo.getRcdAcceptTime());
		info.setRcdApplyClerkId(taskBizInfo.getRcdApplyClerkId());
		info.setRcdApplyClerkName(taskBizInfo.getRcdApplyClerkName());
		info.setRcdAttacchmentCount(taskBizInfo.getRcdAttacchmentCount());
		info.setRcdDocCount(taskBizInfo.getRcdDocCount());
		if(clerk != null){
			info.setRcdOperateClerkId(clerk.getId());
			info.setRcdOperateClerkName(clerk.getName());
		}
		info.setRcdOperateTime(new Date());
		info.setRcdReceiptFileNo(taskBizInfo.getRcdReceiptFileNo());
		info.setRcdRemark(taskBizInfo.getRcdRemark());
		info.setRcdWorkItemName(taskBizInfo.getRcdWorkItemName());
		
		
		
		return isNew ? createTaskBizInfo(task, info) : update(info);
	}

}
