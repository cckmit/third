/*
 * $Id: BizLogServiceImpl.java 5734 2012-05-18 12:36:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bizlog.impl;

import java.util.Date;

import org.springframework.util.Assert;

import cn.redflagsoft.base.aop.Callback;
import cn.redflagsoft.base.aop.annotation.BizLog;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bizlog.BizLogContext;
import cn.redflagsoft.base.bizlog.BizLogService;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.WorkService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BizLogServiceImpl implements BizLogService {
	private TaskService taskService;
	private WorkService workService;
	private ClerkService clerkService;
	
	/**
	 * @return the clerkService
	 */
	public ClerkService getClerkService() {
		return clerkService;
	}


	/**
	 * @param clerkService the clerkService to set
	 */
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}


	/**
	 * @return the taskService
	 */
	public TaskService getTaskService() {
		return taskService;
	}


	/**
	 * @param taskService the taskService to set
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	/**
	 * @return the workService
	 */
	public WorkService getWorkService() {
		return workService;
	}


	/**
	 * @param workService the workService to set
	 */
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}


	public Object processBizLog(MethodCall call, BizLog bizLog, Callback callback) throws Throwable {
		Long taskSN = taskService.generateId();
		Task oTask = new Task();
		oTask.setSn(taskSN);
		oTask.setType(bizLog.taskType());
		
		Work oWork = new Work();
		oWork.setTaskSN(taskSN);
		oWork.setType(bizLog.workType());
		
		BizLogContext.setTask(oTask);
		BizLogContext.setWork(oWork);
		
		Object result = callback.doInCallback();
		
		RFSEntityObject entityObject = null;
		if(bizLog.objectIndex() == -1){
			entityObject = (RFSEntityObject) result;
		}else if(bizLog.objectIndex() >= 0){
			entityObject = (RFSEntityObject) call.getParameterValues()[bizLog.objectIndex()];
		}else{
			throw new IllegalArgumentException("找不到EntityObject，无法写日志。");
		}
		
		Clerk clerk = UserClerkHolder.getNullableClerk();
		if(clerk == null && bizLog.userId() > 0){
			clerk = clerkService.getClerk(bizLog.userId());
		}
		Assert.notNull(clerk, "找不到登录用户");
		
		Work work2 = BizLogContext.getWork();
		Task task2 = BizLogContext.getTask();
		Date workBeginTime = null;
		Date workEndTime = null;
		if(work2 != null){
			workBeginTime = work2.getBeginTime();
			workEndTime = work2.getEndTime();
		}
		Long workSN = workService.generateId();
		Work work = workService.createWork(clerk, entityObject, taskSN, bizLog.workType(), null, workSN, workBeginTime, workEndTime);
		
		Task task = task2;
		if(task == null){
			task = new Task();
		}
		task.setSn(taskSN);
		task.setType(bizLog.taskType());
		task.setActiveWorkSN(work.getSn());
		task.setClerkID(clerk.getId());			//当前办理人
		task.setClerkName(clerk.getName());
		//@since 2.0.2
		task.setBeginTime(work.getBeginTime());
		//task = taskService.createTask(task, mids, objectId, (short) 1, entityID);
		//FIXME: 责任人暂时没有，责任人可能应该从配置中读取
		Long dutyerID = null;
		task = taskService.createTask(task, null, entityObject, (short) 1, dutyerID);
		
		//结束
		workService.terminateWork(work, (byte) 0, workEndTime);
		taskService.terminateTask(task, workEndTime);
		
		return result;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bizlog.BizLogService#saveBizLog(cn.redflagsoft.base.bean.RFSEntityObject, int, int, cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work)
	 */
	public void saveBizLog(RFSEntityObject object, int taskType, int workType,
			Task defaultTask, Work defaultWork, Long userId) {
		RFSEntityObject entityObject = object;
		if(object == null){
			throw new IllegalArgumentException("EntityObject为空，无法写日志。");
		}
		
		Long taskSN = taskService.generateId();
		Work work2 = defaultWork;
		Task task2 = defaultTask;
		
		//Clerk clerk = UserClerkHolder.getClerk();
		Clerk clerk = UserClerkHolder.getNullableClerk();
		if(clerk == null && userId != null && userId.longValue() > 0){
			clerk = clerkService.getClerk(userId);
		}
		Assert.notNull(clerk, "找不到登录用户");
		
		
		Date workBeginTime = null;
		Date workEndTime = null;
		if(work2 != null){
			workBeginTime = work2.getBeginTime();
			workEndTime = work2.getEndTime();
		}
		Long workSN = workService.generateId();
		Work work = workService.createWork(clerk, entityObject, taskSN, workType, null, workSN, workBeginTime, workEndTime);
		
		Task task = task2;
		if(task == null){
			task = new Task();
		}
		task.setSn(taskSN);
		task.setType(taskType);
		task.setActiveWorkSN(work.getSn());
		task.setClerkID(clerk.getId());			//当前办理人
		task.setClerkName(clerk.getName());
		//@since 2.0.2
		task.setBeginTime(work.getBeginTime());
		//task = taskService.createTask(task, mids, objectId, (short) 1, entityID);
		//FIXME: 责任人暂时没有，责任人可能应该从配置中读取
		Long dutyerID = null;
		task = taskService.createTask(task, null, entityObject, (short) 1, dutyerID);
		
		//结束
		workService.terminateWork(work, (byte) 0, workEndTime);
		taskService.terminateTask(task, workEndTime);
	}
}
