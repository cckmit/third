/*
 * $Id: BaseWorkScheme.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskBizInfo;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkItem;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.service.TaskBizInfoService;
import cn.redflagsoft.base.vo.DatumVOList;

/**
 * 基础 WorkScheme ，包含各种通常的业务处理功能。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class BaseWorkScheme extends AbstractWorkScheme {
	private static final Log log = LogFactory.getLog(BaseWorkScheme.class);
	private TaskBizInfoService taskBizInfoService;
	private SchemeManager schemeManager;

	private WorkItem workItem;
	private TaskBizInfo bizInfo;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(taskBizInfoService, "必须设置 taskBizInfoService");
		Assert.notNull(schemeManager, "必须设置schemeManager");
	}

	
	private void processWorkItem(String workItemName){
		Work work = getWork();
		if(work != null){
			if(workItemName != null){
				work.setWorkItemName(workItemName);
				getWorkService().updateWork(work);
			}			
		}
	}
	
	
	/**
	 * 工作分派。
	 * 
	 * @return 操作结果
	 */
	public Object doDispatch(){
		Assert.notNull(workItem, "工作分派对象不能为空。");
		Assert.notNull(workItem.getTimeLimit(), "工作时限不能为空。");
		
		Task task = getTask();
		task.setDutyerID(workItem.getDutyerId());
		task.setDutyerName(workItem.getDutyerIdLabel());
		task.setTimeLimit(workItem.getTimeLimit());
//		task.setTimeUnit(TimeUnit.DAYS);
		task.setNote(workItem.getRemark());
		task.setClerkID(workItem.getDutyerId());
		task.setClerkName(workItem.getDutyerIdLabel());
		
		if(workItem.getBeginTime() != null){
			task.setBeginTime(workItem.getBeginTime());
		}
		if(workItem.getEndTime() != null){
			task.setEndTime(workItem.getEndTime());
		}
		
		getTaskService().updateTask(task);
		processWorkItem(workItem.getWorkItemName());
		
		return "工作分派成功";
	}


	private void setAttachmentsAndDocsCount(TaskBizInfo bizInfo){
		DatumVOList list = getDatumVOList();
		List<Long> ids = getFileIds();
//		bizInfo.setAcptWorkItemName(workItem.getWorkItemName());
		bizInfo.setAcptAttacchmentCount(ids != null ? ids.size() : 0);
		bizInfo.setAcptDocCount(list != null ? list.size() : 0);
	}
	
	/**
	 * 工作受理。
	 * WorkType=13
	 */
	public Object doAccept(){
		//Assert.notNull(workItem, "工作受理对象不能为空。");
		Assert.notNull(bizInfo, "工作受理对象不能为空。");

		Task task = getTask();
		if(workItem != null){
			if(task.getClerkID() == null || !task.getClerkID().equals(workItem.getDutyerId())){
				task.setClerkID(workItem.getDutyerId());
				task.setClerkName(workItem.getDutyerIdLabel());
			}
			
			if(workItem.getBeginTime() != null){
				task.setBeginTime(workItem.getBeginTime());
			}
		}
		
		String s = String.format("该事项于%s由%s受理。", AppsGlobals.formatDate(new Date()), getClerk().getName());
		log.debug(s);
		task.setNote(s);
		
		getTaskService().updateTask(task);
		processWorkItem(bizInfo.getAcptWorkItemName());

		setAttachmentsAndDocsCount(bizInfo);
		System.err.println("-------------------------------" + taskBizInfoService);
		taskBizInfoService.saveAcptBizInfo(task, bizInfo);

		return "工作受理成功";
	}

	
	/**
	 * 审批决定登记。
	 * WorkType=14.
	 * @return 操作结果
	 */
	public Object doDecisionApply(){
		Assert.notNull(bizInfo, "业务对象不能为空。");
		
		processWorkItem(bizInfo.getDaWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveDaBizInfo(task, bizInfo);

		return "审批决定登记完成。";
	}
	
	/**
	 * 审批决定发文。
	 * WorkType=15
	 * @return 操作结果
	 */
	public Object doDecisionResult(){
		Assert.notNull(bizInfo, "业务对象不能为空。");
		
		processWorkItem(bizInfo.getDrWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveDrBizInfo(task, bizInfo);
		
		return "审批决定发文完成。";
	}
	
	
	/**
	 * 报审受理备案。
	 * WorkType=16
	 * @return 操作结果
	 */
	public Object doRecord(){
		Assert.notNull(bizInfo, "业务对象不能为空。");
		
		processWorkItem(bizInfo.getRcdWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveRcdBizInfo(task, bizInfo);
		return "报审受理备案完成。";
	}
	
	/**
	 * 工程资料存档。
	 * WorkType=17
	 * @return 操作结果
	 */
	public Object doArchive(){
		Assert.notNull(bizInfo, "业务对象不能为空。");
		
		processWorkItem(bizInfo.getAcvWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveAcvBizInfo(task, bizInfo);
		
		return "工程资料存档完成。";
	}

	
	/**
	 * 查询业务对象。
	 * @return
	 */
	public TaskBizInfo viewLoadBizInfo(){
		if(bizInfo != null && bizInfo.getTaskSN() != null){
			TaskBizInfo info = taskBizInfoService.getTaskBizInfoByTaskSN(bizInfo.getTaskSN());
			if(info == null){
				info = new TaskBizInfo();
				info.setTaskSN(bizInfo.getTaskSN());
			}
			return info;
		}else{
			throw new IllegalArgumentException("加载业务信息的参数不正确。");
		}
	}
	
	
	public WorkItem getWorkItem() {
		return workItem;
	}

	public void setWorkItem(WorkItem workItem) {
		this.workItem = workItem;
	}


	public TaskBizInfoService getTaskBizInfoService() {
		return taskBizInfoService;
	}


	public void setTaskBizInfoService(TaskBizInfoService taskBizInfoService) {
		this.taskBizInfoService = taskBizInfoService;
	}


	public TaskBizInfo getBizInfo() {
		return bizInfo;
	}


	public void setBizInfo(TaskBizInfo bizInfo) {
		this.bizInfo = bizInfo;
	}

	public final SchemeManager getSchemeManager() {
		return schemeManager;
	}
	
	@Required
	public final void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}
	
	/**
	 * 调用其他Scheme。
	 * 
	 * @param schemeInfo
	 * @param parameters
	 * @return
	 */
	public Object invoke(String schemeInfo, Map<?,?> parameters){
		return SchemeInvoker.invoke(getSchemeManager(), schemeInfo, parameters);
	}
}
