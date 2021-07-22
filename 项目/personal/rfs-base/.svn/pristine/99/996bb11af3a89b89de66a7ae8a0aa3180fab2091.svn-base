package cn.redflagsoft.base.scheduling;

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


public class CreateRemindSendingMsgForAdminQuartzJob extends SingleNodeQuartzJobBean implements InitializingBean {
	public static final String CREATE_REMIND_SENDINGMSG_FOR_ADMIN_JOB_ENABLED = "CreateRemindSendingMsgForAdminQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(CreateRemindSendingMsgForAdminQuartzJob.class);
	private EventMsgService eventMsgService;
	/**
	 * @return the eventMsgService
	 */
	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	/**
	 * @param eventMsgService the eventMsgService to set
	 */
	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public void afterPropertiesSet() throws Exception {
		log.info("启动Job：" + this);
	}

	@Override
	protected void executeInSeniorClusterMember(JobExecutionContext arg0)
			throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()
				&& AppsGlobals.getProperty(CREATE_REMIND_SENDINGMSG_FOR_ADMIN_JOB_ENABLED, true)){
			log.debug("执行Job：" + this);
			
			//TODO
			int count= 0;//查询待发送消息数量 sendingMsgDao.getConfirmSendingMsgCount();
			if(count > 0){
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("${SendingMsg_Count}",String.valueOf(count));
				List<EventMsg> eventMsgs=eventMsgService.findEventMsgCfg(1001, 0L, 201L);
				for(EventMsg em:eventMsgs){
					if(em.getIsCreateMsg() == EventMsg.IS_TRUE){
						eventMsgService.createSmsgByEventMsg(em,0L,null,tmp);
					}
				}	
			}
			log.debug("执行完毕：" + this);
		}else{
			
		}
	}
}
