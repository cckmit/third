package cn.redflagsoft.base.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.service.SmsgManager;



/**
 * 只在一个节点中运行即可。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MessageQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String MESSAGE_JOB_ENABLED = "MessageQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(MessageQuartzJob.class);
	private SmsgManager smsgManager;

	/**
	 * @return the smsgManager
	 */
	public SmsgManager getSmsgManager() {
		return smsgManager;
	}

	/**
	 * @param smsgManager the smsgManager to set
	 */
	public void setSmsgManager(SmsgManager smsgManager) {
		this.smsgManager = smsgManager;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);		
	}

	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0) throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()){
			if(!AppsGlobals.getProperty(MESSAGE_JOB_ENABLED, true)){
				log.debug("MessageQuartzJob 未启用，配置运行时属性 " + MESSAGE_JOB_ENABLED + "=true 启用该Job。");
				return;
			}
			
			log.debug("执行Job：" + this);
			int msgs = smsgManager.sendAvailableMsgs();
			log.debug("执行完毕，共处理Smsg " + msgs + " 条 -> " + this);
		}
	}
}
