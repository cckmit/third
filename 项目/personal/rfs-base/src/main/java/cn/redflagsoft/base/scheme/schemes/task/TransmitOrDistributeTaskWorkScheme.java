/*
 * $Id: TransmitOrDistributeTaskWorkScheme.java 6138 2012-11-29 02:20:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * 转发或者分发。
 * 
 * 配置时只配置workType，调用时需要指定objectServiceName,taskSN,taskType,objectId,userId,username,matterIds[0]等参数。
 * 
 * ?objectServiceName=&taskSN=&taskType=&objectId=&userId=&matterIds[0]
 * 或 ?objectServiceName=&taskSN=&taskType=&objectId=&username=&matterIds[0]
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TransmitOrDistributeTaskWorkScheme extends AbstractTaskWorkScheme {
	private Long userId;//接收人，与username二者取其一
	private String username;//接收人，与userId二者取其一
//	private String note;//备注
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
//
//	/**
//	 * @return the note
//	 */
//	public String getNote() {
//		return note;
//	}
//
//	/**
//	 * @param note the note to set
//	 */
//	public void setNote(String note) {
//		this.note = note;
//	}

	protected Clerk getTargetClerk(){
		Clerk clerk = null;
		if(username != null && userId == null){
			User user = (User) Application.getContext().getUserManager().loadUserByUsername(username);
			if(user != null){
				userId = user.getUserId();
			}
		}
		if(userId != null){
			clerk = getClerkService().getClerk(userId);
		}
		return clerk;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#setWorkSN(java.lang.Long)
	 */
	@Override
	public void setWorkSN(Long workSN) {
		throw new UnsupportedOperationException("转发中不支持设置Work对象");
	}

	public Object doScheme(){
		Clerk clerk = getTargetClerk();
		if(clerk == null){
			throw new IllegalArgumentException("必须指定接收人ID或者用户名");
		}
		Task task2 = getTask();
		//责任人不变
//		task2.setDutyerID(clerk.getId());
//		task2.setDutyerName(clerk.getName());
		//只改变业务办理人
		task2.setClerkID(clerk.getId());
		task2.setClerkName(clerk.getName());
		task2.setNote(getNote());
		getTaskService().updateTask(task2);
		
		Work work2 = getWork();
		work2.setString1(clerk.getId() + "");
		work2.setString2(clerk.getName());
		//work2.setRemark(getNote());
		getWorkService().updateWork(work2);
		
		return "业务处理完成！";
	}
}
