package cn.redflagsoft.base.scheduling;


import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.service.RiskLogService;
import cn.redflagsoft.base.util.DateUtil;

/**
 * 
 *
 */
public class RiskRemindCopySendQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String RISK_REMIND_COPY_SEND_JOB_ENABLED = "RiskRemindCopySendQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(RiskRemindCopySendQuartzJob.class);
	private RiskLogService riskLogService;

	public RiskLogService getRiskLogService() {
		return riskLogService;
	}

	public void setRiskLogService(RiskLogService riskLogService) {
		this.riskLogService = riskLogService;
	}

	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);
	}

	protected void executeInSeniorClusterMember(JobExecutionContext arg0)
			throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(RISK_REMIND_COPY_SEND_JOB_ENABLED, true)){
			log.debug("执行Job：" + this);
			Date now = new Date();
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, -1 );
			Date yesterday = c.getTime();
			
			Date mStart;
			if(now.after(DateUtil.setAndGetTime(now,8,40,0))){ 
				if(now.after(DateUtil.setAndGetTime(now,14,30,0))){
					if(now.after(DateUtil.setAndGetTime(now,17,50,0))){
						mStart=DateUtil.setAndGetTime(now,14,30,0);
					}else{
						mStart=DateUtil.setAndGetTime(now,8,40,0);
					}
				}else{
					mStart=DateUtil.setAndGetTime(yesterday,17,50,0);
				}
			}else{
				mStart=DateUtil.setAndGetTime(yesterday,17,50,0);
			}
			riskLogService.riskRemindCopySend(mStart, now);
			log.debug("执行完毕：" + this);
		}else{
			//ignore
		}
	}
}
