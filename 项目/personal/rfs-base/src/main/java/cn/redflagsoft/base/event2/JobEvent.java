package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Job;


/**
 * JobÊÂ¼þ¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class JobEvent extends Event<JobEvent.Type, Job> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7170097122334815207L;

	private Job oldJob;
	/**
	 * @param eventType
	 * @param source
	 */
	public JobEvent(Type eventType, Job source) {
		super(eventType, source);
	}
	
	public JobEvent(Type eventType, Job oldJob, Job newJob){
		super(eventType, newJob);
		this.oldJob = oldJob;
	}
	
	public Job getOldJob(){
		return oldJob;
	}
	public Job getNewJob(){
		return getSource();
	}

	public static enum Type{
		CREATED, UPDATED, DELETED;
	}
}
