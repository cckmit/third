/*

 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Receiver;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.ReceiverService;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * 
 * @author ck
 *
 */
@ProcessType(ReceiverWorkProcess2.TYPE)
public class ReceiverWorkProcess2 extends AbstractWorkProcess {
	public static final int TYPE =6032;
	private ReceiverService receiverService;
	private Integer taskType;
	private Integer workType;
	private Integer actualProcessType;
    private ClerkService clerkService;
 
    
	public void setReceiverService(ReceiverService receiverService) {
		this.receiverService = receiverService;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public void setActualProcessType(Integer actualProcessType) {
		this.actualProcessType = actualProcessType;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public ReceiverService getReceiverService() {
		return receiverService;
	}

	public Integer getActualProcessType() {
		return actualProcessType;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	/**
	 * 
	 * @see Clerk
	 * @see Receiver
	 */
	public Object execute(Map parameters, boolean needLog) {
		List<Clerk> cList=new ArrayList<Clerk>();
		Clerk clerk = UserClerkHolder.getClerk();	
		if(clerk!=null){
			List<Receiver> rs=receiverService.findReceiver(taskType, workType, actualProcessType, clerk.getId());
			for(Receiver r:rs){
				if(r.getCategory()==Receiver.CATEGORY_CLERK){
					Clerk c=clerkService.getClerk(r.getReceiver());
					cList.add(c);
				}else if(r.getCategory()==Receiver.CATEGORY_ENTITY){
					Long reciver=r.getReceiver();
					if(reciver==0){
						reciver=clerk.getEntityID();
					}
					Logic logic =Restrictions.logic(Restrictions.eq("b.entityID",reciver));	
					ResultFilter rf = ResultFilter.createEmptyResultFilter();	
					rf.setCriterion(logic);
					List<UserClerkVO> cList2=clerkService.findUserClerkVOs(rf);
					for(UserClerkVO c:cList2){
						if(!cList.contains(c)){
							if(c.isEnabled()){//有效的用户
								cList.add(c);
							}
						}
					}
				}			
			}
		}
			
		return cList;
	}
}
