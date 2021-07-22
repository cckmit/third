package org.opoo.apps.scheduling.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;
import org.quartz.Scheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.SchedulerAccessor;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class SchedulerConfigurator extends SchedulerAccessor implements InitializingBean{
	private static final Log log = LogFactory.getLog(SchedulerConfigurator.class);
	private Scheduler scheduler;

	public SchedulerConfigurator() {
		super();
	}


	public SchedulerConfigurator(Scheduler scheduler) {
		super();
		this.scheduler = scheduler;
	}

	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scheduler);
		
		log.debug("afterPropertiesSet...");
		//System.out.println(this + " ---------------->>");
		registerListeners();
		registerJobsAndTriggers();
	}


	/**
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}


	@Override
	protected Scheduler getScheduler() {
		return scheduler;
	}

}
