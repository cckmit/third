package cn.redflagsoft.base.listener;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.event.RiskLogEvent;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.CautionService;


/**
 * Risk结束时，修改相关Caution的整改信息。
 *
 */
public class CautionRectificationListener implements ApplicationListener{

	private static final Log log = LogFactory.getLog(CautionRectificationListener.class);
	
	private CautionService cautionService;
	
	public CautionService getCautionService() {
		return cautionService;
	}

	public void setCautionService(CautionService cautionService) {
		this.cautionService = cautionService;
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof RiskLogEvent){
			Risk risk = ((RiskLogEvent) event).getRisk();
			long riskId = risk.getId();
			int type = ((RiskLogEvent) event).getType();
			// 林总说暂时只做“办结”,所以 “暂停”暂时不做，故注释掉了
			//type == RiskLogEvent.SCALE_CHANGE ||
			if(type == RiskLogEvent.TERMINATE){
				ResultFilter filter = ResultFilter.createEmptyResultFilter();
				filter.setCriterion(Restrictions.eq("riskId", riskId));
				filter.setOrder(Order.desc("creationTime"));
				List<Caution> list = cautionService.findObjects(filter);
				if(list != null && !list.isEmpty()){
					Caution caution = list.get(0);
					if(log.isDebugEnabled()){
						log.debug("查询到监察（" + riskId + "）相关警示信息：" + caution);
					}
					
					if(type == RiskLogEvent.SCALE_CHANGE){
						caution.setRectificationType(Caution.RECTIFICATION_TYPE__延期);
					}
					if(type == RiskLogEvent.TERMINATE){
						caution.setRectificationType(Caution.RECTIFICATION_TYPE__办结);
					}
					caution.setRectificationDesc("该监察事项于" + AppsGlobals.formatDate(new Date()) + "办结。");
					caution.setRectificationActualTime(new Date());
					Clerk clerk = UserClerkHolder.getClerk();
					caution.setRectificationClerkId(clerk.getId());
					caution.setRectificationClerkName(clerk.getName());
					caution.setRectificationOrgId(clerk.getEntityID());
					caution.setRectificationOrgName(clerk.getEntityName());
					caution.setRectificationTime(new Date());
					cautionService.updateObject(caution);
				}
			}
		}
	}
}
