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
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		SignCardNotice signCardNoticeResult = getSignCardNotice(signCard.getId());
		if(signCardNoticeResult == null){
			SignCardNotice newSignCardNotice = new SignCardNotice();
			newSignCardNotice.setSignCardId(signCard.getId());
			SignCardNotice createSignCardNotice = createSignCardNotice(newSignCardNotice);
			signCardNoticeResult = createSignCardNotice;
		}
		//��֪��ʽ
		signCardNoticeResult.setNoticeType(signCardNotice.getNoticeType());
		//��֪�˱��
		signCardNoticeResult.setNoticeDutyerId(signCardNotice.getNoticeDutyerId());
		//��֪������
		signCardNoticeResult.setNoticeDutyerName(signCardNotice.getNoticeDutyerName());
		//��֪ʱ��
		signCardNoticeResult.setNoticeActualTime(signCardNotice.getNoticeActualTime());
		//��֪��ע
		signCardNoticeResult.setNoticeRemark(signCardNotice.getNoticeRemark());
		//��֪��Ϣ�Ǽ���
		signCardNoticeResult.setNoticeCreator(clerk.getId());
		//��֪ϵͳ����ʱ��
		signCardNoticeResult.setNoticeTime(new Date());
		//������
		signCardNoticeResult.setCreator(clerk.getId());
		//����ʱ��
		signCardNoticeResult.setCreationTime(new Date());
		
		return signCardNoticeDao.update(signCardNoticeResult);
	}
	
}
