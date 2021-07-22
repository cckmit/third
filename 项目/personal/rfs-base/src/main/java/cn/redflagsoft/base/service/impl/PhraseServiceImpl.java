package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Phrase;
import cn.redflagsoft.base.dao.PhraseDao;
import cn.redflagsoft.base.service.PhraseService;

public class PhraseServiceImpl implements PhraseService{
	private PhraseDao phraseDao;

	public void setPhraseDao(PhraseDao phraseDao) {
		this.phraseDao = phraseDao;
	}

	public List<Phrase> findPhraseByTypes(int taskType, int workType,
			int processType, Long clerkID) {
		Logic logic = Restrictions.logic(Restrictions.eq("taskType", taskType))
				.and(Restrictions.eq("workType", workType)).and(
						Restrictions.eq("processType", processType)).and(
						Restrictions.eq("clerkID", 0L));// clerkId为0时表示公用部分
		if (null != clerkID) {
			logic.and(Restrictions.eq("clerkID", clerkID));
		}
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(logic);
		rf.setOrder(Order.asc("displayOrder"));
		return phraseDao.find(rf);
	}
	
	public List<Phrase> findPhraseByType(int processType) {
		Criterion c = Restrictions.eq("processType", processType);
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(c);
		rf.setOrder(Order.asc("displayOrder"));
		return phraseDao.find(rf);
	}
	
	public Phrase getPhraseBySn(Long sn){
		return phraseDao.get(sn);
	}
	
	/**
	 * 通过Types查询下拉数据
	 * @param filter
	 * @return
	 */
	@Queryable
	public List<Map<String,Object>> findTitleByTypes(ResultFilter filter) {
		if(filter == null) {
			filter = new ResultFilter();
		}
		filter.setOrder(Order.asc("displayOrder"));
		
		List<Phrase> list = phraseDao.find(filter);
		List<Map<String,Object>> listMap = new ArrayList<Map<String, Object>>();
		if(!list.isEmpty()) {
			for(Phrase ph:list) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("label", ph.getTitle());
				map.put("data", ph.getDisplayOrder());
				map.put("content", ph.getContent());
				map.put("selected", ph.getSelected());
				
				listMap.add(map);
			}
		} 
		return listMap;
	}
}
