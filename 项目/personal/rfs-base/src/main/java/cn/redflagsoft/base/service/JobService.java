/*
 * $Id: JobService.java 4615 2011-08-21 07:10:37Z lcj $
 * JobService.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Job;

/**
 * @author mwx
 *
 */
public interface JobService {
	
	/**
	 * 创建Job
	 * 
	 * @param job
	 * @return Job
	 */
	Job createJob(Job job);
	
	/**
	 * 创建job
	 * 
	 * @param clerkId
	 * @param type
	 * @return Job
	 */
	Job createJob(Long clerkId,int type);
	/**
	 * 创建job
	 * 
	 * @param clerkId
	 * @param type
	 * param matterId
	 * @return Job
	 */
	Job createJob(Long clerkId,int type,Long routeId);
	
	Job createJob(Long clerkId, int bizType, Long bizId, Long objectId, short dutyerType, Long dutyerID);
	
	/**
	 * 
	 * 设置bizTrack
	 * 
	 * @param jobSn
	 * @param track
	 */
	void setTrack(Long jobSn,Long track);
	
	/**
	 * 修改hangTimes属性
	 * 
	 * @param jobSn
	 */
	 void hangJob(Long jobSn);
	 
	 /**
	  * 修改wakeTime和timeHang属性
	  * 
	  * @param jobSn
	  */
	 void wakeJob(Long jobSn);
	 
	 /**
	  * 结束job
	  * 
	  * @param jobSn
	  */
	 void terminateJob(Long jobSn,Byte result);
	 
	 /**
	  * 修改Job
	  * 
	  * @param job
	  * @return Job
	  */
	 Job updateJob(Job job);
	 
	 /**
	  * 删除Job
	  * 
	  * @param job
	  * @return int类型，为1时表示删除成功
	  */
	 int deleteJob(Job job);
	 
	 Job getJobBySn(Long sn);

}
