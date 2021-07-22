package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.dao.MatterDatumDao;
import cn.redflagsoft.base.service.MatterDatumService;

public class MatterDatumServiceImpl implements MatterDatumService{
	private MatterDatumDao matterDatumDao;

	public MatterDatumDao getMatterDatumDao() {
		return matterDatumDao;
	}
	
	public void setMatterDatumDao(MatterDatumDao matterDatumDao) {
		this.matterDatumDao = matterDatumDao;
	}

	public List<MatterDatum> findMatterDatum(int taksType, int workType, int processType, Long matterID) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("taskType", taksType))
			.and(Restrictions.eq("workType", workType))
			.and(Restrictions.eq("processType", processType))
			.and(Restrictions.eq("status", (byte)1));
		if(matterID != null) {
			logic.and(Restrictions.eq("matterID", matterID));
		}
		resultFilter.setCriterion(logic);
		return matterDatumDao.find(resultFilter);
	}
}
