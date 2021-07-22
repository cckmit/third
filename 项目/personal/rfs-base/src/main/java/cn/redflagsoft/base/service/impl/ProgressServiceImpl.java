/*
 * $Id: ProgressServiceImpl.java 6267 2013-06-28 02:51:50Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.util.DateUtils;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.YearProgress;
import cn.redflagsoft.base.dao.ProgressDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.IssueService;
import cn.redflagsoft.base.service.ProgressService;


/**
 * 
 *
 */
public class ProgressServiceImpl implements ProgressService {
	private static final Log log = LogFactory.getLog(ProgressServiceImpl.class);
	
	
	public static final DateFormat titleFormat = new SimpleDateFormat("yyyy��MM��dd�ս��ȱ���");

	private ProgressDao progressDao;
	private IssueService issueService;
	

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public ProgressDao getProgressDao() {
		return progressDao;
	}

	public void setProgressDao(ProgressDao progressDao) {
		this.progressDao = progressDao;
	}

	public Progress createProgress(RFSObject object, String progress) {
		Progress tempprogress = new Progress();
		tempprogress.setRefObjectId(object.getId());
		tempprogress.setRefObjectName(object.getName());
		tempprogress.setRefObjectType(object.getObjectType());
		tempprogress.setDescription(progress);
		return saveProgress(tempprogress);
	}

	public Progress createProgress(RFSObject object, Progress progress) {
		progress.setRefObjectId(object.getId());
		progress.setRefObjectName(object.getName());
		progress.setRefObjectType(object.getObjectType());
		return saveProgress(progress);
	}
	
	/**
	 * 
	 */
	public Progress getLastProgress(RFSObject object) {
		
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1); // 0����0��ʼ
																				// ȡ1����¼
		SimpleExpression eq = Restrictions.eq("refObjectId", object.getId());
		SimpleExpression eq2 = Restrictions.eq("refObjectType", object.getObjectType());

		Logic logic = Restrictions.logic(eq).and(eq2);

		filter.setOrder(Order.desc("reportTime").add(Order.desc("creationTime"))); // ��������
		filter.setCriterion(logic);

		List<Progress> list = progressDao.find(filter);

		if (!list.isEmpty()) {
			return list.get(0);
		}else{
			Progress p = new Progress();
			p.setRefObjectId(object.getId());
			p.setRefObjectName(object.getName());
			p.setRefObjectType(object.getObjectType());
			p.setDescription("��");
			p.setTitle("��");
			return p;
		}
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	public List<Progress> findProgresses(ResultFilter filter){
		return progressDao.find(filter);
	}
	
	public Progress getLastProgressV3(RFSEntityObject object){
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1); // 0����0��ʼ
		// ȡ1����¼
		SimpleExpression eq = Restrictions.eq("refObjectId", object.getId());
		SimpleExpression eq2 = Restrictions.eq("refObjectType", object.getObjectType());
		SimpleExpression eq3 = Restrictions.eq("status", Progress.STATUS_OK);
		Logic logic = Restrictions.logic(eq).and(eq2).and(eq3);
		
		filter.setOrder(Order.desc("belongTime").add(Order.desc("modificationTime"))); // ��������
		filter.setCriterion(logic);
		
		List<Progress> list = progressDao.find(filter);
		if(list.size() > 0){
			return list.iterator().next();
		}
		return null;
	}
	
	
	/**
	 * 
	 */
	public Progress saveProgress(Progress progress) {
//		if (StringUtils.isBlank(progress.getTitle())) {
//			progress.setTitle(titleFormat.format(new Date()));
//		}
		Assert.notNull(progress.getTitle(), "title");
		
		Clerk clerk = UserClerkHolder.getClerk();

		progress.setOrgId(clerk.getEntityID());
		progress.setOrgName(clerk.getEntityName());

		progress.setReporterId(clerk.getId());
		progress.setReporterName(clerk.getName());

		if (progress.getReportTime() == null) {
//			Date day = DateUtils.toStartOfDay(new Date());
			progress.setReportTime(new Date());
		}
		progress.setReportTime(DateUtils.toStartOfDay(progress.getReportTime()));
		
		
		if(log.isDebugEnabled()){
			log.debug("���ȱ��棺" + progress.getId());
			log.debug("::" + progress.getDescription());
		}
		
		return progressDao.save(progress);
	}
	
	@Queryable
	@Deprecated
	public List<Progress> findProjectYearProgressList(ResultFilter filter){
		
		List<Progress> list = progressDao.find(filter);
		List<Progress> result = new ArrayList<Progress>();
//		Calendar cal=Calendar.getInstance();
//		int newY = cal.get(Calendar.YEAR);
		if(list.size() != 0) {
			for(Progress p: list) {
//				cal.setTime(p.getReportTime());
//				int oldY = cal.get(Calendar.YEAR);
			//8-11,SMC
			//	if(newY == oldY) {
					result.add(p);
			//	}
				//TODO
//				System.out.println(newY + ", " + oldY);
			}
		}
		return result;
	}

	public void removeProgress(Long id) {
		progressDao.remove(id);
	}
	
	
	public Progress getProgress(int objectType, long objectID, int year, int month){
		Assert.isTrue(month >= 1 && month <= 12, "�·ݲ���ȷ��");
		
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, 1);
		DateUtils.toStartOfDay(c);
		Date start = c.getTime();
		DateUtils.monthsAfter(1, c);
		Date end = c.getTime();
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("refObjectId", objectID);
		SimpleExpression eq2 = Restrictions.eq("refObjectType", objectType);
		SimpleExpression ge = Restrictions.ge("reportTime", start);
		SimpleExpression lt = Restrictions.lt("reportTime", end);
		filter.setCriterion(Restrictions.logic(eq).and(eq2).and(lt).and(ge));
		
		List<Progress> list = progressDao.find(filter);
		if(list.size() > 1){
			throw new RuntimeException("��ѯ�����Ψһ");
		}else if(list.size() == 1){
			return list.iterator().next();
		}
		return null;
	}
	
	public YearProgress getYearProgress(int objectType, long objectID, int year){
		
		Calendar c = Calendar.getInstance();
		c.set(year,0, 1);
		DateUtils.toStartOfDay(c);
		Date start = c.getTime();
		
		Calendar c2 = Calendar.getInstance();
		c2.set(year+1,0, 1);
		DateUtils.toStartOfDay(c2);
		Date end = c2.getTime();
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("refObjectId", objectID);
		SimpleExpression eq2 = Restrictions.eq("refObjectType", objectType);
		SimpleExpression ge = Restrictions.ge("reportTime", start);
		SimpleExpression lt = Restrictions.lt("reportTime", end);
		filter.setCriterion(Restrictions.logic(eq).and(eq2).and(lt).and(ge));
		
		List<Progress> list = progressDao.find(filter);
		
		YearProgress yearProgress = new YearProgress();
		yearProgress.setYear(year);
		yearProgress.setMonthProgressList(list);
		return yearProgress;
	}

	public Progress getProgress(Long progressId) {
		return progressDao.get(progressId);
	}

	public Progress updateProgress(Progress progress) {
		return progressDao.update(progress);
	}

}
