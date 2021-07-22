/*

 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.vo.TaskVO;

/**
 * 
 * @author ck
 *
 */
@ProcessType(TransmitWorkProcess.TYPE)
public class TransmitWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6033;
	
    private TaskService taskService;
	private List<TaskVO> list;
	private Clerk clerk;

	
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public void setList(List<TaskVO> list) {
		this.list = list;
	}


	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}


	public TaskService getTaskService() {
		return taskService;
	}


	public List<TaskVO> getList() {
		return list;
	}


	public Clerk getClerk() {
		return clerk;
	}


	public Object execute(Map parameters, boolean needLog) {
	    List<Long> taskIds=new ArrayList<Long>();
	    for(TaskVO t:list){
			taskIds.add(t.getId());
		}
		//System.out.println("================clerkid"+clerk.getId());
		if(taskIds.size()>0){
			taskService.updateTaskClerkIdByTaskIds(taskIds,clerk.getKey());
			return "提交成功！";
		}
		
		return "提交失败！";
	}
}
