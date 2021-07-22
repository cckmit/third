package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardCheck;
import cn.redflagsoft.base.dao.SignCardCheckDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardCheckService;

public class SignCardCheckServiceImpl implements SignCardCheckService {
	private SignCardCheckDao signCardCheckDao;

	public SignCardCheckDao getSignCardCheckDao() {
		return signCardCheckDao;
	}

	public void setSignCardCheckDao(SignCardCheckDao signCardCheckDao) {
		this.signCardCheckDao = signCardCheckDao;
	}

	public SignCardCheck createSignCardCheck(SignCardCheck signCardCheck) {
		return signCardCheckDao.save(signCardCheck);
	}

	public SignCardCheck getSignCardCheck(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardCheck> list = signCardCheckDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardCheck updateSignCardCheck(SignCard signCard,
			SignCardCheck signCardCheck) {
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		
		SignCardCheck signCardCheckResult = getSignCardCheck(signCard.getId());
		if(signCardCheckResult == null){
			SignCardCheck newSignCardCheck = new SignCardCheck();
			newSignCardCheck.setSignCardId(signCard.getId());
			SignCardCheck createSignCardCheck = createSignCardCheck(newSignCardCheck);
			signCardCheckResult = createSignCardCheck;
		}
		//整改情况摘要
		signCardCheckResult.setCheckSummary(signCardCheck.getCheckSummary());
		//整改核查人编号
		signCardCheckResult.setCheckDutyerId(signCardCheck.getCheckDutyerId());
		//整改核查人姓名
		signCardCheckResult.setCheckDutyerName(signCardCheck.getCheckDutyerName());
		//整改核查时间
		signCardCheckResult.setCheckActualTime(signCardCheck.getCheckActualTime());
		//整改核查备注
		signCardCheckResult.setCheckRemark(signCardCheck.getCheckRemark());
		//整改核查信息登记人
		signCardCheckResult.setCheckCreator(clerk.getId());
		//整改核查系统操作时间
		signCardCheckResult.setCheckTime(new Date());
		//创建者
		signCardCheckResult.setCreator(clerk.getId());
		//创建时间
		signCardCheckResult.setCreationTime(new Date());
		
		return signCardCheckDao.update(signCardCheckResult);
	}
	
}
