package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardAccept;
import cn.redflagsoft.base.dao.SignCardAcceptDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardAcceptService;

public class SignCardAcceptServiceImpl implements SignCardAcceptService {

	private SignCardAcceptDao signCardAcceptDao;
	
	public SignCardAcceptDao getSignCardAcceptDao() {
		return signCardAcceptDao;
	}

	public void setSignCardAcceptDao(SignCardAcceptDao signCardAcceptDao) {
		this.signCardAcceptDao = signCardAcceptDao;
	}

	public SignCardAccept createSignCardAccept(SignCardAccept signCardAccept) {
		
		return signCardAcceptDao.save(signCardAccept);
	}

	public SignCardAccept getSignCardAccept(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardAccept> list = signCardAcceptDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardAccept updateSignCardAccept(SignCard signCard,SignCardAccept signCardAccept) {
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		SignCardAccept signCardAcceptResult = getSignCardAccept(signCard.getId());
		if(signCardAcceptResult == null){
			SignCardAccept newSignCardAccept = new SignCardAccept();
			newSignCardAccept.setSignCardId(signCard.getId());
			SignCardAccept createSignCardAccept = createSignCardAccept(newSignCardAccept);
			signCardAcceptResult = createSignCardAccept;
		}
		//受理类型
		signCardAcceptResult.setAcceptType(signCardAccept.getAcceptType());
		//受理实际时间
		signCardAcceptResult.setAcceptActualTime(signCardAccept.getAcceptActualTime());
		//受理备注
		signCardAcceptResult.setAcceptRemark(signCardAccept.getAcceptRemark());
		//受理人编号
		signCardAcceptResult.setAcceptDutyerId(signCardAccept.getAcceptDutyerId());
		//受理人姓名
		signCardAcceptResult.setAcceptDutyerName(signCardAccept.getAcceptDutyerName());
		//受理信息登记人
		signCardAcceptResult.setAcceptCreator(clerk.getId());
		//受理系统操作时间
		signCardAcceptResult.setAcceptTime(new Date());
		//创建者
		signCardAcceptResult.setCreator(clerk.getId());
		//创建时间
		signCardAcceptResult.setCreationTime(new Date());
		
		return signCardAcceptDao.update(signCardAcceptResult);
	}

}
