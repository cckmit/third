package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardSurvey;
import cn.redflagsoft.base.dao.SignCardSurveyDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardSurveyService;

public class SignCardSurveyServiceImpl implements SignCardSurveyService {

	private SignCardSurveyDao signCardSurveyDao;
	
	public SignCardSurveyDao getSignCardSurveyDao() {
		return signCardSurveyDao;
	}

	public void setSignCardSurveyDao(SignCardSurveyDao signCardSurveyDao) {
		this.signCardSurveyDao = signCardSurveyDao;
	}

	public SignCardSurvey createSignCardSurvey(SignCardSurvey signCardSurvey) {
		return signCardSurveyDao.save(signCardSurvey);
	}

	public SignCardSurvey getSignCardSurvey(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardSurvey> list = signCardSurveyDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardSurvey updateSignCardSurvey(SignCard signCard,SignCardSurvey signCardSurvey) {
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		
		SignCardSurvey signCardSurveyResult = getSignCardSurvey(signCard.getId());
		if(signCardSurveyResult == null){
			SignCardSurvey newSignCardSurvey = new SignCardSurvey();
			newSignCardSurvey.setSignCardId(signCard.getId());
			SignCardSurvey createSignCardSurvey = createSignCardSurvey(newSignCardSurvey);
			signCardSurveyResult = createSignCardSurvey;
		}
		//调查结论摘要
		signCardSurveyResult.setSurveySummary(signCardSurvey.getSurveySummary());
		//事实说明
		signCardSurveyResult.setSurveyTruth(signCardSurvey.getSurveyTruth());
		//处理方式
		signCardSurveyResult.setDealType(signCardSurvey.getDealType());
		//调查人编号
		signCardSurveyResult.setSurveyDutyerId(signCardSurvey.getSurveyDutyerId());
		//调查人姓名
		signCardSurveyResult.setSurveyDutyerName(signCardSurvey.getSurveyDutyerName());
		//调查实际时间
		signCardSurveyResult.setSurveyActualTime(signCardSurvey.getSurveyActualTime());
		//调查备注
		signCardSurveyResult.setSurveyRemark(signCardSurvey.getSurveyRemark());
		//调查信息登记人
		signCardSurveyResult.setSurveyCreator(clerk.getId());
		//调查系统操作时间
		signCardSurveyResult.setSurveyTime(new Date());
		//创建者
		signCardSurveyResult.setCreator(clerk.getId());
		//创建时间
		signCardSurveyResult.setCreationTime(new Date());
		
		return signCardSurveyDao.update(signCardSurveyResult);
	}

}
