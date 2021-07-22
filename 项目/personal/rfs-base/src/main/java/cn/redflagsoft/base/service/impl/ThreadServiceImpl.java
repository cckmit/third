package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.Job;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.Thread;
import cn.redflagsoft.base.dao.MatterAffairDao;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.ThreadDao;
import cn.redflagsoft.base.service.BizRouteService;
import cn.redflagsoft.base.service.BizTrackNodeService;
import cn.redflagsoft.base.service.JobService;
import cn.redflagsoft.base.service.ThreadService;

public class ThreadServiceImpl implements ThreadService {
	public static final byte STATUS = 1;
    private ThreadDao threadDao;
    private BizRouteService bizRouteService;
    private JobService jobService;
    private MatterAffairDao matterAffairDao;
    private BizTrackNodeService bizTrackNodeService;
    private ObjectsDao objectsDao;

	
	
	public void setMatterAffairDao(MatterAffairDao matterAffairDao) {
		this.matterAffairDao = matterAffairDao;
	}
	public void setBizTrackNodeService(BizTrackNodeService bizTrackNodeService) {
		this.bizTrackNodeService = bizTrackNodeService;
	}

	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public void setThreadDao(ThreadDao threadDao) {
		this.threadDao = threadDao;
	}

	public void setBizRouteService(BizRouteService bizRouteService) {
		this.bizRouteService = bizRouteService;
	}

	public Thread getActiveThread(Long objectID, int type,Long affairID) {
		Criterion c = Restrictions.logic(Restrictions.eq("objectID", objectID))
		.and(Restrictions.eq("affairID", affairID)).and(
				Restrictions.eq("type",type)).and(
				Restrictions.lt("status",(byte)9));
         return threadDao.get(c);
	}

	public Long getActiveThreadSN(Long objectID, int type,Long affairID) {
		Thread thread= getActiveThread(objectID,type,affairID);
		if(thread==null){
			return null;
		}
		return thread.getSn();
	}

	public Thread prepareThread(Long objectID, int type, Long affairID) {
		Byte category=1;		
		BizRoute bizroute=bizRouteService.getBizRoute(category,type,affairID);
		if(bizroute==null)
		{
			return null;						
		}		
		return prepareThread(objectID,type,affairID,bizroute.getId());
	}
	
	public Thread prepareThread(Long objectID, int type, Long affairID,Long routeID) {
//		Byte category=1;	
		byte bizAction = 1;
	    Job job = jobService.createJob(0L, type, routeID);
	    if(job==null){
	    	return null;
	    }
	    Long jobSn = job.getSn();
		Thread thread=new Thread();
		thread.setObjectID(objectID);
		thread.setType(type);
		thread.setAffairID(affairID);
		thread.setBizRoute(routeID);		
		thread.setStatus(STATUS);
		thread.setCreationTime(new Date());
		thread.setJobSN(jobSn);
		thread.setBizTrack(job.getBizTrack());
		//把job关联到相关的线索中,此处category=2
	    List<MatterAffair> relatedAffairList=matterAffairDao.findAffairs((byte)2,affairID,bizAction);
	    for (int k=0;k<relatedAffairList.size();k++) {  
	    	MatterAffair relatedAffair=relatedAffairList.get(k);
	    	if (relatedAffair.getAction()==1){
	    		Long relatedObjectID = objectsDao.getRelatedObjectID(objectID, 1);
			    if (relatedObjectID!=null) {
			    	Thread relatedThread=getActiveThread(relatedObjectID,relatedAffair.getBizType(),relatedAffair.getAffairID());
			        if (relatedThread!=null){
			        	bizTrackNodeService.deriveBizTrackNode((byte)1,relatedAffair.getRefType(),jobSn,relatedThread.getBizTrack());
			        }
			    }
	    	}
	    }	
		return thread;
	}
	public Thread commitThread(Thread thread) {
		if(thread==null){
			return null;
		}
		return threadDao.save(thread);		
	}

	public void terminateThread(Thread thread) {
		if(thread==null) {
			return;
		}
		terminateThread(thread.getId());
	}
	
	public Thread createThread(Long objectID, int type, Long affairID) {
		Thread thread=prepareThread(objectID,type,affairID);		
		return commitThread(thread);
	}
    
	public Thread createThread(Long objectID,int type,Long affairID,Long routeID){
		Thread thread=prepareThread(objectID,type,affairID,routeID);
		return commitThread(thread);
	}
	public void terminateThread(Long threadID) {
		if(threadID==null){
			return ;
		}
		Thread newThread=threadDao.get(threadID);
		newThread.setStatus((byte)9);
		threadDao.update(newThread);
	}
	public Thread createThread(Thread thread) {
		return threadDao.save(thread);
	}
	public int deleteThread(Thread thread) {
		return threadDao.delete(thread);
	}
	public Thread updateThread(Thread thread) {
		return threadDao.update(thread);
	}

}
