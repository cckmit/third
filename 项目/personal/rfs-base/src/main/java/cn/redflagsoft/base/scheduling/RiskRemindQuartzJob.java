package cn.redflagsoft.base.scheduling;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import cn.redflagsoft.base.bean.Holiday;
import cn.redflagsoft.base.service.HolidayService;
import cn.redflagsoft.base.service.RiskService;


public class RiskRemindQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String RISK_REMIND_JOB_ENABLED = "RiskRemindQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(RiskRemindQuartzJob.class);
	private RiskService riskService;
	private HolidayService holidayService;
	private int runType=0;
	private int days=3;
	private int nowRiskRemindEnabled=0;
	private int advanceRiskRemindEnabled=0;
	
	
	public int getNowRiskRemindEnabled() {
		return nowRiskRemindEnabled;
	}

	public void setNowRiskRemindEnabled(int nowRiskRemindEnabled) {
		this.nowRiskRemindEnabled = nowRiskRemindEnabled;
	}

	public int getAdvanceRiskRemindEnabled() {
		return advanceRiskRemindEnabled;
	}

	public void setAdvanceRiskRemindEnabled(int advanceRiskRemindEnabled) {
		this.advanceRiskRemindEnabled = advanceRiskRemindEnabled;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	public HolidayService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	public int getRunType() {
		return runType;
	}

	public void setRunType(int runType) {
		this.runType = runType;
	}

	

	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);
	}

	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0)
			throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(RISK_REMIND_JOB_ENABLED, true)){
			log.debug("执行Job：" + this);
			Holiday h=holidayService.getHoliday(new Date());
			boolean isHoliday=false;
			
			if(h!=null){
				isHoliday=true;
			}else{
				isHoliday=false;
				
			}
			if(( runType == 0 &&( isHoliday == false ) )||( runType == 1 &&( isHoliday == true))||runType==2){
					if(nowRiskRemindEnabled==1){
						riskService.gradeChangeRemind(0);
					}
					if(advanceRiskRemindEnabled==1){
						riskService.gradeChangeRemind(days);
					}
				
			}
			log.debug("执行完毕：" + this);
		}else{
			
		}
		
	}

}
