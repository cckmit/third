package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clue;
import cn.redflagsoft.base.dao.ClueDao;
import cn.redflagsoft.base.service.ClueService;

public class ClueServiceImpl implements ClueService {
	private static final Log log = LogFactory.getLog(ClueServiceImpl.class);
	
	private ClueDao clueDao;
	
	public ClueDao getClueDao() {
		return clueDao;
	}

	public void setClueDao(ClueDao clueDao) {
		this.clueDao = clueDao;
	}

	public Clue getActiveClue(Long objectId, byte category, int bizType, Long bizId) {
		/*
		List<Clue> clues = clueDao.getActiveClue(objectId, category, bizType, bizId);
		if(log.isDebugEnabled()){
			log.debug(String.format("getActiveClue(%s, %s, %s, %s)", objectId, category, bizType, bizId));
			if(clues != null) {
				int i = 0;
				for(Clue clue : clues) {
					log.debug("Clue[" + (i++) + "]: " + clue.toJSONString());
				}
			} else {
				log.debug("No active clue(s).");
			}
		}
		return clues == null ? null : clues.isEmpty() ? null : clues.get(0);
		*/
		
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1);
		if(log.isDebugEnabled()){
			log.debug(String.format("getActiveClue(%s, %s, %s, %s)", objectId, category, bizType, bizId));
			//log.debug("getActiveClue() 改为按时间倒序只取最后一条。");
		}
		Criterion sql = Restrictions.sql("objectId=? and category=? and bizType=? and bizId=? and status<9", 
				new Object[]{objectId, category, bizType, bizId});
		filter.setCriterion(sql);
		//保持原状，不取最新一条
		//filter.setOrder(Order.desc("creationTime"));
		
		List<Clue> list = clueDao.find(filter);
		if(list != null && !list.isEmpty()){
			Clue clue = list.iterator().next();
			if(log.isDebugEnabled()){
				log.debug("ActiveClue: " + clue.toJSONString());
			}
			return clue;
		}
		
		log.debug("Active Clue: null");
		return null;
	}
	
	public Clue createClue(Long objectId, byte category, int bizType, Long bizId, Long bizSN, Long bizTrack) {
		Clue clue = new Clue();
		clue.setObjectId(objectId);
		clue.setCategory(category);
		clue.setBizType(bizType);
		clue.setBizId(bizId);
		clue.setBizSN(bizSN);
		clue.setBizTrack(bizTrack);
		clue.setStatus((byte)1);//在办
		
		return clueDao.save(clue);
	}

	public Clue getClue(Long objectId, byte category) {
		return clueDao.get(Restrictions.logic(
				Restrictions.eq("objectId", objectId)).and(
				Restrictions.eq("category", category)));
	}

	public Clue getClue(Long objectId, byte category, int bizType, Long bizId) {
		return clueDao.get(Restrictions.logic(
				Restrictions.eq("objectId", objectId)).and(
				Restrictions.eq("category", category)).and(
				Restrictions.eq("bizType", bizType)).and(
				Restrictions.eq("bizId", bizId)));
	}

	public boolean updateClueStatus(byte category, Long bizSN, byte status) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(Restrictions.eq("category", category)).and(Restrictions.eq("bizSN", bizSN)));
		List<Clue> elements = clueDao.find(rf);
		if(elements != null && !elements.isEmpty()) {
			Clue tmp;
			for (Clue c : elements) {
				tmp = clueDao.get(c.getKey());
				tmp.setStatus(status);
				clueDao.update(tmp);
			}
			return true;
		} else {
			return false;
		}
	}
	public void deleteClueByBizSN(final Long bizSN){
		clueDao.deleteClueByBizSN(bizSN);
	}
	
}
