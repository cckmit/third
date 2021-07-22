/*
 * $Id: MatterDatumHibernateDao.java 4677 2011-09-14 01:29:38Z lcj $
 * BizDatumHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.ArrayList;
import java.util.List;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithMatterDatum;
import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.dao.MatterDatumDao;

/**
 * @author mwx
 *
 */
public class MatterDatumHibernateDao extends AbstractBaseHibernateDao<MatterDatum,Long> implements MatterDatumDao{
	
	
	public List<DatumCategoryWithMatterDatum> findDatumCategoryWithMatterDatum(ResultFilter filter){
		String qs = "select a, b from DatumCategory a, MatterDatum b where b.datumType=a.id";
		@SuppressWarnings("unchecked")
		List<Object[]> list = getQuerySupport().find(qs, filter);
		List<DatumCategoryWithMatterDatum> result = new ArrayList<DatumCategoryWithMatterDatum>();
		for (Object[] data : list) {
			result.add(new DatumCategoryWithMatterDatum((DatumCategory) data[0], (MatterDatum) data[1]));
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.MatterDatumDao#findDatumCategory(short, short, short, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID) {
		String qs = "select a, b from DatumCategory a, MatterDatum b where b.datumType=a.id and b.taskType=?"
				+ " and b.workType=? and b.processType=? and b.matterID=? order by b.displayOrder";
		List<Object[]> list = getHibernateTemplate().find(qs,
				new Object[] { taskType, workType, processType, matterID });
		List<DatumCategory> result = new ArrayList<DatumCategory>();
		for (Object[] data : list) {
			result.add(new DatumCategoryWithMatterDatum((DatumCategory) data[0], (MatterDatum) data[1]));
		}
		return result;
		//return findDatumCategory(taskType, workType, processType, matterID, MatterDatum.TYPE_д╛хо);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.MatterDatumDao#findDatumCategory(short, short, short, java.lang.Long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<DatumCategory> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType) {
		String qs = "select a, b from DatumCategory a, MatterDatum b where b.datumType=a.id and b.taskType=?"
				+ " and b.workType=? and b.processType=? and b.matterID=? and b.type=? order by b.displayOrder";
		List<Object[]> list = getHibernateTemplate().find(qs, new Object[] { taskType, workType, processType, matterID, matterDatumType});
		List<DatumCategory> result = new ArrayList<DatumCategory>();
		for (Object[] data : list) {
			result.add(new DatumCategoryWithMatterDatum((DatumCategory) data[0], (MatterDatum) data[1]));
		}
		return result;
	}
	
	
	public List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("taskType", taksType))
			.and(Restrictions.eq("workType", workType))
			.and(Restrictions.eq("processType", processType))
			.and(Restrictions.eq("status", (byte)1));
		if(matterID != null) {
			logic.and(Restrictions.eq("matterID", matterID));
		}
		if(category != null) {
			logic.and(Restrictions.eq("category", category.byteValue()));
		}
		resultFilter.setCriterion(logic);
		resultFilter.setOrder(Order.asc("displayOrder"));
		return super.find(resultFilter);
	}
	
	public List<MatterDatum> findMatterDatum(Byte category, int taksType, int workType, int processType, Long matterID,Long datumType) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("taskType", taksType))
			.and(Restrictions.eq("workType", workType))
			.and(Restrictions.eq("processType", processType))
			.and(Restrictions.eq("datumType", datumType))
			.and(Restrictions.eq("status", (byte)1));
		if(matterID != null) {
			logic.and(Restrictions.eq("matterID", matterID));
		}
		if(category != null) {
			logic.and(Restrictions.eq("category", category.byteValue()));
		}
		resultFilter.setCriterion(logic);
		resultFilter.setOrder(Order.asc("displayOrder"));
		return super.find(resultFilter);
	}
}
