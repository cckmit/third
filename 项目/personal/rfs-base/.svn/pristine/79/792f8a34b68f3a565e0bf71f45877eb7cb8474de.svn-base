package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.HistoryRisk;
import cn.redflagsoft.base.dao.HistoryRiskDao;
import cn.redflagsoft.base.service.HistoryRiskService;

public class HistoryRiskServiceImpl implements HistoryRiskService {
	private HistoryRiskDao historyRiskDao;

	public HistoryRiskDao getHistoryRiskDao() {
		return historyRiskDao;
	}

	public void setHistoryRiskDao(HistoryRiskDao historyRiskDao) {
		this.historyRiskDao = historyRiskDao;
	}

	public List<HistoryRisk> findHistoryRisk(Long taskId, Integer taskType) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		if (taskType == null) {
			taskType = 102;
		}
		rf.setCriterion(Restrictions.logic(Restrictions.eq("taskSn", taskId))
				.and(Restrictions.eq("objectType", taskType)));
		return historyRiskDao.find(rf);
	}
}
