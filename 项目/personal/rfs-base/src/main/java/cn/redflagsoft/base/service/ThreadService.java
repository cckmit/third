package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Thread;

public interface ThreadService {
	
	public Thread getActiveThread(Long objectID,int type,Long affairID);
	
	public Long  getActiveThreadSN(Long objectID,int type,Long affairID);
		
	public Thread createThread(Long objectID,int type,Long affairID);
	
	public Thread createThread(Long objectID,int type,Long affairID,Long routeID);
	
	public Thread prepareThread(Long objectID, int type, Long affairID,Long routeID);
	
	public Thread prepareThread(Long objectID,int type,Long affairID);
	
	public Thread commitThread(Thread thread);
	
	public void terminateThread(Thread thread);
	
	public void terminateThread(Long threadID);
	
	public Thread createThread(Thread thread);
	
	public Thread updateThread(Thread thread);
	
	public int deleteThread(Thread thread);
}
