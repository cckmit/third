/*
 * $Id: WorkServiceImpl.java 6095 2012-11-06 01:07:54Z lcj $
 * WorkServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.EntityObjectToWork;
import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.bean.Objects;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.bean.TimeLimit;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.EntityObjectToWorkDao;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.dao.TimeLimitDao;
import cn.redflagsoft.base.dao.WorkDao;
import cn.redflagsoft.base.service.BizRouteService;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.BizTrackService;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.ClueService;
import cn.redflagsoft.base.service.DutyersInfoManager;
import cn.redflagsoft.base.service.GlossaryService;
import cn.redflagsoft.base.service.RiskMonitorableService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskDefProvider;
import cn.redflagsoft.base.service.WorkDefProvider;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.util.DateUtil;

/**
 * @author mwx
 *
 */
public class WorkServiceImpl implements WorkService, InitializingBean{
	private static final Log log = LogFactory.getLog(WorkServiceImpl.class);
	
	public static final byte CATEGORY_WORK = 2;
	private WorkDao workDao;
	private TaskDao taskDao;
	private TimeLimitDao timeLimitDao;
	private BizRouteService bizRouteService ;
	private BizTrackService bizTrackService ;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;
	private CodeGeneratorProvider codeGeneratorProvider;
	private IdGenerator<Long> idGenerator;
	private RiskService riskService;
	private RiskMonitorableService riskMonitorableService;
	private ClueService clueService;
//	private ObjectService<RFSObject> objectService;
	private ObjectsDao objectsDao;
	
	private GlossaryService glossaryService;
	private ClerkService clerkService;
	
	private WorkDefProvider workDefProvider;
	private TaskDefProvider taskDefProvider;
	private EntityObjectToWorkDao entityObjectToWorkDao;

	private DutyersInfoManager dutyersInfoManager;
	
	/**
	 * @param entityObjectToWorkDao the entityObjectToWorkDao to set
	 */
	public void setEntityObjectToWorkDao(EntityObjectToWorkDao entityObjectToWorkDao) {
		this.entityObjectToWorkDao = entityObjectToWorkDao;
	}
	public WorkDefProvider getWorkDefProvider() {
		return workDefProvider;
	}
	public void setWorkDefProvider(WorkDefProvider workDefProvider) {
		this.workDefProvider = workDefProvider;
	}
	public void setGlossaryService(GlossaryService glossaryService) {
		this.glossaryService = glossaryService;
	}
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}
	
	/**
	 * @param taskDefProvider the taskDefProvider to set
	 */
	public void setTaskDefProvider(TaskDefProvider taskDefProvider) {
		this.taskDefProvider = taskDefProvider;
	}
	/**
	 * @return the objectsDao
	 */
	public ObjectsDao getObjectsDao() {
		return objectsDao;
	}
	/**
	 * @param objectsDao the objectsDao to set
	 */
	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}
	/**
	 * @param bizRouteService the bizRouteService to set
	 */
	public void setBizRouteService(BizRouteService bizRouteService) {
		this.bizRouteService = bizRouteService;
	}
	/**
	 * @param bizTrackService the bizTrackService to set
	 */
	public void setBizTrackService(BizTrackService bizTrackService) {
		this.bizTrackService = bizTrackService;
	}
	/**
	 * @param workDao the workDao to set
	 */
	@SuppressWarnings("unchecked")
	public void setWorkDao(WorkDao workDao) {
		this.workDao = workDao;
		idGenerator = ((IdGeneratable<Long>) workDao).getIdGenerator();
	}
	/**
	 * @param timeLimitDao the timeLimitDao to set
	 */
	public void setTimeLimitDao(TimeLimitDao timeLimitDao) {
		this.timeLimitDao = timeLimitDao;
	}
	
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	
	public void setRiskMonitorableService(
			RiskMonitorableService riskMonitorableService) {
		this.riskMonitorableService = riskMonitorableService;
	}
	
	public void setClueService(ClueService clueService) {
		this.clueService = clueService;
	}
	
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
//	public void setObjectService(ObjectService<RFSObject> objectService) {
//		this.objectService = objectService;
//	}
	
	public void setBizTrackNodeInstanceService(
			BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}
	
	/**
	 * @param dutyersInfoManager the dutyersInfoManager to set
	 */
	public void setDutyersInfoManager(DutyersInfoManager dutyersInfoManager) {
		this.dutyersInfoManager = dutyersInfoManager;
	}
	
	
	public Work createWork(Long clerkID, Long taskSn, int type) {
		
		return createWork(clerkID,taskSn,type,(Long)null);
	}
	
	public Work createWork(Long clerkID, Long taskSn, int type, Long matterId) {
		if(matterId!=null){
			return createWork(clerkID,taskSn,type, new Long[]{matterId});
		}else{
			return createWork(clerkID,taskSn,type,(Long[])null);
		}
	}
	
	public Work createWork(Long clerkID, Long taskSn, int type, Long[] matterIds) {
		return createWork(clerkID,taskSn,type, matterIds, null);
	}
	
	/**
	 * 
	 */
	public Work createWork(Long clerkID, Long taskSn, int type, Long[] matterIds, Long sn){
		Clerk clerk = clerkService.getClerk(clerkID);
		return createWork(clerk, null, taskSn, type, matterIds, sn, null, null);	
	}
	
	
	protected String getWorkName(int workType){
		String typeName = glossaryService.getByCategoryAndCode(Glossary.CATEGORY_WORKTYPE, workType);
		if(StringUtils.isNotBlank(typeName)){
			return typeName;
		}
		WorkDef workDef = workDefProvider.getWorkDef(workType);
		if(workDef != null){
			return workDef.getName();
		}
		return null;
	}
	/**
	 * 
	 * @param clerk
	 * @param object
	 * @param taskSn
	 * @param type
	 * @param matterIds
	 * @param sn
	 * @return
	 */
	public Work createWork(Clerk clerk, RFSEntityObject object, Long taskSn, int type, Long[] matterIds, Long sn, Date beginTime, Date endTime) {
		if(sn == null){
			sn = generateId();
		}
		Work work = new Work();
		//IdGenerator<Long> workGen = ((IdGeneratable<Long>) workDao).getIdGenerator();
		//Long workSn = workGen.getNext();
		work.setSn(sn);
		work.setTaskSN(taskSn);
		work.setType(type);
		work.setBeginTime(beginTime != null ? beginTime : new Date());
		work.setStatus(Work.WORK_STATUS_WORK);
		
		if(endTime != null){
			if(endTime.before(work.getBeginTime())){
				endTime = work.getBeginTime();
			}
			work.setEndTime(endTime);
		}
		
		/***
		 * 为创建 Work 添加风险监控,添加时间 2008/12/14 start ymq
		 */
		String name = getWorkName(type);//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_WORKTYPE, type);
		if(name != null){
			work.setName(name);
		}
	
		if(clerk != null){
			work.setClerkID(clerk.getId());
			work.setClerkName(clerk.getName());
		}
		
		if(object != null){
			work.setRefObjectId(object.getId());
			//work.setRefObjectName(object.getName());
			work.setRefObjectType(object.getObjectType());
			if(object instanceof RFSObjectable){
				work.setRefObjectName(((RFSObjectable)object).getName());
			}
			//TODO
			//保存关系
			//saveOrUpdateObjectWork(work.getSn(), object.getId(), object.getObjectType());
			//必须在Work保存后在保存
			//updateWorkEntityObjects(work, object);
		}
		
		WorkDef workDef = workDefProvider.getWorkDef(work.getType());
		//设置三级责任人信息
		log.debug("给业务设置三级责任人信息: " + work);
		dutyersInfoManager.findAndSetDutyers(work, workDef, object, clerk.getId());
		
		
		
		List<RiskEntry> entries = riskService.createRisk(work, work.getType(), work.getClerkID(), null);
		work.setRiskEntries(entries);
		byte scaleId = 0;
		BigDecimal scale;
		Byte valueUnit;
		
		valueUnit = riskMonitorableService.getRiskValueUnit(work, "timeUsed");
		if(valueUnit != null) {
			work.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(work, "timeUsed", scaleId);
			work.setTimeLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(work, "hangUsed");
		if(valueUnit != null) {
			work.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(work, "hangUsed", scaleId);
			work.setHangLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(work, "delayUsed");
		if(valueUnit != null) {
			work.setTimeUnit(valueUnit);
			scale = riskMonitorableService.getRiskScale(work, "delayUsed", scaleId);
			work.setDelayLimit(scale == null ? 0 : scale.shortValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(work, "hangTimes");
		if(valueUnit!= null) {
			scale = riskMonitorableService.getRiskScale(work, "hangTimes", scaleId);
			work.setHangTimes(scale == null ? 0 : scale.byteValue());
		}
		
		valueUnit = riskMonitorableService.getRiskValueUnit(work, "delayTimes");
		if(valueUnit != null) {
			scale = riskMonitorableService.getRiskScale(work, "delayTimes", scaleId);
			work.setDelayTimes(scale == null ? 0 : scale.byteValue());
		}
		
		/******************************************** end ymq ***/
		
		
		if(matterIds!=null&&matterIds.length>0){
			
			/***
			 * 修改 TimeLimits 相关,修改时间 2008/12/14 start ymq
			 */
			for (Long matterId : matterIds) {
				//此处传的matterIds 有问题
				Long taskTrack = createWorkTrack(work.getSn(), type, matterId);
				if(taskTrack!=null){
					work.setBizTrack(taskTrack);
					//clueService.createClue(objectId, CATEGORY_WORK, work.getType(), matterId, work.getSn(), work.getBizTrack());
				}
			}			
			
//			for(int i=0;i<matterIds.length;i++){
//				setTimeLimits(work,matterIds[i]);
//			}
//			Long workTrack = createWorkTrack(work.getSn(),type,matterIds);
//			if(workTrack != null){
//				work.setBizTrack(workTrack);
//			}
		}
		
		String code = codeGeneratorProvider.generateCode(work);
		work.setCode(code);
		
		work = workDao.save(work);

		if(object != null && !(object instanceof RiskObject)){
			//保存关系
			//saveOrUpdateObjectWork(work.getSn(), object.getId(), object.getObjectType());
			//必须在Work保存后在保存
			updateWorkEntityObjects(work, object);
		}
		return work;
	}

	public void hangWork(Work work, Date hangTime) {
//		Work work = workDao.get(workSn);
		work.setHangTime(hangTime != null ? hangTime : new Date());
		work.setHangTimes((byte) (work.getHangTimes() + 1));
		work.setHangUsed((byte)(work.getHangUsed() + 1));
		work.setStatus(Work.WORK_STATUS_HANG);
		workDao.update(work);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_WORK, work.getSn(), Work.WORK_STATUS_HANG);		
	}

	

	public void wakeWork(Work work, Date wakeTime) {
//		Work work = workDao.get(workSn);
		work.setWakeTime(wakeTime != null ? wakeTime : new Date());
		work.setTimeHang((short)(work.getTimeHang()+(DateUtil.getTimeUsed(work.getHangTime(),work.getWakeTime(),(byte)5))));
		work.setStatus(Work.WORK_STATUS_WORK);
		workDao.update(work);
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_WORK, work.getSn(), Work.WORK_STATUS_WORK);		
	}

	public void terminateWork(Work work, Byte result, Date endTime) {
//		Work work = workDao.get(workSn);
		endTime = endTime != null ? endTime : new Date();
		//make sure beginTime is not null
		if(work.getBeginTime() == null){
			work.setBeginTime(endTime);
		}
		if(endTime.before(work.getBeginTime())){
			endTime = work.getBeginTime();
			work.setEndTime(endTime);
			work.setTimeUsed((short)0);
		}else{
			work.setEndTime(endTime/* != null ? endTime : new Date()*/);
			work.setTimeUsed((short)(DateUtil.getTimeUsed(work.getBeginTime(),work.getEndTime(),(byte)5)-work.getTimeHang()-work.getTimeDelay()));
		}
		
		if(result != null){
			work.setResult(result);
		}
		work.setStatus(Work.WORK_STATUS_TERMINATE);
		//Task task = taskDao.get(work.getTaskSN());
		//task.setActiveWorkSN(0L);
		//taskDao.update(task);
		clueService.updateClueStatus((byte)3, work.getId(), Work.WORK_STATUS_TERMINATE);		
		
		workDao.update(work);
		//直接调用更新activeWorkSN的方法。
		//taskDao.updateActiveWorkSN(work.getTaskSN(), 0L);
		Task task = taskDao.get(work.getTaskSN());
		if(task != null){
			log.debug("更新ActiveWorkSN：" + task);
			task.setActiveWorkSN(0L);
			taskDao.update(task);
		}
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_WORK, work.getSn(), Work.WORK_STATUS_TERMINATE);		
	}
	
	
	public boolean setTimeLimits(Work work, Long matter){  
		byte category=3;
		boolean flag = true;
		TimeLimit tl = timeLimitDao.getTimeLimt(category, work.getType(), matter);
		if(tl!=null){
			if(tl.getTimeLimit()>work.getTimeLimit()){
				work.setTimeLimit(tl.getTimeLimit());
			}
			if(tl.getHangLimit()>work.getHangLimit()){
				work.setHangLimit(tl.getHangLimit());
			}
			if(tl.getDelayLimit()>work.getDelayLimit()){
				work.setDelayLimit(tl.getDelayLimit());
			}
			if(tl.getHangTimes()>work.getHangTimes()){
				work.setHangTimes(tl.getHangTimes());
			}
			if(tl.getDelayTimes()>work.getDelayTimes()){
				work.setDelayTimes(tl.getDelayTimes());
			}
		}else{
			flag=false;
		}
		return flag;
	}

	
	public Long  createWorkTrack(Long workSn,int workType,Long ...matterIds){   
		byte category = 3;// work
		BizRoute workRoute;
		Long bizTrackSn = null;
		// TODO 此处循环待修改 暂考虑matterids的长度等于1的情况
		for (int i = 0; i < matterIds.length; i++) {
			workRoute = bizRouteService.getBizRoute(category, workType,
					matterIds[i]);
			if(workRoute!=null){
				bizTrackSn = bizTrackService.createBizTrack(category, workSn,
						workRoute);
			}
		}
		return bizTrackSn;

	}
	
	public Work createWork(Work work) {
		return workDao.save(work);
	}
	
	public int deleteWork(Work work) {
		//删除EntityObjectWork关联对象
		entityObjectToWorkDao.remove(Restrictions.eq("workSN", work.getSn()));
		
		return workDao.delete(work);
	}
	
	public Work updateWork(Work work) {
		return workDao.update(work);
	}

	public Work getWork(Long sn) {
		return workDao.get(sn);
	}
	
	
	/** add by ymq ********************************************/
	
	public List<Work> findWorkByTaskSN(Long taskSN) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setOrder(Order.desc("creationTime"));
		rf.setCriterion(Restrictions.eq("taskSN", taskSN));
		return workDao.find(rf);
	}
	
	/** end add ********************************************/
	
	
	public Long generateId() {
		return idGenerator.getNext();
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(workDao);
		Assert.notNull(idGenerator);
		Assert.notNull(taskDao);
		Assert.notNull(timeLimitDao);
		Assert.notNull(bizRouteService);
		Assert.notNull(bizTrackService);
		Assert.notNull(codeGeneratorProvider);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#findWorkWithAttachmentCount(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable
	public List<Work> findWorkWithAttachmentCount(ResultFilter filter) {
		List<Work> list = filter != null ? workDao.find(filter) : workDao.find();
		
		//TODO 临时这样用，将来name,clerkName这2个属性可能放在数据库中。
		//GlossaryService glossaryService = Application.getContext().get("glossaryService", GlossaryService.class);
		//ClerkService clerkService = Application.getContext().get("clerkService", ClerkService.class);
		
		for(int i = 0 ; i < list.size() ; i++){
			Work work = list.get(i);
			ResultFilter f2 = ResultFilter.createEmptyResultFilter();
			Logic logic = Restrictions.logic(Restrictions.eq("type", Objects.TYPE_工作与附件之间关系))
				.and(Restrictions.eq("fstObject", work.getSn()));
			f2.setCriterion(logic);
			int count = objectsDao.getCount(f2);
			f2.append(Restrictions.eq("", ""));
			work.setAttachmentCount(count);
			
			WorkWrapper ww = new WorkWrapper(work);
			
			if(work.getName() == null){
				String string = getWorkName(work.getType());//glossaryService.getByCategoryAndCode(Glossary.CATEGORY_WORKTYPE, work.getType());
				ww.setName(string);
			}

			if(work.getClerkID() != null && clerkService != null){
				Clerk clerk = clerkService.getClerk(work.getClerkID());
				ww.setClerkName(clerk.getName());
			}
			
			list.set(i, ww);
		}
		return list;
	}
	
	

	
	
	
	public static class WorkWrapper extends Work{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2696519741368785992L;
		private Work work;
		private String clerkName;
		//private String name;
		public WorkWrapper(Work work){
			this.work = work;
		}
		
		
		public String getClerkName() {
			return clerkName;
		}


		public void setClerkName(String clerkName) {
			this.clerkName = clerkName;
		}


//		public String getName() {
//			return name;
//		}
//
//
//		public void setName(String name) {
//			this.name = name;
//		}


		@Override
		public void addRiskEntry(RiskEntry entry) {
			
			work.addRiskEntry(entry);
		}
		@Override
		public RiskEntry getRiskEntryByObjectAttr(String attr) {
			
			return work.getRiskEntryByObjectAttr(attr);
		}
		@Override
		public int getAttachmentCount() {
			
			return work.getAttachmentCount();
		}
		@Override
		public Date getBeginTime() {
			
			return work.getBeginTime();
		}
		@Override
		public Long getBizRoute() {
			
			return work.getBizRoute();
		}
		@Override
		public Long getBizTrack() {
			
			return work.getBizTrack();
		}
		@Override
		public Long getClerkID() {
			
			return work.getClerkID();
		}
		@Override
		public String getCode() {
			
			return work.getCode();
		}
		@Override
		public short getDelayLimit() {
			
			return work.getDelayLimit();
		}
		@Override
		public byte getDelayTimes() {
			
			return work.getDelayTimes();
		}
		@Override
		public byte getDelayUsed() {
			
			return work.getDelayUsed();
		}
		@Override
		public Date getEndTime() {
			
			return work.getEndTime();
		}
		@Override
		public short getHangLimit() {
			
			return work.getHangLimit();
		}
		@Override
		public Date getHangTime() {
			
			return work.getHangTime();
		}
		@Override
		public byte getHangTimes() {
			
			return work.getHangTimes();
		}
		@Override
		public byte getHangUsed() {
			
			return work.getHangUsed();
		}
		@Override
		public Long getId() {
			
			return work.getId();
		}
		@Override
		public int getObjectType() {
			
			return work.getObjectType();
		}
		@Override
		public byte getProcessNum() {
			
			return work.getProcessNum();
		}
		@Override
		public Long getProposer() {
			
			return work.getProposer();
		}
		@Override
		public byte getProposerRole() {
			
			return work.getProposerRole();
		}
		@Override
		public String getRemark() {
			
			return work.getRemark();
		}
		@Override
		public byte getResult() {
			
			return work.getResult();
		}
		@Override
		public List<RiskEntry> getRiskEntries() {
			
			return work.getRiskEntries();
		}
		@Override
		public Long getSn() {
			
			return work.getSn();
		}
		@Override
		public String getSummary() {
			
			return work.getSummary();
		}
		@Override
		public Long getTaskSN() {
			
			return work.getTaskSN();
		}
		@Override
		public short getTimeDelay() {
			
			return work.getTimeDelay();
		}
		@Override
		public short getTimeHang() {
			
			return work.getTimeHang();
		}
		@Override
		public short getTimeLimit() {
			
			return work.getTimeLimit();
		}
		@Override
		public byte getTimeUnit() {
			
			return work.getTimeUnit();
		}
		@Override
		public short getTimeUsed() {
			
			return work.getTimeUsed();
		}
		@Override
		public Date getWakeTime() {
			
			return work.getWakeTime();
		}
		@Override
		public void removeRiskEntry(RiskEntry riskEntry) {
			
			work.removeRiskEntry(riskEntry);
		}
		@Override
		public void removeAllRiskEntries() {
			
			work.removeAllRiskEntries();
		}
		@Override
		public void setAttachmentCount(int attachmentCount) {
			
			work.setAttachmentCount(attachmentCount);
		}
		@Override
		public void setBeginTime(Date beginTime) {
			
			work.setBeginTime(beginTime);
		}
		@Override
		public void setBizRoute(Long bizRoute) {
			
			work.setBizRoute(bizRoute);
		}
		@Override
		public void setBizTrack(Long bizTrack) {
			
			work.setBizTrack(bizTrack);
		}
		@Override
		public void setClerkID(Long clerkID) {
			
			work.setClerkID(clerkID);
		}
		@Override
		public void setCode(String code) {
			
			work.setCode(code);
		}
		@Override
		public void setDelayLimit(short delayLimit) {
			
			work.setDelayLimit(delayLimit);
		}
		@Override
		public void setDelayTimes(byte delayTimes) {
			
			work.setDelayTimes(delayTimes);
		}
		@Override
		public void setDelayUsed(byte delayUsed) {
			
			work.setDelayUsed(delayUsed);
		}
		@Override
		public void setEndTime(Date endTime) {
			
			work.setEndTime(endTime);
		}
		@Override
		public void setHangLimit(short hangLimit) {
			
			work.setHangLimit(hangLimit);
		}
		@Override
		public void setHangTime(Date hangTime) {
			
			work.setHangTime(hangTime);
		}
		@Override
		public void setHangTimes(byte hangTimes) {
			
			work.setHangTimes(hangTimes);
		}
		@Override
		public void setHangUsed(byte hangUsed) {
			
			work.setHangUsed(hangUsed);
		}
		@Override
		public void setId(Long id) {
			
			work.setId(id);
		}
		@Override
		public void setProcessNum(byte processNum) {
			
			work.setProcessNum(processNum);
		}
		@Override
		public void setProposer(Long proposer) {
			
			work.setProposer(proposer);
		}
		@Override
		public void setProposerRole(byte proposerRole) {
			
			work.setProposerRole(proposerRole);
		}
		@Override
		public void setRemark(String remark) {
			
			work.setRemark(remark);
		}
		@Override
		public void setResult(byte result) {
			
			work.setResult(result);
		}
		@Override
		public void setRiskEntries(List<RiskEntry> entries) {
			
			work.setRiskEntries(entries);
		}
		@Override
		public void setSn(Long sn) {
			
			work.setSn(sn);
		}
		@Override
		public void setSummary(String summary) {
			
			work.setSummary(summary);
		}
		@Override
		public void setTaskSN(Long taskSN) {
			
			work.setTaskSN(taskSN);
		}
		@Override
		public void setTimeDelay(short timeDelay) {
			
			work.setTimeDelay(timeDelay);
		}
		@Override
		public void setTimeHang(short timeHang) {
			
			work.setTimeHang(timeHang);
		}
		@Override
		public void setTimeLimit(short timeLimit) {
			
			work.setTimeLimit(timeLimit);
		}
		@Override
		public void setTimeUnit(byte timeUnit) {
			
			work.setTimeUnit(timeUnit);
		}
		@Override
		public void setTimeUsed(short timeused) {
			
			work.setTimeUsed(timeused);
		}
		@Override
		public void setWakeTime(Date wakeTime) {
			
			work.setWakeTime(wakeTime);
		}
		@Override
		public String toString() {
			
			return work.toString();
		}
		@Override
		public byte getStatus() {
			
			return work.getStatus();
		}
		@Override
		public int getType() {
			return work.getType();
		}
		@Override
		public void setStatus(byte status) {
			work.setStatus(status);
		}
		@Override
		public void setType(int type) {
			work.setType(type);
		}
		@Override
		public Long getKey() {
			return work.getKey();
		}
		@Override
		public void setKey(Long id) {
			work.setKey(id);
		}
		
		@Override
		public String getName() {
			return work.getName();
		}


		@Override
		public String getProposerName() {
			return work.getProposerName();
		}


		@Override
		public Long getRefObjectId() {
			return work.getRefObjectId();
		}


		@Override
		public String getRefObjectName() {
			return work.getRefObjectName();
		}


		@Override
		public Integer getRefObjectType() {
			return work.getRefObjectType();
		}


		@Override
		public String getString0() {
			return work.getString0();
		}


		@Override
		public String getString1() {
			return work.getString1();
		}


		@Override
		public String getString2() {
			return work.getString2();
		}


		@Override
		public String getString3() {
			return work.getString3();
		}


		@Override
		public String getString4() {
			return work.getString4();
		}


		@Override
		public String getWorkItemName() {
			return work.getWorkItemName();
		}


		@Override
		public void setName(String name) {
			work.setName(name);
		}


		@Override
		public void setProposerName(String proposerName) {
			work.setProposerName(proposerName);
		}


		@Override
		public void setRefObjectId(Long refObjectId) {
			work.setRefObjectId(refObjectId);
		}


		@Override
		public void setRefObjectName(String refObjectName) {
			work.setRefObjectName(refObjectName);
		}


		@Override
		public void setRefObjectType(Integer refObjectType) {
			work.setRefObjectType(refObjectType);
		}


		@Override
		public void setString0(String string0) {
			work.setString0(string0);
		}


		@Override
		public void setString1(String string1) {
			work.setString1(string1);
		}


		@Override
		public void setString2(String string2) {
			work.setString2(string2);
		}


		@Override
		public void setString3(String string3) {
			work.setString3(string3);
		}


		@Override
		public void setString4(String string4) {
			work.setString4(string4);
		}


		@Override
		public void setWorkItemName(String workItemName) {
			work.setWorkItemName(workItemName);
		}


		@Override
		public Date getCreationTime() {
			return work.getCreationTime();
		}


		@Override
		public Long getCreator() {
			return work.getCreator();
		}


		@Override
		public Date getModificationTime() {
			return work.getModificationTime();
		}


		@Override
		public Long getModifier() {
			return work.getModifier();
		}


		@Override
		public void setCreationTime(Date creationTime) {
			work.setCreationTime(creationTime);
		}


		@Override
		public void setCreator(Long creator) {
			work.setCreator(creator);
		}


		@Override
		public void setModificationTime(Date modificationTime) {
			work.setModificationTime(modificationTime);
		}


		@Override
		public void setModifier(Long modifier) {
			work.setModifier(modifier);
		}
		
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#findWorks(cn.redflagsoft.base.bean.RFSMainObject, java.lang.Short, java.lang.Short, java.lang.Byte)
	 */
	public List<Work> findWorks(RFSObjectable object, Integer taskType, Integer workType, Byte status) {
		Assert.notNull(object, "对象不能为空");
		Assert.notNull(object.getId(), "对象ID不能为空");

		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("creationTime"));
		
		//object
		Logic logic = Restrictions.logic(Restrictions.eq("refObjectId", object.getId()))
			.and(Restrictions.eq("refObjectType", object.getObjectType()));
		//taskType
		if(taskType != null){
			TaskDef taskDef = taskDefProvider.getTaskDef(taskType);
			if(taskDef == null){
				log.error("找不到TaskDef：" + taskType);
				return Collections.emptyList();
			}
			Set<Integer> workTypes = taskDef.getWorkTypes();
			if(workTypes == null || workTypes.size() == 0){
				log.warn("没有taskType(" + taskType + ")对象的workType。");
				return Collections.emptyList();
			}
			logic.and(Restrictions.in("type", workTypes));
		}
		//workType
		if(workType != null){
			logic.and(Restrictions.eq("type", workType));
		}
		//status
		if(status != null){
			logic.and(Restrictions.eq("status", status));
		}
		filter.setCriterion(logic);
		return workDao.find(filter);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#findWorks(cn.redflagsoft.base.bean.RFSBizObject, java.lang.Short, java.lang.Short, java.lang.Byte)
	 */
	public List<Work> findWorks(RFSItemable object, Integer taskType, Integer workType, Byte status) {
		Assert.notNull(object, "对象不能为空");
		Assert.notNull(object.getId(), "对象ID不能为空");
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.desc("w.creationTime"));
		
		//object
		Logic logic = Restrictions.logic(Restrictions.eq("e.objectId", object.getId()))
			.and(Restrictions.eq("e.objectType", object.getObjectType()));
		
		//taskType
		if(taskType != null){
			TaskDef taskDef = taskDefProvider.getTaskDef(taskType);
			if(taskDef == null){
				log.error("找不到TaskDef：" + taskType);
				return Collections.emptyList();
			}
			Set<Integer> workTypes = taskDef.getWorkTypes();
			if(workTypes == null || workTypes.size() == 0){
				log.warn("没有taskType(" + taskType + ")对象的workType。");
				return Collections.emptyList();
			}
			logic.and(Restrictions.in("w.type", workTypes));
		}
		//workType
		if(workType != null){
			logic.and(Restrictions.eq("w.type", workType));
		}
		//status
		if(status != null){
			logic.and(Restrictions.eq("w.status", status));
		}
		
		
//		select distinct w from Work w, RFSEntityObject o where w.sn=o.workSN and o.objectId=? and o.objectType=?
//		String sql = "";
//		if(taskType != null){
//			sql += " and w.type in (?)";
//		}
//		if(workType != null){
//			sql += " and w.type=?";
//		}
		filter.setCriterion(logic);
		return entityObjectToWorkDao.findWorks(filter);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#updateWorkEntityObjects(cn.redflagsoft.base.bean.Work, cn.redflagsoft.base.bean.RFSEntityObject[])
	 */
	public Work updateWorkEntityObjects(Work work, RFSEntityObject... objects) {
		long workSN = work.getSn();
		for(RFSEntityObject object: objects){
			//System.out.println(workSN + "=========================" + object);
			saveEntityObjectToWorkIfRequired(workSN, object.getId(), /*(object instanceof RFSObjectable) ? 0 :*/ object.getObjectType());
		}
		return work;
	}
	
	/**
	 * 保存
	 * @param workSN
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	protected EntityObjectToWork saveEntityObjectToWorkIfRequired(long workSN, long objectId, int objectType) {
		Logic logic = Restrictions.logic(Restrictions.eq("workSN", workSN))
				.and(Restrictions.eq("objectId", objectId))
				.and(Restrictions.eq("objectType", objectType));
		if(log.isDebugEnabled()){
			log.debug(String.format("workSN=%s and objectId=%s and objectType=%s", workSN, objectId, objectType));
		}
		ResultFilter filter = new ResultFilter(logic, null);
		int count = entityObjectToWorkDao.getCount(filter);
		log.debug("查找服务条件的记录数（EntityObjectToWork）：" + count);
		if(count == 0){
			EntityObjectToWork objectWork = new EntityObjectToWork(workSN, objectId, objectType);
			return entityObjectToWorkDao.save(objectWork);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#hangWork(java.lang.Long)
	 */
	public void hangWork(Long workSn) {
		hangWork(workDao.get(workSn), null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#wakeWork(java.lang.Long)
	 */
	public void wakeWork(Long workSn) {
		wakeWork(workDao.get(workSn), null);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#terminateWork(java.lang.Long, java.lang.Byte)
	 */
	public void terminateWork(Long workSn, Byte result) {
		terminateWork(workDao.get(workSn), result, null);
	}
	
	
	protected void endWork(Work work){
		if(work.getEndTime() == null){
			work.setEndTime(new Date());
		}
		work.setTimeUsed((short)(DateUtil.getTimeUsed(work.getBeginTime(),work.getEndTime(),(byte)5)-work.getTimeHang()-work.getTimeDelay()));
	
		clueService.updateClueStatus((byte)3,work.getId(), Work.WORK_STATUS_TERMINATE);		
		
		workDao.update(work);
		//直接调用更新activeWorkSN的方法。
		//taskDao.updateActiveWorkSN(work.getTaskSN(), 0L);
		Task task = taskDao.get(work.getTaskSN());
		if(task != null){
			log.debug("更新ActiveWorkSN：" + task);
			task.setActiveWorkSN(0L);
			taskDao.update(task);
		}
		bizTrackNodeInstanceService.updateBizTrackNodeStatus(CATEGORY_WORK, work.getSn(), Work.WORK_STATUS_TERMINATE);	
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#cancelWork(cn.redflagsoft.base.bean.Work)
	 */
	public void cancelWork(Work work) {
		work.setStatus(Work.STATUS_取消);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#avoidWork(cn.redflagsoft.base.bean.Work)
	 */
	public void avoidWork(Work work) {
		work.setStatus(Work.STATUS_免办);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#rejectWork(cn.redflagsoft.base.bean.Work)
	 */
	public void rejectWork(Work work) {
		work.setStatus(Work.STATUS_驳回);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#stopWork(cn.redflagsoft.base.bean.Work)
	 */
	public void stopWork(Work work) {
		work.setStatus(Work.STATUS_中止);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#transferWork(cn.redflagsoft.base.bean.Work)
	 */
	public void transferWork(Work work) {
		work.setStatus(Work.STATUS_转出);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#withdrawWork(cn.redflagsoft.base.bean.Work)
	 */
	public void withdrawWork(Work work) {
		work.setStatus(Work.STATUS_撤回);
		endWork(work);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkService#undoWork(cn.redflagsoft.base.bean.Work)
	 */
	public void undoWork(Work work) {
		work.setStatus(Work.STATUS_待办);
		endWork(work);
	}
	public void workInvalid(RFSObject rfsObject) {
		workDao.workInvalid(rfsObject);
	}
}
