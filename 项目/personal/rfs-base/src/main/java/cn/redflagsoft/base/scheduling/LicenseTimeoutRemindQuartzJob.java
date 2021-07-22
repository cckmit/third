package cn.redflagsoft.base.scheduling;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.AppsLicense;
import org.opoo.apps.license.AppsLicenseHolder;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.HolidayService;
import cn.redflagsoft.base.util.DateUtil;

/**
 * license到期提醒
 * @author Administrator
 *
 */
public class LicenseTimeoutRemindQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String LICENSE_TIMEOUT_REMIND_JOB_ENABLED = "LicenseTimeoutRemindQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(LicenseTimeoutRemindQuartzJob.class);
	private HolidayService holidayService;
	private EventMsgService eventMsgService;

    private int alertDays1;
    private int alertDays2;
    private int alertDays3;
    private int alertDays4;
    private int alertDays5;
    
	public int getAlertDays1() {
        return alertDays1;
    }

    public void setAlertDays1(int alertDays1) {
        this.alertDays1 = alertDays1;
    }

    public int getAlertDays2() {
        return alertDays2;
    }

    public void setAlertDays2(int alertDays2) {
        this.alertDays2 = alertDays2;
    }

    public int getAlertDays3() {
        return alertDays3;
    }

    public void setAlertDays3(int alertDays3) {
        this.alertDays3 = alertDays3;
    }

    public int getAlertDays4() {
        return alertDays4;
    }

    public void setAlertDays4(int alertDays4) {
        this.alertDays4 = alertDays4;
    }

    public int getAlertDays5() {
        return alertDays5;
    }

    public void setAlertDays5(int alertDays5) {
        this.alertDays5 = alertDays5;
    }

	
	
	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public HolidayService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);
	}

	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0)
			throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(LICENSE_TIMEOUT_REMIND_JOB_ENABLED, true)){
			log.debug("执行Job：" + this);
			Date thisDate = new Date();//DateUtil.getCurrentDate();
//			Holiday h=holidayService.getHoliday(thisDate);
//			boolean isHoliday=false;
//			if(h != null){
//				isHoliday=true;
//			}else{
//				isHoliday=false;				
//			}
			
//			boolean isHoliday = h != null;
			
		    AppsLicense lic=AppsLicenseHolder.getAppsLicense();
		    Date expDate=lic.getExpirationDate();
		    int interval=DateUtil.daysBetween(thisDate, expDate);
		    if(interval==alertDays1 || interval==alertDays2 || interval==alertDays3 || interval==alertDays4 || interval==alertDays5 ){
    			List<EventMsg> eventMsgs=eventMsgService.findEventMsgCfg(1002, 0L, 203L);
    			for(EventMsg em:eventMsgs){
    				if(em.getIsCreateSms()==EventMsg.IS_TRUE){
    					eventMsgService.createSmsgByEventMsg(em,0L,null,null);
    				}
    			}	
		    }
			log.debug("执行完毕：" + this);
		}else{
			
		}		
	}
}
