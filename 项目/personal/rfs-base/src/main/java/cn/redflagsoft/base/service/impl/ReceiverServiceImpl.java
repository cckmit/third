package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Receiver;
import cn.redflagsoft.base.dao.ReceiverDao;
import cn.redflagsoft.base.service.ReceiverService;

public class ReceiverServiceImpl implements ReceiverService {
	private ReceiverDao receiverDao;
	
	public void setReceiverDao(ReceiverDao receiverDao) {
		this.receiverDao = receiverDao;
	}

	public List<Receiver> findReceiverByTypes(int taskType, int workType,
			int processType, Long clerkID) {
		Logic logic = Restrictions.logic(Restrictions.eq("taskType", taskType))
				.and(Restrictions.eq("workType", workType)).and(
						Restrictions.eq("processType", processType)).and(
						Restrictions.eq("clerkID", 0L));// clerkId为0时表示公用部分
		if(null != clerkID) {
			logic.and(Restrictions.eq("clerkID", clerkID));
		}
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(logic);
		//rf.setOrder(Order.asc(""));
		return receiverDao.find(rf);
	}
		
		public List<Receiver> findReceiver(Integer taskType, Integer workType,
				Integer processType, Long clerkID) {
			Logic logicTemp =Restrictions.logic(Restrictions.eq("clerkID", 0L)).or(Restrictions.eq("clerkID", clerkID));// clerkId为0时表示公用部分
			Logic logic =Restrictions.logic(logicTemp);
			if(null!=taskType){
				logic.and(Restrictions.eq("taskType", taskType));
			}
			if(null!=workType){
				logic.and(Restrictions.eq("workType", workType));
			}
			if(null!=processType){
				logic.and(Restrictions.eq("processType", processType));
			}
			logic.and(Restrictions.eq("status", (byte)1));
			ResultFilter rf = ResultFilter.createEmptyResultFilter();
			rf.setCriterion(logic);
			//rf.setOrder(Order.asc(""));
			return receiverDao.find(rf);
		
	}
}
