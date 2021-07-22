package cn.redflagsoft.base.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.CautionDao;
import cn.redflagsoft.base.event2.TaskEvent;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;

public class TaskDeleteEventListener implements EventListener<TaskEvent>{
	private static final Log log = LogFactory.getLog(TaskDeleteEventListener.class);
	private CautionDao cautionDao;
	private SchemeManager schemeManager;
	private String cautionInvalidWorkSchemeName = "cautionInvalidWorkScheme";

	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}
	
	public CautionDao getCautionDao() {
		return cautionDao;
	}

	public void setCautionDao(CautionDao cautionDao) {
		this.cautionDao = cautionDao;
	}
	/**
	 * @param cautionInvalidWorkSchemeName the cautionInvalidWorkSchemeName to set
	 */
	public void setCautionInvalidWorkSchemeName(String cautionInvalidWorkSchemeName) {
		this.cautionInvalidWorkSchemeName = cautionInvalidWorkSchemeName;
	}

	public void handle(TaskEvent event) {
		if(event.getType() == TaskEvent.Type.DELETED){
			Task task = event.getSource();
			SimpleExpression eq = Restrictions.eq("objType", task.getObjectType());
			SimpleExpression eq2 = Restrictions.eq("objId",task.getSn());
			//SimpleExpression eq3 = Restrictions.eq("objCode",task.getCode());
			Logic and = Restrictions.logic(eq).and(eq2);//.and(eq3);
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			filter.setCriterion(and);
			
			List<Long> cautionIds = cautionDao.findIds(filter);
			if (cautionIds != null && !cautionIds.isEmpty()) {
				for (Long cautionId : cautionIds) {
					try {
						Scheme scheme2 = getSchemeManager().getScheme(cautionInvalidWorkSchemeName);
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("objectId", cautionId);
						((AbstractWorkScheme) scheme2).setParameters(map2);
						SchemeInvoker.invoke(scheme2);
					} catch (Exception e) {
						log.error("无法调用警示的作废功能：" + cautionId, e);
					}
				}
			}
		}
	}
}
