/*
 * $Id: JobServiceImpl.java 5240 2011-12-21 09:27:55Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;

import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.Job;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.JobDao;
import cn.redflagsoft.base.event2.JobEvent;
import cn.redflagsoft.base.service.BizRouteService;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.BizTrackService;
import cn.redflagsoft.base.service.ClueService;
import cn.redflagsoft.base.service.JobService;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.RiskMonitorableService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.util.DateUtil;

/**
 * @author mwx
 *
 */
public class JobServiceImpl implements JobService, EventDispatcherAware{
	private static final Log log = LogFactory.getLog(JobServiceImpl.class);
	
	public static final byte CATEGORY_JOB = 1;
	private JobDao jobDao;
	private BizTrackService bizTrackService;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;
	private CodeGeneratorProvider codeGeneratorProvider;
	private RiskMonitorableService riskMonitorableService;
	private BizRouteService bizRouteService;
	private RiskService riskService;
	private ClueService clueService;
	private ObjectService<RFSObject> objectService;

	private EventDispatcher dispatcher;

	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}
	/**
	 * @param bizTrackNodeInstanceService the bizTrackNodeInstanceService to set
	 */
	public void setBizTrackNodeInstanceService(
			BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}
	/**
	 * @param bizTrackService the bizTrackService to set
	 */
	public void setBizTrackService(BizTrackService bizTrackService) {
		this.bizTrackService = bizTrackService;
	}
	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	
	public void setClueService(ClueService clueService) {
		this.clueService = clueService;
	}

	public void setRiskMonitorableService(
			RiskMonitorableService riskMonitorableService) {
		this.riskMonitorableService = riskMonitorableService;
	}

	public void setBizRouteService(BizRouteService bizRouteService) {
		this.bizRouteService = bizRouteService;
	}
	
	public void setObjectService(ObjectService<RFSObject> objectService) {
		this.objectService = objectService;
	}
	
	public Job createJob(Long clerkId, int type) {
		return createJob(clerkId,type,(Long)null);
	}
	
	public Job createJob(Long clerkId, int type, Long routeId) {
		Long trackSn=null;
		Job job = new Job();
		@SuppressWarnings("unchecked")
		IdGenerator<Long> jobGen = ((IdGeneratable<Long>) jobDao).getIdGenerator();
		Long jobSn = jobGen.getNext();
		log.debug("新建的JobSn为：" + jobSn);
		job.setSn(jobSn);
		job.setType(type);
		
		job.setBeginTime(new Date());
		job.setCreator(clerkId);
		job.setCreationTime(new Date());
		job.setStatus(Job.JOB_STATUS_WORK);
		
		if (routeId!=null){  
			trackSn=bizTrackService.createBizTrack((byte)1, job.getSn(), routeId);
		}
		if (trackSn!=null) {
			job.setBizTrack(trackSn);
		}
		
		String code = codeGeneratorProvider.generateCode(job);
		job.setCode(code);
		
		job = jobDao.save(job);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.CREATED, job));
		}
		
		return job;
	}

	public Job createJob(Long clerkId, int bizType, Long bizId, Long objectId, short dutyerType, Long dutyerID) {
        Long routeId=null;
		Long trackSn=null;
		
        /*** 生成基本信息**/
		Job job = new Job();
		@SuppressWarnings("unchecked")
		IdGenerator<Long> jobGen = ((IdGeneratable<Long>) jobDao).getIdGenerator();
		Long jobSn = jobGen.getNext();
		log.debug("新建的JobSn为：" + jobSn);
		job.setSn(jobSn);
		job.setType(bizType);
//		String code = codeGeneratorProvider.generateCode(Job.class, CATEGORY_JOB, bizType);
//		job.setCode(code);
		job.setBeginTime(new Date());
		job.setCreator(clerkId);
		job.setCreationTime(new Date());
		job.setStatus(Job.JOB_STATUS_WORK);
		job.setDutyerID(dutyerID);
		job.setDutyerType(dutyerType);
		
		/*** 生成基本风险**/
		
		RFSObject object = objectService.getObject(objectId);
		
		List<RiskEntry> entries = riskService.createRisk(job, bizType, dutyerID, object);
		job.setRiskEntries(entries);
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(job, "timeUsed");
		if(valueUnit != null) {
			job.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(job, "timeUsed", scaleId);
			job.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(job, "hangUsed");
		if(valueUnit != null) {
			job.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(job, "hangUsed", scaleId);
			job.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(job, "delayUsed");
		if(valueUnit != null) {
			job.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(job, "delayUsed", scaleId);
			job.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(job, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(job, "hangTimes", scaleId);
			job.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(job, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(job, "delayTimes", scaleId);
			job.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		    
		/*** 生成业务流程和业务线索**/
		BizRoute bizRoute = bizRouteService.getBizRoute(CATEGORY_JOB, bizType, bizId);
		
		if(bizRoute != null) {
			routeId = bizRoute.getId();
		}
		if (routeId != null){
			trackSn = bizTrackService.createBizTrack(CATEGORY_JOB, jobSn, routeId);
		}
		if (trackSn!=null) {
			job.setBizTrack(trackSn);
		}
		
		clueService.createClue(objectId, CATEGORY_JOB, bizType, bizId, jobSn,trackSn);
		
		
		String code = codeGeneratorProvider.generateCode(job);
		job.setCode(code);
		
		/*** 保存**/           		
		job = jobDao.save(job);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.CREATED, job));
		}
		
		return job;
	}
	
	
	public void hangJob(Long jobSn) {
		Job job = jobDao.get(jobSn);
		Job oldJob = new Job();
		try {
			PropertyUtils.copyProperties(oldJob, job);
		} catch (Exception e) {
			log.debug("属性复制出错", e);
		}
		
		job.setHangTime(new Date());
		job.setHangTimes((byte)(job.getHangTimes()+1));
		job.setStatus(Job.JOB_STATUS_HANG);
		jobDao.update(job);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_JOB, jobSn, Job.JOB_STATUS_HANG);		
	
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.UPDATED, oldJob, job));
		}
	}

	public void setTrack(Long jobSn, Long track) {
		Job job = jobDao.get(jobSn);
		Job oldJob = job.clone();
		job.setBizTrack(track);
		jobDao.update(job);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.UPDATED, oldJob, job));
		}
	}

	public void terminateJob(Long jobSn,Byte result) {
		Job job = jobDao.get(jobSn);
		Job oldJob = job.clone();
		
		job.setEndTime(new Date());
		job.setTimeUsed((short)(DateUtil.getTimeUsed(job.getBeginTime(), job.getEndTime(), job.getTimeUnit())-job.getTimeHang()-job.getTimeDelay()));
		job.setStatus(Job.JOB_STATUS_TERMINATE);	
		clueService.updateClueStatus((byte)1,job.getId(), Job.JOB_STATUS_TERMINATE);	
		if(result != null) {
			job.setResult(result);
		}
		jobDao.update(job);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_JOB, jobSn, Job.JOB_STATUS_TERMINATE);		
	
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.UPDATED, oldJob, job));
		}
	}

	public void wakeJob(Long jobSn) {
		Job job = jobDao.get(jobSn);
		Job oldJob = job.clone();
		
		//唤醒时间设置为当前
		job.setWakeTime(new Date());
		job.setTimeHang((short)(job.getTimeHang()+DateUtil.getTimeUsed(job.getHangTime(), job.getWakeTime(), (byte)5)));
		job.setStatus(Job.JOB_STATUS_WORK);
		jobDao.update(job);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_JOB, jobSn, Job.JOB_STATUS_WORK);		
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.UPDATED, oldJob, job));
		}
	}

//	@SuppressWarnings("unused")
//	private void attachToTrack (int jobType,Long jobSn,Long trackSn){  
//		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte)1,jobType,jobSn,trackSn);
//	}
//
//	//jobSn与job不属于同一个Jo
//	@SuppressWarnings("unused")
//	private void attachToJob (int jobType,Long jobSn,Job upJob){  
//		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte)1,jobType,jobSn,upJob.getBizTrack());
//	}
//	
//	@SuppressWarnings("unused")
//	private void attachToJob (int jobType,Long jobSn,Long upJobSn){  
//		bizTrackNodeInstanceService.createBizTrackNodeInstance((byte)1,jobType,jobSn,upJobSn);
//	}
//	
	public Job createJob(Job job) {
		job = jobDao.save(job);
		
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.CREATED, job));
		}
		return job;
	}
	
	public int deleteJob(Job job) {
		int result = jobDao.delete(job);
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.DELETED, job));
		}
		return result;
	}
	
	public Job updateJob(Job job) {
		job = jobDao.update(job);
		if(dispatcher != null){
			dispatcher.dispatchEvent(new JobEvent(JobEvent.Type.UPDATED, job));
		}
		return job;
	}
	
	public Job getJobBySn(Long sn) {
		return jobDao.get(sn);
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
