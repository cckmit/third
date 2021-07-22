package cn.redflagsoft.base.service.impl;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.service.InfoStatHandler;
import cn.redflagsoft.base.service.TaskService;

public class TaskInfoStatHandler implements InfoStatHandler {

	private TaskService taskService;
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public boolean supports(RFSObject o, InfoConfig config){
		return (config.getType() == InfoConfig.TYPE_TASK);
	}

	public byte getStatStatus(RFSObject o, InfoConfig config){
		
		//= ("select sn from Task t where t.refObjectType=? and t.refObjectId=? and t.type=?", o.objectType, o.id, config.value);
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();	
		SimpleExpression eq = Restrictions.eq("refObjectId", o.getId());
		SimpleExpression eq2 = Restrictions.eq("refObjectType", o.getObjectType());
		SimpleExpression eq3 = Restrictions.eq("type", Integer.parseInt(config.getValue()));
		
		Logic and = Restrictions.logic(eq).and(eq2).and(eq3);
		filter.setCriterion(and);
		int taskCount = taskService.getTaskCount(filter);
		
		if(taskCount > 0){
			return InfoDetail.STATUS_COMPLETE;
		}else{
			return InfoDetail.STATUS_INCOMPLETE;
		}
	}

}
