package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardPenalty;
import cn.redflagsoft.base.dao.SignCardPenaltyDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardPenaltyService;

public class SignCardPenaltyServiceImpl implements SignCardPenaltyService {
	private SignCardPenaltyDao signCardPenaltyDao;

	public SignCardPenaltyDao getSignCardPenaltyDao() {
		return signCardPenaltyDao;
	}

	public void setSignCardPenaltyDao(SignCardPenaltyDao signCardPenaltyDao) {
		this.signCardPenaltyDao = signCardPenaltyDao;
	}

	public SignCardPenalty createSignCardPenalty(SignCardPenalty signCardPenalty) {
		return signCardPenaltyDao.save(signCardPenalty);
	}

	public SignCardPenalty getSignCardPenalty(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardPenalty> list = signCardPenaltyDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardPenalty updateSignCardPenalty(SignCard signCard,
			SignCardPenalty signCardPenalty) {
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		SignCardPenalty signCardPenaltyResult = getSignCardPenalty(signCard.getId());
		if(signCardPenaltyResult == null){
			SignCardPenalty newSignCardPenalty = new SignCardPenalty();
			newSignCardPenalty.setSignCardId(signCard.getId());
			SignCardPenalty createSignCardPenalty = createSignCardPenalty(newSignCardPenalty);
			signCardPenaltyResult = createSignCardPenalty;
		}
		//处理情况摘要
		signCardPenaltyResult.setPenaltySummary(signCardPenalty.getPenaltySummary());
		//处理人编号
		signCardPenaltyResult.setPenaltyDutyerId(signCardPenalty.getPenaltyDutyerId());
		//处理人姓名
		signCardPenaltyResult.setPenaltyDutyerName(signCardPenalty.getPenaltyDutyerName());
		//处理时间
		signCardPenaltyResult.setPenaltyActualTime(signCardPenalty.getPenaltyActualTime());
		//处理备注
		signCardPenaltyResult.setPenaltyRemark(signCardPenalty.getPenaltyRemark());
		//处理信息登记人
		signCardPenaltyResult.setPenaltyCreator(clerk.getId());
		//处理系统操作时间
		signCardPenaltyResult.setPenaltyTime(new Date());
		//创建者
		signCardPenaltyResult.setCreator(clerk.getId());
		//创建时间
		signCardPenaltyResult.setCreationTime(new Date());
		
		return signCardPenaltyDao.update(signCardPenaltyResult);
	}

}
