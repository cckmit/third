package org.opoo.apps.scheduling.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.cache.CacheFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class SingleNodeQuartzJobBean extends QuartzJobBean {
	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	protected final void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if(CacheFactory.isSeniorClusterMember()){
			if(log.isDebugEnabled()){
				log.debug("在主节点中执行计划任务：" + this);
			}
			executeInSeniorClusterMember(context);
		}
	}
	
	/**
	 * 在主节点中执行任务。
	 * 
	 * @param context
	 * @throws JobExecutionException
	 */
	protected abstract void executeInSeniorClusterMember(JobExecutionContext context) throws JobExecutionException ;
}
