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
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.event.RiskLogEvent;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskService;

/**
 * 变更指定对象（多个）的责任人。
 * 
 * @author ck
 *
 */
@ProcessType(DutyChangeWorkProcess.TYPE)
public class DutyChangeWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6034;
	private ObjectService objectService;
	private TaskService taskService;
	private RiskService riskService;
    
	public RiskService getRiskService() {
		return riskService;
	}



	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}


	private List<RFSObject> list;
	public List<RFSObject> getList() {
		return list;
	}



	public void setList(List<RFSObject> list) {
		this.list = list;
	}


	public TaskService getTaskService() {
		return taskService;
	}



	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	private Clerk clerk;


	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}



	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}
	

	public Clerk getClerk() {
		return clerk;
	}


	public ObjectService getObjectService() {
		return objectService;
	}


	
	public Object execute(Map parameters, boolean needLog) {
	    List<Long> ids=new ArrayList<Long>();
	    for(RFSObject o:list){
	    	ids.add(o.getId());
		}
		//System.out.println("================clerkid"+clerk.getKey());
		//System.out.println("================clerkName"+clerk.getName());
		if(ids.size()>0){
			objectService.updateObjectsDutyClerkIdAndNameByClerkID(ids, clerk.getKey(), clerk.getName());
			for(Long id:ids){
				List<Task> tasks=taskService.findTaskByObjectId(id);
				//List<Long> taskIds=new ArrayList<Long>();
			    for(Task t:tasks){   
			    	if(t.getStatus()!=Task.TASK_STATUS_TERMINATE&&t.getStatus()!=Task.TASK_STATUS_CANCEL&&t.getStatus()!=Task.TASK_STATUS_STOP){
			    		if(t.getDutyerID().equals(clerk.getEntityID())){
			    			if(!clerk.getKey().equals(t.getClerkID())){
			    				t.setClerkID(clerk.getKey());
			    				taskService.updateTask(t);
				    			List<RiskEntry> entries=t.getRiskEntries();
				    			 for(RiskEntry re:entries){
				 					Risk r=riskService.getRiskById(re.getRiskID());
				 					riskService.sendRiskLogEvent(r,t,RiskLogEvent.DUTYER_CHANGE);		
				 				}
			    			}
			    			//taskIds.add(t.getId());	
			    		}
			    	}   		
				}
//			    if(taskIds.size()>0){
//			    	 taskService.updateTaskClerkIdByTaskIds(taskIds,clerk.getKey());	
//			    }
			    
			}
			
			
			
			return "提交成功！";
		}
		
		return "提交失败！";
	}
}
