package cn.redflagsoft.base.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.service.RiskService;


/**
 * 定时计算Risk。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RiskQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String RISK_JOB_ENBALED = "RiskQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(RiskQuartzJob.class);
	private RiskService riskService;

	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	protected void executeInSeniorClusterMember(JobExecutionContext arg0) throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()){
			if(!AppsGlobals.getProperty(RISK_JOB_ENBALED, true)){
				log.debug("RiskQuartzJob 未启用，配置运行时属性 " + RISK_JOB_ENBALED + "=true 启用该Job。");
				return;
			}
			
			log.debug("执行Job：" + this);
//			riskService.calculateAllRunningRisks();
			riskService.calculateAllRunningRisksInThreads();
			log.debug("执行完毕：" + this);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);		
	}
}
