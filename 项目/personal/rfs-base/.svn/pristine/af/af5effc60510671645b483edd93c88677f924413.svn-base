package cn.redflagsoft.base.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.HolidayService;


public class SystemRuningRemindQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String SYSTEM_RUNING_REMIND_JOB_ENABLED = "SystemRuningRemindQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(SystemRuningRemindQuartzJob.class);
	private HolidayService holidayService;
	private EventMsgService eventMsgService;
	private int runType=0;
	
	
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
				&& AppsGlobals.getProperty(SYSTEM_RUNING_REMIND_JOB_ENABLED, true)){
			log.debug("执行Job：" + this);
			boolean isHoliday=holidayService.isHoliday(new Date());
//			System.out.println("runType:"+runType);
//			System.out.println("isHoliday:"+isHoliday);
			
			if(( runType == 0 &&( isHoliday == false ) )||( runType == 1 &&( isHoliday == true))||runType==2){
				List<EventMsg> eventMsgs=eventMsgService.findEventMsgCfg(1002, 0L, 202L);
				Map<String, Object> tmp = new HashMap<String, Object>();
				SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowStr = sp.format(new Date());
				
				tmp.put("now", nowStr);
				for(EventMsg em:eventMsgs){
					if(em.getIsCreateSms() == EventMsg.IS_TRUE){
						eventMsgService.createSmsgByEventMsg(em,0L,null,tmp);
					}
				}	
			}	
			log.debug("执行完毕：" + this);
		}else{
			
		}		
	}
}
