package cn.redflagsoft.base.scheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.service.CautionService;
import cn.redflagsoft.base.service.EventMsgService;

public class CreateCautionRemindSmsgQueartzJob extends SingleNodeQuartzJobBean implements InitializingBean{
	public static final String CREATE_CAUTION_REMIND_SMSG_JOB_ENABLED = "CreateCautionRemindSmsgQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(CreateCautionRemindSmsgQueartzJob.class);
	private EventMsgService eventMsgService;	
	private CautionService cautionService;

	public CautionService getCautionService() {
		return cautionService;
	}

	public void setCautionService(CautionService cautionService) {
		this.cautionService = cautionService;
	}

	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

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
				&& AppsGlobals.getProperty(CREATE_CAUTION_REMIND_SMSG_JOB_ENABLED, false)){
			log.debug("执行Job：" + this);
				List<EventMsg> eventMsgs=eventMsgService.findEventMsgCfg(1002, 0L, 201L);
				Map<String, Object> tmp = createCautionRemindSmsgParameter();
				if(tmp != null){
					if(eventMsgs != null && !eventMsgs.isEmpty()){
						for(EventMsg em:eventMsgs){
							if(em.getIsCreateSms() == EventMsg.IS_TRUE){
								eventMsgService.createSmsgByEventMsg(em,0L,null,tmp);
							}
						}
					}
				}
		}
	}
	
	private Map<String, Object> createCautionRemindSmsgParameter(){
		// 查询 caution （已生成，未处理）的数量。
		ResultFilter filter  = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("bizStatus",(byte)1);
		filter.setCriterion(eq);
		List<Caution> cautionList = cautionService.findObjects(filter);
		int whiteCount = 0;					//提醒
		int blueCount = 0;					//预警
		int yellowCount = 0;				//黄牌
		int redCount = 0;					//红牌
		if(cautionList != null && !cautionList.isEmpty()){
			for (Caution caution : cautionList) {
				byte grade = caution.getGrade();
				switch (grade) {
				case Risk.GRADE_WHITE:
					whiteCount++;
					break;
				case Risk.GRADE_BLUE:
					blueCount++;
					break;
				case Risk.GRADE_YELLOW:
					yellowCount++;
					break;
				case Risk.GRADE_RED:
					redCount++;
					break;
				}	
			}
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("blueCount", blueCount);
			tmp.put("yellowCount", yellowCount);
			tmp.put("redCount", redCount);
			return tmp;
		}
		return null;
	}
}
