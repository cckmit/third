/*
 * $Id: BaseWorkScheme.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ���� WorkScheme ����������ͨ����ҵ�����ܡ�
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
		Assert.notNull(taskBizInfoService, "�������� taskBizInfoService");
		Assert.notNull(schemeManager, "��������schemeManager");
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
	 * �������ɡ�
	 * 
	 * @return �������
	 */
	public Object doDispatch(){
		Assert.notNull(workItem, "�������ɶ�����Ϊ�ա�");
		Assert.notNull(workItem.getTimeLimit(), "����ʱ�޲���Ϊ�ա�");
		
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
		
		return "�������ɳɹ�";
	}


	private void setAttachmentsAndDocsCount(TaskBizInfo bizInfo){
		DatumVOList list = getDatumVOList();
		List<Long> ids = getFileIds();
//		bizInfo.setAcptWorkItemName(workItem.getWorkItemName());
		bizInfo.setAcptAttacchmentCount(ids != null ? ids.size() : 0);
		bizInfo.setAcptDocCount(list != null ? list.size() : 0);
	}
	
	/**
	 * ��������
	 * WorkType=13
	 */
	public Object doAccept(){
		//Assert.notNull(workItem, "�������������Ϊ�ա�");
		Assert.notNull(bizInfo, "�������������Ϊ�ա�");

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
		
		String s = String.format("��������%s��%s����", AppsGlobals.formatDate(new Date()), getClerk().getName());
		log.debug(s);
		task.setNote(s);
		
		getTaskService().updateTask(task);
		processWorkItem(bizInfo.getAcptWorkItemName());

		setAttachmentsAndDocsCount(bizInfo);
		System.err.println("-------------------------------" + taskBizInfoService);
		taskBizInfoService.saveAcptBizInfo(task, bizInfo);

		return "��������ɹ�";
	}

	
	/**
	 * ���������Ǽǡ�
	 * WorkType=14.
	 * @return �������
	 */
	public Object doDecisionApply(){
		Assert.notNull(bizInfo, "ҵ�������Ϊ�ա�");
		
		processWorkItem(bizInfo.getDaWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveDaBizInfo(task, bizInfo);

		return "���������Ǽ���ɡ�";
	}
	
	/**
	 * �����������ġ�
	 * WorkType=15
	 * @return �������
	 */
	public Object doDecisionResult(){
		Assert.notNull(bizInfo, "ҵ�������Ϊ�ա�");
		
		processWorkItem(bizInfo.getDrWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveDrBizInfo(task, bizInfo);
		
		return "��������������ɡ�";
	}
	
	
	/**
	 * ������������
	 * WorkType=16
	 * @return �������
	 */
	public Object doRecord(){
		Assert.notNull(bizInfo, "ҵ�������Ϊ�ա�");
		
		processWorkItem(bizInfo.getRcdWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveRcdBizInfo(task, bizInfo);
		return "������������ɡ�";
	}
	
	/**
	 * �������ϴ浵��
	 * WorkType=17
	 * @return �������
	 */
	public Object doArchive(){
		Assert.notNull(bizInfo, "ҵ�������Ϊ�ա�");
		
		processWorkItem(bizInfo.getAcvWorkItemName());
		
		Task task = getTask();
		setAttachmentsAndDocsCount(bizInfo);
		taskBizInfoService.saveAcvBizInfo(task, bizInfo);
		
		return "�������ϴ浵��ɡ�";
	}

	
	/**
	 * ��ѯҵ�����
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
			throw new IllegalArgumentException("����ҵ����Ϣ�Ĳ�������ȷ��");
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
	 * ��������Scheme��
	 * 
	 * @param schemeInfo
	 * @param parameters
	 * @return
	 */
	public Object invoke(String schemeInfo, Map<?,?> parameters){
		return SchemeInvoker.invoke(getSchemeManager(), schemeInfo, parameters);
	}
}
