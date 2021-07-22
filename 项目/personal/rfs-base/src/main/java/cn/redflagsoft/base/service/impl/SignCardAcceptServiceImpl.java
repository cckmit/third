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
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		SignCardAccept signCardAcceptResult = getSignCardAccept(signCard.getId());
		if(signCardAcceptResult == null){
			SignCardAccept newSignCardAccept = new SignCardAccept();
			newSignCardAccept.setSignCardId(signCard.getId());
			SignCardAccept createSignCardAccept = createSignCardAccept(newSignCardAccept);
			signCardAcceptResult = createSignCardAccept;
		}
		//��������
		signCardAcceptResult.setAcceptType(signCardAccept.getAcceptType());
		//����ʵ��ʱ��
		signCardAcceptResult.setAcceptActualTime(signCardAccept.getAcceptActualTime());
		//����ע
		signCardAcceptResult.setAcceptRemark(signCardAccept.getAcceptRemark());
		//�����˱��
		signCardAcceptResult.setAcceptDutyerId(signCardAccept.getAcceptDutyerId());
		//����������
		signCardAcceptResult.setAcceptDutyerName(signCardAccept.getAcceptDutyerName());
		//������Ϣ�Ǽ���
		signCardAcceptResult.setAcceptCreator(clerk.getId());
		//����ϵͳ����ʱ��
		signCardAcceptResult.setAcceptTime(new Date());
		//������
		signCardAcceptResult.setCreator(clerk.getId());
		//����ʱ��
		signCardAcceptResult.setCreationTime(new Date());
		
		return signCardAcceptDao.update(signCardAcceptResult);
	}

}
