/*
 * $Id: TodayWorkHintWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.TaskService;

/**
 * 
 * 
 * @author Alex Lin
 *
 */
@ProcessType(TodayWorkHintWorkProcess.TYPE)
public class TodayWorkHintWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 5001 ;
	private TaskService taskService;
	
	private  int index; //1:�����û�ID�õ�ҵ���б�
    
	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 * @see BizVO
	 */
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		Clerk clerk = UserClerkHolder.getClerk();
		return taskService.findTaskForTodayHint(clerk.getEntityID(),clerk.getId(),index);
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
}
