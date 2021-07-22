package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardNotice;
import cn.redflagsoft.base.dao.SignCardNoticeDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SignCardNoticeService;

public class SignCardNoticeServiceImpl implements SignCardNoticeService {

	private SignCardNoticeDao signCardNoticeDao;
	
	public SignCardNoticeDao getSignCardNoticeDao() {
		return signCardNoticeDao;
	}

	public void setSignCardNoticeDao(SignCardNoticeDao signCardNoticeDao) {
		this.signCardNoticeDao = signCardNoticeDao;
	}

	public SignCardNotice createSignCardNotice(SignCardNotice signCardNotice) {
		return signCardNoticeDao.save(signCardNotice);
	}

	public SignCardNotice getSignCardNotice(Long signCardId) {
		ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
		SimpleExpression criterions = Restrictions.eq("signCardId", signCardId);
		filters.setCriterion(criterions);
		List<SignCardNotice> list = signCardNoticeDao.find(filters);

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SignCardNotice updateSignCardNotice(SignCard signCard,
			SignCardNotice signCardNotice) {
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		SignCardNotice signCardNoticeResult = getSignCardNotice(signCard.getId());
		if(signCardNoticeResult == null){
			SignCardNotice newSignCardNotice = new SignCardNotice();
			newSignCardNotice.setSignCardId(signCard.getId());
			SignCardNotice createSignCardNotice = createSignCardNotice(newSignCardNotice);
			signCardNoticeResult = createSignCardNotice;
		}
		//告知方式
		signCardNoticeResult.setNoticeType(signCardNotice.getNoticeType());
		//告知人编号
		signCardNoticeResult.setNoticeDutyerId(signCardNotice.getNoticeDutyerId());
		//告知人姓名
		signCardNoticeResult.setNoticeDutyerName(signCardNotice.getNoticeDutyerName());
		//告知时间
		signCardNoticeResult.setNoticeActualTime(signCardNotice.getNoticeActualTime());
		//告知备注
		signCardNoticeResult.setNoticeRemark(signCardNotice.getNoticeRemark());
		//告知信息登记人
		signCardNoticeResult.setNoticeCreator(clerk.getId());
		//告知系统操作时间
		signCardNoticeResult.setNoticeTime(new Date());
		//创建者
		signCardNoticeResult.setCreator(clerk.getId());
		//创建时间
		signCardNoticeResult.setCreationTime(new Date());
		
		return signCardNoticeDao.update(signCardNoticeResult);
	}
	
}
